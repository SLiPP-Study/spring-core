package org.springframework.context;

import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.io.ResourceLoader;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, MessageSource, ResourceLoader {

    ApplicationContext getParent();

    String getDisplayName();

    long getStartupDate();

    void publishEvent(ApplicationEvent event);
}
