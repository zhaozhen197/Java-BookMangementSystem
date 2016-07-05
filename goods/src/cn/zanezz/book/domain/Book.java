package cn.zanezz.book.domain;

import cn.zanezz.book.service.BookService;
import cn.zanezz.category.domain.Category;

public class Book {
	
	
	private String bname;//名称
	private String bid;//id主键
	private String author;//作者
	private String price;//价格
	private String press;//出版社
	private String publishtime;//出版时间
	private int edition;//版次
	private int  pagenum;//页数
	private int wordnum;//字数
	private String printtime;//印刷时间
	private int booksize;//开本
	private String paper;//材质
	private Category category;//所属分类
	private String image_w;//犬图路径
	private String image_b;//小图路径
	private int num;//数量
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String baname) {
		this.bname = baname;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getPublishtime() {
		return publishtime;
	}
	public void setPublishtime(String publishtime) {
		this.publishtime = publishtime;
	}
	public int getEdition() {
		return edition;
	}
	public void setEdition(int edition) {
		this.edition = edition;
	}
	public int getPagenum() {
		return pagenum;
	}
	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}
	public int getWordnum() {
		return wordnum;
	}
	public void setWordnum(int wordnum) {
		this.wordnum = wordnum;
	}
	public String getPrinttime() {
		return printtime;
	}
	public void setPrinttime(String printtime) {
		this.printtime = printtime;
	}
	public int getBooksize() {
		return booksize;
	}
	public void setBooksize(int booksize) {
		this.booksize = booksize;
	}
	public String getPaper() {
		return paper;
	}
	public void setPaper(String paper) {
		this.paper = paper;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getImage_w() {
		return image_w;
	}
	public void setImage_w(String image_w) {
		this.image_w = image_w;
	}
	public String getImage_b() {
		return image_b;
	}
	public void setImage_b(String image_b) {
		this.image_b = image_b;
	}
	@Override
	public String toString() {
		return "Book [bname=" + bname + ", bid=" + bid + ", author=" + author
				+ ", price=" + price + ", press=" + press + ", publishtime="
				+ publishtime + ", edition=" + edition + ", pagenum=" + pagenum
				+ ", wordnum=" + wordnum + ", printtime=" + printtime
				+ ", booksize=" + booksize + ", paper=" + paper + ", category="
				+ category + ", image_w=" + image_w + ", image_b=" + image_b
				+ ", num=" + num + "]";
	}
	
	

}
