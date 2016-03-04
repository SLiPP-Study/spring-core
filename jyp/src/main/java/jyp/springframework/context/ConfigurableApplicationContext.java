package jyp.springframework.context;

import org.springframework.context.ApplicationContextException;

import jyp.springframework.beans.BeansException;
import jyp.springframework.beans.factory.config.BeanFactoryPostProcessor;
import jyp.springframework.beans.factory.config.ConfigurableBeanFactory;

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
