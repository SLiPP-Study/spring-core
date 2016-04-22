import bean.*;
import beans.factory.xml.XmlBeanFactory;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

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
        // Jyp 클래스는 BeanNameAware 인터페이스를 구현했습니다. 내부에 beanName 이라는 변수를 갖고 있으며 인터페이스의 구현 메소드로부터(setBeanName) beanName을 전달받아 주입해줍니다.
        Jyp jyp = beanFactory.getBean("jyp", Jyp.class);
        assertEquals("jyp", jyp.getBeanName());
    }

    //2. BeanFactoryAware's setBeanFactory
    /**
     * bean 에서 해당 bean을 관리하는 BeanFactory 객체를 가지고 있어야 하는 경우 BeanFactoryAware 인터페이스를 구현한다.
     */
    @Test
    public void test_BeanFactoryAware() {
        // Woniper 클래스는 BeanFactoryAware 인터페이스를 구현했습니다. 내부에 beanFactory 라는 변수를 갖고 있으며 인터페이스의 구현 메소드로부터(setBeanFactory) beanFactory를 전달받아 주입해줍니다.
        Woniper woniper = beanFactory.getBean("woniper", Woniper.class);
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
     * 각 bean들에 메소드를 추가해서 능동적으로 호출하는 방식이 아니라 BeanPostProcessor 인터페이스를 구현한 객체에 파라미터로 넘겨진다.
     * BeanPostProcessor 인터페이스를 구현한 객체 중 BeanFactory의 List<BeanPostProcessor> 에 추가된 것들이 호출된다.
     * 모든 bean들이 List<BeanPostProcessor>에 파라미터로 넘겨지면서 호출된다.
     */
    @Test
    public void test_PostProcessorBeforeInitialization() {

        CoreTeam1 coreTeam1 = this.beanFactory.getBean("coreTeam1", CoreTeam1.class);

        Javajigi javajigi = coreTeam1.getJavajigi();
        SpringCoreMember javajigi2 = coreTeam1.getJavajigi2();

        assertEquals(javajigi.getName(), javajigi2.getName());
    }

    //5. InitializingBean's afterPropertiesSet
    @Test
    public void test_InitializingBean() {
        /*Jyp jyp = this.beanFactory.getBean("jyp", Jyp.class);
        assertTrue(jyp.getInitialized());*/
    }

    /**
     * Bean 종료 Life Cycle
     */

    // 6. DisposableBean's destroy
    @Test
    public void test_DisposableBean() {
        this.beanFactory.clear();
    }
}
