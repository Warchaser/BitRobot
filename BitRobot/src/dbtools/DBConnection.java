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
	
	/**
	 * 获得数据库的连接
	 * @return void
	 * @param void
	 * @author Wu
	 * */
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
	
	
	/**
	 * 状态集的sql语句执行
	 * */
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
	
	/**
	 * 执行查询，交给结果集，如果rs == null，则查询失败
	 * @author Wu
	 * @return ResultSet
	 * @param String sql;
	 * */
	
	public ResultSet executeQuery(String sql){
		rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	
	/**
	 * 回滚操作，如果执行sql语句（改变表数据或表结构的语句）失败，则需要调用回滚操作
	 * @return void
	 * @author Wu
	 * @param void
	 * */
	public void rollBack(){
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
