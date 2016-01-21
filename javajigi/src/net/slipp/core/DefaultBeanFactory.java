package net.slipp.core;

import net.slipp.BeanDefinition;
import net.slipp.BeanFactory;

import java.util.HashMap;
import java.util.Map;

public class DefaultBeanFactory implements BeanFactory {
    private Map<String, BeanDefinition> beanDefinitions = new HashMap<String, BeanDefinition>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitions.put(beanName, beanDefinition);
    }

    @Override
    public Object getBean(String name) {
        // create bean. use BeanDefinition
        return null;
    }
}
