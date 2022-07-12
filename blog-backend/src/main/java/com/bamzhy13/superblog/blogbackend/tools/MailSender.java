package com.bamzhy13.superblog.blogbackend.tools;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {
    private static final String EMAIL_OWNER_ADDR_HOST = "smtp.163.com"; //smtp.163.com  smtp.aliyun.com  smtp.qq.com
    private static final String EMAIL_OWNER_ADDR = "zhanghuiosu@163.com";
    private static final String EMAIL_OWNER_ADDR_PASS = "JMBNNBNPNPRBVIOI";

    public static void sendMail(String title, String email, String content) throws MessagingException {
        Properties prop = new Properties();
        prop.put("mail.host", EMAIL_OWNER_ADDR_HOST);
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.auth", "true");

        prop.setProperty("mail.smtp.ssl.enable", "true");
        Session session = Session.getInstance(prop);

        Transport ts = session.getTransport();
        ts.connect(EMAIL_OWNER_ADDR, EMAIL_OWNER_ADDR_PASS);

        MimeMessage mm = new MimeMessage(session);

        mm.setFrom(new InternetAddress(EMAIL_OWNER_ADDR));

        mm.setRecipient(Message.RecipientType.TO, new InternetAddress(email));

        //mm.setRecipient(Message.RecipientType.CC, new InternetAddress("XXXX@qq.com"));

        mm.setSubject(title);

        //mm.setContent("Your verification code:<b style=\"color:blue;\"></b>", "text/html;charset=utf-8");
        mm.setContent(content, "text/html;charset=utf-8");

        ts.sendMessage(mm, mm.getAllRecipients());
    }
}
