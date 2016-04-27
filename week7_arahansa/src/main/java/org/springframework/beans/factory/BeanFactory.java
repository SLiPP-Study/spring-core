package org.springframework.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.BeansException;

/**
 * Created by arahansa on 2016-02-28.
 */
public interface BeanFactory {
    Object getBean(String name) throws BeansException;
    Object getBean(String name, Class requiredType) throws BeansException;

    /**
     * Does this bean factory contain a bean with the given name?
     * <p>Will ask the parent factory if the bean cannot be found in this factory instance.
     * @param name name of the bean to query
     * @return whether a bean with the given name is defined
     */
    boolean containsBean(String name);
}
