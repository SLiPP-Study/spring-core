package net.woniper.spring.core.beans.supports;

/**
 * Created by woniper on 2016. 2. 28..
 */
public interface BeanObjectRegistry {

    /**
     * Bean Object 등록
     * Spring은 getBean 호출 시 BeanDefinition을 이용해 Bean을 Instance화 시키지만,
     * Bean Instance 기능 책임을 나누었음.
     * @param beanName
     * @param beanObject
     */
    void registerBeanObject(String beanName, Object beanObject);
}
