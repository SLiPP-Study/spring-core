import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.dao.CountingDataSource;
import springbook.user.dao.DaoFactory;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import javax.sql.DataSource;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author jinyoung.park89
 * @date 2016. 1. 26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(/*value = locations = "/applicationContext.xml",*/ classes = DaoFactory.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CountingDataSource countingDatasource;

    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp() {
        //ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        user1 = new User("testID1", "박진영", "pw1");
        user2 = new User("testID2", "박진일", "pw2");
        user3 = new User("testID3", "박진이", "pw3");
    }

    @After
    public void after() {
        System.out.println(countingDatasource.getCounter());
    }

    @Test
    public void addAndGet() throws SQLException {

        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        User user = new User("jypID", "jyp", "secret pw");

        userDao.add(user);
        assertThat(userDao.getCount(), is(1));

        User jypID = userDao.get("jypID");
        assertThat(jypID.getName(), is(user.getName()));
        assertThat(jypID.getPassword(), is(user.getPassword()));
    }

    @Test
    public void count() throws SQLException {

        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        userDao.add(user1);
        assertThat(userDao.getCount(), is(1));

        userDao.add(user2);
        assertThat(userDao.getCount(), is(2));

        userDao.add(user3);
        assertThat(userDao.getCount(), is(3));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException {

        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        userDao.get("unknow_id");
    }
}
