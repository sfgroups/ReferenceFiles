import org.apache.commons.codec.Charsets

import java.io.File;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

class EmailService {
    private String host = "";
    private int port = 0;
    private String username = "";
    private String password = "";

    def static main(args){
        new EmailService("smtp.mailtrap.io", 25, "87ba3d9555fae8", "91cb4379af43ed");
    }

    public EmailService(String host, int port, String username, String password) {

        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;

        sendMail();
    }

    private void sendMail() {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.trust", host);
        String MAIL_SOCKET_TIMEOUT = "60000";
        // Set a fixed timeout of 60s for all operations -
        // the default timeout is "infinite"
        properties.put("mail.smtp.connectiontimeout", MAIL_SOCKET_TIMEOUT);
        properties.put("mail.smtp.timeout", MAIL_SOCKET_TIMEOUT);
        properties.put("mail.smtp.writetimeout", MAIL_SOCKET_TIMEOUT);

        // Only cache DNS lookups for 10 seconds
        java.security.Security.setProperty("networkaddress.cache.ttl", "10");
        System.setProperty("file.encoding", Charsets.UTF_8.name());
        System.setProperty("mail.mime.charset", Charsets.UTF_8.name());

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        session.setDebug(true)
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("to@gmail.com"));
            message.setSubject("Mail Subject");

            String msg = "This is my first email using JavaMailer";

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(new File("pom.xml"));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
