import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

public class ProtoBuf {

    private double parseTime;
    private double encodeTime;
    private byte[] data;
    private ArrayList<String> strings;
    private ArrayList<Integer> ints;

    public ProtoBuf() {
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

        this.data = Sample.Wrapper.newBuilder()
            .addAllName(this.strings)
            .addAllNumber(this.ints)
            .build()
            .toByteArray();
    }

    private void testEncode() throws IOException {
        ArrayList<Long> times = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();

           byte[] data = Sample.Wrapper.newBuilder()
                .addAllName(this.strings)
                .addAllNumber(this.ints)
                .build()
                .toByteArray();

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

            Sample.Wrapper p = Sample.Wrapper.parseFrom(this.data);

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

    public int base64Size() {
        return Base64.getEncoder().encode(this.data).length;
    }

    public int getDataSize() {
        return this.data.length;
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
