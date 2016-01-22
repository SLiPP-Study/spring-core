package net.slipp.core;

import net.slipp.BeanDefinition;
import net.slipp.BeanFactory;
import net.slipp.ConfigurableListableBeanFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements ConfigurableListableBeanFactory {
    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<String, BeanDefinition>();
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        synchronized (this.beanDefinitions) {
            beanDefinitions.put(beanName, beanDefinition);
        }
    }

    @Override
    public Object getBean(String name) {
        return singletonObjects.get(name);
    }

    @Override
    public void preInstantiateSinglonetons() {
        Set<String> names = beanDefinitions.keySet();
        for(String name: names) {
            registerBean(name, beanDefinitions.get(name));
        }
    }

    private void registerBean(String beanName, BeanDefinition beanDefinition) {
        synchronized (this.singletonObjects) {
            Optional<Class> clazz = beanDefinition.getBeanClass();
            try {
                singletonObjects.put(beanName, clazz.get().newInstance());
            } catch (Exception e) { }
        }
    }
}
