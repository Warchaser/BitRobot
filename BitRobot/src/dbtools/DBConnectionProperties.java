package dbtools;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  

import util.PropertiesUtil;
  
/**   
 * JdbcUtil.java 
 * @version 1.0 
 * @createTime JDBC获取Connection工具类 
 */  
public class DBConnectionProperties {  
      
    private static Connection conn = null;  
      
    private static final String URL;  
      
    private static final String JDBC_DRIVER;  
      
    private static final String USER_NAME;  
      
    private static final String PASSWORD;
    
    private static Statement stmt;
    
    private static ResultSet rs;
      
    static {  
        URL = PropertiesUtil.getPropertyValue("jdbc.url");  
        JDBC_DRIVER = PropertiesUtil.getPropertyValue("jdbc.driverClassName");  
        USER_NAME = PropertiesUtil.getPropertyValue("jdbc.username");  
        PASSWORD = PropertiesUtil.getPropertyValue("jdbc.password");  
    }  
      
    public static Connection getConnection() {  
        try {  
            Class.forName(JDBC_DRIVER);  
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return conn;
    }
    
    /**
	 * 状态集的sql语句执行
	 * */
	public static boolean execute(String sql){

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
	
	public static void close(){
		try {
			if(stmt != null){
				stmt.close();
			}
			
			if(conn != null){
				conn.close();
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行查询，交给结果集，如果rs == null，则查询失败
	 * @author Wu
	 * @return ResultSet
	 * @param String sql;
	 * */
	
	public static ResultSet executeQuery(String sql){
		rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
      
} 