package step6;

import java.io.InputStream;

public interface BeanDefinitionReader {
    void loadBeanDefinitions(InputStream inputStream);
}
