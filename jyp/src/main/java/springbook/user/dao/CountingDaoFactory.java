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
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(connectionMaker());
        return userDao;
    }

    @Bean
    public DataSource dataSource() {



        CountingConnectionMaker countingConnectionMaker = new CountingConnectionMaker();
        countingConnectionMaker.setRealConnectionMaker(new DConnectionMaker());
        return countingConnectionMaker;
    }
}
*/
