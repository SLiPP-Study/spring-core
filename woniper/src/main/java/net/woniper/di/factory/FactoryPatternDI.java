package net.woniper.di.factory;

import net.woniper.di.common.AccountService;
import net.woniper.di.common.GmailSenderImpl;
import net.woniper.di.common.MailSenderImpl;
import net.woniper.di.factory.type.MailType;

/**
 * Created by woniper on 2016. 1. 23..
 */
public class FactoryPatternDI {

    private static FactoryPatternDI factory;

    private FactoryPatternDI() {}

    public static FactoryPatternDI getInstance() {
        if(factory == null)
            factory = new FactoryPatternDI();

        return factory;
    }

    public AccountService create(MailType type) {
        switch (type) {
            case GMAIL:
                return new AccountService(new GmailSenderImpl());

            case MAIL:
                return new AccountService(new MailSenderImpl());

            default:
                throw new RuntimeException();
        }
    }

}
