package jyp;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String id, BeanDefinition beanDefinition);
}
