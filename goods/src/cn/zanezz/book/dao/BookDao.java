package cn.zanezz.book.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.zanezz.book.domain.Book;
import cn.zanezz.category.domain.Category;
import cn.zanezz.page.Expression;
import cn.zanezz.page.PageBean;
import cn.zanezz.page.PageConstants;

public class BookDao {
	
	private QueryRunner queryRunner = new TxQueryRunner();
	/**
	 * 删除图书
	 * @param bid
	 * @throws SQLException
	 */
	public void deleteBook(String bid) throws SQLException {
		String sql = "delete from t_book where bid=?";
		queryRunner.update(sql,bid);
		
	}
	/**
	 * 
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public void	 editBook(Book book) throws SQLException {
		String sql = "update t_book set bname=?,author=?,price=?" +
				",press=?,publishtime=?,edition=?,pagenum=?,wordnum=?," +
				"printtime=?,booksize=?,paper=?,cid=? ,num=? where bid=?";
		Object[] params = {book.getBname(),book.getAuthor(),
				book.getPrice(),
				book.getPress(),book.getPublishtime(),book.getEdition(),
				book.getPagenum(),book.getWordnum(),book.getPrinttime(),
				book.getBooksize(),book.getPaper(), 
				book.getCategory().getCid(),book.getNum(),book.getBid()};
			queryRunner.update(sql, params);
	}
	public PageBean<Book> findAllBooks(int pc) throws SQLException {
		List<Expression> expressions = new ArrayList<Expression>();
		return findByCriteria(expressions, pc);
	}
	/**
	 * 按bid查
	 * @param bid
	 * @return
	 * @throws SQLException
	 */
	public Book findByBid(String bid) throws SQLException {
		String sql = "select * from t_book b ,t_category c where b.cid=c.cid and b.bid=?";
		Map<String, Object> map = queryRunner.query(sql, new MapHandler(),bid);
		
		//把map中的除了cid映射到book中
		Book book = CommonUtils.toBean(map, Book.class);
		//把map中的cid映射到category
		Category category = CommonUtils.toBean(map, Category.class);
		book.setCategory(category);
		if(map.get("pid")!=null){
			Category parent = new Category();
			parent.setCid((String)map.get("pid"));
			category.setParent(parent);
		}
		return book;
		
		
	}
	/**
	 * 按分类查询
	 * @param cid
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	
	public PageBean<Book> findByCategory(String cid,int pc) throws SQLException {
		List<Expression> expressions = new ArrayList<Expression>();
		expressions.add(new Expression("cid","=",cid));
		return findByCriteria(expressions, pc);
		
		
	}
	/*
	 * 按出版社查询
	 */
	public PageBean<Book> findByPress(String press,int pc) throws SQLException {
		List<Expression> expressions = new ArrayList<Expression>();
		expressions.add(new Expression("press","like","%"+press+"%"));
		return findByCriteria(expressions, pc);
		
	}
	/*
	 * 按书名模糊查询
	 */
	public PageBean<Book> findByBname(String bname,int pc) throws SQLException {
		List<Expression> expressions = new ArrayList<Expression>();
		expressions.add(new Expression("bname","like","%"+bname+"%"));
		return findByCriteria(expressions, pc);
		
		
	}
	/**
	 * 按作者模糊查
	 * @param author
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByAutor(String author,int pc) throws SQLException 
	{
		List<Expression> expressions = new ArrayList<Expression>();
		expressions.add(new Expression("author","like","%"+author+"%"));
		return findByCriteria(expressions, pc);
		
	}
	/*
	 * 多添件组合查询
	 */
	public PageBean<Book> findByCombination(Book criteria,int pc) throws SQLException {
		List<Expression> expressions = new ArrayList<Expression>();
		expressions.add(new Expression("bname","like","%"+criteria.getBname()+"%"));
		expressions.add(new Expression("author","like",""+criteria.getAuthor()+"%"));
		expressions.add(new Expression("press","like",""+criteria.getPress()+"%"));
		return findByCriteria(expressions, pc);
		
	}
	
	private PageBean<Book> findByCriteria(List<Expression> exprList,int pc ) throws SQLException {
		/*
		 * 得到ps
		 */
		 int ps = PageConstants.BOOK_PAGE_SIZE;//每页记录数
		 /*
		  * 通过exprlist 来生成where子句
		  */
		 
		 StringBuilder whereSql = new StringBuilder("where 1=1");
		 List<Object> params = new ArrayList<Object>();
		 for (Expression expr: exprList) {
			 /*
			  * 添加一个条件，以and开头
			  * 条件的名称，
			  * 条件的运算符，可以是 = 、！=、。。。。is null . is null 没有值
			  * 如果重要条件不是is null ,再追加？。同时向params添加一个与？ 对应的值
			  */
			 whereSql .append(" and " ).append(expr.getName()).append(" ").append(expr.getOperator()).append(" ");
			 if(!expr.getOperator().equals("is null")){
				 whereSql .append("?");
				 params.add(expr.getValue());
			 }
			
		}
		 /*
		  * 得到记录总数
		  */
		 String sql = "select count(*) from t_book " + whereSql;
		 Number number = (Number) queryRunner.query(sql, new ScalarHandler(),params.toArray());
		 int tr = number.intValue();
		 /*
		  * 得到 	beanlist，即当前记录
		  */
		  sql = "select * from t_book "+ whereSql +" order by orderBy limit ?,?";
		  /*
		   * 查询当前页的，记录
		   */
		  params.add((pc-1)*ps);
		  params.add(ps);
		  
		  List<Book> beanList =  queryRunner.query(sql, new BeanListHandler<Book>(Book.class),params.toArray());
		  /*
		   * 创建pageBean
		   */
		  PageBean<Book> pb = new PageBean<Book>();
		  pb.setPc(pc);
		  pb.setPs(ps);
		  pb.setTr(tr);
		  pb.setBeanlist(beanList);
		return pb;
	}
	/*
	 * 添加图书
	 */
	public void addBook(Book book) throws SQLException {
		String sql = "insert into t_book(bid,bname,author,price," +
				"press,publishtime,edition,pageNum,wordNum,printtime," +
				"booksize,paper,cid,image_w,image_b,num)" +
				" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {book.getBid(),book.getBname(),book.getAuthor(),
				book.getPrice(),book.getPress(),book.getPublishtime(),book.getEdition(),
				book.getPagenum(),book.getWordnum(),book.getPrinttime(),
				book.getBooksize(),book.getPaper(), book.getCategory().getCid(),
				book.getImage_w(),book.getImage_b(),book.getNum()};
		queryRunner.update(sql, params);
	}
}
