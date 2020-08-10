package com.startapp.android.publish.common.metaData;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Pair;
import com.startapp.android.publish.adsCommon.BaseRequest;
import com.startapp.android.publish.adsCommon.C5051k;
import com.startapp.android.publish.adsCommon.C5053l;
import com.startapp.android.publish.adsCommon.C5059m;
import com.startapp.android.publish.adsCommon.Utils.C4940d;
import com.startapp.android.publish.adsCommon.Utils.C4941e;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.common.p092a.C5138a;
import com.startapp.common.p092a.C5146c;

/* compiled from: StartAppSDK */
public class MetaDataRequest extends BaseRequest {
    private String apkHash;
    private int daysSinceFirstSession;
    private long firstInstalledAppTS;
    private Integer ian;
    private float paidAmount;
    private boolean payingUser;
    private String profileId = MetaData.getInstance().getProfileId();
    private C5109a reason;
    private Pair<String, String> simpleToken;
    private int totalSessions;

    /* renamed from: com.startapp.android.publish.common.metaData.MetaDataRequest$a */
    /* compiled from: StartAppSDK */
    public enum C5109a {
        LAUNCH(1),
        APP_IDLE(2),
        IN_APP_PURCHASE(3),
        CUSTOM(4),
        PERIODIC(5),
        PAS(6);
        
        private int index;

        private C5109a(int i) {
            this.index = i;
        }
    }

    public MetaDataRequest(Context context, C5109a aVar) {
        this.totalSessions = C5051k.m3337a(context, "totalSessions", Integer.valueOf(0)).intValue();
        this.daysSinceFirstSession = calcDaysSinceFirstSession(context);
        this.paidAmount = C5051k.m3336a(context, "inAppPurchaseAmount", Float.valueOf(0.0f)).floatValue();
        this.payingUser = C5051k.m3335a(context, "payingUser", Boolean.valueOf(false)).booleanValue();
        this.reason = aVar;
        this.apkHash = calcApkHash(context, C5051k.m3334a(context), C5059m.m3378a().mo62396e(), new C4946i());
        setIan(context);
        this.simpleToken = C5053l.m3366c();
        this.firstInstalledAppTS = C5053l.m3356a();
    }

    private int calcDaysSinceFirstSession(Context context) {
        return millisToDays(System.currentTimeMillis() - C5051k.m3338a(context, "firstSessionTime", Long.valueOf(System.currentTimeMillis())).longValue());
    }

    private int millisToDays(long j) {
        return (int) (j / 86400000);
    }

    public int getTotalSessions() {
        return this.totalSessions;
    }

    public void setTotalSessions(int i) {
        this.totalSessions = i;
    }

    public String getApkHash() {
        return this.apkHash;
    }

    public void setApkHash(String str) {
        this.apkHash = str;
    }

    public int getDaysSinceFirstSession() {
        return this.daysSinceFirstSession;
    }

    public void setDaysSinceFirstSession(int i) {
        this.daysSinceFirstSession = i;
    }

    public boolean isPayingUser() {
        return this.payingUser;
    }

    public void setPayingUser(boolean z) {
        this.payingUser = z;
    }

    public float getPaidAmount() {
        return this.paidAmount;
    }

    public void setPaidAmount(float f) {
        this.paidAmount = f;
    }

    public C5109a getReason() {
        return this.reason;
    }

    public void setReason(C5109a aVar) {
        this.reason = aVar;
    }

    public int getIan() {
        return this.ian.intValue();
    }

    public void setIan(Context context) {
        int f = C5146c.m3774f(context);
        if (f > 0) {
            this.ian = Integer.valueOf(f);
        }
    }

    public String getProfileId() {
        return this.profileId;
    }

    public void setProfileId(String str) {
        this.profileId = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MetaDataRequest [totalSessions=");
        sb.append(this.totalSessions);
        sb.append(", daysSinceFirstSession=");
        sb.append(this.daysSinceFirstSession);
        sb.append(", payingUser=");
        sb.append(this.payingUser);
        sb.append(", paidAmount=");
        sb.append(this.paidAmount);
        sb.append(", reason=");
        sb.append(this.reason);
        sb.append(", profileId=");
        sb.append(this.profileId);
        sb.append("]");
        return sb.toString();
    }

    public C4941e getNameValueMap() {
        C4941e nameValueMap = super.getNameValueMap();
        if (nameValueMap == null) {
            nameValueMap = new C4940d();
        }
        addParams(nameValueMap);
        return nameValueMap;
    }

    private void addParams(C4941e eVar) {
        eVar.mo62030a(C5138a.m3710a(), (Object) C5138a.m3718d(), true);
        eVar.mo62030a("totalSessions", (Object) Integer.valueOf(this.totalSessions), true);
        eVar.mo62030a("daysSinceFirstSession", (Object) Integer.valueOf(this.daysSinceFirstSession), true);
        eVar.mo62030a("payingUser", (Object) Boolean.valueOf(this.payingUser), true);
        eVar.mo62030a("profileId", (Object) this.profileId, false);
        eVar.mo62030a("paidAmount", (Object) Float.valueOf(this.paidAmount), true);
        eVar.mo62030a("reason", (Object) this.reason, true);
        String str = this.apkHash;
        if (str != null) {
            eVar.mo62030a("apkHash", (Object) str, false);
        }
        eVar.mo62030a("ian", (Object) this.ian, false);
        eVar.mo62030a((String) this.simpleToken.first, this.simpleToken.second, false);
        long j = this.firstInstalledAppTS;
        if (j > 0 && j < Long.MAX_VALUE) {
            eVar.mo62030a("firstInstalledAppTS", (Object) Long.valueOf(j), false);
        }
    }

    public static String calcApkHash(Context context, SharedPreferences sharedPreferences, boolean z, C4946i iVar) {
        String str = "shared_prefs_app_apk_hash";
        String string = sharedPreferences.getString(str, null);
        if (!TextUtils.isEmpty(string) && !z) {
            return string;
        }
        String a = iVar.mo62037a("SHA-256", context);
        sharedPreferences.edit().putString(str, a).commit();
        return a;
    }
}
