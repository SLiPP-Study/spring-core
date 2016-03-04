package jyp.springframework.beans;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class NotWritablePropertyException extends BeansException {

    /**
     * Creates new NotWritablePropertyException.
     */
    public NotWritablePropertyException(String propertyName, Class beanClass) {
        super("Property '" + propertyName + "' is not writable in bean class [" + beanClass.getName() + "]");
    }

    /**
     * Creates new NotWritablePropertyException with a root cause.
     */
    public NotWritablePropertyException(String propertyName, Class beanClass, Throwable ex) {
        super("Property '" + propertyName + "' is not writable in bean class [" + beanClass.getName() + "]", ex);
    }

}
