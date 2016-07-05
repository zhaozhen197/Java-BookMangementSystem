/**
 * 
 */
package cn.zanezz.page;

import java.util.List;

/**
 * @author ZZ
 *
 */
public class PageBean<T> {
	private int pc;//当前页码
	private int tr;//总的记录娄
	private int ps;//每页记录数
	private String urlString;//请求路径和参数
	private List<T> beanlist;
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public int getTr() {
		return tr;
	}
	public void setTr(int tr) {
		this.tr = tr;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public String getUrlString() {
		return urlString;
	}
	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}
	public List<T> getBeanlist() {
		return beanlist;
	}
	public void setBeanlist(List<T> beanlList) {
		this.beanlist = beanlList;
	}
	
	//计算总页数
	public int getTp() {
		int tp = tr/ps;
		return tr % ps == 0? tp : tp+1;
		
	}
}
