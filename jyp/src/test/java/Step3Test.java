import jyp.XmlBeanFactory;
import jyp.beans.factory.BeanCurrentlyInCreationException;
import org.junit.Test;

/**
 * @author jinyoung.park89
 * @date 2016. 3. 30.
 */
public class Step3Test {

    @Test(expected = BeanCurrentlyInCreationException.class)
    public void test_생성자순환참조() {
       XmlBeanFactory beanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step3.xml"));
    }
}
