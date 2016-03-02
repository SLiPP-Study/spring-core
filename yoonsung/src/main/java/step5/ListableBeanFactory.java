package step5;

import java.util.Map;

/**
 * @author jinyoung.park89
 * @since 2016-03-01
 */
public interface ListableBeanFactory extends BeanFactory {

    Map<String, BeanDefinition> getBeanDefinitionHash();
}
