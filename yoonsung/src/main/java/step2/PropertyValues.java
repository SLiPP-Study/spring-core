package step2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PropertyValues {

    private final Map<String, PropertyValue> propertyValueMap;

    public PropertyValues() {
        propertyValueMap = new HashMap<>();
    }

    public void addPropertyValue(PropertyValue propertyValue) {
        propertyValueMap.put(propertyValue.getName(), propertyValue);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        return propertyValueMap.get(propertyName);
        /*for (int i = 0; i < propertyValueMap.size(); i++) {
            PropertyValue pv = (PropertyValue) propertyValueMap.get(i);
            if (pv.getName().equals(propertyName))
                return pv;
        }
        return null;*/
    }

    public PropertyValue[] getPropertyValues() {
        Collection<PropertyValue> values = propertyValueMap.values();
        return values.toArray(new PropertyValue[values.size()]);
    }

    public int getCount() {
        return propertyValueMap.size();
    }
}
