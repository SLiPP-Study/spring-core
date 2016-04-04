package jyp;

import jyp.beans.factory.BeanCurrentlyInCreationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBeanFactory implements BeanFactory {

    private static final Object CURRENTLY_IN_CREATION = new Object();
    protected final Log logger = LogFactory.getLog(getClass());
    private final BeanFactory parentBeanFactory;
    /**
     * Map of Bean objects, keyed by id attribute
     */
    private Map<String, Object> beanHash = new HashMap<>();

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
        if (key == null) {
            throw new IllegalArgumentException("Bean name null is not allowed");
        }

        Object sharedInstance = beanHash.get(key);
        if (sharedInstance != null) {
            if (sharedInstance == CURRENTLY_IN_CREATION) {
                throw new BeanCurrentlyInCreationException(
                    key + " Requested bean is already currently in creation");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("returning cached object: " + key);
            }
            return sharedInstance;

        } else {
            BeanDefinition beanDefinition = getBeanDefinition(key);
            if (beanDefinition != null) {

                sharedInstance = beanHash.get(key);
                if (sharedInstance == null) {
                    if (logger.isInfoEnabled()) {
                        logger.info("Creating shared instance of bean '" + key + "'");
                    }
                    this.beanHash.put(key, CURRENTLY_IN_CREATION);
                    try {
                        sharedInstance = createBean(key);
                        this.beanHash.put(key, sharedInstance);
                    } catch (BeanCurrentlyInCreationException e) {
                        this.beanHash.remove(key);
                        throw e;
                    }
                }

                return sharedInstance;
            } else {
                if (this.parentBeanFactory == null) {
                    throw new IllegalArgumentException(
                        "Cannot instantiate [bean name : " + key + "]; is not exist");
                }
                return parentBeanFactory.getBean(key);
            }
        }
    }

    private Object createBean(String key) {
        try {
            BeanDefinition beanDefinition = getBeanDefinition(key);
            PropertyValues propertyValues = beanDefinition.getPropertyValues();

            Object newlyCreatedBean;

            if (beanDefinition.isCreateWithConstructor()) {

                ConstructorArguments constructorArguments = beanDefinition.getConstructorArguments();
                List<ConstructorArgument> constructorList = constructorArguments.getConstructorArguments();
                Object[] refBeans = new Object[constructorList.size()];
                Class[] refBeanClass = new Class[constructorList.size()];

                for (int i = 0; i < constructorList.size(); i++) {
                    Object refBean = getBean(constructorList.get(i).getRefName());
                    refBeanClass[i] = refBean.getClass();
                    refBeans[i] = refBean;
                }

                Class beanClass = beanDefinition.getBeanClass();
                Constructor constructor = beanClass.getConstructor(refBeanClass);
                newlyCreatedBean = constructor.newInstance(refBeans);

            } else {
                newlyCreatedBean = beanDefinition.getBeanClass().newInstance();
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