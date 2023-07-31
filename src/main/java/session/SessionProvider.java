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

    // TODO: identify when the session has expired and a new one needs to be created to fix exception below
    // Caused by: javax.mail.AuthenticationFailedException: 454 4.7.0 Too many login attempts, please try again later. b8-20020a2e8488000000b002b6f1afd00esm2510417ljh.107 - gsmtp
    public Session get() {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(System.getenv("EMAIL"), System.getenv("TOKEN"));
            }
        });
    }
}
