package jyp.springframework.context;

import java.util.EventObject;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public abstract class ApplicationEvent extends EventObject {

    private long timestamp;

    public ApplicationEvent(Object source) {
        super(source);
        timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return this.timestamp;
    }

}
