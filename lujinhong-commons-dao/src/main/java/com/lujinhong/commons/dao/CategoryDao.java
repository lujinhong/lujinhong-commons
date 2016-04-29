package com.lujinhong.commons.dao;


import java.util.List;

import com.lujinhong.commons.model.Category;


public interface CategoryDao extends Dao{
	
	public List<Category> getCategoryList() throws DaoException;

}
