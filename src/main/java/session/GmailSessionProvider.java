package session;

import java.util.Properties;

public class GmailSessionProvider extends SessionProvider {

    private static final Properties properties = getProperties(); // step 2

    public GmailSessionProvider() { // step 3
        super(properties);
    }

    private static Properties getProperties() { // step 1
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.starttls.required", true);
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.EnableSSL.enable", true);
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return properties;
    }
}
