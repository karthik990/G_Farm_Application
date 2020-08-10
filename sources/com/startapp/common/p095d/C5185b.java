package com.startapp.common.p095d;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.startapp.common.p042c.C5180b;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;

/* renamed from: com.startapp.common.d.b */
/* compiled from: StartAppSDK */
public class C5185b implements CookieStore {

    /* renamed from: a */
    private final CookieStore f3624a = new CookieManager().getCookieStore();

    /* renamed from: b */
    private final SharedPreferences f3625b;

    public C5185b(Context context) {
        String[] split;
        this.f3625b = context.getApplicationContext().getSharedPreferences("com.startapp.android.publish.CookiePrefsFile", 0);
        String string = this.f3625b.getString("names", null);
        if (string != null) {
            for (String str : TextUtils.split(string, ";")) {
                SharedPreferences sharedPreferences = this.f3625b;
                StringBuilder sb = new StringBuilder();
                sb.append("cookie_");
                sb.append(str);
                String string2 = sharedPreferences.getString(sb.toString(), null);
                if (string2 != null) {
                    HttpCookie httpCookie = (HttpCookie) C5180b.m3908a(string2, HttpCookie.class);
                    if (httpCookie != null) {
                        if (httpCookie.hasExpired()) {
                            m3925a(httpCookie);
                            m3928b();
                        } else {
                            this.f3624a.add(URI.create(httpCookie.getDomain()), httpCookie);
                        }
                    }
                }
            }
        }
    }

    public void add(URI uri, HttpCookie httpCookie) {
        String b = m3927b(httpCookie);
        this.f3624a.add(uri, httpCookie);
        m3926a(httpCookie, b);
        m3928b();
    }

    public List<HttpCookie> get(URI uri) {
        return this.f3624a.get(uri);
    }

    public List<HttpCookie> getCookies() {
        return this.f3624a.getCookies();
    }

    public List<URI> getURIs() {
        return this.f3624a.getURIs();
    }

    public boolean remove(URI uri, HttpCookie httpCookie) {
        if (!this.f3624a.remove(uri, httpCookie)) {
            return false;
        }
        m3925a(httpCookie);
        m3928b();
        return true;
    }

    public boolean removeAll() {
        if (!this.f3624a.removeAll()) {
            return false;
        }
        m3924a();
        m3928b();
        return true;
    }

    /* renamed from: a */
    private void m3926a(HttpCookie httpCookie, String str) {
        Editor edit = this.f3625b.edit();
        StringBuilder sb = new StringBuilder();
        sb.append("cookie_");
        sb.append(str);
        edit.putString(sb.toString(), C5180b.m3909a(httpCookie));
        edit.commit();
    }

    /* renamed from: a */
    private void m3924a() {
        Editor edit = this.f3625b.edit();
        edit.clear();
        edit.commit();
    }

    /* renamed from: a */
    private void m3925a(HttpCookie httpCookie) {
        Editor edit = this.f3625b.edit();
        StringBuilder sb = new StringBuilder();
        sb.append("cookie_");
        sb.append(m3927b(httpCookie));
        edit.remove(sb.toString());
        edit.commit();
    }

    /* renamed from: b */
    private void m3928b() {
        Editor edit = this.f3625b.edit();
        edit.putString("names", TextUtils.join(";", m3929c()));
        edit.commit();
    }

    /* renamed from: b */
    private String m3927b(HttpCookie httpCookie) {
        StringBuilder sb = new StringBuilder();
        sb.append(httpCookie.getDomain());
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(httpCookie.getName());
        return sb.toString();
    }

    /* renamed from: c */
    private Set<String> m3929c() {
        HashSet hashSet = new HashSet();
        for (HttpCookie b : this.f3624a.getCookies()) {
            hashSet.add(m3927b(b));
        }
        return hashSet;
    }
}
