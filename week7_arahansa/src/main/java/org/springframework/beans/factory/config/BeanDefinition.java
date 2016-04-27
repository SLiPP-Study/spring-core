package org.springframework.beans.factory.config;

import org.springframework.beans.MutablePropertyValues;

/**
 * Created by arahansa on 2016-03-01.
 */
public interface BeanDefinition {

    /**
     * Return the PropertyValues to be applied to a new instance of the bean.
     */
    MutablePropertyValues getPropertyValues();

    /**
     * Return the constructor argument values for this bean.
     */
    ConstructorArgumentValues getConstructorArgumentValues();

    /**
     * Return a description of the resource that this bean definition
     * came from (for the purpose of showing context in case of errors).
     */
    String getResourceDescription();
}
