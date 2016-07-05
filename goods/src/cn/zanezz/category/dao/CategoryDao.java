package cn.zanezz.category.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.mchange.v2.c3p0.impl.NewPooledConnection;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.zanezz.category.domain.Category;

public class CategoryDao {
	private QueryRunner queryRunner = new TxQueryRunner();
	
	/**
	 * 将map-》baen 
	 * @param map
	 * @return
	 */

	/*
	 * 把一个Map中的数据映射到Category中
	 */
	private Category toCategory(Map<String,Object> map) {
		/*
		 * map {cid:xx, cname:xx, pid:xx, desc:xx, orderBy:xx}
		 * Category{cid:xx, cname:xx, parent:(cid=pid), desc:xx}
		 */
		Category category = CommonUtils.toBean(map, Category.class);
		String pid = (String)map.get("pid");// 如果是一级分类，那么pid是null
		if(pid != null) {//如果父分类ID不为空，
			/*
			 * 使用一个父分类对象来拦截pid
			 * 再把父分类设置给category
			 */
			Category parent = new Category();
			parent.setCid(pid);
			category.setParent(parent);
		}
		return category;
	}
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	
	private List< Category> toCategories(List< Map<String, Object>> maplist) {
		List< Category> categoryList = new ArrayList<Category>();
		for( Map<String,Object> map:maplist)
		{
			Category category = toCategory(map);
			categoryList.add(category);
		}
		return  categoryList;
	}
	//返回所有分类
	public List<Category>  findAll() throws SQLException {
		String sql = "select *  from t_category where pid is null order by orderBy";
		List<Map <String,Object>> maplist = queryRunner.query(sql, new MapListHandler());
		List< Category> parents = toCategories(maplist);
		/*
		 * 循环遍历一级分类，为每个一级分类加载二级分类
		 */
		for(Category parent : parents)
		{
				List<Category> children = findByParent(parent.getCid());
				parent.setChilren(children);
		}
		return parents;
		
	}
	//返回所有父分类
	public List<Category>  findParents() throws SQLException {
		String sql = "select *  from t_category where pid is null order by orderBy";
		List<Map <String,Object>> maplist = queryRunner.query(sql, new MapListHandler());
		return toCategories(maplist);
	}
	/*
	 * 加载分类（既可加载一级分类也可加载二级分类）
	 */
	public Category loadCategory (String cid) throws SQLException {
		String sql = "select * from t_category where cid = ?";
		return toCategory(queryRunner.query(sql, new MapHandler(),cid));
		
	}
	
	/*
	 * edit category
	 */
	public void editCategory (Category category) throws SQLException {
		String sql = "update t_category set cname=? ,pid=?,`desc`=? where cid = ?";
		String pid = null;
		
		if (category.getParent() != null) {
			pid = category.getParent().getCid();
		}
		Object [] params = {category.getCname(),pid,category.getDesc(),category.getCid()};
		queryRunner.update(sql, params);
	}
	/**
	 * 通过父分类查找子分类
	 * @param pid
	 * @return
	 * @throws SQLException 
	 */
	public List<Category> findByParent(String pid) throws SQLException {
		String sql = "select * from t_category where pid=? order by orderBy";
		List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(),pid);
		return toCategories(list);
		
	}
	
	/*
	 * 添加分类
	 * 
	 */
	public void addCategory(Category category) throws SQLException {
		String sql = "insert into t_category(cid,cname ,pid,`desc`) values(?,?,?,?)";
		String pid = null;
		
		if (category.getParent()!= null ) {
			pid = category.getParent().getCid();
			
		}
		Object[] params = {category.getCid(),category.getCname(),pid,category.getDesc()};
		queryRunner.update(sql, params);
	}
	/*
	 * 删除分类
	 */
	public void deleteCategory(String cid) throws SQLException{
		String sql = "delete  from t_category where cid=?";
		queryRunner.update(sql,cid);
	}
}
