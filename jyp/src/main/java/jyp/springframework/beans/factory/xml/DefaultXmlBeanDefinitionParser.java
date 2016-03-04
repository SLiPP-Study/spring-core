package jyp.springframework.beans.factory.xml;

import jyp.springframework.beans.MutablePropertyValues;
import jyp.springframework.beans.PropertyValue;
import jyp.springframework.beans.factory.BeanDefinitionStoreException;
import jyp.springframework.beans.factory.config.BeanDefinition;
import jyp.springframework.beans.factory.config.ConstructorArgumentValues;
import jyp.springframework.beans.factory.config.RuntimeBeanReference;
import jyp.springframework.beans.factory.support.AbstractBeanDefinition;
import jyp.springframework.beans.factory.support.BeanDefinitionHolder;
import jyp.springframework.beans.factory.support.BeanDefinitionRegistry;
import jyp.springframework.beans.factory.support.ChildBeanDefinition;
import jyp.springframework.beans.factory.support.ManagedLinkedMap;
import jyp.springframework.beans.factory.support.RootBeanDefinition;
import jyp.springframework.core.io.Resource;
import jyp.springframework.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.ManagedSet;
import org.springframework.core.JdkVersion;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author jinyoung.park89
 * @since 2016-03-04
 */
public class DefaultXmlBeanDefinitionParser implements XmlBeanDefinitionParser {
    public static final String BEAN_NAME_DELIMITERS = ",; ";
    public static final String TRUE_VALUE = "true";
    public static final String DEFAULT_VALUE = "default";
    public static final String DEFAULT_LAZY_INIT_ATTRIBUTE = "default-lazy-init";
    public static final String DEFAULT_DEPENDENCY_CHECK_ATTRIBUTE = "default-dependency-check";
    public static final String DEFAULT_AUTOWIRE_ATTRIBUTE = "default-autowire";
    public static final String BEAN_ELEMENT = "bean";
    public static final String DESCRIPTION_ELEMENT = "description";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String PARENT_ATTRIBUTE = "parent";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String SINGLETON_ATTRIBUTE = "singleton";
    public static final String DEPENDS_ON_ATTRIBUTE = "depends-on";
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";
    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
    public static final String INDEX_ATTRIBUTE = "index";
    public static final String TYPE_ATTRIBUTE = "type";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String REF_ELEMENT = "ref";
    public static final String IDREF_ELEMENT = "idref";
    public static final String BEAN_REF_ATTRIBUTE = "bean";
    public static final String LOCAL_REF_ATTRIBUTE = "local";
    public static final String LIST_ELEMENT = "list";
    public static final String SET_ELEMENT = "set";
    public static final String MAP_ELEMENT = "map";
    public static final String KEY_ATTRIBUTE = "key";
    public static final String ENTRY_ELEMENT = "entry";
    public static final String VALUE_ELEMENT = "value";
    public static final String NULL_ELEMENT = "null";
    public static final String PROPS_ELEMENT = "props";
    public static final String PROP_ELEMENT = "prop";
    public static final String LAZY_INIT_ATTRIBUTE = "lazy-init";
    public static final String DEPENDENCY_CHECK_ATTRIBUTE = "dependency-check";
    public static final String DEPENDENCY_CHECK_ALL_ATTRIBUTE_VALUE = "all";
    public static final String DEPENDENCY_CHECK_SIMPLE_ATTRIBUTE_VALUE = "simple";
    public static final String DEPENDENCY_CHECK_OBJECTS_ATTRIBUTE_VALUE = "objects";
    public static final String AUTOWIRE_ATTRIBUTE = "autowire";
    public static final String AUTOWIRE_BY_NAME_VALUE = "byName";
    public static final String AUTOWIRE_BY_TYPE_VALUE = "byType";
    public static final String AUTOWIRE_CONSTRUCTOR_VALUE = "constructor";
    public static final String AUTOWIRE_AUTODETECT_VALUE = "autodetect";
    protected final Log logger = LogFactory.getLog(this.getClass());
    private BeanDefinitionRegistry beanFactory;
    private ClassLoader beanClassLoader;
    private Resource resource;
    private String defaultLazyInit;
    private String defaultDependencyCheck;
    private String defaultAutowire;

