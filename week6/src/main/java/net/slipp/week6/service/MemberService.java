package net.slipp.week6.service;

import net.slipp.week6.beans.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by woojin on 2016. 4. 20..
 */
@Service
public class MemberService {
	@Autowired
	@Qualifier(value = "session")
	private Member member;

	public Member getMember() {
		return member;
	}
}
