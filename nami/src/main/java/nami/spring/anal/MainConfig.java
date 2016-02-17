package nami.spring.anal;

import nami.spring.anal.service.DefaultService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "nami.spring.anal" },
    excludeFilters = {
            @ComponentScan.Filter(Configuration.class)
    })
public class MainConfig {

    @Bean
    public DefaultService myService() {
        return new DefaultService();
    }
}
