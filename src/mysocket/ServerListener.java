package mysocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerListener extends Thread {

	public void run() {
		try {
			ServerSocket serversocket = new ServerSocket(12333);
			while (true) {
				// 多个客户端连接用while循环
				Socket socket = serversocket.accept();
				// 建立连接
				System.out.println("连接成功");
				// 将socket传递给新的线程
				ChatSocket cs = new ChatSocket(socket);
				cs.start();
				ChatManager.getChatManager().add(cs);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
