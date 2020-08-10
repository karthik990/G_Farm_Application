package com.mobiroller.viewholders.ecommerce;

import android.view.View;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerTextView;

public class BankAccountViewHolder_ViewBinding implements Unbinder {
    private BankAccountViewHolder target;
    private View view7f0a01a1;

    public BankAccountViewHolder_ViewBinding(final BankAccountViewHolder bankAccountViewHolder, View view) {
        this.target = bankAccountViewHolder;
        bankAccountViewHolder.bankIban = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.bank_iban, "field 'bankIban'", MobirollerTextView.class);
        bankAccountViewHolder.bankBranch = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.bank_branch, "field 'bankBranch'", MobirollerTextView.class);
        bankAccountViewHolder.bankReference = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.bank_reference, "field 'bankReference'", MobirollerTextView.class);
        bankAccountViewHolder.bankAccountName = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.bank_account_number, "field 'bankAccountName'", MobirollerTextView.class);
        bankAccountViewHolder.bankName = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.bank_name, "field 'bankName'", MobirollerTextView.class);
        bankAccountViewHolder.mainLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", ConstraintLayout.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.copy_button, "field 'copyButton' and method 'onClickCopyButton'");
        bankAccountViewHolder.copyButton = (ImageView) C0812Utils.castView(findRequiredView, R.id.copy_button, "field 'copyButton'", ImageView.class);
        this.view7f0a01a1 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                bankAccountViewHolder.onClickCopyButton();
            }
        });
        bankAccountViewHolder.selectedImageView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.selected_image_view, "field 'selectedImageView'", ImageView.class);
    }

    public void unbind() {
        BankAccountViewHolder bankAccountViewHolder = this.target;
        if (bankAccountViewHolder != null) {
            this.target = null;
            bankAccountViewHolder.bankIban = null;
            bankAccountViewHolder.bankBranch = null;
            bankAccountViewHolder.bankReference = null;
            bankAccountViewHolder.bankAccountName = null;
            bankAccountViewHolder.bankName = null;
            bankAccountViewHolder.mainLayout = null;
            bankAccountViewHolder.copyButton = null;
            bankAccountViewHolder.selectedImageView = null;
            this.view7f0a01a1.setOnClickListener(null);
            this.view7f0a01a1 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
