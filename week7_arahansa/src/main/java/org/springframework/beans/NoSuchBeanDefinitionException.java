package org.springframework.beans;

/**
 * Created by arahansa on 2016-03-01.
 */
public class NoSuchBeanDefinitionException extends BeansException {
    /** Name of the missing bean */
    private String beanName;

    /** Required bean type */
    private Class beanType;
    /**
     * Create new <code>NoSuchBeanDefinitionException</code>.
     * @param name the name of the missing bean
     * @param message further, detailed message describing the problem
     */
    public NoSuchBeanDefinitionException(String name, String message) {
        super("No bean named '" + name + "' is defined: " + message);
        this.beanName = name;
    }

    /**
     * Create new <code>NoSuchBeanDefinitionException</code>.
     * @param type required type of bean
     * @param message further, detailed message describing the problem
     */
    public NoSuchBeanDefinitionException(Class type, String message) {
        super("No unique bean of type [" + type.getName() + "] is defined: " + message);
        this.beanType = type;
    }

    /**
     * Return the name of the missing bean,
     * if it was a lookup by name that failed.
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * Return the required type of bean,
     * if it was a lookup by type that failed.
     */
    public Class getBeanType() {
        return beanType;
    }
}
