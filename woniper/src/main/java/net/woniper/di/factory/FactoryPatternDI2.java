package net.woniper.di.factory;

import net.woniper.di.common.AccountService;
import net.woniper.di.common.GmailSenderImpl;
import net.woniper.di.common.MailSenderImpl;
import net.woniper.di.factory.type.MailType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by woniper on 2016. 2. 4..
 */
public class FactoryPatternDI2 {

    private static FactoryPatternDI2 instance;
    private static Map<MailType, AccountService> emailSenderMap = new HashMap<>(2);

    static {
        emailSenderMap.put(MailType.GMAIL, new AccountService(new GmailSenderImpl()));
        emailSenderMap.put(MailType.MAIL, new AccountService(new MailSenderImpl()));
    }

    private FactoryPatternDI2() {}

    public static FactoryPatternDI2 getInstance() {
        if(instance == null)
            instance = new FactoryPatternDI2();

        return instance;
    }

    public AccountService create(MailType type) {
        return emailSenderMap.get(type);
    }
}
