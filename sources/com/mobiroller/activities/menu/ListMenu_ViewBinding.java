package com.mobiroller.activities.menu;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class ListMenu_ViewBinding implements Unbinder {
    private ListMenu target;

    public ListMenu_ViewBinding(ListMenu listMenu) {
        this(listMenu, listMenu.getWindow().getDecorView());
    }

    public ListMenu_ViewBinding(ListMenu listMenu, View view) {
        this.target = listMenu;
        listMenu.imgView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.list_menu_img, "field 'imgView'", ImageView.class);
        listMenu.flAdplaceholder = (FrameLayout) C0812Utils.findRequiredViewAsType(view, R.id.fl_adplaceholder, "field 'flAdplaceholder'", FrameLayout.class);
        listMenu.list = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.menu_list, "field 'list'", RecyclerView.class);
        listMenu.menuListLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.menu_list_layout, "field 'menuListLayout'", LinearLayout.class);
        listMenu.listMenuLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.list_menu_layout, "field 'listMenuLayout'", RelativeLayout.class);
        listMenu.listMenuOverlay = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.list_menu_overlay, "field 'listMenuOverlay'", RelativeLayout.class);
        listMenu.toolbar = (Toolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar_top, "field 'toolbar'", Toolbar.class);
    }

    public void unbind() {
        ListMenu listMenu = this.target;
        if (listMenu != null) {
            this.target = null;
            listMenu.imgView = null;
            listMenu.flAdplaceholder = null;
            listMenu.list = null;
            listMenu.menuListLayout = null;
            listMenu.listMenuLayout = null;
            listMenu.listMenuOverlay = null;
            listMenu.toolbar = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
