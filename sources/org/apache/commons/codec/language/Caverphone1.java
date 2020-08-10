package org.apache.commons.codec.language;

import androidx.exifinterface.media.ExifInterface;
import java.util.Locale;

public class Caverphone1 extends AbstractCaverphone {
    private static final String SIX_1 = "111111";

    public String encode(String str) {
        String str2 = SIX_1;
        if (str == null || str.length() == 0) {
            return str2;
        }
        String str3 = "";
        String str4 = "k";
        String replaceAll = str.toLowerCase(Locale.ENGLISH).replaceAll("[^a-z]", str3).replaceAll("^cough", "cou2f").replaceAll("^rough", "rou2f").replaceAll("^tough", "tou2f").replaceAll("^enough", "enou2f").replaceAll("^gn", "2n").replaceAll("mb$", "m2").replaceAll("cq", "2q").replaceAll("ci", "si").replaceAll("ce", "se").replaceAll("cy", "sy").replaceAll("tch", "2ch").replaceAll("c", str4).replaceAll("q", str4).replaceAll("x", str4).replaceAll("v", "f").replaceAll("dg", "2g").replaceAll("tio", "sio").replaceAll("tia", "sia").replaceAll("d", "t").replaceAll("ph", "fh").replaceAll("b", TtmlNode.TAG_P).replaceAll("sh", "s2").replaceAll("z", "s");
        String str5 = ExifInterface.GPS_MEASUREMENT_IN_PROGRESS;
        String replaceAll2 = replaceAll.replaceAll("^[aeiou]", str5);
        String str6 = ExifInterface.GPS_MEASUREMENT_3D;
        String replaceAll3 = replaceAll2.replaceAll("[aeiou]", str6).replaceAll("3gh3", "3kh3").replaceAll("gh", "22").replaceAll("g", str4).replaceAll("s+", ExifInterface.LATITUDE_SOUTH).replaceAll("t+", ExifInterface.GPS_DIRECTION_TRUE).replaceAll("p+", "P").replaceAll("k+", "K").replaceAll("f+", "F").replaceAll("m+", "M").replaceAll("n+", "N").replaceAll("w3", "W3").replaceAll("wy", "Wy").replaceAll("wh3", "Wh3").replaceAll("why", "Why");
        String str7 = ExifInterface.GPS_MEASUREMENT_2D;
        String replaceAll4 = replaceAll3.replaceAll("w", str7).replaceAll("^h", str5).replaceAll("h", str7).replaceAll("r3", "R3").replaceAll("ry", "Ry").replaceAll("r", str7).replaceAll("l3", "L3").replaceAll("ly", "Ly").replaceAll("l", str7);
        String str8 = "y";
        String replaceAll5 = replaceAll4.replaceAll("j", str8).replaceAll("y3", "Y3").replaceAll(str8, str7).replaceAll(str7, str3).replaceAll(str6, str3);
        StringBuilder sb = new StringBuilder();
        sb.append(replaceAll5);
        sb.append(str2);
        return sb.toString().substring(0, 6);
    }
}
