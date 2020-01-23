import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Util {

    public static String random(int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    public static int random() {
        return new Random().nextInt();
    }

}
