import email_sender.EmailSender;
import org.apache.log4j.Logger;
import parser.Recipient;
import parser.RecipientCsvParser;
import session.GmailSessionProvider;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class MainApplication {

    private static final int SPAM_NUMBER = 1;
    private final static Logger logger = Logger.getLogger(MainApplication.class);

    public static void main(String[] args) {
        RecipientCsvParser recipientCsvParser = new RecipientCsvParser();
        List<Recipient> recipients = recipientCsvParser.parse();

        EmailSender emailSender = new EmailSender(new GmailSessionProvider());

        for (int i = 0; i < SPAM_NUMBER; i++) {
            emailSender.send(
                recipients,
                "Image with cats " + i,
                "Hello. Please see the attachment",
                getRandomResource("resources")
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

    private static Optional<File> getRandomResource(String pathToResources) {
        try {
            new File(MainApplication.class.getResource(pathToResources).toURI());
            return Files.walk(Path.of(pathToResources))
                    .map(Path::toFile)
                    .findAny();
        } catch (IOException e) {
            throw new ResourcesFindException("Resources not found", e);
        } catch (URISyntaxException e) {
        throw new ResourceReadingException("Unable to read resource", e);
        }
    }

    private static final class ResourceReadingException extends RuntimeException {

        public ResourceReadingException(String message, Exception e) {
            super(message, e);
        }
    }

    private static final class ResourcesFindException extends RuntimeException {

        public ResourcesFindException(String message, Exception e) {
            super(message, e);
        }
    }
}
