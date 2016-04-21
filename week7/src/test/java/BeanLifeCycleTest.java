import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author jinyoung.park89
 * @since 2016. 4. 21.
 */
public class BeanLifeCycleTest {

    /**
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
     * 1. DisposableBean's destroy
     * 2. a custom destroy-method definition
     */

    /*private XmlBeanFactory beanFactory;
    private ApplicationContext applicationContext;
    private Jyp jyp;
    
    @Before
    public void setup() {
        this.beanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step5.xml"));
        this.jyp = beanFactory.getBean("jyp", Jyp.class);
    
        this.applicationContext = new ClassPathXmlApplicationContext("4.2.3.xml");
    }
    
    @Test
    public void test_applicationContext_getBean() {
        Service1 service1FromApplicationContext = this.applicationContext.getBean("service1", Service1.class);
        assertNotNull(service1FromApplicationContext);
    }
    
    *//**
      * Bean 생성
      */
    /*
    
    //1. BeanNameAware's setBeanName
    @Test
    public void test_BeanNameAware() {
     String beanName = jyp.getBeanName();
     assertEquals("jyp", beanName);
    }
    
    //2. BeanFactoryAware's setBeanFactory
    @Test
    public void test_BeanFactoryAware() {
     assertEquals(jyp.getBeanFactory(), beanFactory);
    }
    
    //3. ApplicationContextAware's setApplicationContext (only applicable if running in an application context)
    @Test
    public void test_ApplicationContextAware() {
    
    }
    
    //4. postProcessBeforeInitialization methods of BeanPostProcessors
    @Test
    public void test_PostProcessorBeforeInitialization() {
     String dummy = jyp.getDummy();
     assertEquals("jyp.dumdum", dummy);
    }
    
    //5. InitializingBean's afterPropertiesSet
    @Test
    public void test_InitializingBean() {
     Jyp jyp = this.applicationContext.getBean("jyp", Jyp.class);
     assertTrue(jyp.getInitialized());
    }
    
    *//**
      * Bean 종료
      *//*
        
        // 1. DisposableBean's destroy
        @Test
        public void test_DisposableBean() {
        }*/
}
