package arahansa.bean;

import org.springframework.beans.factory.InitializingBean;

/**
 * Created by arahansa on 2016-03-02.
 */
public class C implements InitializingBean{

    public boolean flag = false;

    @Override
    public void afterPropertiesSet() {
        flag = true;
    }
}
