import email_sender.EmailSender;
import parser.Recipient;
import parser.RecipientCsvParser;
import session.SessionProvider;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        RecipientCsvParser recipientCsvParser = new RecipientCsvParser();
        List<Recipient> recipients = recipientCsvParser.parse();

        EmailSender emailSender = new EmailSender(new SessionProvider());
        emailSender.send(recipients);
    }
}
