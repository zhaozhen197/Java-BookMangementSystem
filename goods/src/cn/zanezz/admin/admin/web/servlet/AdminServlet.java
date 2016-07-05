package cn.zanezz.admin.admin.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.zanezz.admin.admin.domain.Admin;
import cn.zanezz.admin.admin.service.AdminService;

public class AdminServlet extends BaseServlet {
	private AdminService adminService = new AdminService();
	/**
	 * 登陆功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 封装表单数据
		 */
		Admin formadmin = CommonUtils.toBean(req.getParameterMap(), Admin.class);
		Admin admin = adminService.findAdmin(formadmin);
		if (admin == null) {
			req.setAttribute("msg", "用户名或密码错误！");
			return "/adminjsps/login.jsp";
		}else {
			req .getSession().setAttribute("admin", admin);
			return "/adminjsps/admin/index.jsp";
		}
		
		
	}
	
	

}
