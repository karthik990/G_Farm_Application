package com.startapp.p054a.p055a.p059d;

import java.util.regex.Pattern;
import org.slf4j.Marker;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;

/* renamed from: com.startapp.a.a.d.d */
/* compiled from: StartAppSDK */
public class C4710d implements C4709c {

    /* renamed from: a */
    private final Pattern f2411a = Pattern.compile("\\+");

    /* renamed from: b */
    private final Pattern f2412b = Pattern.compile("/");

    /* renamed from: c */
    private final Pattern f2413c = Pattern.compile("=");

    /* renamed from: d */
    private final Pattern f2414d = Pattern.compile(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);

    /* renamed from: e */
    private final Pattern f2415e = Pattern.compile("\\*");

    /* renamed from: f */
    private final Pattern f2416f = Pattern.compile("#");

    /* renamed from: a */
    public String mo61117a(String str) {
        return this.f2413c.matcher(this.f2412b.matcher(this.f2411a.matcher(str).replaceAll(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR)).replaceAll(Marker.ANY_MARKER)).replaceAll("#");
    }
}
