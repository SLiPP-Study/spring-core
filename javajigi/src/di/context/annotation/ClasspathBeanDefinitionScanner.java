package di.context.annotation;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.reflections.Reflections;

import com.google.common.collect.Sets;

import di.beans.factory.support.DefaultBeanDefinition;
import di.beans.factory.support.BeanDefinitionRegistry;
import spring.stereotype.Component;
import spring.stereotype.Controller;
import spring.stereotype.Repository;
import spring.stereotype.Service;

public class ClasspathBeanDefinitionScanner {
    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public ClasspathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

	@SuppressWarnings("unchecked")
	public void doScan(Object... basePackages) {
		Reflections reflections = new Reflections(basePackages);
		Set<Class<?>> beanClasses = getTypesAnnotatedWith(reflections, Controller.class, Service.class, Repository.class, Component.class);
		for (Class<?> clazz : beanClasses) {
			beanDefinitionRegistry.registerBeanDefinition(clazz, new DefaultBeanDefinition(clazz));
		}
	}
	
	@SuppressWarnings("unchecked")
	private Set<Class<?>> getTypesAnnotatedWith(Reflections reflections, Class<? extends Annotation>... annotations) {
		Set<Class<?>> preInstantiatedBeans = Sets.newHashSet();
		for (Class<? extends Annotation> annotation : annotations) {
			preInstantiatedBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
		}
		return preInstantiatedBeans;
	}
}
