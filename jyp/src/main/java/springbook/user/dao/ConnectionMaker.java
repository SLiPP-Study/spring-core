package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author jinyoung.park89
 * @date 2016. 1. 26.
 */
public interface ConnectionMaker {
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException;
}
