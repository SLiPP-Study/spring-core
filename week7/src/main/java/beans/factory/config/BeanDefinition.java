package beans.factory.config;

import core.ConstructorArguments;
import beans.PropertyValues;

public class BeanDefinition {
    private final String id;
    private final Class clazz;
    private final ConstructorArguments constructorArguments;
    private final PropertyValues propertyValues;

    public BeanDefinition(String id, Class clazz, ConstructorArguments constructorArguments,
            PropertyValues propertyValues) {
        this.id = id;
        this.clazz = clazz;
        this.constructorArguments = constructorArguments;
        this.propertyValues = propertyValues;
    }

    public String getId() {
        return id;
    }

    public Class getBeanClass() {
        return clazz;
    }

    public ConstructorArguments getConstructorArguments() {
        return constructorArguments;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }
}
