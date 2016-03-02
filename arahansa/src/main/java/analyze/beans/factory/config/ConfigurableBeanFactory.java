package analyze.beans.factory.config;

import analyze.beans.factory.BeanFactory;
import analyze.beans.factory.HierarchicalBeanFactory;

/**
 * Created by arahansa on 2016-03-01.
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory{

    void setParentBeanFactory(BeanFactory parentBeanFactory);
}
