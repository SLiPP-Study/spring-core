package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 16.
 */
public class NoSuchBeanDefinitionException extends BeansException {

    private String beanName;

    private Class beanType;

    public NoSuchBeanDefinitionException(String name, String message) {
        super("No bean named '" + name + "' is defined: " + message);
        this.beanName = beanName;
    }

    public NoSuchBeanDefinitionException(Class type, String message) {
        super("No unique bean of type [" + type.getName() + "] is defined: " + message);
        this.beanType = type;
    }

    public String getBeanName() {
        return beanName;
    }

    public Class getBeanType() {
        return beanType;
    }
}
