package cn.zanezz.admin.admin.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.zanezz.admin.admin.domain.Admin;

public class AdminDao {
	 private QueryRunner queryRunner = new TxQueryRunner();
	 /**
	  * 通过管理员名称和密码查询
	  * @param adminname
	  * @param adminpwd
	  * @return
	  * @throws SQLException
	  */
	 public Admin findAdmin(String adminname ,String adminpwd) throws SQLException {
		 String sql = "select * from t_admin where adminname=? and adminpwd=?";
		 Admin admin = queryRunner.query(sql, new BeanHandler<Admin>(Admin.class), adminname,adminpwd);
		 return admin;
		 
	}
}
