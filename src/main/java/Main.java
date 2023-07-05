import parser.Recipient;
import parser.RecipientCsvParser;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
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
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws MessagingException, URISyntaxException, IOException {
        SessionProvider sessionProvider = new SessionProvider();
        Session session = sessionProvider.get();

        RecipientCsvParser recipientCsvParser = new RecipientCsvParser();
        List<Recipient> recipients = recipientCsvParser.parse();

        String emails = recipients.stream()
            .map(recipient -> recipient.getEmail())
            .collect(Collectors.joining(","));

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(System.getenv("EMAIL")));
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
