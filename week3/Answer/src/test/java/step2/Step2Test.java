package step2;

import bean.B;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

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