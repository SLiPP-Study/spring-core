package net.slipp.week6.beans;

/**
 * Created by woojin on 2016. 4. 20..
 */
public class Outlier {
	private String name;

	public Outlier(String name) {
		this.name = name;
	}

	public void greetings() {
		System.out.println("혼자 놀아요~");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
