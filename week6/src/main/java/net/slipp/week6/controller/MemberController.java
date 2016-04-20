package net.slipp.week6.controller;

import net.slipp.week6.beans.Member;
import net.slipp.week6.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by woojin on 2016. 4. 20..
 */
@Controller
public class MemberController {
	private MemberService memberService;

	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@RequestMapping(value = "/member/{scope}", method = RequestMethod.GET)
	@ResponseBody
	public Member getMember(@PathVariable MemberService.SCOPE scope) {
		return memberService.getMember(scope);
	}

	@RequestMapping("/members")
	public ModelAndView getMembersInNonSingleton() {
		Map<String, Object> memberMap = memberService.getMemberMapByScopeInNonSingleton();
		return new ModelAndView("memberList").addObject("members", memberMap)
				.addObject("title", "Check Object by scope In NonSingleton bean");
	}

	@RequestMapping("/members2")
	public ModelAndView member() {
		Map<String, Object> memberMap = memberService.getMemberMapByScopesInSingleton();
		return new ModelAndView("memberList").addObject("members", memberMap)
				.addObject("title", "Check Object by scope In "
						+ "Singleton bean");
	}
}
