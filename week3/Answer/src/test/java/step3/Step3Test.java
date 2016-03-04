package step3;

import bean.B;
import org.junit.Test;
import java.io.InputStream;

import static org.junit.Assert.*;

public class Step3Test {
    /**
     * step2까지 진행해서 우리는 간단하게나마 XML 설정파일을 이용한 빈생성, 저장, 사용을 할 수 있었다.
     * 우리가 기대하는 기능에는 (Bean끼리의 Dependency 설정, 상속, alias 지정, scope 설정, 지연생성 등)
     * 한참 못미치지만 더 깊이있는 구현은 나중으로 미루고
     * 학습목표의 "현재의 복잡한 추상화"를 이해하기 위해
     * 이번 Step3에서는 리팩토링을 진행해본다.
     *
     * 초기 Step1에서의 XmlBeanFactory는 단순히 Object Bean를 저장하고 가져다 사용하는 기능을 수행했다
     * Step2에서는 Object Bean의 설정 데이터를 BeanDefinition 클래스의 형태로 변환하여 Property값을 XML에서 읽여들어 저장할 수 있게끔 구현했다.
     *
     * Step1 -> Step2로 추가된 내용은 간단하지만 벌써 코드라인수는 100 -> 200라인으로 늘었고
     * 객체지향 개발의 5대 원칙 중 많은 내용을 위반하고 있다.
     *
     * 물론 이 원칙이 항상 옳은것은 아니지만 2주차 스터디 시간에 이야기했던것처럼
     * XmlBeanFactory가 너무 많은 기능을 수행하고 있어서 유연하지 못하고
     * 너무 많은 책임을 가지고 있다.
     *
     * 이번 Step3에서는 XmlBeanFactory의 현재 기능목록을 살펴보고,
     * 확장성을 위해 어떤식의 추상화가 필요할지 살펴본 후
     * 실제 코드로 리팩토링을 진행한다
     *
     * 현재의 XmlBeanFactory의 기능을 크게 나누어 보면 다음과 같다.
     * 1. 클라이언트가 작성한 XML 인터페이스 설정파일을 읽어들인다
     * 2. 클라이언트가 작성한 XML 인터페이스 설정파일을 토대로 BeanDefinition객체를 생성한다.
     * 3. 생성된 BeanDefinition 설정 정보를 저장한다.
     * 4. 클라이언트의 요청에 따라 bean을 제공한다.
     *    만약에 생성된 bean이 없을 경우 저장된 BeanDefinition 객체를 읽어들여
     *    bean을 생성해서 전달한다.
     *
     *
     * XML을 인터페이스로 하는 BeanFactory가 아닌
     * Annotation 등 다른 인터페이스 설정을 통해 동작하는 BeanFactory를 추가한다면
     * 위 4가지 항목중 중복이 발생할 내용은 무엇을까?
     *
     * 1, 2번의 경우에는 Framework에서 지정한 설정을 각 인터페이스에 맞게끔 읽여들여야 하기 때문에
     * 추상화 하기 어렵지만 3, 4번은 모든 BeanFactory에서 중복된 기능을 구현할 가능성이 높다
     * 왜냐하면 Framework 내부적으로 설정하는 표준 인터페이스이기 때문이다.
     *
     *
     * 과제1)
     * BeanFactory기능을 하는 클래스들이 공통적으로 제공해야 할 API 기능은 무엇일까?
     * BeanFactory Interface를 통해 정의해보자.
     *
     * 과제2)
     * beanHash, beanDefinitionHash 필드를 어디서 참조하고 있는가?
     * 3, 4번의 기능을 AbstractBeanFactory로 추상화해보자.
     * AbstractBeanFactory를 추상클래스, 일반 클래스, 인터페이스 중 어떤방식으로 만드는게 좋을까?
     * 추상화 후 어떤 효과를 얻을 수 있을까?
     *
     */
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
}