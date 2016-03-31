package jyp;

public class BeanDefinition {
    private final Class clazz;
    private final PropertyValues propertyValues;
    private final ConstructorArgument constructorArgument;

    public BeanDefinition(Class clazz, PropertyValues propertyValues, ConstructorArgument constructorArgument) {
        this.clazz = clazz;
        this.propertyValues = propertyValues;
        this.constructorArgument = constructorArgument;
    }

    public Class getBeanClass() {
        return clazz;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public ConstructorArgument getConstructorArgument() {
        return constructorArgument;
    }
}
