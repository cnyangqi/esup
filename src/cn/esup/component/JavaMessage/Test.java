package cn.esup.component.JavaMessage;

import java.io.IOException;

/**
 * 测试类
 * 1.短信内容支持中文 
 * 
 * @author yangq(qi.yang.cn@gmail.com)
 */
public class Test {
	public static void main(String[] args) throws IOException {
		Message message = new Message("", "短信系统网关群发测试，杨琪。");
		System.out.println(MessageService.queryBalance(message));
		//System.out.println(MessageService.sendMsg(message));
		//System.out.println(MessageService.queryBalance(message));
	}
}

/**
 * 1.内容长度调整，联通移动 0条（70 个字/条） 小灵通0条（58个字/条） 
 */
