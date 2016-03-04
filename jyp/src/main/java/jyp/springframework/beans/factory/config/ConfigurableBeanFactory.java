package jyp.springframework.beans.factory.config;

import java.beans.PropertyEditor;

import jyp.springframework.beans.BeansException;
import jyp.springframework.beans.factory.BeanFactory;
import jyp.springframework.beans.factory.HierarchicalBeanFactory;

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
