package core;

import java.util.ArrayList;

public class ConstructorArguments {

    private final ArrayList<ConstructorArgument> constructorArguments;

    public ConstructorArguments() {
        constructorArguments = new ArrayList<ConstructorArgument>();
    }

    public void addConstructorArgument(ConstructorArgument constructorArgument) {
        constructorArguments.add(constructorArgument);
    }

    public ConstructorArgument[] getConstructorArguments() {
        return (ConstructorArgument[])constructorArguments.toArray(new ConstructorArgument[0]);
    }

    public int getSize() {
        return constructorArguments.size();
    }

    public boolean hasArguments() {
        return getSize() > 0;
    }
}
