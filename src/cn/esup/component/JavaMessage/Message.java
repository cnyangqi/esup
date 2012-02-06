package cn.esup.component.JavaMessage;

/**
 * 手机短信实体类
 * 
 * @author yangq(qi.yang.cn@gmail.com)
 */
public class Message {

	/** 用户名 */
	private String username;

	/** 密码  */
	private String userpwd;

	/** 手机号码清单 */
	private String phones;

	/** 短信内容 */
	private String content;

	public Message() {
	}

	/** 淘富秘快捷入口 */
	public Message(String phones, String content) {
		setUsername("jiuhua");
		setUserpwd("jiuhua");
		setPhones(phones);
		setContent(content);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
