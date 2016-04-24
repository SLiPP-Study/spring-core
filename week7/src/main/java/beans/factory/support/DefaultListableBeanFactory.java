package beans.factory.support;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import beans.PrintSpringCoreMemberProcessor;
import beans.factory.BeanFactory;
import beans.factory.ListableBeanFactory;
import beans.factory.config.BeanDefinition;

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

        // 4. postProcessBeforeInitialization methods of BeanPostProcessors
        // Todo: BeanPostProcessor 인터페이스를 구현한 클래스를 등록한다. (PrintSpringCoreMemberProcessor)
        addBeanPostProcessor(new PrintSpringCoreMemberProcessor());

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
}
