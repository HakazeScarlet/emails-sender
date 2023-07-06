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

    private final SessionProvider sessionProvider;

    public EmailSender(SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

//    public void send(List<Recipient> recipients, String subject, String message) throws Exception {
//
//    }

    public void send(List<Recipient> recipients, String subject, String message, File attachment) throws Exception {
        Message mimeMessage = new MimeMessage(sessionProvider.get());
        mimeMessage.setFrom(new InternetAddress(System.getenv("EMAIL")));
        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(extractEmails(recipients)));
        mimeMessage.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(message, "text/html; charset=utf-8");
        mimeBodyPart.attachFile(attachment);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        mimeMessage.setContent(multipart);

        Transport.send(mimeMessage);
    }

    private String extractEmails(List<Recipient> recipients) {
         return recipients.stream()
            .map(recipient -> recipient.getEmail())
            .collect(Collectors.joining(","));
    }
}
