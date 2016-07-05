package cn.zanezz.bookcart.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.zanezz.book.dao.BookDao;
import cn.zanezz.book.domain.Book;
import cn.zanezz.bookcart.domain.BookCartItem;
import cn.zanezz.bookcart.service.BookCartService;
import cn.zanezz.user.domain.User;
import cn.zanezz.user.service.exception.UserException;

public class BookCartServlet extends BaseServlet {
	private BookCartService bookCartService = new BookCartService();
	/*
	 * 删除条目
	 */
	public String batchDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException, UserException {
			String cartItemIds =  req.getParameter("cartItemIds");
			bookCartService.bachDelete(cartItemIds);
		return myRent(req, resp);
	}
	//添加条目
	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException, UserException {
		
		
		BookCartItem bookCartItem = CommonUtils.toBean(req.getParameterMap(), BookCartItem.class);
		Book book = CommonUtils.toBean(req.getParameterMap(), Book.class);
		User user = (User) req .getSession().getAttribute("sessionuser");
		if (user == null) {
			req.setAttribute("msg","您还没有登陆!");
			req.setAttribute("code", "error");//将异常信息到request。通知msG.JSP
			 return "f:/jsps/msg.jsp";
		}
		bookCartItem .setBook(book);
		bookCartItem.setUser(user);
		/*
		 * 调用service 完成添加
		 */
		try {
			bookCartService .add(bookCartItem);
		} catch (UserException  e) {
			// TODO: handle exception
			req.setAttribute("msg",e.getMessage());
			req.setAttribute("code", "error");//将异常信息到request。通知msG.JSP
			 return "f:/jsps/msg.jsp";
		}
		
		/*
		 * 查询出当前所所有条目，转发到list.jsp
		 */
		return myRent(req,resp);
		
		
	}
	
	public String myRent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 得到uid
		 */
		User user  =  (User) req.getSession().getAttribute("sessionuser");
		String uid = user.getUid();
		
		/*
		 * 通过servlet得到当前用户的借阅条目
		 */
		
		List<BookCartItem> bookItems = bookCartService.myCart(uid);
		
		/*
		 * 保存list
		 */
		req.setAttribute("bookItems", bookItems);
		return "f:/jsps/cart/list.jsp";
	}

}
