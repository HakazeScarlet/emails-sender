package email_sender;

import org.apache.log4j.Logger;
import parser.Recipient;
import session.SessionProvider;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class EmailSender {

    private static final String RECIPIENT_EMAILS_DELIMITER = ",";
    private static final Logger logger = Logger.getLogger(EmailSender.class);

    private final SessionProvider sessionProvider;

    public EmailSender(SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

//    public void send(List<Recipient> recipients, String subject, String message) throws Exception {
//
//    }

    public void send(List<Recipient> recipients, String subject, String message, File attachment) {
        Message mimeMessage = new MimeMessage(sessionProvider.get());
        try {
            mimeMessage.setFrom(new InternetAddress(System.getenv("EMAIL"))); // System.getenv().getOrDefault("EMAIL", "anotherDefaultEmail@gmail.com");
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(extractEmails(recipients)));
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(buildEmailBody(message, attachment));
            send(mimeMessage);
        } catch (AddressException e) {
            throw new EmailAddressException("Missing sender or recipient emails", e);
        } catch (MessagingException e) {
            throw new EmailBodyException("Incorrect message content or subject", e);
        } catch (Exception e) {
            logger.error("Something went wrong", e);
//            Thread.sleep(5000);
            send(mimeMessage);
        }
    }

    private void send(Message mimeMessage) {
        try {
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new SendingEmailException("Something went wrong during email sending", e);
        }
    }

    private Multipart buildEmailBody(String message, File attachment) throws MessagingException {
        try {
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(message, "text/html; charset=utf-8");
            mimeBodyPart.attachFile(attachment);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            return multipart;
        } catch (IOException e) {
            throw new AttachmentAccessException("Cannot access to attachment", e);
        }
    }

    private String extractEmails(List<Recipient> recipients) {
         return recipients.stream()
            .map(recipient -> recipient.getEmail())
            .collect(Collectors.joining(RECIPIENT_EMAILS_DELIMITER));
    }

    private static final class EmailAddressException extends RuntimeException {

        public EmailAddressException(String message, Exception e) {
            super(message, e);
        }
    }

    private static final class EmailBodyException extends RuntimeException {

        public EmailBodyException(String message, Exception e) {
            super(message, e);
        }
    }

    private static final class AttachmentAccessException extends RuntimeException {

        public AttachmentAccessException(String message, Exception e) {
            super(message, e);
        }
    }

    private static final class SendingEmailException extends RuntimeException {

        public SendingEmailException(String message, Exception e) {
            super(message, e);
        }
    }
}
