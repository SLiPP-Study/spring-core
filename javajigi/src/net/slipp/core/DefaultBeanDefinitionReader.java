package net.slipp.core;

import net.slipp.BeanDefinitionReader;
import net.slipp.BeanDefinitionRegistry;

public class DefaultBeanDefinitionReader implements BeanDefinitionReader {
    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public DefaultBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void loadBeanDefinitions(String location) {
        // load location file => create bean name, BeanDefintion
        beanDefinitionRegistry.registerBeanDefinition("bean1", new DefaultBeanDefinition("net.slipp.Bean1"));
        beanDefinitionRegistry.registerBeanDefinition("bean2", new DefaultBeanDefinition("net.slipp.Bean2"));
        beanDefinitionRegistry.registerBeanDefinition("bean3", new DefaultBeanDefinition("net.slipp.Bean3"));
    }
}
