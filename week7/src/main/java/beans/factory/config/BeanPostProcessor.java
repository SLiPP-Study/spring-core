package beans.factory.config;

/**
 * @author jinyoung.park89
 * @since 2016. 4. 12.
 */
public interface BeanPostProcessor {

    Object postProcessorBeforeInitialization(Object bean, String name);

    Object postProcessorAfterInitialization(Object bean, String name);
}
