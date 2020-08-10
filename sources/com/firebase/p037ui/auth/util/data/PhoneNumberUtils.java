package com.firebase.p037ui.auth.util.data;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.core.view.InputDeviceCompat;
import com.firebase.p037ui.auth.data.model.CountryInfo;
import com.firebase.p037ui.auth.data.model.PhoneNumber;
import com.google.android.exoplayer2.extractor.p040ts.PsExtractor;
import com.google.logging.type.LogSeverity;
import com.twitter.sdk.android.core.internal.TwitterApiConstants.Errors;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.http.HttpStatus;
import org.slf4j.Marker;

/* renamed from: com.firebase.ui.auth.util.data.PhoneNumberUtils */
public final class PhoneNumberUtils {
    private static Map<String, Integer> COUNTRY_TO_ISO_CODES = null;
    private static final SparseArray<List<String>> COUNTRY_TO_REGION_CODES = createCountryCodeToRegionCodeMap();
    private static final CountryInfo DEFAULT_COUNTRY = new CountryInfo(DEFAULT_LOCALE, 1);
    private static final String DEFAULT_COUNTRY_CODE = String.valueOf(1);
    private static final int DEFAULT_COUNTRY_CODE_INT = 1;
    private static final Locale DEFAULT_LOCALE = Locale.US;
    private static final int MAX_COUNTRIES = 248;
    private static final int MAX_COUNTRY_CODES = 215;
    private static final int MAX_LENGTH_COUNTRY_CODE = 3;

