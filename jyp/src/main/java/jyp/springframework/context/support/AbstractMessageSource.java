package jyp.springframework.context.support;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jyp.springframework.context.HierarchicalMessageSource;
import jyp.springframework.context.MessageSource;
import jyp.springframework.context.MessageSourceResolvable;
import jyp.springframework.context.NoSuchMessageException;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 16.
 */
public abstract class AbstractMessageSource implements HierarchicalMessageSource {

    protected final Log logger = LogFactory.getLog(getClass());

    private MessageSource parentMessageSource;

    private boolean useCodeAsDefaultMessage = false;

    public void setParentMessageSource(MessageSource parent) {
        this.parentMessageSource = parent;
    }

    public MessageSource getParentMessageSource() {
        return this.parentMessageSource;
    }

    public void setUseCodeAsDefaultMessage(boolean useCodeAsDefaultMessage) {
        this.useCodeAsDefaultMessage = useCodeAsDefaultMessage;
    }

    public final String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        try {
            return getMessage(code, args, locale);
        } catch (NoSuchMessageException ex) {
            return defaultMessage;
        }
    }

    public final String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        try {
            return getMessageInternal(code, args, locale);
        } catch (NoSuchMessageException ex) {
            if (this.useCodeAsDefaultMessage) {
                return code;
            } else {
                throw ex;
            }
        }
    }

    public final String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        String[] codes = resolvable.getCodes();
        for (int i=0; i<codes.length; i++) {
            try {
                return getMessageInternal(codes[i], resolvable.getArguments(), locale);
            } catch (NoSuchMessageException ex) {

            }
        }

        if (resolvable.getDefaultMessage() != null) {
            return resolvable.getDefaultMessage();
        } else if (this.useCodeAsDefaultMessage && codes.length > 0) {
            return codes[0];
        } else {
            throw new NoSuchMessageException(codes[codes.length - 1], locale);
        }
    }

    protected String getMessageInternal(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        if (locale == null) {
            locale = Locale.getDefault();
        }

        MessageFormat messageFormat = resolveCode(code, locale);
        if (messageFormat != null) {
            return messageFormat.format(resolveArguments(args, locale));
        } else {
            if (this.parentMessageSource != null) {
                return this.parentMessageSource.getMessage(code, args, locale);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("Could not resolve message code [" + code + "] in locale [" + locale + "]");
                }
                throw new NoSuchMessageException(code, locale);
            }
        }
    }

    private Object[] resolveArguments(Object[] args, Locale locale) {
        if (args == null) {
            return new Object[0];
        }
        List resolvedArgs = new ArrayList<>();
        for (int i=0; i<args.length; i++) {
            if (args[i] instanceof MessageSourceResolvable) {
                resolvedArgs.add(getMessage((MessageSourceResolvable) args[i], locale));
            } else {
                resolvedArgs.add(args[i]);
            }
        }
        return resolvedArgs.toArray(new Object[resolvedArgs.size()]);
    }

    protected abstract MessageFormat resolveCode(String code, Locale locale);


}
