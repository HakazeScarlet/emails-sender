import email_sender.EmailSender;
import org.apache.log4j.Logger;
import parser.Recipient;
import parser.RecipientCsvParser;
import session.GmailSessionProvider;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MainApplication {

    private static final int SPAM_NUMBER = 3;
    private final static Logger logger = Logger.getLogger(MainApplication.class);

    public static void main(String[] args) {
        RecipientCsvParser recipientCsvParser = new RecipientCsvParser();
        List<Recipient> recipients = recipientCsvParser.parse();

        EmailSender emailSender = new EmailSender(new GmailSessionProvider());

        for (int i = 1; i < SPAM_NUMBER; i++) {
            File attachment = getRandomResource("/images");

            emailSender.send(
                recipients,
                i + " image with cats",
                "Hello. Please see the attachment",
                attachment
            );

            logger.info("Email with number " + i + " has been sent");
        }
    }

    private static File getRandomResource(String pathToResources) {
        try {
            URI uri = MainApplication.class.getResource(pathToResources).toURI();

            List<File> collect = Files.walk(Path.of(uri))
                .map(Path::toFile)
                .filter(file -> !file.isDirectory())
                .collect(Collectors.toList());

            return getRandom(collect);
        } catch (IOException e) {
            throw new ResourceParsingException("Resources not found", e);
        } catch (URISyntaxException e) {
            throw new ResourceParsingException("Unable to read resource", e);
        }
    }

    public static File getRandom(List<File> files) {
        Random random = new Random();
        return files.get(random.nextInt(files.size()));
    }

    private static final class ResourceParsingException extends RuntimeException {

        public ResourceParsingException(String message, Exception e) {
            super(message, e);
        }
    }
}
