package net.woniper.spring.core.context;

import net.woniper.spring.core.beans.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by woniper on 2016. 1. 16..
 */
@Configuration
public class ACConfig {

    @Bean
    @Scope("singleton")
    public Account singletonAccount() {
        return new Account("Singleton");
    }

    @Bean
    @Scope("prototype")
    public Account prototypeAccount() {
        return new Account("Prototype");
    }
}
