package jyp.springframework.beans.factory;

import jyp.springframework.beans.BeansException;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class BeanNotOfRequiredTypeException extends BeansException {

    /** The name of the instance that was of the wrong type */
    private String beanName;

    /** The required type */
    private Class requiredType;

    /** The offending type */
    private Object actualInstance;


    public BeanNotOfRequiredTypeException(String beanName, Class requiredType, Object actualInstance) {
        super("Bean named '" + beanName + "' must be of type [" + requiredType.getName() +
                "], but was actually of type [" + actualInstance.getClass().getName() + "]");
        this.beanName = beanName;
        this.requiredType = requiredType;
        this.actualInstance = actualInstance;
    }


    /**
     * Return the name of the instance that was of the wrong type.
     */
    public String getBeanName() {
        return this.beanName;
    }

    /**
     * Return the expected type for the bean.
     */
    public Class<?> getRequiredType() {
        return this.requiredType;
    }

    /**
     * Return the actual type of the instance found.
     */
    public Class getActualType() {
        return this.actualInstance.getClass();
    }

    public Object getActualInstance() {
        return this.actualInstance;
    }

}
