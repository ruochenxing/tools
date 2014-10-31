package com.common.jdbc;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
/**	
 * 需要设置数据源，不需要外部jar包
 * User.dat
 * Customer.dat
 * CreateDB.properties
 * */
public class OdbcAndProTest {
	
	/**
	 *获取数据源连接 
	 **/
	public static Connection getConnection() throws IOException, SQLException,ClassNotFoundException {
		Properties props = new Properties();
		String fileName = System.getProperty("user.dir")+ "/src/com/common/jdbc/CreateDB.properties";
		FileInputStream in = new FileInputStream(fileName);
		props.load(in);
		String drivers = props.getProperty("jdbc.drivers");
		if (null != drivers) {
			Class.forName(drivers);// 加载数据库驱动程序
		}
		String url = props.getProperty("jdbc.url");
		return DriverManager.getConnection(url); // 此行代码获取到了数据库的连接
	}

	/**
	 * 创建数据表，并插入数据
	 * */
	public static void createTable(String tableName, BufferedReader in,Statement stmt) throws IOException, SQLException {
		String line = in.readLine();
		String command = "create table " + tableName + "(" + line + ")";
		System.out.println("DDL: " + command);
		stmt.execute(command); // 真正完成表的创建
		while ((line = in.readLine()) != null) {
			command = "insert into " + tableName + " values(" + line + ")";
			stmt.executeUpdate(command); // 真正完成数据的插入
		}
	}

	/**
	 * 查询数据表
	 * */
	public static void showTable(String tableName, Statement stmt)
			throws SQLException {
		String query = "select * from " + tableName;
		ResultSet rs = stmt.executeQuery(query); // 真正执行查询，返回一个ResultSet
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		while (rs.next()) {
			for (int i = 1; i <= columnCount; i++) {
				if (i > 1) {
					System.out.print(",");
				}
				System.out.print(rs.getString(i));
			}
			System.out.println();
		}
		rs.close();
	}

	public static void main(String[] args) {
		try {
			Connection con = getConnection();
			Statement stmt = con.createStatement();
			String tableName = "Customer";
			BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir")+ "/src/com/common/jdbc/" + tableName + ".dat"));
			// 创建表及插入数据
			createTable(tableName, in, stmt);
			// 查询表
			showTable(tableName, stmt);
			in.close();
			stmt.close();
			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("");
	}

}
