package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import java.io.Serializable;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.objectweb.asm.signature.SignatureVisitor;

public final class zzbx implements Serializable {
    private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
    private static final Pattern zzfp = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})([Tt](\\d{2}):(\\d{2}):(\\d{2})(\\.\\d+)?)?([Zz]|([+-])(\\d{2}):(\\d{2}))?");
    private final long value;
    private final boolean zzfq;
    private final int zzfr;

    public zzbx(long j) {
        this(false, 0, null);
    }

    private zzbx(boolean z, long j, Integer num) {
        this.zzfq = z;
        this.value = j;
        int i = z ? 0 : num == null ? TimeZone.getDefault().getOffset(j) / 60000 : num.intValue();
        this.zzfr = i;
    }

    public final String zzby() {
        StringBuilder sb = new StringBuilder();
        GregorianCalendar gregorianCalendar = new GregorianCalendar(GMT);
        gregorianCalendar.setTimeInMillis(this.value + (((long) this.zzfr) * DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS));
        zza(sb, gregorianCalendar.get(1), 4);
        sb.append('-');
        zza(sb, gregorianCalendar.get(2) + 1, 2);
        sb.append('-');
        zza(sb, gregorianCalendar.get(5), 2);
        if (!this.zzfq) {
            sb.append('T');
            zza(sb, gregorianCalendar.get(11), 2);
            sb.append(':');
            zza(sb, gregorianCalendar.get(12), 2);
            sb.append(':');
            zza(sb, gregorianCalendar.get(13), 2);
            if (gregorianCalendar.isSet(14)) {
                sb.append('.');
                zza(sb, gregorianCalendar.get(14), 3);
            }
            int i = this.zzfr;
            if (i == 0) {
                sb.append('Z');
            } else {
                if (i > 0) {
                    sb.append(SignatureVisitor.EXTENDS);
                } else {
                    sb.append('-');
                    i = -i;
                }
                int i2 = i / 60;
                int i3 = i % 60;
                zza(sb, i2, 2);
                sb.append(':');
                zza(sb, i3, 2);
            }
        }
        return sb.toString();
    }

    public final String toString() {
        return zzby();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbx)) {
            return false;
        }
        zzbx zzbx = (zzbx) obj;
        return this.zzfq == zzbx.zzfq && this.value == zzbx.value && this.zzfr == zzbx.zzfr;
    }

    public final int hashCode() {
        long[] jArr = new long[3];
        jArr[0] = this.value;
        jArr[1] = this.zzfq ? 1 : 0;
        jArr[2] = (long) this.zzfr;
        return Arrays.hashCode(jArr);
    }

    public static zzbx zzaf(String str) throws NumberFormatException {
        boolean z;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        Matcher matcher = zzfp.matcher(str);
        if (!matcher.matches()) {
            String str2 = "Invalid date/time format: ";
            String valueOf = String.valueOf(str);
            throw new NumberFormatException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        }
        int parseInt = Integer.parseInt(matcher.group(1));
        int parseInt2 = Integer.parseInt(matcher.group(2)) - 1;
        int parseInt3 = Integer.parseInt(matcher.group(3));
        boolean z2 = matcher.group(4) != null;
        String group = matcher.group(9);
        boolean z3 = group != null;
        Integer num = null;
        if (!z3 || z2) {
            if (z2) {
                int parseInt4 = Integer.parseInt(matcher.group(5));
                int parseInt5 = Integer.parseInt(matcher.group(6));
                int parseInt6 = Integer.parseInt(matcher.group(7));
                if (matcher.group(8) != null) {
                    double parseInt7 = (double) ((float) Integer.parseInt(matcher.group(8).substring(1)));
                    z = z2;
                    double pow = Math.pow(10.0d, (double) (matcher.group(8).substring(1).length() - 3));
                    Double.isNaN(parseInt7);
                    i = (int) (parseInt7 / pow);
                    i3 = parseInt5;
                    i2 = parseInt6;
                } else {
                    z = z2;
                    i3 = parseInt5;
                    i2 = parseInt6;
                    i = 0;
                }
                i4 = parseInt4;
            } else {
                z = z2;
                i4 = 0;
                i3 = 0;
                i2 = 0;
                i = 0;
            }
            GregorianCalendar gregorianCalendar = new GregorianCalendar(GMT);
            gregorianCalendar.set(parseInt, parseInt2, parseInt3, i4, i3, i2);
            gregorianCalendar.set(14, i);
            long timeInMillis = gregorianCalendar.getTimeInMillis();
            if (z && z3) {
                if (Character.toUpperCase(group.charAt(0)) == 'Z') {
                    i5 = 0;
                } else {
                    int parseInt8 = (Integer.parseInt(matcher.group(11)) * 60) + Integer.parseInt(matcher.group(12));
                    int i6 = matcher.group(10).charAt(0) == '-' ? -parseInt8 : parseInt8;
                    timeInMillis -= ((long) i6) * DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS;
                    i5 = i6;
                }
                num = Integer.valueOf(i5);
            }
            return new zzbx(!z, timeInMillis, num);
        }
        String str3 = "Invalid date/time format, cannot specify time zone shift without specifying time: ";
        String valueOf2 = String.valueOf(str);
        throw new NumberFormatException(valueOf2.length() != 0 ? str3.concat(valueOf2) : new String(str3));
    }

    private static void zza(StringBuilder sb, int i, int i2) {
        if (i < 0) {
            sb.append('-');
            i = -i;
        }
        int i3 = i2;
        int i4 = i;
        while (i4 > 0) {
            i4 /= 10;
            i3--;
        }
        for (int i5 = 0; i5 < i3; i5++) {
            sb.append('0');
        }
        if (i != 0) {
            sb.append(i);
        }
    }
}
