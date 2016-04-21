package beans.factory.support;

import java.io.InputStream;

public interface BeanDefinitionReader {
    void loadBeanDefinitions(InputStream inputStream);
}
