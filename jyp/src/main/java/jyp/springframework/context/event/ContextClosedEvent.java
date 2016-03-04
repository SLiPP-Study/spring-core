package jyp.springframework.context.event;

import jyp.springframework.context.ApplicationContext;
import jyp.springframework.context.ApplicationEvent;

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
