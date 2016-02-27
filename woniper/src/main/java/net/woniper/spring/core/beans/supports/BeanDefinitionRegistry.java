package net.woniper.spring.core.beans.supports;

import net.woniper.spring.core.beans.config.BeanDefinition;

/**
 * Created by woniper on 2016. 2. 28..
 */
public interface BeanDefinitionRegistry {
    /**
     * BeanDefinition 등록
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
