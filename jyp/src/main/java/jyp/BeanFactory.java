package jyp;

public interface BeanFactory {
    Object getBean(String name);

    <T> T getBean(String name, Class<T> clazz);
}
