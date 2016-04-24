import bean.*;
import beans.factory.xml.XmlBeanFactory;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * @author jinyoung.park89
 * @since 2016. 4. 22.
 */
public class Step2Test {

    /**
     * 이번 step에서는 Bean Life Cycle을 구현해보도록 하겠습니다.
     * 아래는 spring 1.0 버전에서 지원하는 Bean Life Cycle 목록입니다.
     *
     * spring-framework-1.0
     *
     * Bean factories are supposed to support the standard bean lifecycle interfaces
     * as far as possible. The maximum set of initialization methods and their standard
     * order is:
     * 1. BeanNameAware's setBeanName
     * 2. BeanFactoryAware's setBeanFactory
     * 3. ApplicationContextAware's setApplicationContext (only applicable if running
     * in an application context)
     * 4. postProcessBeforeInitialization methods of BeanPostProcessors<br>
     * 5. InitializingBean's afterPropertiesSet
     * 6. a custom init-method definition
     * 7. postProcessAfterInitialization methods of BeanPostProcessors
     *
     * On shutdown of a bean factory, the following lifecycle methods apply:<br>
     * 6. DisposableBean's destroy
     * 7. a custom destroy-method definition
     *
     *
     *
     * 이제 위 life cycle 들을 순서대로 구현해봅시다!!
     */

    private XmlBeanFactory beanFactory;

    @Before
    public void setUp() {
        beanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step2.xml"));
    }

    /**
     * Bean 생성 Life Cycle
     */

    //1. BeanNameAware's setBeanName
    /**
     * bean 에서 bean 이름값을 가지고 있어야 하는 경우 BeanNameAware 인터페이스를 구현한다.
     */
    @Test
    public void test_BeanNameAware() {
        // AbstractBeanFactory 클래스의 callLifecycleMethodsIfNecessary 메소드를 적절히 수정합니다.
        // Jyp 클래스는 BeanNameAware 인터페이스를 구현합니다. 내부에 beanName 이라는 변수를 갖고 있으며 인터페이스의 구현 메소드로부터(setBeanName) beanName을 전달받아 주입해줍니다.
        Jyp jyp = beanFactory.getBean("jyp", Jyp.class);
        // BeanNameAware 인터페이스의 구현 메소드로부터 'jyp'라는 bean name을 전달받아 내부 변수(beanName)에 주입했기 때문에 이 둘은 동일해야 합니다.
        assertEquals("jyp", jyp.getBeanName());
    }

    //2. BeanFactoryAware's setBeanFactory
    /**
     * bean 에서 해당 bean을 관리하는 BeanFactory 객체를 가지고 있어야 하는 경우 BeanFactoryAware 인터페이스를 구현한다.
     */
    @Test
    public void test_BeanFactoryAware() {
        // AbstractBeanFactory 클래스의 callLifecycleMethodsIfNecessary 메소드를 적절히 수정합니다.
        // Woniper 클래스는 BeanFactoryAware 인터페이스를 구현합니다. 내부에 beanFactory 라는 변수를 갖고 있으며 인터페이스의 구현 메소드로부터(setBeanFactory) beanFactory를 전달받아 주입해줍니다.
        Woniper woniper = beanFactory.getBean("woniper", Woniper.class);
        // BeanFactoryAware 인터페이스의 구현 메소드로부터 beanFactory를 전달받아 내부 변수(beanFactory)에 주입했기 때문에 이 둘은 동일해야 합니다.
        assertEquals(woniper.getBeanFactory(), beanFactory);
    }

    //3. ApplicationContextAware's setApplicationContext (only applicable if running in an application context)
    /**
     * skip
     */
    @Test
    public void test_ApplicationContextAware() {

    }

    //4. postProcessBeforeInitialization methods of BeanPostProcessors
    /**
     * 각 bean에 메소드를 추가해서 능동적으로 호출하는 방식이 아니라 BeanPostProcessor 인터페이스를 구현한 객체에 파라미터로 bean이 넘겨진다.
     * BeanPostProcessor 인터페이스를 구현한 객체 중 BeanFactory의 List<BeanPostProcessor> 에 추가된 것들이 호출된다.
     * 모든 bean들이 List<BeanPostProcessor>에 파라미터로 넘겨지면서 호출된다.
     */
    @Test
    public void test_PostProcessorBeforeInitialization() {
        // SpringCoreMember 인터페이스를 상속한 빈들의 경우 초기화 전,후로 bean 이름을 출력하게끔 하는 PrintSpringCoreMemberProcessor 클래스를 만들어놓았습니다.
        // AbstractBeanFactory 클래스의 beanPostProcessors 변수에 PrintSpringCoreMemberProcessor 클래스를 추가하세요. (DefaultListableBeanFactory 클래스 수정)
        // 그런 후에 AbstractBeanFactory 클래스의 callLifecycleMethodsIfNecessary 메소드를 적절히 수정한 후 테스트 클래스를 돌리면 SpringCoreMember 인터페이스를 구현한 클래스들이 출력될 겁니다. (콘솔창 확인)
    }

    //5. InitializingBean's afterPropertiesSet
    @Test
    public void test_InitializingBean() {
        // AbstractBeanFactory 클래스의 callLifecycleMethodsIfNecessary 메소드를 적절히 수정합니다.
        // Woojin 클래스는 InitializingBean 인터페이스를 구현합니다. 내부에 initialized 라는 변수를 갖고 있으며 구현 메소드에서(afterPropertiesSet) 이 변수를 'true'값으로 세팅해줍니다.
        Woojin woojin = this.beanFactory.getBean("woojin", Woojin.class);
        assertTrue(woojin.isInitialized()); // afterPropertiesSet 메소드에 의해 true 값으로 초기화되어야 합니다.
    }

    /**
     * Bean 종료 Life Cycle
     */

    // 6. DisposableBean's destroy
    @Test
    public void test_DisposableBean() throws Exception {
        // AbstractBeanFactory 클래스의 destroyBean 메소드를 적절히 수정합니다.
        // CoreTeam1 클래스는 DisposableBean 인터페이스를 구현합니다. 내부에 destroy 메소드를 갖고 있으며, CoreTeam1 클래스의 필드 멤버에 null 값으로 세팅해줍니다.
        CoreTeam1 coreTeam1 = this.beanFactory.getBean("coreTeam1", CoreTeam1.class);
        this.beanFactory.destroyBean("coreTeam1", coreTeam1); // beanFactory의 destroyBean 메소드 호출
        assertNull(coreTeam1.getJavajigi()); // CoreTeam1 클래스의 destroy 메소드가 호출되었다면 내부 변수는 null이 되어야 합니다.
    }
}
