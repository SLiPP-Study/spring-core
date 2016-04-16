package net.slipp.week6.member;

import net.slipp.week6.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by woojin on 2016. 4. 16..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
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
		Member currentMember1 = (Member) applicationContext.getBean(CURRENT_MEMBER);
		Member currentMember2 = (Member) applicationContext.getBean(CURRENT_MEMBER);
		testBeanScopeType(currentMember1, currentMember2, true);
	}

	@Test
	public void 프로토타입_빈_확인_테스트() throws Exception {
		Member newMember1 = (Member) applicationContext.getBean(NEW_MEMBER);
		Member newMember2 = (Member) applicationContext.getBean(NEW_MEMBER);

		testBeanScopeType(newMember1, newMember2, false);
	}

	private void testBeanScopeType(final Member member1, final Member member2, final boolean isSingleton) {
		assertNotNull(member1);
		assertNotNull(member2);

		System.out.format("bean 1: %s, bean 2: %s\n", member1, member2);

		if (isSingleton) {
			assertEquals(member1, member2);
		} else {
			assertNotEquals(member1, member2);
		}
	}
}