    public DefaultXmlBeanDefinitionParser() {
    }

    public void registerBeanDefinitions(BeanDefinitionRegistry beanFactory, ClassLoader beanClassLoader, Document doc, Resource resource) {
        this.beanFactory = beanFactory;
        this.beanClassLoader = beanClassLoader;
        this.resource = resource;
        this.logger.debug("Loading bean definitions");
        Element root = doc.getDocumentElement();
        this.defaultLazyInit = root.getAttribute("default-lazy-init");
        this.logger.debug("Default lazy init \'" + this.defaultLazyInit + "\'");
        this.defaultDependencyCheck = root.getAttribute("default-dependency-check");
        this.logger.debug("Default dependency check \'" + this.defaultDependencyCheck + "\'");
        this.defaultAutowire = root.getAttribute("default-autowire");
        this.logger.debug("Default autowire \'" + this.defaultAutowire + "\'");
        NodeList nl = root.getChildNodes();
        int beanDefinitionCounter = 0;

        for(int i = 0; i < nl.getLength(); ++i) {
            Node node = nl.item(i);
            if(node instanceof Element && "bean".equals(node.getNodeName())) {
                ++beanDefinitionCounter;
                this.registerBeanDefinition((Element)node);
            }
        }

        this.logger.debug("Found " + beanDefinitionCounter + " <" + "bean" + "> elements defining beans");
    }

    protected BeanDefinitionRegistry getBeanFactory() {
        return this.beanFactory;
    }

    protected ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    protected String getDefaultLazyInit() {
        return this.defaultLazyInit;
    }

    protected String getDefaultDependencyCheck() {
        return this.defaultDependencyCheck;
    }

    protected String getDefaultAutowire() {
        return this.defaultAutowire;
    }

    protected void registerBeanDefinition(Element ele) {
        BeanDefinitionHolder bdHolder = this.parseBeanDefinition(ele);
        this.logger.debug("Registering bean definition with id \'" + bdHolder.getBeanName() + "\'");
        this.beanFactory.registerBeanDefinition(bdHolder.getBeanName(), bdHolder.getBeanDefinition());
        if(bdHolder.getAliases() != null) {
            for(int i = 0; i < bdHolder.getAliases().length; ++i) {
                this.beanFactory.registerAlias(bdHolder.getBeanName(), bdHolder.getAliases()[i]);
            }
        }

    }

    protected BeanDefinitionHolder parseBeanDefinition(Element ele) {
        String id = ele.getAttribute("id");
        String nameAttr = ele.getAttribute("name");
        ArrayList aliases = new ArrayList();
        if(nameAttr != null && !"".equals(nameAttr)) {
            String[] beanDefinition = StringUtils.tokenizeToStringArray(nameAttr, ",; ", true, true);
            aliases.addAll(Arrays.asList(beanDefinition));
        }

        if(id == null || "".equals(id) && !aliases.isEmpty()) {
            id = (String)aliases.remove(0);
            this.logger.debug("No XML \'id\' specified - using \'" + id + "\' as ID and " + aliases + " as aliases");
        }

        BeanDefinition beanDefinition1 = this.parseBeanDefinition(ele, id);
        if(id == null || "".equals(id)) {
            if(beanDefinition1 instanceof RootBeanDefinition) {
                id = ((RootBeanDefinition)beanDefinition1).getBeanClassName();
                this.logger.debug("Neither XML \'id\' nor \'name\' specified - using bean class name [" + id + "] as ID");
            } else if(beanDefinition1 instanceof ChildBeanDefinition) {
                throw new BeanDefinitionStoreException(this.resource, "", "Child bean definition has neither \'id\' nor \'name\'");
            }
        }

        String[] aliasesArray = (String[])aliases.toArray(new String[aliases.size()]);
        return new BeanDefinitionHolder(beanDefinition1, id, aliasesArray);
    }

