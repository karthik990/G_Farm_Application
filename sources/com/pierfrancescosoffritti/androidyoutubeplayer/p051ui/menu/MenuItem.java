package com.pierfrancescosoffritti.androidyoutubeplayer.p051ui.menu;

import android.view.View.OnClickListener;

/* renamed from: com.pierfrancescosoffritti.androidyoutubeplayer.ui.menu.MenuItem */
public class MenuItem {

    /* renamed from: a */
    private final String f2342a;

    /* renamed from: b */
    private final int f2343b;

    /* renamed from: c */
    private final OnClickListener f2344c;

    public MenuItem(String str, int i, OnClickListener onClickListener) {
        this.f2342a = str;
        this.f2343b = i;
        this.f2344c = onClickListener;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MenuItem menuItem = (MenuItem) obj;
        if (this.f2343b != menuItem.f2343b || !this.f2342a.equals(menuItem.f2342a)) {
            z = false;
        }
        return z;
    }

    public int getIcon() {
        return this.f2343b;
    }

    public OnClickListener getOnClickListener() {
        return this.f2344c;
    }

    public String getText() {
        return this.f2342a;
    }

    public int hashCode() {
        return (this.f2342a.hashCode() * 31) + this.f2343b;
    }
}
