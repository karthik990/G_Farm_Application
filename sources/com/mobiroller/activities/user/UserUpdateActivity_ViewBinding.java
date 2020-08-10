package com.mobiroller.activities.user;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.CircleImageView;
import com.mobiroller.views.custom.MobirollerToolbar;

public class UserUpdateActivity_ViewBinding implements Unbinder {
    private UserUpdateActivity target;
    private View view7f0a061a;

    public UserUpdateActivity_ViewBinding(UserUpdateActivity userUpdateActivity) {
        this(userUpdateActivity, userUpdateActivity.getWindow().getDecorView());
    }

    public UserUpdateActivity_ViewBinding(final UserUpdateActivity userUpdateActivity, View view) {
        this.target = userUpdateActivity;
        userUpdateActivity.toolbarTop = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar_top, "field 'toolbarTop'", MobirollerToolbar.class);
        userUpdateActivity.userUpdateProfileImage = (CircleImageView) C0812Utils.findRequiredViewAsType(view, R.id.user_update_profile_image, "field 'userUpdateProfileImage'", CircleImageView.class);
        userUpdateActivity.userUpdateHeader = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.user_update_header, "field 'userUpdateHeader'", RelativeLayout.class);
        userUpdateActivity.userUpdateList = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.user_update_list, "field 'userUpdateList'", RecyclerView.class);
        userUpdateActivity.userUpdateForm = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.user_update_form, "field 'userUpdateForm'", RelativeLayout.class);
        userUpdateActivity.userOpenGalleryImageView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.user_update_open_gallery, "field 'userOpenGalleryImageView'", ImageView.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.user_update_image_edit, "method 'openGalleryIntent'");
        this.view7f0a061a = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                userUpdateActivity.openGalleryIntent();
            }
        });
    }

    public void unbind() {
        UserUpdateActivity userUpdateActivity = this.target;
        if (userUpdateActivity != null) {
            this.target = null;
            userUpdateActivity.toolbarTop = null;
            userUpdateActivity.userUpdateProfileImage = null;
            userUpdateActivity.userUpdateHeader = null;
            userUpdateActivity.userUpdateList = null;
            userUpdateActivity.userUpdateForm = null;
            userUpdateActivity.userOpenGalleryImageView = null;
            this.view7f0a061a.setOnClickListener(null);
            this.view7f0a061a = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
