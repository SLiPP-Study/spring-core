package jyp.springframework.beans.factory.support;

import java.beans.PropertyEditor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jyp.springframework.beans.BeanWrapper;
import jyp.springframework.beans.BeansException;
import jyp.springframework.beans.factory.BeanCreationException;
import jyp.springframework.beans.factory.BeanDefinitionStoreException;
import jyp.springframework.beans.factory.BeanFactory;
import jyp.springframework.beans.factory.BeanIsNotAFactoryException;
import jyp.springframework.beans.factory.BeanNotOfRequiredTypeException;
import jyp.springframework.beans.factory.FactoryBean;
import jyp.springframework.beans.factory.FactoryBeanCircularReferenceException;
import jyp.springframework.beans.factory.HierarchicalBeanFactory;
import jyp.springframework.beans.factory.NoSuchBeanDefinitionException;
import jyp.springframework.beans.factory.config.BeanDefinition;
import jyp.springframework.beans.factory.config.BeanPostProcessor;
import jyp.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public abstract class AbstractBeanFactory implements ConfigurableBeanFactory, HierarchicalBeanFactory {

    /**
     * Used to dereference a FactoryBean and distinguish it from
     * beans <i>created</i> by the factory. For example,
     * if the bean named <code>myEjb</code> is a factory, getting
     * <code>&myEjb</code> will return the factory, not the instance
     * returned by the factory.
     */
    public static final String FACTORY_BEAN_PREFIX = "&";


    /** Logger available to subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    /** Parent bean factory, for bean inheritance support */
    private BeanFactory parentBeanFactory;

    /** Custom PropertyEditors to apply to the beans of this factory */
    private Map customEditors = new HashMap();

    /** Dependency types to ignore on dependency check and autowire */
    private final Set ignoreDependencyTypes = new HashSet();

    /** BeanPostProcessors to apply in createBean */
    private final List beanPostProcessors = new ArrayList();

    /** Map from alias to canonical bean name */
    private final Map aliasMap = Collections.synchronizedMap(new HashMap());

    /** Cache of singletons: bean name --> bean instance */
    private final Map singletonCache = Collections.synchronizedMap(new HashMap());


    /**
     * Create a new AbstractBeanFactory.
     */
    public AbstractBeanFactory() {
        ignoreDependencyType(BeanFactory.class);
    }

    /**
     * Create a new AbstractBeanFactory with the given parent.
     * @param parentBeanFactory parent bean factory, or null if none
     * @see #getBean
     */
    public AbstractBeanFactory(BeanFactory parentBeanFactory) {
        this();
        this.parentBeanFactory = parentBeanFactory;
    }


    //---------------------------------------------------------------------
    // Implementation of BeanFactory
    //---------------------------------------------------------------------

    /**
     * Return the bean with the given name,
     * checking the parent bean factory if not found.
     * @param name name of the bean to retrieve
     */
    public Object getBean(String name) throws BeansException {
        String beanName = transformedBeanName(name);
        // eagerly check singleton cache for manually registered singletons
        Object sharedInstance = this.singletonCache.get(beanName);
        if (sharedInstance != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Returning cached instance of singleton bean '" + beanName + "'");
            }
            return getObjectForSharedInstance(name, sharedInstance);
        }
        else {
            // check if bean definition exists
            RootBeanDefinition mergedBeanDefinition = null;
            try {
                mergedBeanDefinition = getMergedBeanDefinition(beanName, false);
            }
            catch (NoSuchBeanDefinitionException ex) {
                // not found -> check parent
                if (this.parentBeanFactory != null) {
                    return this.parentBeanFactory.getBean(name);
                }
                throw ex;
            }
            // create bean instance
            if (mergedBeanDefinition.isSingleton()) {
                synchronized (this.singletonCache) {
                    // re-check singleton cache within synchronized block
                    sharedInstance = this.singletonCache.get(beanName);
                    if (sharedInstance == null) {
                        logger.info("Creating shared instance of singleton bean '" + beanName + "'");
                        sharedInstance = createBean(beanName, mergedBeanDefinition);
                        addSingleton(beanName, sharedInstance);
                    }
                }
                return getObjectForSharedInstance(name, sharedInstance);
            }
            else {
                return createBean(name, mergedBeanDefinition);
            }
        }
    }

    public Object getBean(String name, Class requiredType) throws BeansException {
        Object bean = getBean(name);
        if (!requiredType.isAssignableFrom(bean.getClass())) {
            throw new BeanNotOfRequiredTypeException(name, requiredType, bean);
        }
        return bean;
    }

    public boolean containsBean(String name) {
        String beanName = transformedBeanName(name);
        if (this.singletonCache.containsKey(beanName)) {
            return true;
        }
        if (containsBeanDefinition(beanName)) {
            return true;
        }
        else {
            // not found -> check parent
            if (this.parentBeanFactory != null) {
                return this.parentBeanFactory.containsBean(beanName);
            }
            else {
                return false;
            }
        }
    }

    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        String beanName = transformedBeanName(name);
        try {
            Class beanClass = null;
            boolean singleton = true;
            Object beanInstance = this.singletonCache.get(beanName);
            if (beanInstance != null) {
                beanClass = beanInstance.getClass();
                singleton = true;
            }
            else {
                RootBeanDefinition bd = getMergedBeanDefinition(beanName, false);
                beanClass = bd.getBeanClass();
                singleton = bd.isSingleton();
            }
            // in case of FactoryBean, return singleton status of created object if not a dereference
            if (FactoryBean.class.isAssignableFrom(beanClass) && !isFactoryDereference(name)) {
                FactoryBean factoryBean = (FactoryBean) getBean(FACTORY_BEAN_PREFIX + beanName);
                return factoryBean.isSingleton();
            }
            else {
                return singleton;
            }
        }
        catch (NoSuchBeanDefinitionException ex) {
            // not found -> check parent
            if (this.parentBeanFactory != null) {
                return this.parentBeanFactory.isSingleton(beanName);
            }
            throw ex;
        }
    }

    public String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        String beanName = transformedBeanName(name);
        // check if bean actually exists in this bean factory
        if (this.singletonCache.containsKey(beanName) || containsBeanDefinition(beanName)) {
            // if found, gather aliases
            List aliases = new ArrayList();
            for (Iterator it = this.aliasMap.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                if (entry.getValue().equals(beanName)) {
                    aliases.add(entry.getKey());
                }
            }
            return (String[]) aliases.toArray(new String[aliases.size()]);
        }
        else {
            // not found -> check parent
            if (this.parentBeanFactory != null) {
                return this.parentBeanFactory.getAliases(beanName);
            }
            throw new NoSuchBeanDefinitionException(beanName, toString());
        }
    }


    //---------------------------------------------------------------------
    // Implementation of HierarchicalBeanFactory
    //---------------------------------------------------------------------

    public BeanFactory getParentBeanFactory() {
        return parentBeanFactory;
    }


    //---------------------------------------------------------------------
    // Implementation of ConfigurableBeanFactory
    //---------------------------------------------------------------------

    public void setParentBeanFactory(BeanFactory parentBeanFactory) {
        this.parentBeanFactory = parentBeanFactory;
    }

    public void registerCustomEditor(Class requiredType, PropertyEditor propertyEditor) {
        this.customEditors.put(requiredType, propertyEditor);
    }

    /**
     * Return the map of custom editors, with Classes as keys
     * and PropertyEditors as values.
     */
    public Map getCustomEditors() {
        return customEditors;
    }

    public void ignoreDependencyType(Class type) {
        this.ignoreDependencyTypes.add(type);
    }

    /**
     * Return the set of classes that will get ignored for autowiring.
     */
    public Set getIgnoredDependencyTypes() {
        return ignoreDependencyTypes;
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * Return the list of BeanPostProcessors that will get applied
     * to beans created with this factory.
     */
    public List getBeanPostProcessors() {
        return beanPostProcessors;
    }

    public void registerAlias(String beanName, String alias) throws BeanDefinitionStoreException {
        logger.debug("Registering alias '" + alias + "' for bean with name '" + beanName + "'");
        synchronized (this.aliasMap) {
            Object registeredName = this.aliasMap.get(alias);
            if (registeredName != null) {
                throw new BeanDefinitionStoreException("Cannot register alias '" + alias + "' for bean name '" + beanName +
                        "': it's already registered for bean name '" + registeredName + "'");
            }
            this.aliasMap.put(alias, beanName);
        }
    }

    public void registerSingleton(String beanName, Object singletonObject) throws BeanDefinitionStoreException {
        synchronized (this.singletonCache) {
            Object oldObject = this.singletonCache.get(beanName);
            if (oldObject != null) {
                throw new BeanDefinitionStoreException("Could not register object [" + singletonObject +
                        "] under bean name '" + beanName + "': there's already object [" +
                        oldObject + " bound");
            }
            addSingleton(beanName, singletonObject);
        }
    }

    /**
     * Add the given singleton object to the singleton cache of this factory.
     * <p>To be called for eager registration of singletons, e.g. to be able to
     * resolve circular references.
     * @param beanName the name of the bean
     * @param singletonObject the singleton object
     */
    protected void addSingleton(String beanName, Object singletonObject) {
        this.singletonCache.put(beanName, singletonObject);
    }

    public void destroySingletons() {
        if (logger.isInfoEnabled()) {
            logger.info("Destroying singletons in factory {" + this + "}");
        }
        synchronized (this.singletonCache) {
            Set singletonCacheKeys = new HashSet(this.singletonCache.keySet());
            for (Iterator it = singletonCacheKeys.iterator(); it.hasNext();) {
                destroySingleton((String) it.next());
            }
        }
    }

    /**
     * Destroy the given bean. Delegates to destroyBean if a corresponding
     * singleton instance is found.
     * @param beanName name of the bean
     * @see #destroyBean
     */
    protected void destroySingleton(String beanName) {
        Object singletonInstance = this.singletonCache.remove(beanName);
        if (singletonInstance != null) {
            destroyBean(beanName, singletonInstance);
        }
    }


    //---------------------------------------------------------------------
    // Implementation methods
    //---------------------------------------------------------------------

    /**
     * Return the bean name, stripping out the factory dereference prefix if necessary,
     * and resolving aliases to canonical names.
     */
    protected String transformedBeanName(String name) throws NoSuchBeanDefinitionException {
        if (name == null) {
            throw new NoSuchBeanDefinitionException(name, "Cannot get bean with null name");
        }
        if (name.startsWith(FACTORY_BEAN_PREFIX)) {
            name = name.substring(FACTORY_BEAN_PREFIX.length());
        }
        // handle aliasing
        String canonicalName = (String) this.aliasMap.get(name);
        return canonicalName != null ? canonicalName : name;
    }

    /**
     * Return whether this name is a factory dereference
     * (beginning with the factory dereference prefix).
     */
    protected boolean isFactoryDereference(String name) {
        return name.startsWith(FACTORY_BEAN_PREFIX);
    }

    /**
     * Initialize the given BeanWrapper with the custom editors registered
     * with this factory.
     * @param bw the BeanWrapper to initialize
     */
    protected void initBeanWrapper(BeanWrapper bw) {
        for (Iterator it = this.customEditors.keySet().iterator(); it.hasNext();) {
            Class clazz = (Class) it.next();
            bw.registerCustomEditor(clazz, (PropertyEditor) this.customEditors.get(clazz));
        }
    }

    /**
     * Return the names of beans in the singleton cache that match the given
     * object type (including subclasses). Will <i>not</i> consider FactoryBeans
     * as the type of their created objects is not known before instantiation.
     * <p>Does not consider any hierarchy this factory may participate in.
     * @param type class or interface to match, or null for all bean names
     * @return the names of beans in the singleton cache that match the given
     * object type (including subclasses), or an empty array if none
     */
    public String[] getSingletonNames(Class type) {
        Set keys = this.singletonCache.keySet();
        Set matches = new HashSet();
        Iterator itr = keys.iterator();
        while (itr.hasNext()) {
            String name = (String) itr.next();
            Object singletonObject = this.singletonCache.get(name);
            if (type == null || type.isAssignableFrom(singletonObject.getClass())) {
                matches.add(name);
            }
        }
        return (String[]) matches.toArray(new String[matches.size()]);
    }

    /**
     * Get the object for the given shared bean, either the bean
     * instance itself or its created object in case of a FactoryBean.
     * @param name name that may include factory dereference prefix
     * @param beanInstance the shared bean instance
     * @return the singleton instance of the bean
     */
    protected Object getObjectForSharedInstance(String name, Object beanInstance) {
        String beanName = transformedBeanName(name);

        // Don't let calling code try to dereference the
        // bean factory if the bean isn't a factory
        if (isFactoryDereference(name) && !(beanInstance instanceof FactoryBean)) {
            throw new BeanIsNotAFactoryException(beanName, beanInstance);
        }

        // Now we have the bean instance, which may be a normal bean
        // or a FactoryBean. If it's a FactoryBean, we use it to
        // create a bean instance, unless the caller actually wants
        // a reference to the factory.
        if (beanInstance instanceof FactoryBean) {
            if (!isFactoryDereference(name)) {
                // return bean instance from factory
                FactoryBean factory = (FactoryBean) beanInstance;
                logger.debug("Bean with name '" + beanName + "' is a factory bean");
                try {
                    beanInstance = factory.getObject();
                }
                catch (BeansException ex) {
                    throw ex;
                }
                catch (Exception ex) {
                    throw new BeanCreationException("FactoryBean threw exception on object creation", ex);
                }
                if (beanInstance == null) {
                    throw new FactoryBeanCircularReferenceException(
                            "Factory bean '" + beanName + "' returned null object - " +
                                    "possible cause: not fully initialized due to circular bean reference");
                }
            }
            else {
                // the user wants the factory itself
                logger.debug("Calling code asked for FactoryBean instance for name '" + beanName + "'");
            }
        }

        return beanInstance;
    }

    /**
     * Return a RootBeanDefinition, even by traversing parent if the parameter is a child definition.
     * Will ask the parent bean factory if not found in this instance.
     * @return a merged RootBeanDefinition with overridden properties
     */
    public RootBeanDefinition getMergedBeanDefinition(String beanName, boolean includingAncestors)
            throws BeansException {
        try {
            return getMergedBeanDefinition(beanName, getBeanDefinition(beanName));
        }
        catch (NoSuchBeanDefinitionException ex) {
            if (includingAncestors && getParentBeanFactory() instanceof AbstractAutowireCapableBeanFactory) {
                return ((AbstractAutowireCapableBeanFactory) getParentBeanFactory()).getMergedBeanDefinition(beanName, true);
            }
            else {
                throw ex;
            }
        }
    }

    /**
     * Return a RootBeanDefinition, even by traversing parent if the parameter is a child definition.
     * @return a merged RootBeanDefinition with overridden properties
     */
    protected RootBeanDefinition getMergedBeanDefinition(String beanName, BeanDefinition bd) {
        if (bd instanceof RootBeanDefinition) {
            return (RootBeanDefinition) bd;
        }
        else if (bd instanceof ChildBeanDefinition) {
            ChildBeanDefinition cbd = (ChildBeanDefinition) bd;
            // deep copy
            RootBeanDefinition rbd = new RootBeanDefinition(getMergedBeanDefinition(cbd.getParentName(), true));
            // override properties
            for (int i = 0; i < cbd.getPropertyValues().getPropertyValues().length; i++) {
                rbd.getPropertyValues().addPropertyValue(cbd.getPropertyValues().getPropertyValues()[i]);
            }
            // override settings
            rbd.setSingleton(cbd.isSingleton());
            rbd.setLazyInit(cbd.isLazyInit());
            rbd.setResourceDescription(cbd.getResourceDescription());
            return rbd;
        }
        else {
            throw new BeanDefinitionStoreException(bd.getResourceDescription(), beanName,
                    "Definition is neither a RootBeanDefinition nor a ChildBeanDefinition");
        }
    }

    //---------------------------------------------------------------------
    // Abstract methods to be implemented by concrete subclasses
    //---------------------------------------------------------------------

    /**
     * Check if this bean factory contains a bean definition with the given name.
     * Does not consider any hierarchy this factory may participate in.
     * Invoked by containsBean when no cached singleton instance is found.
     * @param beanName the name of the bean to look for
     * @return if this bean factory contains a bean definition with the given name
     * @see #containsBean
     */
    public abstract boolean containsBeanDefinition(String beanName);

    /**
     * Return the bean definition for the given bean name.
     * Subclasses should normally implement caching, as this method is invoked
     * by this class every time bean definition metadata is needed.
     * @param beanName name of the bean to find a definition for
     * @return the BeanDefinition for this prototype name. Must never return null.
     * @throws NoSuchBeanDefinitionException
     * if the bean definition cannot be resolved
     * @throws BeansException in case of errors
     * @see RootBeanDefinition
     * @see ChildBeanDefinition
     */
    public abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * Create a bean instance for the given bean definition.
     * The bean definition will already have been merged with the parent
     * definition in case of a child definition.
     * <p>All the other methods in this class invoke this method, although
     * beans may be cached after being instantiated by this method. All bean
     * instantiation within this class is performed by this method.
     * @param beanName name of the bean
     * @param mergedBeanDefinition the bean definition for the bean
     * @return a new instance of the bean
     * @throws BeansException in case of errors
     */
    protected abstract Object createBean(String beanName, RootBeanDefinition mergedBeanDefinition)
            throws BeansException;

    /**
     * Destroy the given bean. Must destroy beans that depend on the given
     * bean before the bean itself. Should not throw any exceptions.
     * @param beanName name of the bean
     * @param bean the bean instance to destroy
     */
    protected abstract void destroyBean(String beanName, Object bean);

}
