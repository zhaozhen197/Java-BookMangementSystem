package cn.zanezz.category.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.zanezz.category.domain.Category;
import cn.zanezz.category.service.CategoryService;
/**
 * 分类模块web层
 * @author ZZ
 *
 */

public class CategoryServlet extends BaseServlet {
	private  CategoryService categoryService = new CategoryService();
	/*
	 * 查询所有分类
	 */
	public String  findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	List<Category>parents =	categoryService.findAll();
	req.setAttribute("parents", parents);
	return "f:/jsps/left.jsp";
		
	
	}
}
