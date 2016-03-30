import jyp.XmlBeanFactory;
import jyp.bean.D;
import jyp.bean.E;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author jinyoung.park89
 * @since 2016. 3. 30.
 */
public class Week5Test {

    @Test
    public void simpleTest() throws InterruptedException {
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step5.xml"));

        D d = xmlBeanFactory.getBean("D", D.class);
        Thread.sleep(2000);
        E e = xmlBeanFactory.getBean("E", E.class);

        assertTrue(Math.abs(d.createdAt - e.createdAt) < 2000);
    }
}
