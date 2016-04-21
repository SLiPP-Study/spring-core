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

    public XmlBeanFactory(String fileName) throws FileNotFoundException, NoSuchMethodException,
            SecurityException, IllegalArgumentException, InvocationTargetException {
        this(fileName, null);
    }

    public XmlBeanFactory(InputStream inputStream) throws NoSuchMethodException, SecurityException,
            IllegalArgumentException, InvocationTargetException {
        this(inputStream, null);
    }

    public XmlBeanFactory(String fileName, BeanFactory parentBeanFactory) throws FileNotFoundException,
            NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        this(new FileInputStream(fileName), parentBeanFactory);
    }

    public XmlBeanFactory(InputStream inputStream, BeanFactory parentBeanFactory) throws NoSuchMethodException,
            SecurityException, IllegalArgumentException, InvocationTargetException {
        super(parentBeanFactory);
        beanDefinitionReader.loadBeanDefinitions(inputStream);
        super.preInstantiate();
    }
}
