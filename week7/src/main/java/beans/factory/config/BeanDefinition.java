package beans.factory.config;

import core.ConstructorArguments;
import beans.PropertyValues;

public class BeanDefinition {

    private static final String SCOPE_SINGLETON = "singleton";

    private static final String SCOPE_PROTOTYPE = "prototype";

    private final String id;
    private final Class clazz;
    private final ConstructorArguments constructorArguments;
    private final PropertyValues propertyValues;

    private String scope = SCOPE_SINGLETON; // Bean Scope에 관한 특별한 설정이 없는 경우 singleton으로 관리 된다.

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

    // Bean Scope가 singleton 인지 여부
    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope);
    }
}
