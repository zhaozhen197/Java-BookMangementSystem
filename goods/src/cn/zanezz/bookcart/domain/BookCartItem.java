package cn.zanezz.bookcart.domain;

import cn.zanezz.book.domain.Book;
import cn.zanezz.user.domain.User;

public class BookCartItem {
	private String cartItemId;//主键
	private int quantity;//数量 
	private Book book;//对应的图书
	private User user;//所属用户
	private String bid;
	private String uid;
	
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(String bookcartitemid) {
		this.cartItemId = bookcartitemid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
