package beans.factory.xml;

import beans.factory.BeanFactory;
import beans.factory.support.BeanDefinitionReader;
import beans.factory.support.DefaultListableBeanFactory;
import beans.factory.xml.XmlBeanDefinitionReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

public class XmlBeanFactory extends DefaultListableBeanFactory {

    BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(this);

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
        beanDefinitionReader.loadBeanDefinitions(inputStream);
        super.preInstantiate();
    }
}
