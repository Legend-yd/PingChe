package mysqlserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlHelper {

	private static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	private static String dbURL = "jdbc:sqlserver://192.168.1.7:1433;DatabaseName=拼车软件";

	private static String userName = "sa";

	private static String userPwd = "yangduo1998513";

	private static Connection getCoonection() {
		try {
			Class.forName(driverName);
			Connection conn = DriverManager.getConnection(dbURL, userName, userPwd);
			return conn;
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.print("----------------连接失败-----------------------");
		}
		return null;
	}

	public static ResultSet executeQuery(String SQL) {
		try

		{

			Connection conn = getCoonection();
			System.out.println("----------------连接数据库成功---------------------------");
			// String SQL = "SELECT PlanTypeID, PlanTypeName FROM C_PlanType ";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			/*
			 * while (rs.next()) { System.out.println(rs.getString("PlanTypeID") + ", " +
			 * rs.getString("PlanTypeName")); }
			 */
			// rs.close();
			// stmt.close();
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("----------------查询失败");
		}
		return null;
	}

	public static boolean executeUpdate(String SQL) {
		try {
			Connection conn = getCoonection();
			System.out.println("---------------连接数据库成功------------------");
			Statement stmt = conn.createStatement();
			int result = stmt.executeUpdate(SQL);
			if (result > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("----------------更新失败----------------------");
		}
		return false;
	}

	public static Boolean verify_customer(String[] s) {
		// 验证
		String SQL = null;
		SQL = "SELECT  [phone],[password] FROM [拼车软件].[dbo].[customer] ";
		ResultSet rs = executeQuery(SQL);
		try {
			while (rs.next()) {
				if (s[2].equals(rs.getString("phone"))) {

					if (s[3].equals(rs.getString("password"))) {
						return true;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static Boolean verify_driver(String[] s) {
		// 验证
		String SQL = null;
		SQL = "SELECT  [phone],[password] FROM [拼车软件].[dbo].[driver] ";
		ResultSet rs = executeQuery(SQL);
		try {
			while (rs.next()) {
				if (s[2].equals(rs.getString("phone"))) {

					if (s[3].equals(rs.getString("password"))) {
						return true;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
}