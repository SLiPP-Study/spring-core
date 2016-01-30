package net.woniper.di.factory;

import net.woniper.di.common.AccountService;
import net.woniper.di.factory.type.MailType;

/**
 * Created by woniper on 2016. 1. 23..
 */
public class Main {
    public static void main(String[] args) {
        /**
         * JavaDI와는 다르게 Main에서 의존이 생기지 않지만
         * MailType에 의존하게 되고, FactoryPatternDI.create()는
         * EmailSender 클래스가 늘어날 수록 분개문이 생성된다.
         */
        AccountService gmailAccount = FactoryPatternDI.getInstance().create(MailType.GMAIL);
        gmailAccount.newAccount("woniper");
    }
}
