package net.slipp.week6;

import net.slipp.week6.beans.Member;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by woojin on 2016. 4. 16..
 */
public class AppMain {
	private ApplicationContext applicationContext;

	public static void main(String... args) {
		//		new AppMain().runJavaConfigBasedApp();
		new AppMain().runXmlConfigBasedApp();
	}

	private void runXmlConfigBasedApp() {
		applicationContext = new ClassPathXmlApplicationContext("applicationContext-bean.xml");
		getMember();
	}

	private void runJavaConfigBasedApp() {
		applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

		getMember();
	}

	private void getMember() {
		Member currentMember = (Member) applicationContext.getBean("currentMember");
		currentMember.greetings();

		Member newMember1 = (Member) applicationContext.getBean("newMember");
		newMember1.greetings();

		Member newMember2 = (Member) applicationContext.getBean("newMember");
		newMember2.greetings();
	}

}
