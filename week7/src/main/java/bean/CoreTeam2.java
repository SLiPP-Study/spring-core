package bean;

public class CoreTeam2 implements SpringCoreTeam {

    private Jyp jyp;
    private Woniper woniper;

    public CoreTeam2(Jyp jyp, Woniper woniper) {
        this.jyp = jyp;
        this.woniper = woniper;
    }

    public int getMemberCount() {

        int count = 0;

        if (jyp != null) {
            count++;
        }

        if (woniper != null) {
            count++;
        }

        return count;
    }

    public String getMemberNames() {
        String names = "";
        if (jyp != null) {
            names += jyp.getName() + " ";
        }

        if (woniper != null) {
            names += woniper.getName() + " ";
        }

        return names;
    }

}
