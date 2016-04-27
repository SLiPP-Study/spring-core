package slipp.arahansa;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.TestBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import slipp.yoonsung.DefaultListableBeanFactory;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by jarvis on 2016. 4. 23..
 */

@Slf4j
public class BeanDefinitionRegistryTest {

    @Test
    public void beanRegistryTest() throws Exception{
        log.info("beanRegistryTest");
        BeanDefinitionRegistry bdr = new DefaultListableBeanFactory();
        Properties p = new Properties();
        p.setProperty("kerry.class", "org.springframework.beans.TestBean");
        p.setProperty("kerry.age", "35");
        (new PropertiesBeanDefinitionReader(bdr)).registerBeanDefinitions(p);

        final BeanDefinition kerry = bdr.getBeanDefinition("kerry");
        beanDefinitionTest(kerry);
    }

    @Test
    public void propertyValueTest4Prototype() throws Exception{
        log.info("propertyValueTest4Prototype");
        BeanDefinitionRegistry bdr = new DefaultListableBeanFactory();
        Properties p = new Properties() ;
        p.setProperty("kerry.class", "org.springframework.beans.TestBean");
        p.setProperty("kerry.(singleton)", "false");
        p.setProperty("kerry.age", "35");
        (new PropertiesBeanDefinitionReader(bdr)).registerBeanDefinitions(p);

        final BeanDefinition kerry = bdr.getBeanDefinition("kerry");
        log.info("Kerry : {} ", kerry);
        beanDefinitionTest(kerry);
    }

    private void beanDefinitionTest(BeanDefinition beanDefinition){
        log.info("Kerry : {} ", beanDefinition);
        final MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
        final PropertyValue age = propertyValues.getPropertyValue("age");
        final String name = age.getName();
        final Object value = age.getValue();
        log.info("name : {} , value : {}", name, value);
        assertEquals(name, "age");
        assertEquals(value, "35");
    }


}
