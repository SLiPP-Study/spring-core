package step1.core;

public interface ListableBeanFactory extends BeanFactory {
    String[] getBeanDefinitionNames();
    int getBeanDefinitionCount();
}