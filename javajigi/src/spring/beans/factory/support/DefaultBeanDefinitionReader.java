package spring.beans.factory.support;

public class DefaultBeanDefinitionReader implements BeanDefinitionReader {
    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public DefaultBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void loadBeanDefinitions(String location) {
        // location에 해당하는 설정 파일을 읽은 BeanDefinition을 생성해 BeanDefinitionRegistry에 등록.
        // 테스트를 위해 하드코딩해 놓음
        beanDefinitionRegistry.registerBeanDefinition("firstBean", new DefaultBeanDefinition("net.slipp.bean.FirstBean"));
        beanDefinitionRegistry.registerBeanDefinition("secondBean", new DefaultBeanDefinition("net.slipp.bean.SecondBean"));
        beanDefinitionRegistry.registerBeanDefinition("thirdBean", new DefaultBeanDefinition("net.slipp.bean.ThirdBean"));
    }
}
