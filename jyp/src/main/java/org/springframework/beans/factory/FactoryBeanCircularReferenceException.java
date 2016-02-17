package org.springframework.beans.factory;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class FactoryBeanCircularReferenceException extends BeanDefinitionStoreException {

    public FactoryBeanCircularReferenceException(String msg) {
        super(msg);
    }
}
