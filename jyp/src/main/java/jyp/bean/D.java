package jyp.bean;

import jyp.InitializingBean;

public class D implements InitializingBean {
    public long createdAt;

    @Override
    public void afterPropertiesSet() {
        createdAt = System.currentTimeMillis();
        System.out.println(getClass().getName() + " is created at " + createdAt);
    }
}