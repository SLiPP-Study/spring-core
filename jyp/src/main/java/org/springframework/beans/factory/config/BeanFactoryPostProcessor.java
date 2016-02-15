package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface BeanFactoryPostProcessor {

    void postProcessBeanFactory(ConfigurableBeanFactory beanFactory) throws BeansException;
}
