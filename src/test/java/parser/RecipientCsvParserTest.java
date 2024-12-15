package parser;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
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
    void whenCsvIsEmpty_returnEmptyList() {
        List<Recipient> actual = new RecipientCsvParser().parse("emptyFile.csv");
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    void whenCsvWithoutTitle_returnListWithoutFirstRecipient() {
        List<Recipient> expected = List.of(getRecipient("Second User", "second@email.com"));

        List<Recipient> actual = new RecipientCsvParser().parse("emailsWithoutFirstLine.csv");

        assertEquals(expected, actual);
    }

    @Test
    void whenCsvHasOneNotStandardLine_returnEmptyList() {
        List<Recipient> actual = new RecipientCsvParser().parse("notStandardFile.csv");
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    void whenCsvDoesNotExist_throwCsvParsingException() {
        assertThrows(
            RecipientCsvParser.CsvParsingException.class,
            () -> new RecipientCsvParser().parse("unknown.csv")
        );
    }

    private Recipient getRecipient(String name, String email) {
        Recipient recipient = new Recipient();
        recipient.setName(name);
        recipient.setEmail(email);
        return recipient;
    }
}