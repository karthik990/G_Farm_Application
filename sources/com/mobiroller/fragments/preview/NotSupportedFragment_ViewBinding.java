package com.mobiroller.fragments.preview;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class NotSupportedFragment_ViewBinding implements Unbinder {
    private NotSupportedFragment target;

    public NotSupportedFragment_ViewBinding(NotSupportedFragment notSupportedFragment, View view) {
        this.target = notSupportedFragment;
        notSupportedFragment.notSupportedText = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.not_supported_info, "field 'notSupportedText'", TextView.class);
    }

    public void unbind() {
        NotSupportedFragment notSupportedFragment = this.target;
        if (notSupportedFragment != null) {
            this.target = null;
            notSupportedFragment.notSupportedText = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
