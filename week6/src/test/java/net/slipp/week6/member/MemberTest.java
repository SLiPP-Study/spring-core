package net.slipp.week6.member;

import net.slipp.week6.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Created by woojin on 2016. 4. 16..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class MemberTest {

	@Autowired
	private Member currentMember;

	@Test
	public void greetings() throws Exception {
		assertNotNull(currentMember);
		currentMember.greetings();
	}
}