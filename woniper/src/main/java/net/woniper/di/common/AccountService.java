package net.woniper.di.common;

/**
 * Created by woniper on 2016. 1. 23..
 */
public class AccountService implements Initializing {

    private EmailSender emailSender;

    public AccountService() {
        emailSender = new GmailSenderImpl();
    }

    public AccountService(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void newAccount(String user) {
        System.out.println(user + "님이 회원가입 하셨습니다.");
        emailSender.sender(user + "회원가입 완료");
    }

    @Override
    public void afterInitializing() {
        System.out.println("초기화 메소드");
    }
}
