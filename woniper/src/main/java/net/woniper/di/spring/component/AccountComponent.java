package net.woniper.di.spring.component;

import org.springframework.stereotype.Component;

/**
 * Created by woniper on 2016. 1. 30..
 */
@Component
/**
 * @Service
 * @Repository
 * @Controller
 */
public class AccountComponent {
    public void newAccount() {
        System.out.println("hello Component");
    }
}
