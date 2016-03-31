package jyp.bean;

/**
 * @author jinyoung.park89
 * @date 2016. 3. 30.
 */
public class CoreTeam2 {

    private Jyp jyp;
    private Woniper woniper;

    public CoreTeam2(Jyp jyp, Woniper woniper) {
        this.jyp = jyp;
        this.woniper = woniper;
    }

    public String getMeberNames() {
        return this.jyp.getName() + " " + this.woniper.getName();
    }
}
