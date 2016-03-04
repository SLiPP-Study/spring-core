package step1;

import bean.A;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

public class Step1Test {

    /**
     * Step1에서는 아주 간단한 Container를 만들어본다.
     * XML파일을 읽어서 object를 생성하고,
     * singleton으로 관리되는것을 확인한다
     *
     * 과제) 테스트가 통과할 수 있도록 구현한다.
     */
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