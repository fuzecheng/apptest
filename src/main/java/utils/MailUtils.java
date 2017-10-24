package utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailUtils {
    private static Logger logger = LoggerFactory.getLogger(MailUtils.class);

    public static void sendMail() throws MessagingException, UnsupportedEncodingException {
        // 配置发送邮件的环境属性
        Properties props = new Properties();
/*
* 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
* mail.user / mail.from
*/
// 表示SMTP发送邮件，需要进行身份验证
        props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.qq.com");
// 发件人的账号
        props.put("mail.user", "2776119050@qq.com");
// 访问SMTP服务时需要提供的密码
        props.put("mail.password", "lsslyuequtbodgah");//lsslyuequtbodgah   kwedtmyrxjepcbbi
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.starttls.enable", "true");
        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
        message.setFrom(form);
        // 设置收件人
        String toList = "zecheng.fu@tcl.com";
        InternetAddress[] iaToList = new InternetAddress().parse(toList); // 设置多个收件人
        message.setRecipients(MimeMessage.RecipientType.TO, iaToList);
// 设置抄送
//        InternetAddress cc = new InternetAddress("wuguohui@cvte.com");
//        message.setRecipient(RecipientType.CC, cc);

// 设置密送，其他的收件人不能看到密送的邮件地址
// InternetAddress bcc = new InternetAddress("aaaaa@163.com");
// message.setRecipient(RecipientType.CC, bcc);
// 设置邮件标题
        message.setSubject("Web Auto Test Mail");
        Multipart multipart = new MimeMultipart();
        BodyPart localFilePart = new MimeBodyPart();
        localFilePart.setFileName(MimeUtility.encodeText("TestCaseReport.html"));  //设置本地附件名称
        DataSource localDs = new FileDataSource(new File(System.getProperty("user.dir") + "\\" + "test-output" + "\\" + "Extent.html"));
        DataHandler localDh = new DataHandler(localDs);
        localFilePart.setDataHandler(localDh); //设置本地附件资源
        multipart.addBodyPart(localFilePart);

        message.setContent(multipart);
// 发送邮件
        Transport.send(message);
        logger.info("====================sendMail Success=========================");


    }

    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {
    }

}
