package net.slipp;

public interface ConfigurableListableBeanFactory extends BeanFactory {
    void preInstantiateSinglonetons();
}
