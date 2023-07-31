import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class ResourceUtil {

    private ResourceUtil() {
        // hide public constructor
    }

    public static File getRandomResource(String pathToResources) {
        try {
            URI uri = Objects.requireNonNull(ResourceUtil.class.getResource(pathToResources)).toURI();

            // TODO: write another implementation (in 2-3 months)
            // TODO: use try-with-resources for walk() method
            List<File> files = Files.walk(Path.of(uri))
                .map(Path::toFile)
                .filter(file -> !file.isDirectory())
                .collect(Collectors.toList());

            return getRandom(files);
        } catch (IOException e) {
            throw new ResourceParsingException("Resources not found", e);
        } catch (URISyntaxException e) {
            throw new ResourceParsingException("Unable to read resource", e);
        }
    }

    private static File getRandom(List<File> files) {
        return files.get(new Random().nextInt(files.size()));
    }

    private static final class ResourceParsingException extends RuntimeException {

        public ResourceParsingException(String message, Exception e) {
            super(message, e);
        }
    }
}
