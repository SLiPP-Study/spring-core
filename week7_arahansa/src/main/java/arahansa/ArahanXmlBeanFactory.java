package arahansa;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.DefaultListableBeanFactory;
import org.springframework.core.io.Resource;


/**
 * Created by arahansa on 2016-03-02.
 */
public class ArahanXmlBeanFactory extends DefaultListableBeanFactory{

    private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);


    public ArahanXmlBeanFactory(Resource resource) throws BeansException{
        this(resource, null);
    }

    public ArahanXmlBeanFactory(Resource resource, BeanFactory parentBeanFactory) throws BeansException {
        super(parentBeanFactory);
        this.reader.loadBeanDefinitions(resource);
    }
}
