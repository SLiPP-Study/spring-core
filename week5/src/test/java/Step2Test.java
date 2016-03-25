import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import step1.core.XmlBeanFactory;
import step2.bean.CoreTeam1;
import step2.bean.CoreTeam2;
import step2.bean.CoreTeam3;

public class Step2Test {

	private XmlBeanFactory beanFactory;
	
	@Before
	public void setup() {
		beanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step2.xml"));
	}

	@Test
	public void test1() {
		CoreTeam1 coreTeam1 = (CoreTeam1) beanFactory.getBean("coreTeam1");
		CoreTeam2 coreTeam2 = (CoreTeam2) beanFactory.getBean("coreTeam2");
		CoreTeam3 coreTeam3 = (CoreTeam3) beanFactory.getBean("coreTeam3");
		
		assertNotNull(coreTeam1);
		assertNotNull(coreTeam2);
		assertNotNull(coreTeam3);
	}

	public void test2() {
		CoreTeam1 coreTeam1 = (CoreTeam1) beanFactory.getBean("coreTeam1");
		CoreTeam2 coreTeam2 = (CoreTeam2) beanFactory.getBean("coreTeam2");
		CoreTeam3 coreTeam3 = (CoreTeam3) beanFactory.getBean("coreTeam3");
	}
	
	@After
	public void tearDown() {
		beanFactory.clear();
	}
}
