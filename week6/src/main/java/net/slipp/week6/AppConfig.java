package net.slipp.week6;

import net.slipp.week6.member.CurrentMember;
import net.slipp.week6.member.Member;
import net.slipp.week6.member.NewMember;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by woojin on 2016. 4. 16..
 */
@Configuration
public class AppConfig {

	@Bean
	public Member currentMember() {
		return new CurrentMember();
	}

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	//			proxyMode = ScopedProxyMode.INTERFACES)
	public Member newMember() {
		return new NewMember();
	}
}
