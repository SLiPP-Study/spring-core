package beans.factory.support;

import beans.factory.BeanFactory;
import beans.factory.config.BeanDefinition;
import beans.factory.ListableBeanFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DefaultListableBeanFactory extends AbstractBeanFactory
        implements BeanDefinitionRegistry, ListableBeanFactory {

    private Map<String, BeanDefinition> beanDefinitionHash = new HashMap();

    public DefaultListableBeanFactory(BeanFactory parentBeanFactory) {
        super(parentBeanFactory);
    }

    public String[] getBeanDefinitionNames() {
        Set keys = beanDefinitionHash.keySet();
        String[] names = new String[keys.size()];
        Iterator itr = keys.iterator();
        int i = 0;
        while (itr.hasNext()) {
            names[i++] = (String)itr.next();
        }
        return names;
    }

    @Override
    public int getBeanDefinitionCount() {
        return beanDefinitionHash.size();
    }

    protected void preInstantiate() {
        String[] beanNames = getBeanDefinitionNames();
        for (int i = 0; i < getBeanDefinitionCount(); i++) {
            getBean(beanNames[i]);
        }
    }

    public void registerBeanDefinition(String id, BeanDefinition beanDefinition) {
        beanDefinitionHash.put(id, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String key) {
        return beanDefinitionHash.get(key);
    }

    public void clear() {
        beanDefinitionHash.clear();
        super.clearBeanHash();
    }
}
