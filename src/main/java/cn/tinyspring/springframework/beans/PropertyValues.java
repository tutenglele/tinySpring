package cn.tinyspring.springframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性集合
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList = new ArrayList<>();
    public void addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
    }
    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : this.propertyValueList) {
            if (propertyValue.getName().equals(propertyName)) {
                return propertyValue;
            }
        }
        return null;
    }
}
