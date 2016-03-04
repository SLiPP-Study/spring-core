package jyp.springframework.context.event;

import jyp.springframework.context.ApplicationListener;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface ApplicationEventMulticaster extends ApplicationListener {

    void addApplicationListener(ApplicationListener listener);

    void removeApplicationListener(ApplicationListener listener);

    void removeAlllisteners();
}
