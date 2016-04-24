package bean;

import beans.factory.BeanNameAware;

public class Jyp implements SpringCoreMember, BeanNameAware {

    private String beanName;

    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    public String getName() {
        return "박진영";
    }
}
