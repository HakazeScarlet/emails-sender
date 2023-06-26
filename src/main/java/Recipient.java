import com.opencsv.bean.CsvBindByPosition;

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
    public String toString() {
        return "Recipient{" +
            "name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
