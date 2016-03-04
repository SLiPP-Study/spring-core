package jyp.springframework.beans.factory.support;

import jyp.springframework.beans.MutablePropertyValues;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class ChildBeanDefinition extends AbstractBeanDefinition {

    private String parentName;

    public ChildBeanDefinition(String parentName, MutablePropertyValues pvs) {
        super(pvs);
        this.parentName = parentName;
    }

    /**
     * Return the name of the parent bean definition in the bean factory.
     */
    public String getParentName() {
        return parentName;
    }

    public void validate() throws BeanDefinitionValidationException {
        super.validate();
        if (this.parentName == null) {
            throw new BeanDefinitionValidationException("parentName must be set in ChildBeanDefinition");
        }
    }

    public String toString() {
        return "Child bean with parent '" + getParentName() + "' defined in " + getResourceDescription();
    }
}
