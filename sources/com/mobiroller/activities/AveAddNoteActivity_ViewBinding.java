package com.mobiroller.activities;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerToolbar;

public class AveAddNoteActivity_ViewBinding implements Unbinder {
    private AveAddNoteActivity target;

    public AveAddNoteActivity_ViewBinding(AveAddNoteActivity aveAddNoteActivity) {
        this(aveAddNoteActivity, aveAddNoteActivity.getWindow().getDecorView());
    }

    public AveAddNoteActivity_ViewBinding(AveAddNoteActivity aveAddNoteActivity, View view) {
        this.target = aveAddNoteActivity;
        aveAddNoteActivity.toolbarTop = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar_top, "field 'toolbarTop'", MobirollerToolbar.class);
        aveAddNoteActivity.title = (EditText) C0812Utils.findRequiredViewAsType(view, R.id.note_title, "field 'title'", EditText.class);
        aveAddNoteActivity.description = (EditText) C0812Utils.findRequiredViewAsType(view, R.id.note_description, "field 'description'", EditText.class);
        aveAddNoteActivity.colorTextview = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.color_textview, "field 'colorTextview'", TextView.class);
        aveAddNoteActivity.colorRecyclerView = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.note_colors, "field 'colorRecyclerView'", RecyclerView.class);
        aveAddNoteActivity.imagesTextview = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.images_textview, "field 'imagesTextview'", TextView.class);
        aveAddNoteActivity.imageRecyclerView = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.note_images, "field 'imageRecyclerView'", RecyclerView.class);
    }

    public void unbind() {
        AveAddNoteActivity aveAddNoteActivity = this.target;
        if (aveAddNoteActivity != null) {
            this.target = null;
            aveAddNoteActivity.toolbarTop = null;
            aveAddNoteActivity.title = null;
            aveAddNoteActivity.description = null;
            aveAddNoteActivity.colorTextview = null;
            aveAddNoteActivity.colorRecyclerView = null;
            aveAddNoteActivity.imagesTextview = null;
            aveAddNoteActivity.imageRecyclerView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
