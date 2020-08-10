package com.mobiroller.viewholders.user;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerTextView;

public class PopupAddressViewHolder_ViewBinding implements Unbinder {
    private PopupAddressViewHolder target;

    public PopupAddressViewHolder_ViewBinding(PopupAddressViewHolder popupAddressViewHolder, View view) {
        this.target = popupAddressViewHolder;
        popupAddressViewHolder.addressTitle = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.address_title, "field 'addressTitle'", MobirollerTextView.class);
        popupAddressViewHolder.addressDescriptionFirstLine = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.address_description_first, "field 'addressDescriptionFirstLine'", MobirollerTextView.class);
        popupAddressViewHolder.addressDescriptionSecondLine = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.address_description_second, "field 'addressDescriptionSecondLine'", MobirollerTextView.class);
        popupAddressViewHolder.addressDescriptionThirdLine = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.address_description_third, "field 'addressDescriptionThirdLine'", MobirollerTextView.class);
        popupAddressViewHolder.addressContentInnerLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.address_content_inner_layout, "field 'addressContentInnerLayout'", ConstraintLayout.class);
        popupAddressViewHolder.addressMainLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.address_main_layout, "field 'addressMainLayout'", ConstraintLayout.class);
    }

    public void unbind() {
        PopupAddressViewHolder popupAddressViewHolder = this.target;
        if (popupAddressViewHolder != null) {
            this.target = null;
            popupAddressViewHolder.addressTitle = null;
            popupAddressViewHolder.addressDescriptionFirstLine = null;
            popupAddressViewHolder.addressDescriptionSecondLine = null;
            popupAddressViewHolder.addressDescriptionThirdLine = null;
            popupAddressViewHolder.addressContentInnerLayout = null;
            popupAddressViewHolder.addressMainLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
