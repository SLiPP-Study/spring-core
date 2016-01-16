package net.woniper.spring.core.test;

import net.woniper.spring.core.beans.Account;
import net.woniper.spring.core.context.ACConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;

/**
 * Created by woniper on 2016. 1. 16..
 */
public class ApplicationContextTests {

    @Test
    public void configTest() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(ACConfig.class);
//        ApplicationContext context = new AnnotationConfigApplicationContext(net.woniper.spring.core.context);

        Account singleton = context.getBean("singletonAccount", Account.class);
        Account prototype = context.getBean("prototypeAccount", Account.class);

        assertEquals("Singleton", singleton.getUsername());
        assertEquals("Prototype", prototype.getUsername());
    }

}
