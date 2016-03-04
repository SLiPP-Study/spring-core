package jyp.springframework.beans.factory.config;

import jyp.springframework.beans.factory.ListableBeanFactory;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, ConfigurableBeanFactory, AutowireCapableBeanFactory {

    void preInstantiateSingletons();
}
