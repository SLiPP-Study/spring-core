import bean.IndependentBean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hotjoyit on 16. 3. 12.
 */
public class LifecycleTestDrive {

  public static void main(String[] args) {
    AbstractApplicationContext ctx = new ClassPathXmlApplicationContext
        ("classpath:spring-config.xml");

    IndependentBean independentBean = ctx.getBean("independentBean", IndependentBean.class);
    System.out.println(independentBean.getSecret());
  }
}
