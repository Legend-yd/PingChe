package mysqlserver;

import java.sql.ResultSet;
import java.sql.SQLException;

import mysocket.ChatManager;
import mysocket.ChatSocket;
import mysqlserver.SqlHelper;

public class Operate {

	/**
	 * @param args
	 * @throws SQLException
	 */

	public static void SqlQuery(int user) throws SQLException {
		String SQL = null;
		if (user == 0) {
			SQL = "SELECT [phone],[password],[sex],[city],[identity] FROM [拼车软件].[dbo].[customer] ";
			ResultSet rs = SqlHelper.executeQuery(SQL);
			try {
				while (rs.next()) {
					System.out.println(
							rs.getString("phone") + "\t" + rs.getString("password") + "\t" + rs.getString("sex") + "\t"
									+ rs.getString("city") + "\t" + rs.getString("identity") + "\t");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			SQL = "SELECT [phone],[password] ,[sex],[city],[identity],[car],[carnumber] FROM [拼车软件].[dbo].[driver] ";
			ResultSet rs = SqlHelper.executeQuery(SQL);
			while (rs.next()) {
				System.out.println(rs.getString("phone") + "\t" + rs.getString("password") + "\t" + rs.getString("sex")
						+ "\t" + rs.getString("city") + "\t" + rs.getString("identity") + rs.getString("car") + "\t"
						+ rs.getString("carnumber"));
			}
		}
	}

	// 乘客数据插入
	public static void SqlInsert(String phone, String password, String sex, String city, String idedntity) {
		String SQL = "  insert into [拼车软件].[dbo].[costomer]([phone],[password] ,[sex],[city],[identity])values( phone, password, sex, city, idedntity) ";

		myshow(SQL);
	}

	// 司机数据插入
	public static void SqlInsert(String phone, String password, char sex, String city, String idedntity, String car,
			String carnumber) {
		String SQL = "  insert into [拼车软件].[dbo].[driver]([phone],[password] ,[sex],[car],[carnumber],"
				+ "[city],[identity])values( '"+phone+"','"+password+"','"+sex+"','"+city+"','"+idedntity+"','"+car+"','"+carnumber+"') ";

		myshow(SQL);
	}

	public static void SqlUpdate() {
		String SQL = "  update  [拼车软件].[dbo].[customer]  set phone='11245457878'  where phone='2'";
		myshow(SQL);
	}

	// user表示用户类型，0为乘客
	// 1表示司机
	public static void SqltestDelete(int user, String phone) {
		String SQL = null;
		if (user == 0) {
			SQL = "  delete from  [拼车软件].[dbo].[costomer]   where [phone]='phone'";
		} else {
			SQL = "  delete from  [拼车软件].[dbo].[driver]   where [phone]='phone'";
		}
		myshow(SQL);
	}

	public static void myshow(String SQL) {
		if (SqlHelper.executeUpdate(SQL)) {
			System.out.println("操作成功 ");
		} else {
			System.out.println("操作失败 ");
		}
	}

}

// 如果要对数据库中的某个表进行操作，需要像这样子做：String sql = "SELECT* FROM [数据库名].[dbo].[表名] where
// xxx "; 例如String sql = "SELECT* FROM [metro].[dbo].[4] wherexxx"
// 。注意，中括号是必要的，不能去掉。