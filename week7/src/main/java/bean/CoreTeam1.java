package bean;

import beans.factory.DisposableBean;

public class CoreTeam1 implements SpringCoreTeam, DisposableBean {

    private Javajigi javajigi;

    public CoreTeam1() {
    }

    public CoreTeam1(Javajigi javajigi) {
        this.javajigi = javajigi;
    }

    public Javajigi getJavajigi() {
        return javajigi;
    }

    public int getMemberCount() {
        return 1;
    }

    public String getMemberNames() {
        return javajigi.getName();
    }

    @Override
    public void destroy() throws Exception {
        this.javajigi = null;
    }
}
