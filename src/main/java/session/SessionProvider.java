package session;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public abstract class SessionProvider {

    private final Properties properties;

    protected SessionProvider(Properties properties) {
        this.properties = properties;
    }

    public Session get() {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(System.getenv("EMAIL"), System.getenv("TOKEN"));
            }
        });
    }
}
