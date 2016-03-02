package step5;

import java.util.Map;
import java.util.Set;

/**
 * @author jinyoung.park89
 * @since 2016-03-01
 */
public class DefaultListableBeanFactory extends AbstractBeanFactory {

    public DefaultListableBeanFactory(BeanFactory parentBeanFactory) {
        super(parentBeanFactory);
    }

    public void initBeans() {
        Map<String, BeanDefinition> beanDefinitionHash = getBeanDefinitionHash();
        Set<String> beanNames = beanDefinitionHash.keySet();
        if (beanNames.size() == 0) {
            return;
        }

        beanNames.forEach(this::getBean);
    }
}
