package net.slipp.custom.spring.context.test;

import net.slipp.custom.spring.bean.Community;
import net.slipp.custom.spring.bean.Member;
import net.slipp.custom.spring.config.Application;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by woniper on 2016. 5. 4..
 */
public class RelationApplicationContextTest {

    private AnnotationConfigApplicationContext parent;
    private ClassPathXmlApplicationContext child;

    @Before
    public void setUp() throws Exception {
        parent = new AnnotationConfigApplicationContext(Application.class);
        child = new ClassPathXmlApplicationContext();
        child.setParent(parent);
        child.setConfigLocation("classpath:child.xml");

        /**
         * refresh를 마지막에 해주어야 parent가 정상 적용된다.
         */
        child.refresh();
    }

    @Test
    public void test_parent에_등록한_빈을_child에서_찾기() throws Exception {
        Assert.assertNotNull(child.getBean("slipp", Community.class));
    }

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void test_child에_등록한_빈을_parent에서_찾기() throws Exception {
        parent.getBean("member", Member.class);
    }
}
