package cn.zanezz.admin.admin.service;

import java.sql.SQLException;

import cn.zanezz.admin.admin.dao.AdminDao;
import cn.zanezz.admin.admin.domain.Admin;

public class AdminService {
	private AdminDao adminDao=  new AdminDao();
	public Admin findAdmin(Admin admin) {
		try {
			return adminDao.findAdmin(admin.getAdminname(), admin.getAdminpwd());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
	
}
