package com.truenet.android.p096a;

import android.os.Build.VERSION;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import java.util.List;
import p000a.p001a.p002a.C0007g;
import p000a.p001a.p003b.p005b.C0032h;

/* renamed from: com.truenet.android.a.f */
/* compiled from: StartAppSDK */
public final class C5199f {
    /* renamed from: a */
    public static final int m3943a(TelephonyManager telephonyManager) {
        C0032h.m44b(telephonyManager, "$receiver");
        if (VERSION.SDK_INT < 26) {
            return m3945c(telephonyManager);
        }
        List allCellInfo = telephonyManager.getAllCellInfo();
        CellInfo cellInfo = allCellInfo != null ? (CellInfo) C0007g.m14d(allCellInfo) : null;
        String str = "info.cellIdentity";
        if (cellInfo instanceof CellInfoGsm) {
            CellIdentityGsm cellIdentity = ((CellInfoGsm) cellInfo).getCellIdentity();
            C0032h.m41a((Object) cellIdentity, str);
            return cellIdentity.getCid();
        } else if (!(cellInfo instanceof CellInfoCdma)) {
            return m3945c(telephonyManager);
        } else {
            CellIdentityCdma cellIdentity2 = ((CellInfoCdma) cellInfo).getCellIdentity();
            C0032h.m41a((Object) cellIdentity2, str);
            return cellIdentity2.getBasestationId();
        }
    }

    /* renamed from: b */
    public static final int m3944b(TelephonyManager telephonyManager) {
        C0032h.m44b(telephonyManager, "$receiver");
        if (VERSION.SDK_INT < 26) {
            return m3946d(telephonyManager);
        }
        List allCellInfo = telephonyManager.getAllCellInfo();
        CellInfo cellInfo = allCellInfo != null ? (CellInfo) C0007g.m14d(allCellInfo) : null;
        if (!(cellInfo instanceof CellInfoGsm)) {
            return m3946d(telephonyManager);
        }
        CellIdentityGsm cellIdentity = ((CellInfoGsm) cellInfo).getCellIdentity();
        C0032h.m41a((Object) cellIdentity, "info.cellIdentity");
        return cellIdentity.getLac();
    }

    /* renamed from: c */
    private static final int m3945c(TelephonyManager telephonyManager) {
        CellLocation cellLocation = telephonyManager.getCellLocation();
        if (cellLocation instanceof GsmCellLocation) {
            return ((GsmCellLocation) cellLocation).getCid();
        }
        if (cellLocation instanceof CdmaCellLocation) {
            return ((CdmaCellLocation) cellLocation).getBaseStationId();
        }
        return -1;
    }

    /* renamed from: d */
    private static final int m3946d(TelephonyManager telephonyManager) {
        CellLocation cellLocation = telephonyManager.getCellLocation();
        if (cellLocation instanceof GsmCellLocation) {
            return ((GsmCellLocation) cellLocation).getLac();
        }
        return -1;
    }
}
