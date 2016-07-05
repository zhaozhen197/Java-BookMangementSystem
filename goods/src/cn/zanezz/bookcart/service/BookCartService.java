package cn.zanezz.bookcart.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.commons.CommonUtils;
import cn.zanezz.book.domain.Book;
import cn.zanezz.bookcart.dao.BookCartDao;
import cn.zanezz.bookcart.domain.BookCartItem;
import cn.zanezz.user.service.exception.UserException;

public class BookCartService {
	private BookCartDao bookCartDao = new BookCartDao();
	
	/*
	 * 批量删除项目
	 */
	public void bachDelete( String cartItemIds) {
		
		try {
			bookCartDao.batchDelete(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	/*
	 * 添加条目
	 */
	public void add(BookCartItem bookCartItem) throws SQLException, UserException {
		/*
		 * 保用uid和bid去数据库查询这个条目是否存在
		 */
		
			BookCartItem _bookCartItem = bookCartDao.findByUidAndBid(bookCartItem.getUser().getUid(),bookCartItem.getBook().getBid());
			if (_bookCartItem == null) {
				bookCartItem.setCartItemId(CommonUtils.uuid());
				bookCartDao.addCartItem(bookCartItem);
				int num = bookCartItem.getBook().getNum()- bookCartItem.getQuantity();
				bookCartDao.updateNum(bookCartItem.getBook().getBid(), num);
			}
			else {
				bookCartItem.setCartItemId(_bookCartItem.getCartItemId());
				throw new  UserException("该书您已经借阅！不能重复借阅");
		}
		
		
	}
	
	public List<BookCartItem> myCart(String uid) {
		try {
			return bookCartDao.findByUser(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e); 
		}
		
	}
}
