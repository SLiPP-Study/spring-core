import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import jyp.XmlBeanFactory;
import jyp.bean.SpringCoreTeam;

/**
 * @author jinyoung.park89
 * @date 2016. 3. 31.
 */
public class Step4Test {

    // property에도 ref 지정 가능하도록
    @Test
    public void test_property_ref() {
        XmlBeanFactory beanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step4.xml"));
        SpringCoreTeam coreTeam1 = (SpringCoreTeam) beanFactory.getBean("coreTeam1");

        String memberNames = coreTeam1.getMemberNames();
        assertEquals("박재성", memberNames);
    }
}
