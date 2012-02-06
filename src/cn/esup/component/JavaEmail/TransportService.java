package cn.esup.component.JavaEmail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 自动发送邮件服务类
 * 
 * 1.支持通过腾讯邮件系统发送邮件(2011.11.22)
 * 2.支持多附件发送(2011.11.22)
 * 
 * @author yangq(qi.yang.cn@gmail.com)
 * @version 1.00(2011.11.22)
 */
public class TransportService {

	/** 通过QQ邮箱发送邮件 */
	public static void sendMailByQQMail(Email email) throws AddressException, MessagingException,
			UnsupportedEncodingException {

		if (email == null) {
			System.out.println("Email对象为空");
			return;
		}

		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.qq.com");
		properties.put("mail.smtp.port", "25");
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", "true");

		/** 创建邮件会话 */
		Session session = Session.getInstance(properties);
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress(email.getFrom()));

		String[] temp1 = email.getRecipients();
		InternetAddress[] ias = new InternetAddress[temp1.length];
		int i = 0;
		for (String s : temp1) {
			ias[i] = new InternetAddress(s);
			i++;
		}

		/**
		 * Message.RecipientType.TO 发送
		 * Message.RecipientType.CC 抄送
		 * Message.RecipientType.BCC 暗送
		 */
		message.setRecipients(Message.RecipientType.TO, ias);
		message.setSentDate(new Date());

		/** 
		 * MimeUtility.encodeText(name, "UTF-8", "B")
		 * 编码方式有两种："B"代表Base64、"Q"代表QP（Quoted-Printable）方式。
		 */
		message.setSubject(MimeUtility.encodeText(email.getSubject(), "UTF-8", "B"));

		if (email.getContentType().equalsIgnoreCase("html")) {
			Multipart multipart = new MimeMultipart();
			MimeBodyPart html = new MimeBodyPart();
			html.setContent(email.getContent(), "text/html; charset=UTF-8");
			multipart.addBodyPart(html);

			/** 邮件附件 */
			if (email.getFile() != null) {
				String[] temp2 = email.getFile();
				for (String s : temp2) {
					MimeBodyPart file = new MimeBodyPart();
					file.setDataHandler(new DataHandler(new FileDataSource(s)));
					file.setFileName(MimeUtility.encodeText(s, "UTF-8", "B"));
					multipart.addBodyPart(file);
				}
			}

			message.setContent(multipart);
		} else {
			message.setText(email.getContent());
		}

		Transport transport = session.getTransport();
		transport.connect(properties.getProperty("mail.smtp.host"), email.getFrom(), email.getPwd());
		transport.sendMessage(message, message.getAllRecipients());
		System.out.println("邮件发送成功");
	}
}
