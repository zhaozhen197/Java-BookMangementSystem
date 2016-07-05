/**
 * 
 */
package cn.zanezz.category.service;

import java.sql.SQLException;
import java.util.List;

import javax.management.RuntimeErrorException;

import cn.zanezz.category.dao.CategoryDao;
import cn.zanezz.category.domain.Category;

/**
 * @author ZZ
 *
 */
public class CategoryService {
	private CategoryDao categoryDao = new CategoryDao();
	/**
	 * 
	 * @return
	 */
	public void addCategory(Category category) {
		try {
			categoryDao.addCategory(category);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
	/*
	 * 
	 */
	public List<Category> findAll() {
		try {
			return categoryDao.findAll();
		} catch (SQLException e) {
			throw  new RuntimeException(e);
		}
		
	}
	/**
	 * 得到父分类
	 * @return
	 */
	public List<Category> findPartents() {
		try {
			return categoryDao.findParents();
		} catch (SQLException e) {
			throw  new RuntimeException(e);
		}
		
	}
	public List<Category> findByPartent(String pid) {
		try {
			return categoryDao.findByParent(pid);
		} catch (SQLException e) {
			throw  new RuntimeException(e);
		}
	}
	/*
	 * 加载分类
	 */
	public Category loadCategory(String cid) {
		try {
			return categoryDao.loadCategory(cid);
		} catch (SQLException e) {
			throw  new RuntimeException(e);
		}
	}
	
	/*
	 * 编辑分类
	 */
	public void editCategory(Category category) {
		try {
			categoryDao .editCategory(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	/*
	 * 删除分类
	 */
	public void deleteCategory(String cid)
	{
		try {
			categoryDao.deleteCategory(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
