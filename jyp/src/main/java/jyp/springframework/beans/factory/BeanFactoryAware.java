package jyp.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * @author jinyoung.park89
 * @since 2016-03-04
 */
public interface BeanFactoryAware {
    void setBeanFactory(BeanFactory var1) throws BeansException;
}
