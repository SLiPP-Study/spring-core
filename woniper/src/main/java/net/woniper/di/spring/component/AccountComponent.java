package net.woniper.di.spring.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    // 동일한 Type(NotificationSender)인 Bean은 List로 주입 할 수 있다.
    @Autowired private List<NotificationSender> notificationSenders;

    // 생성자 주입 대상 Bean
    // 필드명은 Bean name과 같다.
    private NotificationSender gcm;

    // setter method(setNotificationSender) 주입 대상 Bean
    // 필드명은 Bean name과 같다.
    private NotificationSender sms;

    // Bean 객체는 반드시 기본 생성자가 필요
    public AccountComponent() {}

    @Autowired
    public AccountComponent(NotificationSender gcm) {
        this.gcm = gcm;
    }

    @Autowired
    public void setNotificationSender(NotificationSender sms) {
        this.sms = sms;
    }

    public void newAccount(String username) {
        System.out.println("hello " + username);
        notificationSenders.forEach(noti -> noti.send(username + "회원가입 완료!! [notifications]"));
        gcm.send(username + "회원가입 완료!! [gcm]");
        sms.send(username + "회원가입 완료!! [sms]");
    }
}
