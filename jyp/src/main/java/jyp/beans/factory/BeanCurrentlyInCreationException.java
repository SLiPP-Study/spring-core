package jyp.beans.factory;

/**
 * @author jinyoung.park89
 * @since 2016. 4. 4.
 * @see {http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/beans/factory/BeanCurrentlyInCreationException.html}
 */
public class BeanCurrentlyInCreationException extends RuntimeException {

    public BeanCurrentlyInCreationException() {
    }

    public BeanCurrentlyInCreationException(String message) {
        super(message);
    }
}
