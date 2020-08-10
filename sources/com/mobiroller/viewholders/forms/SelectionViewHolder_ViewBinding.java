package com.mobiroller.viewholders.forms;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SelectionViewHolder_ViewBinding implements Unbinder {
    private SelectionViewHolder target;

    public SelectionViewHolder_ViewBinding(SelectionViewHolder selectionViewHolder, View view) {
        this.target = selectionViewHolder;
        selectionViewHolder.selection = (MaterialEditText) C0812Utils.findRequiredViewAsType(view, R.id.form_item_selection, "field 'selection'", MaterialEditText.class);
    }

    public void unbind() {
        SelectionViewHolder selectionViewHolder = this.target;
        if (selectionViewHolder != null) {
            this.target = null;
            selectionViewHolder.selection = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
