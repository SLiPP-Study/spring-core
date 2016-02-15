package org.springframework.beans.factory.config;

import org.springframework.beans.factory.ListableBeanFactory;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, ConfigurableBeanFactory, AutowireCapableBeanFactory {

    void preInstantiateSingletons();
}
