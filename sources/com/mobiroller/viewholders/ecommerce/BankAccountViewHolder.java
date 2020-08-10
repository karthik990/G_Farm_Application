package com.mobiroller.viewholders.ecommerce;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.BankAccount;
import com.mobiroller.views.custom.MobirollerTextView;

public class BankAccountViewHolder extends ViewHolder {
    private BankAccount bankAccount;
    @BindView(2131361970)
    MobirollerTextView bankAccountName;
    @BindView(2131361971)
    MobirollerTextView bankBranch;
    @BindView(2131361972)
    MobirollerTextView bankIban;
    @BindView(2131361973)
    MobirollerTextView bankName;
    @BindView(2131361974)
    MobirollerTextView bankReference;
    @BindView(2131362209)
    ImageView copyButton;
    public View itemView;
    @BindView(2131362649)
    ConstraintLayout mainLayout;
    @BindView(2131363116)
    ImageView selectedImageView;

    public BankAccountViewHolder(View view) {
        super(view);
        this.itemView = view;
        ButterKnife.bind((Object) this, view);
    }

    public void bind(BankAccount bankAccount2) {
        this.bankAccount = bankAccount2;
        this.bankName.setText(bankAccount2.name);
        if (bankAccount2.nameSurname != null) {
            this.bankReference.setText(bankAccount2.nameSurname);
        } else {
            this.bankReference.setVisibility(8);
        }
        this.bankIban.setText(this.bankAccountName.getContext().getString(R.string.e_commerce_payment_bank_iban, new Object[]{bankAccount2.accountAddress}));
        MobirollerTextView mobirollerTextView = this.bankBranch;
        Context context = this.bankAccountName.getContext();
        StringBuilder sb = new StringBuilder();
        sb.append(bankAccount2.accountName);
        sb.append(" / ");
        sb.append(bankAccount2.accountCode);
        mobirollerTextView.setText(context.getString(R.string.e_commerce_payment_bank_branch, new Object[]{sb.toString()}));
        MobirollerTextView mobirollerTextView2 = this.bankAccountName;
        mobirollerTextView2.setText(mobirollerTextView2.getContext().getString(R.string.e_commerce_payment_bank_account, new Object[]{bankAccount2.accountNumber}));
        if (bankAccount2.isSelected) {
            ConstraintLayout constraintLayout = this.mainLayout;
            constraintLayout.setBackground(ContextCompat.getDrawable(constraintLayout.getContext(), R.drawable.e_commerce_bank_selected));
            this.copyButton.setVisibility(0);
            this.selectedImageView.setVisibility(0);
            return;
        }
        ConstraintLayout constraintLayout2 = this.mainLayout;
        constraintLayout2.setBackground(ContextCompat.getDrawable(constraintLayout2.getContext(), R.drawable.e_commerce_bank_unselected));
        this.copyButton.setVisibility(8);
        this.selectedImageView.setVisibility(8);
    }

    @OnClick({2131362209})
    public void onClickCopyButton() {
        ((ClipboardManager) this.itemView.getContext().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("iban", this.bankAccount.accountAddress));
        Toast.makeText(this.itemView.getContext(), R.string.e_commerce_payment_bank_copy_toast_message, 0).show();
    }
}
