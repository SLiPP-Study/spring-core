import beans.factory.BeanCurrentlyInCreationException;
import beans.factory.xml.XmlBeanFactory;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * @author jinyoung.park89
 * @since 2016. 4. 21.
 */
public class Step1Test {

    /**
     * 본격적으로 7주차에 들어가기 전 잠시 쉬어가는(?) 곳입니다.
     * 현재까지 우리 스터디에서 구현한 코드에서는 생성자-순환참조가 걸려있는 경우 StackOverflowError가 나게 됩니다.
     * (아래의 test_생성자순환참조_StackOverflowError_에러가납니다() 테스트를 돌려보세요)
     * 실수로 이런식의 참조 관계를 설정하는 경우(step1.xml 참고) Spring 에서는 개발자들에게 어떻게 경고해주고 있을까요?
     *
     * 이번 step의 목표는 순환참조가 걸린 경우 StackOverflowError가 아닌 사전에 정의된 'BeanCurrentlyInCreationException' 예외를 내려주는 것입니다.
     * @see {http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/beans/factory/BeanCurrentlyInCreationException.html}
     *
     *
     * 현재 문제는 Service1을 생성하는 도중 Service2를 생성하고, Service2를 생성하려면 Service3이 필요한데 Service3이 생성되려면 Service1이 필요한 구조입니다.(step1.xml 참고)
     * Service1->Service2->Service3->Service1 (순환 참조, 환형 참조 라고 불립니다.)
     *
     * 문제 해결 방법은 다음과 같습니다.
     * 1. bean을 직접적으로 생성하기 전(createBean() 호출하기 전)에 '생성중'이라고 마킹해놓습니다.
     * 2. createBean()으로 bean 생성이 끝나면 '생성중' 마킹을 지웁니다.
     * 3. 1번 이전에 bean이 '생성중'이라고 마킹되어 있는지 체크하고 '생성중'일 경우 BeanCurrentlyInCreationException 예외를 발생시킵니다.
     *
     * 아래의 test_생성자순환참조() 테스트과 통과되도록 AbstractBeanFactory Class를 수정해보세요!!
     */

    @Test
    public void test_생성자순환참조_StackOverflowError_에러가납니다()
            throws NoSuchMethodException, InvocationTargetException {
        XmlBeanFactory beanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step1.xml"));
    }

    @Test(expected = BeanCurrentlyInCreationException.class)
    public void test_생성자순환참조() throws NoSuchMethodException, InvocationTargetException {
        test_생성자순환참조_StackOverflowError_에러가납니다();
    }
}
