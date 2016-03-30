package step2.core;

import java.io.InputStream;

public interface BeanDefinitionReader {
    void loadBeanDefinitions(InputStream inputStream);
}
