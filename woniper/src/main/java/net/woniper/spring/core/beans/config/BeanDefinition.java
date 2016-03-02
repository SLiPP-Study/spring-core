package net.woniper.spring.core.beans.config;

/**
 * Created by woniper on 2016. 2. 28..
 */
public class BeanDefinition {
    private final String beanName;
    private final Class<?> classType;

    public BeanDefinition(String beanName, Class<?> classType) {
        this.beanName = beanName;
        this.classType = classType;
    }

    public String getBeanName() {
        return beanName;
    }

    public Class<?> getClassType() {
        return classType;
    }
}
