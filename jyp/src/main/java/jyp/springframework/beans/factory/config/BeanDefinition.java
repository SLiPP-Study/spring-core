package jyp.springframework.beans.factory.config;

import jyp.springframework.beans.MutablePropertyValues;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public interface BeanDefinition {

    MutablePropertyValues getPropertyValues();

    ConstructorArgumentValues getConstructorArgumentValues();

    String getResourceDescription();
}
