package cn.zanezz.bookcart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;




import org.junit.Test;

import com.mchange.v2.c3p0.impl.NewPooledConnection;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.zanezz.book.domain.Book;
import cn.zanezz.bookcart.domain.BookCartItem;
import cn.zanezz.user.domain.User;

public class BookCartDao {
	private QueryRunner queryRunner = new TxQueryRunner();
	
	private String  toWhereSql(int len) {
		StringBuilder sBuilder = new StringBuilder("cartItemId in (");
		for (int i = 0; i < len; i++) {
			sBuilder.append("?");
			if (i<len-1) {
				sBuilder.append(",");
			}
		}
		sBuilder.append(")");
		return sBuilder.toString();
	}
	/*
	 * findbook
	 */
	public Book findBook(String bid) throws SQLException {
		String sql = "select * from t_book where bid=?";
		Book book = queryRunner.query(sql, new BeanHandler<Book>(Book.class),bid);
		return book;
	}
	/*
	 * 删除条目 
	 */
	public void batchDelete(String cartItemId) throws SQLException {
		Object [] cartItemIdArray = cartItemId.split(",");
		String SQL = "select * from t_cartitem where cartItemId=?";

		for (Object object : cartItemIdArray) {
			String cartitemid = object.toString();
			
			BookCartItem bookCartItem = queryRunner.query(SQL, new BeanHandler<BookCartItem>(BookCartItem.class),cartitemid);
			System.out.println(bookCartItem.getCartItemId());
			Book book = findBook(bookCartItem.getBid());
			bookCartItem .setBook(book);
			int num =  bookCartItem.getBook().getNum();
			num = num + bookCartItem.getQuantity();
			updateNum(bookCartItem.getBook().getBid(), num);
		}
		
			String whereSql = toWhereSql(cartItemIdArray.length);
			String sql = "delete from t_cartitem where "+whereSql;
			queryRunner.update(sql ,cartItemIdArray);
		
	}
	/*
	 * 查询某个用户的某本书的
	 */
	public BookCartItem findByUidAndBid(String uid,String bid) throws SQLException {
		String sql = "select * from t_cartitem where uid = ? and bid = ?";
		Map<String, Object> map = queryRunner.query(sql, new MapHandler(),uid,bid);
		BookCartItem bookCartItem = toCartItem(map);
		System.out.println(bookCartItem);
		
		return bookCartItem;
		
	}
	
	
	/*
	 * 修改在馆图书的数量
	 */
	public void updateNum(String bid,int num) throws SQLException {
		String sql = "update t_book set num=? where bid=?";
		queryRunner.update(sql,num,bid);
	}
	
	/*
	 * 添加条目
	 */
	public void	 addCartItem(BookCartItem bookCartItem) throws SQLException {
		String sql = "insert into t_cartitem (cartItemId,quantity ,bid,uid)"+
				"values(?,?,?,?)";
		Object[] params = {bookCartItem.getCartItemId(),bookCartItem.getQuantity(),bookCartItem.getBook().getBid(),bookCartItem.getUser().getUid()};
		queryRunner.update(sql,params);
	}
	@Test
	public Connection name() {
		Connection connect = null;   
		 try   
		    {   
		      Class.forName("com.mysql.jdbc.Driver");   
		      connect = DriverManager.getConnection(   
		          "jdbc:mysql://localhost/goods", "root", "zzlzzl"); 
		      return connect;
		    } catch (Exception e)   
		    {   
		      // TODO Auto-generated catch block   
		      e.printStackTrace();   
		    } 
		 return null;
	}

	
	//将map映射为bookCartItem
	private BookCartItem toCartItem(Map<String, Object> map)
	{
		if (map == null || map.size() == 0 ) {
			return null;
			
		}
		 BookCartItem bookCartItem = CommonUtils.toBean(map, BookCartItem.class);
		 Book book = CommonUtils.toBean(map, Book.class);
		 User user = CommonUtils.toBean(map, User.class);
		 
		 bookCartItem.setBook(book);
		 bookCartItem.setUser(user);
		 System.out.println(bookCartItem.getCartItemId());
		 return bookCartItem;
	}
	
	/*
	 * 
	 */
	private List<BookCartItem> toBookCartItems(List<Map<String, Object>> maplist) {
		List<BookCartItem> cartList = new ArrayList<BookCartItem>();
		for (Map<String, Object> map : maplist) {
			cartList.add(toCartItem(map));
		}
		
		return cartList;
	}
	/**
	 * 通过用户查询购物车
	 * @param uidb
	 * @return
	 * @throws SQLException
	 */
	public List<BookCartItem> findByUser(String uid) throws SQLException {
		
		String sql = "select * from t_cartitem c,t_book b where  c.bid=b.bid and uid=? order by c.orderBy";
		List<Map<String, Object>> mapList = queryRunner.query(sql, new MapListHandler(), uid);
		return toBookCartItems(mapList);
		
	}

}
