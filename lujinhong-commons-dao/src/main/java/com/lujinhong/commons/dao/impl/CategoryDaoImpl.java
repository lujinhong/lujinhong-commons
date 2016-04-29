package com.lujinhong.commons.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.lujinhong.commons.dao.CategoryDao;
import com.lujinhong.commons.dao.DaoBase;
import com.lujinhong.commons.dao.DaoException;
import com.lujinhong.commons.model.Category;


public class CategoryDaoImpl extends DaoBase implements CategoryDao {

	@Override
	public List<Category> getCategoryList() throws DaoException{
		
		String GET_CATEGORY_SQL = "SELECT * FROM T_CATEGORY";

		List<Category> categoryList = new ArrayList<Category>();
		
		Connection conn = null;
		PreparedStatement pStatment =null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			pStatment = conn.prepareStatement(GET_CATEGORY_SQL);
			rs = pStatment.executeQuery();
			while(rs.next()){
				Category category = new Category();
				category.setCid(rs.getInt("cid"));
				category.setTitle(rs.getString("title"));
				category.setSequnce(rs.getInt("sequnce"));
				category.setDeleted(rs.getInt("deleted"));
				categoryList.add(category);
			}
		}catch(Exception e){
			throw new DaoException("Erorr getting Categorys. " + e.getMessage());
		}finally{
			closeDbObject(rs, pStatment, conn);
		}
		
		return categoryList;

	}

}
