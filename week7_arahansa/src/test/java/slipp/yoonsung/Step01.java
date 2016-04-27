package slipp.yoonsung;

import org.springframework.core.io.ClassPathResource;
import arahansa.ArahanXmlBeanFactory;
import arahansa.bean.A;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;
/**
 * Created by arahansa on 2016-03-02.
 */
public class Step01 {
    
    @Test
    public void xmlLoadTest() throws Exception{
        ArahanXmlBeanFactory beanFactory = new ArahanXmlBeanFactory(new ClassPathResource("step1.xml", getClass()));
        A a = (A) beanFactory.getBean("A", A.class);
        assertEquals(a.name, "A");
        a.name = "AA";

        A reloadedA = (A) beanFactory.getBean("A", A.class);
        assertEquals(reloadedA.name, "AA");
    }
}
