package com.hth.hive;

import java.sql.*;

public class HiveJdbcTest {
	/**
	 * 执行mapreduce可能各种报错，将/tmp下个文件夹权限改掉，如下
	 * hadoop dfs -chmod 777 /tmp/hadoop-yarn/staging/history;
	 * /tmp/hadoop-yarn/staging
	 */
	/**
	 * 早期版本是这个驱动
	 */
	//private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";
	/**
	 * 后期驱动名称变为这个
	 */
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";

	public static void main(String[] args) throws SQLException {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		/**
		 * 早期连接名是这种形式
		 */
		//Connection con = DriverManager.getConnection("jdbc:hive://192.168.0.105:10000/mydb", "", "");
		/**
		 * 后期连接名是这种形式
		 */
		Connection con = DriverManager.getConnection("jdbc:hive2://192.168.0.105:10000/mydb", "", "");
		Statement stmt = con.createStatement();
		String tableName = "test";
		stmt.execute("drop table if exists " + tableName);
		stmt.execute("create table " + tableName + " (key int, value string)");
		System.out.println("Create table success!");
		// show tables
		String sql = "show tables '" + tableName + "'";
		System.out.println("Running: " + sql);
		ResultSet res = stmt.executeQuery(sql);
		if (res.next()) {
			System.out.println(res.getString(1));
		}

		// describe table
		sql = "describe " + tableName;
		System.out.println("Running: " + sql);
		res = stmt.executeQuery(sql);
		while (res.next()) {
			System.out.println(res.getString(1) + "\t" + res.getString(2));
		}

		sql = "select * from " + tableName;
		res = stmt.executeQuery(sql);
		while (res.next()) {
			System.out.println(String.valueOf(res.getInt(1)) + "\t" + res.getString(2));
		}

		sql = "select count(1) from " + tableName;
		System.out.println("Running: " + sql);
		res = stmt.executeQuery(sql);
		while (res.next()) {
			System.out.println(res.getString(1));
		}
	}
}
