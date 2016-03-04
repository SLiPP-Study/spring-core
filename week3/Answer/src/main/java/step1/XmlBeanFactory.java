package step1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class XmlBeanFactory {

    private static final String BEAN_ELEMENT = "bean";
    private static final String CLASS_ATTRIBUTE = "class";
    private static final String ID_ATTRIBUTE = "id";

    /**
     * Map of Bean objects, keyed by id attribute
     */
    private Map beanHash = new HashMap();
    public <T> T getBean(String key, Class<T> claz) {
        return (T) getBean(key);
    }
    public Object getBean(String key) {
        return beanHash.get(key);
    }

    public XmlBeanFactory(String fileName) {
        try {
            loadBeans(fileName);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(String.format("Cant not open file : %s", fileName));
        }
    }

    public XmlBeanFactory(InputStream inputStream) {
        loadBeans(inputStream);
    }

    private void loadBeans(String location) throws FileNotFoundException {
        loadBeans(new FileInputStream(location));
    }

    private void loadBeans(InputStream inputStream) {
        if (inputStream == null)
            throw new IllegalArgumentException("InputStream cannot be null: expected an XML file");

        try (InputStream is = inputStream){
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);

            loadBeans(doc);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Parsing XML Exception !");

        } catch (SAXException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid XML Document !");

        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Parsing XML Document Exception !");
        }
    }

    private void loadBeans(Document doc) {
        Element root = doc.getDocumentElement();
        NodeList nl = root.getElementsByTagName(BEAN_ELEMENT);

        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            loadBean((Element) n);
        }
    }

    private void loadBean(Element element) {
        String id = element.getAttribute(ID_ATTRIBUTE);
        if (id == null || "".equals(id))
            throw new IllegalArgumentException("Bean without id attribute");
        String classCanonicalName = element.getAttribute(CLASS_ATTRIBUTE);

        try {
            Class claz = Class.forName(classCanonicalName);
            registerBean(id, claz.newInstance());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(String.format("Invalid Class Path %s", classCanonicalName));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void registerBean(String propertyName, Object claz) {
        beanHash.put(propertyName, claz);
    }
}
