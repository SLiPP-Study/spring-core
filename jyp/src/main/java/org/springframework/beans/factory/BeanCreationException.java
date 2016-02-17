package org.springframework.beans.factory;

import org.springframework.beans.FatalBeanException;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class BeanCreationException extends FatalBeanException {

    public BeanCreationException(String msg) {
        super(msg);
    }

    public BeanCreationException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public BeanCreationException(String resourceDescription, String beanName, String msg) {
        this(resourceDescription, beanName, msg, null);
    }

    public BeanCreationException(String resourceDescription, String beanName, String msg, Throwable ex) {
        super("Error creating bean with name '" + beanName + "' defined in " + resourceDescription + ": " + msg, ex);
    }
}
