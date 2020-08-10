package com.mobiroller.viewholders.user;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerTextView;

public class AddressViewHolder_ViewBinding implements Unbinder {
    private AddressViewHolder target;
    private View view7f0a0059;
    private View view7f0a005d;
    private View view7f0a013c;
    private View view7f0a01be;

    public AddressViewHolder_ViewBinding(final AddressViewHolder addressViewHolder, View view) {
        this.target = addressViewHolder;
        addressViewHolder.title = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.title, "field 'title'", MobirollerTextView.class);
        addressViewHolder.address = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.address, "field 'address'", MobirollerTextView.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.action_delete, "field 'delete' and method 'onClickDeleteAction'");
        addressViewHolder.delete = (ImageView) C0812Utils.castView(findRequiredView, R.id.action_delete, "field 'delete'", ImageView.class);
        this.view7f0a0059 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                addressViewHolder.onClickDeleteAction();
            }
        });
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.action_edit, "field 'edit' and method 'onClickEditAction'");
        addressViewHolder.edit = (ImageView) C0812Utils.castView(findRequiredView2, R.id.action_edit, "field 'edit'", ImageView.class);
        this.view7f0a005d = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                addressViewHolder.onClickEditAction();
            }
        });
        View findRequiredView3 = C0812Utils.findRequiredView(view, R.id.cancel_button, "field 'cancelButton' and method 'onClickCancelButton'");
        addressViewHolder.cancelButton = (Button) C0812Utils.castView(findRequiredView3, R.id.cancel_button, "field 'cancelButton'", Button.class);
        this.view7f0a013c = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                addressViewHolder.onClickCancelButton();
            }
        });
        addressViewHolder.deleteTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.delete_text_view, "field 'deleteTextView'", MobirollerTextView.class);
        addressViewHolder.deleteLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.delete_layout, "field 'deleteLayout'", ConstraintLayout.class);
        View findRequiredView4 = C0812Utils.findRequiredView(view, R.id.delete_button, "method 'onClickDeleteButton'");
        this.view7f0a01be = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                addressViewHolder.onClickDeleteButton();
            }
        });
    }

    public void unbind() {
        AddressViewHolder addressViewHolder = this.target;
        if (addressViewHolder != null) {
            this.target = null;
            addressViewHolder.title = null;
            addressViewHolder.address = null;
            addressViewHolder.delete = null;
            addressViewHolder.edit = null;
            addressViewHolder.cancelButton = null;
            addressViewHolder.deleteTextView = null;
            addressViewHolder.deleteLayout = null;
            this.view7f0a0059.setOnClickListener(null);
            this.view7f0a0059 = null;
            this.view7f0a005d.setOnClickListener(null);
            this.view7f0a005d = null;
            this.view7f0a013c.setOnClickListener(null);
            this.view7f0a013c = null;
            this.view7f0a01be.setOnClickListener(null);
            this.view7f0a01be = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
