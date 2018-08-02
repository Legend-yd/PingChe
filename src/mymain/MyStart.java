package mymain;

import java.sql.SQLException;

import mysocket.ServerListener;
import mysqlserver.Operate;

public class MyStart {
	public static void main(String[] args) {
		try {
			Operate.SqlQuery(0);
			Operate.SqlQuery(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ServerListener().start();
	}

}
