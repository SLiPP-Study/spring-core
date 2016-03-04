package jyp.springframework.beans.factory;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public interface DisposableBean {
    void destroy() throws Exception;
}
