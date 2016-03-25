package di.beans.factory.support;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import di.beans.factory.support.DefaultBeanFactory;
import di.context.annotation.ClasspathBeanDefinitionScanner;
import net.slipp.bean.MyQnaService;
import net.slipp.bean.MyUserController;
import net.slipp.bean.MyUserService;
import net.slipp.bean.QnaController;

public class BeanFactoryTest {
	private DefaultBeanFactory beanFactory;

	@Before
	public void setup() {
		beanFactory = new DefaultBeanFactory();
		ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
		scanner.doScan("net.slipp.bean");
		beanFactory.preInstantiateSinglonetons();
	}

	@Test
	public void constructorDI() throws Exception {
		QnaController qnaController = beanFactory.getBean(QnaController.class);
		assertNotNull(qnaController);
		assertNotNull(qnaController.getQnaService());

		MyQnaService qnaService = qnaController.getQnaService();
		assertNotNull(qnaService.getUserRepository());
		assertNotNull(qnaService.getQuestionRepository());
	}

	@Test
	public void fieldDI() throws Exception {
		MyUserService userService = beanFactory.getBean(MyUserService.class);
		assertNotNull(userService);
		assertNotNull(userService.getUserRepository());
	}

	@Test
	public void setterDI() throws Exception {
		MyUserController userController = beanFactory.getBean(MyUserController.class);

		assertNotNull(userController);
		assertNotNull(userController.getUserService());
	}

	@After
	public void tearDown() {
		beanFactory.clear();
	}
}
