package jyp.springframework.context;

import java.util.Locale;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface MessageSource {

    String getMessage(String code, Object[] args, String defaultMessage, Locale locale);

    String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException;

    String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException;
}
