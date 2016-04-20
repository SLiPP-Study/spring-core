package net.slipp.week6.beans;

/**
 * Created by woojin on 2016. 4. 16..
 */
public class CurrentMember implements Member {
	private String name;

	public CurrentMember(String name) {
		this.name = name;
	}

	public void greetings() {
		System.out.println("기존 멤버에요~");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
