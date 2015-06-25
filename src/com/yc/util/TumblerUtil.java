package com.yc.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.ui.ModelMap;

public class TumblerUtil {

	private static final Logger LOG = Logger.getLogger(TumblerUtil.class);
	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	public static final String HOST = "smtp.yc-e.net";
	public static final String PROTOCOL = "smtp";
	public static final int PORT = 25;
	public static final String FROM = "jiangbo@yc-e.net";// 发件人的email
	public static final String PWD = "Yc123456";// 发件人密码

	/**
	 * 创建随机数可用于短信验证
	 * 
	 * @param numberFlag
	 *            是否为数字 true 数字 false 英文数字混合
	 * @param length
	 *            随机数长度
	 * @return 随机数
	 */
	public static String createRandom(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);

		return retStr;
	}

	/**
	 * 获取邮件Session
	 * 
	 * @return
	 */
	private static Session getSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", HOST);// 设置服务器地址
		props.put("mail.store.protocol", PROTOCOL);// 设置协议
		props.put("mail.smtp.port", PORT);// 设置端口
		props.put("mail.smtp.auth", true);

		Authenticator authenticator = new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM, PWD);
			}

		};
		Session session = Session.getDefaultInstance(props, authenticator);

		return session;
	}

	/**
	 * 发送邮件
	 * 
	 * @param toEmail
	 *            发送邮件地址
	 * @param content
	 *            邮件内容
	 */
	public static void send(String toEmail, String content) {
		Session session = getSession();
		try {
			LOG.info("--send--" + content);
			// Instantiate a message
			Message msg = new MimeMessage(session);

			// Set message attributes
			msg.setFrom(new InternetAddress(FROM));
			InternetAddress[] address = { new InternetAddress(toEmail) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject("账号邮箱绑定");
			msg.setSentDate(new Date());
			msg.setContent(content, "text/html;charset=utf-8");

			// Send the message
			Transport.send(msg);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	public static ModelMap sendSmsVerification(String mobile) throws Exception {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(Url);

		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");

		String mobile_code = TumblerUtil.createRandom(false, 6);
		String content = new String("您的验证码是： " + mobile_code + "。请不要把验证码泄露给其他人。");

		NameValuePair[] data = {// 提交短信
		new NameValuePair("account", "cf_jb520hss"), new NameValuePair("password", "zhang123456"), // 密码可以使用明文密码或使用32位MD5加密
				// new NameValuePair("password",
				// util.StringUtil.MD5Encode("密码")),
				new NameValuePair("mobile", mobile), new NameValuePair("content", content), };

		method.setRequestBody(data);
		client.executeMethod(method);

		String SubmitResult = method.getResponseBodyAsString();
		Document doc = DocumentHelper.parseText(SubmitResult);
		Element root = doc.getRootElement();

		String code = root.elementText("code");
		String msg = root.elementText("msg");
		String smsid = root.elementText("smsid");

		LOG.info("smsCode==============" + code);
		LOG.info("msg==============" + msg);
		LOG.info("smsid==============" + smsid);
		if ("2".equals(code)) {
			ModelMap mode = new ModelMap();
			mode.put("mobile_code", mobile_code);
			return mode;
		}
		return null;
	}

}
