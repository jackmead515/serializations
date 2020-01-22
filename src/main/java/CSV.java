import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class CSV {
    public CSV() {
    }

    public static Long run(File data) throws IOException {
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            load(data);
        }
        return System.nanoTime() - start;
    }

    private static CSVParser load(File data) throws IOException {
        return CSVParser.parse(data, StandardCharsets.UTF_8, CSVFormat.RFC4180);
    }
}
