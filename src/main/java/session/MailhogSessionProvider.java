package session;

import java.util.Properties;

public class MailhogSessionProvider extends SessionProvider {

    private static final Properties properties = getProperties();

    public MailhogSessionProvider() {
        super(properties);
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "localhost");
        properties.put("mail.smtp.port", 1025);
        return properties;
    }
}
