package org.springframework.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.BeansException;

import java.util.Map;

/**
 * Created by arahansa on 2016-02-28.
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * Return the number of beans defined in the factory.
     * Does not consider any hierarchy this factory may participate in.
     * <p>Note: Ignores any singleton beans that have been registered by
     * other means than bean definitions.
     * @return the number of beans defined in the factory
     */
    int getBeanDefinitionCount();

    /**
     * Return the names of all beans defined in this factory.
     * Does not consider any hierarchy this factory may participate in.
     * <p>Note: Ignores any singleton beans that have been registered by
     * other means than bean definitions.
     * @return the names of all beans defined in this factory,
     * or an empty array if none defined
     */
    String[] getBeanDefinitionNames();

    /**
     * Return the names of beans matching the given type (including subclasses),
     * judging from the bean definitions. Will <i>not</i> consider FactoryBeans,
     * as the type of their created objects is not known before instantiation.
     * Does not consider any hierarchy this factory may participate in.
     * <p>Note: Ignores any singleton beans that have been registered by
     * other means than bean definitions.
     * @param type class or interface to match, or null for all bean names
     * @return the names of beans matching the given object type
     * (including subclasses), or an empty array if none
     */
    String[] getBeanDefinitionNames(Class type);

    /**
     * Check if this bean factory contains a bean definition with the given name.
     * Does not consider any hierarchy this factory may participate in.
     * <p>Note: Ignores any singleton beans that have been registered by
     * other means than bean definitions.
     * @param name the name of the bean to look for
     * @return if this bean factory contains a bean definition with the given name
     */
    boolean containsBeanDefinition(String name);
    /**
     * Return the bean instances that match the given object type (including
     * subclasses), judging from either bean definitions or the value of
     * getObjectType() in the case of FactoryBeans.
     * Does not consider any hierarchy this factory may participate in.
     * <p>If FactoryBean's getObjectType() returns null and the bean is a
     * singleton, the type of the actually created objects should be evaluated.
     * Prototypes without explicit object type specification should be ignored.
     * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
     * by other means than bean definitions.
     * @param type class or interface to match
     * @param includePrototypes whether to include prototype beans too
     * or just singletons (also applies to FactoryBeans)
     * @param includeFactoryBeans whether to include FactoryBeans too
     * or just normal beans
     * @return a Map with the matching beans, containing the bean names as
     * keys and the corresponding bean instances as values
     * @throws BeansException if the beans could not be created
     * @see FactoryBean#getObjectType
     */
    Map getBeansOfType(Class type, boolean includePrototypes, boolean includeFactoryBeans)
            throws BeansException;

}
