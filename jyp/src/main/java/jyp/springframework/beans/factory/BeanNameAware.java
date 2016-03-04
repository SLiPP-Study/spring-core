package jyp.springframework.beans.factory;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public interface BeanNameAware {

    /**
     * Set the name of the bean in the bean factory that created this bean.
     * <p>Invoked after population of normal bean properties but before an init
     * callback like InitializingBean's afterPropertiesSet or a custom init-method.
     * @param name the name of the bean in the factory
     */
    void setBeanName(String name);

}
