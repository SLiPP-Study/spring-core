package net.slipp.core;

import net.slipp.BeanDefinition;

public class DefaultBeanDefinition implements BeanDefinition {
    private String beanClassName;

    public DefaultBeanDefinition(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }
}
