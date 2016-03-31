package jyp.bean;

/**
 * @author jinyoung.park89
 * @date 2016. 3. 30.
 */
public class CoreTeam3 {

    private Javajigi javajigi;
    private Jyp jyp;
    private Woniper woniper;

    public CoreTeam3(Javajigi javajigi, Jyp jyp, Woniper woniper) {
        this.javajigi = javajigi;
        this.jyp = jyp;
        this.woniper = woniper;
    }

    public String getMeberNames() {
        return this.javajigi.getName() + " " + this.jyp.getName() + " " + this.woniper.getName();
    }
}
