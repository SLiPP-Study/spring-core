package bean;

import beans.factory.SpringCoreMemberAware;

public class CoreTeam1 implements SpringCoreTeam, SpringCoreMemberAware {

    private Javajigi javajigi;

    private SpringCoreMember javajigi2;

    public CoreTeam1(Javajigi javajigi) {
        this.javajigi = javajigi;
    }

    public Javajigi getJavajigi() {
        return javajigi;
    }

    public SpringCoreMember getJavajigi2() {
        return javajigi2;
    }

    public int getMemberCount() {
        return 1;
    }

    public String getMemberNames() {
        return javajigi.getName();
    }

    @Override
    public void setSpringCoreMember(SpringCoreMember springCoreMember) {
        this.javajigi2 = springCoreMember;
    }
}
