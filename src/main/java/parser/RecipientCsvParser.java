package parser;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class RecipientCsvParser {

    private static final String EMAILS_CSV = "emails.csv";

    public List<Recipient> parse() {
        try {
            Path path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource(EMAILS_CSV)).toURI());
            return new CsvToBeanBuilder<Recipient>(new FileReader(path.toFile()))
                .withType(Recipient.class)
                .withSkipLines(1)
                .build()
                .parse();
        } catch (Exception e) {
            throw new CsvParsingException("Cannot parse CSV file", e);
        }
    }

    private static final class CsvParsingException extends RuntimeException {

        public CsvParsingException(String message, Exception e) {
            super(message, e);
        }
    }
}
