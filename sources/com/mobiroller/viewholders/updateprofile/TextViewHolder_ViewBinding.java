package com.mobiroller.viewholders.updateprofile;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.rengwuxian.materialedittext.MaterialEditText;

public class TextViewHolder_ViewBinding implements Unbinder {
    private TextViewHolder target;

    public TextViewHolder_ViewBinding(TextViewHolder textViewHolder, View view) {
        this.target = textViewHolder;
        textViewHolder.textArea = (MaterialEditText) C0812Utils.findRequiredViewAsType(view, R.id.form_item_text, "field 'textArea'", MaterialEditText.class);
    }

    public void unbind() {
        TextViewHolder textViewHolder = this.target;
        if (textViewHolder != null) {
            this.target = null;
            textViewHolder.textArea = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
