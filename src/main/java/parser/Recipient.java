package parser;

import com.opencsv.bean.CsvBindByPosition;

import java.util.Objects;

public class Recipient {

    @CsvBindByPosition(position = 0)
    private String name;

    @CsvBindByPosition(position = 1)
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recipient recipient = (Recipient) o;
        return Objects.equals(name, recipient.name) && Objects.equals(email, recipient.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }

    @Override
    public String toString() {
        return "parser.Recipient{" +
            "name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
