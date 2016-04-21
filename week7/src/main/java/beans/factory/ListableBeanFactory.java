package beans.factory;

import beans.factory.BeanFactory;

public interface ListableBeanFactory extends BeanFactory {
    String[] getBeanDefinitionNames();

    int getBeanDefinitionCount();
}