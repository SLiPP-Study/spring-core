import junit.framework.Assert;
import jyp.XmlBeanFactory;
import jyp.bean.SpringCoreTeam;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author jinyoung.park89
 * @date 2016. 3. 31.
 */
public class Step4Test {

    // Todo: property에도 ref 지정 가능하도록
    @Test
    public void test_생성자순환참조() {
        XmlBeanFactory beanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step4.xml"));
        SpringCoreTeam coreTeam1 = (SpringCoreTeam) beanFactory.getBean("coreTeam1");

        String memberNames = coreTeam1.getMemberNames();
        assertEquals("박재성", memberNames);
    }
}
