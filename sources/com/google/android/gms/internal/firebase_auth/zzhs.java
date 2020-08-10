package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs;
import com.google.android.gms.internal.firebase_auth.zzhs.zza;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class zzhs<MessageType extends zzhs<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzfx<MessageType, BuilderType> {
    private static Map<Object, zzhs<?, ?>> zzaal = new ConcurrentHashMap();
    protected zzkn zzaaj = zzkn.zzko();
    private int zzaak = -1;

    public static abstract class zza<MessageType extends zzhs<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzga<MessageType, BuilderType> {
        private final MessageType zzaag;
        protected MessageType zzaah;
        private boolean zzaai = false;

        protected zza(MessageType messagetype) {
            this.zzaag = messagetype;
            this.zzaah = (zzhs) messagetype.zza(zzd.zzaaq, (Object) null, (Object) null);
        }

        /* access modifiers changed from: protected */
        public final void zzid() {
            if (this.zzaai) {
                MessageType messagetype = (zzhs) this.zzaah.zza(zzd.zzaaq, (Object) null, (Object) null);
                zza(messagetype, this.zzaah);
                this.zzaah = messagetype;
                this.zzaai = false;
            }
        }

        public final boolean isInitialized() {
            return zzhs.zza(this.zzaah, false);
        }

        /* renamed from: zzie */
        public MessageType zzig() {
            if (this.zzaai) {
                return this.zzaah;
            }
            this.zzaah.zzfy();
            this.zzaai = true;
            return this.zzaah;
        }

        /* renamed from: zzif */
        public final MessageType zzih() {
            MessageType messagetype = (zzhs) zzig();
            if (messagetype.isInitialized()) {
                return messagetype;
            }
            throw new zzkl(messagetype);
        }

        public final BuilderType zza(MessageType messagetype) {
            zzid();
            zza(this.zzaah, messagetype);
            return this;
        }

        private static void zza(MessageType messagetype, MessageType messagetype2) {
            zzjo.zzjv().zzr(messagetype).zzd(messagetype, messagetype2);
        }

        public final /* synthetic */ zzga zzfw() {
            return (zza) clone();
        }

        public final /* synthetic */ zzjc zzii() {
            return this.zzaag;
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zza zza = (zza) ((zzhs) this.zzaag).zza(zzd.zzaar, (Object) null, (Object) null);
            zza.zza((MessageType) (zzhs) zzig());
            return zza;
        }
    }

    public static abstract class zzb<MessageType extends zzb<MessageType, BuilderType>, BuilderType> extends zzhs<MessageType, BuilderType> implements zzje {
        protected zzhi<Object> zzaam = zzhi.zzhs();
    }

    public static class zzc<T extends zzhs<T, ?>> extends zzgc<T> {
        private final T zzaag;

        public zzc(T t) {
            this.zzaag = t;
        }

        public final /* synthetic */ Object zza(zzgr zzgr, zzhf zzhf) throws zzic {
            return zzhs.zza(this.zzaag, zzgr, zzhf);
        }
    }

    /* 'enum' access flag removed */
    public static final class zzd {
        public static final int zzaan = 1;
        public static final int zzaao = 2;
        public static final int zzaap = 3;
        public static final int zzaaq = 4;
        public static final int zzaar = 5;
        public static final int zzaas = 6;
        public static final int zzaat = 7;
        private static final /* synthetic */ int[] zzaau = {zzaan, zzaao, zzaap, zzaaq, zzaar, zzaas, zzaat};
        public static final int zzaav = 1;
        public static final int zzaaw = 2;
        private static final /* synthetic */ int[] zzaax = {zzaav, zzaaw};
        public static final int zzaay = 1;
        public static final int zzaaz = 2;
        private static final /* synthetic */ int[] zzaba = {zzaay, zzaaz};

        public static int[] zzip() {
            return (int[]) zzaau.clone();
        }
    }

    public static class zze<ContainingType extends zzjc, Type> extends zzhe<ContainingType, Type> {
    }

    /* access modifiers changed from: protected */
    public abstract Object zza(int i, Object obj, Object obj2);

    public String toString() {
        return zzjd.zza(this, super.toString());
    }

    public int hashCode() {
        if (this.zzvm != 0) {
            return this.zzvm;
        }
        this.zzvm = zzjo.zzjv().zzr(this).hashCode(this);
        return this.zzvm;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!((zzhs) zza(zzd.zzaas, (Object) null, (Object) null)).getClass().isInstance(obj)) {
            return false;
        }
        return zzjo.zzjv().zzr(this).equals(this, (zzhs) obj);
    }

    /* access modifiers changed from: protected */
    public final void zzfy() {
        zzjo.zzjv().zzr(this).zzf(this);
    }

    /* access modifiers changed from: protected */
    public final <MessageType extends zzhs<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> BuilderType zzij() {
        return (zza) zza(zzd.zzaar, (Object) null, (Object) null);
    }

    public final boolean isInitialized() {
        return zza((T) this, Boolean.TRUE.booleanValue());
    }

    /* access modifiers changed from: 0000 */
    public final int zzfu() {
        return this.zzaak;
    }

    /* access modifiers changed from: 0000 */
    public final void zzl(int i) {
        this.zzaak = i;
    }

    public final void zzb(zzha zzha) throws IOException {
        zzjo.zzjv().zzf(getClass()).zza(this, zzhc.zza(zzha));
    }

    public final int zzik() {
        if (this.zzaak == -1) {
            this.zzaak = zzjo.zzjv().zzr(this).zzq(this);
        }
        return this.zzaak;
    }

    static <T extends zzhs<?, ?>> T zzd(Class<T> cls) {
        T t = (zzhs) zzaal.get(cls);
        if (t == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                t = (zzhs) zzaal.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (t == null) {
            t = (zzhs) ((zzhs) zzkq.zzh(cls)).zza(zzd.zzaas, (Object) null, (Object) null);
            if (t != null) {
                zzaal.put(cls, t);
            } else {
                throw new IllegalStateException();
            }
        }
        return t;
    }

    protected static <T extends zzhs<?, ?>> void zza(Class<T> cls, T t) {
        zzaal.put(cls, t);
    }

    protected static Object zza(zzjc zzjc, String str, Object[] objArr) {
        return new zzjq(zzjc, str, objArr);
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

    protected static final <T extends zzhs<T, ?>> boolean zza(T t, boolean z) {
        byte byteValue = ((Byte) t.zza(zzd.zzaan, (Object) null, (Object) null)).byteValue();
        if (byteValue == 1) {
            return true;
        }
        if (byteValue == 0) {
            return false;
        }
        boolean zzp = zzjo.zzjv().zzr(t).zzp(t);
        if (z) {
            t.zza(zzd.zzaao, (Object) zzp ? t : null, (Object) null);
        }
        return zzp;
    }

    protected static zzhx zzil() {
        return zzhu.zziq();
    }

    protected static <E> zzhz<E> zzim() {
        return zzjn.zzju();
    }

    static <T extends zzhs<T, ?>> T zza(T t, zzgr zzgr, zzhf zzhf) throws zzic {
        T t2 = (zzhs) t.zza(zzd.zzaaq, (Object) null, (Object) null);
        try {
            zzjo.zzjv().zzr(t2).zza(t2, zzgy.zza(zzgr), zzhf);
            t2.zzfy();
            return t2;
        } catch (IOException e) {
            if (e.getCause() instanceof zzic) {
                throw ((zzic) e.getCause());
            }
            throw new zzic(e.getMessage()).zzh(t2);
        } catch (RuntimeException e2) {
            if (e2.getCause() instanceof zzic) {
                throw ((zzic) e2.getCause());
            }
            throw e2;
        }
    }

    public final /* synthetic */ zzjb zzin() {
        zza zza2 = (zza) zza(zzd.zzaar, (Object) null, (Object) null);
        zza2.zza(this);
        return zza2;
    }

    public final /* synthetic */ zzjb zzio() {
        return (zza) zza(zzd.zzaar, (Object) null, (Object) null);
    }

    public final /* synthetic */ zzjc zzii() {
        return (zzhs) zza(zzd.zzaas, (Object) null, (Object) null);
    }
}