    protected BeanDefinition parseBeanDefinition(Element ele, String beanName) {
        String className = null;

        try {
            if(ele.hasAttribute("class")) {
                className = ele.getAttribute("class");
            }

            String err = null;
            if(ele.hasAttribute("parent")) {
                err = ele.getAttribute("parent");
            }

            if(className == null && err == null) {
                throw new BeanDefinitionStoreException(this.resource, beanName, "Either \'class\' or \'parent\' is required");
            } else {
                Object bd = null;
                MutablePropertyValues pvs = this.getPropertyValueSubElements(beanName, ele);
                if(className != null) {
                    ConstructorArgumentValues lazyInit = this.getConstructorArgSubElements(beanName, ele);
                    RootBeanDefinition rbd = null;
                    if(this.beanClassLoader != null) {
                        Class dependencyCheck = Class.forName(className, true, this.beanClassLoader);
                        rbd = new RootBeanDefinition(dependencyCheck, lazyInit, pvs);
                    } else {
                        rbd = new RootBeanDefinition(className, lazyInit, pvs);
                    }

                    String dependencyCheck1;
                    if(ele.hasAttribute("depends-on")) {
                        dependencyCheck1 = ele.getAttribute("depends-on");
                        rbd.setDependsOn(StringUtils.tokenizeToStringArray(dependencyCheck1, ",; ", true, true));
                    }

                    dependencyCheck1 = ele.getAttribute("dependency-check");
                    if("default".equals(dependencyCheck1)) {
                        dependencyCheck1 = this.defaultDependencyCheck;
                    }

                    rbd.setDependencyCheck(this.getDependencyCheck(dependencyCheck1));
                    String autowire = ele.getAttribute("autowire");
                    if("default".equals(autowire)) {
                        autowire = this.defaultAutowire;
                    }

                    rbd.setAutowireMode(this.getAutowireMode(autowire));
                    String initMethodName = ele.getAttribute("init-method");
                    if(!initMethodName.equals("")) {
                        rbd.setInitMethodName(initMethodName);
                    }

                    String destroyMethodName = ele.getAttribute("destroy-method");
                    if(!destroyMethodName.equals("")) {
                        rbd.setDestroyMethodName(destroyMethodName);
                    }

                    bd = rbd;
                } else {
                    bd = new ChildBeanDefinition(err, pvs);
                }

                if(ele.hasAttribute("singleton")) {
                    ((AbstractBeanDefinition)bd).setSingleton("true".equals(ele.getAttribute("singleton")));
                }

                String lazyInit1 = ele.getAttribute("lazy-init");
                if("default".equals(lazyInit1) && ((AbstractBeanDefinition)bd).isSingleton()) {
                    lazyInit1 = this.defaultLazyInit;
                }

                ((AbstractBeanDefinition)bd).setLazyInit("true".equals(lazyInit1));
                ((AbstractBeanDefinition)bd).setResourceDescription(this.resource.getDescription());
                return (BeanDefinition)bd;
            }
        } catch (ClassNotFoundException var13) {
            throw new BeanDefinitionStoreException(this.resource, beanName, "Bean class [" + className + "] not found", var13);
        } catch (NoClassDefFoundError var14) {
            throw new BeanDefinitionStoreException(this.resource, beanName, "Class that bean class [" + className + "] depends on not found", var14);
        }
    }

    protected ConstructorArgumentValues getConstructorArgSubElements(String beanName, Element beanEle) throws ClassNotFoundException {
        NodeList nl = beanEle.getChildNodes();
        ConstructorArgumentValues cargs = new ConstructorArgumentValues();

        for(int i = 0; i < nl.getLength(); ++i) {
            Node node = nl.item(i);
            if(node instanceof Element && "constructor-arg".equals(node.getNodeName())) {
                this.parseConstructorArgElement(beanName, cargs, (Element)node);
            }
        }

        return cargs;
    }

    protected MutablePropertyValues getPropertyValueSubElements(String beanName, Element beanEle) {
        NodeList nl = beanEle.getChildNodes();
        MutablePropertyValues pvs = new MutablePropertyValues();

        for(int i = 0; i < nl.getLength(); ++i) {
            Node node = nl.item(i);
            if(node instanceof Element && "property".equals(node.getNodeName())) {
                this.parsePropertyElement(beanName, pvs, (Element)node);
            }
        }

        return pvs;
    }

