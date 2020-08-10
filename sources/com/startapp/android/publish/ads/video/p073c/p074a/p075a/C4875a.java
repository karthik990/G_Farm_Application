package com.startapp.android.publish.ads.video.p073c.p074a.p075a;

import com.startapp.common.p092a.C5155g;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.startapp.android.publish.ads.video.c.a.a.a */
/* compiled from: StartAppSDK */
public class C4875a {

    /* renamed from: a */
    private String f2829a;

    /* renamed from: b */
    private Integer f2830b;

    /* renamed from: c */
    private Integer f2831c;

    /* renamed from: d */
    private Integer f2832d;

    /* renamed from: e */
    private Integer f2833e;

    /* renamed from: f */
    private Integer f2834f;

    /* renamed from: g */
    private Integer f2835g;

    /* renamed from: h */
    private String f2836h;

    /* renamed from: i */
    private Integer f2837i;

    /* renamed from: j */
    private List<C4878d> f2838j;

    /* renamed from: k */
    private String f2839k;

    /* renamed from: l */
    private List<String> f2840l;

    /* renamed from: m */
    private List<String> f2841m;

    /* renamed from: a */
    private boolean m2639a(int i) {
        return i > 0;
    }

    /* renamed from: a */
    public void mo61701a(String str) {
        this.f2829a = str;
    }

    /* renamed from: a */
    public Integer mo61699a() {
        return this.f2830b;
    }

    /* renamed from: a */
    public void mo61700a(Integer num) {
        this.f2830b = num;
    }

    /* renamed from: b */
    public Integer mo61702b() {
        return this.f2831c;
    }

    /* renamed from: b */
    public void mo61703b(Integer num) {
        this.f2831c = num;
    }

    /* renamed from: c */
    public Integer mo61705c() {
        return this.f2832d;
    }

    /* renamed from: c */
    public void mo61706c(Integer num) {
        this.f2832d = num;
    }

    /* renamed from: d */
    public Integer mo61708d() {
        return this.f2833e;
    }

    /* renamed from: d */
    public void mo61709d(Integer num) {
        this.f2833e = num;
    }

    /* renamed from: e */
    public void mo61711e(Integer num) {
        this.f2834f = num;
    }

    /* renamed from: f */
    public void mo61713f(Integer num) {
        this.f2835g = num;
    }

    /* renamed from: b */
    public void mo61704b(String str) {
        this.f2836h = str;
    }

    /* renamed from: g */
    public void mo61715g(Integer num) {
        this.f2837i = num;
    }

    /* renamed from: e */
    public List<C4878d> mo61710e() {
        if (this.f2838j == null) {
            this.f2838j = new ArrayList();
        }
        return this.f2838j;
    }

    /* renamed from: c */
    public void mo61707c(String str) {
        this.f2839k = str;
    }

    /* renamed from: f */
    public List<String> mo61712f() {
        if (this.f2840l == null) {
            this.f2840l = new ArrayList();
        }
        return this.f2840l;
    }

    /* renamed from: g */
    public List<String> mo61714g() {
        if (this.f2841m == null) {
            this.f2841m = new ArrayList();
        }
        return this.f2841m;
    }

    /* renamed from: h */
    public boolean mo61716h() {
        Integer b = mo61702b();
        Integer a = mo61699a();
        String str = "VASTIcon";
        if (b == null || a == null || !m2639a(b.intValue()) || !m2639a(a.intValue())) {
            C5155g.m3807a(str, 3, "Validator error: VASTIcon invalid size");
            return false;
        }
        Integer c = mo61705c();
        Integer d = mo61708d();
        if (c == null || d == null || !m2639a(c.intValue()) || !m2639a(d.intValue())) {
            C5155g.m3807a(str, 3, "Validator error: VASTIcon invalid position");
            return false;
        } else if (mo61710e().size() != 0) {
            return true;
        } else {
            C5155g.m3807a(str, 3, "Validator error: VASTIcon no resources");
            return false;
        }
    }
}
