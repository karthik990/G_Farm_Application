package com.startapp.android.publish.adsCommon.adinformation;

import com.startapp.android.publish.adsCommon.adinformation.AdInformationPositions.Position;
import com.startapp.common.p042c.C2362f;
import java.io.Serializable;

/* renamed from: com.startapp.android.publish.adsCommon.adinformation.c */
/* compiled from: StartAppSDK */
public class C4978c implements Serializable {
    private static final long serialVersionUID = 1;
    private boolean enable = true;
    private boolean enableOverride = false;
    @C2362f(mo20786b = Position.class)
    private Position position = Position.getByName(AdInformationPositions.f3102a);
    private boolean positionOverride = false;

    private C4978c() {
    }

    /* renamed from: a */
    public static C4978c m3006a() {
        return new C4978c();
    }

    /* renamed from: b */
    public boolean mo62127b() {
        return this.enable;
    }

    /* renamed from: a */
    public void mo62126a(boolean z) {
        this.enable = z;
        this.enableOverride = true;
    }

    /* renamed from: c */
    public Position mo62128c() {
        return this.position;
    }

    /* renamed from: a */
    public void mo62125a(Position position2) {
        this.position = position2;
        if (position2 != null) {
            this.positionOverride = true;
        } else {
            this.positionOverride = false;
        }
    }

    /* renamed from: d */
    public boolean mo62129d() {
        return this.positionOverride;
    }

    /* renamed from: e */
    public boolean mo62130e() {
        return this.enableOverride;
    }
}
