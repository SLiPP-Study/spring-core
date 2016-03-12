/*
package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

*/
/**
 * @author jinyoung.park89
 * @date 2016. 1. 28.
 *//*

@Configuration
public class CountingDaoFactory {

    @Bean
    public UserDaoJdbc userDao() {
        UserDaoJdbc userDao = new UserDaoJdbc();
        userDao.setDataSource(dataSource());
        return userDao;
    }

    @Bean
    public DataSource dataSource(DataSource dataSource) {

        DataSource countingConnectionMaker = new CountingDataSource();
        countingConnectionMaker.setRealDatasource(dataSource);

        return countingConnectionMaker;
    }
}
*/
