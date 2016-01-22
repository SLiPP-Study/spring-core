package spring.beans.factory;

import spring.beans.factory.support.BeanDefinitionRegistry;

public interface BeanFactory extends BeanDefinitionRegistry {
    Object getBean(String name);
}
