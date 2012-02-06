package cn.esup.component.JavaEmail;

public class Test {

	public static void main(String[] args) throws Exception {
		Email email = new Email();
		email.setFrom("autotransport@qq.com");
		email.setPwd("221133a");
		email.setRecipients(new String[] { "20859822@qq.com" });
		email.setSubject("测试");
		email.setContentType("html");
		email.setContent("<a href=\"http://www.qq.com\">腾讯</a>");
		email.setFile(new String[] { "C:/测试.txt", "C:/测试2.txt" });

		TransportService.sendMailByQQMail(email);
	}
}
