import email_sender.EmailSender;
import org.apache.log4j.Logger;
import parser.Recipient;
import parser.RecipientCsvParser;
import session.SessionProvider;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

public class MainApplication {

    private static final int SPAM_NUMBER = 3;
    private final static Logger logger = Logger.getLogger(MainApplication.class);

    public static void main(String[] args) {
        RecipientCsvParser recipientCsvParser = new RecipientCsvParser();
        List<Recipient> recipients = recipientCsvParser.parse();

        EmailSender emailSender = new EmailSender(new SessionProvider());

        for (int i = 0; i < SPAM_NUMBER; i++) {
            emailSender.send(
                recipients,
                "Image with cats " + i,
                "Hello. Please see the attachment",
                getResource("cats.png")
            );

            logger.info("Email with number " + i + " has been sent");
        }
    }

    private static File getResource(String path) {
        try {
            return new File(MainApplication.class.getResource(path).toURI());
        } catch (URISyntaxException e) {
            throw new ResourceReadingException("Unable to read resource", e);
        }
    }

    private static final class ResourceReadingException extends RuntimeException {

        public ResourceReadingException(String message, Exception e) {
            super(message, e);
        }
    }
}
