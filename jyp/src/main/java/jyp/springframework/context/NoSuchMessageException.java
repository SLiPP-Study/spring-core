package jyp.springframework.context;

import java.util.Locale;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class NoSuchMessageException extends RuntimeException {
    public NoSuchMessageException(String code, Locale locale) {
        super("No message found under code '" + code + "' for locale '" + locale + ".");
    }

    public NoSuchMessageException(String code) {
        super("No Message found under code '" + code + "' for locale" + Locale.getDefault() + "'.");
    }
}
