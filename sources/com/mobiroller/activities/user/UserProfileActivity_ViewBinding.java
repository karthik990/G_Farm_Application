package com.mobiroller.activities.user;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.CircleImageView;
import com.mobiroller.views.HeaderView;
import com.mobiroller.views.custom.MobirollerToolbar;
import jahirfiquitiva.libs.fabsmenu.FABsMenu;
import jahirfiquitiva.libs.fabsmenu.TitleFAB;

public class UserProfileActivity_ViewBinding implements Unbinder {
    private UserProfileActivity target;
    private View view7f0a0246;
    private View view7f0a024d;

    public UserProfileActivity_ViewBinding(UserProfileActivity userProfileActivity) {
        this(userProfileActivity, userProfileActivity.getWindow().getDecorView());
    }

    public UserProfileActivity_ViewBinding(final UserProfileActivity userProfileActivity, View view) {
        this.target = userProfileActivity;
        userProfileActivity.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.top_layout, "field 'mainLayout'", RelativeLayout.class);
        userProfileActivity.overlayLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.overlay_layout, "field 'overlayLayout'", RelativeLayout.class);
        userProfileActivity.toolbarHeaderView = (HeaderView) C0812Utils.findRequiredViewAsType(view, R.id.toolbar_header_view, "field 'toolbarHeaderView'", HeaderView.class);
        userProfileActivity.floatHeaderView = (HeaderView) C0812Utils.findRequiredViewAsType(view, R.id.float_header_view, "field 'floatHeaderView'", HeaderView.class);
        userProfileActivity.appBarLayout = (AppBarLayout) C0812Utils.findRequiredViewAsType(view, R.id.appbar, "field 'appBarLayout'", AppBarLayout.class);
        userProfileActivity.collapsingToolbarLayout = (CollapsingToolbarLayout) C0812Utils.findRequiredViewAsType(view, R.id.collapsing_toolbar, "field 'collapsingToolbarLayout'", CollapsingToolbarLayout.class);
        userProfileActivity.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar, "field 'toolbar'", MobirollerToolbar.class);
        userProfileActivity.userImage = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.image, "field 'userImage'", ImageView.class);
        userProfileActivity.personalTitle = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.personal_data_title, "field 'personalTitle'", TextView.class);
        userProfileActivity.emptyView = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view, "field 'emptyView'", CardView.class);
        userProfileActivity.fab = (FABsMenu) C0812Utils.findRequiredViewAsType(view, R.id.fab, "field 'fab'", FABsMenu.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.fab_block, "field 'fabBlock' and method 'blockUser'");
        userProfileActivity.fabBlock = (TitleFAB) C0812Utils.castView(findRequiredView, R.id.fab_block, "field 'fabBlock'", TitleFAB.class);
        this.view7f0a0246 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                userProfileActivity.blockUser();
            }
        });
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.fab_role, "field 'fabRole' and method 'changeUserRole'");
        userProfileActivity.fabRole = (TitleFAB) C0812Utils.castView(findRequiredView2, R.id.fab_role, "field 'fabRole'", TitleFAB.class);
        this.view7f0a024d = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                userProfileActivity.changeUserRole();
            }
        });
        userProfileActivity.badgeLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.badge_layout, "field 'badgeLayout'", RelativeLayout.class);
        userProfileActivity.roleBadge = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.role_badge, "field 'roleBadge'", ImageView.class);
        userProfileActivity.roleTitle = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.role_title, "field 'roleTitle'", TextView.class);
        userProfileActivity.personalDataRecyclerView = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.user_profile_personal_data_list, "field 'personalDataRecyclerView'", RecyclerView.class);
        userProfileActivity.userLogo = (CircleImageView) C0812Utils.findRequiredViewAsType(view, R.id.conversation_contact_photo, "field 'userLogo'", CircleImageView.class);
    }

    public void unbind() {
        UserProfileActivity userProfileActivity = this.target;
        if (userProfileActivity != null) {
            this.target = null;
            userProfileActivity.mainLayout = null;
            userProfileActivity.overlayLayout = null;
            userProfileActivity.toolbarHeaderView = null;
            userProfileActivity.floatHeaderView = null;
            userProfileActivity.appBarLayout = null;
            userProfileActivity.collapsingToolbarLayout = null;
            userProfileActivity.toolbar = null;
            userProfileActivity.userImage = null;
            userProfileActivity.personalTitle = null;
            userProfileActivity.emptyView = null;
            userProfileActivity.fab = null;
            userProfileActivity.fabBlock = null;
            userProfileActivity.fabRole = null;
            userProfileActivity.badgeLayout = null;
            userProfileActivity.roleBadge = null;
            userProfileActivity.roleTitle = null;
            userProfileActivity.personalDataRecyclerView = null;
            userProfileActivity.userLogo = null;
            this.view7f0a0246.setOnClickListener(null);
            this.view7f0a0246 = null;
            this.view7f0a024d.setOnClickListener(null);
            this.view7f0a024d = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
