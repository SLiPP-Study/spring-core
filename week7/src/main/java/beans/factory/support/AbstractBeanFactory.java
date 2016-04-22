package beans.factory.support;

import beans.PropertyValue;
import beans.PropertyValues;
import beans.factory.BeanCurrentlyInCreationException;
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

    // '생성중' 이라는 마킹을 위해 선언된 변수
    private static final Object CURRENTLY_IN_CREATION = new Object();
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

    private Object getBeanInternal(String name) throws NoSuchMethodException, SecurityException,
            IllegalArgumentException, InvocationTargetException {

        if (name == null)
            throw new IllegalArgumentException("Bean name null is not allowed");

        if (beanHash.containsKey(name)) {
            Object object = beanHash.get(name);
            // Todo: 1. beanHash 맵에서 꺼낸 object가 '생성중(CURRENTLY_IN_CREATION)'이라고 마킹되어 있는 경우 특정 에러를 빌생시킵니다.

            return object;
        } else {
            BeanDefinition beanDefinition = getBeanDefinition(name);
            if (beanDefinition != null) {
                // Todo: 2. 새로운 bean을 생성하기 전에 일단 '생성중(CURRENTLY_IN_CREATION)' 이라고 마킹을 합니다.

                Object newlyCreatedBean = createBean(beanDefinition, name);
                beanHash.put(name, newlyCreatedBean); // 3. '생성중(CURRENTLY_IN_CREATION)' 마킹을 지우면서 실제 생성된 객체를 저장합니다.
                return newlyCreatedBean;
            } else {
                if (this.parentBeanFactory == null)
                    throw new IllegalArgumentException(
                        "Cannot instantiate [bean name : " + name + "]; is not exist");
                return parentBeanFactory.getBean(name);
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