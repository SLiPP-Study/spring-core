package arahansa;

import analyze.beans.BeansException;
import analyze.beans.factory.BeanFactory;

import analyze.beans.factory.xml.XmlBeanDefinitionReader;
import analyze.context.support.DefaultListableBeanFactory;
import analyze.core.io.Resource;


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
