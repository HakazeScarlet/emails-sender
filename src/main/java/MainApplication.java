import email_sender.EmailSender;
import parser.Recipient;
import parser.RecipientCsvParser;
import session.SessionProvider;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

public class MainApplication {

    public static void main(String[] args) {
        RecipientCsvParser recipientCsvParser = new RecipientCsvParser();
        List<Recipient> recipients = recipientCsvParser.parse();

        EmailSender emailSender = new EmailSender(new SessionProvider());

        emailSender.send(
            recipients,
            "Image with cats",
            "Hello. Please see the attachment",
            getResource("cats.png")
        );

//        emailSender.send(
//            recipients1,
//            "Image with dogs",
//            "Hello. Please see the attachment",
//            getResource("dog.png")
//        );
//
//        emailSender.send(
//            recipients2,
//            "Image with mouse",
//            "Hello. Please see the attachment",
//            getResource("mouse.png")
//        );
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
