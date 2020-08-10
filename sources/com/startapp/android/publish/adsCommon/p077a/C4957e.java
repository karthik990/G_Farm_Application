package com.startapp.android.publish.adsCommon.p077a;

import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.Constants;
import com.startapp.common.p042c.C2362f;
import com.startapp.common.p092a.C5155g;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

/* renamed from: com.startapp.android.publish.adsCommon.a.e */
/* compiled from: StartAppSDK */
public class C4957e implements Serializable {
    private static final long serialVersionUID = 1;

    /* renamed from: a */
    private transient Set<Class<? extends C4955c>> f3078a = new HashSet();
    private boolean applyOnBannerRefresh = true;
    @C2362f(mo20786b = HashMap.class, mo20787c = ArrayList.class, mo20788d = Placement.class, mo20789e = C4955c.class)
    private Map<Placement, List<C4955c>> placements = new HashMap();
    @C2362f(mo20786b = ArrayList.class, mo20787c = C4955c.class)
    private List<C4955c> session = new ArrayList();
    @C2362f(mo20786b = HashMap.class, mo20787c = ArrayList.class, mo20789e = C4955c.class)
    private Map<String, List<C4955c>> tags = new HashMap();

    /* renamed from: a */
    public boolean mo62062a() {
        return this.applyOnBannerRefresh;
    }

    /* renamed from: a */
    public synchronized C4958f mo62061a(Placement placement, String str) {
        C4958f a;
        String str2;
        this.f3078a.clear();
        a = m2955a((List) this.tags.get(str), C4954b.m2946a().mo62054a(str), C4956d.TAG, str);
        if (a.mo62064a()) {
            a = m2955a((List) this.placements.get(placement), C4954b.m2946a().mo62053a(placement), C4956d.PLACEMENT, placement.toString());
            if (a.mo62064a()) {
                a = m2955a(this.session, C4954b.m2946a().mo62057c(), C4956d.SESSION, SettingsJsonConstants.SESSION_KEY);
            }
        }
        String str3 = "AdRules";
        StringBuilder sb = new StringBuilder();
        sb.append("shouldDisplayAd result: ");
        sb.append(a.mo62064a());
        if (a.mo62064a()) {
            str2 = "";
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(" because of rule ");
            sb2.append(a.mo62065b());
            str2 = sb2.toString();
        }
        sb.append(str2);
        C5155g.m3807a(str3, 3, sb.toString());
        return a;
    }

    /* renamed from: a */
    private C4958f m2955a(List<C4955c> list, List<C4953a> list2, C4956d dVar, String str) {
        String str2;
        if (list == null) {
            return new C4958f(true);
        }
        for (C4955c cVar : list) {
            if (cVar.mo62059a() || !this.f3078a.contains(cVar.getClass())) {
                if (!cVar.mo62060a(list2)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(cVar.getClass().getSimpleName());
                    sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                    sb.append(dVar);
                    if (Constants.m3707a().booleanValue()) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(" ");
                        sb2.append(str);
                        sb2.append(":");
                        sb2.append(cVar);
                        str2 = sb2.toString();
                    } else {
                        str2 = "";
                    }
                    sb.append(str2);
                    return new C4958f(false, sb.toString());
                }
                this.f3078a.add(cVar.getClass());
            }
        }
        return new C4958f(true);
    }

    /* renamed from: b */
    public void mo62063b() {
        this.f3078a = new HashSet();
    }
}
