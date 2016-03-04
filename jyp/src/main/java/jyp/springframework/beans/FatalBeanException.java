package jyp.springframework.beans;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class FatalBeanException extends BeansException {

    public FatalBeanException(String msg) {
        super(msg);
    }

    public FatalBeanException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
