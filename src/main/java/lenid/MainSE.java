package lenid;

import org.apache.commons.codec.net.QuotedPrintableCodec;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.List;

// The conversation type does not work
public class MainSE {
    public static void main(String[] args) throws Exception {

        BufferedReader br = null;
        List<String> lines = null;
        String sCurrentLine;
        String sEncodedLine;
        FileWriter writer = null;
        final QuotedPrintableCodec codec = new QuotedPrintableCodec();
        final String CRLF = "\r\n";

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("src.vcf"), "UTF-8"));
            writer = new FileWriter("dest.vcf", false);

            while ((sCurrentLine = br.readLine()) != null) {
                sEncodedLine = codec.encode(sCurrentLine) + CRLF;
                writer.write(sEncodedLine);
            }

            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (writer != null)
                    writer.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}