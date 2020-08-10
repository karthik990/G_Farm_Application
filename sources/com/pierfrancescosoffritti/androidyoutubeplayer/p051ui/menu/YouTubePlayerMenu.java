package com.pierfrancescosoffritti.androidyoutubeplayer.p051ui.menu;

import android.view.View;

/* renamed from: com.pierfrancescosoffritti.androidyoutubeplayer.ui.menu.YouTubePlayerMenu */
public interface YouTubePlayerMenu {
    void addItem(MenuItem menuItem);

    void dismiss();

    int getItemCount();

    void removeItem(int i);

    void show(View view);
}
