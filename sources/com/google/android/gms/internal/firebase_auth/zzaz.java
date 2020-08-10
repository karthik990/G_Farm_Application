package com.google.android.gms.internal.firebase_auth;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.objectweb.asm.signature.SignatureVisitor;

public abstract class zzaz<K, V> implements Serializable, Map<K, V> {
    private static final Entry<?, ?>[] zzgw = new Entry[0];
    private transient zzbc<Entry<K, V>> zzgx;
    private transient zzbc<K> zzgy;
    private transient zzav<V> zzgz;

    public static <K, V> zzaz<K, V> zzb(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        zzat.zza(k, v);
        zzat.zza(k2, v2);
        zzat.zza(k3, v3);
        zzat.zza(k4, v4);
        return zzbe.zza(4, new Object[]{k, v, k2, v2, k3, v3, k4, v4});
    }

    public abstract V get(@NullableDecl Object obj);

    /* access modifiers changed from: 0000 */
    public abstract zzbc<Entry<K, V>> zzcf();

    /* access modifiers changed from: 0000 */
    public abstract zzbc<K> zzcg();

    /* access modifiers changed from: 0000 */
    public abstract zzav<V> zzch();

    zzaz() {
    }

    @Deprecated
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final V remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(@NullableDecl Object obj) {
        return get(obj) != null;
    }

    public boolean containsValue(@NullableDecl Object obj) {
        return ((zzav) values()).contains(obj);
    }

    public final V getOrDefault(@NullableDecl Object obj, @NullableDecl V v) {
        V v2 = get(obj);
        return v2 != null ? v2 : v;
    }

    public boolean equals(@NullableDecl Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        return entrySet().equals(((Map) obj).entrySet());
    }

    public int hashCode() {
        return zzbh.zza((zzbc) entrySet());
    }

    public String toString() {
        int size = size();
        if (size >= 0) {
            StringBuilder sb = new StringBuilder((int) Math.min(((long) size) << 3, 1073741824));
            sb.append('{');
            boolean z = true;
            for (Entry entry : entrySet()) {
                if (!z) {
                    sb.append(", ");
                }
                z = false;
                sb.append(entry.getKey());
                sb.append(SignatureVisitor.INSTANCEOF);
                sb.append(entry.getValue());
            }
            sb.append('}');
            return sb.toString();
        }
        String str = "size";
        StringBuilder sb2 = new StringBuilder(str.length() + 40);
        sb2.append(str);
        sb2.append(" cannot be negative but was: ");
        sb2.append(size);
        throw new IllegalArgumentException(sb2.toString());
    }

    public /* synthetic */ Set entrySet() {
        zzbc<Entry<K, V>> zzbc = this.zzgx;
        if (zzbc != null) {
            return zzbc;
        }
        zzbc<Entry<K, V>> zzcf = zzcf();
        this.zzgx = zzcf;
        return zzcf;
    }

    public /* synthetic */ Collection values() {
        zzav<V> zzav = this.zzgz;
        if (zzav != null) {
            return zzav;
        }
        zzav<V> zzch = zzch();
        this.zzgz = zzch;
        return zzch;
    }

    public /* synthetic */ Set keySet() {
        zzbc<K> zzbc = this.zzgy;
        if (zzbc != null) {
            return zzbc;
        }
        zzbc<K> zzcg = zzcg();
        this.zzgy = zzcg;
        return zzcg;
    }
}
