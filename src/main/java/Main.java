import com.opencsv.bean.CsvToBeanBuilder;

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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws MessagingException, URISyntaxException, FileNotFoundException {
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

        Path path = Path.of(Main.class.getResource("emails.csv").toURI());
        List<Recipient> recipients = new CsvToBeanBuilder<Recipient>(new FileReader(path.toFile()))
            .withType(Recipient.class)
            .withSkipLines(1)
            .build()
            .parse();

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

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }
}
