package net.woniper.di.common;

/**
 * Created by woniper on 2016. 1. 23..
 */
public class GmailSenderImpl implements EmailSender {
    public void sender(String msg) {
        System.out.println("Gmail : " + msg);
    }
}
