package com.startapp.android.publish.ads.list3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.p080d.C5002b;
import com.startapp.android.publish.common.metaData.MetaData;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* renamed from: com.startapp.android.publish.ads.list3d.b */
/* compiled from: StartAppSDK */
public class C4802b extends ArrayAdapter<ListItem> {

    /* renamed from: a */
    private String f2617a;

    /* renamed from: b */
    private String f2618b;

    public C4802b(Context context, List<ListItem> list, String str, String str2, String str3) {
        super(context, 0, list);
        this.f2617a = str2;
        this.f2618b = str3;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        C4808d dVar;
        if (view == null) {
            dVar = new C4808d(getContext());
            view2 = dVar.mo61474a();
        } else {
            view2 = view;
            dVar = (C4808d) view.getTag();
        }
        ListItem listItem = (ListItem) getItem(i);
        dVar.mo61475a(C4983b.m3032a().mo62161a(listItem.mo61418n()));
        dVar.mo61478c().setText(listItem.mo61411g());
        dVar.mo61479d().setText(listItem.mo61412h());
        Bitmap a = C4811f.m2473a().mo61493a(this.f2618b).mo61482a(i, listItem.mo61404a(), listItem.mo61413i());
        if (a == null) {
            dVar.mo61477b().setImageResource(17301651);
            dVar.mo61477b().setTag("tag_error");
        } else {
            dVar.mo61477b().setImageBitmap(a);
            dVar.mo61477b().setTag("tag_ok");
        }
        dVar.mo61480e().setRating(listItem.mo61415k());
        dVar.mo61476a(listItem.mo61421q());
        C4811f.m2473a().mo61493a(this.f2618b).mo61484a(getContext(), listItem.mo61404a(), listItem.mo61406c(), new C5002b(this.f2617a), m2426a(listItem));
        return view2;
    }

    /* renamed from: a */
    private long m2426a(ListItem listItem) {
        if (listItem.mo61422r() != null) {
            return TimeUnit.SECONDS.toMillis(listItem.mo61422r().longValue());
        }
        return TimeUnit.SECONDS.toMillis(MetaData.getInstance().getIABDisplayImpressionDelayInSeconds());
    }
}
