package springbook.user.dao;

import com.mysql.jdbc.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

/**
 * @author jinyoung.park89
 * @date 2016. 1. 27.
 */
@Configuration
public class DaoFactory {

    @Autowired
    private CountingDataSource countingDataSource;

    @Bean
    public UserDao userDao() {
        UserDaoJdbc userDao = new UserDaoJdbc();
        userDao.setDataSource(countingDataSource);
        return userDao;
    }

    @Bean
    public CountingDataSource countingDatasource() {
        CountingDataSource countingDataSource = new CountingDataSource();
        countingDataSource.setRealDatasource(dataSource());
        return countingDataSource;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/springbook");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    }
}
