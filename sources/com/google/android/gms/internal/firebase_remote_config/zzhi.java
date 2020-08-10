package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.gms.internal.firebase_remote_config.zzhi;
import com.google.android.gms.internal.firebase_remote_config.zzhi.zza;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class zzhi<MessageType extends zzhi<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzfn<MessageType, BuilderType> {
    private static Map<Object, zzhi<?, ?>> zzsy = new ConcurrentHashMap();
    protected zzkc zzsw = zzkc.zzje();
    private int zzsx = -1;

    public static abstract class zza<MessageType extends zzhi<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzfo<MessageType, BuilderType> {
        private final MessageType zzsz;
        private MessageType zzta;
        private boolean zztb = false;

        protected zza(MessageType messagetype) {
            this.zzsz = messagetype;
            this.zzta = (zzhi) messagetype.zzb(zze.zztg, null, null);
        }

        public final boolean isInitialized() {
            return zzhi.zza(this.zzta, false);
        }

        /* renamed from: zzha */
        public MessageType zzhc() {
            if (this.zztb) {
                return this.zzta;
            }
            this.zzta.zzer();
            this.zztb = true;
            return this.zzta;
        }

        /* renamed from: zzhb */
        public final MessageType zzhd() {
            MessageType messagetype = (zzhi) zzhc();
            if (messagetype.isInitialized()) {
                return messagetype;
            }
            throw new zzka(messagetype);
        }

        /* renamed from: zzb */
        public final BuilderType zza(MessageType messagetype) {
            if (this.zztb) {
                MessageType messagetype2 = (zzhi) this.zzta.zzb(zze.zztg, null, null);
                zza(messagetype2, this.zzta);
                this.zzta = messagetype2;
                this.zztb = false;
            }
            zza(this.zzta, messagetype);
            return this;
        }

        private static void zza(MessageType messagetype, MessageType messagetype2) {
            zzjb.zzik().zzz(messagetype).zze(messagetype, messagetype2);
        }

        public final /* synthetic */ zzfo zzep() {
            return (zza) clone();
        }

        public final /* synthetic */ zzio zzgz() {
            return this.zzsz;
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zza zza = (zza) ((zzhi) this.zzsz).zzb(zze.zzth, null, null);
            zza.zza((zzhi) zzhc());
            return zza;
        }
    }

    public static class zzb<T extends zzhi<T, ?>> extends zzfp<T> {
        private final T zzsz;

        public zzb(T t) {
            this.zzsz = t;
        }
    }

    public static abstract class zzc<MessageType extends zzc<MessageType, BuilderType>, BuilderType> extends zzhi<MessageType, BuilderType> implements zziq {
        protected zzha<Object> zztc = zzha.zzgn();

        /* access modifiers changed from: 0000 */
        public final zzha<Object> zzhe() {
            if (this.zztc.isImmutable()) {
                this.zztc = (zzha) this.zztc.clone();
            }
            return this.zztc;
        }
    }

    public static class zzd<ContainingType extends zzio, Type> extends zzgt<ContainingType, Type> {
    }

    /* 'enum' access flag removed */
    public static final class zze {
        public static final int zztd = 1;
        public static final int zzte = 2;
        public static final int zztf = 3;
        public static final int zztg = 4;
        public static final int zzth = 5;
        public static final int zzti = 6;
        public static final int zztj = 7;
        private static final /* synthetic */ int[] zztk = {zztd, zzte, zztf, zztg, zzth, zzti, zztj};
        public static final int zztl = 1;
        public static final int zztm = 2;
        private static final /* synthetic */ int[] zztn = {zztl, zztm};
        public static final int zzto = 1;
        public static final int zztp = 2;
        private static final /* synthetic */ int[] zztq = {zzto, zztp};

        public static int[] zzhf() {
            return (int[]) zztk.clone();
        }
    }

    /* access modifiers changed from: protected */
    public abstract Object zzb(int i, Object obj, Object obj2);

    public String toString() {
        return zzir.zza(this, super.toString());
    }

    public int hashCode() {
        if (this.zzod != 0) {
            return this.zzod;
        }
        this.zzod = zzjb.zzik().zzz(this).hashCode(this);
        return this.zzod;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!((zzhi) zzb(zze.zzti, null, null)).getClass().isInstance(obj)) {
            return false;
        }
        return zzjb.zzik().zzz(this).equals(this, (zzhi) obj);
    }

    /* access modifiers changed from: protected */
    public final void zzer() {
        zzjb.zzik().zzz(this).zzm(this);
    }

    public final boolean isInitialized() {
        return zza((T) this, Boolean.TRUE.booleanValue());
    }

    /* access modifiers changed from: 0000 */
    public final int zzeo() {
        return this.zzsx;
    }

    /* access modifiers changed from: 0000 */
    public final void zzr(int i) {
        this.zzsx = i;
    }

    public final void zzb(zzgp zzgp) throws IOException {
        zzjb.zzik().zzk(getClass()).zza(this, zzgr.zza(zzgp));
    }

    public final int zzgo() {
        if (this.zzsx == -1) {
            this.zzsx = zzjb.zzik().zzz(this).zzw(this);
        }
        return this.zzsx;
    }

    static <T extends zzhi<?, ?>> T zzi(Class<T> cls) {
        T t = (zzhi) zzsy.get(cls);
        if (t == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                t = (zzhi) zzsy.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (t == null) {
            t = (zzhi) ((zzhi) zzkh.zzm(cls)).zzb(zze.zzti, null, null);
            if (t != null) {
                zzsy.put(cls, t);
            } else {
                throw new IllegalStateException();
            }
        }
        return t;
    }

    protected static <T extends zzhi<?, ?>> void zza(Class<T> cls, T t) {
        zzsy.put(cls, t);
    }

    protected static Object zza(zzio zzio, String str, Object[] objArr) {
        return new zzjd(zzio, str, objArr);
    }

    static Object zza(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
            }
        }
    }

    protected static final <T extends zzhi<T, ?>> boolean zza(T t, boolean z) {
        byte byteValue = ((Byte) t.zzb(zze.zztd, null, null)).byteValue();
        if (byteValue == 1) {
            return true;
        }
        if (byteValue == 0) {
            return false;
        }
        boolean zzy = zzjb.zzik().zzz(t).zzy(t);
        if (z) {
            t.zzb(zze.zzte, zzy ? t : null, null);
        }
        return zzy;
    }

    protected static <E> zzhn<E> zzgw() {
        return zzjc.zzil();
    }

    private static <T extends zzhi<T, ?>> T zza(T t, zzgi zzgi, zzgv zzgv) throws zzho {
        T t2 = (zzhi) t.zzb(zze.zztg, null, null);
        try {
            zzjb.zzik().zzz(t2).zza(t2, zzgn.zza(zzgi), zzgv);
            t2.zzer();
            return t2;
        } catch (IOException e) {
            if (e.getCause() instanceof zzho) {
                throw ((zzho) e.getCause());
            }
            throw new zzho(e.getMessage()).zzg(t2);
        } catch (RuntimeException e2) {
            if (e2.getCause() instanceof zzho) {
                throw ((zzho) e2.getCause());
            }
            throw e2;
        }
    }

    private static <T extends zzhi<T, ?>> T zza(T t, byte[] bArr, int i, int i2, zzgv zzgv) throws zzho {
        T t2 = (zzhi) t.zzb(zze.zztg, null, null);
        try {
            zzjb.zzik().zzz(t2).zza(t2, bArr, 0, i2, new zzft(zzgv));
            t2.zzer();
            if (t2.zzod == 0) {
                return t2;
            }
            throw new RuntimeException();
        } catch (IOException e) {
            if (e.getCause() instanceof zzho) {
                throw ((zzho) e.getCause());
            }
            throw new zzho(e.getMessage()).zzg(t2);
        } catch (IndexOutOfBoundsException unused) {
            throw zzho.zzhg().zzg(t2);
        }
    }

    private static <T extends zzhi<T, ?>> T zza(T t) throws zzho {
        if (t == null || t.isInitialized()) {
            return t;
        }
        throw new zzho(new zzka(t).getMessage()).zzg(t);
    }

    protected static <T extends zzhi<T, ?>> T zza(T t, byte[] bArr) throws zzho {
        return zza(zza(t, bArr, 0, bArr.length, zzgv.zzgj()));
    }

    protected static <T extends zzhi<T, ?>> T zza(T t, InputStream inputStream) throws zzho {
        zzgi zzgi;
        if (inputStream == null) {
            byte[] bArr = zzhk.zztt;
            zzgi = zzgi.zza(bArr, 0, bArr.length, false);
        } else {
            zzgi = new zzgl(inputStream);
        }
        return zza(zza(t, zzgi, zzgv.zzgj()));
    }

    public final /* synthetic */ zzip zzgx() {
        zza zza2 = (zza) zzb(zze.zzth, null, null);
        zza2.zza(this);
        return zza2;
    }

    public final /* synthetic */ zzip zzgy() {
        return (zza) zzb(zze.zzth, null, null);
    }

    public final /* synthetic */ zzio zzgz() {
        return (zzhi) zzb(zze.zzti, null, null);
    }
}
