package beans;

import bean.SpringCoreMember;
import beans.factory.config.BeanPostProcessor;
import org.apache.log4j.Logger;

/**
 * @author jinyoung.park89
 * @since 2016. 4. 24.
 */
public class PrintSpringCoreMemberProcessor implements BeanPostProcessor {

    private Logger log = org.apache.log4j.Logger.getLogger(this.getClass().getName());

    @Override
    public Object postProcessorBeforeInitialization(Object bean, String name) {
        // bean이 SpringCoreMember 를 구현했으면 bean 이름을 출력한다.
        if (bean instanceof SpringCoreMember) {
            log.debug("SpringCoreMember before instance,  name: " + name);
        }
        return bean;
    }

    @Override
    public Object postProcessorAfterInitialization(Object bean, String name) {
        // bean이 SpringCoreMember 를 구현했으면 bean 이름을 출력한다.
        if (bean instanceof SpringCoreMember) {
            log.debug("SpringCoreMember after instance,  name: " + name);
        }
        return bean;
    }
}
