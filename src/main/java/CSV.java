import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSV {

    private double parseTime;
    private double encodeTime;
    private StringBuilder data;
    private ArrayList<String> strings;
    private ArrayList<Integer> ints;

    public CSV() {
        data = new StringBuilder();
        parseTime = 0;
        encodeTime = 0;
    }

    private void generate() throws IOException {
       this.strings = new ArrayList<>(Constants.DATA_SIZE);
        for (int i = 0; i < Constants.DATA_SIZE; i++) {
            this.strings.add(Util.random(Constants.STRING_SIZE));
        }

        this.ints = new ArrayList<>(Constants.DATA_SIZE);
        for(int i = 0; i < Constants.DATA_SIZE; i++) {
            this.ints.add(Util.random());
        }

        try (CSVPrinter printer = new CSVPrinter(this.data, CSVFormat.RFC4180)) {
            printer.printRecord("name", "number");
            for (int x = 0; x < Constants.DATA_SIZE; x++) {
                printer.printRecord(strings.get(x), ints.get(x));
            }
        }
    }

    private void testEncode() throws IOException {
        ArrayList<Long> times = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();
            try (CSVPrinter printer = new CSVPrinter(new StringBuilder(), CSVFormat.RFC4180)) {
                printer.printRecord("name", "number");
                for (int x = 0; x < Constants.DATA_SIZE; x++) {
                    printer.printRecord(strings.get(x), ints.get(x));
                }
            }
            times.add(System.nanoTime() - start);
        }

        double averageEncodeTime = times.stream()
            .mapToInt(Math::toIntExact)
            .average()
            .getAsDouble();

        this.setEncodeTime(averageEncodeTime);
    }

    private void testParse() throws IOException {
        ArrayList<Long> times = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();
            List<CSVRecord> list = CSVParser.parse(this.data.toString(), CSVFormat.RFC4180).getRecords();
            times.add(System.nanoTime() - start);
        }

        double averageParseTime = times.stream()
                .mapToInt(Math::toIntExact)
                .average()
                .getAsDouble();

        this.setParseTime(averageParseTime);
    }


    public void run() throws Exception {
        this.generate();
        this.testParse();
        this.testEncode();
    }

    public int getDataSize() {
        return this.data.toString().getBytes().length;
    }

    public double getParseTime() {
        return parseTime;
    }

    public void setParseTime(double parseTime) {
        this.parseTime = parseTime;
    }

    public double getEncodeTime() {
        return encodeTime;
    }

    public void setEncodeTime(double encodeTime) {
        this.encodeTime = encodeTime;
    }
}
