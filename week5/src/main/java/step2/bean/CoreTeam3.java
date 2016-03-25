package step2.bean;

import java.util.ArrayList;
import java.util.List;

import step1.bean.Javajigi;
import step1.bean.Jyp;
import step1.bean.SpringCoreMember;
import step1.bean.Woniper;

public class CoreTeam3 implements SpringCoreTeam {

	private List<SpringCoreMember> members = new ArrayList<SpringCoreMember>();
	
	public CoreTeam3(Javajigi javajigi, Jyp jyp, Woniper woniper) {
		members.add(javajigi);
		members.add(jyp);
		members.add(woniper);
	}

	@Override
	public int getMemberCount() {
		return members.size();
	}

	@Override
	public String getMeberNames() {
		
		String names = "";
		for (SpringCoreMember springCoreMember : members) {
			names += springCoreMember.getName() + " ";
		}
		
		return names;
	}
	
}
