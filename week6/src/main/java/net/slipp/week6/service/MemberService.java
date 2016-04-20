package net.slipp.week6.service;

import net.slipp.week6.beans.Member;
import net.slipp.week6.beans.Outlier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by woojin on 2016. 4. 20..
 */
@Service
public class MemberService {
	@Autowired
	private Member currentMember;

	@Autowired
	private Member newMember;

	@Autowired
	private Member memberByRequest;

	@Autowired
	private Member memberBySession;

	@Autowired
	private Outlier aloneMember;

	@Resource
	private Map<String, Object> memberMap;

	public enum SCOPE {SINGLETON, PROTOTYPE, REQUEST, SESSION}

	public Map<String, Object> getMemberMapByScopeInNonSingleton() {
		Map<String, Object> memberMap = new HashMap<String, Object>();

		memberMap.put("singleton", currentMember);
		memberMap.put("prototype", newMember);
		memberMap.put("request", memberByRequest);
		memberMap.put("session", memberBySession);
		memberMap.put("outlier", aloneMember);
		return memberMap;
	}

	public Map<String, Object> getMemberMapByScopesInSingleton() {
		return memberMap;
	}

	public Member getMember(SCOPE scope) {
		switch (scope) {
			case PROTOTYPE:
				return newMember;
			case REQUEST:
				return memberByRequest;
			case SESSION:
				return memberBySession;
			case SINGLETON:
			default:
				return currentMember;
		}
	}

}
