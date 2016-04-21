package beans.factory;

import java.lang.reflect.InvocationTargetException;

public interface BeanFactory {
    Object getBean(String name) throws SecurityException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException;

    <T> T getBean(String name, Class<T> clazz) throws NoSuchMethodException, SecurityException,
            IllegalArgumentException, InvocationTargetException;

    void clear();
}
