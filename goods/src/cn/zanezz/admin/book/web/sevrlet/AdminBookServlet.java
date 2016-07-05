package cn.zanezz.admin.book.web.sevrlet;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.zanezz.admin.book.service.AdminBookService;
import cn.zanezz.book.domain.Book;
import cn.zanezz.book.service.BookService;
import cn.zanezz.category.domain.Category;
import cn.zanezz.category.service.CategoryService;
import cn.zanezz.page.PageBean;

public class AdminBookServlet extends BaseServlet {
	private AdminBookService adminBookService = new AdminBookService();
	private CategoryService categoryService  = new CategoryService();
	private BookService bookService = new BookService();
	/**
	 * 删除图书 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 */
	public String  deleteBook(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		String bid =req .getParameter("bid");
		
		Book book = bookService.findByBid(bid);
		String savaPath = this.getServletContext().getRealPath("/");
		new File(savaPath,book.getImage_w()).delete();
		new File(savaPath,book.getImage_b()).delete();
		bookService.deleteBook(bid);
		req.setAttribute("msg", "删除成功！");
		return "f:/adminjsps/msg.jsp";
		
		
	}
	/**
	 *修改图书 
	 * 
	 */
	public String  editBook(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map map = req.getParameterMap();
		
		Book book = CommonUtils.toBean(map, Book.class);
		Category category = CommonUtils.toBean(map, Category.class); 
		
		book.setCategory(category);
		bookService.editBook(book);
		
		req.setAttribute("msg", "修改图书成功！");
		return "f:/adminjsps/msg.jsp";
		
		
	}
	/*
	 * 查询所有分类
	 */
	public String  findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	List<Category>parents =	categoryService.findAll();
	req.setAttribute("parents", parents);
	return "f:/adminjsps/admin/book/left.jsp";

		
	
	}
	
	/**
	 * find all books
	 * @param req
	 * @param resp
	 * @return
	 * @throws SQLException 
	 */
	public String findAllBooks (HttpServletRequest req, HttpServletResponse resp) throws SQLException {
		/*
		 * 获取pc，如果页面没有传值,pc=1
		 */
		int pc = getPc(req);
		/*
		 * 获取url
		 */
		String url = getUrl(req);
		
		PageBean<Book> pb = bookService.findAllbooks(pc);
		pb.setUrlString(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/book/list.jsp";
	}
	
	

	/*
	 * 分类查找
	 */
	public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * 获取pc，如果页面没有传值,pc=1
		 */
		int pc = getPc(req);
		/*
		 * 获取url
		 */
		String url = getUrl(req);
		/*
		 * 获取cid
		 */
		String cid = req.getParameter("cid");
		/*
		 * 使用pc和cid得到pagebean
		 */
		PageBean<Book> pb = bookService.findByCategory(cid, pc);
		/*
		 * 给pagebean设置url参数，转发到list.jsp
		 */
		pb.setUrlString(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/book/list.jsp";
	}
	
	/*
	 * 按作者查找
	 */
	public String findByAuthor(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		// TODO Auto-generated method stub
		/*
		 * 获取pc，如果页面没有传值,pc=1
		 */
		int pc = getPc(req);
		/*
		 * 获取url
		 */
		String url = getUrl(req);
		/*
		 * 获取cid
		 */
		String author = req.getParameter("author");
		author = req.getQueryString();
		int firsteaual = author.indexOf("method=");
		
		int pcloction = author.indexOf("&pc");
		
		if (pcloction  != -1) {
			String str = author.substring(firsteaual,pcloction);
			author = str.substring(str.lastIndexOf("=")+1);
		}else {
			author = author.substring(author.lastIndexOf("=")+1);
		}
		
		
		author = URLDecoder.decode(author, "UTF-8");

		/*
		 * 
		 * 使用pc和cid得到pagebean
		 */
		PageBean<Book> pb = bookService.findByAuthor(author, pc);
		/*
		 * 给pagebean设置url参数，转发到list.jsp
		 */
		pb.setUrlString(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/book/list.jsp";
	}
	/*
	 * 按出版社查找
	 */
	public String findByPress(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * 获取pc，如果页面没有传值,pc=1
		 */
		int pc = getPc(req);
		/*
		 * 获取url
		 */
		String url = getUrl(req);
		/*
		 * 获取cid
		 */
		String press = req.getParameter("press");
		
		press = req.getQueryString();
		int firsteaual = press.indexOf("method=");
		
		int pcloction = press.indexOf("&pc");
		
		if (pcloction  != -1) {
			String str = press.substring(firsteaual,pcloction);
			press = str.substring(str.lastIndexOf("=")+1);
		}else {
			press = press.substring(press.lastIndexOf("=")+1);
		}
		
		press = URLDecoder.decode(press, "UTF-8");
		/*
		 * 使用pc和cid得到pagebean
		 */
		PageBean<Book> pb = bookService.findByPress(press, pc);
		/*
		 * 给pagebean设置url参数，转发到list.jsp
		 */
		pb.setUrlString(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/book/list.jsp";
	}
	/*
	 * 出名查找
	 */
	public String findByBname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * 获取pc，如果页面没有传值,pc=1
		 */
		int pc = getPc(req);
		/*
		 * 获取url
		 */
		String url = getUrl(req);
		/*
		 * 获取cid
		 */
		String name = req.getQueryString();
		int firsteaual = name.indexOf("method=");
		
		int pcloction = name.indexOf("&pc");
		
		if (pcloction  != -1) {
			String str = name.substring(firsteaual,pcloction);
			name = str.substring(str.lastIndexOf("=")+1);
		}else {
			name = name.substring(name.lastIndexOf("=")+1);
		}
		
		name = URLDecoder.decode(name, "UTF-8");
		
		/*
		 * 使用pc和cid得到pagebean
		 */
		PageBean<Book> pb = bookService.findByBname(name, pc);
		/*
		 * 给pagebean设置url参数，转发到list.jsp
		 */
		pb.setUrlString(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}
	
	/*
	 * 分类查找
	 */
	public String findByCombination(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * 获取pc，如果页面没有传值,pc=1
		 */
		int pc = getPc(req);
		/*
		 * 获取url
		 */
		String url = getUrl(req);
		
	    Book criteria = CommonUtils.toBean(req.getParameterMap(), Book.class);	
		/*
		 * 使用pc和cid得到pagebean
		 */
		PageBean<Book> pb = bookService.findByCombination(criteria, pc);
		/*
		 * 给pagebean设置url参数，转发到list.jsp
		 */
		pb.setUrlString(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}
	/**
	 * 异步请求
	 * 获取json
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxFindChildren(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException { 
		String pid=req.getParameter("pid");
		List<Category> children = categoryService.findByPartent(pid);
		String json = toJson(children);
		System.out.println(json);
		resp.getWriter().print(json);
		return null;
		
	}
	//{"cid":"sadfasf"}
	public String toJason(Category category) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		
		sb.append("\"cid\"").append(":").append("\"").append(category.getCid()).append("\"");
		sb.append(",");
		sb.append("\"cname\"").append(":").append("\"").append(category.getCname()).append("\"");
		sb.append("}");
		return sb.toString();
	}
	//[{"cid":"sadfasf"},{"cid":"sadfasf"}]
	public  String toJson(List<Category> categoryList) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < categoryList.size(); i++) {
			sb.append(toJason(categoryList.get(i)));
			if (i<categoryList.size()-1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	/*
	 * 获取当前页码
	 */
	private int getPc(HttpServletRequest req) {
		int pc = 1;
		String param = req .getParameter("pc");
		if (param != null && !param.trim().isEmpty()) {
			try {
				pc = Integer.parseInt(param);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return pc;
		
	}
/*
 * 截取url
 */
	private String getUrl(HttpServletRequest req) {
		String url = req.getRequestURI()+"?"+req.getQueryString();
		/**
		 * 如果存在pc参数，截取掉pc
		 */
		int index = url.indexOf("&pc=");
		if(index != -1){
			url = url .substring(0,index);
		}
		System.out.println(url);
		return url;
	}
	/*
	 * 添加图书
	 */
	public String addBookPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Category> parents = categoryService.findPartents();
		req.setAttribute("parents", parents);
		return  "f:/adminjsps/admin/book/add.jsp";
	}
	public String loadByBid(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		/*
		 * 获取bid，得到book对象
		 */
		String bid = req.getParameter("bid");
		Book book = bookService.findByBid(bid);
		req.setAttribute("book", book);
		/*
		 * 		获取所有一级分类，
		 */
		req.setAttribute("parents", categoryService.findPartents());
		
		/*
		 * 获取当前图书 所属一级分类的所有二级分类
		 */
		String pid = book.getCategory().getParent().getCid();
		req.setAttribute("children", categoryService.findByPartent(pid));
		return "f:/adminjsps/admin/book/desc.jsp";
				
	}
}