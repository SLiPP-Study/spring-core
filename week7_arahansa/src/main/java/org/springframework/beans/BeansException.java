package org.springframework.beans;

import org.springframework.core.NestedRuntimeException;

/**
 * Created by arahansa on 2016-03-01.
 */
public class BeansException extends NestedRuntimeException{
    /**
     * Constructs a <code>BeansException</code> with the specified message.
     * @param msg the detail message
     */
    public BeansException(String msg) {
        super(msg);
    }

    /**
     * Constructs a <code>BeansException</code> with the specified message
     * and root cause.
     * @param msg the detail message
     * @param ex the root cause
     */
    public BeansException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
