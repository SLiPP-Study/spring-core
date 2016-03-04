package jyp.springframework.beans.factory;


import jyp.springframework.beans.BeansException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * @author jinyoung.park89
 * @since 2016-03-04
 */
public abstract class BeanFactoryUtils {
    public BeanFactoryUtils() {
    }

    public static int countBeansIncludingAncestors(ListableBeanFactory lbf) {
        return beanNamesIncludingAncestors(lbf).length;
    }

    public static String[] beanNamesIncludingAncestors(ListableBeanFactory lbf) {
        HashSet result = new HashSet();
        result.addAll(Arrays.asList(lbf.getBeanDefinitionNames()));
        if(lbf instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory hbf = (HierarchicalBeanFactory)lbf;
            if(hbf.getParentBeanFactory() != null && hbf.getParentBeanFactory() instanceof ListableBeanFactory) {
                String[] parentResult = beanNamesIncludingAncestors((ListableBeanFactory)hbf.getParentBeanFactory());
                result.addAll(Arrays.asList(parentResult));
            }
        }

        return (String[])result.toArray(new String[result.size()]);
    }

    public static String[] beanNamesIncludingAncestors(ListableBeanFactory lbf, Class type) {
        HashSet result = new HashSet();
        result.addAll(Arrays.asList(lbf.getBeanDefinitionNames(type)));
        if(lbf instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory hbf = (HierarchicalBeanFactory)lbf;
            if(hbf.getParentBeanFactory() != null && hbf.getParentBeanFactory() instanceof ListableBeanFactory) {
                String[] parentResult = beanNamesIncludingAncestors((ListableBeanFactory)hbf.getParentBeanFactory(), type);
                result.addAll(Arrays.asList(parentResult));
            }
        }

        return (String[])result.toArray(new String[result.size()]);
    }

    public static Map beansOfTypeIncludingAncestors(ListableBeanFactory lbf, Class type, boolean includePrototypes, boolean includeFactoryBeans) throws BeansException {
        HashMap result = new HashMap();
        result.putAll(lbf.getBeansOfType(type, includePrototypes, includeFactoryBeans));
        if(lbf instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory hbf = (HierarchicalBeanFactory)lbf;
            if(hbf.getParentBeanFactory() != null && hbf.getParentBeanFactory() instanceof ListableBeanFactory) {
                Map parentResult = beansOfTypeIncludingAncestors((ListableBeanFactory)hbf.getParentBeanFactory(), type, includePrototypes, includeFactoryBeans);
                Iterator it = parentResult.keySet().iterator();

                while(it.hasNext()) {
                    Object key = it.next();
                    if(!result.containsKey(key)) {
                        result.put(key, parentResult.get(key));
                    }
                }
            }
        }

        return result;
    }

    public static Object beanOfTypeIncludingAncestors(ListableBeanFactory lbf, Class type, boolean includePrototypes, boolean includeFactoryBeans) throws BeansException {
        Map beansOfType = beansOfTypeIncludingAncestors(lbf, type, includePrototypes, includeFactoryBeans);
        if(beansOfType.size() == 1) {
            return beansOfType.values().iterator().next();
        } else {
            throw new NoSuchBeanDefinitionException(type, "Expected single bean but found " + beansOfType.size());
        }
    }

    public static Object beanOfType(ListableBeanFactory lbf, Class type, boolean includePrototypes, boolean includeFactoryBeans) throws BeansException {
        Map beansOfType = lbf.getBeansOfType(type, includePrototypes, includeFactoryBeans);
        if(beansOfType.size() == 1) {
            return beansOfType.values().iterator().next();
        } else {
            throw new NoSuchBeanDefinitionException(type, "Expected single bean but found " + beansOfType.size());
        }
    }

    public static Object beanOfType(ListableBeanFactory lbf, Class type) throws BeansException {
        return beanOfType(lbf, type, true, true);
    }
}
