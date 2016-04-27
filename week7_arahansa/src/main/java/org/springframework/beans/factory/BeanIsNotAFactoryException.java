package org.springframework.beans.factory;

/**
 * Created by arahansa on 2016-03-01.
 */
public class BeanIsNotAFactoryException extends BeanNotOfRequiredTypeException{
    public BeanIsNotAFactoryException(String name, Object actualInstance) {
        super(name, FactoryBean.class, actualInstance);
    }
}
