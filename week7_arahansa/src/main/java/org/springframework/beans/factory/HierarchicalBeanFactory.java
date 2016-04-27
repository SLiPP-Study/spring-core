package org.springframework.beans.factory;

/**
 * Created by arahansa on 2016-03-01.
 */
public interface HierarchicalBeanFactory extends BeanFactory {
    BeanFactory getParentBeanFactory();
}
