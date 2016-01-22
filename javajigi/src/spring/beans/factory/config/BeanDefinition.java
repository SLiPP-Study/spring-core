package spring.beans.factory.config;

import java.util.Optional;

public interface BeanDefinition {
    String getBeanClassName();

    Optional<Class> getBeanClass();
}
