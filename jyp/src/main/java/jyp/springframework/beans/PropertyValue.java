package jyp.springframework.beans;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class PropertyValue {

    private String name;
    private Object value;

    public PropertyValue(String name, Object value) {
        if (name == null) {
            throw new IllegalArgumentException("Property name connot be null");
        }
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }

    public String toString() {
        return "PropertyValue: name='" + name + "'; value=[" + value +"]";
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof PropertyValue)) {
            return false;
        }

        PropertyValue otherPv = (PropertyValue) other;
        return (this.name.equals(otherPv.name) && ((this.value == null && otherPv.value == null) ||
        this.value.equals(otherPv.value)));
    }

    public int hashCode() {
        return this.name.hashCode() * 29 + (this.value != null ? this.value.hashCode() : 0);
    }
}
