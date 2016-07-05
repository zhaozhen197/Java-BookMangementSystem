package cn.zanezz.book.service;

import java.sql.SQLException;
import java.util.List;

import javax.print.DocFlavor.INPUT_STREAM;

import cn.zanezz.book.dao.BookDao;
import cn.zanezz.book.domain.Book;
import cn.zanezz.page.PageBean;

public class BookService {
	private BookDao bookDao = new BookDao();
	/**
	 * 删除图书
	 * @param book
	 */
	public void deleteBook(String book) {
		try {
			bookDao.deleteBook(book);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	/**
	 *修改图书 
	 */
	public void editBook(Book book) {
		try {
			bookDao.editBook(book);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	/**
	 * 查找所有图书
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findAllbooks(int pc) throws SQLException {
		return bookDao.findAllBooks(pc);
	}
	public Book findByBid (String  bid) throws SQLException {
		return bookDao.findByBid(bid);
	}
	/*
	 * 按分类查
	 */
	public PageBean<Book> findByCategory(String cid,int pc) {
		try {
			return bookDao.findByCategory(cid, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	/*
	 * 按书名查
	 */
	public PageBean<Book> findByBname(String bname,int pc) {
		try {
			return bookDao.findByBname(bname, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	/*
	 * 作者查询
	 */
	public PageBean<Book> findByAuthor(String author,int pc) {
		try {
			return bookDao.findByAutor(author, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	/*
	 * 
	 */
	public PageBean<Book> findByPress(String press,int pc) {
		try {
			return bookDao.findByPress(press, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	public PageBean<Book> findByCombination(Book criteria,int pc) {
		try {
			return bookDao.findByCombination(criteria, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	public void addBook(Book book) {
		
		try {
			bookDao.addBook(book);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
