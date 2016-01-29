package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author jinyoung.park89
 * @date 2016. 1. 28.
 */
public class CountingConnectionMaker implements ConnectionMaker {

    private int counter = 0;
    private ConnectionMaker realConnectionMaker;

    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }

    @Override
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        this.counter++;
        return this.realConnectionMaker.makeNewConnection();
    }

    public int getCounter() {
        return this.counter;
    }
}
