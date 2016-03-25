package step2.bean;

import step1.bean.Javajigi;

public class CoreTeam1 implements SpringCoreTeam {

	private Javajigi javajigi;
	
	public CoreTeam1(Javajigi javajigi) {
		this.javajigi = javajigi;
	}

	@Override
	public int getMemberCount() {
		return 1;
	}

	@Override
	public String getMeberNames() {
		return javajigi.getName();
	}
}
