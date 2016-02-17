package org.springframework.context.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class StaticMessageSource extends AbstractMessageSource {

    private final Log logger = LogFactory.getLog(getClass());

    private Map messages = new HashMap<>();

    protected MessageFormat resolveCode(String code, Locale locale) {
        return (MessageFormat) this.messages.get(code + "_" + locale.toString());
    }

    public void addMessage(String code, Locale locale, String message) {
        this.messages.put(code + "_" + locale.toString(), new MessageFormat(message));
        logger.info("Added message");
    }

    public String toString() {
        return getClass().getName() + ": " + this.messages;
    }
}
