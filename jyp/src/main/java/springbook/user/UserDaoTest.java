package springbook.user;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
        UserDao userDao = (UserDao) context.getBean("userDao", UserDao.class);
        System.out.println(userDao);

        /*User user = new User();
        user.setName("jyp kaka");
        user.setId("jypID");
        user.setPassword("secret pw");

        userDao.add(user);


        User jypID = userDao.get("jypID");
        System.out.println(jypID);*/

    }
}
