package com.google.android.gms.tagmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLayer {
    public static final String EVENT_KEY = "event";
    public static final Object OBJECT_NOT_PRESENT = new Object();
    private static final String[] zzafn = "gtm.lifetime".split("\\.");
    private static final Pattern zzafo = Pattern.compile("(\\d+)\\s*([smhd]?)");
    private final ConcurrentHashMap<zzb, Integer> zzafp;
    private final Map<String, Object> zzafq;
    private final ReentrantLock zzafr;
    private final LinkedList<Map<String, Object>> zzafs;
    private final zzc zzaft;
    /* access modifiers changed from: private */
    public final CountDownLatch zzafu;

    static final class zza {
        public final String mKey;
        public final Object mValue;

        zza(String str, Object obj) {
            this.mKey = str;
            this.mValue = obj;
        }

        public final String toString() {
            String str = this.mKey;
            String obj = this.mValue.toString();
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 13 + String.valueOf(obj).length());
            sb.append("Key: ");
            sb.append(str);
            sb.append(" value: ");
            sb.append(obj);
            return sb.toString();
        }

        public final int hashCode() {
            return Arrays.hashCode(new Integer[]{Integer.valueOf(this.mKey.hashCode()), Integer.valueOf(this.mValue.hashCode())});
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zza = (zza) obj;
            if (!this.mKey.equals(zza.mKey) || !this.mValue.equals(zza.mValue)) {
                return false;
            }
            return true;
        }
    }

    interface zzb {
        void zzc(Map<String, Object> map);
    }

    interface zzc {
        void zza(zzaq zzaq);

        void zza(List<zza> list, long j);

        void zzas(String str);
    }

    DataLayer() {
        this(new zzao());
    }

    DataLayer(zzc zzc2) {
        this.zzaft = zzc2;
        this.zzafp = new ConcurrentHashMap<>();
        this.zzafq = new HashMap();
        this.zzafr = new ReentrantLock();
        this.zzafs = new LinkedList<>();
        this.zzafu = new CountDownLatch(1);
        this.zzaft.zza(new zzap(this));
    }

    public String toString() {
        String sb;
        synchronized (this.zzafq) {
            StringBuilder sb2 = new StringBuilder();
            for (Entry entry : this.zzafq.entrySet()) {
                sb2.append(String.format("{\n\tKey: %s\n\tValue: %s\n}\n", new Object[]{entry.getKey(), entry.getValue()}));
            }
            sb = sb2.toString();
        }
        return sb;
    }

    public void pushEvent(String str, Map<String, Object> map) {
        HashMap hashMap = new HashMap(map);
        hashMap.put("event", str);
        push(hashMap);
    }

    public void push(String str, Object obj) {
        push(zzg(str, obj));
    }

    public void push(Map<String, Object> map) {
        try {
            this.zzafu.await();
        } catch (InterruptedException unused) {
            zzdi.zzac("DataLayer.push: unexpected InterruptedException");
        }
        zze(map);
    }

    /* access modifiers changed from: private */
    public final void zze(Map<String, Object> map) {
        Long l;
        Object obj;
        this.zzafr.lock();
        try {
            this.zzafs.offer(map);
            int i = 0;
            if (this.zzafr.getHoldCount() == 1) {
                int i2 = 0;
                while (true) {
                    Map map2 = (Map) this.zzafs.poll();
                    if (map2 == null) {
                        break;
                    }
                    synchronized (this.zzafq) {
                        for (String str : map2.keySet()) {
                            zzb(zzg(str, map2.get(str)), this.zzafq);
                        }
                    }
                    for (zzb zzc2 : this.zzafp.keySet()) {
                        zzc2.zzc(map2);
                    }
                    i2++;
                    if (i2 > 500) {
                        this.zzafs.clear();
                        throw new RuntimeException("Seems like an infinite loop of pushing to the data layer");
                    }
                }
            }
            String[] strArr = zzafn;
            int length = strArr.length;
            Object obj2 = map;
            while (true) {
                l = null;
                if (i >= length) {
                    obj = obj2;
                    break;
                }
                String str2 = strArr[i];
                if (!(obj2 instanceof Map)) {
                    obj = 0;
                    break;
                } else {
                    i++;
                    obj2 = ((Map) obj2).get(str2);
                }
            }
            if (obj != 0) {
                l = zzar(obj.toString());
            }
            if (l != null) {
                ArrayList arrayList = new ArrayList();
                zza(map, "", arrayList);
                this.zzaft.zza(arrayList, l.longValue());
            }
            this.zzafr.unlock();
        } catch (Throwable th) {
            this.zzafr.unlock();
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzaq(String str) {
        push(str, null);
        this.zzaft.zzas(str);
    }

    private final void zza(Map<String, Object> map, String str, Collection<zza> collection) {
        for (Entry entry : map.entrySet()) {
            String str2 = str.length() == 0 ? "" : ".";
            String str3 = (String) entry.getKey();
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + str2.length() + String.valueOf(str3).length());
            sb.append(str);
            sb.append(str2);
            sb.append(str3);
            String sb2 = sb.toString();
            if (entry.getValue() instanceof Map) {
                zza((Map) entry.getValue(), sb2, collection);
            } else if (!sb2.equals("gtm.lifetime")) {
                collection.add(new zza(sb2, entry.getValue()));
            }
        }
    }

    private static Long zzar(String str) {
        long j;
        Matcher matcher = zzafo.matcher(str);
        if (!matcher.matches()) {
            String str2 = "unknown _lifetime: ";
            String valueOf = String.valueOf(str);
            zzdi.zzaw(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            return null;
        }
        try {
            j = Long.parseLong(matcher.group(1));
        } catch (NumberFormatException unused) {
            String str3 = "illegal number in _lifetime value: ";
            String valueOf2 = String.valueOf(str);
            zzdi.zzac(valueOf2.length() != 0 ? str3.concat(valueOf2) : new String(str3));
            j = 0;
        }
        if (j <= 0) {
            String str4 = "non-positive _lifetime: ";
            String valueOf3 = String.valueOf(str);
            zzdi.zzaw(valueOf3.length() != 0 ? str4.concat(valueOf3) : new String(str4));
            return null;
        }
        String group = matcher.group(2);
        if (group.length() == 0) {
            return Long.valueOf(j);
        }
        char charAt = group.charAt(0);
        if (charAt == 'd') {
            return Long.valueOf(j * 1000 * 60 * 60 * 24);
        }
        if (charAt == 'h') {
            return Long.valueOf(j * 1000 * 60 * 60);
        }
        if (charAt == 'm') {
            return Long.valueOf(j * 1000 * 60);
        }
        if (charAt == 's') {
            return Long.valueOf(j * 1000);
        }
        String str5 = "unknown units in _lifetime: ";
        String valueOf4 = String.valueOf(str);
        zzdi.zzac(valueOf4.length() != 0 ? str5.concat(valueOf4) : new String(str5));
        return null;
    }

    public Object get(String str) {
        String[] split;
        synchronized (this.zzafq) {
            Object obj = this.zzafq;
            for (String str2 : str.split("\\.")) {
                if (!(obj instanceof Map)) {
                    return null;
                }
                obj = ((Map) obj).get(str2);
                if (obj == null) {
                    return null;
                }
            }
            return obj;
        }
    }

    public static Map<String, Object> mapOf(Object... objArr) {
        if (objArr.length % 2 == 0) {
            HashMap hashMap = new HashMap();
            int i = 0;
            while (i < objArr.length) {
                if (objArr[i] instanceof String) {
                    hashMap.put(objArr[i], objArr[i + 1]);
                    i += 2;
                } else {
                    String valueOf = String.valueOf(objArr[i]);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 21);
                    sb.append("key is not a string: ");
                    sb.append(valueOf);
                    throw new IllegalArgumentException(sb.toString());
                }
            }
            return hashMap;
        }
        throw new IllegalArgumentException("expected even number of key-value pairs");
    }

    public static List<Object> listOf(Object... objArr) {
        ArrayList arrayList = new ArrayList();
        for (Object add : objArr) {
            arrayList.add(add);
        }
        return arrayList;
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzb zzb2) {
        this.zzafp.put(zzb2, Integer.valueOf(0));
    }

    static Map<String, Object> zzg(String str, Object obj) {
        HashMap hashMap = new HashMap();
        String[] split = str.toString().split("\\.");
        int i = 0;
        HashMap hashMap2 = hashMap;
        while (i < split.length - 1) {
            HashMap hashMap3 = new HashMap();
            hashMap2.put(split[i], hashMap3);
            i++;
            hashMap2 = hashMap3;
        }
        hashMap2.put(split[split.length - 1], obj);
        return hashMap;
    }

    private final void zzb(Map<String, Object> map, Map<String, Object> map2) {
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            if (obj instanceof List) {
                if (!(map2.get(str) instanceof List)) {
                    map2.put(str, new ArrayList());
                }
                zza((List) obj, (List) map2.get(str));
            } else if (obj instanceof Map) {
                if (!(map2.get(str) instanceof Map)) {
                    map2.put(str, new HashMap());
                }
                zzb((Map) obj, (Map) map2.get(str));
            } else {
                map2.put(str, obj);
            }
        }
    }

    private final void zza(List<Object> list, List<Object> list2) {
        while (list2.size() < list.size()) {
            list2.add(null);
        }
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (obj instanceof List) {
                if (!(list2.get(i) instanceof List)) {
                    list2.set(i, new ArrayList());
                }
                zza((List) obj, (List) list2.get(i));
            } else if (obj instanceof Map) {
                if (!(list2.get(i) instanceof Map)) {
                    list2.set(i, new HashMap());
                }
                zzb((Map) obj, (Map) list2.get(i));
            } else if (obj != OBJECT_NOT_PRESENT) {
                list2.set(i, obj);
            }
        }
    }
}
