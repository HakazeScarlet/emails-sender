package parser;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecipientCsvParserTest {

    @Test
    void whenCsvHasRecipients_returnRecipientsList() {
        List<Recipient> expected = new ArrayList<>();
        expected.add(getRecipient("First User", "first@email.com"));
        expected.add(getRecipient("Second User", "second@email.com"));

        List<Recipient> actual = new RecipientCsvParser().parse("emails.csv");

        assertEquals(expected, actual);
    }

    @Test
    void whenCsvIsEmpty_returnEmptyRecipientsList() {
        List<Recipient> actual = new RecipientCsvParser().parse("emptyFile.csv");
        assertEquals(new ArrayList<Recipient>(), actual);
    }

    @Test
    void whenCsvWithoutFirstLine_returnListWithoutFirstRecipient() {
        List<Recipient> expected = new ArrayList<>();
        expected.add(getRecipient("Second User", "second@email.com"));

        List<Recipient> actual = new RecipientCsvParser().parse("emailsWithoutFirstLine.csv");

        assertEquals(expected, actual);
    }

    @Test
    void whenCsvHasOnceRandomLine_returnEmptyRecipientList() {
        List<Recipient> actual = new RecipientCsvParser().parse("randomFile.csv");
        assertEquals(new ArrayList<Recipient>(), actual);
    }

    @Test
    void whenCsvDoesNotExist_throwCsvParsingException() {
        assertThrows(
            RecipientCsvParser.CsvParsingException.class,
            () -> new RecipientCsvParser().parse("voidName.csv")
        );
    }

    private Recipient getRecipient(String name, String email) {
        Recipient recipient = new Recipient();
        recipient.setName(name);
        recipient.setEmail(email);
        return recipient;
    }
}