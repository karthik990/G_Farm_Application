package com.mobiroller.viewholders.forms;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.SquareImageView;

public class ImagePickerViewHolder_ViewBinding implements Unbinder {
    private ImagePickerViewHolder target;

    public ImagePickerViewHolder_ViewBinding(ImagePickerViewHolder imagePickerViewHolder, View view) {
        this.target = imagePickerViewHolder;
        imagePickerViewHolder.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.form_item_title, "field 'title'", TextView.class);
        imagePickerViewHolder.imageMainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.form_image_main_layout, "field 'imageMainLayout'", RelativeLayout.class);
        imagePickerViewHolder.image = (SquareImageView) C0812Utils.findRequiredViewAsType(view, R.id.form_image, "field 'image'", SquareImageView.class);
        imagePickerViewHolder.removeImageView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.form_image_remove, "field 'removeImageView'", ImageView.class);
    }

    public void unbind() {
        ImagePickerViewHolder imagePickerViewHolder = this.target;
        if (imagePickerViewHolder != null) {
            this.target = null;
            imagePickerViewHolder.title = null;
            imagePickerViewHolder.imageMainLayout = null;
            imagePickerViewHolder.image = null;
            imagePickerViewHolder.removeImageView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
