package di.context.support;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import di.beans.factory.support.DefaultBeanFactory;
import di.context.ApplicationContext;
import di.context.annotation.AnnotatedBeanDefinitionReader;
import di.context.annotation.ClasspathBeanDefinitionScanner;
import spring.stereotype.ComponentScan;

public class AnnotationConfigApplicationContext implements ApplicationContext {
	private static final Logger log = LoggerFactory.getLogger(AnnotationConfigApplicationContext.class);
	
	private DefaultBeanFactory beanFactory;

	public AnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
		Object[] basePackages = findBasePackages(annotatedClasses);
		beanFactory = new DefaultBeanFactory();
		AnnotatedBeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
		abdr.register(annotatedClasses);
		
		if (basePackages.length > 0) {
			ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
			scanner.doScan(basePackages);
		}
		beanFactory.preInstantiateSinglonetons();
	}

	private Object[] findBasePackages(Class<?>[] annotatedClasses) {
		List<Object> basePackages = Lists.newArrayList();
		for (Class<?> annotatedClass : annotatedClasses) {
			ComponentScan componentScan = annotatedClass.getAnnotation(ComponentScan.class);
			if (componentScan == null) {
				continue;
			}
			for(String basePackage : componentScan.value()) {
				log.info("Component Scan basePackage : {}", basePackage);
			}
			basePackages.addAll(Arrays.asList(componentScan.value()));
		}
		return basePackages.toArray();
	}

	@Override
	public <T> T getBean(Class<T> clazz) {
		return beanFactory.getBean(clazz);
	}

	@Override
	public Set<Class<?>> getBeanClasses() {
		return beanFactory.getBeanClasses();
	}
}
