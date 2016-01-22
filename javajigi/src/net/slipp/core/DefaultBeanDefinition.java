package net.slipp.core;

import net.slipp.BeanDefinition;

import java.util.Optional;

public class DefaultBeanDefinition implements BeanDefinition {
    private String beanClassName;

    public DefaultBeanDefinition(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }

    @Override
    public Optional<Class> getBeanClass() {
        try {
            return Optional.of(Class.forName(getBeanClassName()));
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    }
}
