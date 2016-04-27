package arahansa.bean;

import org.springframework.beans.factory.InitializingBean;

/**
 * Created by arahansa on 2016-03-02.
 */
public class E  implements InitializingBean {

    public long createdAt;

    @Override
    public void afterPropertiesSet() {
        createdAt = System.currentTimeMillis();
        System.out.println(getClass().getName() + " is created at " + createdAt);
    }
}
