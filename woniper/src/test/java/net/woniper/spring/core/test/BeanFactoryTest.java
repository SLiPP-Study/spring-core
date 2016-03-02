package net.woniper.spring.core.test;

import net.woniper.obj.Account;
import net.woniper.spring.core.beans.factory.BeanFactory;
import net.woniper.spring.core.beans.factory.DefaultBeanFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by woniper on 2016. 2. 28..
 */
public class BeanFactoryTest {

    private final String packagePath = "net.woniper.di.common";

    @Test
    public void testName() throws Exception {
        BeanFactory beanFactory = new DefaultBeanFactory(packagePath);
        Account account1 = beanFactory.getBean("account", Account.class);
        Account account2 = beanFactory.getBean(Account.class);
        Assert.assertNotNull(account1);
        Assert.assertNotNull(account2);

        Assert.assertEquals(account1, account2);
    }
}
