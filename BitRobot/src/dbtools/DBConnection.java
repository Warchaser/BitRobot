package dbtools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 连接到数据库的工具类
 * */

public class DBConnection {
	private String driver = "com.mysql.jdbc.Driver";
	private String dbUrl = "jdbc:mysql://127.0.0.1:3306/bishe"; 
    private String user = "wu"; 
    private String password = "123456";
	private Connection conn = null; 
	private Statement stmt;
	private ResultSet rs = null;
	
	public int connect(){
		
		try {
			Class.forName(driver);
			
			conn = DriverManager.getConnection(dbUrl,user,password);

			stmt = conn.createStatement();
			
			if(conn.isClosed()){
				return 0;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 1;
	}
	
	public boolean execute(String sql){

		try {
			if(stmt.execute(sql)){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;

	}
	
	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
