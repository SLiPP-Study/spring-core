package di.context.annotation;

import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.junit.Test;

import di.beans.factory.support.DefaultBeanFactory;
import di.context.annotation.AnnotatedBeanDefinitionReader;
import di.context.annotation.ClasspathBeanDefinitionScanner;
import net.slipp.bean.ExampleConfig;
import net.slipp.bean.IntegrationConfig;
import net.slipp.bean.JdbcUserRepository;
import net.slipp.bean.MyJdbcTemplate;

public class AnnotatedBeanDefinitionReaderTest {
	@Test
	public void register_simple() {
		DefaultBeanFactory beanFactory = new DefaultBeanFactory();
		AnnotatedBeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
		abdr.register(ExampleConfig.class);
		beanFactory.preInstantiateSinglonetons();
		
		assertNotNull(beanFactory.getBean(DataSource.class));
	}
	
	@Test
	public void register_ClasspathBeanDefinitionScanner_통합() {
		DefaultBeanFactory beanFactory = new DefaultBeanFactory();
		AnnotatedBeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
		abdr.register(IntegrationConfig.class);
		
		ClasspathBeanDefinitionScanner cbds = new ClasspathBeanDefinitionScanner(beanFactory);
		cbds.doScan("net.slipp.bean");
		
		beanFactory.preInstantiateSinglonetons();
		
		assertNotNull(beanFactory.getBean(DataSource.class));
		
		JdbcUserRepository userRepository = beanFactory.getBean(JdbcUserRepository.class);
		assertNotNull(userRepository);
		assertNotNull(userRepository.getDataSource());
		
		MyJdbcTemplate jdbcTemplate = beanFactory.getBean(MyJdbcTemplate.class);
		assertNotNull(jdbcTemplate);
		assertNotNull(jdbcTemplate.getDataSource());		
	}
}
