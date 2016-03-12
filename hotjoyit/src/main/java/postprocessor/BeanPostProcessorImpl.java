package postprocessor;

import bean.CommonBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by hotjoyit on 16. 3. 12.
 */
public class BeanPostProcessorImpl implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof CommonBean) {
      beanName = ((CommonBean) bean).getName();
    }
    System.out.println(
        String.format("@BeanPostProcessor : processing %s instance before initialization (before init lifecycle event)", beanName)
    );
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof CommonBean) {
      beanName = ((CommonBean) bean).getName();
    }
    System.out.println(
        String.format("@BeanPostProcessor : processing %s instance after initialization (just after init lifecycle event)", beanName)
    );
    return bean;
  }
}
