package springbook.user.dao;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author jinyoung.park89
 * @date 2016. 1. 28.
 */
public class CountingDataSource implements DataSource {

    private int counter = 0;
    private DataSource realDatasource;

    public CountingDataSource() {}

    /*public CountingDataSource(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }*/

    public void setRealDatasource(DataSource realDatasource) {
        this.realDatasource = realDatasource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        this.counter++;
        return this.realDatasource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        this.counter++;
        return this.realDatasource.getConnection();
    }

    public int getCounter() {
        return this.counter;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
