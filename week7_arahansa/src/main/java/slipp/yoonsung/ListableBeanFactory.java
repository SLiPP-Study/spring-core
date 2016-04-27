package slipp.yoonsung;

public interface ListableBeanFactory extends BeanFactory {
    String[] getBeanDefinitionNames();
    int getBeanDefinitionCount();
}