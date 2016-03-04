package step6;

import bean.*;
import org.junit.Test;
import java.io.InputStream;
import static org.junit.Assert.*;

public class Step6Test {
    /**
     * step5에서 DefaultListableBeanFactory를 추가하다보니
     * 자연스럽게 AbstractBeanFactory에서 가지고 있던
     * BeanDefinition과 Bean에 대한 책임이 분리되었다.
     *
     * - AbstractBeanFactory의 역할 :  BeanDefinition을 토대로 실제 bean을 생성, 관리하는 역할을 하고 있고,
     *                               BeanDefinition 정보를 가져오는 역할은 구현클래스에게 위임하고 있다. (Template Method Pattern)
     *
     * - AbstractBeanFactory 구현체의 역할 :  AbstractBeanFactory를 상속받아 getBeanDefinition 메서드를 정의해서
     *                                    name에 맞는 BeanDefinition 객체를 반환한다.
     *
     * 하지만 상식적으로 생각해볼때 AbstractBeanFactory 구현체가
     * 실제로 이름을 기준으로 BeanDefinition 객체를 반환하기 위해서는 (getBeanDefinition(String name))
     * 먼저 BeanDefinition을 읽어들이고, 등록하는 과정이 필요할 것이다.
     *
     * 그렇다면 이러한 메서드를 AbstractBeanFactory에 같은 방식으로 선언해 (Template Method Pattern)
     * 구현 클래스에게 제약을 가해야 할까?
     *
     * (주의. 앞으로 나오는 내용들은 주관적인 의견입니다)
     * 프로그래밍에서 모듈화의 이유는 프로그램의 복잡도를 줄이고
     * 소프트웨어가 변화에 효과적이고 유연하게 대처하기 위해서이다.
     *
     * 우리가 객체지향적으로 프로그래밍할때 모든 단위에서 (메서드, 클래스, 인터페이스)
     * 하나의 책임을 가지고 있도록 프로그래밍 하는 것을 권장하는 것도 위의 이유와 같다.
     * 우리는 이런 개념을 SRP (Single Responsibility Principle)라고 부른다.
     * [참고링크 https://en.wikipedia.org/wiki/Single_responsibility_principle]
     *
     * 우리가 구현했던 AbstractBeanFactory와 그것을 구현하는 DefaultListableBeanFactory도
     * 단일책임을 가지기 위한 노력의 산물이기도 하다.
     *
     * 다시 코드로 돌아가 AbstractBeanFactory가 그 구현체를 바라볼때 기대하는 역할과 책임은 무엇일까?
     * 단순히 이름을 통해 BeanDefinition을 돌려주길 바랄뿐이다. 그 행동을 위해 어떤 과정이 필요한지는 관심가질 필요도 없다.
     *
     * 즉, AbstractBeanFactory가 BeanDefinition을 등록하고 읽어들이는 일까지 신경쓰게 된다면
     * 여러 관심사에 신경쓰게 되는 것이므로 구현클래스를 복잡하게 만들고 여러가지 제약을 강요하게 되므로
     * 구현 클래스 또한 다수의 관심사를 가지게 된다.
     *
     * 이런 문제를 스프링은 (스프링에 국한되는 내용이 아닌 자바 프로그래밍 전반에 해당한다. 이펙티브 자바의 규칙 16에서 비슷한 내용을 찾을 수 있다)
     * 클래스 상속이 아닌 조합과, 클래스와 조합대상을 같은 인터페이스로 구현하하여 조합대상에게 위임하는 방식으로 해결하고 있다.
     * 예를들면 다음과 같다.
     *
     * interface Role {
     *     void expectedAction();
     * }
     *
     * class A implement Role {
     *
     *     Role role = new B();
     *
     *     @Override
     *     public void expectedAction() {
     *         role.expectedAction();
     *     }
     * }
     *
     * class B implement Role {
     *     @Override
     *     public void expectedAction() {
     *         role.expectedAction();
     *     }
     * }
     *
     * 이번 step6에서는 클라이언트의 설정파일로 부터 BeanDefinition을 읽어들이는 과정과
     * BeanDefinition을 등록하는 과정을 리팩토링한다.
     *
     *
     *
     *
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * 과제1) BeanDefinitionRegistry 인터페이스를 추가하고 제공할 API 메서드를 선언하자.
     *       그 역할을 누가 담당해야하는지 찾아서 구현까지 해보자.
     *
     * 먼저 BeanDefinition 등록과정에 리팩토링이 필요한 이유는 무엇일까?
     * 현재 BeanDefinition을 등록하는 역할은 DefaultListableBeanFactory에서 수행하고 있고
     * 선언된 메서드는 다음과 같다.
     * 1. getBeanDefinitionNames
     * 2. getBeanDefinitionCount
     * 3. preInstantiate
     * 4. registerBeanDefinition
     * 5. getBeanDefinition
     *
     * 여기서 1, 2는 ListableBeanFactory의 관심사이다.
     * (3번을 하나의 역할로 판단할 수 있을 것이다. Spring의 경우 "ConfigureListableBeanFactory 인터페이스에 정의하고 있는데, 그 이유는 아직까지 모르겠다. (알게되면 공유 부탁해요!))
     * 4번은 beanDefinition을 관리하고 있는 현재의 역할에서 적절해보이지만 DefaultListableBeanFactory라는 이름은 어울리지 않아보인다.
     * 차라리 DefaultListableBeanDefinitionFactory나 register라는 메소드 명에 따라 BeanDefinitionRegistry같은 네이밍이 어울린다.
     *
     * 그렇다면 이름을 중심으로 다시 한번 생각해보자.
     * DefaultListableBeanFactory는 AbstractBeanFactory를 상속받고 있다.
     * 상속은 has a 관계가 아니라 is a 관계이다. 그러므로 BeanFactory라는 naming이 자연스럽기도 하다.
     *
     * 결론적으로 registerBeanDefinition은 DefaultListableBeanFactory와 AbstractBeanFactory 양쪽 어디에도 속할 이유가 없어보인다.
     * 그렇다면 빈정보를 등록하는 역할을 어떻게 나타낼 것인가? 상속아 아닌 조합(구현)을 사용하면 된다.
     *
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * 과제2) BeanDefinitionReader 인터페이스를 추가하고 제공할 API 메서드를 선언한다.
     *       앞선 과제에서 추가한 BeanDefinitionRegister와 새로 정의한 BeanDefinitionReader가 DI를 통해 동작하도록 한다.
     *
     * 조금 늦은것 같지만 step1부터 만들어온 XmlBeanFactory를 다시 살펴보자.
     * 잘 만들어진 것일까? 먼저 선언된 메서드를 살펴보자.
     *
     * 1. 파라미터 시그니처가 다른 다수의 loadBeanDefinitions
     * 2. loadBeanDefinition
     * 3. createBeanDefinition
     * 4. createPropertyValues
     * 5. createPropertyValue, getValue
     *
     * 생성자에서 1번을 호출하고 나머지 2, 3, 4, 5번은 1번을 보조하는 메서드들이다.
     * 1번에 메서드에서 최종적으로 호출되는 loadBeanDefinition은 for루프를 돌며 registerBeanDefinition을 호출하고 있고,
     * 상위 클래스에 선언된 메서드들이 순차적으로 실행된다.
     *
     * 이 모든 메서드들이 XmlBeanFactory의 역할일까?
     * BeanFactory는 말 그대로 bean을 생성하는 공장이다. 물론 BeanDefinition이 Bean을 만들기 위한 주요한 정보이지만
     * Xml을 파싱하고 읽어들이는 일을 담당하는게 조금 어색해 보이기도 한다.
     * 과제 1에서 만든 BeanDefinitionRegister 역할처럼 분리하는게 합당하지 않을까?
     *
     * BeanDefinitionLoader 혹은 BeanDefinitionReader와 같은 역할로 나눌 수 있지 않을까?
     *
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * 과제3)
     * 2번 과제까지 수행하고 나면 XmlBeanFactory는 XmlBeanDefinitionReader에게
     * BeanDefinition등록을 위임하고 있다.
     * 앞서 1번 과제에서는 BeanDefinitionRegistry의 역할을 implements를 통해 직접하고 있는데
     * 2번에서는 왜 위임을 하는 것일까?
     * 그냥 XmlBeanFactory가 BeanDefinitionReader의 역할을 implements하면 안될까?
     *
     * 과제 1번 방식과 과제 2번 방식의 차이는 무엇일까?
     */


