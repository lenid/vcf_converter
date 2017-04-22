package lenid.util;

import lenid.model.Vcf;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VcfParser {
    private static final String NAME_KEY = "N:";
    private static final String TEL_KEY = "TEL";
    private static final String ORG_KEY = "ORG:";
    private static final String ITEM_KEY = "item";

    public static Vcf getVcf(List<String> lines) {
        Vcf vcf = new Vcf();
        String nameLine = null;
        String orgLine = null;
        List<String> tel = new ArrayList<String>();

        for (String line : lines) {
            if (isTheStr(line, NAME_KEY)) {
                nameLine = getName(line);
            } else if (isTheStr(line, ORG_KEY)) {
                orgLine = getName(line);
            } else if (isTheStr(line, TEL_KEY) || isTheStr(line, ITEM_KEY)) {
                addTelToList(tel, line);
            }
        }

        vcf.setName(nameLine);
        vcf.setOrg(orgLine);
        vcf.setTel(tel);

        return vcf;
    }

    private static String getName(String line) {
        String[] split_1 = line.split(":");
        
        if (split_1.length != 2) {
            throw new RuntimeException();
        }

        String[] split_2 = split_1[1].split(";");

        String res = Stream.of(split_2)
                .filter(str -> !"".equals(str))
                .collect(Collectors.joining(" "));

        return res;
    }

    private static void addTelToList(List<String> tel, String line) {
        String[] split_1 = line.split(TEL_KEY);

        if (split_1.length != 2) {
            return;
        }

        String[] split_2 = split_1[1].split(":");

        if (split_2.length != 2) {
            throw new RuntimeException();
        }

        tel.add(split_2[1]);
    }

    private static boolean isTheStr(String str, String prefix) {
        int prefixLength = prefix.length();

        if (prefixLength > str.length()) {
            return false;
        }

        return prefix.equals(str.substring(0, prefixLength));
    }

}