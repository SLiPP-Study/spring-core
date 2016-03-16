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

/**
 * Created by woojin on 2016. 3. 16.
 */
@Slf4j
public class XmlBeanFactory {
	public static final String CLASS_FIELDS = "class";
	public static final String ID_FIELDS = "id";
	private Document document;

	public XmlBeanFactory(InputStream inputStream) {

		beforeParseXml();
		parseXml(inputStream);
		afterParseXml();

		initBeans();
	}

	private void initBeans() {
		NodeList nList = document.getElementsByTagName("bean");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String beanId = eElement.getAttribute(ID_FIELDS);
				String beanClassPackage = eElement.getAttribute(CLASS_FIELDS);
				log.debug("Bean id: {} / class: {}", beanId, beanClassPackage);
			}
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
		return null;
	}
}

