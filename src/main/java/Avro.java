import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Avro {

    private double parseTime;
    private double encodeTime;
    private ByteBuffer data;
    private ArrayList<CharSequence> strings;
    private ArrayList<Integer> ints;

    public Avro() {
        parseTime = 0;
        encodeTime = 0;
    }

    private void generate() throws IOException {
        this.strings = new ArrayList<>(Constants.DATA_SIZE);
        for (int i = 0; i < Constants.DATA_SIZE; i++) {
            CharSequence cs = Util.random(Constants.STRING_SIZE);
            this.strings.add(cs);
        }

        this.ints = new ArrayList<>(Constants.DATA_SIZE);
        for(int i = 0; i < Constants.DATA_SIZE; i++) {
            this.ints.add(Util.random());
        }

        this.data = AvroSample.newBuilder()
                .setName(this.strings)
                .setNumber(this.ints)
                .build()
                .toByteBuffer();
    }

    private void testEncode() throws IOException {
        ArrayList<Long> times = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();

            byte[] data = AvroSample.newBuilder()
                    .setName(this.strings)
                    .setNumber(this.ints)
                    .build()
                    .toByteBuffer()
                    .array();

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
        DatumReader<GenericRecord> datumReader = new SpecificDatumReader<>(AvroSample.getClassSchema());
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();

            AvroSample s = AvroSample.fromByteBuffer(this.data);

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
        return this.data.array().length;
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
