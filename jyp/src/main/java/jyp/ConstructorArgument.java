package jyp;

/**
 * @author jinyoung.park89
 * @date 2016. 3. 30.
 */
public class ConstructorArgument {
    private String[] refNames;

    public ConstructorArgument(String[] refBeanNames) {
        this.refNames = refBeanNames;
    }

    public String[] getRefNames() {
        return refNames;
    }
}
