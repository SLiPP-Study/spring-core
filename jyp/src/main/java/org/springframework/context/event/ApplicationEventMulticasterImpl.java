package org.springframework.context.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 16.
 */
public class ApplicationEventMulticasterImpl implements ApplicationEventMulticaster {

    private Set eventListeners = new HashSet<>();

    public void addApplicationListener(ApplicationListener l) {
        this.eventListeners.add(l);
    }

    public void removeApplicationListener(ApplicationListener l) {
        this.eventListeners.remove(l);
    }

    public void onApplicationEvent(ApplicationEvent e) {
        Iterator i = this.eventListeners.iterator();
        while (i.hasNext()) {
            ApplicationListener l = (ApplicationListener) i.next();
            l.onApplicationEvent(e);
        }
    }

    public void removeAlllisteners() {
        this.eventListeners.clear();
    }

}
