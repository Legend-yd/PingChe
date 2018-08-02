package mysocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import mysqlserver.SqlHelper;

public class ChatSocket extends Thread {
	Socket socket;

	public ChatSocket(Socket s) {
		this.socket = s;
	}

	public void out(String out) {
		try {
			socket.getOutputStream().write(out.getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			String line = null;
			while ((line = read.readLine()) != null) {
				System.out.println(line);
				customerlogin(line);
				driverlogin(line);
				callcar(line);
				askforaccept(line);
				hasaccept(line);
			}
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void customerlogin(String s) {
		String[] a = s.split(" ");
		if (a[0].equals("customer") && a[1].equals("login")) {
			if (SqlHelper.verify_customer(a)) {
				ChatManager.getChatManager().publish_present(this, "customer login true");
			}
		}
	}

	public void driverlogin(String s) {
		String[] a = s.split(" ");
		if (a[0].equals("driver") && a[1].equals("login")) {
			if (SqlHelper.verify_driver(a)) {
				ChatManager.getChatManager().publish_present(this, "driver login true");
			}
		}

	}

	public void driverlocation(String s) {
		String[] a = s.split(" ");
		if (a[0].equals("driver") && a[1].equals("location")) {
			ChatManager.getChatManager().publish(this, s);
		}
	}

	// 乘客叫车
	public void callcar(String s) {
		String[] a = s.split(" ");
		if (a[0].equals("callcar"))
			ChatManager.getChatManager().publish(this, s);
	}

	// 司机请求接单
	public void askforaccept(String s) {
		// acceptcall + customer_phone +driver_phone
		String[] a = s.split(" ");
		if (a[0].equals("askforaccept")) {
			s = "permission" + " " + a[1] + " " + a[2];
			ChatManager.getChatManager().publish_present(this, s);
		}
	}

	public void hasaccept(String s) {
		// hasaccept + customer_phone+ driver_phone
		String[] a = s.split(" ");
		if (a[0].equals("hasaccept"))
			s = "hasaccept" + " " + a[1] + " " + a[2];
		ChatManager.getChatManager().publish(this, s);
	}

}
