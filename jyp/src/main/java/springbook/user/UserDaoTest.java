package springbook.user;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.dao.*;
import springbook.user.domain.User;

import java.sql.SQLException;

/**
 * @author jinyoung.park89
 * @date 2016. 1. 26.
 */
public class UserDaoTest {

    public static void main(String args[]) throws SQLException, ClassNotFoundException {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = context.getBean("userDao", UserDao.class);

        User jyp3 = userDao.get("jyp3");
        userDao.get("jyp3");
        userDao.get("jyp3");
        userDao.get("jyp3");
        userDao.get("jyp3");
        userDao.get("jyp3");
        userDao.get("jyp3");
        System.out.println(jyp3);

        CountingConnectionMaker ccm = context.getBean("countingConnectionMaker", CountingConnectionMaker.class);
        System.out.println("connection count is " + ccm.getCounter());
    }
}
