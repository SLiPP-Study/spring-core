package jyp;

public class BeanDefinition {
    private final Class clazz;
    private final PropertyValues propertyValues;
    private final ConstructorArguments constructorArguments;

    public BeanDefinition(Class clazz, PropertyValues propertyValues,
            ConstructorArguments constructorArguments) {
        this.clazz = clazz;
        this.propertyValues = propertyValues;
        this.constructorArguments = constructorArguments;
    }

    public Class getBeanClass() {
        return clazz;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public ConstructorArguments getConstructorArguments() {
        return constructorArguments;
    }

    public boolean isCreateWithConstructor() {
        return this.constructorArguments != null && this.constructorArguments.hasConstructorArguments();
    }
}
