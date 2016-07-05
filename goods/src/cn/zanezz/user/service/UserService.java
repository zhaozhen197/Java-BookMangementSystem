package cn.zanezz.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.jws.soap.SOAPBinding.Use;
import javax.mail.MessagingException;
import javax.mail.Session;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.zanezz.user.dao.UserDao;
import cn.zanezz.user.domain.User;
import cn.zanezz.user.service.exception.UserException;

/**
 * 用户模块业务层
 * 
 * @author ZZ
 *
 */


public class UserService {
	UserDao userDao = new UserDao();
	/**
	 * 修改密码
	 * @param uid
	 * @param newpass
	 * @param oldpass
	 * @throws UserException 
	 */
	public void updatePassword(String  uid,String newpass,String oldpass) throws UserException{
		try {
			/*
			 * 校验老密码
			 */
			boolean bool  = userDao.findByUidAndPass(uid, oldpass);
			if (!bool) {
				throw new UserException("原密码错误 ！");
			}
			/*
			 * 修改密码
			 */
			userDao.updatePassword(uid, newpass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
		
		
	}
	/**
	 * 登陆功能
	 * @param user
	 * @return
	 */
	public User login(User user) {
		try {
			return userDao.findByLoginnameAndLoginpass(user.getLoginname(), user.getLoginpass());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	/**
	 * 激活功能
	 * @throws UserException 
	 */
	
	public void  activation(String code) throws UserException {
		try {
			User user = userDao.findByCode(code);
			if (user == null) {
				throw new  UserException("无效的激活码！");
				
			}
			if (user.getStatus() == 1) {
				throw new UserException("已经激活过了，请不要重复激活！");
				
			}
			userDao.updateStatus(user.getUid(), 1);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}
	/**
	 * 注册校对
	 * @param loginname
	 * @return
	 */
	
	
	public boolean ajaxCheckoutLoginname(String loginname) {
	
		try {
			return userDao.ajaxCheckoutLoginname(loginname);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	/**
	 * 邮箱注册校对
	 * @param email
	 * @return
	 */
	public boolean ajaxCheckoutEmail(String email) {
		try {
			return userDao.ajaxCheckoutEmail(email);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	/*
	 * 注册功能
	 */
	public void regist(User user ) throws SQLException {
		/*
		 * 补全javabean
		 */
		user.setUid(CommonUtils.uuid());
		user.setStatus(0);
		user.setActivationCode(CommonUtils.uuid()+CommonUtils.uuid());
		/*
		 * 写入数据库
		 */
		userDao.add(user);
		/*
		 * 发送邮件
		 */
		
		/*
		 * 把配置文件加载到properties
		 */
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
		} catch (IOException e1) {
			throw new RuntimeException();
		}
		/*
		 * 登陆邮件服务器，得到session
		 */
		String host = properties.getProperty("host");//服务器主机名
		String name=properties.getProperty("username");//登陆名
		String password =properties.getProperty("password");//登陆密码
		Session session = new  MailUtils().createSession(host, name, password);
		/*
		 * 创建mail对象
		 */
		
		String  from = properties.getProperty("from");
		String to = user.getEmail();
		String subject = properties.getProperty("subject");
		//messageFormat.format 将｛0｝｛1｝.。。。替换为param
		String  content = MessageFormat.format(properties.getProperty("content"),user.getActivationCode());
		Mail mail = new Mail(from,to,subject,content);
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			throw new RuntimeException();
		} catch (IOException e) {
			throw new RuntimeException();
		}
				
	}
}
