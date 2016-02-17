package org.springframework.beans;

import org.springframework.core.ErrorCoded;

import java.beans.PropertyChangeEvent;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public abstract class PropertyAccessException extends BeansException implements ErrorCoded {

    private PropertyChangeEvent propertyChangeEvent;

    public PropertyAccessException(String msg, PropertyChangeEvent propertyChangeEvent) {
        super(msg);
        this.propertyChangeEvent = propertyChangeEvent;
    }

    public PropertyAccessException(String msg, PropertyChangeEvent propertyChangeEvent, Throwable ex) {
        super(msg, ex);
        this.propertyChangeEvent = propertyChangeEvent;
    }

    /**
     * Return the PropertyChangeEvent that resulted in the problem.
     */
    public PropertyChangeEvent getPropertyChangeEvent() {
        return propertyChangeEvent;
    }

}
