package com.startapp.android.publish.adsCommon;

import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdPreferences.Placement;

/* compiled from: StartAppSDK */
public class AdsConstants {
    public static final int AD_INFORMATION_EXTENDED_ID = 1475346434;
    public static final int AD_INFORMATION_ID = 1475346433;
    public static final Boolean FORCE_NATIVE_VIDEO_PLAYER;
    public static final int LIST_3D_CLOSE_BUTTON_ID = 1475346435;
    public static final String OVERRIDE_HOST = null;
    public static final Boolean OVERRIDE_NETWORK;
    public static final int SPLASH_NATIVE_MAIN_LAYOUT_ID = 1475346437;
    public static final int STARTAPP_AD_MAIN_LAYOUT_ID = 1475346432;
    public static final Boolean VIDEO_DEBUG;

    /* renamed from: a */
    public static final String f3017a = new String("get");

    /* renamed from: b */
    public static final String f3018b;

    /* renamed from: c */
    public static final String f3019c;

    /* renamed from: d */
    public static final String f3020d = new String("trackdownload");

    /* renamed from: e */
    public static final String f3021e;

    /* renamed from: f */
    public static final String f3022f = new String("https://imp.startappservice.com/tracking/adImpression");

    /* renamed from: g */
    public static final Boolean f3023g;

    /* renamed from: h */
    public static final String f3024h = C4946i.m2928b();

    /* renamed from: i */
    public static final String f3025i = C4946i.m2931c();

    /* renamed from: j */
    public static final String f3026j = C4946i.m2933d();

    /* renamed from: k */
    public static final String[] f3027k = {"back_", "back_dark", "browser_icon_dark", "forward_", "forward_dark", "x_dark"};

    /* renamed from: l */
    public static final String[] f3028l = {"empty_star", "filled_star", "half_star"};

    /* renamed from: com.startapp.android.publish.adsCommon.AdsConstants$1 */
    /* compiled from: StartAppSDK */
    static /* synthetic */ class C49281 {

        /* renamed from: a */
        static final /* synthetic */ int[] f3029a = new int[ServiceApiType.values().length];

        /* renamed from: b */
        static final /* synthetic */ int[] f3030b = new int[AdApiType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(11:0|1|2|3|(2:5|6)|7|9|10|11|12|14) */
        /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0032 */
        static {
            /*
                com.startapp.android.publish.adsCommon.AdsConstants$AdApiType[] r0 = com.startapp.android.publish.adsCommon.AdsConstants.AdApiType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f3030b = r0
                r0 = 1
                int[] r1 = f3030b     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.startapp.android.publish.adsCommon.AdsConstants$AdApiType r2 = com.startapp.android.publish.adsCommon.AdsConstants.AdApiType.HTML     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = f3030b     // Catch:{ NoSuchFieldError -> 0x001f }
                com.startapp.android.publish.adsCommon.AdsConstants$AdApiType r3 = com.startapp.android.publish.adsCommon.AdsConstants.AdApiType.JSON     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                com.startapp.android.publish.adsCommon.AdsConstants$ServiceApiType[] r2 = com.startapp.android.publish.adsCommon.AdsConstants.ServiceApiType.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                f3029a = r2
                int[] r2 = f3029a     // Catch:{ NoSuchFieldError -> 0x0032 }
                com.startapp.android.publish.adsCommon.AdsConstants$ServiceApiType r3 = com.startapp.android.publish.adsCommon.AdsConstants.ServiceApiType.METADATA     // Catch:{ NoSuchFieldError -> 0x0032 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0032 }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x0032 }
            L_0x0032:
                int[] r0 = f3029a     // Catch:{ NoSuchFieldError -> 0x003c }
                com.startapp.android.publish.adsCommon.AdsConstants$ServiceApiType r2 = com.startapp.android.publish.adsCommon.AdsConstants.ServiceApiType.DOWNLOAD     // Catch:{ NoSuchFieldError -> 0x003c }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x003c }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x003c }
            L_0x003c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.adsCommon.AdsConstants.C49281.<clinit>():void");
        }
    }

    /* compiled from: StartAppSDK */
    public enum AdApiType {
        HTML,
        JSON
    }

    /* compiled from: StartAppSDK */
    public enum ServiceApiType {
        METADATA,
        DOWNLOAD
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(f3017a);
        sb.append(new String("ads"));
        f3018b = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(f3017a);
        sb2.append(new String("htmlad"));
        f3019c = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(f3017a);
        sb3.append(new String("adsmetadata"));
        f3021e = sb3.toString();
        Boolean valueOf = Boolean.valueOf(false);
        OVERRIDE_NETWORK = valueOf;
        f3023g = valueOf;
        VIDEO_DEBUG = valueOf;
        FORCE_NATIVE_VIDEO_PLAYER = valueOf;
    }

    /* renamed from: a */
    public static String m2856a(ServiceApiType serviceApiType) {
        String str;
        int i = C49281.f3029a[serviceApiType.ordinal()];
        String str2 = null;
        if (i == 1) {
            str2 = f3021e;
            str = MetaData.getInstance().getMetaDataHost();
        } else if (i != 2) {
            str = null;
        } else {
            str2 = f3020d;
            str = MetaData.getInstance().getAdPlatformHost();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        return sb.toString();
    }

    /* renamed from: a */
    public static String m2855a(AdApiType adApiType, Placement placement) {
        String str;
        int i = C49281.f3030b[adApiType.ordinal()];
        String str2 = null;
        if (i == 1) {
            str2 = f3019c;
            str = MetaData.getInstance().getAdPlatformHost(placement);
        } else if (i != 2) {
            str = null;
        } else {
            str2 = f3018b;
            str = MetaData.getInstance().getAdPlatformHost(placement);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        return sb.toString();
    }

    /* renamed from: a */
    public static Boolean m2854a() {
        return VIDEO_DEBUG;
    }
}
