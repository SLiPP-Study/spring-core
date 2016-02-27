package net.woniper.spring.core.beans.factory;

/**
 * Created by woniper on 2016. 2. 28..
 */
public interface BeanFactory {

    /**
     * BeanName, Bean Class Type으로 조회
     * @param beanName
     * @param type
     * @param <T>
     * @return
     */
    <T> T getBean(String beanName, Class<T> type);

    /**
     * Bean Class Type으로 조회
     * @param type
     * @param <T>
     * @return
     */
    <T> T getBean(Class<T> type);
}
