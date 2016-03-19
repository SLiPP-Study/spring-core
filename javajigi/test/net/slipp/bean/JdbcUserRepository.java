package net.slipp.bean;

import javax.sql.DataSource;

import spring.stereotype.Inject;
import spring.stereotype.Repository;

@Repository
public class JdbcUserRepository implements UserRepository {
	@Inject
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}
}
