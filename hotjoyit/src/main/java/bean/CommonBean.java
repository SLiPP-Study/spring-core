package bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

/**
 * Created by hotjoyit on 16. 3. 12.
 */
public abstract class CommonBean implements BeanNameAware, BeanClassLoaderAware,
    BeanFactoryAware, ResourceLoaderAware {

  public CommonBean() {
    System.out.println(String.format("@Construct : %s instantiated", getName()));
  }

  private String secret;

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
    System.out.println(String.format("@Setter : %s property is set", getName()));
  }

  public abstract String getName();

  @Override
  public void setBeanName(String name) {
    System.out.println(String.format("@BeanNameAware : %s setBeanName() is called", getName()));
  }

  @Override
  public void setBeanClassLoader(ClassLoader classLoader) {
    System.out.println(String.format("@BeanClassLoaderAware : %s setBeanClassLoader() is called", getName()));
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    System.out.println(String.format("@BeanFactoryAware : %s setBeanFactory() is called", getName()));
  }

  @Override
  public void setResourceLoader(ResourceLoader resourceLoader) {
    System.out.println(String.format("@ResourceLoaderAware : %s setResourceLoader() is called", getName()));
  }
}
