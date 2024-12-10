package parser;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class RecipientCsvParser {

    public List<Recipient> parse(String fileName) {
        try {
            Path path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI());
            return new CsvToBeanBuilder<Recipient>(new FileReader(path.toFile()))
                .withType(Recipient.class)
                .withSkipLines(1)
                .build()
                .parse();
        } catch (Exception e) {
            throw new CsvParsingException("Cannot parse CSV file or the file path is incorrect", e);
        }
    }

    public static final class CsvParsingException extends RuntimeException {

        public CsvParsingException(String message, Exception e) {
            super(message, e);
        }
    }
}
