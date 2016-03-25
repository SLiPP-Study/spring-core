package springbook.user.domain;

import jyp.springframework.beans.factory.*;
import jyp.springframework.beans.factory.config.BeanPostProcessor;
import jyp.springframework.context.ResourceLoaderAware;
import jyp.springframework.core.io.ResourceLoader;
import org.springframework.beans.BeansException;

/**
 * @author jinyoung.park89
 * @since 2016. 3. 16.
 */
public class Age implements InitializingBean, DisposableBean, BeanNameAware, BeanFactoryAware,
        ResourceLoaderAware, BeanPostProcessor {
    private int realAge;

    private int fakeAge;

    public Age() {
    }

    public Age(int realAge, int fakeAge) {
        this.realAge = realAge;
        this.fakeAge = fakeAge;
    }

    @Override
    public void setBeanFactory(BeanFactory var1) throws BeansException {

    }

    @Override
    public void setBeanName(String name) {

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String name)
            throws jyp.springframework.beans.BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String name)
            throws jyp.springframework.beans.BeansException {
        return bean;
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setResourceLoader(ResourceLoader var1) {

    }
}
