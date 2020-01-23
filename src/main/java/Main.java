import com.fasterxml.jackson.databind.JsonSerializable;

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
        this.runJSON();
        this.runProto();
        this.runAvro();
    }

    public void runCSV() throws Exception {
        CSV csv = new CSV();
        csv.run();
        System.out.println("==| CSV |==");
        System.out.println(String.format("Data Size: %s bytes", csv.getDataSize()));
        System.out.println(String.format("Encode Time: %s ms", csv.getEncodeTime()/1000000));
        System.out.println(String.format("Parse Time: %s ms", csv.getParseTime()/1000000));
    }

    public void runJSON() throws Exception {
        JSON json = new JSON();
        json.run();
        System.out.println("==| JSON |==");
        System.out.println(String.format("Data Size: %s bytes", json.getDataSize()));
        System.out.println(String.format("Encode Time: %s ms", json.getEncodeTime()/1000000));
        System.out.println(String.format("Parse Time: %s ms", json.getParseTime()/1000000));
    }

    public void runProto() throws Exception {
        ProtoBuf proto = new ProtoBuf();
        proto.run();
        System.out.println("==| ProtoBuf |==");
        System.out.println(String.format("Data Size: %s bytes", proto.getDataSize()));
        System.out.println(String.format("Encode Time: %s ms", proto.getEncodeTime()/1000000));
        System.out.println(String.format("Parse Time: %s ms", proto.getParseTime()/1000000));
    }

    public void runAvro() throws Exception {
        Avro avro = new Avro();
        avro.run();
        System.out.println("==| Avro |==");
        System.out.println(String.format("Data Size: %s bytes", avro.getDataSize()));
        System.out.println(String.format("Encode Time: %s ms", avro.getEncodeTime()/1000000));
        System.out.println(String.format("Parse Time: %s ms", avro.getParseTime()/1000000));
    }
}
