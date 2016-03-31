import jyp.XmlBeanFactory;
import jyp.bean.CoreTeam1;
import jyp.bean.CoreTeam2;
import jyp.bean.CoreTeam3;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author jinyoung.park89
 * @date 2016. 3. 30.
 */
public class Step2Test {

    private XmlBeanFactory beanFactory;

    @Before
    public void setup() {
        beanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step2.xml"));
    }
    @Test
    public void test1() {
        CoreTeam1 coreTeam1 = (CoreTeam1) beanFactory.getBean("coreTeam1");
        CoreTeam2 coreTeam2 = (CoreTeam2) beanFactory.getBean("coreTeam2");
        CoreTeam3 coreTeam3 = (CoreTeam3) beanFactory.getBean("coreTeam3");

        assertNotNull(coreTeam1);
        assertNotNull(coreTeam2);
        assertNotNull(coreTeam3);
    }

    @Test
    public void test2() {
        CoreTeam1 coreTeam1 = (CoreTeam1) beanFactory.getBean("coreTeam1");
        CoreTeam2 coreTeam2 = (CoreTeam2) beanFactory.getBean("coreTeam2");
        CoreTeam3 coreTeam3 = (CoreTeam3) beanFactory.getBean("coreTeam3");

        assertEquals("박재성", coreTeam1.getMemberNames());
        assertEquals("박진영 이경원", coreTeam2.getMeberNames());
        assertEquals("박재성 박진영 이경원", coreTeam3.getMeberNames());
    }

    @After
    public void tearDown() {
        beanFactory.clear();
    }
}
