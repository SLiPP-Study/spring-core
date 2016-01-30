package net.woniper.di.spring.config;

import net.woniper.di.common.AccountService;
import net.woniper.di.common.GmailSenderImpl;
import net.woniper.di.common.WMPMailSenderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by woniper on 2016. 1. 23..
 */
@Configuration
public class ApplicationConfig {

    @Bean(name = "gmail")
    public AccountService gmailAccount() {
        return new AccountService(new GmailSenderImpl());
    }

    @Bean(name = "wmpMail")
    public AccountService wmpMailAccount() {
        return new AccountService(new WMPMailSenderImpl());
    }

}
