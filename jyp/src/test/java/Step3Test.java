import jyp.XmlBeanFactory;
import org.junit.Test;

/**
 * @author jinyoung.park89
 * @date 2016. 3. 30.
 */
public class Step3Test {

    // Todo: 순환참조 관계일때 stackoverflow 에러가 아니라 사전에 인지할 수 있도록
    @Test
    public void test_생성자순환참조() {
       XmlBeanFactory beanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step3.xml"));
    }
}
