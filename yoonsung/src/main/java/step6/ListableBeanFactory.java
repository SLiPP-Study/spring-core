package step6;

public interface ListableBeanFactory extends BeanFactory {
    String[] getBeanDefinitionNames();
    int getBeanDefinitionCount();
}