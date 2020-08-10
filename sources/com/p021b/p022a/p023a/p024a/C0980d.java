package com.p021b.p022a.p023a.p024a;

import com.p021b.p022a.p023a.p024a.p030e.C0991e;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.b.a.a.a.d */
class C0980d {

    /* renamed from: a */
    private static final Pattern f154a = Pattern.compile("<(head)( [^>]*)?>", 2);

    /* renamed from: b */
    private static final Pattern f155b = Pattern.compile("<(head)( [^>]*)?/>", 2);

    /* renamed from: c */
    private static final Pattern f156c = Pattern.compile("<(body)( [^>]*?)?>", 2);

    /* renamed from: d */
    private static final Pattern f157d = Pattern.compile("<(body)( [^>]*?)?/>", 2);

    /* renamed from: e */
    private static final Pattern f158e = Pattern.compile("<(html)( [^>]*?)?>", 2);

    /* renamed from: f */
    private static final Pattern f159f = Pattern.compile("<(html)( [^>]*?)?/>", 2);

    /* renamed from: g */
    private static final Pattern f160g = Pattern.compile("<!DOCTYPE [^>]*>", 2);

    /* renamed from: a */
    static String m214a(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script type=\"text/javascript\">");
        sb.append(str);
        sb.append("</script>");
        return m218b(str2, sb.toString());
    }

    /* renamed from: a */
    private static boolean m215a(int i, int[][] iArr) {
        if (iArr != null) {
            for (int[] iArr2 : iArr) {
                if (i >= iArr2[0] && i <= iArr2[1]) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: a */
    private static boolean m216a(String str, StringBuilder sb, Pattern pattern, String str2, int[][] iArr) {
        Matcher matcher = pattern.matcher(str);
        int i = 0;
        while (matcher.find(i)) {
            int start = matcher.start();
            int end = matcher.end();
            if (!m215a(start, iArr)) {
                sb.append(str.substring(0, matcher.end()));
                sb.append(str2);
                sb.append(str.substring(matcher.end()));
                return true;
            }
            i = end;
        }
        return false;
    }

    /* renamed from: a */
    private static int[][] m217a(String str) {
        ArrayList arrayList = new ArrayList();
        int length = str.length();
        int i = 0;
        while (i < length) {
            int indexOf = str.indexOf("<!--", i);
            if (indexOf >= 0) {
                int indexOf2 = str.indexOf("-->", indexOf);
                int[] iArr = new int[2];
                if (indexOf2 >= 0) {
                    iArr[0] = indexOf;
                    iArr[1] = indexOf2;
                    arrayList.add(iArr);
                    i = indexOf2 + 3;
                } else {
                    iArr[0] = indexOf;
                    iArr[1] = length;
                    arrayList.add(iArr);
                }
            }
            i = length;
        }
        return (int[][]) arrayList.toArray((int[][]) Array.newInstance(int.class, new int[]{0, 2}));
    }

    /* renamed from: b */
    static String m218b(String str, String str2) {
        C0991e.m258a(str, "HTML is null or empty");
        int[][] a = m217a(str);
        StringBuilder sb = new StringBuilder(str.length() + str2.length() + 16);
        if (m219b(str, sb, f155b, str2, a)) {
            return sb.toString();
        }
        if (m216a(str, sb, f154a, str2, a)) {
            return sb.toString();
        }
        if (m219b(str, sb, f157d, str2, a)) {
            return sb.toString();
        }
        if (m216a(str, sb, f156c, str2, a)) {
            return sb.toString();
        }
        if (m219b(str, sb, f159f, str2, a)) {
            return sb.toString();
        }
        if (m216a(str, sb, f158e, str2, a)) {
            return sb.toString();
        }
        if (m216a(str, sb, f160g, str2, a)) {
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append(str);
        return sb2.toString();
    }

    /* renamed from: b */
    private static boolean m219b(String str, StringBuilder sb, Pattern pattern, String str2, int[][] iArr) {
        Matcher matcher = pattern.matcher(str);
        int i = 0;
        while (matcher.find(i)) {
            int start = matcher.start();
            int end = matcher.end();
            if (!m215a(start, iArr)) {
                sb.append(str.substring(0, matcher.end() - 2));
                String str3 = ">";
                sb.append(str3);
                sb.append(str2);
                sb.append("</");
                sb.append(matcher.group(1));
                sb.append(str3);
                sb.append(str.substring(matcher.end()));
                return true;
            }
            i = end;
        }
        return false;
    }
}
