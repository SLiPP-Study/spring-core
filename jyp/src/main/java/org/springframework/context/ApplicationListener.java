package org.springframework.context;

import java.util.EventListener;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface ApplicationListener extends EventListener {

    void onApplicationEvent(ApplicationEvent e);
}
