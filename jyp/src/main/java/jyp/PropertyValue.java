package jyp;

public class PropertyValue {

    private String name;
    private Object value;
    private String ref;

    public PropertyValue(String name, Object value, String ref) {
        this.name = name;
        this.value = value;
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public String getRef() {
        return this.ref;
    }
}
