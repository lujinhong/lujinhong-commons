package com.lujinhong.commons.dao.factory;

import com.lujinhong.commons.dao.CategoryDao;
import com.lujinhong.commons.dao.impl.CategoryDaoImpl;


public class DaoFactory {
	
	public static CategoryDao getCategoryDao() {
		return new CategoryDaoImpl();
	}
}

