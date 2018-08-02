package mysocket;

import java.util.Vector;

public class ChatManager {
	// 只能有一个聊天管理器实现单例化
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

	// 像当前线程发信息
	public void publish_present(ChatSocket cs, String out) {
		cs.out(out + "\n");
	}
}
