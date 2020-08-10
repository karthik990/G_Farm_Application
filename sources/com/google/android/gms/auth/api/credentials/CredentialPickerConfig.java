package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class CredentialPickerConfig extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<CredentialPickerConfig> CREATOR = new zze();
    private final boolean mShowCancelButton;
    private final int zzu;
    private final boolean zzv;
    @Deprecated
    private final boolean zzw;
    private final int zzx;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Prompt {
        public static final int CONTINUE = 1;
        public static final int SIGN_IN = 2;
        public static final int SIGN_UP = 3;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public boolean mShowCancelButton = true;
        /* access modifiers changed from: private */
        public boolean zzv = false;
        /* access modifiers changed from: private */
        public int zzy = 1;

        public Builder setShowAddAccountButton(boolean z) {
            this.zzv = z;
            return this;
        }

        public Builder setShowCancelButton(boolean z) {
            this.mShowCancelButton = z;
            return this;
        }

        public Builder setPrompt(int i) {
            this.zzy = i;
            return this;
        }

        @Deprecated
        public Builder setForNewAccount(boolean z) {
            this.zzy = z ? 3 : 1;
            return this;
        }

        public CredentialPickerConfig build() {
            return new CredentialPickerConfig(this);
        }
    }

    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r3v2, types: [boolean] */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r3v4, types: [int] */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v1
      assigns: [?[boolean, int, float, short, byte, char], ?[int, float, short, byte, char], ?[int, float, boolean, short, byte, char, OBJECT, ARRAY]]
      uses: [boolean, int]
      mth insns count: 17
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
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    CredentialPickerConfig(int r2, boolean r3, boolean r4, boolean r5, int r6) {
        /*
            r1 = this;
            r1.<init>()
            r1.zzu = r2
            r1.zzv = r3
            r1.mShowCancelButton = r4
            r3 = 1
            r4 = 3
            r0 = 2
            if (r2 >= r0) goto L_0x0016
            r1.zzw = r5
            if (r5 == 0) goto L_0x0013
            r3 = 3
        L_0x0013:
            r1.zzx = r3
            return
        L_0x0016:
            if (r6 != r4) goto L_0x0019
            goto L_0x001a
        L_0x0019:
            r3 = 0
        L_0x001a:
            r1.zzw = r3
            r1.zzx = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.credentials.CredentialPickerConfig.<init>(int, boolean, boolean, boolean, int):void");
    }

    private CredentialPickerConfig(Builder builder) {
        this(2, builder.zzv, builder.mShowCancelButton, false, builder.zzy);
    }

    public final boolean shouldShowAddAccountButton() {
        return this.zzv;
    }

    public final boolean shouldShowCancelButton() {
        return this.mShowCancelButton;
    }

    @Deprecated
    public final boolean isForNewAccount() {
        return this.zzx == 3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBoolean(parcel, 1, shouldShowAddAccountButton());
        SafeParcelWriter.writeBoolean(parcel, 2, shouldShowCancelButton());
        SafeParcelWriter.writeBoolean(parcel, 3, isForNewAccount());
        SafeParcelWriter.writeInt(parcel, 4, this.zzx);
        SafeParcelWriter.writeInt(parcel, 1000, this.zzu);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
