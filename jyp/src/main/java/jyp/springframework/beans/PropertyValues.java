package jyp.springframework.beans;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public interface PropertyValues {

    PropertyValue[] getPropertyValues();

    PropertyValue getPropertyValue(String propertyName);

    boolean contains(String propertyName);

    PropertyValues changesSince(PropertyValues old);
}
