package lenid.rw;

import lenid.model.Vcf;
import lenid.util.VcfParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andi on 17.04.2017.
 */
public class Reader {

    private static final String BEGIN = "BEGIN:VCARD";
    private static final String END = "END:VCARD";

    public static List<Vcf> read(String path) {
        BufferedReader br = null;
        List<String> lines = null;
        List<Vcf> contacts = new ArrayList<Vcf>();
        String sCurrentLine;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));

            while ((sCurrentLine = br.readLine()) != null) {
                if (BEGIN.equals(sCurrentLine)) {
                    lines = new ArrayList<String>();
                } else if (END.equals(sCurrentLine)) {
                    Vcf vcf = VcfParser.getVcf(lines);
                    contacts.add(vcf);
                } else {
                    lines.add(sCurrentLine);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return contacts;
        }
    }

}
