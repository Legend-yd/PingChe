# PingChe

此服务端是利用socket进行通信、

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

``其中的System.out.println("连接成功");这条语句是为了能在控制台看到是否已经连接成功


在定义一个新的类继承Thread，用来接收消息，


这句代码是为了能够在服务端看到客户端传来的消息 
会将所有客户端发来的消息都显示到控制台上 
其中line就是一个客户端单次发送过来的消息，只需要对其进行判断就能对对客户端进行操作

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

            }
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    服务端只可以有一个所以需要对其进行单例化，定义一个ChatManager类将其getChatManager函数的返回值设为ChatManager,使其能通过此方法获得ChatManager的对象
    
    import java.util.Vector;

public class ChatManager {
    // 实现单例化
    private ChatManager() {
    };

    private static final ChatManager cm = new ChatManager();

    public static ChatManager getChatManager() {// 返回值为ChatManager
        return cm;
    }

    // 单例化完成
    Vector<ChatSocket> vector = new Vector<ChatSocket>();

    public void add(ChatSocket cs) {// 为当前集合添加chatsocket对象
        vector.add(cs);
    }

    // 某一个线程向其他的客户端发送信息
    public void publish(ChatSocket cs, String out) {
        for (int i = 0; i < vector.size(); i++) {// 遍历所有的线程
            ChatSocket csChatSocket = vector.get(i);
            if (csChatSocket != cs)// 判断不是当前线程就发送此消息
                csChatSocket.out(out + "\n");
        }
    }

    // 向当前线程发信息
    public void publish_present(ChatSocket cs, String out) {
        cs.out(out + "\n");
    }
}


