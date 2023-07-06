package email_sender;

import parser.Recipient;
import session.SessionProvider;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class EmailSender {

    private SessionProvider sessionProvider;

    public EmailSender(SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    public void send(List<Recipient> recipients) throws Exception {
        Message message = new MimeMessage(sessionProvider.get());
        message.setFrom(new InternetAddress(System.getenv("EMAIL")));
        message.setRecipients(
            Message.RecipientType.TO, InternetAddress.parse(extractEmails(recipients)));
        message.setSubject("1st app");

        String msg = "Hello)";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
        mimeBodyPart.attachFile(new File(getClass().getResource("cats.png").toURI()));

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

    public String extractEmails(List<Recipient> recipients) {
         return recipients.stream()
            .map(recipient -> recipient.getEmail())
            .collect(Collectors.joining(","));
    }
}
