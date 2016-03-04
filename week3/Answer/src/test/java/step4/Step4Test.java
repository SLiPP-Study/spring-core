package step4;

import bean.B;
import bean.C;
import org.junit.Test;

import static org.junit.Assert.*;

public class Step4Test {
    /**
     * step3까지 진행해서 복잡하던 XmlBeanFactory는
     * Xml 파싱하는 영역을 제외하고 나머지 부분들이 AbstractBeanFactory로 이동했다.
     * 만약 Annotation 인터페이스를 통해 설정되는 AnnotationBeanFactory와 같은 클래스가 추가된다면
     * AbstractBeanFactory를 상속받는 클래스를 정의한뒤, Annotation을 파싱하여 BeanDefinition객체를 만드는 로직을 수행하도록 하면 된다.
     *
     * 아직 추상화가 충분하지 않다고 느낄 수 있으나, 당장 AnnotationBeanFactory와 같은 클래스를 추가하지 않을 것이므로
     * 이 정도에서 1차적으로 마무리한다.
     *
     * 이번 Step4에서는 두가지 기능을 추가할 것이다.
     *
     * 과제1)
     * 첫번재 기능은 클라이언트 사용자가 여러가지 이유로 빈 생성 후 동작할 후처리작업을 위한 설정을 추가할 수 있도록 한다.
     * InitializingBean 인터페이스와 1주차 Comment를 참고한다.
     * 또한 InitializingBean과 BeanPostProcessor의 차이점을 확인해보자.
     *
     * 과제2)
     * 두번째 기능은 지금까지는 하나의 BeanFactory가 동작하는 방식이였는데, 다중 BeanFactory가 서로 child-parent 관계로 동작할 수 있게 만든다.
     */
    @Test
    public void invokeMethodAfterInitializingBean() {
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step4.xml"));
        C c = xmlBeanFactory.getBean("C", C.class);
        assertEquals(c.flag, true);
    }

    @Test
    public void hierarchicalBeanFactory() {
        XmlBeanFactory parentFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step4_parent.xml"));
        XmlBeanFactory childFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step4_child.xml"), parentFactory);

        assertNotNull(childFactory.getBean("A"));
        assertEquals(childFactory.getBean("B"), parentFactory.getBean("B"));

        B b = childFactory.getBean("B", B.class);
        assertEquals(b.name, "Yoonsung");
        assertEquals(b.age.intValue(), 29);
    }
}