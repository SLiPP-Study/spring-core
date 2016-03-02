package net.woniper.spring.core.beans.supports;

import net.woniper.obj.Account;
import net.woniper.spring.core.beans.config.BeanDefinition;

/**
 * Created by woniper on 2016. 2. 28..
 */
public class BeanDefinitionReader {

    private BeanDefinitionRegistry beanDefinitionRegistry;

    public BeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void loadBeanDefinitions(String basePackage) {
        // 테스트를 위한 코드
        // 추후 package scan 코드 추가 예정
        beanDefinitionRegistry.registerBeanDefinition("account", new BeanDefinition("account", Account.class));
    }
}
