package com.google.firebase.auth;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.internal.firebase_auth.zzfe;
import com.google.android.gms.internal.firebase_auth.zzfw;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;
import com.google.firebase.auth.api.internal.zzap;
import com.google.firebase.auth.api.internal.zzdr;
import com.google.firebase.auth.api.internal.zzec;
import com.google.firebase.auth.api.internal.zzed;
import com.google.firebase.auth.api.internal.zzem;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.auth.internal.zzaa;
import com.google.firebase.auth.internal.zzak;
import com.google.firebase.auth.internal.zzaq;
import com.google.firebase.auth.internal.zzas;
import com.google.firebase.auth.internal.zzat;
import com.google.firebase.auth.internal.zzau;
import com.google.firebase.auth.internal.zzax;
import com.google.firebase.auth.internal.zzg;
import com.google.firebase.auth.internal.zzj;
import com.google.firebase.auth.internal.zzm;
import com.google.firebase.auth.internal.zzz;
import com.google.firebase.internal.InternalTokenResult;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class FirebaseAuth implements InternalAuthProvider {
    private String zzhx;
    private String zzhy;
    private FirebaseApp zzik;
    /* access modifiers changed from: private */
    public final List<IdTokenListener> zzil;
    /* access modifiers changed from: private */
    public final List<com.google.firebase.auth.internal.IdTokenListener> zzim;
    /* access modifiers changed from: private */
    public List<AuthStateListener> zzin;
    private zzap zzio;
    /* access modifiers changed from: private */
    public FirebaseUser zzip;
    /* access modifiers changed from: private */
    public zzj zziq;
    private final Object zzir;
    private final Object zzis;
    private final zzat zzit;
    private final zzak zziu;
    private zzas zziv;
    private zzau zziw;

    public interface AuthStateListener {
        void onAuthStateChanged(FirebaseAuth firebaseAuth);
    }

    public interface IdTokenListener {
        void onIdTokenChanged(FirebaseAuth firebaseAuth);
    }

    class zza extends zzb implements com.google.firebase.auth.internal.zza, zzz {
        zza() {
            super();
        }

        public final void zza(Status status) {
            if (status.getStatusCode() == 17011 || status.getStatusCode() == 17021 || status.getStatusCode() == 17005) {
                FirebaseAuth.this.signOut();
            }
        }
    }

    class zzb implements com.google.firebase.auth.internal.zza {
        zzb() {
        }

        public final void zza(zzes zzes, FirebaseUser firebaseUser) {
            Preconditions.checkNotNull(zzes);
            Preconditions.checkNotNull(firebaseUser);
            firebaseUser.zza(zzes);
            FirebaseAuth.this.zza(firebaseUser, zzes, true);
        }
    }

    class zzc extends zzb implements com.google.firebase.auth.internal.zza, zzz {
        zzc(FirebaseAuth firebaseAuth) {
            super();
        }

        public final void zza(Status status) {
        }
    }

    public static FirebaseAuth getInstance() {
        return (FirebaseAuth) FirebaseApp.getInstance().get(FirebaseAuth.class);
    }

    public static FirebaseAuth getInstance(FirebaseApp firebaseApp) {
        return (FirebaseAuth) firebaseApp.get(FirebaseAuth.class);
    }

    public FirebaseAuth(FirebaseApp firebaseApp) {
        this(firebaseApp, zzec.zza(firebaseApp.getApplicationContext(), new zzed(firebaseApp.getOptions().getApiKey()).zzef()), new zzat(firebaseApp.getApplicationContext(), firebaseApp.getPersistenceKey()), zzak.zzfn());
    }

    private FirebaseAuth(FirebaseApp firebaseApp, zzap zzap, zzat zzat, zzak zzak) {
        this.zzir = new Object();
        this.zzis = new Object();
        this.zzik = (FirebaseApp) Preconditions.checkNotNull(firebaseApp);
        this.zzio = (zzap) Preconditions.checkNotNull(zzap);
        this.zzit = (zzat) Preconditions.checkNotNull(zzat);
        this.zziq = new zzj();
        this.zziu = (zzak) Preconditions.checkNotNull(zzak);
        this.zzil = new CopyOnWriteArrayList();
        this.zzim = new CopyOnWriteArrayList();
        this.zzin = new CopyOnWriteArrayList();
        this.zziw = zzau.zzfs();
        this.zzip = this.zzit.zzfr();
        FirebaseUser firebaseUser = this.zzip;
        if (firebaseUser != null) {
            zzes zzh = this.zzit.zzh(firebaseUser);
            if (zzh != null) {
                zza(this.zzip, zzh, false);
            }
        }
        this.zziu.zzf(this);
    }

    public FirebaseUser getCurrentUser() {
        return this.zzip;
    }

    public String getUid() {
        FirebaseUser firebaseUser = this.zzip;
        if (firebaseUser == null) {
            return null;
        }
        return firebaseUser.getUid();
    }

    public final void zza(FirebaseUser firebaseUser, zzes zzes, boolean z) {
        boolean z2;
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(zzes);
        FirebaseUser firebaseUser2 = this.zzip;
        boolean z3 = true;
        if (firebaseUser2 == null) {
            z2 = true;
        } else {
            boolean z4 = !firebaseUser2.zzcy().getAccessToken().equals(zzes.getAccessToken());
            boolean equals = this.zzip.getUid().equals(firebaseUser.getUid());
            z2 = !equals || z4;
            if (equals) {
                z3 = false;
            }
        }
        Preconditions.checkNotNull(firebaseUser);
        FirebaseUser firebaseUser3 = this.zzip;
        if (firebaseUser3 == null) {
            this.zzip = firebaseUser;
        } else {
            firebaseUser3.zza(firebaseUser.getProviderData());
            if (!firebaseUser.isAnonymous()) {
                this.zzip.zzcx();
            }
            this.zzip.zzb(firebaseUser.zzdb().zzdc());
        }
        if (z) {
            this.zzit.zzg(this.zzip);
        }
        if (z2) {
            FirebaseUser firebaseUser4 = this.zzip;
            if (firebaseUser4 != null) {
                firebaseUser4.zza(zzes);
            }
            zzb(this.zzip);
        }
        if (z3) {
            zzc(this.zzip);
        }
        if (z) {
            this.zzit.zza(firebaseUser, zzes);
        }
        zzct().zzc(this.zzip.zzcy());
    }

    public final void zzcs() {
        FirebaseUser firebaseUser = this.zzip;
        if (firebaseUser != null) {
            zzat zzat = this.zzit;
            Preconditions.checkNotNull(firebaseUser);
            zzat.clear(String.format("com.google.firebase.auth.GET_TOKEN_RESPONSE.%s", new Object[]{firebaseUser.getUid()}));
            this.zzip = null;
        }
        this.zzit.clear("com.google.firebase.auth.FIREBASE_USER");
        zzb((FirebaseUser) null);
        zzc((FirebaseUser) null);
    }

    private final synchronized void zza(zzas zzas) {
        this.zziv = zzas;
    }

    private final synchronized zzas zzct() {
        if (this.zziv == null) {
            zza(new zzas(this.zzik));
        }
        return this.zziv;
    }

    public FirebaseApp getApp() {
        return this.zzik;
    }

    public final FirebaseApp zzcu() {
        return this.zzik;
    }

    public void addIdTokenListener(IdTokenListener idTokenListener) {
        this.zzil.add(idTokenListener);
        this.zziw.execute(new zzj(this, idTokenListener));
    }

    public void addIdTokenListener(com.google.firebase.auth.internal.IdTokenListener idTokenListener) {
        Preconditions.checkNotNull(idTokenListener);
        this.zzim.add(idTokenListener);
        zzct().zzj(this.zzim.size());
    }

    public void removeIdTokenListener(IdTokenListener idTokenListener) {
        this.zzil.remove(idTokenListener);
    }

    public void removeIdTokenListener(com.google.firebase.auth.internal.IdTokenListener idTokenListener) {
        Preconditions.checkNotNull(idTokenListener);
        this.zzim.remove(idTokenListener);
        zzct().zzj(this.zzim.size());
    }

    public void addAuthStateListener(AuthStateListener authStateListener) {
        this.zzin.add(authStateListener);
        this.zziw.execute(new zzi(this, authStateListener));
    }

    public void removeAuthStateListener(AuthStateListener authStateListener) {
        this.zzin.remove(authStateListener);
    }

    private final void zzb(FirebaseUser firebaseUser) {
        String str = "FirebaseAuth";
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();
            StringBuilder sb = new StringBuilder(String.valueOf(uid).length() + 45);
            sb.append("Notifying id token listeners about user ( ");
            sb.append(uid);
            sb.append(" ).");
            Log.d(str, sb.toString());
        } else {
            Log.d(str, "Notifying id token listeners about a sign-out event.");
        }
        this.zziw.execute(new zzl(this, new InternalTokenResult(firebaseUser != null ? firebaseUser.zzda() : null)));
    }

    private final void zzc(FirebaseUser firebaseUser) {
        String str = "FirebaseAuth";
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();
            StringBuilder sb = new StringBuilder(String.valueOf(uid).length() + 47);
            sb.append("Notifying auth state listeners about user ( ");
            sb.append(uid);
            sb.append(" ).");
            Log.d(str, sb.toString());
        } else {
            Log.d(str, "Notifying auth state listeners about a sign-out event.");
        }
        this.zziw.execute(new zzk(this));
    }

    public Task<GetTokenResult> getAccessToken(boolean z) {
        return zza(this.zzip, z);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.firebase.auth.zzn, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v0, types: [com.google.firebase.auth.zzn, com.google.firebase.auth.internal.zzax]
      assigns: [com.google.firebase.auth.zzn]
      uses: [com.google.firebase.auth.internal.zzax]
      mth insns count: 19
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
    public final com.google.android.gms.tasks.Task<com.google.firebase.auth.GetTokenResult> zza(com.google.firebase.auth.FirebaseUser r4, boolean r5) {
        /*
            r3 = this;
            if (r4 != 0) goto L_0x0012
            com.google.android.gms.common.api.Status r4 = new com.google.android.gms.common.api.Status
            r5 = 17495(0x4457, float:2.4516E-41)
            r4.<init>(r5)
            com.google.firebase.FirebaseException r4 = com.google.firebase.auth.api.internal.zzdr.zzb(r4)
            com.google.android.gms.tasks.Task r4 = com.google.android.gms.tasks.Tasks.forException(r4)
            return r4
        L_0x0012:
            com.google.android.gms.internal.firebase_auth.zzes r0 = r4.zzcy()
            boolean r1 = r0.isValid()
            if (r1 == 0) goto L_0x002b
            if (r5 != 0) goto L_0x002b
            java.lang.String r4 = r0.getAccessToken()
            com.google.firebase.auth.GetTokenResult r4 = com.google.firebase.auth.internal.zzan.zzdf(r4)
            com.google.android.gms.tasks.Task r4 = com.google.android.gms.tasks.Tasks.forResult(r4)
            return r4
        L_0x002b:
            com.google.firebase.auth.api.internal.zzap r5 = r3.zzio
            com.google.firebase.FirebaseApp r1 = r3.zzik
            java.lang.String r0 = r0.zzs()
            com.google.firebase.auth.zzn r2 = new com.google.firebase.auth.zzn
            r2.<init>(r3)
            com.google.android.gms.tasks.Task r4 = r5.zza(r1, r4, r0, r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.FirebaseAuth.zza(com.google.firebase.auth.FirebaseUser, boolean):com.google.android.gms.tasks.Task");
    }

    public Task<AuthResult> signInWithCredential(AuthCredential authCredential) {
        Preconditions.checkNotNull(authCredential);
        if (authCredential instanceof EmailAuthCredential) {
            EmailAuthCredential emailAuthCredential = (EmailAuthCredential) authCredential;
            if (!emailAuthCredential.zzcr()) {
                return this.zzio.zzb(this.zzik, emailAuthCredential.getEmail(), emailAuthCredential.getPassword(), this.zzhy, (com.google.firebase.auth.internal.zza) new zzb());
            }
            if (zzbs(emailAuthCredential.zzco())) {
                return Tasks.forException(zzdr.zzb(new Status(17072)));
            }
            return this.zzio.zza(this.zzik, emailAuthCredential, (com.google.firebase.auth.internal.zza) new zzb());
        } else if (!(authCredential instanceof PhoneAuthCredential)) {
            return this.zzio.zza(this.zzik, authCredential, this.zzhy, (com.google.firebase.auth.internal.zza) new zzb());
        } else {
            return this.zzio.zza(this.zzik, (PhoneAuthCredential) authCredential, this.zzhy, (com.google.firebase.auth.internal.zza) new zzb());
        }
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: type inference failed for: r6v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: type inference failed for: r2v2, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: type inference failed for: r7v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r5v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax]
      assigns: [com.google.firebase.auth.FirebaseAuth$zza]
      uses: [com.google.firebase.auth.internal.zzax]
      mth insns count: 51
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
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.tasks.Task<java.lang.Void> zza(com.google.firebase.auth.FirebaseUser r9, com.google.firebase.auth.AuthCredential r10) {
        /*
            r8 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r9)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r10)
            java.lang.Class<com.google.firebase.auth.EmailAuthCredential> r0 = com.google.firebase.auth.EmailAuthCredential.class
            java.lang.Class r1 = r10.getClass()
            boolean r0 = r0.isAssignableFrom(r1)
            if (r0 == 0) goto L_0x0063
            com.google.firebase.auth.EmailAuthCredential r10 = (com.google.firebase.auth.EmailAuthCredential) r10
            java.lang.String r0 = r10.getSignInMethod()
            java.lang.String r1 = "password"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x003b
            com.google.firebase.auth.api.internal.zzap r1 = r8.zzio
            com.google.firebase.FirebaseApp r2 = r8.zzik
            java.lang.String r4 = r10.getEmail()
            java.lang.String r5 = r10.getPassword()
            java.lang.String r6 = r9.zzba()
            com.google.firebase.auth.FirebaseAuth$zza r7 = new com.google.firebase.auth.FirebaseAuth$zza
            r7.<init>()
            r3 = r9
            com.google.android.gms.tasks.Task r9 = r1.zza(r2, r3, r4, r5, r6, r7)
            return r9
        L_0x003b:
            java.lang.String r0 = r10.zzco()
            boolean r0 = r8.zzbs(r0)
            if (r0 == 0) goto L_0x0055
            com.google.android.gms.common.api.Status r9 = new com.google.android.gms.common.api.Status
            r10 = 17072(0x42b0, float:2.3923E-41)
            r9.<init>(r10)
            com.google.firebase.FirebaseException r9 = com.google.firebase.auth.api.internal.zzdr.zzb(r9)
            com.google.android.gms.tasks.Task r9 = com.google.android.gms.tasks.Tasks.forException(r9)
            return r9
        L_0x0055:
            com.google.firebase.auth.api.internal.zzap r0 = r8.zzio
            com.google.firebase.FirebaseApp r1 = r8.zzik
            com.google.firebase.auth.FirebaseAuth$zza r2 = new com.google.firebase.auth.FirebaseAuth$zza
            r2.<init>()
            com.google.android.gms.tasks.Task r9 = r0.zza(r1, r9, r10, r2)
            return r9
        L_0x0063:
            boolean r0 = r10 instanceof com.google.firebase.auth.PhoneAuthCredential
            if (r0 == 0) goto L_0x007b
            com.google.firebase.auth.api.internal.zzap r1 = r8.zzio
            com.google.firebase.FirebaseApp r2 = r8.zzik
            r4 = r10
            com.google.firebase.auth.PhoneAuthCredential r4 = (com.google.firebase.auth.PhoneAuthCredential) r4
            java.lang.String r5 = r8.zzhy
            com.google.firebase.auth.FirebaseAuth$zza r6 = new com.google.firebase.auth.FirebaseAuth$zza
            r6.<init>()
            r3 = r9
            com.google.android.gms.tasks.Task r9 = r1.zza(r2, r3, r4, r5, r6)
            return r9
        L_0x007b:
            com.google.firebase.auth.api.internal.zzap r0 = r8.zzio
            com.google.firebase.FirebaseApp r1 = r8.zzik
            java.lang.String r4 = r9.zzba()
            com.google.firebase.auth.FirebaseAuth$zza r5 = new com.google.firebase.auth.FirebaseAuth$zza
            r5.<init>()
            r2 = r9
            r3 = r10
            com.google.android.gms.tasks.Task r9 = r0.zza(r1, r2, r3, r4, r5)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.FirebaseAuth.zza(com.google.firebase.auth.FirebaseUser, com.google.firebase.auth.AuthCredential):com.google.android.gms.tasks.Task");
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: type inference failed for: r6v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: type inference failed for: r2v2, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: type inference failed for: r7v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r5v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax]
      assigns: [com.google.firebase.auth.FirebaseAuth$zza]
      uses: [com.google.firebase.auth.internal.zzax]
      mth insns count: 51
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
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.tasks.Task<com.google.firebase.auth.AuthResult> zzb(com.google.firebase.auth.FirebaseUser r9, com.google.firebase.auth.AuthCredential r10) {
        /*
            r8 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r9)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r10)
            java.lang.Class<com.google.firebase.auth.EmailAuthCredential> r0 = com.google.firebase.auth.EmailAuthCredential.class
            java.lang.Class r1 = r10.getClass()
            boolean r0 = r0.isAssignableFrom(r1)
            if (r0 == 0) goto L_0x0063
            com.google.firebase.auth.EmailAuthCredential r10 = (com.google.firebase.auth.EmailAuthCredential) r10
            java.lang.String r0 = r10.getSignInMethod()
            java.lang.String r1 = "password"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x003b
            com.google.firebase.auth.api.internal.zzap r1 = r8.zzio
            com.google.firebase.FirebaseApp r2 = r8.zzik
            java.lang.String r4 = r10.getEmail()
            java.lang.String r5 = r10.getPassword()
            java.lang.String r6 = r9.zzba()
            com.google.firebase.auth.FirebaseAuth$zza r7 = new com.google.firebase.auth.FirebaseAuth$zza
            r7.<init>()
            r3 = r9
            com.google.android.gms.tasks.Task r9 = r1.zzb(r2, r3, r4, r5, r6, r7)
            return r9
        L_0x003b:
            java.lang.String r0 = r10.zzco()
            boolean r0 = r8.zzbs(r0)
            if (r0 == 0) goto L_0x0055
            com.google.android.gms.common.api.Status r9 = new com.google.android.gms.common.api.Status
            r10 = 17072(0x42b0, float:2.3923E-41)
            r9.<init>(r10)
            com.google.firebase.FirebaseException r9 = com.google.firebase.auth.api.internal.zzdr.zzb(r9)
            com.google.android.gms.tasks.Task r9 = com.google.android.gms.tasks.Tasks.forException(r9)
            return r9
        L_0x0055:
            com.google.firebase.auth.api.internal.zzap r0 = r8.zzio
            com.google.firebase.FirebaseApp r1 = r8.zzik
            com.google.firebase.auth.FirebaseAuth$zza r2 = new com.google.firebase.auth.FirebaseAuth$zza
            r2.<init>()
            com.google.android.gms.tasks.Task r9 = r0.zzb(r1, r9, r10, r2)
            return r9
        L_0x0063:
            boolean r0 = r10 instanceof com.google.firebase.auth.PhoneAuthCredential
            if (r0 == 0) goto L_0x007b
            com.google.firebase.auth.api.internal.zzap r1 = r8.zzio
            com.google.firebase.FirebaseApp r2 = r8.zzik
            r4 = r10
            com.google.firebase.auth.PhoneAuthCredential r4 = (com.google.firebase.auth.PhoneAuthCredential) r4
            java.lang.String r5 = r8.zzhy
            com.google.firebase.auth.FirebaseAuth$zza r6 = new com.google.firebase.auth.FirebaseAuth$zza
            r6.<init>()
            r3 = r9
            com.google.android.gms.tasks.Task r9 = r1.zzb(r2, r3, r4, r5, r6)
            return r9
        L_0x007b:
            com.google.firebase.auth.api.internal.zzap r0 = r8.zzio
            com.google.firebase.FirebaseApp r1 = r8.zzik
            java.lang.String r4 = r9.zzba()
            com.google.firebase.auth.FirebaseAuth$zza r5 = new com.google.firebase.auth.FirebaseAuth$zza
            r5.<init>()
            r2 = r9
            r3 = r10
            com.google.android.gms.tasks.Task r9 = r0.zzb(r1, r2, r3, r4, r5)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.FirebaseAuth.zzb(com.google.firebase.auth.FirebaseUser, com.google.firebase.auth.AuthCredential):com.google.android.gms.tasks.Task");
    }

    public Task<AuthResult> signInWithCustomToken(String str) {
        Preconditions.checkNotEmpty(str);
        return this.zzio.zza(this.zzik, str, this.zzhy, (com.google.firebase.auth.internal.zza) new zzb());
    }

    public Task<AuthResult> signInWithEmailAndPassword(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        return this.zzio.zzb(this.zzik, str, str2, this.zzhy, (com.google.firebase.auth.internal.zza) new zzb());
    }

    public Task<AuthResult> signInWithEmailLink(String str, String str2) {
        return signInWithCredential(EmailAuthProvider.getCredentialWithLink(str, str2));
    }

    public Task<AuthResult> signInAnonymously() {
        FirebaseUser firebaseUser = this.zzip;
        if (firebaseUser == null || !firebaseUser.isAnonymous()) {
            return this.zzio.zza(this.zzik, (com.google.firebase.auth.internal.zza) new zzb(), this.zzhy);
        }
        zzm zzm = (zzm) this.zzip;
        zzm.zzs(false);
        return Tasks.forResult(new zzg(zzm));
    }

    public final void zza(String str, long j, TimeUnit timeUnit, OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, Activity activity, Executor executor, boolean z) {
        OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks2;
        long j2 = j;
        long convert = TimeUnit.SECONDS.convert(j, timeUnit);
        if (convert < 0 || convert > 120) {
            throw new IllegalArgumentException("We only support 0-120 seconds for sms-auto-retrieval timeout");
        }
        zzfe zzfe = new zzfe(str, convert, z, this.zzhx, this.zzhy);
        if (this.zziq.zzfe()) {
            String str2 = str;
            if (str.equals(this.zziq.getPhoneNumber())) {
                onVerificationStateChangedCallbacks2 = new zzm(this, onVerificationStateChangedCallbacks);
                this.zzio.zza(this.zzik, zzfe, onVerificationStateChangedCallbacks2, activity, executor);
            }
        }
        onVerificationStateChangedCallbacks2 = onVerificationStateChangedCallbacks;
        this.zzio.zza(this.zzik, zzfe, onVerificationStateChangedCallbacks2, activity, executor);
    }

    /* JADX WARNING: type inference failed for: r0v7, types: [com.google.firebase.auth.FirebaseAuth$zzc, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v7, types: [com.google.firebase.auth.FirebaseAuth$zzc, com.google.firebase.auth.internal.zzax]
      assigns: [com.google.firebase.auth.FirebaseAuth$zzc]
      uses: [com.google.firebase.auth.internal.zzax]
      mth insns count: 39
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
    public com.google.android.gms.tasks.Task<java.lang.Void> updateCurrentUser(com.google.firebase.auth.FirebaseUser r4) {
        /*
            r3 = this;
            if (r4 == 0) goto L_0x0076
            java.lang.String r0 = r4.zzba()
            if (r0 == 0) goto L_0x0014
            java.lang.String r0 = r4.zzba()
            java.lang.String r1 = r3.zzhy
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0022
        L_0x0014:
            java.lang.String r0 = r3.zzhy
            if (r0 == 0) goto L_0x0032
            java.lang.String r1 = r4.zzba()
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0032
        L_0x0022:
            com.google.android.gms.common.api.Status r4 = new com.google.android.gms.common.api.Status
            r0 = 17072(0x42b0, float:2.3923E-41)
            r4.<init>(r0)
            com.google.firebase.FirebaseException r4 = com.google.firebase.auth.api.internal.zzdr.zzb(r4)
            com.google.android.gms.tasks.Task r4 = com.google.android.gms.tasks.Tasks.forException(r4)
            return r4
        L_0x0032:
            com.google.firebase.FirebaseApp r0 = r4.zzcu()
            com.google.firebase.FirebaseOptions r0 = r0.getOptions()
            java.lang.String r0 = r0.getApiKey()
            com.google.firebase.FirebaseApp r1 = r3.zzik
            com.google.firebase.FirebaseOptions r1 = r1.getOptions()
            java.lang.String r1 = r1.getApiKey()
            com.google.android.gms.internal.firebase_auth.zzes r2 = r4.zzcy()
            boolean r2 = r2.isValid()
            if (r2 == 0) goto L_0x006c
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x006c
            com.google.firebase.FirebaseApp r0 = r3.zzik
            com.google.firebase.auth.FirebaseUser r0 = com.google.firebase.auth.internal.zzm.zza(r0, r4)
            com.google.android.gms.internal.firebase_auth.zzes r4 = r4.zzcy()
            r1 = 1
            r3.zza(r0, r4, r1)
            r4 = 0
            com.google.android.gms.tasks.Task r4 = com.google.android.gms.tasks.Tasks.forResult(r4)
            return r4
        L_0x006c:
            com.google.firebase.auth.FirebaseAuth$zzc r0 = new com.google.firebase.auth.FirebaseAuth$zzc
            r0.<init>(r3)
            com.google.android.gms.tasks.Task r4 = r3.zza(r4, r0)
            return r4
        L_0x0076:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Cannot update current user with null user!"
            r4.<init>(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.FirebaseAuth.updateCurrentUser(com.google.firebase.auth.FirebaseUser):com.google.android.gms.tasks.Task");
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax]
      assigns: [com.google.firebase.auth.FirebaseAuth$zza]
      uses: [com.google.firebase.auth.internal.zzax]
      mth insns count: 3
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
    public final com.google.android.gms.tasks.Task<java.lang.Void> zzd(com.google.firebase.auth.FirebaseUser r2) {
        /*
            r1 = this;
            com.google.firebase.auth.FirebaseAuth$zza r0 = new com.google.firebase.auth.FirebaseAuth$zza
            r0.<init>()
            com.google.android.gms.tasks.Task r2 = r1.zza(r2, r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.FirebaseAuth.zzd(com.google.firebase.auth.FirebaseUser):com.google.android.gms.tasks.Task");
    }

    private final Task<Void> zza(FirebaseUser firebaseUser, zzax zzax) {
        Preconditions.checkNotNull(firebaseUser);
        return this.zzio.zza(this.zzik, firebaseUser, zzax);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax]
      assigns: [com.google.firebase.auth.FirebaseAuth$zza]
      uses: [com.google.firebase.auth.internal.zzax]
      mth insns count: 7
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
    public final com.google.android.gms.tasks.Task<com.google.firebase.auth.AuthResult> zzc(com.google.firebase.auth.FirebaseUser r4, com.google.firebase.auth.AuthCredential r5) {
        /*
            r3 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r5)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r4)
            com.google.firebase.auth.api.internal.zzap r0 = r3.zzio
            com.google.firebase.FirebaseApp r1 = r3.zzik
            com.google.firebase.auth.FirebaseAuth$zza r2 = new com.google.firebase.auth.FirebaseAuth$zza
            r2.<init>()
            com.google.android.gms.tasks.Task r4 = r0.zza(r1, r4, r5, r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.FirebaseAuth.zzc(com.google.firebase.auth.FirebaseUser, com.google.firebase.auth.AuthCredential):com.google.android.gms.tasks.Task");
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax]
      assigns: [com.google.firebase.auth.FirebaseAuth$zza]
      uses: [com.google.firebase.auth.internal.zzax]
      mth insns count: 7
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
    public final com.google.android.gms.tasks.Task<com.google.firebase.auth.AuthResult> zza(com.google.firebase.auth.FirebaseUser r4, java.lang.String r5) {
        /*
            r3 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r5)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r4)
            com.google.firebase.auth.api.internal.zzap r0 = r3.zzio
            com.google.firebase.FirebaseApp r1 = r3.zzik
            com.google.firebase.auth.FirebaseAuth$zza r2 = new com.google.firebase.auth.FirebaseAuth$zza
            r2.<init>()
            com.google.android.gms.tasks.Task r4 = r0.zzd(r1, r4, r5, r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.FirebaseAuth.zza(com.google.firebase.auth.FirebaseUser, java.lang.String):com.google.android.gms.tasks.Task");
    }

    public Task<AuthResult> createUserWithEmailAndPassword(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        return this.zzio.zza(this.zzik, str, str2, this.zzhy, (com.google.firebase.auth.internal.zza) new zzb());
    }

    public Task<SignInMethodQueryResult> fetchSignInMethodsForEmail(String str) {
        Preconditions.checkNotEmpty(str);
        return this.zzio.zza(this.zzik, str, this.zzhy);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax]
      assigns: [com.google.firebase.auth.FirebaseAuth$zza]
      uses: [com.google.firebase.auth.internal.zzax]
      mth insns count: 7
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
    public final com.google.android.gms.tasks.Task<java.lang.Void> zza(com.google.firebase.auth.FirebaseUser r4, com.google.firebase.auth.UserProfileChangeRequest r5) {
        /*
            r3 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r4)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r5)
            com.google.firebase.auth.api.internal.zzap r0 = r3.zzio
            com.google.firebase.FirebaseApp r1 = r3.zzik
            com.google.firebase.auth.FirebaseAuth$zza r2 = new com.google.firebase.auth.FirebaseAuth$zza
            r2.<init>()
            com.google.android.gms.tasks.Task r4 = r0.zza(r1, r4, r5, r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.FirebaseAuth.zza(com.google.firebase.auth.FirebaseUser, com.google.firebase.auth.UserProfileChangeRequest):com.google.android.gms.tasks.Task");
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax]
      assigns: [com.google.firebase.auth.FirebaseAuth$zza]
      uses: [com.google.firebase.auth.internal.zzax]
      mth insns count: 7
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
    public final com.google.android.gms.tasks.Task<java.lang.Void> zzb(com.google.firebase.auth.FirebaseUser r4, java.lang.String r5) {
        /*
            r3 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r4)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r5)
            com.google.firebase.auth.api.internal.zzap r0 = r3.zzio
            com.google.firebase.FirebaseApp r1 = r3.zzik
            com.google.firebase.auth.FirebaseAuth$zza r2 = new com.google.firebase.auth.FirebaseAuth$zza
            r2.<init>()
            com.google.android.gms.tasks.Task r4 = r0.zzb(r1, r4, r5, r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.FirebaseAuth.zzb(com.google.firebase.auth.FirebaseUser, java.lang.String):com.google.android.gms.tasks.Task");
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax]
      assigns: [com.google.firebase.auth.FirebaseAuth$zza]
      uses: [com.google.firebase.auth.internal.zzax]
      mth insns count: 7
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
    public final com.google.android.gms.tasks.Task<java.lang.Void> zza(com.google.firebase.auth.FirebaseUser r4, com.google.firebase.auth.PhoneAuthCredential r5) {
        /*
            r3 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r4)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r5)
            com.google.firebase.auth.api.internal.zzap r0 = r3.zzio
            com.google.firebase.FirebaseApp r1 = r3.zzik
            com.google.firebase.auth.FirebaseAuth$zza r2 = new com.google.firebase.auth.FirebaseAuth$zza
            r2.<init>()
            com.google.android.gms.tasks.Task r4 = r0.zza(r1, r4, r5, r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.FirebaseAuth.zza(com.google.firebase.auth.FirebaseUser, com.google.firebase.auth.PhoneAuthCredential):com.google.android.gms.tasks.Task");
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v0, types: [com.google.firebase.auth.FirebaseAuth$zza, com.google.firebase.auth.internal.zzax]
      assigns: [com.google.firebase.auth.FirebaseAuth$zza]
      uses: [com.google.firebase.auth.internal.zzax]
      mth insns count: 7
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
    public final com.google.android.gms.tasks.Task<java.lang.Void> zzc(com.google.firebase.auth.FirebaseUser r4, java.lang.String r5) {
        /*
            r3 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r4)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r5)
            com.google.firebase.auth.api.internal.zzap r0 = r3.zzio
            com.google.firebase.FirebaseApp r1 = r3.zzik
            com.google.firebase.auth.FirebaseAuth$zza r2 = new com.google.firebase.auth.FirebaseAuth$zza
            r2.<init>()
            com.google.android.gms.tasks.Task r4 = r0.zzc(r1, r4, r5, r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.FirebaseAuth.zzc(com.google.firebase.auth.FirebaseUser, java.lang.String):com.google.android.gms.tasks.Task");
    }

    public Task<Void> sendPasswordResetEmail(String str) {
        Preconditions.checkNotEmpty(str);
        return sendPasswordResetEmail(str, null);
    }

    public Task<Void> sendPasswordResetEmail(String str, ActionCodeSettings actionCodeSettings) {
        Preconditions.checkNotEmpty(str);
        if (actionCodeSettings == null) {
            actionCodeSettings = ActionCodeSettings.zzcj();
        }
        String str2 = this.zzhx;
        if (str2 != null) {
            actionCodeSettings.zzbq(str2);
        }
        actionCodeSettings.zzb(zzfw.PASSWORD_RESET);
        return this.zzio.zza(this.zzik, str, actionCodeSettings, this.zzhy);
    }

    public Task<Void> sendSignInLinkToEmail(String str, ActionCodeSettings actionCodeSettings) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(actionCodeSettings);
        if (actionCodeSettings.canHandleCodeInApp()) {
            String str2 = this.zzhx;
            if (str2 != null) {
                actionCodeSettings.zzbq(str2);
            }
            return this.zzio.zzb(this.zzik, str, actionCodeSettings, this.zzhy);
        }
        throw new IllegalArgumentException("You must set canHandleCodeInApp in your ActionCodeSettings to true for Email-Link Sign-in.");
    }

    public boolean isSignInWithEmailLink(String str) {
        return EmailAuthCredential.isSignInWithEmailLink(str);
    }

    public final Task<Void> zza(ActionCodeSettings actionCodeSettings, String str) {
        Preconditions.checkNotEmpty(str);
        if (this.zzhx != null) {
            if (actionCodeSettings == null) {
                actionCodeSettings = ActionCodeSettings.zzcj();
            }
            actionCodeSettings.zzbq(this.zzhx);
        }
        return this.zzio.zza(this.zzik, actionCodeSettings, str);
    }

    public Task<ActionCodeResult> checkActionCode(String str) {
        Preconditions.checkNotEmpty(str);
        return this.zzio.zzb(this.zzik, str, this.zzhy);
    }

    public Task<Void> applyActionCode(String str) {
        Preconditions.checkNotEmpty(str);
        return this.zzio.zzc(this.zzik, str, this.zzhy);
    }

    public Task<String> verifyPasswordResetCode(String str) {
        Preconditions.checkNotEmpty(str);
        return this.zzio.zzd(this.zzik, str, this.zzhy);
    }

    public Task<Void> confirmPasswordReset(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        return this.zzio.zza(this.zzik, str, str2, this.zzhy);
    }

    public Task<AuthResult> startActivityForSignInWithProvider(Activity activity, FederatedAuthProvider federatedAuthProvider) {
        Preconditions.checkNotNull(federatedAuthProvider);
        Preconditions.checkNotNull(activity);
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        if (!this.zziu.zza(activity, taskCompletionSource, this)) {
            return Tasks.forException(zzdr.zzb(new Status(17057)));
        }
        zzaq.zza(activity.getApplicationContext(), this);
        federatedAuthProvider.zza(activity);
        return taskCompletionSource.getTask();
    }

    public final Task<AuthResult> zza(Activity activity, FederatedAuthProvider federatedAuthProvider, FirebaseUser firebaseUser) {
        Preconditions.checkNotNull(activity);
        Preconditions.checkNotNull(federatedAuthProvider);
        Preconditions.checkNotNull(firebaseUser);
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        if (!this.zziu.zza(activity, taskCompletionSource, this, firebaseUser)) {
            return Tasks.forException(zzdr.zzb(new Status(17057)));
        }
        zzaq.zza(activity.getApplicationContext(), this, firebaseUser);
        federatedAuthProvider.zzb(activity);
        return taskCompletionSource.getTask();
    }

    public final Task<AuthResult> zzb(Activity activity, FederatedAuthProvider federatedAuthProvider, FirebaseUser firebaseUser) {
        Preconditions.checkNotNull(activity);
        Preconditions.checkNotNull(federatedAuthProvider);
        Preconditions.checkNotNull(firebaseUser);
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        if (!this.zziu.zza(activity, taskCompletionSource, this, firebaseUser)) {
            return Tasks.forException(zzdr.zzb(new Status(17057)));
        }
        zzaq.zza(activity.getApplicationContext(), this, firebaseUser);
        federatedAuthProvider.zzc(activity);
        return taskCompletionSource.getTask();
    }

    public Task<AuthResult> getPendingAuthResult() {
        return this.zziu.zzfo();
    }

    public final Task<Void> zze(FirebaseUser firebaseUser) {
        Preconditions.checkNotNull(firebaseUser);
        return this.zzio.zza(firebaseUser, (zzaa) new zzo(this, firebaseUser));
    }

    public void signOut() {
        zzcs();
        zzas zzas = this.zziv;
        if (zzas != null) {
            zzas.cancel();
        }
    }

    public void setLanguageCode(String str) {
        Preconditions.checkNotEmpty(str);
        synchronized (this.zzir) {
            this.zzhx = str;
        }
    }

    public String getLanguageCode() {
        String str;
        synchronized (this.zzir) {
            str = this.zzhx;
        }
        return str;
    }

    public final void zzf(String str) {
        Preconditions.checkNotEmpty(str);
        synchronized (this.zzis) {
            this.zzhy = str;
        }
    }

    public final String zzba() {
        String str;
        synchronized (this.zzis) {
            str = this.zzhy;
        }
        return str;
    }

    public void useAppLanguage() {
        synchronized (this.zzir) {
            this.zzhx = zzem.zzem();
        }
    }

    public FirebaseAuthSettings getFirebaseAuthSettings() {
        return this.zziq;
    }

    public Task<Void> setFirebaseUIVersion(String str) {
        return this.zzio.setFirebaseUIVersion(str);
    }

    private final boolean zzbs(String str) {
        zzb zzbr = zzb.zzbr(str);
        return zzbr != null && !TextUtils.equals(this.zzhy, zzbr.zzba());
    }
}
