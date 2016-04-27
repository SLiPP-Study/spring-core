package org.springframework.beans.factory.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.BeanFactory;

/**
 * Created by arahansa on 2016-03-01.
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory{

    void setParentBeanFactory(BeanFactory parentBeanFactory);
}