    protected void parseConstructorArgElement(String beanName, ConstructorArgumentValues cargs, Element ele) throws DOMException, ClassNotFoundException {
        Object val = this.getPropertyValue(ele, beanName);
        String indexAttr = ele.getAttribute("index");
        String typeAttr = ele.getAttribute("type");
        if(!"".equals(indexAttr)) {
            try {
                int ex = Integer.parseInt(indexAttr);
                if(ex < 0) {
                    throw new BeanDefinitionStoreException(this.resource, beanName, "\'index\' cannot be lower than 0");
                }

                if(!"".equals(typeAttr)) {
                    cargs.addIndexedArgumentValue(ex, val, typeAttr);
                } else {
                    cargs.addIndexedArgumentValue(ex, val);
                }
            } catch (NumberFormatException var8) {
                throw new BeanDefinitionStoreException(this.resource, beanName, "Attribute \'index\' of tag \'constructor-arg\' must be an integer");
            }
        } else if(!"".equals(typeAttr)) {
            cargs.addGenericArgumentValue(val, typeAttr);
        } else {
            cargs.addGenericArgumentValue(val);
        }

    }

    protected void parsePropertyElement(String beanName, MutablePropertyValues pvs, Element ele) throws DOMException {
        String propertyName = ele.getAttribute("name");
        if("".equals(propertyName)) {
            throw new BeanDefinitionStoreException(this.resource, beanName, "Tag \'property\' must have a \'name\' attribute");
        } else {
            Object val = this.getPropertyValue(ele, beanName);
            pvs.addPropertyValue(new PropertyValue(propertyName, val));
        }
    }

    protected Object getPropertyValue(Element ele, String beanName) {
        NodeList nl = ele.getChildNodes();
        Element valueRefOrCollectionElement = null;

        for(int i = 0; i < nl.getLength(); ++i) {
            if(nl.item(i) instanceof Element) {
                Element candidateEle = (Element)nl.item(i);
                if(!"description".equals(candidateEle.getTagName())) {
                    valueRefOrCollectionElement = candidateEle;
                }
            }
        }

        if(valueRefOrCollectionElement == null) {
            throw new BeanDefinitionStoreException(this.resource, beanName, "<property> element must have a subelement like \'value\' or \'ref\'");
        } else {
            return this.parsePropertySubelement(valueRefOrCollectionElement, beanName);
        }
    }

    protected Object parsePropertySubelement(Element ele, String beanName) {
        if(ele.getTagName().equals("bean")) {
            return this.parseBeanDefinition(ele);
        } else {
            String beanRef;
            if(ele.getTagName().equals("ref")) {
                beanRef = ele.getAttribute("bean");
                if("".equals(beanRef)) {
                    beanRef = ele.getAttribute("local");
                    if("".equals(beanRef)) {
                        throw new BeanDefinitionStoreException(this.resource, beanName, "Either \'bean\' or \'local\' is required for a reference");
                    }
                }

                return new RuntimeBeanReference(beanRef);
            } else if(ele.getTagName().equals("idref")) {
                beanRef = ele.getAttribute("bean");
                if("".equals(beanRef)) {
                    beanRef = ele.getAttribute("local");
                    if("".equals(beanRef)) {
                        throw new BeanDefinitionStoreException(this.resource, beanName, "Either \'bean\' or \'local\' is required for an idref");
                    }
                }

                return beanRef;
            } else if(ele.getTagName().equals("list")) {
                return this.getList(ele, beanName);
            } else if(ele.getTagName().equals("set")) {
                return this.getSet(ele, beanName);
            } else if(ele.getTagName().equals("map")) {
                return this.getMap(ele, beanName);
            } else if(ele.getTagName().equals("props")) {
                return this.getProps(ele, beanName);
            } else if(ele.getTagName().equals("value")) {
                return this.getTextValue(ele, beanName);
            } else if(ele.getTagName().equals("null")) {
                return null;
            } else {
                throw new BeanDefinitionStoreException(this.resource, beanName, "Unknown subelement of <property>: <" + ele.getTagName() + ">");
            }
        }
    }

