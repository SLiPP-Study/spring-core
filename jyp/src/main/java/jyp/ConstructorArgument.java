package jyp;

/**
 * @author jinyoung.park89
 * @date 2016. 3. 30.
 */
public class ConstructorArgument {
    private String refName;

    public ConstructorArgument(String refBeanName) {
        this.refName = refBeanName;
    }

    public String getRefName() {
        return refName;
    }
}
