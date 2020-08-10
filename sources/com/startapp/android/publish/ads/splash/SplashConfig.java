package com.startapp.android.publish.ads.splash;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import androidx.work.WorkRequest;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.common.p042c.C2362f;
import java.io.Serializable;

/* compiled from: StartAppSDK */
public class SplashConfig implements Serializable {
    private static long DEFAULT_MAX_LOAD = 7500;
    private static final int INT_EMPTY_VALUE = -1;
    private static final String STRING_EMPTY_VALUE = "";
    private static final String VALUE_DEFAULT_HTML_BG_COLOR = "#066CAA";
    private static final String VALUE_DEFAULT_HTML_FONT_COLOR = "ffffff";
    private static final String VALUE_DEFAULT_HTML_LOADING_TYPE = "LoadingDots";
    private static final boolean VALUE_DEFAULT_HTML_SPLASH = true;
    private static final MaxAdDisplayTime VALUE_DEFAULT_MAXADDISPLAY = MaxAdDisplayTime.FOR_EVER;
    private static final long VALUE_DEFAULT_MAXLOAD = DEFAULT_MAX_LOAD;
    private static final MinSplashTime VALUE_DEFAULT_MINSPLASHTIME = MinSplashTime.REGULAR;
    private static final Orientation VALUE_DEFAULT_ORIENTATION = Orientation.AUTO;
    private static final Theme VALUE_DEFAULT_THEME = Theme.OCEAN;
    private static final long serialVersionUID = 1;
    private String appName;
    private int customScreen = -1;
    @C2362f(mo20786b = MaxAdDisplayTime.class)
    private MaxAdDisplayTime defaultMaxAdDisplayTime;
    private Long defaultMaxLoadTime;
    @C2362f(mo20786b = MinSplashTime.class)
    private MinSplashTime defaultMinSplashTime;
    @C2362f(mo20786b = Orientation.class)
    private Orientation defaultOrientation;
    @C2362f(mo20786b = Theme.class)
    private Theme defaultTheme;
    private transient String errMsg;
    private boolean forceNative = false;
    private boolean htmlSplash;
    private transient Drawable logo;
    private byte[] logoByteArray;
    private int logoRes;
    private String splashBgColor;
    private String splashFontColor;
    private String splashLoadingType;

