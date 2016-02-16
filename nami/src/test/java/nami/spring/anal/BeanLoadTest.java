package nami.spring.anal;

import nami.spring.anal.service.DefaultService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanLoadTest {
    @Test
    public void configrationLoadTest() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MainConfig.class);
        ctx.refresh();

        DefaultService defaultService = ctx.getBean("myService", DefaultService.class);
        Assert.assertNotNull(defaultService);
    }

    @Test
    public void componentScanTest() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("nami.spring.anal.service");
        ctx.refresh();
        DefaultService defaultService = ctx.getBean(DefaultService.class);
        Assert.assertNotNull(defaultService);
    }
}
