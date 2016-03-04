package jyp.springframework.beans.factory;

/**
 * @author jinyoung.park89
 * @since 2016-03-01
 */
public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
