package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface BeanPostProcessor {

    Object postProcessorBeforeInitialization(Object bean, String name) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String name) throws BeansException;
}
