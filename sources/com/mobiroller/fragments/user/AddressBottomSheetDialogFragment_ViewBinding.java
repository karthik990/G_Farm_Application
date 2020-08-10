package com.mobiroller.fragments.user;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerToolbar;

public class AddressBottomSheetDialogFragment_ViewBinding implements Unbinder {
    private AddressBottomSheetDialogFragment target;

    public AddressBottomSheetDialogFragment_ViewBinding(AddressBottomSheetDialogFragment addressBottomSheetDialogFragment, View view) {
        this.target = addressBottomSheetDialogFragment;
        addressBottomSheetDialogFragment.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar, "field 'toolbar'", MobirollerToolbar.class);
        addressBottomSheetDialogFragment.nameTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.nameTextInputEditText, "field 'nameTextInputEditText'", TextInputEditText.class);
        addressBottomSheetDialogFragment.nameTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.nameTextInputLayout, "field 'nameTextInputLayout'", TextInputLayout.class);
        addressBottomSheetDialogFragment.surnameTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.surnameTextInputEditText, "field 'surnameTextInputEditText'", TextInputEditText.class);
        addressBottomSheetDialogFragment.surnameTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.surnameTextInputLayout, "field 'surnameTextInputLayout'", TextInputLayout.class);
        addressBottomSheetDialogFragment.phoneTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.phoneTextInputEditText, "field 'phoneTextInputEditText'", TextInputEditText.class);
        addressBottomSheetDialogFragment.phoneTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.phoneTextInputLayout, "field 'phoneTextInputLayout'", TextInputLayout.class);
        addressBottomSheetDialogFragment.countryTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.countryTextInputEditText, "field 'countryTextInputEditText'", TextInputEditText.class);
        addressBottomSheetDialogFragment.countryTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.countryTextInputLayout, "field 'countryTextInputLayout'", TextInputLayout.class);
        addressBottomSheetDialogFragment.stateTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.cityTextInputEditText, "field 'stateTextInputEditText'", TextInputEditText.class);
        addressBottomSheetDialogFragment.stateTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.cityTextInputLayout, "field 'stateTextInputLayout'", TextInputLayout.class);
        addressBottomSheetDialogFragment.districtTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.districtTextInputEditText, "field 'districtTextInputEditText'", TextInputEditText.class);
        addressBottomSheetDialogFragment.districtTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.districtTextInputLayout, "field 'districtTextInputLayout'", TextInputLayout.class);
        addressBottomSheetDialogFragment.addressTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.addressTextInputEditText, "field 'addressTextInputEditText'", TextInputEditText.class);
        addressBottomSheetDialogFragment.addressTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.addressTextInputLayout, "field 'addressTextInputLayout'", TextInputLayout.class);
        addressBottomSheetDialogFragment.postalTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.postalTextInputEditText, "field 'postalTextInputEditText'", TextInputEditText.class);
        addressBottomSheetDialogFragment.postalTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.postalTextInputLayout, "field 'postalTextInputLayout'", TextInputLayout.class);
        addressBottomSheetDialogFragment.titleTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.titleTextInputEditText, "field 'titleTextInputEditText'", TextInputEditText.class);
        addressBottomSheetDialogFragment.titleTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.titleTextInputLayout, "field 'titleTextInputLayout'", TextInputLayout.class);
        addressBottomSheetDialogFragment.saveAsBilling = (CheckBox) C0812Utils.findRequiredViewAsType(view, R.id.save_as_billing, "field 'saveAsBilling'", CheckBox.class);
        addressBottomSheetDialogFragment.radioGroup = (RadioGroup) C0812Utils.findRequiredViewAsType(view, R.id.radio_group, "field 'radioGroup'", RadioGroup.class);
        addressBottomSheetDialogFragment.companyNameTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.companyNameTextInputEditText, "field 'companyNameTextInputEditText'", TextInputEditText.class);
        addressBottomSheetDialogFragment.companyNameTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.companyNameTextInputLayout, "field 'companyNameTextInputLayout'", TextInputLayout.class);
        addressBottomSheetDialogFragment.taxAdminTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.taxAdminTextInputEditText, "field 'taxAdminTextInputEditText'", TextInputEditText.class);
        addressBottomSheetDialogFragment.taxAdminTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.taxAdminTextInputLayout, "field 'taxAdminTextInputLayout'", TextInputLayout.class);
        addressBottomSheetDialogFragment.taxNumberTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.taxNumberTextInputEditText, "field 'taxNumberTextInputEditText'", TextInputEditText.class);
        addressBottomSheetDialogFragment.taxNumberTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.taxNumberTextInputLayout, "field 'taxNumberTextInputLayout'", TextInputLayout.class);
        addressBottomSheetDialogFragment.identityNumberTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.identityNumberTextInputEditText, "field 'identityNumberTextInputEditText'", TextInputEditText.class);
        addressBottomSheetDialogFragment.identityNumberTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.identityNumberTextInputLayout, "field 'identityNumberTextInputLayout'", TextInputLayout.class);
        addressBottomSheetDialogFragment.billingLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.billing_layout, "field 'billingLayout'", ConstraintLayout.class);
        addressBottomSheetDialogFragment.individual = (AppCompatRadioButton) C0812Utils.findRequiredViewAsType(view, R.id.individual, "field 'individual'", AppCompatRadioButton.class);
        addressBottomSheetDialogFragment.corporate = (AppCompatRadioButton) C0812Utils.findRequiredViewAsType(view, R.id.corporate, "field 'corporate'", AppCompatRadioButton.class);
    }

    public void unbind() {
        AddressBottomSheetDialogFragment addressBottomSheetDialogFragment = this.target;
        if (addressBottomSheetDialogFragment != null) {
            this.target = null;
            addressBottomSheetDialogFragment.toolbar = null;
            addressBottomSheetDialogFragment.nameTextInputEditText = null;
            addressBottomSheetDialogFragment.nameTextInputLayout = null;
            addressBottomSheetDialogFragment.surnameTextInputEditText = null;
            addressBottomSheetDialogFragment.surnameTextInputLayout = null;
            addressBottomSheetDialogFragment.phoneTextInputEditText = null;
            addressBottomSheetDialogFragment.phoneTextInputLayout = null;
            addressBottomSheetDialogFragment.countryTextInputEditText = null;
            addressBottomSheetDialogFragment.countryTextInputLayout = null;
            addressBottomSheetDialogFragment.stateTextInputEditText = null;
            addressBottomSheetDialogFragment.stateTextInputLayout = null;
            addressBottomSheetDialogFragment.districtTextInputEditText = null;
            addressBottomSheetDialogFragment.districtTextInputLayout = null;
            addressBottomSheetDialogFragment.addressTextInputEditText = null;
            addressBottomSheetDialogFragment.addressTextInputLayout = null;
            addressBottomSheetDialogFragment.postalTextInputEditText = null;
            addressBottomSheetDialogFragment.postalTextInputLayout = null;
            addressBottomSheetDialogFragment.titleTextInputEditText = null;
            addressBottomSheetDialogFragment.titleTextInputLayout = null;
            addressBottomSheetDialogFragment.saveAsBilling = null;
            addressBottomSheetDialogFragment.radioGroup = null;
            addressBottomSheetDialogFragment.companyNameTextInputEditText = null;
            addressBottomSheetDialogFragment.companyNameTextInputLayout = null;
            addressBottomSheetDialogFragment.taxAdminTextInputEditText = null;
            addressBottomSheetDialogFragment.taxAdminTextInputLayout = null;
            addressBottomSheetDialogFragment.taxNumberTextInputEditText = null;
            addressBottomSheetDialogFragment.taxNumberTextInputLayout = null;
            addressBottomSheetDialogFragment.identityNumberTextInputEditText = null;
            addressBottomSheetDialogFragment.identityNumberTextInputLayout = null;
            addressBottomSheetDialogFragment.billingLayout = null;
            addressBottomSheetDialogFragment.individual = null;
            addressBottomSheetDialogFragment.corporate = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
