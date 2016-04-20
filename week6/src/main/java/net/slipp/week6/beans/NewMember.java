package net.slipp.week6.beans;

/**
 * Created by woojin on 2016. 4. 16..
 */
public class NewMember implements Member {
	private String name;

	public NewMember(String name) {
		this.name = name;
	}

	public void greetings() {
		System.out.println("Slipp 스터디 뉴비입니다");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
