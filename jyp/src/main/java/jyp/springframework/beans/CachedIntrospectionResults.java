package jyp.springframework.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
final class CachedIntrospectionResults {

    private static final Log logger = LogFactory.getLog(CachedIntrospectionResults.class);

    /** Map keyed by class containing CachedIntrospectionResults */
    private static HashMap classCache = new HashMap();

    /**
     * We might use this from the EJB tier, so we don't want to use synchronization.
     * Object references are atomic, so we can live with doing the occasional
     * unnecessary lookup at startup only.
     */
    protected static CachedIntrospectionResults forClass(Class clazz) throws BeansException {
        Object results = classCache.get(clazz);
        if (results == null) {
            // can throw BeansException
            results = new CachedIntrospectionResults(clazz);
            classCache.put(clazz, results);
        }
        else {
            if (logger.isDebugEnabled()) {
                logger.debug("Using cached introspection results for class " + clazz.getName());
            }
        }
        return (CachedIntrospectionResults) results;
    }


    private BeanInfo beanInfo;

    /** Property descriptors keyed by property name */
    private Map propertyDescriptorMap;

    /**
     * Create new CachedIntrospectionResults instance fot the given class.
     */
    private CachedIntrospectionResults(Class clazz) throws FatalBeanException {
        try {
            logger.debug("Getting BeanInfo for class [" + clazz.getName() + "]");
            this.beanInfo = Introspector.getBeanInfo(clazz);

            logger.debug("Caching PropertyDescriptors for class [" + clazz.getName() + "]");
            this.propertyDescriptorMap = new HashMap();
            // This call is slow so we do it once
            PropertyDescriptor[] pds = this.beanInfo.getPropertyDescriptors();
            for (int i = 0; i < pds.length; i++) {
                logger.debug("Found property '" + pds[i].getName() + "' of type [" + pds[i].getPropertyType() +
                        "]; editor=[" + pds[i].getPropertyEditorClass() + "]");
                this.propertyDescriptorMap.put(pds[i].getName(), pds[i]);
            }
        }
        catch (IntrospectionException ex) {
            throw new FatalBeanException("Cannot get BeanInfo for object of class [" + clazz.getName() + "]", ex);
        }
    }

    protected BeanInfo getBeanInfo() {
        return beanInfo;
    }

    protected Class getBeanClass() {
        return beanInfo.getBeanDescriptor().getBeanClass();
    }

    protected PropertyDescriptor getPropertyDescriptor(String propertyName) throws BeansException {
        PropertyDescriptor pd = (PropertyDescriptor) this.propertyDescriptorMap.get(propertyName);
        if (pd == null) {
            throw new FatalBeanException("No property '" + propertyName + "' in class [" + getBeanClass().getName() + "]", null);
        }
        return pd;
    }

}
