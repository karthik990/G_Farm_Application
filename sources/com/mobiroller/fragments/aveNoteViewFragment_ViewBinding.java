package com.mobiroller.fragments;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobiroller.mobi942763453128.R;

public class aveNoteViewFragment_ViewBinding implements Unbinder {
    private aveNoteViewFragment target;

    public aveNoteViewFragment_ViewBinding(aveNoteViewFragment avenoteviewfragment, View view) {
        this.target = avenoteviewfragment;
        avenoteviewfragment.noteListView = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.note_list_view, "field 'noteListView'", RecyclerView.class);
        avenoteviewfragment.notificationEmptyImage = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.notification_empty_image, "field 'notificationEmptyImage'", ImageView.class);
        avenoteviewfragment.noteEmptyText = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.note_empty_text, "field 'noteEmptyText'", TextView.class);
        avenoteviewfragment.emptyView = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.empty_view, "field 'emptyView'", RelativeLayout.class);
        avenoteviewfragment.addNoteFAB = (FloatingActionButton) C0812Utils.findRequiredViewAsType(view, R.id.fab_add, "field 'addNoteFAB'", FloatingActionButton.class);
        avenoteviewfragment.fabLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.fab_layout, "field 'fabLayout'", RelativeLayout.class);
        avenoteviewfragment.noteFrameView = (FrameLayout) C0812Utils.findRequiredViewAsType(view, R.id.note_frame, "field 'noteFrameView'", FrameLayout.class);
        avenoteviewfragment.note_rel_layout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.note_layout, "field 'note_rel_layout'", RelativeLayout.class);
        avenoteviewfragment.noteLinearLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.note_linear_layout, "field 'noteLinearLayout'", LinearLayout.class);
    }

    public void unbind() {
        aveNoteViewFragment avenoteviewfragment = this.target;
        if (avenoteviewfragment != null) {
            this.target = null;
            avenoteviewfragment.noteListView = null;
            avenoteviewfragment.notificationEmptyImage = null;
            avenoteviewfragment.noteEmptyText = null;
            avenoteviewfragment.emptyView = null;
            avenoteviewfragment.addNoteFAB = null;
            avenoteviewfragment.fabLayout = null;
            avenoteviewfragment.noteFrameView = null;
            avenoteviewfragment.note_rel_layout = null;
            avenoteviewfragment.noteLinearLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
