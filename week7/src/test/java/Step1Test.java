import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bean.Woniper;
import bean.Woojin;
import bean.Yoonsung;
import beans.factory.xml.XmlBeanFactory;

import java.lang.reflect.InvocationTargetException;

public class Step1Test {

    private XmlBeanFactory beanFactory;

    @Before
    public void setup() throws NoSuchMethodException, InvocationTargetException {
        beanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step1.xml"));
    }

    @Test
    public void step1CreatedBeans() throws NoSuchMethodException, InvocationTargetException {
        Woojin woojin = (Woojin)beanFactory.getBean("woojin");
        Woniper woniper = (Woniper)beanFactory.getBean("woniper");

        assertNotNull(woojin);
        assertNotNull(woniper);
    }

    @Test
    public void step1ClassIsSingletone() throws NoSuchMethodException, InvocationTargetException {

        Yoonsung yoonsung1 = (Yoonsung)beanFactory.getBean("yoonsung");
        Yoonsung yoonsung2 = (Yoonsung)beanFactory.getBean("yoonsung");

        assertEquals(yoonsung1, yoonsung2);
    }

    @After
    public void tearDown() {
        beanFactory.clear();
    }

}
