package utils;


import model.MailsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailUtils {
    private static Logger logger = LoggerFactory.getLogger(MailUtils.class);

    public  void sendMail(MailsProperties properties) throws MessagingException, UnsupportedEncodingException {
        // 配置发送邮件的环境属性
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host",properties.getMail_host());//"smtp.qq.com"
        props.put("mail.user",properties.getMail_user());//"2776119050@qq.com"
        props.put("mail.password", properties.getKey());//lsslyuequtbodgah
        props.put("mail.smtp.port",properties.getPort());//"25"
        props.put("mail.smtp.starttls.enable", "true");
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        Session mailSession = Session.getInstance(props, authenticator);
        MimeMessage message = new MimeMessage(mailSession);
        InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
        message.setFrom(form);
        Address[] ads;
        String[] toList =properties.getReceiver();//"zecheng.fu@tcl.com"
        if (toList!=null&&!toList.equals("")){
            ads=new InternetAddress[toList.length];
            for(int i=0;i<toList.length;i++){
                ads[i]=new InternetAddress(toList[i]);
            }
            message.setRecipients(Message.RecipientType.TO, ads);
        }
// 设置抄送
        Address[] ccads;
        String[] cc =properties.getCc();
        if (cc!=null&&!cc.equals("")){
            ccads=new InternetAddress[cc.length];
            for(int i=0;i<cc.length;i++){
                ccads[i]=new InternetAddress(cc[i]);
            }
            message.setRecipients(Message.RecipientType.CC, ccads);
        }
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
        Transport.send(message);
        logger.info("====================sendMail Success=========================");
    }

    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {



    }


}
