package jyp.springframework.beans.factory.xml;

import jyp.springframework.beans.BeansException;
import jyp.springframework.beans.factory.support.BeanDefinitionRegistry;
import jyp.springframework.core.io.Resource;
import org.w3c.dom.Document;

/**
 * @author jinyoung.park89
 * @since 2016-03-04
 */
public interface XmlBeanDefinitionParser {
    void registerBeanDefinitions(BeanDefinitionRegistry var1, ClassLoader var2, Document var3, Resource var4) throws BeansException;
}
