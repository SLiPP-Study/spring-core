package net.woniper.di.spring.config;

import net.woniper.di.common.AccountService;
import net.woniper.di.common.GmailSenderImpl;
import net.woniper.di.common.MailSenderImpl;
import net.woniper.di.spring.component.GcmSender;
import net.woniper.di.spring.component.NotificationSender;
import net.woniper.di.spring.component.SmsSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by woniper on 2016. 1. 23..
 */
@Configuration
@ComponentScan(basePackages = "net.woniper.di.spring.component")
public class ApplicationConfig {

    @Bean(name = "gmail")
    public AccountService gmailAccount() {
        return new AccountService(new GmailSenderImpl());
    }

    @Bean(name = "mail")
    public AccountService mailAccount() {
        return new AccountService(new MailSenderImpl());
    }

    @Bean(name = "gcm")
    public NotificationSender gcmSender() {
        return new GcmSender();
    }

    @Bean(name = "sms")
    public NotificationSender smsSender() {
        return new SmsSender();
    }
}
