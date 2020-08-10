package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import com.google.android.gms.internal.auth.zzaz;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class zzr extends zzaz {
    public static final Creator<zzr> CREATOR = new zzs();
    private static final HashMap<String, Field<?, ?>> zzaz;
    private String mPackageName;
    private final Set<Integer> zzba;
    private zzt zzbk;
    private String zzbl;
    private String zzbm;
    private final int zzv;

    public zzr() {
        this.zzba = new HashSet(3);
        this.zzv = 1;
    }

    zzr(Set<Integer> set, int i, zzt zzt, String str, String str2, String str3) {
        this.zzba = set;
        this.zzv = i;
        this.zzbk = zzt;
        this.zzbl = str;
        this.mPackageName = str2;
        this.zzbm = str3;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        Set<Integer> set = this.zzba;
        if (set.contains(Integer.valueOf(1))) {
            SafeParcelWriter.writeInt(parcel, 1, this.zzv);
        }
        if (set.contains(Integer.valueOf(2))) {
            SafeParcelWriter.writeParcelable(parcel, 2, this.zzbk, i, true);
        }
        if (set.contains(Integer.valueOf(3))) {
            SafeParcelWriter.writeString(parcel, 3, this.zzbl, true);
        }
        if (set.contains(Integer.valueOf(4))) {
            SafeParcelWriter.writeString(parcel, 4, this.mPackageName, true);
        }
        if (set.contains(Integer.valueOf(5))) {
            SafeParcelWriter.writeString(parcel, 5, this.zzbm, true);
        }
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    /* access modifiers changed from: protected */
    public boolean isFieldSet(Field field) {
        return this.zzba.contains(Integer.valueOf(field.getSafeParcelableFieldId()));
    }

    /* access modifiers changed from: protected */
    public Object getFieldValue(Field field) {
        int safeParcelableFieldId = field.getSafeParcelableFieldId();
        if (safeParcelableFieldId == 1) {
            return Integer.valueOf(this.zzv);
        }
        if (safeParcelableFieldId == 2) {
            return this.zzbk;
        }
        if (safeParcelableFieldId == 3) {
            return this.zzbl;
        }
        if (safeParcelableFieldId == 4) {
            return this.mPackageName;
        }
        int safeParcelableFieldId2 = field.getSafeParcelableFieldId();
        StringBuilder sb = new StringBuilder(37);
        sb.append("Unknown SafeParcelable id=");
        sb.append(safeParcelableFieldId2);
        throw new IllegalStateException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public void setStringInternal(Field<?, ?> field, String str, String str2) {
        int safeParcelableFieldId = field.getSafeParcelableFieldId();
        if (safeParcelableFieldId == 3) {
            this.zzbl = str2;
        } else if (safeParcelableFieldId == 4) {
            this.mPackageName = str2;
        } else {
            throw new IllegalArgumentException(String.format("Field with id=%d is not known to be a string.", new Object[]{Integer.valueOf(safeParcelableFieldId)}));
        }
        this.zzba.add(Integer.valueOf(safeParcelableFieldId));
    }

    public <T extends FastJsonResponse> void addConcreteTypeInternal(Field<?, ?> field, String str, T t) {
        int safeParcelableFieldId = field.getSafeParcelableFieldId();
        if (safeParcelableFieldId == 2) {
            this.zzbk = (zzt) t;
            this.zzba.add(Integer.valueOf(safeParcelableFieldId));
            return;
        }
        throw new IllegalArgumentException(String.format("Field with id=%d is not a known custom type. Found %s", new Object[]{Integer.valueOf(safeParcelableFieldId), t.getClass().getCanonicalName()}));
    }

    public /* synthetic */ Map getFieldMappings() {
        return zzaz;
    }

    static {
        HashMap<String, Field<?, ?>> hashMap = new HashMap<>();
        zzaz = hashMap;
        String str = "authenticatorInfo";
        hashMap.put(str, Field.forConcreteType(str, 2, zzt.class));
        String str2 = "signature";
        zzaz.put(str2, Field.forString(str2, 3));
        String str3 = "package";
        zzaz.put(str3, Field.forString(str3, 4));
    }
}
