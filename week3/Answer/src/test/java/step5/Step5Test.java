package step5;

import bean.D;
import bean.E;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Step5Test {
    /**
     * 지금까지 작성한 XmlBeanFactory는
     * 클라이언트에서 요청할때 bean을 담고있는 Map 필드에 접근해서
     * 초기화된 object가 있는지 확인하고 없으면
     * 새로 생성해서 Map에 저장한후 반환하는 형태로 코드가 구현되어 있다
     *
     * 이런 방식은 우리가 작성한 어플리케이션이 bean설정을 잘못하더라도 초기화시 잘 동작하고
     * 클라이언트가 요청할때 실제 object를 생성하므로 프로그램 런타임에 오류가 발생될 수 있다.
     *
     * 이러한 문제점을 해결하기 위해 Spring Container에서는
     * ListableBeanFactory라는 인터페이스로 해결하고 있는듯 하다.
     * 즉 ListableBean (리스트화 할 수 있는 bean) 목록을 통해
     * 일괄 초기화를 진행하도록 하는 것이다.
     *
     * 우리가 기대하듯이 대부분의 프로그램은 일괄 초기화가 필요하고
     * 그래서 DefaultListableBeanFactory가 대표적으로 사용된다고 볼 수 있다.
     * 위의 내용이 이해가지 않는다면 링크를 확인하기 바란다.
     * [https://slipp.net/wiki/pages/viewpage.action?pageId=25527577]
     *
     * 또한 현재 AbstractBeanFactory에서는 다음과 같이 두가지 역할을 모두하고 있다.
     * 1. 빈 정보인 BeanDefinition 객체관리
     * 2. 실제 Bean 객체 관리
     *
     * 이번 step에서는 일괄 초기화기능을 넣으며 AbstractBeanFactory를 리팩토링한다.
     * (힌트. AbstractBeanFactory가 가진 2가지 역할을 분리한다.)
     *
     * 과제1)
     * - ListableBeanFactory이라고 해서 꼭 일괄초기화 기능이 있어야 하는건 아니다.
     * - 이름 그대로 Listable한 bean 정보를 가져올 수 있으면 ListableBeanFactory라는 점이다.
     * - 그렇다면 일괄초기화 기능을 어디서 가지고 있어야 할지 생각해본다.
     *
     * 과제2)
     * ListableBeanFactory의 인터페이스를 추가하고,
     * 공개 API를 선언한다. 코드구현 후 Spring에서 실제로 어떻게 활용하고 있는지 확인한다.
     *
     * 과제3-1)
     * XmlBeanFactory와 AbstractBeanFactory사이에 어떤 관계로 설정되어야 할지 생각해본다.
     * [고민해볼 내용]
     * - XmlBeanFactory가 직접 ListableBeanFactory를 구현하면 되는가? 현재 bean 생성로직은 AbstractBeanFactory에 있다.
     * - AbstractBeanFactory가 ListableBeanFactory를 구현하면 되는가? AbstractBeanFactory의 기능을 제한하는건 아닐까?
     *   * 참고 : 2주차 시간에 왜 초기화 메서드를 만들지 않고, getBean 메서드를 통해 초기화 하는지 논의했던 내용을 떠올려본다.
     *
     * 과제3-2)
     * 현재의 코드를 확장해 XmlBeanFactory가 일괄 초기화기능을 수행하게끔 한다.
     * AbstractBeanFactory를 리팩토링하면서 DefaultListableBeanFactory를 구현한다.
     * 결과적으로 아래의 테스트코드가 성공할 수 있도록 한다.
     *
     * 힌트)
     * 1. AbstractBeanFactory는 역할분리를 위해 템플릿 메서드패턴이 필요하므로 abstact class가 된다.
     * 2. BeanDefinition의 정보를 관리하는 메서드가 어디서 사용되는 확인해본다.
     */
    @Test
    public void successIfBeanInitializedAtFirst() throws InterruptedException {
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step5.xml"));
        D d = xmlBeanFactory.getBean("D", D.class);
        Thread.sleep(2000);
        E e = xmlBeanFactory.getBean("E", E.class);

        assertTrue(Math.abs(d.createdAt - e.createdAt) < 2000);
    }
}