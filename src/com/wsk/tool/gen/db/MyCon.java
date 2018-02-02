package com.wsk.tool.gen.db;
import java.sql.Connection;
import java.sql.DriverManager;

import com.wsk.tool.gen.fram.Main;

public class MyCon {
	
	public static Connection getConnection(String url,String userName,String password,String driver){
		
		Connection conn = null;
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userName,password);
		} catch (Exception e) {
			Main.result.append("数据库连接失败\n");
			e.printStackTrace();
		}
		
		return conn;
	}
	
}
