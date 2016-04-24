package bean;

import beans.factory.InitializingBean;

public class Woojin implements SpringCoreMember, InitializingBean {

    private boolean initialized;

    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void afterPropertiesSet() {
        this.initialized = true;
    }

    public String getName() {
        return "조우진";
    }

}
