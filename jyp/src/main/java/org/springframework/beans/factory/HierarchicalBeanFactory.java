package org.springframework.beans.factory;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface HierarchicalBeanFactory {

    BeanFactory getParentBeanFactory();
}
