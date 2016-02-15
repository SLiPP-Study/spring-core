package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    Object getBean(String name, Class requiredType) throws BeansException;

    boolean containsBean(String name);

    boolean isSingleton(String name) throws NoSuchBeanDefinitionException;

    String[] getAliases(String name) throws NoSuchBeanDefinitionException;
}
