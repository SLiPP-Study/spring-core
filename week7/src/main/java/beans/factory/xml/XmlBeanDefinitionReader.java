package beans.factory.xml;

import beans.PropertyValue;
import beans.PropertyValues;
import beans.factory.config.BeanDefinition;
import beans.factory.support.BeanDefinitionReader;
import beans.factory.support.BeanDefinitionRegistry;
import core.ConstructorArgument;
import core.ConstructorArguments;
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

public class XmlBeanDefinitionReader implements BeanDefinitionReader {

    private static final String BEAN_ELEMENT = "bean";
    private static final String CLASS_ATTRIBUTE = "class";
    private static final String ID_ATTRIBUTE = "id";
    private static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
    private static final String PROPERTY_ELEMENT = "property";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String VALUE_ATTRIBUTE = "value";
    private static final String REF_ATTRIBUTE = "ref";
    private static final String SCOPE_ATTRIBUTE = "scope";

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void loadBeanDefinitions(InputStream inputStream) {
        if (inputStream == null)
            throw new IllegalArgumentException("InputStream cannot be null: expected an XML file");

        try (InputStream is = inputStream) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);

            loadBeanDefinitions(doc);
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

    private void loadBeanDefinitions(Document doc) {
        Element root = doc.getDocumentElement();
        NodeList nl = root.getElementsByTagName(BEAN_ELEMENT);

        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            loadBeanDefinition((Element)n);
        }
    }

    private void loadBeanDefinition(Element element) {
        String id = element.getAttribute(ID_ATTRIBUTE);
        if (id == null || "".equals(id))
            throw new IllegalArgumentException("Bean without id attribute");

        ConstructorArguments constructorArguments = createConstructorArguments(element);
        String scope = readScopeValue(element);
        PropertyValues propertyValues = createPropertyValues(element);
        BeanDefinition beanDefinition = createBeanDefinition(element, id, constructorArguments, propertyValues,
            scope);
        beanDefinitionRegistry.registerBeanDefinition(id, beanDefinition);
    }

    /**
     * element에 scope attribute 가 있는 경우 이를 읽어서 리턴한다.
     * @param element
     * @return
     */
    private String readScopeValue(Element element) {
        // Todo: DOM API 를 사용해서 element 를 파싱한다.
        String scopeValue = element.getAttribute(SCOPE_ATTRIBUTE);
        if (scopeValue == null || "".equals(scopeValue)) {
            return BeanDefinition.SCOPE_SINGLETON;
        }
        return scopeValue;
    }

    private ConstructorArguments createConstructorArguments(Element beanElement) {
        ConstructorArguments constructorArguments = new ConstructorArguments();

        NodeList nodeList = beanElement.getElementsByTagName(CONSTRUCTOR_ARG_ELEMENT);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element)nodeList.item(i);
            ConstructorArgument constructorArgument = new ConstructorArgument(
                element.getAttribute(REF_ATTRIBUTE));
            constructorArguments.addConstructorArgument(constructorArgument);
        }

        return constructorArguments;
    }

    private PropertyValue createPropertyValue(Element propElement) {
        String propertyName = propElement.getAttribute(NAME_ATTRIBUTE);
        if (propertyName == null || "".equals(propertyName))
            throw new IllegalArgumentException("Property without a name");
        return new PropertyValue(propertyName, getValue(propElement));
    }

    private Object getValue(Element propElement) {
        //nested value, 예를들어 List, Map등의 판별을 통해 데이터를 만들어내야 하지만
        //학습을 위한 구현이므로 하나의 데이터를 저장하게끔 구현한다
        return propElement.getAttribute(VALUE_ATTRIBUTE);
    }

    private PropertyValues createPropertyValues(Element beanElement) {
        PropertyValues propertyValues = new PropertyValues();
        NodeList nl = beanElement.getElementsByTagName(PROPERTY_ELEMENT);
        for (int i = 0; i < nl.getLength(); i++) {
            Element propElement = (Element)nl.item(i);
            PropertyValue propertyValue = createPropertyValue(propElement);
            propertyValues.addPropertyValue(propertyValue);
        }

        return propertyValues;
    }

    private BeanDefinition createBeanDefinition(Element element,
                                                String id,
                                                ConstructorArguments constructorArguments,
                                                PropertyValues propertyValues,
                                                String scope) {
        if (!element.hasAttribute(CLASS_ATTRIBUTE))
            throw new IllegalArgumentException("Bean without class attribute");
        String classname = element.getAttribute(CLASS_ATTRIBUTE);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            // Todo: BeanDefinition 생성자를 수정하여 파라미터로 넘겨받은 scope를 생성자에 넘겨준다.
            return new BeanDefinition(id, Class.forName(classname, true, classLoader), constructorArguments,
                propertyValues, scope);
        } catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException(
                "Error creating bean with name [" + id + "]: class '" + classname + "' not found", e);
        }
    }
}
