import jyp.XmlBeanFactory;
import jyp.bean.Woniper;
import jyp.bean.Woojin;
import jyp.bean.Yoonsung;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author jinyoung.park89
 * @since 2016. 3. 30.
 */
public class Step1Test {

    private XmlBeanFactory beanFactory;

    @Before
    public void setup() {
        beanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step1.xml"));
    }

    @Test
    public void step1CreatedBeans() {
        Woojin woojin = (Woojin)beanFactory.getBean("woojin");
        Woniper woniper = (Woniper)beanFactory.getBean("woniper");

        assertNotNull(woojin);
        assertNotNull(woniper);
    }

    @Test
    public void step1ClassIsSingletone() {

        Yoonsung yoonsung1 = (Yoonsung)beanFactory.getBean("yoonsung");
        Yoonsung yoonsung2 = (Yoonsung)beanFactory.getBean("yoonsung");

        assertEquals(yoonsung1, yoonsung2);
    }

    @After
    public void tearDown() {
        beanFactory.clear();
    }
}
