package woojin.spring.core;

import lombok.extern.slf4j.Slf4j;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by woojin on 2016. 3. 16.
 */
@Slf4j
public class XmlBeanFactory {
	public static final String CLASS_ATTR = "class";
	public static final String ID_ATTR = "id";
	private Document document;
	private static final Map<String, Object> BEAN_CONTAINER = new HashMap<>();

	public XmlBeanFactory(InputStream inputStream) {

		beforeParseXml();
		parseXml(inputStream);
		afterParseXml();

		initBeans();
	}

	private void initBeans() {
		NodeList nList = document.getElementsByTagName("bean");

		try {
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String beanId = eElement.getAttribute(ID_ATTR);
					String beanClassQname = eElement.getAttribute(CLASS_ATTR);
					log.debug("Bean id: {} / class: {}", beanId, beanClassQname);
					Object c = Class.forName(beanClassQname).newInstance();
					BEAN_CONTAINER.put(beanId, c);
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

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

