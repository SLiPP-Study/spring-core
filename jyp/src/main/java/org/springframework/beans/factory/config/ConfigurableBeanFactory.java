package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;

import java.beans.PropertyEditor;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory {

    void setParentBeanFactory(BeanFactory parentBeanFactory);

    void registerCustomEditor(Class requiredType, PropertyEditor propertyEditor);

    void ignoreDependencyType(Class type);

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void registerAlias(String beanName, String alias) throws BeansException;

    void registerSingleton(String beanName, Object singletonObject) throws BeansException;

    void destroySingletons();
}
