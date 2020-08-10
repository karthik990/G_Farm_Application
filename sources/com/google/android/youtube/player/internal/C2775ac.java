package com.google.android.youtube.player.internal;

import android.app.Activity;
import android.content.Context;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.android.youtube.player.internal.C2827t.C2828a;
import com.google.android.youtube.player.internal.C2827t.C2829b;
import com.google.android.youtube.player.internal.C2834w.C2835a;

/* renamed from: com.google.android.youtube.player.internal.ac */
public final class C2775ac extends C2773aa {
    /* renamed from: a */
    public final C2772a mo38055a(C2776b bVar, YouTubeThumbnailView youTubeThumbnailView) {
        return new C2810p(bVar, youTubeThumbnailView);
    }

    /* renamed from: a */
    public final C2776b mo38056a(Context context, String str, C2828a aVar, C2829b bVar) {
        C2809o oVar = new C2809o(context, str, context.getPackageName(), C2838z.m1757d(context), aVar, bVar);
        return oVar;
    }

    /* renamed from: a */
    public final C2780d mo38057a(Activity activity, C2776b bVar, boolean z) throws C2835a {
        return C2834w.m1743a(activity, bVar.mo38058a(), z);
    }
}
