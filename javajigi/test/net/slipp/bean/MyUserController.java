package net.slipp.bean;

import spring.stereotype.Controller;
import spring.stereotype.Inject;

@Controller
public class MyUserController {
	@Inject
	private MyQnaService qnaService;
	
	private MyUserService userService;
	
	@Inject
	public void setUserService(MyUserService userService) {
		this.userService = userService;
	}
	
	public MyUserService getUserService() {
		return userService;
	}
}
