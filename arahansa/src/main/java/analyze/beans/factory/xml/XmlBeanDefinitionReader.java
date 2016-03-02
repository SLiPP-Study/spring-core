/*
 * Copyright 2002-2004 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package analyze.beans.factory.xml;

import analyze.beans.BeanUtils;
import analyze.beans.BeansException;
import analyze.beans.factory.BeanDefinitionStoreException;
import analyze.beans.factory.support.AbstractBeanDefinitionReader;
import analyze.beans.factory.support.BeanDefinitionRegistry;
import analyze.core.io.Resource;
import lombok.extern.slf4j.Slf4j;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Bean definition reader for Spring's default XML bean definition format.
 * Typically applied to a DefaultListableBeanFactory.
 *
 * <p>The structure, element and attribute names of the required XML document
 * are hard-coded in this class. (Of course a transform could be run if necessary
 * to produce this format). "beans" doesn't need to be the root element of the XML
 * document: This class will parse all bean definition elements in the XML file.
 *
 * <p>This class registers each bean definition with the given bean factory superclass,
 * and relies on the latter's implementation of the BeanDefinitionRegistry interface.
 * It supports singletons, prototypes, and references to either of these kinds of bean.

 * @author Juergen Hoeller
 * @since 26.11.2003
 * @see #setParserClass
 * @version $Id: XmlBeanDefinitionReader.java,v 1.8 2004/03/18 02:46:12 trisberg Exp $
 */
@Slf4j
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

	private boolean validating = true;

	private EntityResolver entityResolver;

	private Class parserClass = DefaultXmlBeanDefinitionParser.class;


	/**
	 * Create new XmlBeanDefinitionReader for the given bean factory.
	 */
	public XmlBeanDefinitionReader(BeanDefinitionRegistry beanFactory) {
		super(beanFactory);
	}

	/**
	 * Set if the XML parser should validate the document and thus enforce a DTD.
	 */
	public void setValidating(boolean validating) {
		this.validating = validating;
	}

	/**
	 * Set a SAX entity resolver to be used for parsing. By default, BeansDtdResolver
	 * will be used. Can be overridden for custom entity resolution, e.g. relative
	 * to some specific base path.
	 * @see org.springframework.beans.factory.xml.BeansDtdResolver
	 */
	public void setEntityResolver(EntityResolver entityResolver) {
		this.entityResolver = entityResolver;
	}

	/**
	 * Set the XmlBeanDefinitionParser implementation to use.
	 * Default is DefaultXmlBeanDefinitionParser.
	 * @see XmlBeanDefinitionParser
	 * @see DefaultXmlBeanDefinitionParser
	 */
	public void setParserClass(Class parserClass) {
		if (this.parserClass == null || !XmlBeanDefinitionParser.class.isAssignableFrom(parserClass)) {
			throw new IllegalArgumentException("parserClass must be a XmlBeanDefinitionParser");
		}
		this.parserClass = parserClass;
	}

	/**
	 * Load bean definitions from the specified XML file.
	 * @param resource the resource descriptor for the XML file
	 * @throws BeansException in case of loading or parsing errors
	 */
	public void loadBeanDefinitions(Resource resource) throws BeansException {
		if (resource == null) {
			throw new BeanDefinitionStoreException("Resource cannot be null: expected an XML file");
		}
		InputStream is = null;
		try {
			log.info("Loading XML bean definitions from " + resource + "");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			log.debug("Using JAXP implementation [" + factory + "]");
			factory.setValidating(this.validating);
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			docBuilder.setErrorHandler(new BeansErrorHandler());
			docBuilder.setEntityResolver(this.entityResolver != null ? this.entityResolver : new BeansDtdResolver());
			is = resource.getInputStream();
			Document doc = docBuilder.parse(is);
			registerBeanDefinitions(doc, resource);
		}
		catch (ParserConfigurationException ex) {
			throw new BeanDefinitionStoreException("Parser configuration exception parsing XML from " + resource, ex);
		}
		catch (SAXParseException ex) {
			throw new BeanDefinitionStoreException("Line " + ex.getLineNumber() + " in XML document from " + resource + " is invalid", ex);
		}
		catch (SAXException ex) {
			throw new BeanDefinitionStoreException("XML document from " + resource + " is invalid", ex);
		}
		catch (IOException ex) {
			throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource, ex);
		}
		finally {
			if (is != null) {
				try {
					is.close();
				}
				catch (IOException ex) {
					log.warn("Could not close InputStream", ex);
				}
			}
		}
	}

	/**
	 * Register the bean definitions contained in the given DOM document.
	 * All calls go through this.
	 * @param doc the DOM document
	 * @throws BeansException in case of parsing errors
	 */
	public void registerBeanDefinitions(Document doc, Resource resource) throws BeansException {
		XmlBeanDefinitionParser parser = (XmlBeanDefinitionParser) BeanUtils.instantiateClass(this.parserClass);
		parser.registerBeanDefinitions(getBeanFactory(), getBeanClassLoader(), doc, resource);
	}


	/**
	 * Private implementation of SAX ErrorHandler used when validating XML.
	 */
	@Slf4j
	private static class BeansErrorHandler implements ErrorHandler {

		/**
		 * We can't use the enclosing class' log as it's protected and inherited.
		 */

		public void error(SAXParseException ex) throws SAXException {
			throw ex;
		}

		public void fatalError(SAXParseException ex) throws SAXException {
			throw ex;
		}

		public void warning(SAXParseException ex) throws SAXException {
			log.warn("Ignored XML validation warning: " + ex);
		}
	}

}
