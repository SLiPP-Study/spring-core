package jyp.springframework.beans.factory;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public interface FactoryBean {
    Object getObject() throws Exception;

    Class getObjectType();

    boolean isSingleton();
}
