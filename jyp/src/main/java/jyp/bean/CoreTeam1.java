package jyp.bean;

/**
 * @author jinyoung.park89
 * @date 2016. 3. 30.
 */
public class CoreTeam1 implements SpringCoreTeam {

    private Javajigi javajigi;

    public CoreTeam1() {}

    public CoreTeam1(Javajigi javajigi) {
        this.javajigi = javajigi;
    }

    public void setJavajigi(Javajigi javajigi) {
        this.javajigi = javajigi;
    }

    @Override
    public int getMemberCount() {
        return 1;
    }

    @Override
    public String getMemberNames() {
        return javajigi.getName();
    }
}
