package step3;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AbstractBeanFactory implements BeanFactory {
    /**
     * Map of Bean objects, keyed by id attribute
     */
    private Map<String, BeanDefinition> beanDefinitionHash = new HashMap();
    private Map beanHash = new HashMap();

    @Override
    public <T> T getBean(String key, Class<T> clazz) {
        return (T) getBean(key);
    }

    @Override
    public Object getBean(String key) {
        return getBeanInternal(key);
    }

    private Object getBeanInternal(String key) {
        if (key == null)
            throw new IllegalArgumentException("Bean name null is not allowed");

        if (beanHash.containsKey(key)) {
            return beanHash.get(key);
        }

        Object newlyCreatedBean = createBean(key);
        beanHash.put(key, newlyCreatedBean);
        return newlyCreatedBean;
    }

    public void registerBeanDefinition(String id, BeanDefinition beanDefinition) {
        beanDefinitionHash.put(id, beanDefinition);
    }

    public BeanDefinition getBeanDefinition(String key) {
        return beanDefinitionHash.get(key);
    }

    private Object createBean(String key) {
        try {
            BeanDefinition beanDefinition = getBeanDefinition(key);
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            Object newlyCreatedBean = beanDefinition.getBeanClass().newInstance();
            applyPropertyValues(beanDefinition, propertyValues, newlyCreatedBean, key);
            return newlyCreatedBean;
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cannot instantiate [bean name : " + key+ "]; is it an interface or an abstract class?");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cannot instantiate [bean name : " + key + "]; has class definition changed? Is there a public constructor?");
        }
    }

    private void applyPropertyValues(BeanDefinition beanDefinition, PropertyValues propertyValues, Object bean, String beanName) {
        Class clazz = beanDefinition.getBeanClass();

        PropertyValue[] array = propertyValues.getPropertyValues();
        for (int i = 0 ; i < propertyValues.getCount() ; ++i) {
            PropertyValue property = array[i];
            try {
                Field field = clazz.getDeclaredField(property.getName());
                String propertyName = property.getName();

                Method method = clazz.getMethod("set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), new Class[]{field.getType()});

                //Integer와 String 필드에 대해서만 동작
                if ("java.lang.Integer".equals(field.getType().getName())) {
                    method.invoke(bean, Integer.parseInt(property.getValue().toString()));
                } else {
                    method.invoke(bean, property.getValue().toString());
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Cannot instantiate [bean name : " + beanName + "]; is not have field [" + property.getName() + "]");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Cannot instantiate [bean name : " + beanName + "]; Cannot access field [" + property.getName() + "]");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Cannot instantiate [bean name : " + beanName + "]; Cannot access field, set method not defined [" + property.getName() + "]");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
