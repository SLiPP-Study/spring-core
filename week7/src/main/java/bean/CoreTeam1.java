package bean;

public class CoreTeam1 implements SpringCoreTeam {

    private Javajigi javajigi;

    public CoreTeam1(Javajigi javajigi) {
        this.javajigi = javajigi;
    }

    public int getMemberCount() {
        return 1;
    }

    public String getMeberNames() {
        return javajigi.getName();
    }
}
