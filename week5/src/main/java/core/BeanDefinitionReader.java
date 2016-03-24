package core;

import java.io.InputStream;

public interface BeanDefinitionReader {
    void loadBeanDefinitions(InputStream inputStream);
}
