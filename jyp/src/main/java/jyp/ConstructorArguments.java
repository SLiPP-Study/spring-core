package jyp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jinyoung.park89
 * @since 2016. 4. 1.
 */
public class ConstructorArguments {

    private List<ConstructorArgument> constructorArguments;

    public ConstructorArguments() {
    }

    public void addConstructorArgument(ConstructorArgument constructorArgument) {

        if (constructorArgument == null) {
            return;
        }

        if (this.constructorArguments == null) {
            this.constructorArguments = new ArrayList<>();
        }

        this.constructorArguments.add(constructorArgument);
    }

    public List<ConstructorArgument> getConstructorArguments() {
        return constructorArguments;
    }

    public boolean hasConstructorArguments() {
        if (this.constructorArguments == null || this.constructorArguments.size() == 0) {
            return false;
        }
        return true;
    }
}
