package slipp.arahansa.xmltest;

import arahansa.ArahanXmlBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.ITestBean;
import org.springframework.beans.TestBean;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import slipp.yoonsung.DefaultListableBeanFactory;

import static org.junit.Assert.assertTrue;

/**
 * Created by jarvis on 2016. 4. 24..
 */
@Slf4j
public class XmlFactoryTest {

    // 싱글톤에 대한 레퍼런스를 테스트합니다.
    @Test
    public void testRefToSingleton() throws Exception {
        DefaultListableBeanFactory xbf = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(xbf);
        reader.setValidating(false);
        reader.loadBeanDefinitions(new ClassPathResource("reftypes.xml", getClass()));
        assertTrue("7 beans in reftypes, not " + xbf.getBeanDefinitionCount(), xbf.getBeanDefinitionCount() == 3);
        TestBean jen = (TestBean) xbf.getBean("jenny");
        log.debug("jen : {} ", jen);
        final ITestBean jennySpouse = jen.getSpouse();
        log.debug("jenSpouse: {} ", jennySpouse);
        TestBean dave = (TestBean) xbf.getBean("david");
        TestBean jenks = (TestBean) xbf.getBean("jenks");
        ITestBean davesJen = dave.getSpouse();
        ITestBean jenksJen = jenks.getSpouse();
        log.debug("davesJen : {} , jenksJen : {}" , davesJen, jenksJen);
        assertTrue("1 jen instance", davesJen == jenksJen);
        //assertTrue("1 jen instance", davesJen == jen);
    }

    @Test
    public void arahansaReftoSingleTon() throws Exception{
        log.info("arahansaReftoSingleTon");
        org.springframework.context.support.DefaultListableBeanFactory xbf = new org.springframework.context.support.DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(xbf);
        reader.setValidating(false);
        reader.loadBeanDefinitions(new ClassPathResource("reftypes.xml", getClass()));
        assertTrue("7 beans in reftypes, not " + xbf.getBeanDefinitionCount(), xbf.getBeanDefinitionCount() == 3);
        log.info("getBean을 시작합니다");
        TestBean jen = (TestBean) xbf.getBean("jenny");
        TestBean dave = (TestBean) xbf.getBean("david");
        TestBean jenks = (TestBean) xbf.getBean("jenks");
        ITestBean davesJen = dave.getSpouse();
        ITestBean jenksJen = jenks.getSpouse();
        log.debug("davesJen : {} , jenksJen : {}" , davesJen, jenksJen);
       //  assertTrue("1 jen instance", davesJen == jenksJen);
//        assertTrue("1 jen instance", davesJen == jen);
    }
}
