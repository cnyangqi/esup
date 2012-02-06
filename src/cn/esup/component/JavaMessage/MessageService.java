package cn.esup.component.JavaMessage;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 手机短信服务
 * 
 * @author yangq(qi.yang.cn@gmail.com)
 */
public class MessageService {

	/** 查询短信剩余发送数量地址 */
	private final static String queryBalanceUrl = "http://61.147.112.22:8004/0576_GetAccount.asp";

	/** 群发短信地址 */
	private final static String sendMsgUrl = "http://61.147.112.22:8004/0576_IO.asp";

	/** 查询账户短信剩余发送数量  */
	public static int queryBalance(Message message) throws IOException {

		StringBuilder sb = new StringBuilder();
		sb.append("username=").append(message.getUsername());
		sb.append("&");
		sb.append("password=").append(message.getUserpwd());

		String result = execPost(sb, "queryBalance");

		if (result != null) {
			return Integer.parseInt(result);
		}
		return 0;
	}

	/** 群发短信  */
	public static int sendMsg(Message message) throws IOException {

		StringBuilder sb = new StringBuilder();
		sb.append("username=").append(message.getUsername());
		sb.append("&");
		sb.append("password=").append(message.getUserpwd());
		sb.append("&");
		sb.append("mobile=").append(message.getPhones());
		sb.append("&");
		sb.append("content=").append(URLEncoder.encode(message.getContent(), "GB2312"));// 解决信息内容中文乱码问题

		String result = execPost(sb, "sendMsg");

		if (result != null) {
			return Integer.parseInt(result);
		}
		return -999;
	}

	/** POST提交 */
	public static String execPost(StringBuilder sb, String flag) throws IOException {

		URL url = null;
		if (flag.equalsIgnoreCase("querybalance")) {
			url = new URL(MessageService.queryBalanceUrl);
		}
		if (flag.equalsIgnoreCase("sendmsg")) {
			url = new URL(MessageService.sendMsgUrl);
		}

		if (url != null) {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);// 使用连接输入
			connection.setDoOutput(true);// 使用连接输出
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);

			//	System.out.println(URLEncoder.encode(sb.toString(), "UTF-8"));

			connection.getOutputStream().write(sb.toString().getBytes());
			connection.getOutputStream().flush();
			connection.getOutputStream().close();

			if (connection.getResponseCode() == 200) {
				DataInputStream dis = new DataInputStream(connection.getInputStream());
				int len = dis.available();
				byte[] temp = new byte[len];
				dis.readFully(temp);
				return new String(temp);
			}
		}

		return null;
	}
}
