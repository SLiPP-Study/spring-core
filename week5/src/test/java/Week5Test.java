import static org.junit.Assert.*;

import org.junit.Test;

import core.XmlBeanFactory;

public class Week5Test {

	@Test
	public void step1Test1() {
		XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(ClassLoader.getSystemResourceAsStream("step1.xml"));
	}

}
