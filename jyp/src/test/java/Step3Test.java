import jyp.XmlBeanFactory;
import jyp.beans.factory.BeanCurrentlyInCreationException;
import org.junit.Test;

/**
 * @author jinyoung.park89
 * @date 2016. 3. 30.
 */
public class Step3Test {

    @Test(expected = BeanCurrentlyInCreationException.class)
    public void test_생성자순환참조() {

        /**
         SEVERE: Context initialization failedorg.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'myService': Bean with name 'myService' has been injected into other beans [otherService] in its raw version as part of a circular reference, but has eventually been wrapped. This means that said other beans do not use the final version of the bean. This is often the result of over-eager type matching - consider using 'getBeanNamesOfType' with the 'allowEagerInit' flag turned off, for example.
        
        
        
         BeanCurrentlyInCreationException.java
        
         */

        XmlBeanFactory beanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step3.xml"));
    }
}
