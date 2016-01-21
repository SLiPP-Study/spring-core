package net.slipp;

public interface BeanFactory extends BeanDefinitionRegistry {
    Object getBean(String name);
}
