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
     * 과제1) 테스트가 모두 통과할 수 있도록 한다. 모든 메서드가 사용될 수 있도록 구현한다.
     * 과제2) BeanDefinition은 PropertyValues와 PropertyValue 객체를 사용하고 있다.
     * Key, Value를 저장하는 객체를 Map이 아니라 따로 구현한 이유는 무엇일까?
     */
    @Test
    public void registerBeanWithProperty() {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("step2.xml");
        /*XmlBeanFactory beanFactory = new XmlBeanFactory(inputStream);*/
        XmlBeanFactory beanFactory = new XmlBeanFactory("step2.xml");
        B b = beanFactory.getBean("B", B.class);
        assertEquals(b.name, "Yoonsung");
        assertEquals(b.age.intValue(), 29);
        b.name = "Java";
        b.age = 22;

        B reloadedB = beanFactory.getBean("B", B.class);
        assertEquals(reloadedB.name, "Java");
        assertEquals(reloadedB.age.intValue(), 22);
    }

    /**
     * 과제1) 통과!
     *
     * 과제2)
     * 일단 PropertyValues 클래스를 사용하지 않고 XmlBeanFactory에서 List<PropertyValue>를 사용하게끔 구현해보았습니다.
     * 그리고 테스트 통과 후 Map으로도 한 번 구현해봐야지~ 하고 다시 수정하려고 보니 수정할데가 한두군데가 아니더군요!!
     *
     * 일단 BeanDefinition 클래스도 수정해야 하고 XmlBeanFactory 클래스도 수정해야 했습니다.
     * 단순히 List를 Map으로만 바꾸는 것이 아니라 XmlBeanFactory 클래스의 createPropertyValues 메소드에서는 List에 저장하던 코드 (propertyValues.add())를
     * map에 저장하기 위해 propertyValues.put()으로 바꿔야 했습니다. bean property를 관리하는 저장 객체만 하나 바꿨을 뿐인데 수정해야 할 곳이 너무 많았던 것입니다!!
     *
     *
     * 다음에는 PropertyValues 클래스에서 List를 사용하지 않고 Map을 사용하게끔 수정해보았습니다. (XmlBeanFactory는 다시 원복했습니다.)
     * 이때는 BeanDefinition, XmlBeanFactory 클래스는 전혀 수정할 필요가 없었습니다. 다만 PropertyValues 클래스 내부만 몇 줄 수정했을 뿐입니다.
     *
     * step2에서 PropertyValue는 xml에 선언하는 bean에 관한 정보를 담고 있는 클래스이며 PropertyValues는 이 bean 객체들을 담고 있는 그릇이라고 할 수 있습니다.
     * xmlBeanFactory는 bean의 생성과 bean 간의 관계 설정, bean 반환 등의 역할을 담당합니다.
     * 기능상으로만 보면 PropertyValue와 xmlBeanFactory는 밀접한 관계를 맺고 있지만 중간에 인터페이스를 둠으로써 코드 상으로는 관계를 해소하는 것을 볼 수 있었습니다.
     *
     * (step2~5)에서는 PropertyValues가 구체적인 클래스로 나타나지만 실제 스프링 코드에서는 인터페이스인 것도 확인했습니다!!)
     * @link {https://github.com/spring-projects/spring-framework/blob/master/spring-beans/src/main/java/org/springframework/beans/PropertyValues.java}
     */
}