package com.flurry.sdk;

import android.content.SharedPreferences.Editor;
import com.flurry.sdk.C1756ex.C1758a;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.flurry.sdk.ae */
public final class C1526ae extends C1766f {

    /* renamed from: a */
    String f472a = "";

    /* renamed from: b */
    boolean f473b = false;

    /* renamed from: d */
    AtomicBoolean f474d = new AtomicBoolean(false);

    C1526ae() {
        super("AdvertisingIdProvider", C1756ex.m904a(C1758a.PROVIDER));
    }

    /* renamed from: a */
    public final void mo16243a() {
        Info b = m413b();
        if (b != null) {
            this.f472a = b.getId();
            this.f473b = !b.isLimitAdTrackingEnabled();
            this.f474d.set(true);
            if (b != null) {
                C1775fe.m937a("advertising_id", b.getId());
                boolean isLimitAdTrackingEnabled = b.isLimitAdTrackingEnabled();
                Editor edit = C1576b.m502a().getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0).edit();
                edit.putBoolean(String.format(Locale.US, "com.flurry.sdk.%s", new Object[]{"ad_tracking_enabled"}), isLimitAdTrackingEnabled);
                edit.apply();
            }
        }
    }

    /* renamed from: b */
    private static Info m413b() {
        String str = "There is a problem with the Google Play Services library, which is required for Android Advertising ID support. The Google Play Services library should be integrated in any app shipping in the Play Store that uses analytics or advertising.";
        String str2 = "AdvertisingIdProvider";
        try {
            Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(C1576b.m502a());
            return new Info(advertisingIdInfo.getId(), advertisingIdInfo.isLimitAdTrackingEnabled());
        } catch (NoClassDefFoundError unused) {
            C1685cy.m762b(str2, str);
            return null;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("GOOGLE PLAY SERVICES ERROR: ");
            sb.append(e.getMessage());
            C1685cy.m762b(str2, sb.toString());
            C1685cy.m762b(str2, str);
            return null;
        }
    }
}
