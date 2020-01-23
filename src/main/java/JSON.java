import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSON {

    private double parseTime;
    private double encodeTime;
    private StringBuilder data;
    private ArrayList<String> strings;
    private ArrayList<Integer> ints;

    class Wrapper {
        ArrayList<String> name;
        ArrayList<Integer> number;
        public Wrapper(ArrayList<String> names, ArrayList<Integer> numbers) {
            this.name = names;
            this.number = numbers;
        }
    }

    public JSON() {
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

        Gson json = new Gson();
        this.data = new StringBuilder(json.toJson(new Wrapper(this.strings, this.ints)));
    }

    private void testEncode() throws IOException {
        ArrayList<Long> times = new ArrayList<>(1000);
        Gson json = new Gson();
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();
            String data = json.toJson(new Wrapper(this.strings, this.ints));
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
        Gson json = new Gson();
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();
            Wrapper p = json.fromJson(this.data.toString(), Wrapper.class);
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
