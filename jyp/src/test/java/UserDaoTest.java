import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

/**
 * @author jinyoung.park89
 * @date 2016. 1. 26.
 */
public class UserDaoTest {

    @Test
    public void test() throws SQLException {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao) context.getBean("userDao", UserDao.class);
        System.out.println(userDao);

        User user = new User();
        user.setName("jyp kaka");
        user.setId("jypID");
        user.setPassword("secret pw");

        userDao.add(user);


        User jypID = userDao.get("jypID");
        System.out.println(jypID);

    }
}
