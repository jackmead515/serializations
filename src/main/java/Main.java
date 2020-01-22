import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        new Main();
    }

    public Main() throws Exception {
        this.runCSV();
    }

    public void runCSV() throws Exception {
        URL csvFolder = Main.class.getClassLoader().getResource("csv");
        File[] files = new File(csvFolder.getPath()).listFiles();

        double averageTime = Arrays.asList(files)
            .stream()
            .mapToInt((file) -> {
                try {
                    return Math.toIntExact(CSV.run(file));
                } catch(Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                return 0;
            })
            .average()
            .getAsDouble();

        System.out.println("CSV: Average time: " + averageTime);
    }
}
