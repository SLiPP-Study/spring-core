import beans.factory.xml.XmlBeanFactory;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * @author jinyoung.park89
 * @since 2016. 4. 21.
 */
public class Step3Test {

    /**
     * 본격적으로 7주차에 들어가기 전 잠시 쉬어가는(?) 곳입니다.
     * 현재까지 우리 스터디에서 구현한 코드에서는 생성자에 순환참조가 걸려있는 경우 StackOverflowError가 나게 됩니다.
     * (아래의 test_생성자순환참조_StackOverflowError_에러가납니다() 테스트를 돌려보세요)
     * 실수로 이런식의 참조 관계를 설정하는 경우 Spring 에서는 개발자들에게 어떻게 경고해주고 있을까요?
     *
     * 우리의 목표는 순환참조가 걸릴 경우 StackOverflowError가 아닌 사전에 정의된 'BeanCurrentlyInCreationException' 예외를 내려주는 것입니다.
     *
     */

    @Test
    public void test_생성자순환참조_StackOverflowError_에러가납니다()
            throws NoSuchMethodException, InvocationTargetException {
        XmlBeanFactory beanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step3.xml"));
    }

    /*@Test(expected = BeanCurrentlyInCreationException.class)
    public void test_생성자순환참조() throws NoSuchMethodException, InvocationTargetException {
        XmlBeanFactory beanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step3.xml"));
    }*/
}
