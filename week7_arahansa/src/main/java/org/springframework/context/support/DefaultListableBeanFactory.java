package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;

import java.util.*;

/**
 * Created by arahansa on 2016-02-28.
 */
@Slf4j
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {

    /* Whether to allow re-registration of a different definition with the same name */
    private boolean allowBeanDefinitionOverriding = true;

    /** Map of bean definition objects, keyed by bean name */
    private Map beanDefinitionMap = new HashMap();
    /** List of bean definition names, in registration order */
    private List beanDefinitionNames = new ArrayList();

    public DefaultListableBeanFactory(BeanFactory parentBeanFactory) {
        super(parentBeanFactory);
    }

    protected Map findMatchingBeans(Class requiredType) {
        return BeanFactoryUtils.beansOfTypeIncludingAncestors(this, requiredType, true, true);
    }

    @Override
    protected String[] getDependingBeanNames(String beanName) throws BeansException {
        return new String[0];
    }

    public DefaultListableBeanFactory() {
        super();
    }

    public void initializingBeans() throws BeansException{
        log.debug("Bean Creating programmatically");
        beanDefinitionMap.keySet().forEach(n-> getBean((String) n));
    }

    //
    // BeanDefinitionRegistry 등록
    //
    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }

    @Override
    public String[] getBeanDefinitionNames(Class type) {
        return new String[0];
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return false;
    }

    @Override
    public Map getBeansOfType(Class type, boolean includePrototypes, boolean includeFactoryBeans) throws BeansException {
        return null;
    }



    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition bd = (BeanDefinition) this.beanDefinitionMap.get(beanName);
        if (bd == null) {
            throw new NoSuchBeanDefinitionException(beanName, toString());
        }
        return bd;
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition)
            throws BeanDefinitionStoreException {
        if (beanDefinition instanceof AbstractBeanDefinition) {
            try {
                ((AbstractBeanDefinition) beanDefinition).validate();
            }
            catch (BeanDefinitionValidationException ex) {
                throw new BeanDefinitionStoreException(beanDefinition.getResourceDescription(), name,
                        "Validation of bean definition with name failed", ex);
            }
        }
        Object oldBeanDefinition = this.beanDefinitionMap.get(name);
        if (oldBeanDefinition != null) {
            if (!this.allowBeanDefinitionOverriding) {
                throw new BeanDefinitionStoreException("Cannot register bean definition [" + beanDefinition + "] for bean '" +
                        name + "': there's already [" + oldBeanDefinition + "] bound");
            }
            else {
                log.info("Overriding bean definition for bean '" + name +
                        "': replacing [" + oldBeanDefinition + "] with [" + beanDefinition + "]");
            }
        }
        else {
            this.beanDefinitionNames.add(name);
        }
        this.beanDefinitionMap.put(name, beanDefinition);
    }

    @Override
    public String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return new String[0];
    }

    @Override
    public void registerAlias(String name, String alias) throws BeansException {

    }

    @Override
    public void preInstantiateSingletons() {

    }
}
