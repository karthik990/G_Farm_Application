package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzrc;
import com.google.android.gms.internal.gtm.zzrc.zza;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class zzrc<MessageType extends zzrc<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzpl<MessageType, BuilderType> {
    private static Map<Object, zzrc<?, ?>> zzbam = new ConcurrentHashMap();
    protected zzts zzbak = zzts.zzrj();
    private int zzbal = -1;

    public static abstract class zza<MessageType extends zzrc<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzpm<MessageType, BuilderType> {
        private final MessageType zzban;
        private MessageType zzbao;
        private boolean zzbap = false;

        protected zza(MessageType messagetype) {
            this.zzban = messagetype;
            this.zzbao = (zzrc) messagetype.zza(zze.zzbau, (Object) null, (Object) null);
        }

        public final boolean isInitialized() {
            return zzrc.zza(this.zzbao, false);
        }

        /* renamed from: zzpj */
        public MessageType zzpl() {
            if (this.zzbap) {
                return this.zzbao;
            }
            this.zzbao.zzmi();
            this.zzbap = true;
            return this.zzbao;
        }

        /* renamed from: zzpk */
        public final MessageType zzpm() {
            MessageType messagetype = (zzrc) zzpl();
            if (messagetype.isInitialized()) {
                return messagetype;
            }
            throw new zztq(messagetype);
        }

        public final BuilderType zza(MessageType messagetype) {
            if (this.zzbap) {
                MessageType messagetype2 = (zzrc) this.zzbao.zza(zze.zzbau, (Object) null, (Object) null);
                zza(messagetype2, this.zzbao);
                this.zzbao = messagetype2;
                this.zzbap = false;
            }
            zza(this.zzbao, messagetype);
            return this;
        }

        private static void zza(MessageType messagetype, MessageType messagetype2) {
            zzsw.zzqs().zzaf(messagetype).zzd(messagetype, messagetype2);
        }

        public final /* synthetic */ zzpm zzmx() {
            return (zza) clone();
        }

        public final /* synthetic */ zzsk zzpi() {
            return this.zzban;
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zza zza = (zza) ((zzrc) this.zzban).zza(zze.zzbav, (Object) null, (Object) null);
            zza.zza((MessageType) (zzrc) zzpl());
            return zza;
        }
    }

    public static class zzb<T extends zzrc<T, ?>> extends zzpn<T> {
        private final T zzban;

        public zzb(T t) {
            this.zzban = t;
        }

        public final /* synthetic */ Object zza(zzqe zzqe, zzqp zzqp) throws zzrk {
            return zzrc.zza(this.zzban, zzqe, zzqp);
        }
    }

    public static abstract class zzc<MessageType extends zzc<MessageType, BuilderType>, BuilderType> extends zzrc<MessageType, BuilderType> implements zzsm {
        protected zzqt<Object> zzbaq = zzqt.zzov();
    }

    public static class zzd<ContainingType extends zzsk, Type> extends zzqn<ContainingType, Type> {
    }

    /* 'enum' access flag removed */
    public static final class zze {
        public static final int zzbar = 1;
        public static final int zzbas = 2;
        public static final int zzbat = 3;
        public static final int zzbau = 4;
        public static final int zzbav = 5;
        public static final int zzbaw = 6;
        public static final int zzbax = 7;
        private static final /* synthetic */ int[] zzbay = {zzbar, zzbas, zzbat, zzbau, zzbav, zzbaw, zzbax};
        public static final int zzbaz = 1;
        public static final int zzbba = 2;
        private static final /* synthetic */ int[] zzbbb = {zzbaz, zzbba};
        public static final int zzbbc = 1;
        public static final int zzbbd = 2;
        private static final /* synthetic */ int[] zzbbe = {zzbbc, zzbbd};

        public static int[] zzpn() {
            return (int[]) zzbay.clone();
        }
    }

    /* access modifiers changed from: protected */
    public abstract Object zza(int i, Object obj, Object obj2);

    public String toString() {
        return zzsn.zza(this, super.toString());
    }

    public int hashCode() {
        if (this.zzavp != 0) {
            return this.zzavp;
        }
        this.zzavp = zzsw.zzqs().zzaf(this).hashCode(this);
        return this.zzavp;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!((zzrc) zza(zze.zzbaw, (Object) null, (Object) null)).getClass().isInstance(obj)) {
            return false;
        }
        return zzsw.zzqs().zzaf(this).equals(this, (zzrc) obj);
    }

    /* access modifiers changed from: protected */
    public final void zzmi() {
        zzsw.zzqs().zzaf(this).zzt(this);
    }

    public final boolean isInitialized() {
        return zza((T) this, Boolean.TRUE.booleanValue());
    }

    public final BuilderType zzpd() {
        BuilderType buildertype = (zza) zza(zze.zzbav, (Object) null, (Object) null);
        buildertype.zza(this);
        return buildertype;
    }

    /* access modifiers changed from: 0000 */
    public final int zzmw() {
        return this.zzbal;
    }

    /* access modifiers changed from: 0000 */
    public final void zzag(int i) {
        this.zzbal = i;
    }

    public final void zzb(zzqj zzqj) throws IOException {
        zzsw.zzqs().zzi(getClass()).zza(this, zzql.zza(zzqj));
    }

    public final int zzpe() {
        if (this.zzbal == -1) {
            this.zzbal = zzsw.zzqs().zzaf(this).zzad(this);
        }
        return this.zzbal;
    }

    static <T extends zzrc<?, ?>> T zzg(Class<T> cls) {
        T t = (zzrc) zzbam.get(cls);
        if (t == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                t = (zzrc) zzbam.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (t == null) {
            t = (zzrc) ((zzrc) zztx.zzk(cls)).zza(zze.zzbaw, (Object) null, (Object) null);
            if (t != null) {
                zzbam.put(cls, t);
            } else {
                throw new IllegalStateException();
            }
        }
        return t;
    }

    protected static <T extends zzrc<?, ?>> void zza(Class<T> cls, T t) {
        zzbam.put(cls, t);
    }

    protected static Object zza(zzsk zzsk, String str, Object[] objArr) {
        return new zzsx(zzsk, str, objArr);
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

    protected static final <T extends zzrc<T, ?>> boolean zza(T t, boolean z) {
        byte byteValue = ((Byte) t.zza(zze.zzbar, (Object) null, (Object) null)).byteValue();
        if (byteValue == 1) {
            return true;
        }
        if (byteValue == 0) {
            return false;
        }
        boolean zzae = zzsw.zzqs().zzaf(t).zzae(t);
        if (z) {
            t.zza(zze.zzbas, (Object) zzae ? t : null, (Object) null);
        }
        return zzae;
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [com.google.android.gms.internal.gtm.zzrd, com.google.android.gms.internal.gtm.zzri] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v0, types: [com.google.android.gms.internal.gtm.zzrd, com.google.android.gms.internal.gtm.zzri]
      assigns: [com.google.android.gms.internal.gtm.zzrd]
      uses: [com.google.android.gms.internal.gtm.zzri]
      mth insns count: 2
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static com.google.android.gms.internal.gtm.zzri zzpf() {
        /*
            com.google.android.gms.internal.gtm.zzrd r0 = com.google.android.gms.internal.gtm.zzrd.zzpo()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzrc.zzpf():com.google.android.gms.internal.gtm.zzri");
    }

    static <T extends zzrc<T, ?>> T zza(T t, zzqe zzqe, zzqp zzqp) throws zzrk {
        T t2 = (zzrc) t.zza(zze.zzbau, (Object) null, (Object) null);
        try {
            zzsw.zzqs().zzaf(t2).zza(t2, zzqh.zza(zzqe), zzqp);
            t2.zzmi();
            return t2;
        } catch (IOException e) {
            if (e.getCause() instanceof zzrk) {
                throw ((zzrk) e.getCause());
            }
            throw new zzrk(e.getMessage()).zzg(t2);
        } catch (RuntimeException e2) {
            if (e2.getCause() instanceof zzrk) {
                throw ((zzrk) e2.getCause());
            }
            throw e2;
        }
    }

    public final /* synthetic */ zzsl zzpg() {
        zza zza2 = (zza) zza(zze.zzbav, (Object) null, (Object) null);
        zza2.zza(this);
        return zza2;
    }

    public final /* synthetic */ zzsl zzph() {
        return (zza) zza(zze.zzbav, (Object) null, (Object) null);
    }

    public final /* synthetic */ zzsk zzpi() {
        return (zzrc) zza(zze.zzbaw, (Object) null, (Object) null);
    }
}
