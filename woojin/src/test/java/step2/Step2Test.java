package step2;

import bean.B;
import org.junit.Test;
import woojin.spring.core.XmlBeanFactory;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class Step2Test {
    /**
     * step1은 단순히 객체를 초기화 - 저장 - 사용 하는 용도의 기능밖에 수행하지 못한다
     * Bean Container는 복잡한 객체간의 연관관계를 맺어주고, 각각의 object가 가진 설정들을 Inject해주는 기능이 필요하다
     *
     * 또한, Container 내부에서 object를 제어하기 위해서는 object에 대한 정보가 필요한데
     * 이러한 설정을 담아둘 정형화된 역할이 필요하다.
     *
     * XML은 사용자가 이용하는 하나의 인터페이스일 뿐이지, 컨테이너가 관리하는 설정대상이라고 할 수 없다
     * 그러므로 우리는 Spring Container의 기능을 수행하기 위해서 BeanDefinition과 동일한 기능을 수행하는 클래스를 정의해서 활용한다
     *
     * 이번 단계에서는 간단하게 xml에 등록하는 object의 필드 데이터, property와 bean id, class 속성 데이터를 BeanDefinition객체를 활용해서 관리할 수 있도록 리팩토링한다.
     *
     * 과제1) 테스트가 모두 통과할 수 있도록 한다. 모든 메서드가 사용될 수 있도록 구현한다.
     * 과제2) BeanDefinition은 PropertyValues와 PropertyValue 객체를 사용하고 있다.
     * Key, Value를 저장하는 객체를 Map이 아니라 따로 구현한 이유는 무엇일까?
     */
    @Test
    public void registerBeanWithProperty() {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("homework/step2.xml");
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