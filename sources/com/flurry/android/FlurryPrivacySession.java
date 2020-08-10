package com.flurry.android;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.util.Base64;
import com.flurry.sdk.C1750eh.C1751a;
import com.flurry.sdk.C1752ei.C1753a;
import com.flurry.sdk.C1752ei.C1754b;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Locale;

public interface FlurryPrivacySession {

    public interface Callback {
        void failure();

        void success();
    }

    public static class Request implements C1751a {

        /* renamed from: a */
        final String f308a;

        /* renamed from: b */
        final String f309b;

        /* renamed from: c */
        final String f310c;
        public final Callback callback;
        public final Context context;
        public final String verifier = C1754b.m899a();

        public Request(Context context2, Callback callback2) {
            String str;
            this.context = context2;
            this.callback = callback2;
            this.f309b = context2.getPackageName();
            String str2 = this.verifier;
            MessageDigest a = C1753a.m898a("SHA-256");
            if (a != null) {
                a.update(str2.getBytes(Charset.defaultCharset()));
                str = Base64.encodeToString(a.digest(), 11);
            } else {
                str = "";
            }
            this.f308a = str;
            Locale locale = Locale.getDefault();
            String language = locale.getLanguage();
            if (TextUtils.isEmpty(language)) {
                language = f1011e;
            }
            String country = locale.getCountry();
            if (TextUtils.isEmpty(country)) {
                country = f1010d;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(language);
            sb.append("-");
            sb.append(country);
            this.f310c = sb.toString();
        }
    }

    /* renamed from: com.flurry.android.FlurryPrivacySession$a */
    public static class C1477a {

        /* renamed from: a */
        public final Uri f311a;

        public C1477a(String str, long j, Request request) {
            String str2 = "device_verifier";
            String str3 = "lang";
            this.f311a = new Builder().scheme("https").authority("flurry.mydashboard.oath.com").appendQueryParameter("device_session_id", str).appendQueryParameter("expires_in", String.valueOf(j)).appendQueryParameter(str2, request.f308a).appendQueryParameter(str3, request.f310c).build();
        }
    }
}
