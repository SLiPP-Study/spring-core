package slipp.yoonsung;

import org.springframework.core.io.ClassPathResource;
import arahansa.ArahanXmlBeanFactory;
import arahansa.bean.B;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by arahansa on 2016-03-02.
 */
public class Step03 {

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
}
