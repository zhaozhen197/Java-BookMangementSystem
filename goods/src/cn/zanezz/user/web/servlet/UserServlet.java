package cn.zanezz.user.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.functors.ForClosure;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.zanezz.user.domain.User;
import cn.zanezz.user.service.UserService;
import cn.zanezz.user.service.exception.UserException;

/**
 * 用户模块的控制层
 * 
 * @author ZZ
 *
 */


public class UserServlet extends BaseServlet {
	 private UserService userService = new UserService();
	 /**
	  * 用户名是否注册校验
	  * @param req
	  * @param resp
	  * @return
	  * @throws ServletException
	  * @throws IOException
	  */
	 public String ajaxCheckoutLoginname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 /*
		  * 获取用户名
		  */
		 String loginname = req.getParameter("loginname");
		 /**
		  * 通过service得到校对结果
		  */
		 boolean flag = userService.ajaxCheckoutLoginname(loginname);
		 /**
		  * 回写到客户端
		  */
		 resp.getWriter().print(flag);
		 /*
		  * 返回空即既不重定向也不转发
		  */
		 return null;
	}
	 /**
	  * 邮箱是否注册校验
	  * @param req
	  * @param resp
	  * @return
	  * @throws ServletException
	  * @throws IOException
	  */
	 public String ajaxCheckoutEmail(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
				 /*
				  * 获取邮箱名
				  */
				 String email= req.getParameter("email");
				 /**
				  * 通过service得到校对结果
				  */
				 boolean flag = userService.ajaxCheckoutEmail(email);
				 /**
				  * 回写到客户端
				  */
				 resp.getWriter().print(flag);
				 return null;
		}
	 /**
	  * 验证是否一致
	  * @param req
	  * @param resp
	  * @return
	  * @throws ServletException
	  * @throws IOException
	  */
	 public String ajaxCheckoutVerifyCode(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
		 	/*
		 	 * 获致图片上的验证码
		 	 */
		String vCode =  (String) req.getSession().getAttribute("vCode");
		 /*
		  * 获取用户输入的值
		  */
		 String verifyCode = req.getParameter("verifyCode");
		 /**
		  * 校对图片的验证码和用户输入 的是否一致
		  */
		 boolean flage = vCode.equalsIgnoreCase(verifyCode);
		 resp.getWriter().print(flage);
		 
			 return null;
		}
	 /**
	  * 注册功能
	  * @param req
	  * @param resp
	  * @return
	  * @throws ServletException
	  * @throws IOException
	  * @throws SQLException 
	  */
	 public String regist(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException, SQLException {
		 /*
		  * 封装表单数据到user对象
		  */
		 User userform = CommonUtils.toBean(req.getParameterMap(), User.class);
		 /*
		  * 校验表单
		  */
		 Map<String, String> errors=checkoutRegist(userform, req.getSession());
		 if(errors.size()>0){
			 req.setAttribute("form", userform);
			 req.setAttribute("errors", errors);
			 return "f:/jsps/user/regist.jsp";
		 }
		 /*
		  *使用service完成任务 
		  */
		 userService.regist(userform);
		 /*
		  * 保存成功信息，转发到信息显示页面
		  */
		
		 req.setAttribute("code", "success");
		 req.setAttribute("msg", "注册成功！请到邮箱激活！");
		 
		 return "f:/jsps/msg.jsp";
		 
	 }
	 /**
	  * 注册校验
	  */
	 private Map<String, String> checkoutRegist(User userForm,HttpSession session)
	 
	 {
		 Map< String, String> errors = new  HashMap<String, String>();
		 /*
		  * 登陆名校对
		  */
		 String loginname =  userForm.getLoginname();
		 if(loginname == null || loginname.trim().isEmpty())
		 {
			 errors.put("loginname", "用户名不能为空！");
		 }else if(loginname.length()<3 || loginname.length()>20){
			 errors.put("loginname", "用户名长度必须在3~20之间！");
		 }else if(!userService.ajaxCheckoutLoginname(loginname)){
			 errors.put("loginname", "用户名已被注册！");
		 }
		 /*
		  * 校验登陆密码
		  */
		 String loginpass =  userForm.getLoginpass();
		 if(loginpass == null || loginpass.trim().isEmpty())
		 {
			 errors.put("loginpass", "密码不能为空！");
		 }else if(loginname.length()<3 || loginname.length()>20){
			 errors.put("loginpass", "密码长度必须在3~20之间！");
		 }
		 /*
		  * 确认校验登陆密码
		  */
		 String reloginpass =  userForm.getReloginpass();
		 if(reloginpass == null || reloginpass.trim().isEmpty())
		 {
			 errors.put("reloginpass", "密码不能为空！");
		 }else if(!reloginpass.equals(loginpass)){
			 errors.put("reloginpass", "两次输入不一致");
		 }
		 /*
		  * 邮箱校对
		  */
		 
		 String email =  userForm.getEmail();
		 if(email == null || email.trim().isEmpty())
		 {
			 errors.put("email", "邮箱不能为空！");
		 }else if(!email.matches( "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")){
			 errors.put("email", "email格式错误！");
		 }else if(!userService.ajaxCheckoutEmail(email)){
			 errors.put("email", "email已被注册！");
		 }
		 /*
		  * 验证码校对
		  */
		 String verifyCode =  userForm.getVerifyCode();
		 String vcode = (String) session.getAttribute("vCode");
		 if(verifyCode == null || verifyCode.trim().isEmpty())
		 {
			 errors.put("verifyCode", "验证码不能为空！");
		 }else if(!vcode.equalsIgnoreCase(verifyCode)){
			 errors.put("verifyCode", "验证码错误！");
		 }
		 return errors;
	 }
	 /**
	  * 激活功能
	  * @param req
	  * @param resp
	  * @return
	  * @throws ServletException
	  * @throws IOException
	  */
	 public String activation(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
		 /*
		  * 获取参数中的激活码
		  * 
		  */
		 String code = req.getParameter("activationCode");
		 try {
			userService.activation(code);
			req.setAttribute("code", "success");//没有异常
			req.setAttribute("msg","恭喜！激活成功！");
		} catch (UserException e) {
			req.setAttribute("msg",e.getMessage());
			req.setAttribute("code", "error");//将异常信息到request。通知msG.JSP
		}
		 return "f:/jsps/msg.jsp";
	 }
	 /**
	  * 退出
	  * @param req
	  * @param resp
	  * @return
	  * @throws ServletException
	  * @throws IOException
	  */
	 public String quit(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
		 req.getSession().invalidate();
		 return "f:/index.jsp";
		 
		 
	 }
	 
	 /**
	  * 登录功能
	  * @param req
	  * @param resp
	  * @return
	  * @throws ServletException
	  * @throws IOException
	  */
	 public String login(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
		 
		 /*
		  * 封装表单数据到user-bean中
		  */
		 User userform = CommonUtils.toBean(req.getParameterMap(), User.class);
		 /*
		  * 校验表单
		  */
		 Map<String, String> errors=checkoutLogin(userform, req.getSession());
		 if(errors.size()>0){
			 req.setAttribute("form", userform);
			 req.setAttribute("errors", errors);
			 return "f:/jsps/user/login.jsp";
		 }
		 /*
		  * 调用service层中的login()方法
		  */
		 User user = userService.login(userform);
		 /*
		  * 判断查找结果
		  */
		 if (user == null) {
			req.setAttribute("msg", "用户名或密码错误！");
			req.setAttribute("user",userform);
			return "f:/jsps/user/login.jsp";
		}else if(user.getStatus() == 0){
			req.setAttribute("msg", "您的帐户没有激活！");
			req.setAttribute("user",userform);
			return "f:/jsps/user/login.jsp";
			
		}else{
			//保存用户到session
			req.getSession().setAttribute("sessionuser", user);
			//保存用户名到cookie
			String loginString = user.getLoginname();
			loginString = URLEncoder.encode(loginString, "UTF-8");
			Cookie cookie = new Cookie("loginname", loginString);
			cookie.setMaxAge(60*60*24*10);
			resp.addCookie(cookie);
			return "r:/index.jsp";
		}
		 
	 }
	 /**
	  * 登陆校验
	  */
	 private Map<String, String> checkoutLogin(User userForm,HttpSession session)
	 
	 {
		 Map< String, String> errors = new  HashMap<String, String>();
		 /*
		  * 登陆名校对
		  */
		 String loginname =  userForm.getLoginname();
		 if(loginname == null || loginname.trim().isEmpty())
		 {
			 errors.put("loginname", "用户名不能为空！");
		 }
		 /*
		  * 校验登陆密码
		  */
		 String loginpass =  userForm.getLoginpass();
		 if(loginpass == null || loginpass.trim().isEmpty())
		 {
			 errors.put("loginpass", "密码不能为空！");
		 }
		 
		 
		 /*
		  * 验证码校对
		  */
		 String verifyCode =  userForm.getVerifyCode();
		 String vcode = (String) session.getAttribute("vCode");
		 if(verifyCode == null || verifyCode.trim().isEmpty())
		 {
			 errors.put("verifyCode", "验证码不能为空！");
		 }else if(!vcode.equalsIgnoreCase(verifyCode)){
			 errors.put("verifyCode", "验证码错误！");
		 }
		 return errors;
	 }
	 /**
	  * 修改密码
	  * @param req
	  * @param resp
	  * @return
	  * @throws ServletException
	  * @throws IOException
	  */
	 public String updatePass(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
		 /*
		  * 将表单数据封装到user中
		  */
		 User userform = CommonUtils.toBean(req.getParameterMap(), User.class);
		 /*
		  * 如果用户没有登陆
		  */
		 User user= (User)req.getSession().getAttribute("sessionuser");
		 if(user == null)
		 {
			 req.setAttribute("msg", "您还没有登陆！");
			 return "f:/jsps/user/login.jsp";
		 }
		 //修改密码
		 
		 try {
			userService.updatePassword(user.getUid(), userform.getNewpass(), userform.getLoginpass());
			req.setAttribute("msg", "修改密码成功！");
			req.setAttribute("code", "success");
			return "f:/jsps/msg.jsp";
		} catch (UserException e) {
			req.setAttribute("msg",e.getMessage());//保存异常信息！
			req.setAttribute("user", userform);
			return "f:/jsps/user/login.jsp";
		}
		 
	 }
	 }


