package woojin.spring.core;

import lombok.extern.slf4j.Slf4j;
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
			Object c = null;
			try {
				c = Class.forName(beanClassQName).newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			BEAN_CONTAINER.put(beanId, c);

			putProperties(beanClassQName, eElement.getElementsByTagName(PROPERTY_NAME));
		}
	}

	private void putProperties(String beanName, NodeList propertyNodes) {
		IntStream.range(0, propertyNodes.getLength()).forEach(i -> {
			Node propertyNode = propertyNodes.item(i);
			if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) propertyNode;
				String propName = eElement.getAttribute(NAME_ATTR);
				String propVal = eElement.getAttribute(VALUE_ATTR);
				Map<String, String> properties = new HashMap<>();
				properties.put(NAME_ATTR, propName);
				properties.put(VALUE_ATTR, propVal);
				BEAN_PROPERTIES.put(beanName, properties);
			}
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
		T bean = beanClassType.cast(BEAN_CONTAINER.get(beanName));

		Map<String, String> properties = BEAN_PROPERTIES.get(beanName);

		properties.entrySet().stream().forEach(e -> invokeSetter(bean, e.getKey(), e.getValue()));

		return bean;
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
				beanType.getDeclaredMethod("set" + WordUtils.capitalize(propName), Integer.class)
						.invoke(bean, propVal);
			}
		} catch (NoSuchFieldException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}
}

