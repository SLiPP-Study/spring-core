package net.slipp.week6.service;

import net.slipp.week6.beans.Member;
import net.slipp.week6.beans.Outlier;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by woojin on 2016. 4. 20..
 */
@Service
public class MemberService implements ApplicationContextAware {
	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public enum SCOPE {SINGLETON, PROTOTYPE, REQUEST, SESSION}

	public Map<String, Object> getMemberMapByScopeInNonSingleton() {
		Map<String, Object> memberMap = new HashMap<String, Object>();

		memberMap.put("singleton", applicationContext.getBean("currentMember", Member.class));
		memberMap.put("prototype", applicationContext.getBean("newMember", Member.class));
		memberMap.put("request", applicationContext.getBean("memberByRequest", Member.class));
		memberMap.put("session", applicationContext.getBean("memberBySession", Member.class));
		memberMap.put("outlier", applicationContext.getBean("aloneMember", Outlier.class));
		return memberMap;
	}

	public Map<String, Object> getMemberMapByScopesInSingleton() {
		return applicationContext.getBean("memberMap", Map.class);
	}

	public Member getMember(SCOPE scope) {
		switch (scope) {
			case PROTOTYPE:
				return 	 applicationContext.getBean("newMember", Member.class);
			case REQUEST:
				return applicationContext.getBean("memberByRequest", Member.class);
			case SESSION:
				return applicationContext.getBean("memberBySession", Member.class);
			case SINGLETON:
			default:
				return applicationContext.getBean("currentMember", Member.class);
		}
	}

}
