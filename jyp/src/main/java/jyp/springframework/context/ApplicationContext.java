package jyp.springframework.context;

import jyp.springframework.beans.factory.HierarchicalBeanFactory;
import jyp.springframework.beans.factory.ListableBeanFactory;
import jyp.springframework.core.io.ResourceLoader;

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
