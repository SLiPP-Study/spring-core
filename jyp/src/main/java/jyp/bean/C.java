package jyp.bean;

import jyp.InitializingBean;

public class C implements InitializingBean {
    public boolean flag = false;

    @Override
    public void afterPropertiesSet() {
        flag = true;
    }
}
