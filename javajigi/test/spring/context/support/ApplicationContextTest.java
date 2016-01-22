package spring.context.support;

import static org.junit.Assert.*;

import spring.context.ApplicationContext;
import net.slipp.bean.FirstBean;
import org.junit.Test;

public class ApplicationContextTest {
    @Test public void init() {
        ApplicationContext ac = new DefaultApplicationContext("net.slipp.bean");
        FirstBean firstBean = (FirstBean)ac.getBean("firstBean");
        assertNotNull(firstBean);
    }
}