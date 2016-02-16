package nami.spring.anal;

import nami.spring.anal.service.DefaultService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletException;

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
    }

    @Bean
    public DefaultService myService() {
        return new DefaultService();
    }
}
