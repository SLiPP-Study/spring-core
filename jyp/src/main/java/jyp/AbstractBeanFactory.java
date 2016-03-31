package jyp;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractBeanFactory implements BeanFactory {

    private final BeanFactory parentBeanFactory;
    /**
     * Map of Bean objects, keyed by id attribute
     */
    private Map<String, Object> beanHash = new HashMap();

    public AbstractBeanFactory(BeanFactory parentBeanFactory) {
        this.parentBeanFactory = parentBeanFactory;
    }

    public abstract BeanDefinition getBeanDefinition(String key);

    @Override
    public <T> T getBean(String key, Class<T> clazz) {
        return (T)getBean(key);
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
        } else {
            BeanDefinition beanDefinition = getBeanDefinition(key);
            if (beanDefinition != null) {
                Object newlyCreatedBean = createBean(key);
                beanHash.put(key, newlyCreatedBean);
                return newlyCreatedBean;
            } else {
                if (this.parentBeanFactory == null)
                    throw new IllegalArgumentException(
                        "Cannot instantiate [bean name : " + key + "]; is not exist");
                return parentBeanFactory.getBean(key);
            }
        }
    }

    private Object createBean(String key) {
        try {
            BeanDefinition beanDefinition = getBeanDefinition(key);
            PropertyValues propertyValues = beanDefinition.getPropertyValues();

            Object newlyCreatedBean;

            ConstructorArgument constructorArgument = beanDefinition.getConstructorArgument();
            if (constructorArgument == null) {
                newlyCreatedBean = beanDefinition.getBeanClass().newInstance();
            } else {
                String[] refNames = constructorArgument.getRefNames();
                Object[] refBeans = new Object[refNames.length];
                Class[] refBeanClass = new Class[refNames.length];

                for (int i=0; i<refNames.length; i++) {
                    Object refBean = getBean(refNames[i]);
                    refBeanClass[i] = refBean.getClass();
                    refBeans[i] = refBean;
                }

                Class beanClass = beanDefinition.getBeanClass();
                Constructor constructor = beanClass.getConstructor(refBeanClass);
                newlyCreatedBean = constructor.newInstance(refBeans);
            }

            applyPropertyValues(beanDefinition, propertyValues, newlyCreatedBean, key);
            callLifecycleMethodsIfNecessary(newlyCreatedBean);
            return newlyCreatedBean;
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                "Cannot instantiate [bean name : " + key + "]; is it an interface or an abstract class?");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cannot instantiate [bean name : " + key
                + "]; has class definition changed? Is there a public constructor?");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cannot instantiate [bean name: " + key + "]");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cannot instantiate [bean name: " + key + "]");
        }
    }

    private void applyPropertyValues(BeanDefinition beanDefinition,
                                     PropertyValues propertyValues,
                                     Object bean,
                                     String beanName) {
        Class clazz = beanDefinition.getBeanClass();

        PropertyValue[] array = propertyValues.getPropertyValues();
        for (int i = 0; i < propertyValues.getCount(); ++i) {
            PropertyValue property = array[i];
            try {
                Field field = clazz.getDeclaredField(property.getName());
                String propertyName = property.getName();

                Method method = clazz.getMethod(
                    "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1),
                    new Class[] {field.getType()});

                //Integer와 String 필드에 대해서만 동작
                if ("java.lang.Integer".equals(field.getType().getName())) {
                    method.invoke(bean, Integer.parseInt(property.getValue().toString()));
                } else {
                    method.invoke(bean, property.getValue().toString());
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Cannot instantiate [bean name : " + beanName
                    + "]; is not have field [" + property.getName() + "]");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Cannot instantiate [bean name : " + beanName
                    + "]; Cannot access field [" + property.getName() + "]");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Cannot instantiate [bean name : " + beanName
                    + "]; Cannot access field, set method not defined [" + property.getName() + "]");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void callLifecycleMethodsIfNecessary(Object bean) {
        /*if (bean instanceof InitializingBean) {
            ((InitializingBean)bean).afterPropertiesSet();
        }*/
    }

    protected void clear() {
        this.beanHash.clear();
    }
}