package org.springframework.beans.factory;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class BeanIsNotAFactoryException extends BeanNotOfRequiredTypeException {
    public BeanIsNotAFactoryException(String name, Object actualInstance) {
        super(name, FactoryBean.class, actualInstance);
    }
}
