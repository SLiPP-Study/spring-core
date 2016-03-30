package jyp;

public interface ListableBeanFactory extends BeanFactory {
    String[] getBeanDefinitionNames();

    int getBeanDefinitionCount();
}