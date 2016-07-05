package cn.zanezz.admin.category.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.zanezz.category.domain.Category;
import cn.zanezz.category.service.CategoryService;

public class AdminCategoryServlet extends BaseServlet {
	private CategoryService categoryService = new  CategoryService();
	/**
	 * 添加一级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Category category = CommonUtils.toBean(req.getParameterMap(), Category.class);
		category.setCid(CommonUtils.uuid());
		categoryService.addCategory(category);
		return fingAllCategory(req, resp);
	}
	public String addChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Category child = CommonUtils.toBean(req.getParameterMap(), Category.class);
		child.setCid(CommonUtils.uuid());
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		
		categoryService.addCategory(child);
		return fingAllCategory(req, resp);
	}	
	/**
	 * 添加子分类准备
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChildPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pid = req.getParameter("pid");
		List<Category> parents = categoryService.findPartents();
	
		req.setAttribute("pid", pid);
		req.setAttribute("parents", parents);
		return "f:/adminjsps/admin/category/add2.jsp";
		
		
	}
	/**
	 * 查询所有分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String fingAllCategory(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("parents", categoryService.findAll());
		
		return "f:/adminjsps/admin/category/list.jsp";
	}
	/**
	 * 修改
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParentPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cid = req.getParameter("pid");
		Category category = categoryService.loadCategory(cid);
		req.setAttribute("parent", category);
		return "f:/adminjsps/admin/category/edit.jsp";
	}
	public String editParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Category parent = CommonUtils.toBean(req.getParameterMap(), Category.class);
		categoryService.editCategory(parent);
		return fingAllCategory(req, resp);
	}
	
	/**
	 * 添加子分类准备
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChildPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cid = req.getParameter("cid");
		String pid = req.getParameter("pid");
		List<Category> parents = categoryService.findPartents();
		Category child= categoryService.loadCategory(cid);
		req.setAttribute("child", child);
		
		req.setAttribute("pid", pid);
		req.setAttribute("parents", parents);
		return "f:/adminjsps/admin/category/edit2.jsp";
		
		
	}
	public String editChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Category child = CommonUtils.toBean(req.getParameterMap(), Category.class);
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		System.out.println(pid);
		categoryService.editCategory(child);
		return fingAllCategory(req, resp);
	}
	
	public String deleteCategory(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		categoryService.deleteCategory(req.getParameter("cid"));
		return fingAllCategory(req, resp);
//		return "javascript:alert('删除一级分类成功！')";
	}
}
