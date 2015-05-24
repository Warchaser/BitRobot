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
    
	/**
	 * 数据库链接对象
	 * */
    private static Connection conn = null;  
    
    /**
     * 数据库地址
     * */
    private static final String URL;  
    
    /**
     * 数据库链接驱动
     * */
    private static final String JDBC_DRIVER;  
    
    /**
     * 用户名
     * */
    private static final String USER_NAME;  
    
    /**
     * 密码
     * */
    private static final String PASSWORD;
    
    /**
     * 状态集
     * */
    private static Statement stmt;
    
    /**
     * 结果集
     * */
    private static ResultSet rs;
    
    /**
     * 从配置文件test.properties中获取连接数据库的参数
     * */
    static {  
        URL = PropertiesUtil.getPropertyValue("jdbc.url");  
        JDBC_DRIVER = PropertiesUtil.getPropertyValue("jdbc.driverClassName");  
        USER_NAME = PropertiesUtil.getPropertyValue("jdbc.username");  
        PASSWORD = PropertiesUtil.getPropertyValue("jdbc.password");  
    }  
    
    /**
     * 连接数据库
     * */
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

		int rows = 0;
		
		try {
			
			rows = stmt.executeUpdate(sql);
			
			if(rows > 0){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return false;

	}
	
	/**
	 * 关闭连接（数据库和状态集）
	 * */
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