package jyp.springframework.beans.factory.config;

import jyp.springframework.beans.BeansException;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String name) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String name) throws BeansException;
}
