package com.google.android.gms.internal.firebase_remote_config;

import com.fasterxml.jackson.core.JsonPointer;
import kotlin.text.Typography;
import org.objectweb.asm.signature.SignatureVisitor;

enum zzal {
    PLUS(Character.valueOf(SignatureVisitor.EXTENDS), "", ",", false, true),
    HASH(Character.valueOf('#'), "#", ",", false, true),
    DOT(Character.valueOf('.'), ".", ".", false, false),
    FORWARD_SLASH(Character.valueOf(JsonPointer.SEPARATOR), "/", "/", false, false),
    SEMI_COLON(Character.valueOf(';'), ";", ";", true, false),
    QUERY(Character.valueOf('?'), "?", "&", true, false),
    AMP(Character.valueOf(Typography.amp), "&", "&", true, false),
    SIMPLE(null, "", ",", false, false);
    
    private final Character zzcm;
    private final String zzcn;
    private final String zzco;
    private final boolean zzcp;
    private final boolean zzcq;

    private zzal(Character ch, String str, String str2, boolean z, boolean z2) {
        this.zzcm = ch;
        this.zzcn = (String) zzds.checkNotNull(str);
        this.zzco = (String) zzds.checkNotNull(str2);
        this.zzcp = z;
        this.zzcq = z2;
        if (ch != null) {
            zzak.zzcd.put(ch, this);
        }
    }

    /* access modifiers changed from: 0000 */
    public final String zzal() {
        return this.zzcn;
    }

    /* access modifiers changed from: 0000 */
    public final String zzam() {
        return this.zzco;
    }

    /* access modifiers changed from: 0000 */
    public final boolean zzan() {
        return this.zzcp;
    }

    /* access modifiers changed from: 0000 */
    public final int zzao() {
        return this.zzcm == null ? 0 : 1;
    }

    /* access modifiers changed from: 0000 */
    public final String zzaa(String str) {
        if (this.zzcq) {
            return zzcr.zzai(str);
        }
        return zzcr.zzag(str);
    }

    /* access modifiers changed from: 0000 */
    public final boolean zzap() {
        return this.zzcq;
    }
}
