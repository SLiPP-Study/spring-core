package analyze.beans.factory.config;

import analyze.beans.BeansException;
import analyze.beans.factory.BeanFactory;

/**
 * Created by arahansa on 2016-03-01.
 */
public interface AutowireCapableBeanFactory extends BeanFactory{

    /**
     * Constant that indicates autowiring by name.
     * @see #autowire
     * @see #autowireBeanProperties
     */
    int AUTOWIRE_BY_NAME = 1;

    /**
     * Constant that indicates autowiring by type.
     * @see #autowire
     * @see #autowireBeanProperties
     */
    int AUTOWIRE_BY_TYPE = 2;

    /**
     * Constant that indicates autowiring a constructor.
     * @see #autowire
     */
    int AUTOWIRE_CONSTRUCTOR = 3;

    /**
     * Constant that indicates determining an appropriate autowire strategy
     * through introspection of the bean class.
     * @see #autowire
     */
    int AUTOWIRE_AUTODETECT = 4;


    /**
     * Create a new bean instance of the given class with the specified autowire
     * strategy. All constants defined in this interface are supported here.
     * @param beanClass the class of the bean to instantiate
     * @param autowireMode by name or type, using the constants in this interface
     * @param dependencyCheck whether to perform a dependency check for objects
     * (not applicable to autowiring a constructor, thus ignored there)
     * @return the new bean instance
     * @throws BeansException if instantiation respectively wiring failed
     * @see #AUTOWIRE_BY_NAME
     * @see #AUTOWIRE_BY_TYPE
     * @see #AUTOWIRE_CONSTRUCTOR
     * @see #AUTOWIRE_AUTODETECT
     */
    Object autowire(Class beanClass, int autowireMode, boolean dependencyCheck)
            throws BeansException;

    /**
     * Autowire the bean properties of the given bean instance by name or type.
     * @param existingBean the existing bean instance
     * @param autowireMode by name or type, using the constants in this interface
     * @param dependencyCheck whether to perform a dependency check for object
     * @throws BeansException if wiring failed
     * @see #AUTOWIRE_BY_NAME
     * @see #AUTOWIRE_BY_TYPE
     */
    void autowireBeanProperties(Object existingBean, int autowireMode, boolean dependencyCheck)
            throws BeansException;

    /**
     * Apply BeanPostProcessors to the given existing bean instance,
     * invoking their postProcessBeforeInitialization methods.
     * The returned bean instance may be a wrapper around the original.
     * @param existingBean the new bean instance
     * @param name the name of the bean
     * @return the bean instance to use, either the original or a wrapped one
     * @throws BeansException if any post-processing failed
     * @see BeanPostProcessor#postProcessBeforeInitialization
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String name)
            throws BeansException;


    /**
     * Apply BeanPostProcessors to the given existing bean instance,
     * invoking their postProcessAfterInitialization methods.
     * The returned bean instance may be a wrapper around the original.
     * @param existingBean the new bean instance
     * @param name the name of the bean
     * @return the bean instance to use, either the original or a wrapped one
     * @throws BeansException if any post-processing failed
     * @see BeanPostProcessor#postProcessAfterInitialization
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String name)
            throws BeansException;

}
