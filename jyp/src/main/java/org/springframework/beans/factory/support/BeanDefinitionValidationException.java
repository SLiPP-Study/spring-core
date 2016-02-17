package org.springframework.beans.factory.support;

import org.springframework.beans.FatalBeanException;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class BeanDefinitionValidationException extends FatalBeanException {
    public BeanDefinitionValidationException(String msg) {
        super(msg);
    }

    public BeanDefinitionValidationException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
