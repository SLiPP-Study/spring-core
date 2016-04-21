package beans.factory.support;

import beans.PropertyValue;
import beans.PropertyValues;
import beans.factory.BeanFactory;
import beans.factory.config.BeanDefinition;
import core.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBeanFactory implements BeanFactory {

    private final BeanFactory parentBeanFactory;
    /**
     * Map of Bean objects, keyed by id attribute
     */
    private Map beanHash = new HashMap();

    public AbstractBeanFactory(BeanFactory parentBeanFactory) {
        this.parentBeanFactory = parentBeanFactory;
    }

    public abstract BeanDefinition getBeanDefinition(String key);

    public <T> T getBean(String key, Class<T> clazz) throws NoSuchMethodException, SecurityException,
            IllegalArgumentException, InvocationTargetException {
        return (T)getBean(key);
    }

    public Object getBean(String key) throws NoSuchMethodException, SecurityException, IllegalArgumentException,
            InvocationTargetException {
        return getBeanInternal(key);
    }

    private Object getBeanInternal(String key) throws NoSuchMethodException, SecurityException,
            IllegalArgumentException, InvocationTargetException {
        if (key == null)
            throw new IllegalArgumentException("Bean name null is not allowed");

        if (beanHash.containsKey(key)) {
            return beanHash.get(key);
        } else {
            BeanDefinition beanDefinition = getBeanDefinition(key);
            if (beanDefinition != null) {
                Object newlyCreatedBean = createBean(beanDefinition, key);
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

    private Object createBean(BeanDefinition beanDefinition, String beanName) throws NoSuchMethodException,
            SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            //BeanDefinition beanDefinition = getBeanDefinition(key);
            ConstructorArguments constructorArguments = beanDefinition.getConstructorArguments();
            PropertyValues propertyValues = beanDefinition.getPropertyValues();

            Object newlyCreatedBean;

            ConstructorArgument[] argsArray = constructorArguments.getConstructorArguments();

            if (argsArray.length > 0) {

                int argsSize = argsArray.length;

                Class[] constructorArgs = new Class[argsSize];
                Object[] instanceArgs = new Object[argsSize];

                for (int i = 0; i < argsSize; i++) {
                    Object argBean = getBean(argsArray[i].getRefId());
                    constructorArgs[i] = argBean.getClass();
                    instanceArgs[i] = argBean;
                }

                Constructor constructor = beanDefinition.getBeanClass().getConstructor(constructorArgs);
                newlyCreatedBean = constructor.newInstance(instanceArgs);
            } else {
                newlyCreatedBean = beanDefinition.getBeanClass().newInstance();
                ;
            }

            applyPropertyValues(beanDefinition, propertyValues, newlyCreatedBean, beanName);
            callLifecycleMethodsIfNecessary(newlyCreatedBean);
            return newlyCreatedBean;
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                "Cannot instantiate [bean name : " + beanName + "]; is it an interface or an abstract class?");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cannot instantiate [bean name : " + beanName
                + "]; has class definition changed? Is there a public constructor?");
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

                //Integer?? String ?��?��?�� ???��?���? ?��?��
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
        //        if (bean instanceof step4.InitializingBean) {
        //            ((step4.InitializingBean) bean).afterPropertiesSet();
        //        }
    }

    public void clearBeanHash() {
        beanHash.clear();
    }
}