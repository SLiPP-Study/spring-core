package net.slipp;

import java.util.Optional;

public interface BeanDefinition {
    String getBeanClassName();

    Optional<Class> getBeanClass();
}
