package com.google.android.gms.internal.firebase_auth;

final class zzbb<E> extends zzay<E> {
    static final zzay<Object> zzhb = new zzbb(new Object[0], 0);
    private final transient int size;
    private final transient Object[] zzhc;

    zzbb(Object[] objArr, int i) {
        this.zzhc = objArr;
        this.size = i;
    }

    /* access modifiers changed from: 0000 */
    public final int zzcb() {
        return 0;
    }

    public final int size() {
        return this.size;
    }

    /* access modifiers changed from: 0000 */
    public final Object[] zzca() {
        return this.zzhc;
    }

    /* access modifiers changed from: 0000 */
    public final int zzcc() {
        return this.size;
    }

    /* access modifiers changed from: 0000 */
    public final int zza(Object[] objArr, int i) {
        System.arraycopy(this.zzhc, 0, objArr, i, this.size);
        return i + this.size;
    }

    public final E get(int i) {
        zzaj.zza(i, this.size);
        return this.zzhc[i];
    }
}
