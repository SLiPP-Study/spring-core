package net.woniper.di.spring.component;

/**
 * Created by woniper on 2016. 1. 31..
 */
public class SmsSender implements NotificationSender {
    @Override
    public void send(String msg) {
        System.out.println("sms : " + msg);
    }
}
