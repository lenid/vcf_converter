
package lenid.rw;

import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public static void write(String data, String fileName) {
        FileWriter writer = null;

        try {
            writer = new FileWriter(fileName, false);
            writer.write(data);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}