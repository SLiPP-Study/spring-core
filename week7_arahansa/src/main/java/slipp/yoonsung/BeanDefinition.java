package slipp.yoonsung;

public class BeanDefinition {
    private final Class clazz;
    private final PropertyValues propertyValues;

    public BeanDefinition(Class clazz, PropertyValues propertyValues) {
        this.clazz = clazz;
        this.propertyValues = propertyValues;
    }

    public Class getBeanClass() {
        return clazz;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }
}
