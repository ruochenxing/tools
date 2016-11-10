package com.ocr.util;
import java.sql.*;
import java.util.*;


// <dependency>
// 	<groupId>org.xerial</groupId>
// 	<artifactId>sqlite-jdbc</artifactId>
// 	<version>3.7.2</version>
// </dependency>
public class SQLiteUtil{

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SQLiteUtil.class);
	public static void main(String []args){
		//INSTANCE.createTable();
		// List<String> contents=FileUtil.readContentsByFilename("content.txt");
		// for(String s:contents){
		// 	INSTANCE.insert(s);	
		// }
		//INSTANCE.insert("呵呵5");
		//INSTANCE.update(1,1);
		System.out.println(INSTANCE.queryLastContent()[1]);
	}
	static{
		try{
			Class.forName("org.sqlite.JDBC");
		}catch (Exception e) {
			logger.info("org.sqlite.JDBC not found");
		}
	}
	public static final SQLiteUtil INSTANCE=new SQLiteUtil();
	private SQLiteUtil(){
	}
	public static void init(){
		INSTANCE.createTable();
		List<String> contents=FileUtil.readContentsByFilename("content.txt");
		for(String s:contents){
			INSTANCE.insert(s);
		}
		logger.info("save data success.data size:"+contents.size());
	}
	public void delete(int id){
		try{
            String sql = "DELETE from "+tableName()+" where id="+id+";";
            executeSql(sql);
		}catch (Exception e) {
			logger.info("delete error"+" : " + e.getMessage());
        }
	}

	public void update(int id,int status){
		try{
			String sql = "UPDATE "+tableName()+" set status = "+status+" where id="+id+";";
            executeSql(sql);
		}catch(Exception e){
			logger.info("update error"+" : " + e.getMessage());	
		}
	}

	public void insert(String content){
		try{
			String sql="INSERT INTO "+tableName()+" (content,status) VALUES ('"+content+"',0);";
			executeSql(sql);
		}catch(Exception e){
			logger.info("insert error"+" : " + e.getMessage());	
		}
	}
	private void executeSql(String sql) throws Exception{
		Connection conn=getConnection();
		conn.setAutoCommit(false);
		Statement statement = conn.createStatement();
		statement.executeUpdate(sql);
		statement.close();
		conn.commit();
		conn.close();
	}

	public Object[] queryLastContent(){
		Object result[]=new Object[2];
		try{
			Connection conn=getConnection();
			conn.setAutoCommit(false);
			Statement statement = conn.createStatement();
			String sql="SELECT * FROM "+tableName()+" WHERE status=0 LIMIT 1;";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				result[0] = rs.getInt("id");
			    result[1] = new String(rs.getString("content").getBytes(),"UTF-8");
			    break;
			}
			rs.close();
			statement.close();
			conn.close();
			return result;
		}catch(Exception e){
			logger.info("queryLastContent error"+" : " + e.getMessage());
		}
		return null;
	}

	public void createTable(){
        try {
          	String sql = "CREATE TABLE "+tableName()+" " +
                       "(id integer PRIMARY KEY AUTOINCREMENT NOT NULL," +
                       " content           TEXT, " + 
                       " status            INT)"; 
          	executeSql(sql);
          	logger.info("Table created successfully");
          } catch ( Exception e ) {
          	e.printStackTrace();
          }
	}
	private Connection getConnection() throws Exception{
		Connection conn = DriverManager.getConnection("jdbc:sqlite:content.db");
		return conn;
	}
	private String tableName(){
		return "tb_content";
	}
}