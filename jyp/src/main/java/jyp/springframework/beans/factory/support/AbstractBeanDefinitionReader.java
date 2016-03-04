package jyp.springframework.beans.factory.support;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public abstract class AbstractBeanDefinitionReader {
    private BeanDefinitionRegistry beanFactory;

    private ClassLoader beanClassLoader = Thread.currentThread().getContextClassLoader();

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanDefinitionRegistry getBeanFactory() {
        return this.beanFactory;
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }
}
