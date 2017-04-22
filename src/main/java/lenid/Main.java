package lenid;

import lenid.model.Vcf;
import lenid.rw.Reader;
import lenid.rw.Writer;
import lenid.util.VcfConverter;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        List<Vcf> contacts = Reader.read("src.vcf");
        String data = VcfConverter.getWritableString(contacts);
        Writer.write(data, "dest.vcf");
    }

}
