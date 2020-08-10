package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class zzan implements zzch {
    public static final String MEDIA_TYPE = new zzz("application/x-www-form-urlencoded").zza(zzbp.UTF_8).zzp();

    public static void zze(String str, Object obj) {
        String str2 = str;
        Object obj2 = obj;
        if (str2 != null) {
            try {
                StringReader stringReader = new StringReader(str2);
                Class cls = obj.getClass();
                zzbq zzc = zzbq.zzc(cls);
                List asList = Arrays.asList(new Type[]{cls});
                zzbz zzbz = zzbz.class.isAssignableFrom(cls) ? (zzbz) obj2 : null;
                Map map = Map.class.isAssignableFrom(cls) ? (Map) obj2 : null;
                zzbm zzbm = new zzbm(obj2);
                StringWriter stringWriter = new StringWriter();
                StringWriter stringWriter2 = new StringWriter();
                StringWriter stringWriter3 = stringWriter;
                boolean z = true;
                while (true) {
                    int read = stringReader.read();
                    if (read == -1 || read == 38) {
                        String zzah = zzcr.zzah(stringWriter3.toString());
                        if (zzah.length() != 0) {
                            String zzah2 = zzcr.zzah(stringWriter2.toString());
                            zzby zzae = zzc.zzae(zzah);
                            if (zzae != null) {
                                Type zza = zzbs.zza(asList, zzae.getGenericType());
                                if (zzcn.zzc(zza)) {
                                    Class zzb = zzcn.zzb(asList, zzcn.zzd(zza));
                                    zzbm.zza(zzae.zzbz(), zzb, zza((Type) zzb, asList, zzah2));
                                } else if (zzcn.zza(zzcn.zzb(asList, zza), Iterable.class)) {
                                    Collection collection = (Collection) zzae.zzh(obj2);
                                    if (collection == null) {
                                        collection = zzbs.zzb(zza);
                                        zzae.zzb(obj2, collection);
                                    }
                                    collection.add(zza(zza == Object.class ? null : zzcn.zze(zza), asList, zzah2));
                                } else {
                                    zzae.zzb(obj2, zza(zza, asList, zzah2));
                                }
                            } else if (map != null) {
                                ArrayList arrayList = (ArrayList) map.get(zzah);
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                    if (zzbz != null) {
                                        zzbz.zzb(zzah, arrayList);
                                    } else {
                                        map.put(zzah, arrayList);
                                    }
                                }
                                arrayList.add(zzah2);
                            }
                        }
                        StringWriter stringWriter4 = new StringWriter();
                        StringWriter stringWriter5 = new StringWriter();
                        if (read == -1) {
                            zzbm.zzbu();
                            return;
                        }
                        stringWriter3 = stringWriter4;
                        stringWriter2 = stringWriter5;
                        z = true;
                    } else if (read != 61) {
                        if (z) {
                            stringWriter3.write(read);
                        } else {
                            stringWriter2.write(read);
                        }
                    } else if (z) {
                        z = false;
                    } else {
                        stringWriter2.write(read);
                    }
                }
            } catch (IOException e) {
                throw zzea.zza(e);
            }
        }
    }

    private static Object zza(Type type, List<Type> list, String str) {
        return zzbs.zza(zzbs.zza(list, type), str);
    }

    public final <T> T zza(InputStream inputStream, Charset charset, Class<T> cls) throws IOException {
        throw new NoSuchMethodError();
    }
}
