package lenid.util;

import lenid.model.Vcf;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.QuotedPrintableCodec;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VcfConverter {
    private static final String BEGIN = "BEGIN:VCARD";
    private static final String VERSION = "VERSION:2.1";
    private static final String NAME_KEY = "N;CHARSET=UTF-8;ENCODING=QUOTED-PRINTABLE:";
    private static final String TEL_KEY = "TEL:";
    private static final String END = "END:VCARD";
    private static final String CRLF = "\r\n";

    private static final QuotedPrintableCodec codec = new QuotedPrintableCodec();

    public static String getWritableString(List<Vcf> vcfList) {
        return vcfList.stream().map(vcf -> getWritableString(vcf)).collect(Collectors.joining(CRLF));
    }

    public static String getWritableString(Vcf vcf) {
        List<String> lines = new ArrayList<>();

        lines.add(BEGIN);
        lines.add(VERSION);
        lines.add(NAME_KEY + convertName(vcf));
        vcf.getTel().stream().forEach(t -> lines.add(TEL_KEY + t));
        lines.add(END);

        return lines.stream().collect(Collectors.joining(CRLF));
    }

    private static String convertName(Vcf vcf) {
        String name = Stream.of(vcf.getName(), vcf.getOrg())
                .filter(str -> str != null && !"".equals(str))
                .collect(Collectors.joining(" "));

        try {
            name = codec.encode(name);
        } catch (EncoderException e) {
            e.printStackTrace();
            return name;
        }

        return name;
    }

}