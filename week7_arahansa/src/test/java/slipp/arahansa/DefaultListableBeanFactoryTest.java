package slipp.arahansa;

import org.junit.Test;
import org.springframework.beans.TestBean;
import org.springframework.beans.factory.config.PropertiesBeanDefinitionReader;
import slipp.yoonsung.DefaultListableBeanFactory;

import java.util.Properties;

import static org.junit.Assert.assertTrue;

/**
 * Created by jarvis on 2016. 4. 23..
 */


public class DefaultListableBeanFactoryTest {

    @Test
    public void testPrototype() throws Exception {
        DefaultListableBeanFactory lbf = new DefaultListableBeanFactory();
        Properties p = new Properties();
        p.setProperty("kerry.class", "org.springframework.beans.TestBean");
        p.setProperty("kerry.age", "35");
        (new PropertiesBeanDefinitionReader(lbf)).registerBeanDefinitions(p);
        TestBean kerry1 = (TestBean) lbf.getBean("kerry");
        TestBean kerry2 = (TestBean) lbf.getBean("kerry");
        assertTrue("Non null", kerry1 != null);
        assertTrue("Singletons equal", kerry1 == kerry2);

        lbf = new DefaultListableBeanFactory();
        p = new Properties();
        p.setProperty("kerry.class", "org.springframework.beans.TestBean");
        p.setProperty("kerry.(singleton)", "false");
        p.setProperty("kerry.age", "35");
        (new PropertiesBeanDefinitionReader(lbf)).registerBeanDefinitions(p);
        kerry1 = (TestBean) lbf.getBean("kerry");
        kerry2 = (TestBean) lbf.getBean("kerry");
        assertTrue("Non null", kerry1 != null);
        assertTrue("Prototypes NOT equal", kerry1 != kerry2);

        lbf = new DefaultListableBeanFactory();
        p = new Properties();
        p.setProperty("kerry.class", "org.springframework.beans.TestBean");
        p.setProperty("kerry.(singleton)", "true");
        p.setProperty("kerry.age", "35");
        (new PropertiesBeanDefinitionReader(lbf)).registerBeanDefinitions(p);
        kerry1 = (TestBean) lbf.getBean("kerry");
        kerry2 = (TestBean) lbf.getBean("kerry");
        assertTrue("Non null", kerry1 != null);
        assertTrue("Specified singletons equal", kerry1 == kerry2);
    }
}
