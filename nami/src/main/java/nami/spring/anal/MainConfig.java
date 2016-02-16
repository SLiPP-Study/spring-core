package nami.spring.anal;

import nami.spring.anal.service.DefaultService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Configuration
//@ComponentScan(basePackages = { "nami.spring.anal" },
//    excludeFilters = {
//            @ComponentScan.Filter(Configuration.class)
//    })
public class MainConfig implements WebApplicationInitializer {
    private AnnotationConfigWebApplicationContext rootContext;

    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(MainConfig.class);
        //rootContext.refresh();

        servletContext.addListener(new ContextLoaderListener(rootContext));

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("main", new DispatcherServlet(rootContext));

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/main/*");
    }

    @Bean
    public DefaultService myService() {
        return new DefaultService();
    }
}
