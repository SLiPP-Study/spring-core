import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import bean.Arahansa;
import org.junit.Before;
import org.junit.Test;

import bean.Yoonsung;
import beans.factory.BeanFactory;
import beans.factory.xml.XmlBeanFactory;

/**
 * @author jinyoung.park89
 * @since 2016. 4. 24.
 */
public class Step3Test {

    /**
     * 이번 step 에서는 Bean Scope(singleton, prototype) 기능을 구현해보도록 하겠습니다.
     * Bean Scope 에는 singleton, prototype, request, session, global session 등이 있지만 이번 step 에서는 singleton, prototype 만 다룹니다.
     *
     * 현재까지 스터디에서 구현한 스프링 코드에서는 모든 bean을 singleton 으로 관리하고 있었습니다.
     * 여기에 추가로 prototype scope를 구현해보도록 하겠습니다.
     *
     *
     * 1. XmlBeanDefinitionReader 클래스를 수정하여 bean element의 scope 프로퍼티 처리
     *  => bean scope를 설정하는 방법은 xml 파일에서 <bean scope="prototype"></bean> or <bean scope="singleton"></bean> 와 같이 설정하는 것입니다.
     *     일단 xml 파일을 읽어서 BeanDefinition을 만드는 XmlBeanDefinitionReader 클래스를 수정해야 합니다.
     *
     * 2. 그리고 scope에 설정된 값에 따라서 (prototype or singleton) BeanDefinition 클래스의 scope 변수를 설정합니다.
     *
     * 3. AbstractBeanFactory 클래스에서 getBean 하는 부분 수정
     *  => beanDefinition의 singleton 변수를 읽어서 singleton 일때 처리와 prototype 일때 처리를 나눠서 합니다.
     */

    private BeanFactory beanFactory;

    @Before
    public void setUp() {
        beanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step3.xml"));
    }

    /**
     * 동일한 bean 을 두 번 가져와 동일한지 비교하는 테스트 코드입니다. 스프링에서는 scope에 관한 다른 설정을 하지 않은 경우 singleton으로 관리하기 때문에
     * 아래 테스트는 통과되어야 합니다.(따로 구현하지 않아도 테스트는 통과됩니다.)
     */
    @Test
    public void test_singleton() {
        Yoonsung yoonsung1 = this.beanFactory.getBean("yoonsung", Yoonsung.class);
        Yoonsung yoonsung2 = this.beanFactory.getBean("yoonsung", Yoonsung.class);

        assertEquals(yoonsung1, yoonsung2);
    }

    /**
     * arahansa bean은 scope="prototype"으로 설정된 bean 입니다. 따라서 동일한 bean name으로 두 번 가져올 경우 각각 다른 객체여야 합니다.
     */
    @Test
    public void test_prototype() {
        Arahansa arahansa1 = this.beanFactory.getBean("arahansa", Arahansa.class);
        Arahansa arahansa2 = this.beanFactory.getBean("arahansa", Arahansa.class);

        assertNotEquals(arahansa1, arahansa2);
    }
}
