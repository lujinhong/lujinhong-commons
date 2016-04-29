package com.lujinhong.commons.dao;

import java.sql.Connection;

public interface Dao {
	
	public Connection getConnection() throws DaoException;

}
