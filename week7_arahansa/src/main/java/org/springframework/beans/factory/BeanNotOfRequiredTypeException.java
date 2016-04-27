package org.springframework.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.BeansException;

/**
 * Created by arahansa on 2016-03-01.
 */
public class BeanNotOfRequiredTypeException  extends BeansException {


    /** The name of the instance that was of the wrong type */
    private String name;

    /** The required type */
    private Class requiredType;

    /** The offending instance */
    private Object actualInstance;

    /**
     * Creates new <code>BeanNotOfRequiredTypeException</code>.
     * @param name name of the bean requested
     * @param requiredType required type
     * @param actualInstance the instance actually returned, whose
     * class did not match the expected type.
     */
    public BeanNotOfRequiredTypeException(String name, Class requiredType, Object actualInstance) {
        super("Bean named '" + name + "' must be of type [" + requiredType.getName() +
                "], but was actually of type [" + actualInstance.getClass().getName() + "]", null);
        this.name = name;
        this.requiredType = requiredType;
        this.actualInstance = actualInstance;
    }

    public String getBeanName() {
        return name;
    }

    public Class getRequiredType() {
        return requiredType;
    }

    public Class getActualType() {
        return actualInstance.getClass();
    }

    public Object getActualInstance() {
        return actualInstance;
    }

}
