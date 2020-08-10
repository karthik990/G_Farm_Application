package com.startapp.android.publish.ads.banner;

import com.startapp.common.p042c.C2362f;
import java.io.Serializable;

/* compiled from: StartAppSDK */
public class BannerOptions implements Serializable {
    private static final long serialVersionUID = 1;
    private int adsNumber = 4;
    private int delayFaceTime = 5000;
    @C2362f(mo20786b = Effect.class)
    private Effect effect = Effect.ROTATE_3D;
    private int height = 50;
    private float heightRatio = 1.0f;
    private int htmlAdsNumber = 10;
    private float minScale = 0.88f;
    private int minViewabilityPercentage = 50;
    private int refreshRate = 60000;
    private int refreshRate3D = 60000;
    private boolean rotateThroughOnStart = true;
    private int rotateThroughSpeedMult = 2;
    private int scalePower = 4;
    private int stepSize = 5;
    private int timeBetweenFrames = 25;
    private int width = 300;
    private float widthRatio = 1.0f;

    /* compiled from: StartAppSDK */
    public enum Effect {
        ROTATE_3D(1),
        EXCHANGE(2),
        FLY_IN(3);
        
        private int index;

        private Effect(int i) {
            this.index = i;
        }

        public int getIndex() {
            return this.index;
        }

        public int getRotationMultiplier() {
            return (int) Math.pow(2.0d, (double) (this.index - 1));
        }

        public static Effect getByIndex(int i) {
            Effect effect = ROTATE_3D;
            Effect[] values = values();
            for (int i2 = 0; i2 < values.length; i2++) {
                if (values[i2].getIndex() == i) {
                    effect = values[i2];
                }
            }
            return effect;
        }

        public static Effect getByName(String str) {
            Effect effect = ROTATE_3D;
            Effect[] values = values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].name().toLowerCase().compareTo(str.toLowerCase()) == 0) {
                    effect = values[i];
                }
            }
            return effect;
        }
    }

    public BannerOptions() {
    }

    public BannerOptions(BannerOptions bannerOptions) {
        this.width = bannerOptions.width;
        this.height = bannerOptions.height;
        this.timeBetweenFrames = bannerOptions.timeBetweenFrames;
        this.stepSize = bannerOptions.stepSize;
        this.delayFaceTime = bannerOptions.delayFaceTime;
        this.adsNumber = bannerOptions.adsNumber;
        this.htmlAdsNumber = bannerOptions.htmlAdsNumber;
        this.refreshRate3D = bannerOptions.refreshRate3D;
        this.widthRatio = bannerOptions.widthRatio;
        this.heightRatio = bannerOptions.heightRatio;
        this.minScale = bannerOptions.minScale;
        this.scalePower = bannerOptions.scalePower;
        this.effect = bannerOptions.effect;
        this.rotateThroughOnStart = bannerOptions.rotateThroughOnStart;
        this.rotateThroughSpeedMult = bannerOptions.rotateThroughSpeedMult;
        this.refreshRate = bannerOptions.refreshRate;
    }

    /* renamed from: a */
    public void mo61272a(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    /* renamed from: a */
    public int mo61271a() {
        return this.timeBetweenFrames;
    }

    /* renamed from: b */
    public int mo61273b() {
        return this.stepSize;
    }

    /* renamed from: c */
    public int mo61274c() {
        return this.delayFaceTime;
    }

    /* renamed from: d */
    public int mo61275d() {
        return this.width;
    }

    /* renamed from: e */
    public int mo61276e() {
        return this.height;
    }

    /* renamed from: f */
    public int mo61278f() {
        return this.adsNumber;
    }

    /* renamed from: g */
    public int mo61279g() {
        return this.htmlAdsNumber;
    }

    /* renamed from: h */
    public int mo61280h() {
        return this.refreshRate3D;
    }

    /* renamed from: i */
    public int mo61281i() {
        return this.refreshRate;
    }

    /* renamed from: j */
    public float mo61282j() {
        return this.widthRatio;
    }

    /* renamed from: k */
    public float mo61283k() {
        return this.heightRatio;
    }

    /* renamed from: l */
    public float mo61284l() {
        return this.minScale;
    }

    /* renamed from: m */
    public int mo61285m() {
        return this.scalePower;
    }

    /* renamed from: n */
    public Effect mo61286n() {
        return this.effect;
    }

    /* renamed from: o */
    public boolean mo61287o() {
        return this.rotateThroughOnStart;
    }

    /* renamed from: p */
    public int mo61288p() {
        return this.rotateThroughSpeedMult;
    }

    /* renamed from: q */
    public int mo61289q() {
        return this.minViewabilityPercentage;
    }

    public boolean equals(Object obj) {
        BannerOptions bannerOptions = (BannerOptions) obj;
        return bannerOptions.mo61278f() == mo61278f() && bannerOptions.mo61279g() == mo61279g() && bannerOptions.mo61280h() == mo61280h() && bannerOptions.mo61274c() == mo61274c() && bannerOptions.mo61276e() == mo61276e() && bannerOptions.mo61273b() == mo61273b() && bannerOptions.mo61271a() == mo61271a() && bannerOptions.mo61275d() == mo61275d() && bannerOptions.mo61281i() == mo61281i();
    }
}
