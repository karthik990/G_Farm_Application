package com.startapp.android.publish.adsCommon.adinformation;

import android.content.Context;
import com.startapp.android.publish.adsCommon.C5051k;
import com.startapp.android.publish.adsCommon.adinformation.AdInformationPositions.Position;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.p042c.C2362f;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: StartAppSDK */
public class AdInformationConfig implements Serializable {
    private static final long serialVersionUID = 1;
    @C2362f(mo20786b = ArrayList.class, mo20787c = C4981e.class)
    private List<C4981e> ImageResources = new ArrayList();
    @C2362f(mo20786b = HashMap.class, mo20787c = Position.class, mo20788d = Placement.class)
    protected HashMap<Placement, Position> Positions = new HashMap<>();

    /* renamed from: a */
    private transient EnumMap<ImageResourceType, C4981e> f3101a = new EnumMap<>(ImageResourceType.class);
    private String dialogUrlSecured = "https://d1byvlfiet2h9q.cloudfront.net/InApp/resources/adInformationDialog3.html";
    private boolean enabled = true;
    private String eulaUrlSecured = "https://www.com.startapp.com/policy/sdk-policy/";
    private float fatFingersFactor = 200.0f;

    /* compiled from: StartAppSDK */
    public enum ImageResourceType {
        INFO_S(17, 14),
        INFO_EX_S(88, 14),
        INFO_L(25, 21),
        INFO_EX_L(130, 21);
        
        private int height;
        private int width;

        private ImageResourceType(int i, int i2) {
            this.width = i;
            this.height = i2;
        }

        public int getDefaultWidth() {
            return this.width;
        }

        public int getDefaultHeight() {
            return this.height;
        }

        public static ImageResourceType getByName(String str) {
            ImageResourceType imageResourceType = INFO_S;
            ImageResourceType[] values = values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].name().toLowerCase().compareTo(str.toLowerCase()) == 0) {
                    imageResourceType = values[i];
                }
            }
            return imageResourceType;
        }
    }

    private AdInformationConfig() {
    }

    /* renamed from: a */
    public static AdInformationConfig m2966a() {
        AdInformationConfig adInformationConfig = new AdInformationConfig();
        m2967a(adInformationConfig);
        return adInformationConfig;
    }

    /* renamed from: a */
    public static void m2967a(AdInformationConfig adInformationConfig) {
        adInformationConfig.mo62097i();
        adInformationConfig.mo62096h();
    }

    /* renamed from: b */
    public String mo62090b() {
        String str = this.eulaUrlSecured;
        return (str == null || str.equals("")) ? "https://www.com.startapp.com/policy/sdk-policy/" : this.eulaUrlSecured;
    }

    /* renamed from: c */
    public String mo62091c() {
        return (!this.f3101a.containsKey(ImageResourceType.INFO_L) || ((C4981e) this.f3101a.get(ImageResourceType.INFO_L)).mo62143d().equals("")) ? "https://info.startappservice.com/InApp/resources/info_l.png" : ((C4981e) this.f3101a.get(ImageResourceType.INFO_L)).mo62143d();
    }

    /* renamed from: d */
    public boolean mo62092d() {
        return this.enabled;
    }

    /* renamed from: a */
    public boolean mo62089a(Context context) {
        return !C5051k.m3335a(context, "userDisabledAdInformation", Boolean.valueOf(false)).booleanValue() && mo62092d();
    }

    /* renamed from: a */
    public void mo62087a(Context context, boolean z) {
        C5051k.m3342b(context, "userDisabledAdInformation", Boolean.valueOf(!z));
    }

    /* renamed from: e */
    public float mo62093e() {
        return this.fatFingersFactor / 100.0f;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo62088a(ImageResourceType imageResourceType, C4981e eVar) {
        mo62098j().put(imageResourceType, eVar);
    }

    /* renamed from: f */
    public String mo62094f() {
        String str = this.dialogUrlSecured;
        return str != null ? str : "https://d1byvlfiet2h9q.cloudfront.net/InApp/resources/adInformationDialog3.html";
    }

    /* renamed from: a */
    public Position mo62085a(Placement placement) {
        Position position = (Position) this.Positions.get(placement);
        if (position != null) {
            return position;
        }
        Position position2 = Position.BOTTOM_LEFT;
        this.Positions.put(placement, position2);
        return position2;
    }

    /* renamed from: a */
    public C4981e mo62086a(ImageResourceType imageResourceType) {
        return (C4981e) mo62098j().get(imageResourceType);
    }

    /* renamed from: g */
    public void mo62095g() {
        for (C4981e eVar : this.ImageResources) {
            mo62088a(ImageResourceType.getByName(eVar.mo62134a()), eVar);
            eVar.mo62144e();
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: h */
    public void mo62096h() {
        ImageResourceType[] values = ImageResourceType.values();
        int length = values.length;
        int i = 0;
        while (i < length) {
            ImageResourceType imageResourceType = values[i];
            if (mo62098j().get(imageResourceType) != null) {
                i++;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("AdInformation error in ImageResource [");
                sb.append(imageResourceType);
                sb.append("] cannot be found in MetaData");
                throw new IllegalArgumentException(sb.toString());
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: i */
    public void mo62097i() {
        ImageResourceType[] values;
        for (ImageResourceType imageResourceType : ImageResourceType.values()) {
            C4981e eVar = (C4981e) mo62098j().get(imageResourceType);
            Boolean valueOf = Boolean.valueOf(true);
            if (eVar == null) {
                eVar = C4981e.m3014c(imageResourceType.name());
                Iterator it = this.ImageResources.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (ImageResourceType.getByName(((C4981e) it.next()).mo62134a()).equals(imageResourceType)) {
                            valueOf = Boolean.valueOf(false);
                            break;
                        }
                    } else {
                        break;
                    }
                }
                mo62098j().put(imageResourceType, eVar);
                if (valueOf.booleanValue()) {
                    this.ImageResources.add(eVar);
                }
            }
            eVar.mo62135a(imageResourceType.getDefaultWidth());
            eVar.mo62140b(imageResourceType.getDefaultHeight());
            StringBuilder sb = new StringBuilder();
            sb.append(imageResourceType.name().toLowerCase());
            sb.append(".png");
            eVar.mo62137a(sb.toString());
        }
    }

    /* renamed from: j */
    public EnumMap<ImageResourceType, C4981e> mo62098j() {
        return this.f3101a;
    }

    /* renamed from: k */
    public void mo62099k() {
        this.f3101a = new EnumMap<>(ImageResourceType.class);
    }
}
