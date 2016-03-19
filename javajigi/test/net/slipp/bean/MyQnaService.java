package net.slipp.bean;

import spring.stereotype.Inject;
import spring.stereotype.Service;

@Service
public class MyQnaService {
	private UserRepository userRepository;
	private QuestionRepository questionRepository;

	@Inject
	public MyQnaService(UserRepository userRepository, QuestionRepository questionRepository) {
		this.userRepository = userRepository;
		this.questionRepository = questionRepository;
	}
	
	public UserRepository getUserRepository() {
		return userRepository;
	}
	
	public QuestionRepository getQuestionRepository() {
		return questionRepository;
	}
}
