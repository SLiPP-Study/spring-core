package net.slipp.core;

import net.slipp.ApplicationContext;
import net.slipp.BeanDefinition;
import net.slipp.BeanDefinitionReader;
import net.slipp.BeanFactory;

public class DefaultApplicationContext implements ApplicationContext {
    private BeanFactory beanFactory;
    private BeanDefinitionReader beanDefinitionReader;

    public DefaultApplicationContext(String location) {
        beanFactory = new DefaultBeanFactory();
        beanDefinitionReader = new DefaultBeanDefinitionReader(this);
        beanDefinitionReader.loadBeanDefinitions(location);
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public Object getBean(String name) {
        return beanFactory.getBean(name);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }
}
