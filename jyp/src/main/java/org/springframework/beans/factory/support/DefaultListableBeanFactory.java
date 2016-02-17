package org.springframework.beans.factory.support;

import java.util.*;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.StringUtils;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {

    /* Whether to allow re-registration of a different definition with the same name */
    private boolean allowBeanDefinitionOverriding = true;

    /** Map of bean definition objects, keyed by bean name */
    private Map beanDefinitionMap = new HashMap();

    /** List of bean definition names, in registration order */
    private List beanDefinitionNames = new ArrayList();


    /**
     * Create a new DefaultListableBeanFactory.
     */
    public DefaultListableBeanFactory() {
        super();
    }

    /**
     * Create a new DefaultListableBeanFactory with the given parent.
     */
    public DefaultListableBeanFactory(BeanFactory parentBeanFactory) {
        super(parentBeanFactory);
    }

    /**
     * Set if it should be allowed to override bean definitions by registering a
     * different definition with the same name, automatically replacing the former.
     * If not, an exception will be thrown. Default is true.
     */
    public void setAllowBeanDefinitionOverriding(boolean allowBeanDefinitionOverriding) {
        this.allowBeanDefinitionOverriding = allowBeanDefinitionOverriding;
    }


    //---------------------------------------------------------------------
    // Implementation of ListableBeanFactory
    //---------------------------------------------------------------------

    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    public String[] getBeanDefinitionNames() {
        return getBeanDefinitionNames(null);
    }

    /**
     * Note that this method is slow. Don't invoke it too often:
     * it's best used only in application initialization.
     */
    public String[] getBeanDefinitionNames(Class type) {
        List matches = new ArrayList();
        Iterator it = this.beanDefinitionNames.iterator();
        while (it.hasNext()) {
            String name = (String) it.next();
            if (type == null || type.isAssignableFrom(getMergedBeanDefinition(name, false).getBeanClass())) {
                matches.add(name);
            }
        }
        return (String[]) matches.toArray(new String[matches.size()]);
    }

    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    public Map getBeansOfType(Class type, boolean includePrototypes, boolean includeFactoryBeans)
            throws BeansException {

        String[] beanNames = getBeanDefinitionNames(type);
        Map result = new HashMap();
        for (int i = 0; i < beanNames.length; i++) {
            if (includePrototypes || isSingleton(beanNames[i])) {
                result.put(beanNames[i], getBean(beanNames[i]));
            }
        }

        String[] singletonNames = getSingletonNames(type);
        for (int i = 0; i < singletonNames.length; i++) {
            result.put(singletonNames[i], getBean(singletonNames[i]));
        }

        if (includeFactoryBeans) {
            String[] factoryNames = getBeanDefinitionNames(FactoryBean.class);
            for (int i = 0; i < factoryNames.length; i++) {
                try {
                    FactoryBean factory = (FactoryBean) getBean(FACTORY_BEAN_PREFIX + factoryNames[i]);
                    Class objectType = factory.getObjectType();
                    if ((objectType == null && factory.isSingleton()) ||
                            ((factory.isSingleton() || includePrototypes) &&
                                    objectType != null && type.isAssignableFrom(objectType))) {
                        Object createdObject = getBean(factoryNames[i]);
                        if (type.isInstance(createdObject)) {
                            result.put(factoryNames[i], createdObject);
                        }
                    }
                }
                catch (FactoryBeanCircularReferenceException ex) {
                    // we're currently creating that FactoryBean
                    // sensible to ignore it, as we are just looking for a certain type
                    logger.debug("Ignoring exception on FactoryBean type check", ex);
                }
            }
        }

        return result;
    }


    //---------------------------------------------------------------------
    // Implementation of ConfigurableListableBeanFactory
    //---------------------------------------------------------------------

    public void preInstantiateSingletons() {
        if (logger.isInfoEnabled()) {
            logger.info("Pre-instantiating singletons in factory [" + this + "]");
        }
        for (Iterator it = this.beanDefinitionNames.iterator(); it.hasNext();) {
            String beanName = (String) it.next();
            if (containsBeanDefinition(beanName)) {
                RootBeanDefinition bd = getMergedBeanDefinition(beanName, false);
                if (bd.isSingleton() && !bd.isLazyInit()) {
                    if (FactoryBean.class.isAssignableFrom(bd.getBeanClass())) {
                        FactoryBean factory = (FactoryBean) getBean(FACTORY_BEAN_PREFIX + beanName);
                        if (factory.isSingleton()) {
                            getBean(beanName);
                        }
                    }
                    else {
                        getBean(beanName);
                    }
                }
            }
        }
    }


    //---------------------------------------------------------------------
    // Implementation of BeanDefinitionRegistry
    //---------------------------------------------------------------------

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
                logger.info("Overriding bean definition for bean '" + name +
                        "': replacing [" + oldBeanDefinition + "] with [" + beanDefinition + "]");
            }
        }
        else {
            this.beanDefinitionNames.add(name);
        }
        this.beanDefinitionMap.put(name, beanDefinition);
    }


    //---------------------------------------------------------------------
    // Implementation of superclass abstract methods
    //---------------------------------------------------------------------

    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition bd = (BeanDefinition) this.beanDefinitionMap.get(beanName);
        if (bd == null) {
            throw new NoSuchBeanDefinitionException(beanName, toString());
        }
        return bd;
    }

    protected String[] getDependingBeanNames(String beanName) throws BeansException {
        List dependingBeanNames = new ArrayList();
        String[] beanDefinitionNames = getBeanDefinitionNames();
        for (int i = 0; i < beanDefinitionNames.length; i++) {
            if (containsBeanDefinition(beanDefinitionNames[i])) {
                RootBeanDefinition bd = getMergedBeanDefinition(beanDefinitionNames[i], false);
                if (bd.getDependsOn() != null) {
                    List dependsOn = Arrays.asList(bd.getDependsOn());
                    if (dependsOn.contains(beanName)) {
                        logger.debug("Found depending bean '" + beanDefinitionNames[i] + "' for bean '" + beanName + "'");
                        dependingBeanNames.add(beanDefinitionNames[i]);
                    }
                }
            }
        }
        return (String[]) dependingBeanNames.toArray(new String[dependingBeanNames.size()]);
    }

    protected Map findMatchingBeans(Class requiredType) {
        return BeanFactoryUtils.beansOfTypeIncludingAncestors(this, requiredType, true, true);
    }


    public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getName());
        sb.append(" defining beans [" + StringUtils.arrayToDelimitedString(getBeanDefinitionNames(), ",") + "]");
        if (getParentBeanFactory() == null) {
            sb.append("; Root of BeanFactory hierarchy");
        }
        else {
            sb.append("; parent=<" + getParentBeanFactory() + ">");
        }
        return sb.toString();
    }

}
