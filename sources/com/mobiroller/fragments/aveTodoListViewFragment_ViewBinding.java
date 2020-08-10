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

public class aveTodoListViewFragment_ViewBinding implements Unbinder {
    private aveTodoListViewFragment target;

    public aveTodoListViewFragment_ViewBinding(aveTodoListViewFragment avetodolistviewfragment, View view) {
        this.target = avetodolistviewfragment;
        avetodolistviewfragment.todoListView = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.listview, "field 'todoListView'", RecyclerView.class);
        avetodolistviewfragment.todoEmptyImage = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.todo_empty_image, "field 'todoEmptyImage'", ImageView.class);
        avetodolistviewfragment.todoEmptyText = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.todo_empty_text, "field 'todoEmptyText'", TextView.class);
        avetodolistviewfragment.emptyView = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.empty_view, "field 'emptyView'", RelativeLayout.class);
        avetodolistviewfragment.fab_add = (FloatingActionButton) C0812Utils.findRequiredViewAsType(view, R.id.fab_add, "field 'fab_add'", FloatingActionButton.class);
        avetodolistviewfragment.fabLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.fab_layout, "field 'fabLayout'", RelativeLayout.class);
        avetodolistviewfragment.todo_frame_layout = (FrameLayout) C0812Utils.findRequiredViewAsType(view, R.id.todo_frame, "field 'todo_frame_layout'", FrameLayout.class);
        avetodolistviewfragment.todo_layout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.todo_layout, "field 'todo_layout'", RelativeLayout.class);
        avetodolistviewfragment.mainview = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.mainview, "field 'mainview'", LinearLayout.class);
    }

    public void unbind() {
        aveTodoListViewFragment avetodolistviewfragment = this.target;
        if (avetodolistviewfragment != null) {
            this.target = null;
            avetodolistviewfragment.todoListView = null;
            avetodolistviewfragment.todoEmptyImage = null;
            avetodolistviewfragment.todoEmptyText = null;
            avetodolistviewfragment.emptyView = null;
            avetodolistviewfragment.fab_add = null;
            avetodolistviewfragment.fabLayout = null;
            avetodolistviewfragment.todo_frame_layout = null;
            avetodolistviewfragment.todo_layout = null;
            avetodolistviewfragment.mainview = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
