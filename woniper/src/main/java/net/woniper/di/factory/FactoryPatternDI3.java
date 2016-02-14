package net.woniper.di.factory;

import net.woniper.di.common.*;
import net.woniper.di.factory.type.MailType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by woniper on 2016. 2. 4..
 */
public class FactoryPatternDI3 {

    private static FactoryPatternDI3 instance;
    private static Map<MailType, AccountService> emailSenderMap = new HashMap<>(2);

    static {
        emailSenderMap.put(MailType.GMAIL, new AccountService(new GmailSenderImpl()));
        emailSenderMap.put(MailType.MAIL, new AccountService(new MailSenderImpl()));

        emailSenderMap.forEach((type, account) -> {
            if(account instanceof Initializing)
                ((Initializing)account).afterInitializing();

            if(account instanceof StringAware)
                ((StringAware)account).setString("이것은 StringAware 입니다.");
        });
    }

    private FactoryPatternDI3() {}

    public static FactoryPatternDI3 getInstance() {
        if(instance == null)
            instance = new FactoryPatternDI3();

        return instance;
    }

    public AccountService create(MailType type) {
        return emailSenderMap.get(type);
    }
}
