package cn.zanezz.book.web.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.zanezz.book.domain.Book;
import cn.zanezz.book.service.BookService;
import cn.zanezz.page.PageBean;

public class Bookservlet extends BaseServlet {
	private BookService bookService  = new  BookService();
/*	public String setFindAllUrl(HttpServletRequest req) {
		
		
	}*/
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
		return "f:/jsps/book/list.jsp";
	}
	
	//按bid查询
	public String loadByBid(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
		String bid = req.getParameter("bid");
		Book book = bookService.findByBid(bid);
		req.setAttribute("book", book);
		return "f:/jsps/book/desc.jsp";
		
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
		return "f:/jsps/book/list.jsp";
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
		return "f:/jsps/book/list.jsp";
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
		return "f:/jsps/book/list.jsp";
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
}
