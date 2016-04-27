package slipp.yoonsung;

import org.springframework.core.io.ClassPathResource;
import arahansa.ArahanXmlBeanFactory;
import arahansa.bean.D;
import arahansa.bean.E;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by arahansa on 2016-03-02.
 */
public class Step05 {
    
    @Test
    public void eagerLoading() throws Exception{
        ArahanXmlBeanFactory xmlBeanFactory = new ArahanXmlBeanFactory(new ClassPathResource("step5.xml", getClass()));
        xmlBeanFactory.initializingBeans();
        D d = (D) xmlBeanFactory.getBean("D", D.class);
        Thread.sleep(2000);
        E e = (E) xmlBeanFactory.getBean("E", E.class);

        assertTrue(Math.abs(d.createdAt - e.createdAt) < 2000);

    }
    
}
