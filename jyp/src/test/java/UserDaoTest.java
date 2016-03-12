import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.dao.CountingDataSource;
import springbook.user.dao.UserDaoJdbc;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author jinyoung.park89
 * @date 2016. 1. 26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserDaoTest {

    @Autowired
    private UserDaoJdbc userDao;

    @Autowired
    private CountingDataSource countingDatasource;

    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp() {
        //ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        user1 = new User("testID1", "jyp", "pw1", Level.BASIC, 1, 0);
        user2 = new User("testID2", "jyp2", "pw2", Level.SILVER, 55, 10);
        user3 = new User("testID3", "jyp3", "pw3", Level.GOLD, 100, 40);
    }

    @After
    public void after() {
        System.out.println(countingDatasource.getCounter());
    }

    @Test
    public void addAndGet() throws SQLException {

        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        userDao.add(user1);
        User userget1 = userDao.get(user1.getId());
        checkSameUser(user1, userget1);

        userDao.add(user2);
        User userget2 = userDao.get(user2.getId());
        checkSameUser(user2, userget2);
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

    @Test
    public void getAll() throws SQLException {
        userDao.deleteAll();

        List<User> users0 = userDao.getAll();
        assertThat(users0.size(), is(0));

        userDao.add(user1);
        List<User> users1 = userDao.getAll();
        assertThat(users1.size(), is(1));
        checkSameUser(user1, users1.get(0));

        userDao.add(user2);
        List<User> users2 = userDao.getAll();
        assertThat(users2.size(), is(2));
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        userDao.add(user3);
        List<User> users3 = userDao.getAll();
        assertThat(users3.size(), is(3));
        checkSameUser(user1, users3.get(0));
        checkSameUser(user2, users3.get(1));
        checkSameUser(user3, users3.get(2));

    }

    @Test
    public void update() {
        userDao.deleteAll();

        userDao.add(user1);
        userDao.add(user2);

        user1.setName("JTPTPTP");
        user1.setPassword("JTPTPTPPWD");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);
        userDao.update(user1);

        User userget1 = userDao.get(user1.getId());
        checkSameUser(user1, userget1);
        User usersame = userDao.get(user2.getId());
        checkSameUser(user2, usersame);

    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId(), is(user2.getId()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
        assertThat(user1.getLevel(), is(user2.getLevel()));
        assertThat(user1.getLogin(), is(user2.getLogin()));
        assertThat(user1.getRecommend(), is(user2.getRecommend()));
    }
}
