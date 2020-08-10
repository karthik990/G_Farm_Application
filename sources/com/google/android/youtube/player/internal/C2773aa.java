package com.google.android.youtube.player.internal;

import android.app.Activity;
import android.content.Context;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.android.youtube.player.internal.C2827t.C2828a;
import com.google.android.youtube.player.internal.C2827t.C2829b;
import com.google.android.youtube.player.internal.C2834w.C2835a;

/* renamed from: com.google.android.youtube.player.internal.aa */
public abstract class C2773aa {

    /* renamed from: a */
    private static final C2773aa f1662a = m1491b();

    /* renamed from: a */
    public static C2773aa m1490a() {
        return f1662a;
    }

    /* renamed from: b */
    private static C2773aa m1491b() {
        try {
            return (C2773aa) Class.forName("com.google.android.youtube.api.locallylinked.LocallyLinkedFactory").asSubclass(C2773aa.class).newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e2) {
            throw new IllegalStateException(e2);
        } catch (ClassNotFoundException unused) {
            return new C2775ac();
        }
    }

    /* renamed from: a */
    public abstract C2772a mo38055a(C2776b bVar, YouTubeThumbnailView youTubeThumbnailView);

    /* renamed from: a */
    public abstract C2776b mo38056a(Context context, String str, C2828a aVar, C2829b bVar);

    /* renamed from: a */
    public abstract C2780d mo38057a(Activity activity, C2776b bVar, boolean z) throws C2835a;
}
