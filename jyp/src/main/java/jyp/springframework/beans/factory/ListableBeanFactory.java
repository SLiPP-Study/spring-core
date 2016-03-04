package jyp.springframework.beans.factory;

import java.util.Map;

import jyp.springframework.beans.BeansException;

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