    public static String format(String str, CountryInfo countryInfo) {
        String str2 = Marker.ANY_NON_NULL_MARKER;
        if (str.startsWith(str2)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(String.valueOf(countryInfo.getCountryCode()));
        sb.append(str.replaceAll("[^\\d.]", ""));
        return sb.toString();
    }

    public static String formatUsingCurrentCountry(String str, Context context) {
        return format(str, getCurrentCountryInfo(context));
    }

    public static CountryInfo getCurrentCountryInfo(Context context) {
        Locale simBasedLocale = getSimBasedLocale(context);
        if (simBasedLocale == null) {
            simBasedLocale = getOSLocale();
        }
        if (simBasedLocale == null) {
            return DEFAULT_COUNTRY;
        }
        Integer countryCode = getCountryCode(simBasedLocale.getCountry());
        return countryCode == null ? DEFAULT_COUNTRY : new CountryInfo(simBasedLocale, countryCode.intValue());
    }

    public static PhoneNumber getPhoneNumber(String str) {
        String str2 = DEFAULT_COUNTRY_CODE;
        String country = DEFAULT_LOCALE.getCountry();
        if (str.startsWith(Marker.ANY_NON_NULL_MARKER)) {
            str2 = getCountryCodeForPhoneNumberOrDefault(str);
            country = getCountryIsoForCountryCode(str2);
            str = stripCountryCode(str, str2);
        }
        return new PhoneNumber(str, country, str2);
    }

    public static boolean isValid(String str) {
        return str.startsWith(Marker.ANY_NON_NULL_MARKER) && getCountryCodeForPhoneNumber(str) != null;
    }

    public static boolean isValidIso(String str) {
        return getCountryCode(str) != null;
    }

    public static PhoneNumber getPhoneNumber(String str, String str2) {
        Integer countryCode = getCountryCode(str);
        if (countryCode == null) {
            countryCode = Integer.valueOf(1);
            str = DEFAULT_COUNTRY_CODE;
        }
        return new PhoneNumber(stripPlusSign(str2), str, String.valueOf(countryCode));
    }

    public static Integer getCountryCode(String str) {
        if (COUNTRY_TO_ISO_CODES == null) {
            initCountryCodeByIsoMap();
        }
        if (str == null) {
            return null;
        }
        return (Integer) COUNTRY_TO_ISO_CODES.get(str.toUpperCase(Locale.getDefault()));
    }

    public static Map<String, Integer> getImmutableCountryIsoMap() {
        if (COUNTRY_TO_ISO_CODES == null) {
            initCountryCodeByIsoMap();
        }
        return COUNTRY_TO_ISO_CODES;
    }

    private static String getCountryIsoForCountryCode(String str) {
        List list = (List) COUNTRY_TO_REGION_CODES.get(Integer.parseInt(str));
        if (list != null) {
            return (String) list.get(0);
        }
        return DEFAULT_LOCALE.getCountry();
    }

    public static List<String> getCountryIsosFromCountryCode(String str) {
        if (!isValid(str)) {
            return null;
        }
        return (List) COUNTRY_TO_REGION_CODES.get(Integer.parseInt(str.substring(1)));
    }

    private static String getCountryCodeForPhoneNumber(String str) {
        String replaceFirst = str.replaceFirst("^\\+", "");
        int length = replaceFirst.length();
        int i = 1;
        while (i <= 3 && i <= length) {
            String substring = replaceFirst.substring(0, i);
            if (COUNTRY_TO_REGION_CODES.indexOfKey(Integer.valueOf(substring).intValue()) >= 0) {
                return substring;
            }
            i++;
        }
        return null;
    }

    private static String getCountryCodeForPhoneNumberOrDefault(String str) {
        String countryCodeForPhoneNumber = getCountryCodeForPhoneNumber(str);
        return countryCodeForPhoneNumber == null ? DEFAULT_COUNTRY_CODE : countryCodeForPhoneNumber;
    }

    private static String stripCountryCode(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("^\\+?");
        sb.append(str2);
        return str.replaceFirst(sb.toString(), "");
    }

    private static String stripPlusSign(String str) {
        return str.replaceFirst("^\\+?", "");
    }

    private static Locale getSimBasedLocale(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        String simCountryIso = telephonyManager != null ? telephonyManager.getSimCountryIso() : null;
        if (TextUtils.isEmpty(simCountryIso)) {
            return null;
        }
        return new Locale("", simCountryIso);
    }

    private static Locale getOSLocale() {
        return Locale.getDefault();
    }

    private static SparseArray<List<String>> createCountryCodeToRegionCodeMap() {
        SparseArray<List<String>> sparseArray = new SparseArray<>(MAX_COUNTRY_CODES);
        sparseArray.put(1, Arrays.asList(new String[]{"US", "AG", "AI", "AS", "BB", "BM", "BS", "CA", "DM", "DO", "GD", "GU", "JM", "KN", "KY", "LC", "MP", "MS", "PR", "SX", "TC", "TT", "VC", "VG", "VI"}));
        sparseArray.put(7, Arrays.asList(new String[]{"RU", "KZ"}));
        sparseArray.put(20, Collections.singletonList("EG"));
        sparseArray.put(27, Collections.singletonList("ZA"));
        sparseArray.put(30, Collections.singletonList("GR"));
        sparseArray.put(31, Collections.singletonList("NL"));
        sparseArray.put(32, Collections.singletonList("BE"));
        sparseArray.put(33, Collections.singletonList("FR"));
        sparseArray.put(34, Collections.singletonList("ES"));
        sparseArray.put(36, Collections.singletonList("HU"));
        sparseArray.put(39, Collections.singletonList("IT"));
        sparseArray.put(40, Collections.singletonList("RO"));
        sparseArray.put(41, Collections.singletonList("CH"));
        sparseArray.put(43, Collections.singletonList("AT"));
        sparseArray.put(44, Arrays.asList(new String[]{"GB", "GG", "IM", "JE"}));
        sparseArray.put(45, Collections.singletonList("DK"));
        sparseArray.put(46, Collections.singletonList("SE"));
        sparseArray.put(47, Arrays.asList(new String[]{"NO", "SJ"}));
        sparseArray.put(48, Collections.singletonList("PL"));
        sparseArray.put(49, Collections.singletonList("DE"));
        sparseArray.put(51, Collections.singletonList("PE"));
        sparseArray.put(52, Collections.singletonList("MX"));
        sparseArray.put(53, Collections.singletonList("CU"));
        sparseArray.put(54, Collections.singletonList("AR"));
        sparseArray.put(55, Collections.singletonList("BR"));
        sparseArray.put(56, Collections.singletonList("CL"));
        sparseArray.put(57, Collections.singletonList("CO"));
        sparseArray.put(58, Collections.singletonList("VE"));
        sparseArray.put(60, Collections.singletonList("MY"));
        sparseArray.put(61, Arrays.asList(new String[]{"AU", "CC", "CX"}));
        sparseArray.put(62, Collections.singletonList("ID"));
        sparseArray.put(63, Collections.singletonList("PH"));
        sparseArray.put(64, Collections.singletonList("NZ"));
        sparseArray.put(65, Collections.singletonList("SG"));
        sparseArray.put(66, Collections.singletonList("TH"));
        sparseArray.put(81, Collections.singletonList("JP"));
        sparseArray.put(82, Collections.singletonList("KR"));
        sparseArray.put(84, Collections.singletonList("VN"));
        sparseArray.put(86, Collections.singletonList("CN"));
        sparseArray.put(90, Collections.singletonList("TR"));
        sparseArray.put(91, Collections.singletonList("IN"));
        sparseArray.put(92, Collections.singletonList("PK"));
        sparseArray.put(93, Collections.singletonList("AF"));
        sparseArray.put(94, Collections.singletonList("LK"));
        sparseArray.put(95, Collections.singletonList("MM"));
        sparseArray.put(98, Collections.singletonList("IR"));
        sparseArray.put(211, Collections.singletonList("SS"));
        sparseArray.put(212, Arrays.asList(new String[]{"MA", "EH"}));
        sparseArray.put(213, Collections.singletonList("DZ"));
        sparseArray.put(216, Collections.singletonList("TN"));
        sparseArray.put(218, Collections.singletonList("LY"));
        sparseArray.put(220, Collections.singletonList("GM"));
        sparseArray.put(221, Collections.singletonList("SN"));
        sparseArray.put(222, Collections.singletonList("MR"));
        sparseArray.put(223, Collections.singletonList("ML"));
        sparseArray.put(224, Collections.singletonList("GN"));
        sparseArray.put(225, Collections.singletonList("CI"));
        sparseArray.put(226, Collections.singletonList("BF"));
        sparseArray.put(227, Collections.singletonList("NE"));
        sparseArray.put(228, Collections.singletonList("TG"));
        sparseArray.put(229, Collections.singletonList("BJ"));
        sparseArray.put(230, Collections.singletonList("MU"));
        sparseArray.put(231, Collections.singletonList("LR"));
        sparseArray.put(232, Collections.singletonList("SL"));
        sparseArray.put(233, Collections.singletonList("GH"));
        sparseArray.put(234, Collections.singletonList("NG"));
        sparseArray.put(235, Collections.singletonList("TD"));
        sparseArray.put(236, Collections.singletonList("CF"));
        sparseArray.put(237, Collections.singletonList("CM"));
        sparseArray.put(238, Collections.singletonList("CV"));
        sparseArray.put(Errors.GUEST_AUTH_ERROR_CODE, Collections.singletonList("ST"));
        sparseArray.put(PsExtractor.VIDEO_STREAM_MASK, Collections.singletonList("GQ"));
        sparseArray.put(241, Collections.singletonList("GA"));
        sparseArray.put(242, Collections.singletonList("CG"));
        sparseArray.put(243, Collections.singletonList("CD"));
        sparseArray.put(244, Collections.singletonList("AO"));
        sparseArray.put(245, Collections.singletonList("GW"));
        sparseArray.put(246, Collections.singletonList("IO"));
        sparseArray.put(247, Collections.singletonList("AC"));
        sparseArray.put(MAX_COUNTRIES, Collections.singletonList("SC"));
        sparseArray.put(249, Collections.singletonList("SD"));
        sparseArray.put(250, Collections.singletonList("RW"));
        sparseArray.put(251, Collections.singletonList("ET"));
        sparseArray.put(252, Collections.singletonList("SO"));
        sparseArray.put(253, Collections.singletonList("DJ"));
        sparseArray.put(254, Collections.singletonList("KE"));
        sparseArray.put(255, Collections.singletonList("TZ"));
        sparseArray.put(256, Collections.singletonList("UG"));
        sparseArray.put(InputDeviceCompat.SOURCE_KEYBOARD, Collections.singletonList("BI"));
        sparseArray.put(258, Collections.singletonList("MZ"));
        sparseArray.put(260, Collections.singletonList("ZM"));
        sparseArray.put(261, Collections.singletonList("MG"));
        sparseArray.put(262, Arrays.asList(new String[]{"RE", "YT"}));
        sparseArray.put(263, Collections.singletonList("ZW"));
        sparseArray.put(264, Collections.singletonList("NA"));
        sparseArray.put(265, Collections.singletonList("MW"));
        sparseArray.put(266, Collections.singletonList("LS"));
        sparseArray.put(267, Collections.singletonList("BW"));
        sparseArray.put(268, Collections.singletonList("SZ"));
        sparseArray.put(269, Collections.singletonList("KM"));
        sparseArray.put(290, Arrays.asList(new String[]{"SH", "TA"}));
        sparseArray.put(291, Collections.singletonList("ER"));
        sparseArray.put(297, Collections.singletonList("AW"));
        sparseArray.put(298, Collections.singletonList("FO"));
        sparseArray.put(299, Collections.singletonList("GL"));
        sparseArray.put(350, Collections.singletonList("GI"));
        sparseArray.put(351, Collections.singletonList("PT"));
        sparseArray.put(352, Collections.singletonList("LU"));
        sparseArray.put(353, Collections.singletonList("IE"));
        sparseArray.put(354, Collections.singletonList("IS"));
        sparseArray.put(355, Collections.singletonList("AL"));
        sparseArray.put(356, Collections.singletonList("MT"));
        sparseArray.put(357, Collections.singletonList("CY"));
        sparseArray.put(358, Arrays.asList(new String[]{"FI", "AX"}));
        sparseArray.put(359, Collections.singletonList("BG"));
        sparseArray.put(370, Collections.singletonList("LT"));
        sparseArray.put(371, Collections.singletonList("LV"));
        sparseArray.put(372, Collections.singletonList("EE"));
        sparseArray.put(373, Collections.singletonList("MD"));
        sparseArray.put(374, Collections.singletonList("AM"));
        sparseArray.put(375, Collections.singletonList("BY"));
        sparseArray.put(376, Collections.singletonList("AD"));
        sparseArray.put(377, Collections.singletonList("MC"));
        sparseArray.put(378, Collections.singletonList("SM"));
        sparseArray.put(379, Collections.singletonList("VA"));
        sparseArray.put(380, Collections.singletonList("UA"));
        sparseArray.put(381, Collections.singletonList("RS"));
        sparseArray.put(382, Collections.singletonList("ME"));
        sparseArray.put(385, Collections.singletonList("HR"));
        sparseArray.put(386, Collections.singletonList("SI"));
        sparseArray.put(387, Collections.singletonList("BA"));
        sparseArray.put(389, Collections.singletonList("MK"));
        sparseArray.put(HttpStatus.SC_METHOD_FAILURE, Collections.singletonList("CZ"));
        sparseArray.put(421, Collections.singletonList("SK"));
        sparseArray.put(HttpStatus.SC_LOCKED, Collections.singletonList("LI"));
        sparseArray.put(500, Collections.singletonList("FK"));
        sparseArray.put(HttpStatus.SC_NOT_IMPLEMENTED, Collections.singletonList("BZ"));
        sparseArray.put(502, Collections.singletonList("GT"));
        sparseArray.put(503, Collections.singletonList("SV"));
        sparseArray.put(HttpStatus.SC_GATEWAY_TIMEOUT, Collections.singletonList("HN"));
        sparseArray.put(HttpStatus.SC_HTTP_VERSION_NOT_SUPPORTED, Collections.singletonList("NI"));
        sparseArray.put(506, Collections.singletonList("CR"));
        sparseArray.put(HttpStatus.SC_INSUFFICIENT_STORAGE, Collections.singletonList("PA"));
        sparseArray.put(508, Collections.singletonList("PM"));
        sparseArray.put(509, Collections.singletonList("HT"));
        sparseArray.put(590, Arrays.asList(new String[]{"GP", "BL", "MF"}));
        sparseArray.put(591, Collections.singletonList("BO"));
        sparseArray.put(592, Collections.singletonList("GY"));
        sparseArray.put(593, Collections.singletonList("EC"));
        sparseArray.put(594, Collections.singletonList("GF"));
        sparseArray.put(595, Collections.singletonList("PY"));
        sparseArray.put(596, Collections.singletonList("MQ"));
        sparseArray.put(597, Collections.singletonList("SR"));
        sparseArray.put(598, Collections.singletonList("UY"));
        sparseArray.put(599, Arrays.asList(new String[]{"CW", "BQ"}));
        sparseArray.put(670, Collections.singletonList("TL"));
        sparseArray.put(672, Collections.singletonList("NF"));
        sparseArray.put(673, Collections.singletonList("BN"));
        sparseArray.put(674, Collections.singletonList("NR"));
        sparseArray.put(675, Collections.singletonList("PG"));
        sparseArray.put(676, Collections.singletonList("TO"));
        sparseArray.put(677, Collections.singletonList("SB"));
        sparseArray.put(678, Collections.singletonList("VU"));
        sparseArray.put(679, Collections.singletonList("FJ"));
        sparseArray.put(680, Collections.singletonList("PW"));
        sparseArray.put(681, Collections.singletonList("WF"));
        sparseArray.put(682, Collections.singletonList("CK"));
        sparseArray.put(683, Collections.singletonList("NU"));
        sparseArray.put(685, Collections.singletonList("WS"));
        sparseArray.put(686, Collections.singletonList("KI"));
        sparseArray.put(687, Collections.singletonList("NC"));
        sparseArray.put(688, Collections.singletonList("TV"));
        sparseArray.put(689, Collections.singletonList("PF"));
        sparseArray.put(690, Collections.singletonList("TK"));
        sparseArray.put(691, Collections.singletonList("FM"));
        sparseArray.put(692, Collections.singletonList("MH"));
        String str = "001";
        sparseArray.put(LogSeverity.EMERGENCY_VALUE, Collections.singletonList(str));
        sparseArray.put(808, Collections.singletonList(str));
        sparseArray.put(850, Collections.singletonList("KP"));
        sparseArray.put(852, Collections.singletonList("HK"));
        sparseArray.put(853, Collections.singletonList("MO"));
        sparseArray.put(855, Collections.singletonList("KH"));
        sparseArray.put(856, Collections.singletonList("LA"));
        sparseArray.put(870, Collections.singletonList(str));
        sparseArray.put(878, Collections.singletonList(str));
        sparseArray.put(880, Collections.singletonList("BD"));
        sparseArray.put(881, Collections.singletonList(str));
        sparseArray.put(882, Collections.singletonList(str));
        sparseArray.put(883, Collections.singletonList(str));
        sparseArray.put(886, Collections.singletonList("TW"));
        sparseArray.put(888, Collections.singletonList(str));
        sparseArray.put(960, Collections.singletonList("MV"));
        sparseArray.put(961, Collections.singletonList("LB"));
        sparseArray.put(962, Collections.singletonList("JO"));
        sparseArray.put(963, Collections.singletonList("SY"));
        sparseArray.put(964, Collections.singletonList("IQ"));
        sparseArray.put(965, Collections.singletonList("KW"));
        sparseArray.put(966, Collections.singletonList("SA"));
        sparseArray.put(967, Collections.singletonList("YE"));
        sparseArray.put(968, Collections.singletonList("OM"));
        sparseArray.put(970, Collections.singletonList("PS"));
        sparseArray.put(971, Collections.singletonList("AE"));
        sparseArray.put(972, Collections.singletonList("IL"));
        sparseArray.put(973, Collections.singletonList("BH"));
        sparseArray.put(974, Collections.singletonList("QA"));
        sparseArray.put(975, Collections.singletonList("BT"));
        sparseArray.put(976, Collections.singletonList("MN"));
        sparseArray.put(977, Collections.singletonList("NP"));
        sparseArray.put(979, Collections.singletonList(str));
        sparseArray.put(992, Collections.singletonList("TJ"));
        sparseArray.put(993, Collections.singletonList("TM"));
        sparseArray.put(994, Collections.singletonList("AZ"));
        sparseArray.put(995, Collections.singletonList("GE"));
        sparseArray.put(996, Collections.singletonList("KG"));
        sparseArray.put(998, Collections.singletonList("UZ"));
        return sparseArray;
    }

    private static void initCountryCodeByIsoMap() {
        HashMap hashMap = new HashMap(MAX_COUNTRIES);
        for (int i = 0; i < COUNTRY_TO_REGION_CODES.size(); i++) {
            int keyAt = COUNTRY_TO_REGION_CODES.keyAt(i);
            for (String str : (List) COUNTRY_TO_REGION_CODES.get(keyAt)) {
                if (!str.equals("001")) {
                    if (!hashMap.containsKey(str)) {
                        hashMap.put(str, Integer.valueOf(keyAt));
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Duplicate regions for country code: ");
                        sb.append(keyAt);
                        throw new IllegalStateException(sb.toString());
                    }
                }
            }
        }
        hashMap.remove("TA");
        hashMap.put("HM", Integer.valueOf(672));
        hashMap.put("GS", Integer.valueOf(500));
        hashMap.put("XK", Integer.valueOf(381));
        COUNTRY_TO_ISO_CODES = Collections.unmodifiableMap(hashMap);
    }
}
