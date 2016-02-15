package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] configLocations;

    public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
        this.configLocations = new String[] {configLocation};
        refresh();
    }

    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.configLocations = configLocations;
        refresh();
    }

    public ClassPathXmlApplicationContext(String[] configLocations, ApplicationContext parent) {
        super(parent);
        this.configLocations = configLocations;
        refresh();
    }

    protected String[] getConfigLocations() {
        return this.configLocations;
    }

}
