package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.gms.internal.firebase_remote_config.zzbz.zzc;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import p043io.fabric.sdk.android.services.network.HttpRequest;

public final class zzx extends zzbz {
    @zzcb("Accept")
    private List<String> accept;
    @zzcb("Accept-Encoding")
    private List<String> acceptEncoding = new ArrayList(Collections.singleton(HttpRequest.ENCODING_GZIP));
    @zzcb("Age")
    private List<Long> age;
    @zzcb("WWW-Authenticate")
    private List<String> authenticate;
    @zzcb("Authorization")
    private List<String> authorization;
    @zzcb("Cache-Control")
    private List<String> cacheControl;
    @zzcb("Content-Encoding")
    private List<String> contentEncoding;
    @zzcb("Content-Length")
    private List<Long> contentLength;
    @zzcb("Content-MD5")
    private List<String> contentMD5;
    @zzcb("Content-Range")
    private List<String> contentRange;
    @zzcb("Content-Type")
    private List<String> contentType;
    @zzcb("Cookie")
    private List<String> cookie;
    @zzcb("Date")
    private List<String> date;
    @zzcb("ETag")
    private List<String> etag;
    @zzcb("Expires")
    private List<String> expires;
    @zzcb("If-Match")
    private List<String> ifMatch;
    @zzcb("If-Modified-Since")
    private List<String> ifModifiedSince;
    @zzcb("If-None-Match")
    private List<String> ifNoneMatch;
    @zzcb("If-Range")
    private List<String> ifRange;
    @zzcb("If-Unmodified-Since")
    private List<String> ifUnmodifiedSince;
    @zzcb("Last-Modified")
    private List<String> lastModified;
    @zzcb("Location")
    private List<String> location;
    @zzcb("MIME-Version")
    private List<String> mimeVersion;
    @zzcb("Range")
    private List<String> range;
    @zzcb("Retry-After")
    private List<String> retryAfter;
    @zzcb("User-Agent")
    private List<String> userAgent;

    public zzx() {
        super(EnumSet.of(zzc.IGNORE_CASE));
    }

    public final zzx zzo(String str) {
        this.authorization = zza((T) null);
        return this;
    }

    public final String getContentType() {
        return (String) zza(this.contentType);
    }

    public final String zzq() {
        return (String) zza(this.etag);
    }

    public final zzx zzp(String str) {
        this.ifModifiedSince = zza((T) null);
        return this;
    }

    public final zzx zzq(String str) {
        this.ifMatch = zza((T) null);
        return this;
    }

    public final zzx zzr(String str) {
        this.ifNoneMatch = zza((T) str);
        return this;
    }

    public final zzx zzs(String str) {
        this.ifUnmodifiedSince = zza((T) null);
        return this;
    }

    public final zzx zzt(String str) {
        this.ifRange = zza((T) null);
        return this;
    }

    public final String getLocation() {
        return (String) zza(this.location);
    }

    public final String zzr() {
        return (String) zza(this.userAgent);
    }

    public final zzx zzu(String str) {
        this.userAgent = zza((T) str);
        return this;
    }

    private static void zza(Logger logger, StringBuilder sb, StringBuilder sb2, zzai zzai, String str, Object obj, Writer writer) throws IOException {
        if (obj != null && !zzbs.isNull(obj)) {
            String name = obj instanceof Enum ? zzby.zza((Enum) obj).getName() : obj.toString();
            String str2 = (("Authorization".equalsIgnoreCase(str) || "Cookie".equalsIgnoreCase(str)) && (logger == null || !logger.isLoggable(Level.ALL))) ? "<Not Logged>" : name;
            String str3 = ": ";
            if (sb != null) {
                sb.append(str);
                sb.append(str3);
                sb.append(str2);
                sb.append(zzcm.zzgh);
            }
            if (sb2 != null) {
                sb2.append(" -H '");
                sb2.append(str);
                sb2.append(str3);
                sb2.append(str2);
                sb2.append("'");
            }
            if (zzai != null) {
                zzai.addHeader(str, name);
            }
            if (writer != null) {
                writer.write(str);
                writer.write(str3);
                writer.write(name);
                writer.write("\r\n");
            }
        }
    }

    static void zza(zzx zzx, StringBuilder sb, StringBuilder sb2, Logger logger, zzai zzai) throws IOException {
        HashSet hashSet = new HashSet();
        for (Entry entry : zzx.entrySet()) {
            String str = (String) entry.getKey();
            Object[] objArr = {str};
            if (hashSet.add(str)) {
                Object value = entry.getValue();
                if (value != null) {
                    zzby zzae = zzx.zzcc().zzae(str);
                    if (zzae != null) {
                        str = zzae.getName();
                    }
                    Class cls = value.getClass();
                    if ((value instanceof Iterable) || cls.isArray()) {
                        for (Object zza : zzcn.zzi(value)) {
                            zza(logger, sb, sb2, zzai, str, zza, null);
                        }
                    } else {
                        zza(logger, sb, sb2, zzai, str, value, null);
                    }
                }
            } else {
                throw new IllegalArgumentException(zzdz.zza("multiple headers of the same name (headers are case insensitive): %s", objArr));
            }
        }
    }

    public final void zza(zzaj zzaj, StringBuilder sb) throws IOException {
        clear();
        zzy zzy = new zzy(this, sb);
        int zzak = zzaj.zzak();
        for (int i = 0; i < zzak; i++) {
            String zzc = zzaj.zzc(i);
            String zzd = zzaj.zzd(i);
            List<Type> list = zzy.zzas;
            zzbq zzbq = zzy.zzar;
            zzbm zzbm = zzy.zzap;
            StringBuilder sb2 = zzy.zzaq;
            if (sb2 != null) {
                StringBuilder sb3 = new StringBuilder(String.valueOf(zzc).length() + 2 + String.valueOf(zzd).length());
                sb3.append(zzc);
                sb3.append(": ");
                sb3.append(zzd);
                sb2.append(sb3.toString());
                sb2.append(zzcm.zzgh);
            }
            zzby zzae = zzbq.zzae(zzc);
            if (zzae != null) {
                Type zza = zzbs.zza(list, zzae.getGenericType());
                if (zzcn.zzc(zza)) {
                    Class zzb = zzcn.zzb(list, zzcn.zzd(zza));
                    zzbm.zza(zzae.zzbz(), zzb, zza(zzb, list, zzd));
                } else if (zzcn.zza(zzcn.zzb(list, zza), Iterable.class)) {
                    Collection collection = (Collection) zzae.zzh(this);
                    if (collection == null) {
                        collection = zzbs.zzb(zza);
                        zzae.zzb(this, collection);
                    }
                    collection.add(zza(zza == Object.class ? null : zzcn.zze(zza), list, zzd));
                } else {
                    zzae.zzb(this, zza(zza, list, zzd));
                }
            } else {
                ArrayList arrayList = (ArrayList) get(zzc);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    zzb(zzc, arrayList);
                }
                arrayList.add(zzd);
            }
        }
        zzy.zzap.zzbu();
    }

    private static <T> T zza(List<T> list) {
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    private static <T> List<T> zza(T t) {
        if (t == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(t);
        return arrayList;
    }

    private static Object zza(Type type, List<Type> list, String str) {
        return zzbs.zza(zzbs.zza(list, type), str);
    }

    public final /* synthetic */ zzbz zzb() {
        return (zzx) clone();
    }

    public final /* synthetic */ zzbz zzb(String str, Object obj) {
        return (zzx) super.zzb(str, obj);
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return (zzx) super.clone();
    }
}
