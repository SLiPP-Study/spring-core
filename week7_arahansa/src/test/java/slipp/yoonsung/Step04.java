package slipp.yoonsung;

import org.springframework.core.io.ClassPathResource;
import arahansa.ArahanXmlBeanFactory;
import arahansa.bean.B;
import arahansa.bean.C;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by arahansa on 2016-03-02.
 */
public class Step04 {

    @Test
    public void step03() throws Exception{
        ArahanXmlBeanFactory beanFactory = new ArahanXmlBeanFactory(new ClassPathResource("step2.xml", getClass()));
        B b = (B) beanFactory.getBean("B", B.class);
        assertEquals(b.name, "Yoonsung");
        assertEquals(b.age.intValue(), 29);
        b.name = "Java";
        b.age = 22;

        B reloadedB = (B) beanFactory.getBean("B", B.class);
        assertEquals(reloadedB.name, "Java");
        assertEquals(reloadedB.age.intValue(), 22);
    }

    @Test
    public void invokeMethodAfterInitializingBean() {
        ArahanXmlBeanFactory xmlBeanFactory = new ArahanXmlBeanFactory(new ClassPathResource("step4.xml", getClass()));
        C c = (C) xmlBeanFactory.getBean("C", C.class);
        assertEquals(c.flag, true);
        xmlBeanFactory.initializingBeans();
    }

    @Test
    public void hierarchicalBeanFactory() {
        ArahanXmlBeanFactory parentFactory = new ArahanXmlBeanFactory(new ClassPathResource("step4_parent.xml", getClass()));
        ArahanXmlBeanFactory childFactory = new ArahanXmlBeanFactory(new ClassPathResource("step4_child.xml", getClass()), parentFactory);

        assertNotNull(childFactory.getBean("A"));
        assertNotNull(childFactory.getBean("B"));
        assertEquals(childFactory.getBean("B"), parentFactory.getBean("B"));

        B b = (B) childFactory.getBean("B", B.class);
        assertEquals(b.name, "Yoonsung");
        assertEquals(b.age.intValue(), 29);
    }
}
