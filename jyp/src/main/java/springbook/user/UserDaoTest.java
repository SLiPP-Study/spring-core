package springbook.user;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import jyp.spring.core.JYP;

/**
 * @author jinyoung.park89
 * @date 2016. 1. 26.
 */
public class UserDaoTest {

    public static void main(String args[]) throws SQLException, ClassNotFoundException {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        /*UserDao userDao = (UserDao) context.getBean("userDao", UserDao.class);
        System.out.println(userDao);*/

        JYP jyp = (JYP) context.getBean("jyp", JYP.class);
        jyp.setAge(28);
        System.out.println(jyp.getName());
        jyp.setName("park jin young");
        System.out.println(jyp.getName());

        /*User user = new User();
        user.setName("jyp kaka");
        user.setId("jypID");
        user.setPassword("secret pw");

        userDao.add(user);


        User jypID = userDao.get("jypID");
        System.out.println(jypID);*/

    }
}
