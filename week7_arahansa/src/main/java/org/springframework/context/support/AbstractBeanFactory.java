package org.springframework.context.support;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;

import java.beans.PropertyEditor;
import java.util.*;

/**
 * Created by arahansa on 2016-03-01.
 *
 * 빈팩토리 구현체를 위한 추상화된 슈퍼클래스
 * ConfigurableBeanFactory SPI 인터페이스를 구현한다.
 *
 * 이 클래스는 싱글톤/프로토타입 결정, 싱글톤캐시, 별칭, 팩토리빈핸들링, 자식 빈정의를 위한 빈 정의 병합을 제공합니다.
 * 또한 HierachicalBeanFactory 인터페이스를 구현하여, 빈 팩토리 계층을 관리하게 해줍니다.
 *
 * 주요 템플릿 메소드는 getBeanDefinition과 createBean 입니다. 주어진이름으로 빈정의를 얻거나, 주어진 빈정의로 빈 인스턴스 생성
 */
@Slf4j
public abstract class AbstractBeanFactory implements ConfigurableBeanFactory {

    /**
     *  Factory 빈을 역참조하기 위해 사용됨. 팩토리에의해 생성된빈과 구별하기 위해 사용됨.
     *  예를 들어 myEJB빈이 팩토리라면, &myEJB는 팩토리에 의해 리턴되는 인스턴스가 아니라  팩토리를 리턴할것임.
     */
    public static final String FACTORY_BEAN_PREFIX = "&";

    private BeanFactory parentBeanFactory;
    /** 커스텀 프로퍼티 에디터: 이 팩토리의 빈들에게적용하기 위한 */
    private Map customEditors = new HashMap();
    /** 의존성 타입 : 오토와이어와 의존성 체크 무시를 위한  */
    private final Set ignoreDependencyTypes = new HashSet();
    /** 빈 생성시 적용하기 위한 빈프로세서들:  BeanPostProcessors to apply in createBean */
    private final List beanPostProcessors = new ArrayList();
    /** 기본 빈이름에 대한 별칭으로부터의 맵 ?  Map from alias to canonical bean name */
    private final Map aliasMap = Collections.synchronizedMap(new HashMap());
    /** 싱글톤 캐쉬 : 빈 이름 -> 빈 인스턴스 Cache of singletons: bean name --> bean instance */
    private final Map singletonCache = Collections.synchronizedMap(new HashMap());


    /**
     * 새로운 AbstractBeanFactory 생성 : BeanFactory 타입을 의존성 타입 Set 에 추가
     */
    public AbstractBeanFactory(){
        ignoreDependencyType(BeanFactory.class);
    }
    public void ignoreDependencyType(Class type) {
        this.ignoreDependencyTypes.add(type);
    }

