package jyp.springframework.beans.factory.config;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class RuntimeBeanReference {

    private final String beanName;

    /**
     * Create a new RuntimeBeanReference to the given bean name.
     * @param beanName name of the target bean
     */
    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
    }

    /**
     * Return the target bean name.
     */
    public String getBeanName() {
        return beanName;
    }

    public String toString() {
        return '<' + getBeanName() + '>';
    }

}
