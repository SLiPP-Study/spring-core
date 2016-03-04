package jyp.springframework.beans;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.util.Map;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public interface BeanWrapper {

    String NESTED_PROPERTY_SEPARATOR = ".";


    /**
     * Change the wrapped object. Implementations are required
     * to allow the type of the wrapped object to change.
     * @param obj wrapped object that we are manipulating
     */
    void setWrappedInstance(Object obj) throws BeansException;

    /**
     * Return the bean wrapped by this object (cannot be null).
     * @return the bean wrapped by this object
     */
    Object getWrappedInstance();

    /**
     * Convenience method to return the class of the wrapped object.
     * @return the class of the wrapped object
     */
    Class getWrappedClass();

    /**
     * Register the given custom property editor for all properties of the
     * given type.
     * @param requiredType type of the property
     * @param propertyEditor editor to register
     */
    void registerCustomEditor(Class requiredType, PropertyEditor propertyEditor);

    /**
     * Register the given custom property editor for the given type and
     * property, or for all properties of the given type.
     * @param requiredType type of the property, can be null if a property is
     * given but should be specified in any case for consistency checking
     * @param propertyPath path of the property (name or nested path), or
     * null if registering an editor for all properties of the given type
     * @param propertyEditor editor to register
     */
    void registerCustomEditor(Class requiredType, String propertyPath, PropertyEditor propertyEditor);

    /**
     * Find a custom property editor for the given type and property.
     * @param requiredType type of the property, can be null if a property is
     * given but should be specified in any case for consistency checking
     * @param propertyPath path of the property (name or nested path), or
     * null if looking for an editor for all properties of the given type
     * @return the registered editor, or null if none
     */
    PropertyEditor findCustomEditor(Class requiredType, String propertyPath);


    /**
     * Get the value of a property.
     * @param propertyName name of the property to get the value of
     * @return the value of the property.
     * @throws FatalBeanException if there is no such property, if the property
     * isn't readable, or if the property getter throws an exception.
     */
    Object getPropertyValue(String propertyName) throws BeansException;

    /**
     * Set a property value. This method is provided for convenience only.
     * The setPropertyValue(PropertyValue) method is more powerful.
     * @param propertyName name of the property to set value of
     * @param value the new value
     */
    void setPropertyValue(String propertyName, Object value) throws BeansException;

    /**
     * Update a property value.
     * <b>This is the preferred way to update an individual property.</b>
     * @param pv object containing new property value
     */
    void setPropertyValue(PropertyValue pv) throws BeansException;

    /**
     * Perform a bulk update from a Map.
     * <p>Bulk updates from PropertyValues are more powerful: This method is
     * provided for convenience. Behaviour will be identical to that of
     * the setPropertyValues(PropertyValues) method.
     * @param map Map to take properties from. Contains property value objects,
     * keyed by property name
     */
    void setPropertyValues(Map map) throws BeansException;

    /**
     * The preferred way to perform a bulk update.
     * <p>Note that performing a bulk update differs from performing a single update,
     * in that an implementation of this class will continue to update properties
     * if a <b>recoverable</b> error (such as a vetoed property change or a type mismatch,
     * but <b>not</b> an invalid fieldname or the like) is encountered, throwing a
     * PropertyAccessExceptionsException containing all the individual errors.
     * This exception can be examined later to see all binding errors.
     * Properties that were successfully updated stay changed.
     * <p>Does not allow unknown fields.
     * Equivalent to setPropertyValues(pvs, false, null).
     * @param pvs PropertyValues to set on the target object
     */
    void setPropertyValues(PropertyValues pvs) throws BeansException;

    /**
     * Perform a bulk update with full control over behavior.
     * Note that performing a bulk update differs from performing a single update,
     * in that an implementation of this class will continue to update properties
     * if a <b>recoverable</b> error (such as a vetoed property change or a type mismatch,
     * but <b>not</b> an invalid fieldname or the like) is encountered, throwing a
     * PropertyAccessExceptionsException containing all the individual errors.
     * This exception can be examined later to see all binding errors.
     * Properties that were successfully updated stay changed.
     * <p>Does not allow unknown fields.
     * @param pvs PropertyValues to set on the target object
     * @param ignoreUnknown should we ignore unknown values (not found in the bean!?)
     */
    void setPropertyValues(PropertyValues pvs, boolean ignoreUnknown)
            throws BeansException;


    /**
     * Get the PropertyDescriptors identified on this object
     * (standard JavaBeans introspection).
     * @return the PropertyDescriptors identified on this object
     */
    PropertyDescriptor[] getPropertyDescriptors() throws BeansException;

    /**
     * Get the property descriptor for a particular property.
     * @param propertyName property to check status for
     * @return the property descriptor for a particular property
     * @throws FatalBeanException if there is no such property
     */
    PropertyDescriptor getPropertyDescriptor(String propertyName) throws BeansException;

    /**
     * Return whether this property is readable.
     * Returns false if the property doesn't exist.
     * @param propertyName property to check status for
     * @return whether this property is readable
     */
    boolean isReadableProperty(String propertyName);

    /**
     * Return whether this property is writable.
     * Returns false if the property doesn't exist.
     * @param propertyName property to check status for
     * @return whether this property is writable
     */
    boolean isWritableProperty(String propertyName);
}
