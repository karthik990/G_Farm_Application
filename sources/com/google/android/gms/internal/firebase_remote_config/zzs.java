package com.google.android.gms.internal.firebase_remote_config;

import com.fasterxml.jackson.core.JsonPointer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import kotlin.text.Typography;
import org.objectweb.asm.signature.SignatureVisitor;

public final class zzs extends zzbz {
    private static final zzcs zzai = new zzct("=&-_.!~*'()@:$,;/?:", false);
    private String fragment;
    private int port;
    private String zzaj;
    private String zzak;
    private String zzal;
    private List<String> zzam;

    public zzs() {
        this.port = -1;
    }

    public zzs(String str) {
        this(zzn(str));
    }

    public zzs(URL url) {
        this(url.getProtocol(), url.getHost(), url.getPort(), url.getPath(), url.getRef(), url.getQuery(), url.getUserInfo());
    }

    private zzs(String str, String str2, int i, String str3, String str4, String str5, String str6) {
        this.port = -1;
        this.zzaj = str.toLowerCase(Locale.US);
        this.zzak = str2;
        this.port = i;
        this.zzam = zzm(str3);
        String str7 = null;
        this.fragment = str4 != null ? zzcr.zzah(str4) : null;
        if (str5 != null) {
            zzan.zze(str5, this);
        }
        if (str6 != null) {
            str7 = zzcr.zzah(str6);
        }
        this.zzal = str7;
    }

    public final int hashCode() {
        return zzp().hashCode();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || !(obj instanceof zzs)) {
            return false;
        }
        return zzp().equals(((zzs) obj).zzp());
    }

    public final String toString() {
        return zzp();
    }

    public final String zzp() {
        StringBuilder sb = new StringBuilder();
        sb.append((String) zzds.checkNotNull(this.zzaj));
        sb.append("://");
        String str = this.zzal;
        if (str != null) {
            sb.append(zzcr.zzak(str));
            sb.append('@');
        }
        sb.append((String) zzds.checkNotNull(this.zzak));
        int i = this.port;
        if (i != -1) {
            sb.append(':');
            sb.append(i);
        }
        String valueOf = String.valueOf(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        List<String> list = this.zzam;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str2 = (String) this.zzam.get(i2);
                if (i2 != 0) {
                    sb2.append(JsonPointer.SEPARATOR);
                }
                if (str2.length() != 0) {
                    sb2.append(zzcr.zzai(str2));
                }
            }
        }
        zza(entrySet(), sb2);
        String str3 = this.fragment;
        if (str3 != null) {
            sb2.append('#');
            sb2.append(zzai.zzam(str3));
        }
        String valueOf2 = String.valueOf(sb2.toString());
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public final URL zzk(String str) {
        try {
            return new URL(zzn(zzp()), str);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public final void zzl(String str) {
        this.zzam = zzm(null);
    }

    private static List<String> zzm(String str) {
        String str2;
        if (str == null || str.length() == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        int i = 0;
        while (z) {
            int indexOf = str.indexOf(47, i);
            boolean z2 = indexOf != -1;
            if (z2) {
                str2 = str.substring(i, indexOf);
            } else {
                str2 = str.substring(i);
            }
            arrayList.add(zzcr.zzah(str2));
            i = indexOf + 1;
            z = z2;
        }
        return arrayList;
    }

    static void zza(Set<Entry<String, Object>> set, StringBuilder sb) {
        boolean z = true;
        for (Entry entry : set) {
            Object value = entry.getValue();
            if (value != null) {
                String zzal2 = zzcr.zzal((String) entry.getKey());
                if (value instanceof Collection) {
                    for (Object zza : (Collection) value) {
                        z = zza(z, sb, zzal2, zza);
                    }
                } else {
                    z = zza(z, sb, zzal2, value);
                }
            }
        }
    }

    private static boolean zza(boolean z, StringBuilder sb, String str, Object obj) {
        if (z) {
            z = false;
            sb.append('?');
        } else {
            sb.append(Typography.amp);
        }
        sb.append(str);
        String zzal2 = zzcr.zzal(obj.toString());
        if (zzal2.length() != 0) {
            sb.append(SignatureVisitor.INSTANCEOF);
            sb.append(zzal2);
        }
        return z;
    }

    private static URL zzn(String str) {
        try {
            return new URL(str);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public final /* synthetic */ zzbz zzb() {
        return (zzs) clone();
    }

    public final /* synthetic */ zzbz zzb(String str, Object obj) {
        return (zzs) super.zzb(str, obj);
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzs zzs = (zzs) super.clone();
        List<String> list = this.zzam;
        if (list != null) {
            zzs.zzam = new ArrayList(list);
        }
        return zzs;
    }
}
