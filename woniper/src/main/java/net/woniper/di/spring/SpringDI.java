package net.woniper.di.spring;

import net.woniper.di.common.AccountService;
import net.woniper.di.spring.config.ApplicationConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by woniper on 2016. 1. 23..
 */
public class SpringDI {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        AccountService gmailAccount = context.getBean("gmail", AccountService.class);
        gmailAccount.newAccount("woniper");

        AccountService wmpMailAccount = context.getBean("wmpMail", AccountService.class);
        wmpMailAccount.newAccount("woniper");
    }
}
