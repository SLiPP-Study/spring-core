package org.springframework.context.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.*;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.ApplicationEventMulticasterImpl;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.OrderComparator;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.*;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader
implements ConfigurableApplicationContext {

    public static final String MESSAGE_SOURCE_BEAN_NAME = "messageSource";

    protected final Log logger = LogFactory.getLog(getClass());

    private ApplicationContext parent;

    private final List beanFactoryPostProcessors = new ArrayList<>();

    private String displayName = getClass().getName() + ";hashCode=" + hashCode();

    private long startupTime;

    private MessageSource messageSource;

    private final ApplicationEventMulticaster eventMulticaster = new ApplicationEventMulticasterImpl();


    public AbstractApplicationContext() {}

    public AbstractApplicationContext(ApplicationContext parent) {
        this.parent = parent;
    }

    public ApplicationContext getParent() {
        return this.parent;
    }

    protected void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public long getStartupTime() {
        return this.startupTime;
    }

    public void publishEvent(ApplicationEvent event) {
        if (logger.isDebugEnabled()) {
            logger.debug("Publishing event in context [" + getDisplayName() + "]: " + event.toString());
        }
        this.eventMulticaster.onApplicationEvent(event);
        if (this.parent != null) {
            this.parent.publishEvent(event);
        }
    }

    public void setParent(ApplicationContext parent) {
        this.parent = parent;
    }

    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor beanFactoryPostProcessor) {
        this.beanFactoryPostProcessors.add(beanFactoryPostProcessor);
    }

    public List getBeanFactoryPostProcessors() {
        return this.beanFactoryPostProcessors;
    }

    public void refresh() throws BeansException {
        this.startupTime = System.currentTimeMillis();

        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        beanFactory.registerCustomEditor(Resource.class, new ContextResourceEditor(this));
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        beanFactory.ignoreDependencyType(ResourceLoader.class);
        beanFactory.ignoreDependencyType(ApplicationContext.class);
        postProcessBeanFactory(beanFactory);

        for (Iterator it = getBeanFactoryPostProcessors().iterator(); it.hasNext(); ) {
            BeanFactoryPostProcessor factoryProcessor = (BeanFactoryPostProcessor) it.next();
            factoryProcessor.postProcessBeanFactory(beanFactory);
        }

        if (getBeanDefinitionCount() == 0) {
            logger.warn("No beans defined in ApplicationContext [" + getDisplayName() + "]");
        } else {
            logger.info(getBeanDefinitionCount() + " beans defined in ApplicationContext [" + getDisplayName() + "]");
        }


    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    public abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors() throws BeansException {
        String[] beanNames = getBeanDefinitionNames(BeanFactoryPostProcessor.class);
        BeanFactoryPostProcessor[] factoryProcessors = new BeanFactoryPostProcessor[beanNames.length];
        for (int i=0; i<beanNames.length; i++) {
            factoryProcessors[i] = (BeanFactoryPostProcessor) getBean(beanNames[i]);
        }
        Arrays.sort(factoryProcessors, new OrderComparator());
        for (int i=0; i<factoryProcessors.length; i++) {
            BeanFactoryPostProcessor factoryProcessor = factoryProcessors[i];
            factoryProcessor.postProcessBeanFactory(getBeanFactory());
        }
    }

    private void registerBeanPostProcessors() throws BeansException {
        String[] beanNames = getBeanDefinitionNames(BeanFactoryPostProcessor.class);
        if (beanNames.length > 0) {
            List beanProcessors = new ArrayList<>();
            for (int i=0; i<beanNames.length; i++) {
                beanProcessors.add(getBean(beanNames[i]));
            }
            Collections.sort(beanProcessors, new OrderComparator());
            for (Iterator it = beanProcessors.iterator(); it.hasNext(); ) {
                getBeanFactory().addBeanPostProcessor((BeanPostProcessor) it.next());
            }
        }
    }

    private void initMessageSource() throws BeansException {
        try {
            this.messageSource = (MessageSource) getBean(MESSAGE_SOURCE_BEAN_NAME);
            if (this.parent != null && (this.messageSource instanceof HierarchicalMessageSource) &&
                    Arrays.asList(getBeanDefinitionNames()).contains(MESSAGE_SOURCE_BEAN_NAME)) {
                ((HierarchicalMessageSource) this.messageSource).setParentMessageSource(this.parent);
            }
        } catch (NoSuchBeanDefinitionException ex) {
            logger.info("No MessageSource found for [" + getDisplayName() + "]: using empty StaticMessageSource");
            this.messageSource = new StaticMessageSource();
        }
    }

    protected void onRefresh() throws BeansException {

    }

    private void refreshListeners() throws BeansException {
        logger.info("Refreshing listeners");
        Collection listeners = getBeansOfType(ApplicationListener.class, true, false).values();
        logger.debug("Found " + listeners.size() + " listeners in bean factory");
        for (Iterator it = listeners.iterator(); it.hasNext(); ) {
            ApplicationListener listener = (ApplicationListener) it.next();
            addListener(listener);
            logger.info("Application listener [" + listener + "] added");
        }
    }

    protected void addListener(ApplicationListener listener) {
        this.eventMulticaster.addApplicationListener(listener);
    }

    public void close() {
        logger.info("Closing application context [" + getDisplayName() + "]");

        getBeanFactory().destroySingletons();

        publishEvent(new ContextClosedEvent(this));
    }

    @Override
    public long getStartupDate() {
        return startupTime;
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Class requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

    @Override
    public int getBeanDefinitionCount() {
        return getBeanFactory().getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public String[] getBeanDefinitionNames(Class type) {
        return getBeanFactory().getBeanDefinitionNames(type);
    }

    @Override
    public boolean containBeanDefinition(String name) {
        return getBeanFactory().containBeanDefinition(name);
    }

    @Override
    public Map getBeansOfType(Class type, boolean includePropertyTypes, boolean includeFactoryBeans) throws BeansException {
        return getBeanFactory().getBeansOfType(type, includePropertyTypes, includeFactoryBeans);
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return getBeanFactory().isSingleton(name);
    }

    @Override
    public String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return getBeanFactory().getAliases(name);
    }

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return this.messageSource.getMessage(code, args, defaultMessage, locale);
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMethodException {
        return this.messageSource.getMessage(code, args, locale);
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return this.messageSource.getMessage(resolvable, locale);
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return getParent();
    }
}