    /**
     * 부모 BeanFactory타입을 가지고 생성할 경우..
     */
    public AbstractBeanFactory(BeanFactory parentBeanFactory){
        this();
        this.parentBeanFactory = parentBeanFactory;
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

    //---------------------------------------------------------------------
    // Implementation of HierarchicalBeanFactory
    //---------------------------------------------------------------------

    public BeanFactory getParentBeanFactory() {
        return parentBeanFactory;
    }


    public List getBeanPostProcessors() {
        return beanPostProcessors;
    }

    //---------------------------------------------------------------------
    // BeanFactory의 구현 Implementation of BeanFactory
    //---------------------------------------------------------------------
    @Override
    public Object getBean(String name, Class requiredType) throws BeansException {
        Object bean = getBean(name);
        if (!requiredType.isAssignableFrom(bean.getClass())) {
            throw new BeanNotOfRequiredTypeException(name, requiredType, bean);
        }
        return bean;
    }


    @Override
    public Object getBean(String name) throws BeansException{
        String beanName = transformedBeanName(name);
        Object sharedInstance = this.singletonCache.get(beanName);
        if (sharedInstance != null) {
            return getObjectForSharedInstance(name, sharedInstance);
        }else{
            // 빈 정의가 존재하는지 알아본다
            RootBeanDefinition mergedBeanDefinition = null;
            try{
                mergedBeanDefinition = getMergedBeanDefinition(beanName, false);
            }catch(NoSuchBeanDefinitionException ex){
                // 없을때는 부모를 뒤져본다.
                if(this.parentBeanFactory!=null){
                    return this.parentBeanFactory.getBean(name);
                }
                throw ex;
            }
            // 빈 인스턴스 생성한다.
            if (mergedBeanDefinition.isSingleton()) {
                synchronized (this.singletonCache) {
                    sharedInstance = this.singletonCache.get(beanName);
                    if(sharedInstance == null){
                        log.info("Creating shared instance of singleton bean '" + beanName + "'");
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
    public Set getIgnoredDependencyTypes() {
        return ignoreDependencyTypes;
    }


    //---------------------------------------------------------------------
    // 구현체 메소드들 Implementation methods
    //---------------------------------------------------------------------
    /**
     * 빈 이름을 반납하는데, 필요하다면 팩토리 역참조 제거를 한다. 별칭으로부터 원본 이름을 해석한다.
     * Return the bean name, stripping out the factory dereference prefix if necessary,
     * and resolving aliases to canonical names.
     */
    protected String transformedBeanName(String name) throws org.springframework.beans.NoSuchBeanDefinitionException {
        if (name == null) {
            throw new org.springframework.beans.NoSuchBeanDefinitionException(name, "Cannot get bean with null name");
        }
        if (name.startsWith(FACTORY_BEAN_PREFIX)) {
            name = name.substring(FACTORY_BEAN_PREFIX.length());
        }
        // handle aliasing
        String canonicalName = (String) this.aliasMap.get(name);
        return canonicalName != null ? canonicalName : name;
    }

    /**
     * 주어진 공유빈을 위한 객체를 얻음. 팩토리빈일 경우를 생각하여 빈 인스턴스 자체나 생성된 객체나를 반납함.
     * Get the object for the given shared bean, either the bean
     * instance itself or its created object in case of a FactoryBean.
     * @param name name that may include factory dereference prefix
     * @param beanInstance the shared bean instance
     * @return the singleton instance of the bean
     */
    protected Object getObjectForSharedInstance(String name, Object beanInstance) {
        String beanName = transformedBeanName(name);

        // 이름에 팩토리 역참조가 있는데, 빈이 팩토리가 아닐때는 예외를 던지는 듯?
        // Don't let calling code try to dereference the
        // bean factory if the bean isn't a factory
        if (isFactoryDereference(name) && !(beanInstance instanceof FactoryBean)) {
            throw new BeanIsNotAFactoryException(beanName, beanInstance);
        }

        // 자, 우리는 인제 빈 인스턴스를 가지고있는데, 노멀빈이나 팩토리빈이다..
        // 만약 팩토리빈일때는, 빈인스턴스를 생성하는 데 사용할 수 있으며, 그렇지 않으면
        // 호출자는 팩토리에게 참조를 원하게 된다.
        // Now we have the bean instance, which may be a normal bean
        // or a FactoryBean. If it's a FactoryBean, we use it to
        // create a bean instance, unless the caller actually wants
        // a reference to the factory.
        if (beanInstance instanceof FactoryBean) {
            if (!isFactoryDereference(name)) {
                // return bean instance from factory
                FactoryBean factory = (FactoryBean) beanInstance;
                log.debug("Bean with name '" + beanName + "' is a factory bean");
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
                log.debug("Calling code asked for FactoryBean instance for name '" + beanName + "'");
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
        catch (org.springframework.beans.NoSuchBeanDefinitionException ex) {
            if (includingAncestors && getParentBeanFactory() instanceof AbstractAutowireCapableBeanFactory) {
                return ((AbstractAutowireCapableBeanFactory) getParentBeanFactory()).getMergedBeanDefinition(beanName, true);
            }
            else {
                throw ex;
            }
        }
    }

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

    public Map getCustomEditors() {
        return customEditors;
    }

    protected void initBeanWrapper(BeanWrapper bw) {
        for (Iterator it = this.customEditors.keySet().iterator(); it.hasNext();) {
            Class clazz = (Class) it.next();
            bw.registerCustomEditor(clazz, (PropertyEditor) this.customEditors.get(clazz));
        }
    }
    protected boolean isFactoryDereference(String name) {
        return name.startsWith(FACTORY_BEAN_PREFIX);
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

    //---------------------------------------------------------------------
    // 설정가능빈팩토리구현 Implementation of ConfigurableBeanFactory
    //---------------------------------------------------------------------

    protected void addSingleton(String beanName, Object singletonObject) {
        this.singletonCache.put(beanName, singletonObject);
    }

    public void destroySingletons() {
        if (log.isInfoEnabled()) {
            log.info("Destroying singletons in factory {" + this + "}");
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
    // 추상메소드들 Abstract methods to be implemented by concrete subclasses
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
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
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
