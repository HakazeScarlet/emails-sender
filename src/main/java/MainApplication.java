import email_sender.EmailSender;
import parser.Recipient;
import parser.RecipientCsvParser;
import session.SessionProvider;

import java.io.File;
import java.util.List;

public class MainApplication {

    public static void main(String[] args) {
        RecipientCsvParser recipientCsvParser = new RecipientCsvParser();
        List<Recipient> recipients = recipientCsvParser.parse();

        EmailSender emailSender = new EmailSender(new SessionProvider());

        File attachment = new File(MainApplication.class.getResource("cats.png").toURI());
        emailSender.send(
            recipients,
            "Image with cats",
            "Hello. Please see the attachment",
            attachment
        );

//        emailSender.send(
//            otherRecipients,
//            "Image with food",
//            "Hello...",
//            new File(Main.class.getResource("apples.png").toURI())
//        );
    }

    private static File getResource(String path) {
        return new File(MainApplication.class.getResource("cats.png").toURI());
    }
}
