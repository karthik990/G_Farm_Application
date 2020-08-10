package com.startapp.android.publish.ads.splash;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;
import com.startapp.android.publish.ads.p066a.C4736b;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.common.p092a.C5155g;
import java.io.Serializable;

/* renamed from: com.startapp.android.publish.ads.splash.g */
/* compiled from: StartAppSDK */
public class C4835g extends C4736b {

    /* renamed from: d */
    private SplashConfig f2712d = null;

    /* renamed from: e */
    private C4836h f2713e;

    /* renamed from: f */
    private boolean f2714f = false;

    /* renamed from: g */
    private boolean f2715g = false;

    /* renamed from: q */
    public void mo61178q() {
    }

    /* renamed from: a */
    public void mo61151a(Bundle bundle) {
        C5155g.m3807a("SplashMode", 3, "onCreate");
        this.f2712d = (SplashConfig) mo61149a().getSerializableExtra("SplashConfig");
    }

    /* renamed from: a */
    public boolean mo61158a(int i, KeyEvent keyEvent) {
        C5155g.m3807a("SplashMode", 3, "onKeyDown");
        if (this.f2714f) {
            if (i == 25) {
                if (!this.f2715g) {
                    this.f2715g = true;
                    this.f2713e.mo61620g();
                    Toast.makeText(mo61159b(), "Test Mode", 0).show();
                    return true;
                }
            } else if (i == 24 && this.f2715g) {
                mo61159b().finish();
                return true;
            }
        }
        return i == 4;
    }

    /* renamed from: s */
    public void mo61180s() {
        C5155g.m3807a("SplashMode", 3, "onPause");
        C4836h hVar = this.f2713e;
        if (hVar != null) {
            hVar.mo61613a();
        }
    }

    /* renamed from: t */
    public void mo61181t() {
        C5155g.m3807a("SplashMode", 3, "onStop");
        C4836h hVar = this.f2713e;
        if (hVar != null) {
            hVar.mo61615b();
        }
    }

    /* renamed from: u */
    public void mo61182u() {
        AdPreferences adPreferences;
        C5155g.m3807a("SplashMode", 3, "onResume");
        if (this.f2712d != null) {
            Serializable serializableExtra = mo61149a().getSerializableExtra("AdPreference");
            if (serializableExtra != null) {
                adPreferences = (AdPreferences) serializableExtra;
            } else {
                adPreferences = new AdPreferences();
            }
            this.f2714f = mo61149a().getBooleanExtra("testMode", false);
            this.f2713e = new C4836h(mo61159b(), this.f2712d, adPreferences);
            this.f2713e.mo61614a(null);
        }
    }

    /* renamed from: v */
    public void mo61183v() {
        C5155g.m3807a("SplashMode", 3, "onDestroy");
    }
}
