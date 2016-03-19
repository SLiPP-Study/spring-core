package net.slipp.bean;

import spring.stereotype.Inject;
import spring.stereotype.Service;

@Service
public class MyUserService {
	@Inject
	private UserRepository userRepository;
	
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public UserRepository getUserRepository() {
		return this.userRepository;
	}
}
