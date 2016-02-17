package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

import java.util.Map;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface ListableBeanFactory extends BeanFactory {

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();

    String[] getBeanDefinitionNames(Class Type);

    boolean containsBeanDefinition(String name);

    Map getBeansOfType(Class type, boolean includePropertyTypes, boolean includeFactoryBeans) throws BeansException;
}
