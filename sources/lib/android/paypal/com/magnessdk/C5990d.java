package lib.android.paypal.com.magnessdk;

import android.content.Context;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import lib.android.paypal.com.magnessdk.p046b.C5988a;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: lib.android.paypal.com.magnessdk.d */
abstract class C5990d {

    /* renamed from: A */
    static final int f4244A = 27;

    /* renamed from: B */
    static final int f4245B = 28;

    /* renamed from: C */
    static final int f4246C = 29;

    /* renamed from: D */
    static final int f4247D = 30;

    /* renamed from: E */
    static final int f4248E = 31;

    /* renamed from: F */
    static final int f4249F = 32;

    /* renamed from: G */
    static final int f4250G = 33;

    /* renamed from: H */
    static final int f4251H = 34;

    /* renamed from: I */
    static final int f4252I = 35;

    /* renamed from: J */
    static final int f4253J = 36;

    /* renamed from: K */
    static final int f4254K = 37;

    /* renamed from: L */
    static final int f4255L = 38;

    /* renamed from: M */
    static final int f4256M = 39;

    /* renamed from: N */
    static final int f4257N = 40;

    /* renamed from: O */
    static final int f4258O = 41;

    /* renamed from: P */
    static final int f4259P = 42;

    /* renamed from: Q */
    static final int f4260Q = 43;

    /* renamed from: R */
    static final int f4261R = 44;

    /* renamed from: S */
    static final int f4262S = 45;

    /* renamed from: T */
    static final int f4263T = 46;

    /* renamed from: U */
    static final int f4264U = 47;

    /* renamed from: V */
    static final int f4265V = 48;

    /* renamed from: W */
    static final int f4266W = 49;

    /* renamed from: X */
    static final int f4267X = 50;

    /* renamed from: Y */
    static final int f4268Y = 51;

    /* renamed from: Z */
    static final int f4269Z = 52;

    /* renamed from: a */
    static final int f4270a = 0;

    /* renamed from: aA */
    static final int f4271aA = 79;

    /* renamed from: aB */
    static final int f4272aB = 80;

    /* renamed from: aC */
    static final int f4273aC = 81;

    /* renamed from: aD */
    static final int f4274aD = 82;

    /* renamed from: aE */
    static final int f4275aE = 83;

    /* renamed from: aF */
    static final int f4276aF = 84;

    /* renamed from: aG */
    static final int f4277aG = 85;

    /* renamed from: aH */
    static final int f4278aH = 86;

    /* renamed from: aI */
    static final int f4279aI = 87;

    /* renamed from: aa */
    static final int f4280aa = 53;

    /* renamed from: ab */
    static final int f4281ab = 54;

    /* renamed from: ac */
    static final int f4282ac = 55;

    /* renamed from: ad */
    static final int f4283ad = 56;

    /* renamed from: ae */
    static final int f4284ae = 57;

    /* renamed from: af */
    static final int f4285af = 58;

    /* renamed from: ag */
    static final int f4286ag = 59;

    /* renamed from: ah */
    static final int f4287ah = 60;

    /* renamed from: ai */
    static final int f4288ai = 61;

    /* renamed from: aj */
    static final int f4289aj = 62;

    /* renamed from: ak */
    static final int f4290ak = 63;

    /* renamed from: al */
    static final int f4291al = 64;

    /* renamed from: am */
    static final int f4292am = 65;

    /* renamed from: an */
    static final int f4293an = 66;

    /* renamed from: ao */
    static final int f4294ao = 67;

    /* renamed from: ap */
    static final int f4295ap = 68;

    /* renamed from: aq */
    static final int f4296aq = 69;

    /* renamed from: ar */
    static final int f4297ar = 70;

    /* renamed from: as */
    static final int f4298as = 71;

    /* renamed from: at */
    static final int f4299at = 72;

    /* renamed from: au */
    static final int f4300au = 73;

    /* renamed from: av */
    static final int f4301av = 74;

    /* renamed from: aw */
    static final int f4302aw = 75;

    /* renamed from: ax */
    static final int f4303ax = 76;

    /* renamed from: ay */
    static final int f4304ay = 77;

    /* renamed from: az */
    static final int f4305az = 78;

    /* renamed from: b */
    static final int f4306b = 1;

    /* renamed from: c */
    static final int f4307c = 2;

    /* renamed from: d */
    static final int f4308d = 3;

    /* renamed from: e */
    static final int f4309e = 4;

    /* renamed from: f */
    static final int f4310f = 5;

    /* renamed from: g */
    static final int f4311g = 6;

    /* renamed from: h */
    static final int f4312h = 7;

    /* renamed from: i */
    static final int f4313i = 8;

    /* renamed from: j */
    static final int f4314j = 9;

    /* renamed from: k */
    static final int f4315k = 10;

    /* renamed from: l */
    static final int f4316l = 11;

    /* renamed from: m */
    static final int f4317m = 12;

    /* renamed from: n */
    static final int f4318n = 13;

    /* renamed from: o */
    static final int f4319o = 14;

    /* renamed from: p */
    static final int f4320p = 15;

    /* renamed from: q */
    static final int f4321q = 16;

    /* renamed from: r */
    static final int f4322r = 17;

    /* renamed from: s */
    static final int f4323s = 18;

    /* renamed from: t */
    static final int f4324t = 19;

    /* renamed from: u */
    static final int f4325u = 20;

    /* renamed from: v */
    static final int f4326v = 21;

    /* renamed from: w */
    static final int f4327w = 22;

    /* renamed from: x */
    static final int f4328x = 23;

    /* renamed from: y */
    static final int f4329y = 25;

    /* renamed from: z */
    static final int f4330z = 26;

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: lib.android.paypal.com.magnessdk.d$a */
    @interface C2391a {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: lib.android.paypal.com.magnessdk.d$b */
    @interface C2392b {
    }

    C5990d() {
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public abstract JSONObject mo72538a();

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public abstract JSONObject mo72539a(Context context);

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public JSONObject mo72540a(JSONObject jSONObject) {
        JSONObject a = mo72538a();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            try {
                String str = (String) keys.next();
                a.put(str, jSONObject.get(str));
            } catch (JSONException e) {
                C5988a.m4032a(getClass(), 3, (Throwable) e);
            }
        }
        return a;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public abstract void mo72541a(int i, Context context);

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public boolean mo72542a(Context context, String str) {
        boolean z = false;
        try {
            if (context.checkCallingOrSelfPermission(str) == 0) {
                z = true;
            }
            return z;
        } catch (Exception e) {
            C5988a.m4032a(getClass(), 3, (Throwable) e);
            return false;
        }
    }
}
