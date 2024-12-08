import email_sender.EmailSender;
import org.apache.log4j.Logger;
import parser.Recipient;
import parser.RecipientCsvParser;
import session.MailhogSessionProvider;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainApplication {

    private static final int SPAM_NUMBER = 3;
    private static final int THREAD_NUMBER = 7;
    private static final Logger logger = Logger.getLogger(MainApplication.class);

    public static void main(String[] args) {
        RecipientCsvParser recipientCsvParser = new RecipientCsvParser();
        List<Recipient> recipients = recipientCsvParser.parse("emails.csv");

        EmailSender emailSender = new EmailSender(new MailhogSessionProvider());

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUMBER);
        Runnable runnable = () -> spam(recipients, emailSender);
        executorService.submit(runnable);
//        executorService.awaitTermination(5, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    private static void spam(List<Recipient> recipients, EmailSender emailSender) {
        for (int i = 1; i <= SPAM_NUMBER; i++) {
            File attachment = ResourceUtil.getRandomResource("/images");
            emailSender.send(
                recipients,
                "Image with cats " + LocalDateTime.now(),
                "Hello. Please see the attachment",
                attachment
            );
            logger.info("Email with number " + i + " has been sent");
        }
    }
}
