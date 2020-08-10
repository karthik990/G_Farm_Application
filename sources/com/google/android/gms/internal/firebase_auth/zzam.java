package com.google.android.gms.internal.firebase_auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class zzam {
    /* access modifiers changed from: private */
    public final int limit;
    /* access modifiers changed from: private */
    public final zzae zzgm;
    private final boolean zzgn;
    private final zzas zzgo;

    private zzam(zzas zzas) {
        this(zzas, false, zzai.zzgk, Integer.MAX_VALUE);
    }

    private zzam(zzas zzas, boolean z, zzae zzae, int i) {
        this.zzgo = zzas;
        this.zzgn = false;
        this.zzgm = zzae;
        this.limit = Integer.MAX_VALUE;
    }

    public static zzam zzd(char c) {
        zzag zzag = new zzag(c);
        zzaj.checkNotNull(zzag);
        return new zzam(new zzal(zzag));
    }

    public static zzam zzbp(String str) {
        zzaj.checkArgument(str.length() != 0, "The separator may not be the empty string.");
        if (str.length() == 1) {
            return zzd(str.charAt(0));
        }
        return new zzam(new zzan(str));
    }

    public final List<String> zza(CharSequence charSequence) {
        zzaj.checkNotNull(charSequence);
        Iterator zza = this.zzgo.zza(this, charSequence);
        ArrayList arrayList = new ArrayList();
        while (zza.hasNext()) {
            arrayList.add((String) zza.next());
        }
        return Collections.unmodifiableList(arrayList);
    }
}
