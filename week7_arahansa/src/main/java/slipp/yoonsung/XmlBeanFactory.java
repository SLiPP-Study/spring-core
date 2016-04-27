package slipp.yoonsung;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class XmlBeanFactory extends DefaultListableBeanFactory {

    // BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(this);

    public XmlBeanFactory(String fileName) throws FileNotFoundException {
        this(fileName, null);
    }

    public XmlBeanFactory(InputStream inputStream) {
        this(inputStream, null);
    }

    public XmlBeanFactory(String fileName, BeanFactory parentBeanFactory) throws FileNotFoundException {
        this(new FileInputStream(fileName), parentBeanFactory);
    }

    public XmlBeanFactory(InputStream inputStream, BeanFactory parentBeanFactory) {
        super(parentBeanFactory);
        //TODO BeanDefinition을 load할 수 있도록 적절한 메서드를 호출합니다.
        super.preInstantiate();
    }
}
