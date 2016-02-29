package step3;

import java.io.FileNotFoundException;

/**
 * @author jinyoung.park89
 * @since 2016-02-29
 */
public interface BeanFactory {

    <T> T getBean(String key, Class<T> clazz);

    Object getBean(String key);

    void loadBeanDefinitions(String location) throws FileNotFoundException;

    BeanDefinition createBeanDefinition(String className, PropertyValues propertyValues);

    void registerBeanDefinition(String id, BeanDefinition beanDefinition);
}
