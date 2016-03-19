package woojin.spring.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * Created by woojin on 2016. 3. 16.
 */
@Slf4j
public class XmlBeanFactory {
	public static final String CLASS_ATTR = "class";
	public static final String ID_ATTR = "id";
	public static final String PROPERTY_NAME = "property";
	public static final String NAME_ATTR = "name";
	public static final String VALUE_ATTR = "value";

	private Document document;
	private static final Map<String, Object> BEAN_CONTAINER = new HashMap<>();
	private static final Map<String, Map<String, String>> BEAN_PROPERTIES = new HashMap<>();

	public XmlBeanFactory(InputStream inputStream) {

		beforeParseXml();
		parseXml(inputStream);
		afterParseXml();

		initBeans();
	}

	private void initBeans() {
		NodeList beanNodes = document.getElementsByTagName("bean");
		IntStream.range(0, beanNodes.getLength()).forEach(i -> putBean(beanNodes.item(i)));
	}

	private void putBean(Node nNode) {
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;
			String beanId = eElement.getAttribute(ID_ATTR);
			String beanClassQName = eElement.getAttribute(CLASS_ATTR);

			log.debug("Bean id: {} / class: {}", beanId, beanClassQName);
			try {
				final Object c = Class.forName(beanClassQName).newInstance();

				NodeList childNodes = eElement.getChildNodes();
				String[] packages = StringUtils.split(beanClassQName, ".");
				putProperties(packages[packages.length-1], childNodes);
				Map<String, String> properties = BEAN_PROPERTIES.get(beanId);
				properties.entrySet().stream().forEach(e -> invokeSetter(c, e.getKey(), e.getValue()));

				BEAN_CONTAINER.put(beanId, c);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	private <T> void invokeSetter(T bean, String propName, String propVal) {
		try {
			Class beanType = bean.getClass();
			Field field = beanType.getField(propName);
			Class fieldClazz = beanType.getField(propName).getType();

			if (fieldClazz.equals(Integer.class)) {
				beanType.getDeclaredMethod("set" + WordUtils.capitalize(propName), Integer.class)
						.invoke(bean, Integer.parseInt(propVal));

			} else if (fieldClazz.equals(String.class)) {
				beanType.getDeclaredMethod("set" + WordUtils.capitalize(propName), String.class)
						.invoke(bean, propVal);
			}
		} catch (NoSuchFieldException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}

	private void putProperties(String beanName, NodeList propertyNodes) {
		Map<String, String> properties = new ConcurrentHashMap<>();
		IntStream.range(0, propertyNodes.getLength())
				.parallel()
				.mapToObj(propertyNodes::item)
				.filter(n -> n.getNodeName().equals(PROPERTY_NAME))
				.filter(n -> n.getNodeType() == Node.ELEMENT_NODE)
				.map(n -> (Element) n)
				.forEach(e -> {
					final String propName = e.getAttribute(NAME_ATTR);
					final String propVal = e.getAttribute(VALUE_ATTR);
					properties.put(propName, propVal);
					BEAN_PROPERTIES.put(beanName, properties);
				});
	}

	protected void beforeParseXml() {
		log.debug("before parse xml");
	}

	protected void afterParseXml() {
		log.debug("after parse xml");
	}

	private void parseXml(InputStream inputStream) {
		log.debug("start parse xml");

		DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = df.newDocumentBuilder();
			assert inputStream != null;
			document = db.parse(inputStream);
			document.getDocumentElement().normalize();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	public <T> T getBean(String beanName, Class<T> beanClassType) {
		return beanClassType.cast(BEAN_CONTAINER.get(beanName));
	}

}

