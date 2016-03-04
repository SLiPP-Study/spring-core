package jyp.springframework.beans;

import jyp.springframework.core.NestedRuntimeException;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 16.
 */
public abstract class BeansException extends NestedRuntimeException {
    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
