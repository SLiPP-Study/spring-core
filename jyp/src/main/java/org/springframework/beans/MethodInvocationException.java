package org.springframework.beans;

import java.beans.PropertyChangeEvent;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class MethodInvocationException extends PropertyAccessException {

    /**
     * Constructor to use when an exception results from a PropertyChangeEvent.
     * @param ex Throwable raised by invoked method
     * @param propertyChangeEvent PropertyChangeEvent that resulted in an exception
     */
    public MethodInvocationException(Throwable ex, PropertyChangeEvent propertyChangeEvent) {
        super("Property '" + propertyChangeEvent.getPropertyName() + "' threw exception", propertyChangeEvent, ex);
    }

    public String getErrorCode() {
        return "methodInvocation";
    }

}
