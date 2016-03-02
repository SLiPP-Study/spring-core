package net.woniper.spring.core.beans.factory;

import net.woniper.spring.core.beans.config.BeanDefinition;
import net.woniper.spring.core.beans.supports.BeanDefinitionReader;
import net.woniper.spring.core.beans.supports.BeanDefinitionRegistry;
import net.woniper.spring.core.beans.supports.BeanObjectReader;
import net.woniper.spring.core.beans.supports.BeanObjectRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @see BeanFactory
 * @see BeanDefinitionRegistry
 * @see BeanObjectRegistry
 *
 * 위 3개 interface로 책임을 분리했으며 DefaultBeanFactory는 이를 조합한 class
 * Created by woniper on 2016. 2. 28..
 */
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry, BeanObjectRegistry {

    private final BeanDefinitionReader beanDefinitionReader;
    private final BeanObjectReader beanObjectReader;

    /**
     * Bean Definition Container
     */
    private final Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

    /**
     * Bean Instance Container
     * Bean Name key
     */
    private final Map<String, Object> beanObjects = new ConcurrentHashMap<>();

    /**
     * Bean Instance Container
     * Bean Class Type Key
     */
    private final Map<Class<?>, Object> beanTypeObjects = new ConcurrentHashMap<>();

    /**
     * 생성자 호출 시점에 BeanDefinition을 등록
     * @param packagePath
     */
    public DefaultBeanFactory(String packagePath) {
        this.beanDefinitionReader = new BeanDefinitionReader(this);
        this.beanObjectReader= new BeanObjectReader(this);
        this.beanDefinitionReader.loadBeanDefinitions(packagePath);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        if(beanDefinition == null) throw new NullPointerException();

        synchronized (beanDefinitions) {
            this.beanDefinitions.put(beanName, beanDefinition);
        }
    }

    @Override
    public <T> T getBean(String beanName, Class<T> type) {
        return (T) doGetBean(beanName, type);
    }

    @Override
    public <T> T getBean(Class<T> type) {
        return getBean(null, type);
    }

    private Object doGetBean(String beanName, Class<?> type) {
        Object beanObj = getInstanceOrRegistry(beanName, type);
        if(beanObj == null)
            throw new NullPointerException();
        return beanObj;
    }

    /**
     * bean instance를 return
     * bean instance가 없는 경우(BeanDefinition만 등록된 경우) instance화 후 return
     * @param beanName
     * @param type
     * @return
     */
    private Object getInstanceOrRegistry(String beanName, Class<?> type) {
        Object beanObj = getInstanceBean(beanName, type);

        if(beanObj == null) {
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            if(beanDefinition == null)
                throw new NullPointerException();

            beanObjectReader.loadBeanObject(beanDefinition);
            beanObj = getInstanceBean(beanName, type);
        }

        return beanObj;
    }

    /**
     * bean Name 또는 bean type으로 bean instance return
     * @param beanName
     * @param type
     * @return
     */
    private Object getInstanceBean(String beanName, Class<?> type) {
        return (beanName == null ? beanTypeObjects.get(type) : beanObjects.get(beanName));
    }

    @Override
    public void registerBeanObject(String beanName, Object beanObject) {
        if(beanObject == null) throw new NullPointerException();
        synchronized (beanTypeObjects) {
            this.beanTypeObjects.put(beanObject.getClass(), beanObject);
        }
        synchronized (beanObjects) {
            this.beanObjects.put(beanName, beanObject);
        }
    }

}
