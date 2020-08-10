package com.pierfrancescosoffritti.androidyoutubeplayer.p051ui.menu.defaultMenu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.C4571R;
import com.pierfrancescosoffritti.androidyoutubeplayer.p051ui.menu.MenuItem;
import com.pierfrancescosoffritti.androidyoutubeplayer.p051ui.menu.YouTubePlayerMenu;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.pierfrancescosoffritti.androidyoutubeplayer.ui.menu.defaultMenu.DefaultYouTubePlayerMenu */
public class DefaultYouTubePlayerMenu implements YouTubePlayerMenu {

    /* renamed from: a */
    private final Context f2345a;

    /* renamed from: b */
    private final List<MenuItem> f2346b = new ArrayList();

    /* renamed from: c */
    private PopupWindow f2347c;

    public DefaultYouTubePlayerMenu(Context context) {
        this.f2345a = context;
    }

    /* renamed from: a */
    private PopupWindow m2069a() {
        LayoutInflater layoutInflater = (LayoutInflater) this.f2345a.getSystemService("layout_inflater");
        if (layoutInflater != null) {
            View inflate = layoutInflater.inflate(C4571R.layout.player_menu, null);
            m2070a((RecyclerView) inflate.findViewById(C4571R.C4574id.recycler_view));
            PopupWindow popupWindow = new PopupWindow(inflate, -2, -2);
            popupWindow.setFocusable(true);
            popupWindow.setWidth(-2);
            popupWindow.setHeight(-2);
            popupWindow.setContentView(inflate);
            return popupWindow;
        }
        throw new RuntimeException("can't access LAYOUT_INFLATER_SERVICE");
    }

    /* renamed from: a */
    private void m2070a(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.f2345a));
        recyclerView.setAdapter(new C4601a(this.f2345a, this.f2346b));
    }

    public void addItem(MenuItem menuItem) {
        this.f2346b.add(menuItem);
    }

    public void dismiss() {
        PopupWindow popupWindow = this.f2347c;
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public int getItemCount() {
        return this.f2346b.size();
    }

    public void removeItem(int i) {
        this.f2346b.remove(i);
    }

    public void show(View view) {
        this.f2347c = m2069a();
        this.f2347c.showAsDropDown(view, 0, (-this.f2345a.getResources().getDimensionPixelSize(C4571R.dimen._8dp)) * 4);
        if (this.f2346b.size() == 0) {
            Log.e(YouTubePlayerMenu.class.getName(), "The menu is empty");
        }
    }
}
