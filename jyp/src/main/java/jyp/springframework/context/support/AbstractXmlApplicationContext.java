package jyp.springframework.context.support;

import java.io.IOException;

import org.springframework.context.ApplicationContextException;

import jyp.springframework.beans.BeansException;
import jyp.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import jyp.springframework.beans.factory.support.DefaultListableBeanFactory;
import jyp.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import jyp.springframework.context.ApplicationContext;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public abstract class AbstractXmlApplicationContext extends AbstractApplicationContext {

    private ConfigurableListableBeanFactory beanFactory;

    public AbstractXmlApplicationContext() {}

    public AbstractXmlApplicationContext(ApplicationContext parent) {
        super(parent);
    }

    protected void refreshBeanFactory() throws BeansException {
        try {
            DefaultListableBeanFactory beanFactory = createBeanFactory();
            XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
            beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));
            initBeanDefinitionReader(beanDefinitionReader);
            loadBeanDefinitions(beanDefinitionReader);
            this.beanFactory = beanFactory;
            if (logger.isInfoEnabled()) {
                logger.info("Bean factory for application context '" + getDisplayName() + "': " + beanFactory);
            }
        } catch (IOException ex) {
            throw new ApplicationContextException("I/O error parsing XML document for application context [" + getDisplayName() + "]", ex);
        }
    }

    public ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    protected void initBeanDefinitionReader(XmlBeanDefinitionReader beanDefinitionReader) {

    }

    protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException, IOException {
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            for (int i=0; i<configLocations.length; i++) {
                reader.loadBeanDefinitions(getResource(configLocations[i]));
            }
        }
    }

    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory(getParent());
    }

    protected abstract String[] getConfigLocations();
}
