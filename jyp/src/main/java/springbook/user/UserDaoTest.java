package springbook.user;

import springbook.user.dao.DConnectionMaker;
import springbook.user.dao.DaoFactory;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;

/**
 * @author jinyoung.park89
 * @date 2016. 1. 26.
 */
public class UserDaoTest {

    public static void main(String args[]) throws SQLException, ClassNotFoundException {

        UserDao userDao = new DaoFactory().userDao();

        User user = new User();
        user.setId("jyp5");
        user.setName("jyp2");
        user.setPassword("kaka");

        userDao.add(user);

        System.out.println(userDao.get(user.getId()));
    }
}
