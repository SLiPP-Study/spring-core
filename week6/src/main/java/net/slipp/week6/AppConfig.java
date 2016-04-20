package net.slipp.week6;

import net.slipp.week6.beans.CurrentMember;
import net.slipp.week6.beans.Member;
import net.slipp.week6.beans.NewMember;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * Created by woojin on 2016. 4. 16..
 */
//@Configuration
public class AppConfig {

	@Bean
	public Member currentMember() {
		return new CurrentMember("이경원");
	}

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Member newMember() {
		return new NewMember("이시훈");
	}

}
