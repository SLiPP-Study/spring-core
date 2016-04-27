package slipp.yoonsung;

import java.util.ArrayList;

public class PropertyValues {

    private final ArrayList propertyValueList;

    public PropertyValues() {
        propertyValueList = new ArrayList(10);
    }

    public void addPropertyValue(PropertyValue propertyValue) {
        propertyValueList.add(propertyValue);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (int i = 0; i < propertyValueList.size(); i++) {
            PropertyValue pv = (PropertyValue) propertyValueList.get(i);
            if (pv.getName().equals(propertyName))
                return pv;
        }
        return null;
    }

    public PropertyValue[] getPropertyValues() {
        return (PropertyValue[]) propertyValueList.toArray(new PropertyValue[0]);
    }

    public int getCount() {
        return propertyValueList.size();
    }
}
