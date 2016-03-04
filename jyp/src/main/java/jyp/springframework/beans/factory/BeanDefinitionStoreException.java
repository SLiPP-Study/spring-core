package jyp.springframework.beans.factory;

import jyp.springframework.beans.FatalBeanException;
import jyp.springframework.core.io.Resource;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class BeanDefinitionStoreException extends FatalBeanException {

    public BeanDefinitionStoreException(String msg) {
        super(msg);
    }

    public BeanDefinitionStoreException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public BeanDefinitionStoreException(Resource documentLocation, String beanName, String msg) {
        this(documentLocation.getDescription(), beanName, msg, null);
    }

    public BeanDefinitionStoreException(Resource documentLocation, String beanName, String msg, Throwable ex) {
        this(documentLocation.getDescription(), beanName, msg, ex);
    }

    public BeanDefinitionStoreException(String resourceDescription, String beanName, String msg) {
        this(resourceDescription, beanName, msg, null);
    }

    public BeanDefinitionStoreException(String resourceDescription, String beanName, String msg, Throwable ex) {
        super("Error registering bean with name '" + beanName + "' defined in " + resourceDescription + ": " + msg, ex);
    }
}
