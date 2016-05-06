package net.slipp.custom.spring.config;

import net.slipp.custom.spring.bean.Community;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by woniper on 2016. 5. 1..
 */
@Configuration
public class Application {

    @Bean(name = "slipp")
    public Community community() {
        return new Community();
    }

}