    /* renamed from: com.startapp.android.publish.ads.splash.SplashConfig$1 */
    /* compiled from: StartAppSDK */
    static /* synthetic */ class C48221 {

        /* renamed from: $SwitchMap$com$startapp$android$publish$ads$splash$SplashConfig$Theme */
        static final /* synthetic */ int[] f2683xd2cb10b = new int[Theme.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|(3:13|14|16)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|16) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.startapp.android.publish.ads.splash.SplashConfig$Theme[] r0 = com.startapp.android.publish.ads.splash.SplashConfig.Theme.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2683xd2cb10b = r0
                int[] r0 = f2683xd2cb10b     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.startapp.android.publish.ads.splash.SplashConfig$Theme r1 = com.startapp.android.publish.ads.splash.SplashConfig.Theme.DEEP_BLUE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2683xd2cb10b     // Catch:{ NoSuchFieldError -> 0x001f }
                com.startapp.android.publish.ads.splash.SplashConfig$Theme r1 = com.startapp.android.publish.ads.splash.SplashConfig.Theme.SKY     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f2683xd2cb10b     // Catch:{ NoSuchFieldError -> 0x002a }
                com.startapp.android.publish.ads.splash.SplashConfig$Theme r1 = com.startapp.android.publish.ads.splash.SplashConfig.Theme.ASHEN_SKY     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f2683xd2cb10b     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.startapp.android.publish.ads.splash.SplashConfig$Theme r1 = com.startapp.android.publish.ads.splash.SplashConfig.Theme.BLAZE     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = f2683xd2cb10b     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.startapp.android.publish.ads.splash.SplashConfig$Theme r1 = com.startapp.android.publish.ads.splash.SplashConfig.Theme.GLOOMY     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r0 = f2683xd2cb10b     // Catch:{ NoSuchFieldError -> 0x004b }
                com.startapp.android.publish.ads.splash.SplashConfig$Theme r1 = com.startapp.android.publish.ads.splash.SplashConfig.Theme.OCEAN     // Catch:{ NoSuchFieldError -> 0x004b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                int[] r0 = f2683xd2cb10b     // Catch:{ NoSuchFieldError -> 0x0056 }
                com.startapp.android.publish.ads.splash.SplashConfig$Theme r1 = com.startapp.android.publish.ads.splash.SplashConfig.Theme.USER_DEFINED     // Catch:{ NoSuchFieldError -> 0x0056 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0056 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0056 }
            L_0x0056:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.ads.splash.SplashConfig.C48221.<clinit>():void");
        }
    }

    /* compiled from: StartAppSDK */
    public enum MaxAdDisplayTime {
        SHORT(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS),
        LONG(WorkRequest.MIN_BACKOFF_MILLIS),
        FOR_EVER(86400000);
        
        private long index;

        private MaxAdDisplayTime(long j) {
            this.index = j;
        }

        public long getIndex() {
            return this.index;
        }

        public static MaxAdDisplayTime getByIndex(long j) {
            MaxAdDisplayTime maxAdDisplayTime = SHORT;
            MaxAdDisplayTime[] values = values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].getIndex() == j) {
                    maxAdDisplayTime = values[i];
                }
            }
            return maxAdDisplayTime;
        }

        public static MaxAdDisplayTime getByName(String str) {
            MaxAdDisplayTime maxAdDisplayTime = FOR_EVER;
            MaxAdDisplayTime[] values = values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].name().toLowerCase().compareTo(str.toLowerCase()) == 0) {
                    maxAdDisplayTime = values[i];
                }
            }
            return maxAdDisplayTime;
        }
    }

    /* compiled from: StartAppSDK */
    public enum MinSplashTime {
        REGULAR(3000),
        SHORT(2000),
        LONG(5000);
        
        private long index;

        private MinSplashTime(int i) {
            this.index = (long) i;
        }

        public long getIndex() {
            return this.index;
        }

        public static MinSplashTime getByIndex(long j) {
            MinSplashTime minSplashTime = SHORT;
            MinSplashTime[] values = values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].getIndex() == j) {
                    minSplashTime = values[i];
                }
            }
            return minSplashTime;
        }

        public static MinSplashTime getByName(String str) {
            MinSplashTime minSplashTime = LONG;
            MinSplashTime[] values = values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].name().toLowerCase().compareTo(str.toLowerCase()) == 0) {
                    minSplashTime = values[i];
                }
            }
            return minSplashTime;
        }
    }

    /* compiled from: StartAppSDK */
    public enum Orientation {
        PORTRAIT(1),
        LANDSCAPE(2),
        AUTO(3);
        
        private int index;

        private Orientation(int i) {
            this.index = i;
        }

        public int getIndex() {
            return this.index;
        }

        public static Orientation getByIndex(int i) {
            Orientation orientation = PORTRAIT;
            Orientation[] values = values();
            for (int i2 = 0; i2 < values.length; i2++) {
                if (values[i2].getIndex() == i) {
                    orientation = values[i2];
                }
            }
            return orientation;
        }

        public static Orientation getByName(String str) {
            Orientation orientation = AUTO;
            Orientation[] values = values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].name().toLowerCase().compareTo(str.toLowerCase()) == 0) {
                    orientation = values[i];
                }
            }
            return orientation;
        }
    }

    /* compiled from: StartAppSDK */
    public enum Theme {
        DEEP_BLUE(1),
        SKY(2),
        ASHEN_SKY(3),
        BLAZE(4),
        GLOOMY(5),
        OCEAN(6),
        USER_DEFINED(0);
        
        private int index;

        private Theme(int i) {
            this.index = i;
        }

        public int getIndex() {
            return this.index;
        }

        public static Theme getByIndex(int i) {
            Theme theme = DEEP_BLUE;
            Theme[] values = values();
            for (int i2 = 0; i2 < values.length; i2++) {
                if (values[i2].getIndex() == i) {
                    theme = values[i2];
                }
            }
            return theme;
        }

        public static Theme getByName(String str) {
            Theme theme = DEEP_BLUE;
            Theme[] values = values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].name().toLowerCase().compareTo(str.toLowerCase()) == 0) {
                    theme = values[i];
                }
            }
            return theme;
        }
    }

    public SplashConfig() {
        String str = "";
        this.appName = str;
        this.logo = null;
        this.logoByteArray = null;
        this.logoRes = -1;
        this.defaultTheme = VALUE_DEFAULT_THEME;
        this.defaultMinSplashTime = VALUE_DEFAULT_MINSPLASHTIME;
        this.defaultMaxLoadTime = Long.valueOf(VALUE_DEFAULT_MAXLOAD);
        this.defaultMaxAdDisplayTime = VALUE_DEFAULT_MAXADDISPLAY;
        this.defaultOrientation = VALUE_DEFAULT_ORIENTATION;
        this.htmlSplash = true;
        this.splashBgColor = VALUE_DEFAULT_HTML_BG_COLOR;
        this.splashFontColor = VALUE_DEFAULT_HTML_FONT_COLOR;
        this.splashLoadingType = VALUE_DEFAULT_HTML_LOADING_TYPE;
        this.errMsg = str;
    }

    public static SplashConfig getDefaultSplashConfig() {
        SplashConfig splashConfig = new SplashConfig();
        splashConfig.setTheme(VALUE_DEFAULT_THEME).setMinSplashTime(VALUE_DEFAULT_MINSPLASHTIME).setMaxLoadAdTimeout(VALUE_DEFAULT_MAXLOAD).setMaxAdDisplayTime(VALUE_DEFAULT_MAXADDISPLAY).setOrientation(VALUE_DEFAULT_ORIENTATION).setLoadingType(VALUE_DEFAULT_HTML_LOADING_TYPE).setAppName("");
        return splashConfig;
    }

    private static void applyDefaultSplashConfig(SplashConfig splashConfig, Context context) {
        SplashConfig defaultSplashConfig = getDefaultSplashConfig();
        if (splashConfig.getTheme() == null) {
            splashConfig.setTheme(defaultSplashConfig.getTheme());
        }
        if (splashConfig.getMinSplashTime() == null) {
            splashConfig.setMinSplashTime(defaultSplashConfig.getMinSplashTime());
        }
        if (splashConfig.getMaxLoadAdTimeout() == null) {
            splashConfig.setMaxLoadAdTimeout(defaultSplashConfig.getMaxLoadAdTimeout().longValue());
        }
        if (splashConfig.getMaxAdDisplayTime() == null) {
            splashConfig.setMaxAdDisplayTime(defaultSplashConfig.getMaxAdDisplayTime());
        }
        if (splashConfig.getOrientation() == null) {
            splashConfig.setOrientation(defaultSplashConfig.getOrientation());
        }
        if (splashConfig.getLoadingType() == null) {
            splashConfig.setLoadingType(defaultSplashConfig.getLoadingType());
        }
        if (splashConfig.getAppName().equals("")) {
            splashConfig.setAppName(C4988c.m3096a(context, "Welcome!"));
        }
    }

    public SplashConfig setTheme(Theme theme) {
        this.defaultTheme = theme;
        return this;
    }

    private void setSplashColorsByTheme(Theme theme) {
        int i = C48221.f2683xd2cb10b[theme.ordinal()];
        String str = "#333333";
        String str2 = "#FFFFFF";
        String str3 = VALUE_DEFAULT_HTML_BG_COLOR;
        switch (i) {
            case 1:
                break;
            case 2:
                str3 = "#a3d4e5";
                break;
            case 3:
                str3 = "#E3E3E3";
                break;
            case 4:
                str3 = "#FF6600";
                break;
            case 5:
                str = "#33B5E5";
                str3 = "#2F353F";
                break;
            case 6:
                str = "#063D51";
                str3 = "#237C9A";
                break;
            default:
                str = VALUE_DEFAULT_HTML_FONT_COLOR;
                break;
        }
        str = str2;
        this.splashBgColor = str3;
        this.splashFontColor = str;
    }

    public SplashConfig setCustomScreen(int i) {
        this.customScreen = i;
        return this;
    }

    public SplashConfig setAppName(String str) {
        this.appName = str;
        return this;
    }

    public SplashConfig setLogo(int i) {
        this.logoRes = i;
        return this;
    }

    public SplashConfig setLogo(byte[] bArr) {
        this.logoByteArray = bArr;
        return this;
    }

    private SplashConfig setLogo(Drawable drawable) {
        this.logo = drawable;
        return this;
    }

    /* access modifiers changed from: protected */
    public SplashConfig setMaxLoadAdTimeout(long j) {
        this.defaultMaxLoadTime = Long.valueOf(j);
        return this;
    }

    public SplashConfig setOrientation(Orientation orientation) {
        this.defaultOrientation = orientation;
        return this;
    }

    public SplashConfig setMinSplashTime(MinSplashTime minSplashTime) {
        this.defaultMinSplashTime = minSplashTime;
        return this;
    }

    public SplashConfig setMaxAdDisplayTime(MaxAdDisplayTime maxAdDisplayTime) {
        this.defaultMaxAdDisplayTime = maxAdDisplayTime;
        return this;
    }

    /* access modifiers changed from: protected */
    public SplashConfig setHtmlSplash(boolean z) {
        this.htmlSplash = z;
        return this;
    }

    private void setErrorMsg(String str) {
        this.errMsg = str;
    }

    public int getCustomScreen() {
        return this.customScreen;
    }

    public String getAppName() {
        return this.appName;
    }

    public Drawable getLogo() {
        return this.logo;
    }

    public int getLogoRes() {
        return this.logoRes;
    }

    public byte[] getLogoByteArray() {
        return this.logoByteArray;
    }

    /* access modifiers changed from: protected */
    public Long getMaxLoadAdTimeout() {
        return this.defaultMaxLoadTime;
    }

    public String getErrorMessage() {
        return this.errMsg;
    }

    /* access modifiers changed from: protected */
    public Theme getTheme() {
        return this.defaultTheme;
    }

    public Orientation getOrientation() {
        return this.defaultOrientation;
    }

    public MinSplashTime getMinSplashTime() {
        return this.defaultMinSplashTime;
    }

    public MaxAdDisplayTime getMaxAdDisplayTime() {
        return this.defaultMaxAdDisplayTime;
    }

    public boolean isHtmlSplash() {
        if (this.forceNative) {
            return false;
        }
        return this.htmlSplash;
    }

    public String getBgColor() {
        return this.splashBgColor;
    }

    public String getFontColor() {
        return this.splashFontColor;
    }

    public String getLoadingType() {
        return this.splashLoadingType;
    }

    public SplashConfig setLoadingType(String str) {
        this.splashLoadingType = str;
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean validate(Context context) {
        if (C48221.f2683xd2cb10b[getTheme().ordinal()] != 7) {
            if (getAppName().equals("")) {
                setAppName(C4988c.m3096a(context, "Welcome!"));
            }
            if (getLogo() == null && getLogoByteArray() == null) {
                if (getLogoRes() == -1) {
                    setLogo(context.getApplicationInfo().icon);
                    setLogo(context.getResources().getDrawable(context.getApplicationInfo().icon));
                } else {
                    setLogo(context.getResources().getDrawable(getLogoRes()));
                }
            }
        } else if (getCustomScreen() == -1) {
            setErrorMsg("StartApp: Exception getting custom screen resource id, make sure it is set");
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public View getLayout(Context context) {
        if (C48221.f2683xd2cb10b[getTheme().ordinal()] != 7) {
            return C4847i.m2542a(context, this);
        }
        try {
            return ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(getCustomScreen(), null);
        } catch (NotFoundException unused) {
            throw new NotFoundException("StartApp: Can't find Custom layout resource");
        } catch (InflateException unused2) {
            throw new InflateException("StartApp: Can't inflate layout in Custom mode, Are you sure layout resource is valid?");
        } catch (Exception e) {
            C5017f.m3256a(context, C5015d.EXCEPTION, "SplashConfig.getLayout - System service failed", e.getMessage(), "");
            return null;
        }
    }

    public void setDefaults(Context context) {
        SplashConfig a = C4834f.m2519b().mo61612a();
        if (a == null) {
            a = getDefaultSplashConfig();
        } else {
            setHtmlSplash(a.isHtmlSplash());
        }
        applyDefaultSplashConfig(a, context);
        if (getMaxAdDisplayTime() == null) {
            setMaxAdDisplayTime(a.getMaxAdDisplayTime());
        }
        if (getMaxLoadAdTimeout() == null) {
            setMaxLoadAdTimeout(a.getMaxLoadAdTimeout().longValue());
        }
        if (getMinSplashTime() == null) {
            setMinSplashTime(a.getMinSplashTime());
        }
        if (getOrientation() == null) {
            setOrientation(a.getOrientation());
        }
        if (getTheme() == null) {
            setTheme(a.getTheme());
        }
        if (getLogoRes() == -1) {
            setLogo(context.getApplicationInfo().icon);
        }
        if (getAppName() == "") {
            setAppName(a.getAppName());
        }
    }

    /* access modifiers changed from: protected */
    public void initSplashLogo(Activity activity) {
        if (getLogo() == null && getLogoRes() == -1 && getLogoByteArray() != null) {
            byte[] logoByteArray2 = getLogoByteArray();
            setLogo((Drawable) new BitmapDrawable(activity.getResources(), BitmapFactory.decodeByteArray(logoByteArray2, 0, logoByteArray2.length)));
        }
    }

    /* access modifiers changed from: protected */
    public C4831d initSplashHtml(Activity activity) {
        setSplashColorsByTheme(getTheme());
        C4831d dVar = new C4831d(activity);
        dVar.mo61609b(this);
        dVar.mo61606a(this);
        return dVar;
    }

    /* access modifiers changed from: 0000 */
    public boolean isUserDefinedSplash() {
        return getTheme() == Theme.USER_DEFINED || getCustomScreen() != -1;
    }
}
