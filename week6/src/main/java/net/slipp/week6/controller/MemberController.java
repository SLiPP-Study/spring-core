package net.slipp.week6.controller;

import net.slipp.week6.beans.Member;
import net.slipp.week6.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping("/")
	public ModelAndView main() {
		return new ModelAndView("index");
	}

	@RequestMapping("/member")
	public ModelAndView member() {
		Member member = memberService.getMember();
		return new ModelAndView("memberList").addObject("member",member);
	}
}
