package com.startapp.android.publish.adsCommon.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import com.startapp.android.publish.ads.p066a.C4736b;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.C5160b;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5155g;

/* compiled from: StartAppSDK */
public class OverlayActivity extends Activity {

    /* renamed from: a */
    private C4736b f3080a;

    /* renamed from: b */
    private boolean f3081b;

    /* renamed from: c */
    private int f3082c;

    /* renamed from: d */
    private boolean f3083d;

    /* renamed from: e */
    private Bundle f3084e;

    /* renamed from: f */
    private boolean f3085f = false;

    /* renamed from: g */
    private int f3086g = -1;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        boolean z = false;
        overridePendingTransition(0, 0);
        super.onCreate(bundle);
        boolean booleanExtra = getIntent().getBooleanExtra("videoAd", false);
        requestWindowFeature(1);
        if (getIntent().getBooleanExtra("fullscreen", false) || booleanExtra) {
            getWindow().setFlags(1024, 1024);
        }
        C5155g.m3807a("AppWallActivity", 2, "AppWallActivity::onCreate");
        String str = "activityShouldLockOrientation";
        this.f3083d = getIntent().getBooleanExtra(str, true);
        if (bundle == null && !booleanExtra) {
            C5160b.m3831a((Context) this).mo62880a(new Intent("com.startapp.android.ShowDisplayBroadcastListener"));
        }
        if (bundle != null) {
            this.f3086g = bundle.getInt("activityLockedOrientation", -1);
            this.f3083d = bundle.getBoolean(str, true);
        }
        this.f3082c = getIntent().getIntExtra("orientation", getResources().getConfiguration().orientation);
        if (getResources().getConfiguration().orientation != this.f3082c) {
            z = true;
        }
        this.f3081b = z;
        if (!this.f3081b) {
            m2964a();
            this.f3080a.mo61151a(bundle);
            return;
        }
        this.f3084e = bundle;
    }

    /* renamed from: a */
    private void m2964a() {
        this.f3080a = C4736b.m2181a(this, getIntent(), Placement.getByIndex(getIntent().getIntExtra("placement", 0)));
        if (this.f3080a == null) {
            finish();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.f3081b) {
            m2964a();
            this.f3080a.mo61151a(this.f3084e);
            this.f3080a.mo61182u();
            this.f3081b = false;
        }
        this.f3080a.mo61150a(configuration);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        C4736b bVar = this.f3080a;
        if (bVar == null || bVar.mo61158a(i, keyEvent)) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        C5155g.m3807a("AppWallActivity", 2, "OverlayActivity::onPause");
        super.onPause();
        if (!this.f3081b) {
            this.f3080a.mo61180s();
            C4988c.m3099a((Context) this);
        }
        overridePendingTransition(0, 0);
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        C5155g.m3807a("AppWallActivity", 2, "AppWallActivity::onSaveInstanceState");
        super.onSaveInstanceState(bundle);
        if (!this.f3081b) {
            this.f3080a.mo61161b(bundle);
            bundle.putInt("activityLockedOrientation", this.f3086g);
            bundle.putBoolean("activityShouldLockOrientation", this.f3083d);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        C5155g.m3807a("AppWallActivity", 2, "AppWallActivity::onResume");
        super.onResume();
        if (this.f3085f) {
            this.f3080a.mo61163c();
        }
        int i = this.f3086g;
        if (i == -1) {
            this.f3086g = C4946i.m2901a((Activity) this, this.f3082c, this.f3083d);
        } else {
            C5146c.m3750a((Activity) this, i);
        }
        if (!this.f3081b) {
            this.f3080a.mo61182u();
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        C5155g.m3807a("AppWallActivity", 2, "AppWallActivity::onStop");
        super.onStop();
        if (!this.f3081b) {
            this.f3080a.mo61181t();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        C5155g.m3807a("AppWallActivity", 2, "AppWallActivity::onDestroy");
        if (!this.f3081b) {
            this.f3080a.mo61183v();
            this.f3080a = null;
            C4946i.m2911a((Activity) this, false);
        }
        super.onDestroy();
    }

    public void onBackPressed() {
        if (!this.f3080a.mo61179r()) {
            super.onBackPressed();
        }
    }

    public void finish() {
        C4736b bVar = this.f3080a;
        if (bVar != null) {
            bVar.mo61178q();
        }
        super.finish();
    }
}
