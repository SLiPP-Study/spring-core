package net.slipp.week6.beans;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by woojin on 2016. 4. 16..
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
@ContextConfiguration(locations = {"classpath:applicationContext-bean.xml"})
public class MemberTest implements ApplicationContextAware {
	public static final String CURRENT_MEMBER = "currentMember";
	public static final String NEW_MEMBER = "newMember";

	@Autowired
	private Member currentMember;
	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Test
	public void greetings() throws Exception {
		assertNotNull(currentMember);
		currentMember.greetings();
	}

	@Test
	public void 싱글톤_빈_확인_테스트() throws Exception {
		Member currentMember1 = applicationContext.getBean(CURRENT_MEMBER, Member.class);
		Member currentMember2 = applicationContext.getBean(CURRENT_MEMBER, Member.class);
		assertNotNull(currentMember1);
		assertNotNull(currentMember2);

		System.out.format("bean 1: %s, bean 2: %s\n", currentMember1, currentMember2);
		assertTrue(currentMember1 == currentMember2);
	}

	@Test
	public void 프로토타입_빈_확인_테스트() throws Exception {
		Member newMember1 = applicationContext.getBean(NEW_MEMBER, Member.class);
		Member newMember2 = applicationContext.getBean(NEW_MEMBER, Member.class);

		assertNotNull(newMember1);
		assertNotNull(newMember2);
		System.out.format("bean 1: %s, bean 2: %s\n", newMember1, newMember2);

		assertFalse(newMember1 == newMember2);
	}

}