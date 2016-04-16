package net.slipp.week6;

import net.slipp.week6.member.Member;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by woojin on 2016. 4. 16..
 */
public class AppMain {
	private ApplicationContext applicationContext;

	public static void main(String... args) {
		new AppMain().runApp();
	}

	private void runApp() {
		applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

		Member currentMember = (Member) applicationContext.getBean("currentMember");
		currentMember.greetings();

		Member newMember = (Member) applicationContext.getBean("newMember");
		newMember.greetings();
	}

}
