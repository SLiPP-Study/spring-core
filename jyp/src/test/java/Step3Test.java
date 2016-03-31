import jyp.XmlBeanFactory;
import org.junit.Test;

/**
 * @author jinyoung.park89
 * @date 2016. 3. 30.
 */
public class Step3Test {

    @Test
    public void test_생성자상호참조() {
       XmlBeanFactory beanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step3.xml"));
    }
}
