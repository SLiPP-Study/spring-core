package springbook.user.service;

import springbook.user.domain.User;

/**
 * @author jinyoung.park89
 * @date 2016. 3. 13.
 */
public interface UserService {
    void add(User user);

    void upgradeLevels();
}
