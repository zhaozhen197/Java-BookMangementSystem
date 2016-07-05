package cn.zanezz.user.dao;


import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mchange.v2.c3p0.impl.NewPooledConnection;

import cn.itcast.jdbc.TxQueryRunner;
import cn.zanezz.user.domain.User;

/**
 * 用户模块持久层
 * @author ZZ
 *
 */

public class UserDao {
	private QueryRunner queryRunner = new TxQueryRunner();
	/**
	 * 按uid和密码查询用户是否  
	 * @param uid
	 * @param pass
	 * @return
	 * @throws SQLException
	 */
	public boolean findByUidAndPass(String uid ,String pass) throws SQLException {
		String sql = "select count(1) from t_user where uid= ? and loginpass = ?";		
		Number number = (Number)queryRunner.query(sql,new ScalarHandler(),uid,pass);
		return number.intValue()>0;
		
	}
	/**
	 * 更改密码！
	 * @param uid
	 * @param password
	 */
	/**
	 * @param uidu
	 * @param password
	 * @throws SQLException 
	 */
	public void updatePassword(String uid ,String password) throws SQLException {
		String sql = "update t_user set loginpass =? where uid=?";
		queryRunner.update(sql,password,uid);
	}
	
	
	/**
	 * 按用户名和密码查询用户是否  
	 * @param loginnameString
	 * @param loginpass
	 * @return
	 * @throws SQLException 
	 */
	
	public User findByLoginnameAndLoginpass(String loginname ,String loginpass) throws SQLException {
		String sql = "select * from t_user where loginname=? and loginpass=?";		
		return queryRunner.query(sql,new BeanHandler<User>(User.class),loginname,loginpass);
		
	}
	/**
	 * 根据激活码查找用户
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	
	public  User findByCode(String code) throws SQLException{
		String sql = "select * from t_user where activationCode=?";
		return queryRunner.query(sql, new BeanHandler<User>(User.class),code);
	}
	/**
	 * 修改用户状态
	 * @throws SQLException 
	 */
	public void updateStatus(String uid,int status) throws SQLException{
		String sql = "update t_user set status=? where uid=?";
		queryRunner.update(sql,status,uid);
	}

	/**
	 * 校对用户名是否注册 
	 * @param loginname
	 * @return
	 * @throws SQLException 
	 */
	public boolean ajaxCheckoutLoginname(String loginname) throws SQLException {
		String sql = "select count(1) from t_user where loginname=?";
		 Number number=(Number)queryRunner.query(sql ,new ScalarHandler(),loginname);
		return number.intValue() == 0;
		
	}
	/**
	 * 校验邮箱是否被使用
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public boolean ajaxCheckoutEmail(String email) throws SQLException {
		String sql = "select count(1) from t_user where email=?";
		 Number number=(Number)queryRunner.query(sql ,new ScalarHandler(),email);
		return number.intValue() == 0;
		
	}
	/**
	 * 添加用户
	 * @param user
	 * @throws SQLException 
	 */
	public void add(User user) throws SQLException{
		String sql="insert into t_user values(?,?,?,?,?,?)";
		Object [] params = {user.getUid(),user.getLoginname(),user.getLoginpass(),user.getEmail(),user.getStatus(),user.getActivationCode()};
		queryRunner.update(sql,params);
		
	}
}
