package springbook.user.dao;

/**
 * @author jinyoung.park89
 * @date 2016. 1. 27.
 */
public class DaoFactory {
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    private DConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}
