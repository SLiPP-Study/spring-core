package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public interface BeanDefinitionRegistry {

    /**
     * Return the number of beans defined in the registry.
     * @return the number of beans defined in the registry
     */
    int getBeanDefinitionCount();

    /**
     * Return the names of all beans defined in this registry.
     * @return the names of all beans defined in this registry,
     * or an empty array if none defined
     */
    String[] getBeanDefinitionNames();

    /**
     * Check if this registry contains a bean definition with the given name.
     * @param name the name of the bean to look for
     * @return if this bean factory contains a bean definition with the given name
     */
    boolean containsBeanDefinition(String name);

    /**
     * Return the BeanDefinition for the given bean name.
     * @param name name of the bean to find a definition for
     * @return the BeanDefinition for the given name (never null)
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     * if the bean definition cannot be resolved
     * @throws BeansException in case of errors
     */
    BeanDefinition getBeanDefinition(String name) throws BeansException;

    /**
     * Register a new bean definition with this registry.
     * Must support RootBeanDefinition and ChildBeanDefinition.
     * @param name the name of the bean instance to register
     * @param beanDefinition definition of the bean instance to register
     * @throws BeansException if the bean definition is invalid
     * @see RootBeanDefinition
     * @see ChildBeanDefinition
     */
    void registerBeanDefinition(String name, BeanDefinition beanDefinition)
            throws BeansException;

    /**
     * Return the aliases for the given bean name, if defined.
     * <p>Will ask the parent factory if the bean cannot be found in this factory instance.
     * @param name the bean name to check for aliases
     * @return the aliases, or an empty array if none
     * @throws NoSuchBeanDefinitionException if there's no such bean definition
     */
    String[] getAliases(String name) throws NoSuchBeanDefinitionException;

    /**
     * Given a bean name, create an alias. We typically use this method to
     * support names that are illegal within XML ids (used for bean names).
     * @param name the name of the bean
     * @param alias alias that will behave the same as the bean name
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     * if there is no bean with the given name
     * @throws BeansException if the alias is already in use
     */
    void registerAlias(String name, String alias) throws BeansException;

}