    protected List getList(Element collectionEle, String beanName) {
        NodeList nl = collectionEle.getChildNodes();
        ManagedList list = new ManagedList(nl.getLength());

        for(int i = 0; i < nl.getLength(); ++i) {
            if(nl.item(i) instanceof Element) {
                Element ele = (Element)nl.item(i);
                list.add(this.parsePropertySubelement(ele, beanName));
            }
        }

        return list;
    }

    protected Set getSet(Element collectionEle, String beanName) {
        NodeList nl = collectionEle.getChildNodes();
        ManagedSet set = new ManagedSet(nl.getLength());

        for(int i = 0; i < nl.getLength(); ++i) {
            if(nl.item(i) instanceof Element) {
                Element ele = (Element)nl.item(i);
                set.add(this.parsePropertySubelement(ele, beanName));
            }
        }

        return set;
    }

    protected Map getMap(Element mapEle, String beanName) {
        List list = this.getChildElementsByTagName(mapEle, "entry");
        Object map = null;
        if(JdkVersion.getMajorJavaVersion() >= 1) {
            map = DefaultXmlBeanDefinitionParser.ManagedLinkedMapCreator.createManagedLinkedMap(list.size());
        } else {
            map = new ManagedMap(list.size());
        }

        for(int i = 0; i < list.size(); ++i) {
            Element entryEle = (Element)list.get(i);
            String key = entryEle.getAttribute("key");
            NodeList subEles = entryEle.getElementsByTagName("*");
            ((Map)map).put(key, this.parsePropertySubelement((Element)subEles.item(0), beanName));
        }

        return (Map)map;
    }

    protected List getChildElementsByTagName(Element mapEle, String elementName) {
        NodeList nl = mapEle.getChildNodes();
        ArrayList nodes = new ArrayList();

        for(int i = 0; i < nl.getLength(); ++i) {
            Node n = nl.item(i);
            if(n instanceof Element && elementName.equals(n.getNodeName())) {
                nodes.add(n);
            }
        }

        return nodes;
    }

    protected Properties getProps(Element propsEle, String beanName) {
        Properties props = new Properties();
        NodeList nl = propsEle.getElementsByTagName("prop");

        for(int i = 0; i < nl.getLength(); ++i) {
            Element propEle = (Element)nl.item(i);
            String key = propEle.getAttribute("key");
            String value = this.getTextValue(propEle, beanName).trim();
            props.setProperty(key, value);
        }

        return props;
    }

    protected String getTextValue(Element ele, String beanName) {
        NodeList nl = ele.getChildNodes();
        if(nl.item(0) == null) {
            return "";
        } else if(nl.getLength() == 1 && nl.item(0) instanceof Text) {
            Text t = (Text)nl.item(0);
            return t.getData();
        } else {
            throw new BeanDefinitionStoreException(this.resource, beanName, "Unexpected element or type mismatch: expected single node of " + nl.item(0).getClass() + " to be of type Text: " + "found " + ele, (Throwable)null);
        }
    }

    protected int getDependencyCheck(String att) {
        byte dependencyCheckCode = 0;
        if("all".equals(att)) {
            dependencyCheckCode = 3;
        } else if("simple".equals(att)) {
            dependencyCheckCode = 2;
        } else if("objects".equals(att)) {
            dependencyCheckCode = 1;
        }

        return dependencyCheckCode;
    }

    protected int getAutowireMode(String att) {
        byte autowire = 0;
        if("byName".equals(att)) {
            autowire = 1;
        } else if("byType".equals(att)) {
            autowire = 2;
        } else if("constructor".equals(att)) {
            autowire = 3;
        } else if("autodetect".equals(att)) {
            autowire = 4;
        }

        return autowire;
    }

    private abstract static class ManagedLinkedMapCreator {
        private ManagedLinkedMapCreator() {
        }

        private static Map createManagedLinkedMap(int capacity) {
            return new ManagedLinkedMap(capacity);
        }
    }
}
