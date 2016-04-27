package org.springframework.beans.factory.support;

import lombok.ToString;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * Created by arahansa on 2016-03-01.
 */
@ToString
public abstract class AbstractBeanDefinition implements BeanDefinition {

    private MutablePropertyValues propertyValues;

    private String resourceDescription;

    private boolean singleton = true;

    private boolean lazyInit = false;


    /**
     * Create a new bean definition.
     * @param pvs the PropertyValues to be applied to a new instance of the bean
     */
    protected AbstractBeanDefinition(MutablePropertyValues pvs) {
        this.propertyValues = (pvs != null) ? pvs : new MutablePropertyValues();
    }

    public MutablePropertyValues getPropertyValues() {
        return propertyValues;
    }

    /**
     * This implementations returns null: Just RootBeanDefinitions
     * have concrete support for constructor argument values.
     */
    public ConstructorArgumentValues getConstructorArgumentValues() {
        return null;
    }

    /**
     * Set a description of the resource that this bean definition
     * came from (for the purpose of showing context in case of errors).
     */
    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    public String getResourceDescription() {
        return resourceDescription;
    }

    /**
     * Set if this a <b>Singleton</b>, with a single, shared instance returned
     * on all calls. If false, the BeanFactory will apply the <b>Prototype</b>
     * design pattern, with each caller requesting an instance getting an
     * independent instance. How this is defined will depend on the BeanFactory.
     * "Singletons" are the commoner type.
     */
    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    /**
     * Return whether this a <b>Singleton</b>, with a single, shared instance
     * returned on all calls,
     */
    public boolean isSingleton() {
        return singleton;
    }

    /**
     * Set whether this bean should be lazily initialized.
     * Only applicable to a singleton bean.
     * If false, it will get instantiated on startup by bean factories
     * that perform eager initialization of singletons.
     */
    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    /**
     * Return whether this bean should be lazily initialized.
     */
    public boolean isLazyInit() {
        return lazyInit;
    }

    /**
     * Validate this bean definition.
     * @throws BeanDefinitionValidationException in case of validation failure
     */
    public void validate() throws BeanDefinitionValidationException {
        if (this.lazyInit && !this.singleton) {
            throw new BeanDefinitionValidationException("Lazy initialization is just applicable to singleton beans");
        }
    }

}
