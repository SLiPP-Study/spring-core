package org.springframework.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    void setParent(ApplicationContext parent);

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor beanFactoryPostProcessor);

    void refresh() throws BeansException;

    ConfigurableBeanFactory getBeanFactory();

    void close() throws ApplicationContextException;
}
