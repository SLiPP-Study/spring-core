package analyze.beans;

/**
 * Created by arahansa on 2016-03-01.
 */
public interface PropertyValues {

    /**
     * Return an array of the PropertyValue objects held in this object.
     * @return an array of the PropertyValue objects held in this object.
     */
    PropertyValue[] getPropertyValues();

    /**
     * Return the property value with the given name.
     * @param propertyName name to search for
     * @return pv or null
     */
    PropertyValue getPropertyValue(String propertyName);

    /**
     * Is there a property value for this property?
     * @param propertyName name of the property we're interested in
     * @return whether there is a property value for this property
     */
    boolean contains(String propertyName);

    /**
     * Return the changes since the previous PropertyValues.
     * Subclasses should also override equals.
     * @param old old property values
     * @return PropertyValues updated or new properties.
     * Return the empty PropertyValues if there are no changes.
     */
    PropertyValues changesSince(PropertyValues old);

}
