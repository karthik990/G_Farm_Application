package com.pierfrancescosoffritti.androidyoutubeplayer.p051ui.menu.defaultMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.pierfrancescosoffritti.androidyoutubeplayer.C4571R;
import com.pierfrancescosoffritti.androidyoutubeplayer.p051ui.menu.MenuItem;
import java.util.List;

/* renamed from: com.pierfrancescosoffritti.androidyoutubeplayer.ui.menu.defaultMenu.a */
class C4601a extends Adapter<C4602a> {

    /* renamed from: a */
    private final Context f2348a;

    /* renamed from: b */
    private final List<MenuItem> f2349b;

    /* renamed from: com.pierfrancescosoffritti.androidyoutubeplayer.ui.menu.defaultMenu.a$a */
    class C4602a extends ViewHolder {

        /* renamed from: a */
        final View f2350a;

        /* renamed from: b */
        final TextView f2351b;

        C4602a(View view) {
            super(view);
            this.f2350a = view;
            this.f2351b = (TextView) view.findViewById(C4571R.C4574id.text);
        }
    }

    C4601a(Context context, List<MenuItem> list) {
        this.f2348a = context;
        this.f2349b = list;
    }

    /* renamed from: a */
    public C4602a onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new C4602a(LayoutInflater.from(viewGroup.getContext()).inflate(C4571R.layout.menu_item, viewGroup, false));
    }

    /* renamed from: a */
    public void onBindViewHolder(C4602a aVar, int i) {
        aVar.f2350a.setOnClickListener(((MenuItem) this.f2349b.get(i)).getOnClickListener());
        aVar.f2351b.setText(((MenuItem) this.f2349b.get(i)).getText());
        aVar.f2351b.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this.f2348a, ((MenuItem) this.f2349b.get(i)).getIcon()), null, null, null);
    }

    public int getItemCount() {
        return this.f2349b.size();
    }
}
