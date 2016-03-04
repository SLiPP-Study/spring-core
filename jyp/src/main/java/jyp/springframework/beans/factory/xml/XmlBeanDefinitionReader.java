package jyp.springframework.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.xml.BeansDtdResolver;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import jyp.springframework.beans.BeanUtils;
import jyp.springframework.beans.BeansException;
import jyp.springframework.beans.factory.BeanDefinitionStoreException;
import jyp.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import jyp.springframework.beans.factory.support.BeanDefinitionRegistry;
import jyp.springframework.core.io.Resource;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    protected final Log logger = LogFactory.getLog(getClass());

    private boolean validating = true;

    private EntityResolver entityResolver;

    private Class parserClass = DefaultXmlBeanDefinitionParser.class;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanFactory) {
        super(beanFactory);
    }

    public void setEntityResolver(EntityResolver entityResolver) {
        this.entityResolver = entityResolver;
    }

    public void setParserClass(Class parserClass) {
        if (this.parserClass == null || !XmlBeanDefinitionParser.class.isAssignableFrom(parserClass)) {
            throw new IllegalArgumentException("parserClass must be a XmlBeanDefinitionParser");
        }
        this.parserClass = parserClass;
    }

    public void loadBeanDefinitions(Resource resource) throws BeansException {
        if (resource == null) {
            throw new BeanDefinitionStoreException("Resource cannot be null: expected an xml file");
        }

        InputStream is = null;

        try {
            logger.info("Loading XML bean definitions from "+ resource + "");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            logger.debug("Using JAXP implementation [" + factory + "]");
            factory.setValidating(this.validating);
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            docBuilder.setErrorHandler(new BeansErrorHandler());
            docBuilder.setEntityResolver(this.entityResolver != null ? this.entityResolver : new BeansDtdResolver());
            is = resource.getInputStream();
            Document doc = docBuilder.parse(is);
            registerBeanDefinitions(doc, resource);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {

                }
            }
        }
    }

    private void registerBeanDefinitions(Document doc, Resource resource) {
        XmlBeanDefinitionParser parser = (XmlBeanDefinitionParser) BeanUtils.instantiateClass(this.parserClass);
        parser.registerBeanDefinitions(getBeanFactory(), getBeanClassLoader(), doc, resource);
    }

    private static class BeansErrorHandler implements ErrorHandler {
        private final static Log logger = LogFactory.getLog(XmlBeanFactory.class);

        public void error(SAXParseException ex) throws SAXException {
            throw ex;
        }

        public void fatalError(SAXParseException ex) throws SAXException {
            throw ex;
        }

        public void warning(SAXParseException ex) throws SAXException {
            logger.warn("");
        }
    }
}
