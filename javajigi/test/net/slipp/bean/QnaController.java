package net.slipp.bean;

import spring.stereotype.Controller;
import spring.stereotype.Inject;

@Controller
public class QnaController {
	private MyQnaService qnaService;

	@Inject
	public QnaController(MyQnaService qnaService) {
		this.qnaService = qnaService;
	}
	
	public MyQnaService getQnaService() {
		return qnaService;
	}
}
