import parser.Recipient;
import parser.RecipientCsvParser;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws MessagingException, URISyntaxException, IOException {
        Properties prop = new Properties();
        prop.put("mail.smtp.starttls", true);
        prop.put("mail.smtp.starttls.enable", true);
        prop.put("mail.smtp.starttls.required", true);
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 587);
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.EnableSSL.enable", true);
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("hakaze.scarlet@gmail.com", "mpbsfybeskfvaspq");
            }
        });

        RecipientCsvParser recipientCsvParser = new RecipientCsvParser();
        List<Recipient> recipients = recipientCsvParser.parse();

        String emails = recipients.stream()
            .map(recipient -> recipient.getEmail())
            .collect(Collectors.joining(","));

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("hakaze.scarlet@gmail.com"));
        message.setRecipients(
            Message.RecipientType.TO, InternetAddress.parse(emails));
        message.setSubject("1st app");

        String msg = "Hello)";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
        mimeBodyPart.attachFile(new File(Main.class.getResource("cats.png").toURI()));

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }
}
