package jyp.spring.core;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 16.
 */
public class JYP implements InitializingBean {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.name = "KAKA";
    }
}
