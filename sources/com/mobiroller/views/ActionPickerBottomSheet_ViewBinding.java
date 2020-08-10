package com.mobiroller.views;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class ActionPickerBottomSheet_ViewBinding implements Unbinder {
    private ActionPickerBottomSheet target;

    public ActionPickerBottomSheet_ViewBinding(ActionPickerBottomSheet actionPickerBottomSheet, View view) {
        this.target = actionPickerBottomSheet;
        actionPickerBottomSheet.recyclerView = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.recycler_view, "field 'recyclerView'", RecyclerView.class);
    }

    public void unbind() {
        ActionPickerBottomSheet actionPickerBottomSheet = this.target;
        if (actionPickerBottomSheet != null) {
            this.target = null;
            actionPickerBottomSheet.recyclerView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
