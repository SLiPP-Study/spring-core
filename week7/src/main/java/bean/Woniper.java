package bean;

import beans.factory.BeanFactory;
import beans.factory.BeanFactoryAware;

public class Woniper implements SpringCoreMember, BeanFactoryAware {

    private BeanFactory beanFactory;

    public String getName() {
        return "이경원";
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
