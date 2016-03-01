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


    /**
     * 과제1)
     * ## InitializingBean과 BeanPostProcessor의 차이점
     * =>
     * bean의 생성 시점 전,후로 특별한 처리를 한다는 공통점은 있으나 처리 로직을 누가 가지고 있는가에 차이점이 있습니다.
     *
     * InitializingBean은 각 bean에서 구현하기 때문에 bean 생성 시 처리 로직을 각 bean 클래스에서 가집니다.
     * 다시 말해서 bean 클래스 관점에서 본인(?) 객체가 생성될 때 처리해야 할 로직을 작성하는 것입니다.
     *
     * 반면 BeanPostProcessor는 BeanFactory 관점에서 본인이 처리해야 하는 bean 들이 생성될 때 처리해야 할 로직을 작성합니다.
     * @link {https://github.com/spring-projects/spring-framework/blob/d5ee787e1e6653257720afe31ee3f8819cd4605c/spring-beans/src/main/java/org/springframework/beans/factory/config/ConfigurableBeanFactory.java#L230}
     * 위 링크는 ConfigurableBeanFactory interface의 addBeanPostProcessor 메소드 명세입니다.
     *
     * @link {https://github.com/spring-projects/spring-framework/blob/f6ebc4ce977e49d800b5ea5332dd4b5934eb0706/spring-context/src/main/java/org/springframework/context/support/AbstractApplicationContext.java#L631}
     * 위 링크는 실제 BeanFactory의 addBeanPostProcessor 메소드를 호출하여 BeanPostProcessor를 등록하는 코드입니다.
     * 위 메소드로 등록한 BeanPostProcessor들은 아래 멤버 변수에 담깁니다.
     * @link {https://github.com/spring-projects/spring-framework/blob/04f31816bde5d5fa27f44ef8fb746ff2572c5287/spring-beans/src/main/java/org/springframework/beans/factory/support/AbstractBeanFactory.java#L148}
     *
     * List에 담긴 BeanPostProcessor들이 실제 사용되는 곳은 아래 링크에 잘 나와있습니다.
     * @link {https://github.com/spring-projects/spring-framework/blob/4b2ce60d65fb09717d016e71552a4f259e4bdc0d/spring-beans/src/main/java/org/springframework/beans/factory/support/AbstractAutowireCapableBeanFactory.java#L403}
     * 코드를 보시면 List를 for loop 돌면서 하나의 bean 객체를 chain 형식으로 처리하고 마지막까지 처리된 bean을 다시 리턴하는 것을 볼 수 있습니다.
     *
     *
     * 결론적으로, BeanFactory 관점에서 생성하는 모든 bean 객체에 대해서 공통적으로 객체 생성 전,후 시점으로 처리하려는 로직은 BeanPostProcessor가 담당하며,
     * 각 bean 별로 개별적으로 객체 생성 전 시점에 처리하려는 로직은 InitializingBean 인터페이스를 구현하여 로직을 작성한다고 생각됩니다.
     *
     * 여기에 추가적으로 InitializingBean과 BeanPostProcessor이 모두 적용된 bean이 있을 경우 호출 우선순위가 궁금해져서 알아봤습니다.
     * @link {https://github.com/spring-projects/spring-framework/blob/4b2ce60d65fb09717d016e71552a4f259e4bdc0d/spring-beans/src/main/java/org/springframework/beans/factory/support/AbstractAutowireCapableBeanFactory.java#L1570}
     * 위 링크는 AbstractAutowireCapableBeanFactory 클래스의 createBean 메소드의 중간 부분입니다.
     * 1570번째 줄을 보면 applyBeanPostProcessorsBeforeInitialization 메소드를 호출합니다. 이 메소드는 BeanFactory에 등록된 BeanPostProcessor List loop를 돌면서 postProcessBeforeInitialization 메소드를 호출합니다.
     * 그리고 1574번째 줄에서 invokeInitMethods 메소드를 호출합니다. 이 메소드에서는 현재 bean이 InitializingBean을 구현했는지 여부를 판별하고 구현했을 경우 afterPropertiesSet 메소드를 호출합니다.
     * 그리고 1583번째 줄에서 applyBeanPostProcessorsAfterInitialization 메소드를 호출합니다. 이 메소드는 BeanFactory에 등록된 BeanPostProcessor List loop를 돌면서 postProcessAfterInitialization 메소드를 호출합니다.
     * 즉, postProcessBeforeInitialization -> afterPropertiesSet -> postProcessAfterInitialization 순서입니다.
     */
}