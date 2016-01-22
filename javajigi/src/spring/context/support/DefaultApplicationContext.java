package spring.context.support;

import spring.beans.factory.ConfigurableListableBeanFactory;
import spring.beans.factory.config.BeanDefinition;
import spring.beans.factory.support.BeanDefinitionReader;
import spring.beans.factory.support.DefaultBeanDefinitionReader;
import spring.beans.factory.support.DefaultBeanFactory;
import spring.context.ApplicationContext;

public class DefaultApplicationContext implements ApplicationContext {
    private ConfigurableListableBeanFactory beanFactory;
    private BeanDefinitionReader beanDefinitionReader;

    public DefaultApplicationContext(String location) {
        beanFactory = new DefaultBeanFactory();
        beanDefinitionReader = new DefaultBeanDefinitionReader(this);
        beanDefinitionReader.loadBeanDefinitions(location);
        refresh();
    }

    private void refresh() {
        finishBeanFactoryInitializer(beanFactory);
    }

    private void finishBeanFactoryInitializer(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.preInstantiateSinglonetons();
    }

    public ConfigurableListableBeanFactory getBeanFactory() {
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
