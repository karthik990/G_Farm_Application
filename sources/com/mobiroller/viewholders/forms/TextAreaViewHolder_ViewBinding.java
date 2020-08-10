package com.mobiroller.viewholders.forms;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.rengwuxian.materialedittext.MaterialEditText;

public class TextAreaViewHolder_ViewBinding implements Unbinder {
    private TextAreaViewHolder target;

    public TextAreaViewHolder_ViewBinding(TextAreaViewHolder textAreaViewHolder, View view) {
        this.target = textAreaViewHolder;
        textAreaViewHolder.textArea = (MaterialEditText) C0812Utils.findRequiredViewAsType(view, R.id.form_item_text_area, "field 'textArea'", MaterialEditText.class);
    }

    public void unbind() {
        TextAreaViewHolder textAreaViewHolder = this.target;
        if (textAreaViewHolder != null) {
            this.target = null;
            textAreaViewHolder.textArea = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
