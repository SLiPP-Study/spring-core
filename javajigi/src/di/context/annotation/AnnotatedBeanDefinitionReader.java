package di.context.annotation;

import java.lang.reflect.Method;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import di.beans.factory.support.DefaultBeanDefinition;
import di.beans.factory.support.BeanDefinitionRegistry;
import di.beans.factory.support.BeanFactoryUtils;
import spring.stereotype.Bean;


public class AnnotatedBeanDefinitionReader {
	private static final Logger log = LoggerFactory.getLogger(AnnotatedBeanDefinitionReader.class);
	
	private BeanDefinitionRegistry beanDefinitionRegistry;

	public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
		this.beanDefinitionRegistry = beanDefinitionRegistry;
	}

	public void register(Class<?>... annotatedClasses) {
		for (Class<?> annotatedClass : annotatedClasses) {
			registerBean(annotatedClass);
		}
	}

	public void registerBean(Class<?> annotatedClass) {
		beanDefinitionRegistry.registerBeanDefinition(annotatedClass, new DefaultBeanDefinition(annotatedClass));
		Set<Method> beanMethods = BeanFactoryUtils.getBeanMethods(annotatedClass, Bean.class);
		for (Method beanMethod : beanMethods) {
			log.debug("@Bean method : {}", beanMethod);
			AnnotatedBeanDefinition abd = new AnnotatedBeanDefinition(beanMethod.getReturnType(), beanMethod);
			beanDefinitionRegistry.registerBeanDefinition(beanMethod.getReturnType(), abd);
		}
	}
}
