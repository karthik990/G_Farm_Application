package com.truenet.android;

import com.startapp.common.p042c.C2362f;
import java.util.ArrayList;
import java.util.List;
import p000a.p001a.p003b.p005b.C0032h;

/* compiled from: StartAppSDK */
public final class ValidationResults {
    @C2362f(mo20786b = ArrayList.class, mo20787c = ValidationResult.class)
    private final List<ValidationResult> results;

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<com.truenet.android.ValidationResult>, for r1v0, types: [java.util.List] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ com.truenet.android.ValidationResults copy$default(com.truenet.android.ValidationResults r0, java.util.List<com.truenet.android.ValidationResult> r1, int r2, java.lang.Object r3) {
        /*
            r2 = r2 & 1
            if (r2 == 0) goto L_0x0006
            java.util.List<com.truenet.android.ValidationResult> r1 = r0.results
        L_0x0006:
            com.truenet.android.ValidationResults r0 = r0.copy(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.truenet.android.ValidationResults.copy$default(com.truenet.android.ValidationResults, java.util.List, int, java.lang.Object):com.truenet.android.ValidationResults");
    }

    public final List<ValidationResult> component1() {
        return this.results;
    }

    public final ValidationResults copy(List<ValidationResult> list) {
        C0032h.m44b(list, "results");
        return new ValidationResults(list);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0010, code lost:
        if (p000a.p001a.p003b.p005b.C0032h.m43a((java.lang.Object) r1.results, (java.lang.Object) ((com.truenet.android.ValidationResults) r2).results) != false) goto L_0x0015;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r2) {
        /*
            r1 = this;
            if (r1 == r2) goto L_0x0015
            boolean r0 = r2 instanceof com.truenet.android.ValidationResults
            if (r0 == 0) goto L_0x0013
            com.truenet.android.ValidationResults r2 = (com.truenet.android.ValidationResults) r2
            java.util.List<com.truenet.android.ValidationResult> r0 = r1.results
            java.util.List<com.truenet.android.ValidationResult> r2 = r2.results
            boolean r2 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r2)
            if (r2 == 0) goto L_0x0013
            goto L_0x0015
        L_0x0013:
            r2 = 0
            return r2
        L_0x0015:
            r2 = 1
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.truenet.android.ValidationResults.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        List<ValidationResult> list = this.results;
        if (list != null) {
            return list.hashCode();
        }
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ValidationResults(results=");
        sb.append(this.results);
        sb.append(")");
        return sb.toString();
    }

    public ValidationResults(List<ValidationResult> list) {
        C0032h.m44b(list, "results");
        this.results = list;
    }

    public final List<ValidationResult> getResults() {
        return this.results;
    }
}
