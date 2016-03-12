package springbook.user.dao;

import java.util.List;

import springbook.user.domain.User;

/**
 * @author jinyoung.park89
 * @date 2016. 3. 9.
 */
public interface UserDao {

    void add(final User user);

    User get(String id);

    void deleteAll();

    int getCount();

    List<User> getAll();

    void update(User user);
}
