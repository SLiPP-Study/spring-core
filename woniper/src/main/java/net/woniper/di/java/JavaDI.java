package net.woniper.di.java;

import net.woniper.di.common.AccountService;
import net.woniper.di.common.MailSenderImpl;

/**
 * Created by woniper on 2016. 1. 23..
 */
public class JavaDI {
    public static void main(String[] args) {
        // default constructor는 GmailSender에 강한 의존
        AccountService gmailAccount = new AccountService();
        gmailAccount.newAccount("woniper");

        /**
         * setter method 또는 생성자 주입 사용은 AccountService와 EmailSender에 의존을 줄이지만
         * JavaDI 클래스가 주입하는 주체가 되기 때문에 JavaDI와 의존가 생긴다.
         */
        AccountService mailAccount = new AccountService();
        // mailAccount = new AccountService(new MailSenderImpl());
        mailAccount.setEmailSender(new MailSenderImpl());
        mailAccount.newAccount("woniper");
    }
}
