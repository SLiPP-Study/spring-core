package beans.factory;

/**
 * @author jinyoung.park89
 * @since 2016. 4. 24.
 */
public interface DisposableBean {
    void destroy() throws Exception;
}
