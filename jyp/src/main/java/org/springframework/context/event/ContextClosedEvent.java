package org.springframework.context.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class ContextClosedEvent extends ApplicationEvent {

    public ContextClosedEvent(ApplicationContext source) {
        super(source);
    }

    public ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