    /**
     * = step1부터 step5까지의 아래 테스트코드가 모두 동작하도록 한다.
     */

    @Test
    public void successIfBeanInitializedAtFirst() throws InterruptedException {
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step5.xml"));
        D d = xmlBeanFactory.getBean("D", D.class);
        Thread.sleep(2000);
        E e = xmlBeanFactory.getBean("E", E.class);

        assertTrue(Math.abs(d.createdAt - e.createdAt) < 2000);
    }


    //Step4
    @Test
    public void invokeMethodAfterInitializingBean() {
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step4.xml"));
        C c = xmlBeanFactory.getBean("C", C.class);
        assertTrue(c.flag);
    }

    //Step4
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

    //Step2,3
    @Test
    public void registerBeanWithProperty() {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("step2.xml");
        XmlBeanFactory beanFactory = new XmlBeanFactory(inputStream);
        B b = beanFactory.getBean("B", B.class);
        assertEquals(b.name, "Yoonsung");
        assertEquals(b.age.intValue(), 29);
        b.name = "Java";
        b.age = 22;

        B reloadedB = beanFactory.getBean("B", B.class);
        assertEquals(reloadedB.name, "Java");
        assertEquals(reloadedB.age.intValue(), 22);
    }

    //Step1
    @Test
    public void registerObjectAsSingleTon() {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("step1.xml");
        XmlBeanFactory beanFactory = new XmlBeanFactory(inputStream);
        A a = beanFactory.getBean("A", A.class);
        assertEquals(a.name, "A");
        a.name = "AA";

        A reloadedA = beanFactory.getBean("A", A.class);
        assertEquals(reloadedA.name, "AA");
    }
}