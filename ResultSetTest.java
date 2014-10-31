package com.common.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetTest {
	
	private final String DRIVERNAME="com.mysql.jdbc.Driver";
	private final String URL="jdbc:mysql://localhost:3306/demo?useUnicode=true&amp;characterEncoding=utf8";
	private final String USER="root";
	private final String PASSWORD="moshu521";
	private Connection conn;
	private PreparedStatement state;
	private ResultSet result;
	
	public ResultSetTest(){
		try{
			Class.forName(DRIVERNAME);
			conn=DriverManager.getConnection(URL, USER, PASSWORD);
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return conn;
	}
	
	public static void main(String []args) throws SQLException{
		ResultSetTest test=new ResultSetTest();
		test.begin();
	}
	public void begin() throws SQLException{
		String sql="select * from customer";
		state=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
		result=state.executeQuery();
		while(result.next()){
			System.out.print(result.getString(1)+"  ");
			System.out.println(result.getString(2));
		}
		System.out.println();
		
		
		result.first();//移到第一条数据
		result.updateString(2, "admin");//修改第一条数据的第四行
		result.updateString(4, "湖南长沙");
		result.updateRow();

		result.beforeFirst();//移动到第一条数据的前面
		while(result.next()){
			System.out.print(result.getString(1)+"  ");
			System.out.print(result.getString(2)+"  ");
			System.out.print(result.getString(3)+"  ");
			System.out.println(result.getString(4));
		}
		result.absolute(2);//移到第二行数据
		result.updateString(4, "第二行第四列");//修改第二行数据的第四列
		result.updateRow();
		
		System.out.println();
		result.beforeFirst();
		while(result.next()){
			System.out.print(result.getString(1)+"  ");
			System.out.print(result.getString(2)+"  ");
			System.out.print(result.getString(3)+"  ");
			System.out.println(result.getString(4));
		}
	}
}
