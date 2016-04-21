package bean;

import java.util.ArrayList;
import java.util.List;

public class CoreTeam3 implements SpringCoreTeam {

    private List<SpringCoreMember> members = new ArrayList<SpringCoreMember>();

    public CoreTeam3(Javajigi javajigi, Jyp jyp, Woniper woniper) {
        members.add(javajigi);
        members.add(jyp);
        members.add(woniper);
    }

    public int getMemberCount() {
        return members.size();
    }

    public String getMeberNames() {

        String names = "";
        for (SpringCoreMember springCoreMember : members) {
            names += springCoreMember.getName() + " ";
        }

        return names;
    }

}
