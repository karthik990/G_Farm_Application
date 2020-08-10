package com.google.android.gms.internal.firebase_remote_config;

import com.fasterxml.jackson.core.JsonPointer;
import com.google.android.exoplayer2.util.MimeTypes;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import p043io.fabric.sdk.android.services.network.HttpRequest;

public final class zzz {
    private static final Pattern zzat = Pattern.compile("[\\w!#$&.+\\-\\^_]+|[*]");
    private static final Pattern zzau = Pattern.compile("[\\p{ASCII}&&[^\\p{Cntrl} ;/=\\[\\]\\(\\)\\<\\>\\@\\,\\:\\\"\\?\\=]]+");
    private static final Pattern zzav;
    private static final Pattern zzaw;
    private String type = MimeTypes.BASE_TYPE_APPLICATION;
    private String zzax = "octet-stream";
    private final SortedMap<String, String> zzay = new TreeMap();
    private String zzaz;

    public zzz(String str) {
        Matcher matcher = zzav.matcher(str);
        if (matcher.matches()) {
            String group = matcher.group(1);
            if (zzat.matcher(group).matches()) {
                this.type = group;
                this.zzaz = null;
                String group2 = matcher.group(2);
                if (zzat.matcher(group2).matches()) {
                    this.zzax = group2;
                    this.zzaz = null;
                    String group3 = matcher.group(3);
                    if (group3 != null) {
                        Matcher matcher2 = zzaw.matcher(group3);
                        while (matcher2.find()) {
                            String group4 = matcher2.group(1);
                            String group5 = matcher2.group(3);
                            if (group5 == null) {
                                group5 = matcher2.group(2);
                            }
                            zza(group4, group5);
                        }
                        return;
                    }
                    return;
                }
                throw new IllegalArgumentException("Subtype contains reserved characters");
            }
            throw new IllegalArgumentException("Type contains reserved characters");
        }
        throw new IllegalArgumentException("Type must be in the 'maintype/subtype; parameter=value' format");
    }

    private final zzz zza(String str, String str2) {
        if (str2 == null) {
            this.zzaz = null;
            this.zzay.remove(str.toLowerCase(Locale.US));
            return this;
        } else if (zzau.matcher(str).matches()) {
            this.zzaz = null;
            this.zzay.put(str.toLowerCase(Locale.US), str2);
            return this;
        } else {
            throw new IllegalArgumentException("Name contains reserved characters");
        }
    }

    static boolean zzv(String str) {
        return zzau.matcher(str).matches();
    }

    public final String zzp() {
        String str = this.zzaz;
        if (str != null) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.type);
        sb.append(JsonPointer.SEPARATOR);
        sb.append(this.zzax);
        SortedMap<String, String> sortedMap = this.zzay;
        if (sortedMap != null) {
            for (Entry entry : sortedMap.entrySet()) {
                String str2 = (String) entry.getValue();
                sb.append("; ");
                sb.append((String) entry.getKey());
                sb.append("=");
                if (!zzv(str2)) {
                    String replace = str2.replace("\\", "\\\\");
                    String str3 = "\"";
                    String replace2 = replace.replace(str3, "\\\"");
                    StringBuilder sb2 = new StringBuilder(String.valueOf(replace2).length() + 2);
                    sb2.append(str3);
                    sb2.append(replace2);
                    sb2.append(str3);
                    str2 = sb2.toString();
                }
                sb.append(str2);
            }
        }
        this.zzaz = sb.toString();
        return this.zzaz;
    }

    public final String toString() {
        return zzp();
    }

    private final boolean zza(zzz zzz) {
        return zzz != null && this.type.equalsIgnoreCase(zzz.type) && this.zzax.equalsIgnoreCase(zzz.zzax);
    }

    public static boolean zzb(String str, String str2) {
        return str2 != null && new zzz(str).zza(new zzz(str2));
    }

    public final zzz zza(Charset charset) {
        zza(HttpRequest.PARAM_CHARSET, charset == null ? null : charset.name());
        return this;
    }

    public final Charset zzs() {
        String str = (String) this.zzay.get(HttpRequest.PARAM_CHARSET.toLowerCase(Locale.US));
        if (str == null) {
            return null;
        }
        return Charset.forName(str);
    }

    public final int hashCode() {
        return zzp().hashCode();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzz)) {
            return false;
        }
        zzz zzz = (zzz) obj;
        if (!zza(zzz) || !this.zzay.equals(zzz.zzay)) {
            return false;
        }
        return true;
    }

    static {
        String str = "[^\\s/=;\"]+";
        String str2 = ";.*";
        StringBuilder sb = new StringBuilder(str.length() + 14 + str.length() + str2.length());
        sb.append("\\s*(");
        sb.append(str);
        sb.append(")/(");
        sb.append(str);
        sb.append(")\\s*(");
        sb.append(str2);
        sb.append(")?");
        zzav = Pattern.compile(sb.toString(), 32);
        String str3 = "\"([^\"]*)\"";
        String str4 = "[^\\s;\"]*";
        StringBuilder sb2 = new StringBuilder(str3.length() + 1 + str4.length());
        sb2.append(str3);
        sb2.append("|");
        sb2.append(str4);
        String sb3 = sb2.toString();
        StringBuilder sb4 = new StringBuilder(str.length() + 12 + String.valueOf(sb3).length());
        sb4.append("\\s*;\\s*(");
        sb4.append(str);
        sb4.append(")=(");
        sb4.append(sb3);
        sb4.append(")");
        zzaw = Pattern.compile(sb4.toString());
    }
}
