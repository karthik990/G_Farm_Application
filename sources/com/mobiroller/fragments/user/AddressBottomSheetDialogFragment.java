package com.mobiroller.fragments.user;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.core.view.ViewCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobiroller.adapters.DialogFilterAdapter;
import com.mobiroller.constants.UserConstants;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.EditBillingAddressEvent;
import com.mobiroller.models.events.EditShippingAddressEvent;
import com.mobiroller.models.events.NewBillingAddressEvent;
import com.mobiroller.models.events.NewShippingAddressEvent;
import com.mobiroller.models.user.AddBillingAddress;
import com.mobiroller.models.user.AddShippingAddress;
import com.mobiroller.models.user.CountryModel;
import com.mobiroller.models.user.DefaultAddressList;
import com.mobiroller.models.user.EditBillingAddress;
import com.mobiroller.models.user.EditShippingAddress;
import com.mobiroller.models.user.UserBillingAddressModel;
import com.mobiroller.models.user.UserShippingAddressModel;
import com.mobiroller.serviceinterfaces.ApplyzeUserServiceInterface;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.views.ItemClickSupport;
import com.mobiroller.views.ItemClickSupport.OnItemClickListener;
import com.mobiroller.views.MaterialListFilterDialog;
import com.mobiroller.views.custom.MobirollerButton;
import com.mobiroller.views.custom.MobirollerToolbar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressBottomSheetDialogFragment extends BottomSheetDialogFragment {
    @BindView(2131361918)
    TextInputEditText addressTextInputEditText;
    @BindView(2131361919)
    TextInputLayout addressTextInputLayout;
    private ApplyzeUserServiceInterface applyzeUserServiceInterface;
    Behavior behavior;
    @BindView(2131361996)
    ConstraintLayout billingLayout;
    @BindView(2131362180)
    TextInputEditText companyNameTextInputEditText;
    @BindView(2131362181)
    TextInputLayout companyNameTextInputLayout;
    @BindView(2131362210)
    AppCompatRadioButton corporate;
    Call<List<CountryModel>> countriesCall;
    /* access modifiers changed from: private */
    public List<CountryModel> countryList;
    /* access modifiers changed from: private */
    public CountryModel countrySelectedModel;
    private int countrySelectedPosition;
    @BindView(2131362212)
    TextInputEditText countryTextInputEditText;
    @BindView(2131362213)
    TextInputLayout countryTextInputLayout;
    Call<List<CountryModel>> districtCall;
    /* access modifiers changed from: private */
    public List<CountryModel> districtList;
    /* access modifiers changed from: private */
    public CountryModel districtSelectedModel;
    /* access modifiers changed from: private */
    public int districtSelectedPosition;
    @BindView(2131362299)
    TextInputEditText districtTextInputEditText;
    @BindView(2131362300)
    TextInputLayout districtTextInputLayout;
    View focusView = null;
    @BindView(2131362528)
    TextInputEditText identityNumberTextInputEditText;
    @BindView(2131362529)
    TextInputLayout identityNumberTextInputLayout;
    @BindView(2131362567)
    AppCompatRadioButton individual;
    private boolean isBilling = false;
    private boolean isEdit = false;
    private boolean isShipping = false;
    private BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetCallback() {
        public void onSlide(View view, float f) {
        }

        public void onStateChanged(View view, int i) {
            if (i == 5) {
                AddressBottomSheetDialogFragment.this.dismiss();
            }
        }
    };
    @BindView(2131362787)
    TextInputEditText nameTextInputEditText;
    @BindView(2131362788)
    TextInputLayout nameTextInputLayout;
    @BindView(2131362920)
    TextInputEditText phoneTextInputEditText;
    @BindView(2131362921)
    TextInputLayout phoneTextInputLayout;
    @BindView(2131362976)
    TextInputEditText postalTextInputEditText;
    @BindView(2131362977)
    TextInputLayout postalTextInputLayout;
    /* access modifiers changed from: private */
    public ProgressViewHelper progressViewHelper;
    @BindView(2131363017)
    RadioGroup radioGroup;
    RequestHelper requestHelper;
    @BindView(2131363082)
    CheckBox saveAsBilling;
    MobirollerButton saveButton;
    ConstraintLayout saveLayout;
    SharedPrefHelper sharedPrefHelper;
    /* access modifiers changed from: private */
    public List<CountryModel> stateList;
    /* access modifiers changed from: private */
    public CountryModel stateSelectedModel;
    /* access modifiers changed from: private */
    public int stateSelectedPosition;
    @BindView(2131362161)
    TextInputEditText stateTextInputEditText;
    @BindView(2131362162)
    TextInputLayout stateTextInputLayout;
    Call<List<CountryModel>> statesCall;
    @BindView(2131363204)
    TextInputEditText surnameTextInputEditText;
    @BindView(2131363205)
    TextInputLayout surnameTextInputLayout;
    @BindView(2131363223)
    TextInputEditText taxAdminTextInputEditText;
    @BindView(2131363224)
    TextInputLayout taxAdminTextInputLayout;
    @BindView(2131363225)
    TextInputEditText taxNumberTextInputEditText;
    @BindView(2131363226)
    TextInputLayout taxNumberTextInputLayout;
    @BindView(2131363260)
    TextInputEditText titleTextInputEditText;
    @BindView(2131363261)
    TextInputLayout titleTextInputLayout;
    @BindView(2131363270)
    MobirollerToolbar toolbar;
    Unbinder unbinder;
    private UserBillingAddressModel userBillingAddressModel;
    private UserShippingAddressModel userShippingAddressModel;

    public Dialog onCreateDialog(Bundle bundle) {
        return super.onCreateDialog(bundle);
    }

    public void setupDialog(final Dialog dialog, int i) {
        super.setupDialog(dialog, i);
        View inflate = View.inflate(getContext(), R.layout.bottom_sheet_add_address_content_view, null);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.requestHelper = new RequestHelper();
        this.applyzeUserServiceInterface = this.requestHelper.getApplyzeUserAPIService();
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        dialog.setContentView(inflate);
        dialog.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialogInterface) {
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) dialog.findViewById(R.id.coordinator);
                FrameLayout frameLayout = (FrameLayout) dialog.findViewById(R.id.container);
                View inflate = dialog.getLayoutInflater().inflate(R.layout.bottom_sheet_bottom_button, null);
                LayoutParams layoutParams = new LayoutParams(-1, -2);
                layoutParams.gravity = 80;
                inflate.setLayoutParams(layoutParams);
                AddressBottomSheetDialogFragment.this.saveButton = (MobirollerButton) inflate.findViewById(R.id.save_button);
                AddressBottomSheetDialogFragment.this.saveLayout = (ConstraintLayout) inflate.findViewById(R.id.save_layout);
                AddressBottomSheetDialogFragment.this.saveButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        AddressBottomSheetDialogFragment.this.onClickSave();
                    }
                });
                frameLayout.addView(inflate);
                inflate.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
                ((MarginLayoutParams) coordinatorLayout.getLayoutParams()).bottomMargin = inflate.getMeasuredHeight();
                frameLayout.requestLayout();
            }
        });
        this.behavior = ((CoordinatorLayout.LayoutParams) ((View) inflate.getParent()).getLayoutParams()).getBehavior();
        Behavior behavior2 = this.behavior;
        if (behavior2 != null && (behavior2 instanceof BottomSheetBehavior)) {
            ((BottomSheetBehavior) behavior2).setBottomSheetCallback(this.mBottomSheetBehaviorCallback);
            ((BottomSheetBehavior) this.behavior).setHideable(false);
        }
        if (getArguments() != null) {
            Bundle arguments = getArguments();
            String str = UserConstants.BUNDLE_EXTRA_USER_ADDRESS;
            if (arguments.containsKey(str)) {
                if (getArguments().getString(str).equalsIgnoreCase(UserConstants.BUNDLE_EXTRA_USER_BILLING_ADDRESS)) {
                    this.isBilling = true;
                } else {
                    this.isShipping = true;
                }
            }
        }
        if (getArguments() != null && getArguments().containsKey(UserConstants.BUNDLE_EXTRA_USER_ADDRESS_EDIT)) {
            this.isEdit = true;
        }
        if (!this.isEdit) {
            this.stateTextInputLayout.setVisibility(8);
            this.districtTextInputLayout.setVisibility(8);
        }
        getCountries();
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AddressBottomSheetDialogFragment.this.dismiss();
            }
        });
        if (this.isBilling) {
            this.saveAsBilling.setVisibility(8);
            this.billingLayout.setVisibility(0);
        }
        this.toolbar.setNavigationIcon((int) R.drawable.ic_close_white_24dp);
        setUIColor(ViewCompat.MEASURED_STATE_MASK);
        this.saveAsBilling.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    AddressBottomSheetDialogFragment.this.billingLayout.setVisibility(0);
                } else {
                    AddressBottomSheetDialogFragment.this.billingLayout.setVisibility(8);
                }
            }
        });
        this.corporate.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    AddressBottomSheetDialogFragment.this.companyNameTextInputLayout.setVisibility(0);
                    AddressBottomSheetDialogFragment.this.taxAdminTextInputLayout.setVisibility(0);
                    AddressBottomSheetDialogFragment.this.taxNumberTextInputLayout.setVisibility(0);
                    AddressBottomSheetDialogFragment.this.identityNumberTextInputLayout.setVisibility(8);
                    return;
                }
                AddressBottomSheetDialogFragment.this.companyNameTextInputLayout.setVisibility(8);
                AddressBottomSheetDialogFragment.this.taxAdminTextInputLayout.setVisibility(8);
                AddressBottomSheetDialogFragment.this.taxNumberTextInputLayout.setVisibility(8);
                AddressBottomSheetDialogFragment.this.identityNumberTextInputLayout.setVisibility(0);
            }
        });
        this.individual.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (!z) {
                    AddressBottomSheetDialogFragment.this.companyNameTextInputLayout.setVisibility(0);
                    AddressBottomSheetDialogFragment.this.taxAdminTextInputLayout.setVisibility(0);
                    AddressBottomSheetDialogFragment.this.taxNumberTextInputLayout.setVisibility(0);
                    AddressBottomSheetDialogFragment.this.identityNumberTextInputLayout.setVisibility(8);
                    return;
                }
                AddressBottomSheetDialogFragment.this.companyNameTextInputLayout.setVisibility(8);
                AddressBottomSheetDialogFragment.this.taxAdminTextInputLayout.setVisibility(8);
                AddressBottomSheetDialogFragment.this.taxNumberTextInputLayout.setVisibility(8);
                AddressBottomSheetDialogFragment.this.identityNumberTextInputLayout.setVisibility(0);
            }
        });
        this.countryTextInputEditText.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (AddressBottomSheetDialogFragment.this.countryList != null) {
                    final MaterialListFilterDialog materialListFilterDialog = new MaterialListFilterDialog(AddressBottomSheetDialogFragment.this.getActivity(), R.string.user_address_country, AddressBottomSheetDialogFragment.this.countryList);
                    ItemClickSupport.addTo(materialListFilterDialog.show()).setOnItemClickListener(new OnItemClickListener() {
                        public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                            CountryModel countryModel = (CountryModel) ((DialogFilterAdapter) recyclerView.getAdapter()).getItemAtPosition(i);
                            AddressBottomSheetDialogFragment.this.countrySelectedModel = countryModel;
                            AddressBottomSheetDialogFragment.this.districtTextInputEditText.setEnabled(true);
                            String str = "";
                            AddressBottomSheetDialogFragment.this.districtTextInputEditText.setText(str);
                            AddressBottomSheetDialogFragment.this.stateTextInputEditText.setEnabled(true);
                            AddressBottomSheetDialogFragment.this.stateTextInputEditText.setText(str);
                            AddressBottomSheetDialogFragment.this.countryTextInputEditText.setText(countryModel.name);
                            materialListFilterDialog.dismiss();
                            AddressBottomSheetDialogFragment.this.getStates(countryModel.f2190id);
                        }
                    });
                } else if (!UtilManager.networkHelper().isConnected()) {
                    DialogUtil.showNoConnectionInfo(AddressBottomSheetDialogFragment.this.getContext());
                } else {
                    AddressBottomSheetDialogFragment.this.getCountries();
                }
            }
        });
        this.stateTextInputEditText.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (AddressBottomSheetDialogFragment.this.countrySelectedModel != null && AddressBottomSheetDialogFragment.this.stateList != null && AddressBottomSheetDialogFragment.this.stateList.size() != 0) {
                    final MaterialListFilterDialog materialListFilterDialog = new MaterialListFilterDialog(AddressBottomSheetDialogFragment.this.getActivity(), R.string.user_address_city, AddressBottomSheetDialogFragment.this.stateList);
                    ItemClickSupport.addTo(materialListFilterDialog.show()).setOnItemClickListener(new OnItemClickListener() {
                        public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                            CountryModel countryModel = (CountryModel) ((DialogFilterAdapter) recyclerView.getAdapter()).getItemAtPosition(i);
                            AddressBottomSheetDialogFragment.this.stateSelectedModel = countryModel;
                            AddressBottomSheetDialogFragment.this.stateSelectedPosition = i;
                            materialListFilterDialog.dismiss();
                            if (!AddressBottomSheetDialogFragment.this.stateTextInputEditText.getText().toString().equalsIgnoreCase(countryModel.name)) {
                                AddressBottomSheetDialogFragment.this.districtTextInputEditText.setEnabled(true);
                                AddressBottomSheetDialogFragment.this.districtTextInputEditText.setText("");
                                AddressBottomSheetDialogFragment.this.stateTextInputEditText.setText(countryModel.name);
                                AddressBottomSheetDialogFragment.this.districtList = new ArrayList();
                                AddressBottomSheetDialogFragment.this.getCities(countryModel.f2190id);
                            }
                        }
                    });
                } else if (!UtilManager.networkHelper().isConnected()) {
                    DialogUtil.showNoConnectionInfo(AddressBottomSheetDialogFragment.this.getContext());
                } else {
                    AddressBottomSheetDialogFragment addressBottomSheetDialogFragment = AddressBottomSheetDialogFragment.this;
                    addressBottomSheetDialogFragment.getStates(addressBottomSheetDialogFragment.countrySelectedModel.f2190id);
                }
            }
        });
        this.districtTextInputEditText.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (AddressBottomSheetDialogFragment.this.stateSelectedModel != null && AddressBottomSheetDialogFragment.this.districtList != null && AddressBottomSheetDialogFragment.this.districtList.size() != 0) {
                    final MaterialListFilterDialog materialListFilterDialog = new MaterialListFilterDialog(AddressBottomSheetDialogFragment.this.getActivity(), R.string.user_address_district, AddressBottomSheetDialogFragment.this.districtList);
                    ItemClickSupport.addTo(materialListFilterDialog.show()).setOnItemClickListener(new OnItemClickListener() {
                        public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                            materialListFilterDialog.dismiss();
                            CountryModel countryModel = (CountryModel) ((DialogFilterAdapter) recyclerView.getAdapter()).getItemAtPosition(i);
                            AddressBottomSheetDialogFragment.this.districtSelectedModel = countryModel;
                            AddressBottomSheetDialogFragment.this.districtSelectedPosition = i;
                            AddressBottomSheetDialogFragment.this.districtTextInputEditText.setEnabled(true);
                            AddressBottomSheetDialogFragment.this.districtTextInputEditText.setText(countryModel.name);
                        }
                    });
                } else if (!UtilManager.networkHelper().isConnected()) {
                    DialogUtil.showNoConnectionInfo(AddressBottomSheetDialogFragment.this.getContext());
                } else {
                    AddressBottomSheetDialogFragment addressBottomSheetDialogFragment = AddressBottomSheetDialogFragment.this;
                    addressBottomSheetDialogFragment.getStates(addressBottomSheetDialogFragment.countrySelectedModel.f2190id);
                }
            }
        });
        if (this.isEdit) {
            Bundle arguments2 = getArguments();
            String str2 = UserConstants.BUNDLE_EXTRA_USER_ADDRESS_MODEL;
            if (arguments2.containsKey(str2)) {
                this.stateTextInputEditText.setEnabled(true);
                this.districtTextInputEditText.setEnabled(true);
                this.toolbar.setTitle((CharSequence) getString(R.string.user_my_address_update_address_title));
                Serializable serializable = getArguments().getSerializable(str2);
                if (serializable instanceof UserBillingAddressModel) {
                    this.userBillingAddressModel = (UserBillingAddressModel) serializable;
                } else {
                    this.userShippingAddressModel = (UserShippingAddressModel) serializable;
                }
                bindModel();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void bindModel() {
        String str = " ";
        if (this.userShippingAddressModel != null) {
            this.saveAsBilling.setVisibility(8);
            this.billingLayout.setVisibility(8);
            String[] split = this.userShippingAddressModel.contact.nameSurname.split(str);
            this.surnameTextInputEditText.setText(split[split.length - 1]);
            for (int i = 0; i < split.length - 1; i++) {
                if (i == 0) {
                    this.nameTextInputEditText.setText(split[0]);
                } else {
                    TextInputEditText textInputEditText = this.nameTextInputEditText;
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.nameTextInputEditText.getText().toString());
                    sb.append(str);
                    sb.append(split[i]);
                    textInputEditText.setText(sb.toString());
                }
            }
            this.phoneTextInputEditText.setText(this.userShippingAddressModel.contact.phoneNumber);
            this.addressTextInputEditText.setText(this.userShippingAddressModel.addressLine);
            this.districtTextInputEditText.setText(this.userShippingAddressModel.city);
            this.countryTextInputEditText.setText(this.userShippingAddressModel.country);
            this.stateTextInputEditText.setText(this.userShippingAddressModel.state);
            this.postalTextInputEditText.setText(this.userShippingAddressModel.zipCode);
            this.titleTextInputEditText.setText(this.userShippingAddressModel.title);
        } else {
            this.saveAsBilling.setVisibility(8);
            this.billingLayout.setVisibility(0);
            String[] split2 = this.userBillingAddressModel.contact.nameSurname.split(str);
            this.surnameTextInputEditText.setText(split2[split2.length - 1]);
            for (int i2 = 0; i2 < split2.length - 1; i2++) {
                if (i2 == 0) {
                    this.nameTextInputEditText.setText(split2[0]);
                } else {
                    TextInputEditText textInputEditText2 = this.nameTextInputEditText;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(this.nameTextInputEditText.getText().toString());
                    sb2.append(str);
                    sb2.append(split2[i2]);
                    textInputEditText2.setText(sb2.toString());
                }
            }
            this.phoneTextInputEditText.setText(this.userBillingAddressModel.contact.phoneNumber);
            this.addressTextInputEditText.setText(this.userBillingAddressModel.addressLine);
            this.districtTextInputEditText.setText(this.userBillingAddressModel.city);
            this.countryTextInputEditText.setText(this.userBillingAddressModel.country);
            this.stateTextInputEditText.setText(this.userBillingAddressModel.state);
            this.postalTextInputEditText.setText(this.userBillingAddressModel.zipCode);
            this.titleTextInputEditText.setText(this.userBillingAddressModel.title);
            if (this.userBillingAddressModel.type.equalsIgnoreCase("Corporate")) {
                this.corporate.setChecked(true);
                this.companyNameTextInputEditText.setText(this.userBillingAddressModel.companyName);
                this.taxAdminTextInputEditText.setText(this.userBillingAddressModel.taxOffice);
                this.taxNumberTextInputEditText.setText(this.userBillingAddressModel.taxNumber);
            } else {
                this.individual.setChecked(true);
                this.identityNumberTextInputEditText.setText(this.userBillingAddressModel.identityNumber);
            }
        }
        this.districtTextInputEditText.setEnabled(false);
        this.stateTextInputEditText.setEnabled(false);
    }

    /* access modifiers changed from: 0000 */
    public void setUIColor(int i) {
        int[][] iArr = {new int[]{16842912}, new int[0]};
        int[] iArr2 = {i, Color.parseColor("#757575")};
        CompoundButtonCompat.setButtonTintList(this.saveAsBilling, new ColorStateList(iArr, iArr2));
        CompoundButtonCompat.setButtonTintList(this.individual, new ColorStateList(iArr, iArr2));
        CompoundButtonCompat.setButtonTintList(this.corporate, new ColorStateList(iArr, iArr2));
    }

    public void onClickSave() {
        if (!validateForm()) {
            return;
        }
        if (this.isEdit) {
            editAddress();
        } else {
            saveAddress();
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x00ab  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x015e  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0176  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0188  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x01c2  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0204  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0221  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x026c  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0289  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x02d4  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x02f1  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0331  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x033a  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x034b  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0459  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean validateForm() {
        /*
            r8 = this;
            r0 = 0
            r8.focusView = r0
            com.google.android.material.textfield.TextInputEditText r1 = r8.nameTextInputEditText
            android.text.Editable r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = ""
            boolean r1 = r1.equalsIgnoreCase(r2)
            r3 = 3
            r4 = 2131231047(0x7f080147, float:1.8078164E38)
            r5 = 1
            r6 = 0
            if (r1 == 0) goto L_0x003d
            com.google.android.material.textfield.TextInputLayout r1 = r8.nameTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputLayout r1 = r8.nameTextInputLayout
            r7 = 2131821410(0x7f110362, float:1.9275562E38)
            java.lang.String r7 = r8.getString(r7)
            r1.setError(r7)
            com.google.android.material.textfield.TextInputEditText r1 = r8.nameTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputLayout r1 = r8.nameTextInputLayout
            r1.setPasswordVisibilityToggleEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.nameTextInputEditText
            r1.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r4, r6)
        L_0x003b:
            r1 = 0
            goto L_0x007e
        L_0x003d:
            com.google.android.material.textfield.TextInputEditText r1 = r8.nameTextInputEditText
            android.text.Editable r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            int r1 = r1.length()
            if (r1 >= r3) goto L_0x006e
            com.google.android.material.textfield.TextInputLayout r1 = r8.nameTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputLayout r1 = r8.nameTextInputLayout
            r1.setPasswordVisibilityToggleEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.nameTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputLayout r1 = r8.nameTextInputLayout
            r7 = 2131821411(0x7f110363, float:1.9275564E38)
            java.lang.String r7 = r8.getString(r7)
            r1.setError(r7)
            com.google.android.material.textfield.TextInputEditText r1 = r8.nameTextInputEditText
            r1.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r4, r6)
            goto L_0x003b
        L_0x006e:
            com.google.android.material.textfield.TextInputLayout r1 = r8.nameTextInputLayout
            r1.setPasswordVisibilityToggleEnabled(r6)
            com.google.android.material.textfield.TextInputLayout r1 = r8.nameTextInputLayout
            r1.setError(r0)
            com.google.android.material.textfield.TextInputEditText r1 = r8.nameTextInputEditText
            r1.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r6, r6)
            r1 = 1
        L_0x007e:
            com.google.android.material.textfield.TextInputEditText r7 = r8.surnameTextInputEditText
            android.text.Editable r7 = r7.getText()
            java.lang.String r7 = r7.toString()
            boolean r7 = r7.equalsIgnoreCase(r2)
            if (r7 == 0) goto L_0x00ab
            com.google.android.material.textfield.TextInputLayout r1 = r8.surnameTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.surnameTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputEditText r1 = r8.surnameTextInputEditText
            r1.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r4, r6)
            com.google.android.material.textfield.TextInputLayout r1 = r8.surnameTextInputLayout
            r7 = 2131821416(0x7f110368, float:1.9275575E38)
            java.lang.String r7 = r8.getString(r7)
            r1.setError(r7)
        L_0x00a9:
            r1 = 0
            goto L_0x00e6
        L_0x00ab:
            com.google.android.material.textfield.TextInputEditText r7 = r8.surnameTextInputEditText
            android.text.Editable r7 = r7.getText()
            java.lang.String r7 = r7.toString()
            int r7 = r7.length()
            if (r7 >= r3) goto L_0x00d7
            com.google.android.material.textfield.TextInputLayout r1 = r8.surnameTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.surnameTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputEditText r1 = r8.surnameTextInputEditText
            r1.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r4, r6)
            com.google.android.material.textfield.TextInputLayout r1 = r8.surnameTextInputLayout
            r7 = 2131821417(0x7f110369, float:1.9275577E38)
            java.lang.String r7 = r8.getString(r7)
            r1.setError(r7)
            goto L_0x00a9
        L_0x00d7:
            com.google.android.material.textfield.TextInputLayout r7 = r8.surnameTextInputLayout
            r7.setError(r0)
            com.google.android.material.textfield.TextInputEditText r7 = r8.surnameTextInputEditText
            r7.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r6, r6)
            com.google.android.material.textfield.TextInputLayout r7 = r8.surnameTextInputLayout
            r7.setErrorEnabled(r6)
        L_0x00e6:
            com.google.android.material.textfield.TextInputEditText r7 = r8.phoneTextInputEditText
            android.text.Editable r7 = r7.getText()
            java.lang.String r7 = r7.toString()
            boolean r7 = r7.equalsIgnoreCase(r2)
            if (r7 == 0) goto L_0x0113
            com.google.android.material.textfield.TextInputLayout r1 = r8.phoneTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.phoneTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputLayout r1 = r8.phoneTextInputLayout
            r7 = 2131821412(0x7f110364, float:1.9275566E38)
            java.lang.String r7 = r8.getString(r7)
            r1.setError(r7)
            com.google.android.material.textfield.TextInputEditText r1 = r8.phoneTextInputEditText
            r1.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r4, r6)
        L_0x0111:
            r1 = 0
            goto L_0x014e
        L_0x0113:
            com.google.android.material.textfield.TextInputEditText r7 = r8.phoneTextInputEditText
            android.text.Editable r7 = r7.getText()
            java.lang.String r7 = r7.toString()
            int r7 = r7.length()
            if (r7 >= r3) goto L_0x013f
            com.google.android.material.textfield.TextInputLayout r1 = r8.phoneTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.phoneTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputLayout r1 = r8.phoneTextInputLayout
            r7 = 2131821413(0x7f110365, float:1.9275568E38)
            java.lang.String r7 = r8.getString(r7)
            r1.setError(r7)
            com.google.android.material.textfield.TextInputEditText r1 = r8.phoneTextInputEditText
            r1.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r4, r6)
            goto L_0x0111
        L_0x013f:
            com.google.android.material.textfield.TextInputLayout r7 = r8.phoneTextInputLayout
            r7.setError(r0)
            com.google.android.material.textfield.TextInputLayout r7 = r8.phoneTextInputLayout
            r7.setErrorEnabled(r6)
            com.google.android.material.textfield.TextInputEditText r7 = r8.phoneTextInputEditText
            r7.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r6, r6)
        L_0x014e:
            com.google.android.material.textfield.TextInputEditText r7 = r8.countryTextInputEditText
            android.text.Editable r7 = r7.getText()
            java.lang.String r7 = r7.toString()
            boolean r7 = r7.equalsIgnoreCase(r2)
            if (r7 == 0) goto L_0x0176
            com.google.android.material.textfield.TextInputLayout r1 = r8.countryTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.countryTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputLayout r1 = r8.countryTextInputLayout
            r7 = 2131821407(0x7f11035f, float:1.9275556E38)
            java.lang.String r7 = r8.getString(r7)
            r1.setError(r7)
            r1 = 0
            goto L_0x0180
        L_0x0176:
            com.google.android.material.textfield.TextInputLayout r7 = r8.countryTextInputLayout
            r7.setError(r0)
            com.google.android.material.textfield.TextInputLayout r7 = r8.countryTextInputLayout
            r7.setErrorEnabled(r6)
        L_0x0180:
            com.google.android.material.textfield.TextInputLayout r7 = r8.stateTextInputLayout
            int r7 = r7.getVisibility()
            if (r7 != 0) goto L_0x01b0
            com.google.android.material.textfield.TextInputEditText r7 = r8.stateTextInputEditText
            android.text.Editable r7 = r7.getText()
            java.lang.String r7 = r7.toString()
            boolean r7 = r7.equalsIgnoreCase(r2)
            if (r7 == 0) goto L_0x01b0
            com.google.android.material.textfield.TextInputLayout r1 = r8.stateTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.stateTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputLayout r1 = r8.stateTextInputLayout
            r7 = 2131821405(0x7f11035d, float:1.9275552E38)
            java.lang.String r7 = r8.getString(r7)
            r1.setError(r7)
            r1 = 0
            goto L_0x01ba
        L_0x01b0:
            com.google.android.material.textfield.TextInputLayout r7 = r8.stateTextInputLayout
            r7.setError(r0)
            com.google.android.material.textfield.TextInputLayout r7 = r8.stateTextInputLayout
            r7.setErrorEnabled(r6)
        L_0x01ba:
            com.google.android.material.textfield.TextInputEditText r7 = r8.districtTextInputEditText
            int r7 = r7.getVisibility()
            if (r7 != 0) goto L_0x01ea
            com.google.android.material.textfield.TextInputEditText r7 = r8.districtTextInputEditText
            android.text.Editable r7 = r7.getText()
            java.lang.String r7 = r7.toString()
            boolean r7 = r7.equalsIgnoreCase(r2)
            if (r7 == 0) goto L_0x01ea
            com.google.android.material.textfield.TextInputLayout r1 = r8.districtTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.districtTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputLayout r1 = r8.districtTextInputLayout
            r7 = 2131821408(0x7f110360, float:1.9275558E38)
            java.lang.String r7 = r8.getString(r7)
            r1.setError(r7)
            r1 = 0
            goto L_0x01f4
        L_0x01ea:
            com.google.android.material.textfield.TextInputLayout r7 = r8.districtTextInputLayout
            r7.setError(r0)
            com.google.android.material.textfield.TextInputLayout r7 = r8.districtTextInputLayout
            r7.setErrorEnabled(r6)
        L_0x01f4:
            com.google.android.material.textfield.TextInputEditText r7 = r8.addressTextInputEditText
            android.text.Editable r7 = r7.getText()
            java.lang.String r7 = r7.toString()
            boolean r7 = r7.equalsIgnoreCase(r2)
            if (r7 == 0) goto L_0x0221
            com.google.android.material.textfield.TextInputLayout r1 = r8.addressTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.addressTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputEditText r1 = r8.addressTextInputEditText
            r1.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r4, r6)
            com.google.android.material.textfield.TextInputLayout r1 = r8.addressTextInputLayout
            r7 = 2131821401(0x7f110359, float:1.9275544E38)
            java.lang.String r7 = r8.getString(r7)
            r1.setError(r7)
        L_0x021f:
            r1 = 0
            goto L_0x025c
        L_0x0221:
            com.google.android.material.textfield.TextInputEditText r7 = r8.addressTextInputEditText
            android.text.Editable r7 = r7.getText()
            java.lang.String r7 = r7.toString()
            int r7 = r7.length()
            if (r7 >= r3) goto L_0x024d
            com.google.android.material.textfield.TextInputLayout r1 = r8.addressTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.addressTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputEditText r1 = r8.addressTextInputEditText
            r1.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r4, r6)
            com.google.android.material.textfield.TextInputLayout r1 = r8.addressTextInputLayout
            r7 = 2131821404(0x7f11035c, float:1.927555E38)
            java.lang.String r7 = r8.getString(r7)
            r1.setError(r7)
            goto L_0x021f
        L_0x024d:
            com.google.android.material.textfield.TextInputLayout r7 = r8.addressTextInputLayout
            r7.setError(r0)
            com.google.android.material.textfield.TextInputEditText r7 = r8.addressTextInputEditText
            r7.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r6, r6)
            com.google.android.material.textfield.TextInputLayout r7 = r8.addressTextInputLayout
            r7.setErrorEnabled(r6)
        L_0x025c:
            com.google.android.material.textfield.TextInputEditText r7 = r8.postalTextInputEditText
            android.text.Editable r7 = r7.getText()
            java.lang.String r7 = r7.toString()
            boolean r7 = r7.equalsIgnoreCase(r2)
            if (r7 == 0) goto L_0x0289
            com.google.android.material.textfield.TextInputLayout r1 = r8.postalTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.postalTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputLayout r1 = r8.postalTextInputLayout
            r3 = 2131821414(0x7f110366, float:1.927557E38)
            java.lang.String r3 = r8.getString(r3)
            r1.setError(r3)
            com.google.android.material.textfield.TextInputEditText r1 = r8.postalTextInputEditText
            r1.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r4, r6)
        L_0x0287:
            r1 = 0
            goto L_0x02c4
        L_0x0289:
            com.google.android.material.textfield.TextInputEditText r7 = r8.postalTextInputEditText
            android.text.Editable r7 = r7.getText()
            java.lang.String r7 = r7.toString()
            int r7 = r7.length()
            if (r7 >= r3) goto L_0x02b5
            com.google.android.material.textfield.TextInputLayout r1 = r8.postalTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.postalTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputLayout r1 = r8.postalTextInputLayout
            r3 = 2131821415(0x7f110367, float:1.9275573E38)
            java.lang.String r3 = r8.getString(r3)
            r1.setError(r3)
            com.google.android.material.textfield.TextInputEditText r1 = r8.postalTextInputEditText
            r1.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r4, r6)
            goto L_0x0287
        L_0x02b5:
            com.google.android.material.textfield.TextInputLayout r3 = r8.postalTextInputLayout
            r3.setError(r0)
            com.google.android.material.textfield.TextInputEditText r3 = r8.postalTextInputEditText
            r3.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r6, r6)
            com.google.android.material.textfield.TextInputLayout r3 = r8.postalTextInputLayout
            r3.setErrorEnabled(r6)
        L_0x02c4:
            com.google.android.material.textfield.TextInputEditText r3 = r8.titleTextInputEditText
            android.text.Editable r3 = r3.getText()
            java.lang.String r3 = r3.toString()
            boolean r3 = r3.equalsIgnoreCase(r2)
            if (r3 == 0) goto L_0x02f1
            com.google.android.material.textfield.TextInputLayout r1 = r8.titleTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.titleTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputLayout r1 = r8.titleTextInputLayout
            r3 = 2131821402(0x7f11035a, float:1.9275546E38)
            java.lang.String r3 = r8.getString(r3)
            r1.setError(r3)
            com.google.android.material.textfield.TextInputEditText r1 = r8.titleTextInputEditText
            r1.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r4, r6)
        L_0x02ef:
            r1 = 0
            goto L_0x032d
        L_0x02f1:
            com.google.android.material.textfield.TextInputEditText r3 = r8.titleTextInputEditText
            android.text.Editable r3 = r3.getText()
            java.lang.String r3 = r3.toString()
            int r3 = r3.length()
            r7 = 2
            if (r3 >= r7) goto L_0x031e
            com.google.android.material.textfield.TextInputLayout r1 = r8.titleTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.titleTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputLayout r1 = r8.titleTextInputLayout
            r3 = 2131821403(0x7f11035b, float:1.9275548E38)
            java.lang.String r3 = r8.getString(r3)
            r1.setError(r3)
            com.google.android.material.textfield.TextInputEditText r1 = r8.titleTextInputEditText
            r1.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r4, r6)
            goto L_0x02ef
        L_0x031e:
            com.google.android.material.textfield.TextInputEditText r3 = r8.titleTextInputEditText
            r3.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r6, r6)
            com.google.android.material.textfield.TextInputLayout r3 = r8.titleTextInputLayout
            r3.setError(r0)
            com.google.android.material.textfield.TextInputLayout r3 = r8.titleTextInputLayout
            r3.setErrorEnabled(r6)
        L_0x032d:
            boolean r3 = r8.isEdit
            if (r3 == 0) goto L_0x0336
            com.mobiroller.models.user.UserShippingAddressModel r3 = r8.userShippingAddressModel
            if (r3 == 0) goto L_0x0336
            return r1
        L_0x0336:
            boolean r3 = r8.isShipping
            if (r3 == 0) goto L_0x0343
            android.widget.CheckBox r3 = r8.saveAsBilling
            boolean r3 = r3.isChecked()
            if (r3 != 0) goto L_0x0343
            return r1
        L_0x0343:
            androidx.constraintlayout.widget.ConstraintLayout r3 = r8.billingLayout
            int r3 = r3.getVisibility()
            if (r3 != 0) goto L_0x0455
            androidx.appcompat.widget.AppCompatRadioButton r3 = r8.corporate
            boolean r3 = r3.isChecked()
            if (r3 == 0) goto L_0x0407
            com.google.android.material.textfield.TextInputEditText r3 = r8.companyNameTextInputEditText
            android.text.Editable r3 = r3.getText()
            java.lang.String r3 = r3.toString()
            boolean r3 = r3.equalsIgnoreCase(r2)
            if (r3 == 0) goto L_0x0380
            com.google.android.material.textfield.TextInputLayout r1 = r8.companyNameTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.companyNameTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputEditText r1 = r8.companyNameTextInputEditText
            r1.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r4, r6)
            com.google.android.material.textfield.TextInputLayout r1 = r8.companyNameTextInputLayout
            r3 = 2131821406(0x7f11035e, float:1.9275554E38)
            java.lang.String r3 = r8.getString(r3)
            r1.setError(r3)
            r1 = 0
            goto L_0x038f
        L_0x0380:
            com.google.android.material.textfield.TextInputLayout r3 = r8.companyNameTextInputLayout
            r3.setError(r0)
            com.google.android.material.textfield.TextInputEditText r3 = r8.companyNameTextInputEditText
            r3.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r6, r6)
            com.google.android.material.textfield.TextInputLayout r3 = r8.companyNameTextInputLayout
            r3.setErrorEnabled(r6)
        L_0x038f:
            com.google.android.material.textfield.TextInputEditText r3 = r8.taxAdminTextInputEditText
            android.text.Editable r3 = r3.getText()
            java.lang.String r3 = r3.toString()
            boolean r3 = r3.equalsIgnoreCase(r2)
            if (r3 == 0) goto L_0x03bc
            com.google.android.material.textfield.TextInputLayout r1 = r8.taxAdminTextInputLayout
            r1.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r1 = r8.taxAdminTextInputEditText
            r8.setFocusView(r1)
            com.google.android.material.textfield.TextInputEditText r1 = r8.taxAdminTextInputEditText
            r1.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r4, r6)
            com.google.android.material.textfield.TextInputLayout r1 = r8.taxAdminTextInputLayout
            r3 = 2131821418(0x7f11036a, float:1.9275579E38)
            java.lang.String r3 = r8.getString(r3)
            r1.setError(r3)
            r1 = 0
            goto L_0x03cb
        L_0x03bc:
            com.google.android.material.textfield.TextInputLayout r3 = r8.taxAdminTextInputLayout
            r3.setError(r0)
            com.google.android.material.textfield.TextInputEditText r3 = r8.taxAdminTextInputEditText
            r3.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r6, r6)
            com.google.android.material.textfield.TextInputLayout r3 = r8.taxAdminTextInputLayout
            r3.setErrorEnabled(r6)
        L_0x03cb:
            com.google.android.material.textfield.TextInputEditText r3 = r8.taxNumberTextInputEditText
            android.text.Editable r3 = r3.getText()
            java.lang.String r3 = r3.toString()
            boolean r2 = r3.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x03f7
            com.google.android.material.textfield.TextInputLayout r0 = r8.taxNumberTextInputLayout
            r0.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r0 = r8.taxNumberTextInputEditText
            r8.setFocusView(r0)
            com.google.android.material.textfield.TextInputEditText r0 = r8.taxNumberTextInputEditText
            r0.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r4, r6)
            com.google.android.material.textfield.TextInputLayout r0 = r8.taxNumberTextInputLayout
            r1 = 2131821419(0x7f11036b, float:1.927558E38)
            java.lang.String r1 = r8.getString(r1)
            r0.setError(r1)
            goto L_0x0454
        L_0x03f7:
            com.google.android.material.textfield.TextInputLayout r2 = r8.taxNumberTextInputLayout
            r2.setError(r0)
            com.google.android.material.textfield.TextInputEditText r0 = r8.taxNumberTextInputEditText
            r0.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r6, r6)
            com.google.android.material.textfield.TextInputLayout r0 = r8.taxNumberTextInputLayout
            r0.setErrorEnabled(r6)
            goto L_0x0455
        L_0x0407:
            com.google.android.material.textfield.TextInputEditText r3 = r8.identityNumberTextInputEditText
            android.text.Editable r3 = r3.getText()
            java.lang.String r3 = r3.toString()
            boolean r2 = r3.equalsIgnoreCase(r2)
            if (r2 != 0) goto L_0x0439
            com.google.android.material.textfield.TextInputEditText r2 = r8.identityNumberTextInputEditText
            android.text.Editable r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            int r2 = r2.length()
            r3 = 5
            if (r2 >= r3) goto L_0x0429
            goto L_0x0439
        L_0x0429:
            com.google.android.material.textfield.TextInputEditText r2 = r8.identityNumberTextInputEditText
            r2.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r6, r6)
            com.google.android.material.textfield.TextInputLayout r2 = r8.identityNumberTextInputLayout
            r2.setError(r0)
            com.google.android.material.textfield.TextInputLayout r0 = r8.identityNumberTextInputLayout
            r0.setErrorEnabled(r6)
            goto L_0x0455
        L_0x0439:
            com.google.android.material.textfield.TextInputLayout r0 = r8.identityNumberTextInputLayout
            r0.setErrorEnabled(r5)
            com.google.android.material.textfield.TextInputEditText r0 = r8.identityNumberTextInputEditText
            r8.setFocusView(r0)
            com.google.android.material.textfield.TextInputEditText r0 = r8.identityNumberTextInputEditText
            r0.setCompoundDrawablesWithIntrinsicBounds(r6, r6, r4, r6)
            com.google.android.material.textfield.TextInputLayout r0 = r8.identityNumberTextInputLayout
            r1 = 2131821409(0x7f110361, float:1.927556E38)
            java.lang.String r1 = r8.getString(r1)
            r0.setError(r1)
        L_0x0454:
            r1 = 0
        L_0x0455:
            android.view.View r0 = r8.focusView
            if (r0 == 0) goto L_0x045c
            r0.requestFocus()
        L_0x045c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.fragments.user.AddressBottomSheetDialogFragment.validateForm():boolean");
    }

    /* access modifiers changed from: 0000 */
    public void editAddress() {
        if (!UtilManager.networkHelper().isConnected()) {
            DialogUtil.showNoConnectionInfo(getContext());
            return;
        }
        String str = "Corporate";
        String str2 = "Individual";
        String str3 = " ";
        if (this.userBillingAddressModel != null) {
            EditBillingAddress editBillingAddress = new EditBillingAddress();
            editBillingAddress.apiKey = getString(R.string.applyze_api_key);
            editBillingAddress.appKey = getString(R.string.applyze_app_key);
            editBillingAddress.addressLine = this.addressTextInputEditText.getText().toString();
            editBillingAddress.city = this.districtTextInputEditText.getText().toString();
            editBillingAddress.country = this.countryTextInputEditText.getText().toString();
            editBillingAddress.state = this.stateTextInputEditText.getText().toString();
            editBillingAddress.zipCode = this.postalTextInputEditText.getText().toString();
            editBillingAddress.phone = this.phoneTextInputEditText.getText().toString();
            StringBuilder sb = new StringBuilder();
            sb.append(this.nameTextInputEditText.getText().toString());
            sb.append(str3);
            sb.append(this.surnameTextInputEditText.getText().toString());
            editBillingAddress.nameSurname = sb.toString();
            editBillingAddress.title = this.titleTextInputEditText.getText().toString();
            editBillingAddress.f2191id = this.userBillingAddressModel.f2189id;
            if (this.corporate.isChecked()) {
                editBillingAddress.type = str;
                editBillingAddress.companyName = this.companyNameTextInputEditText.getText().toString();
                editBillingAddress.taxNumber = this.taxNumberTextInputEditText.getText().toString();
                editBillingAddress.taxOffice = this.taxAdminTextInputEditText.getText().toString();
            } else {
                editBillingAddress.type = str2;
                editBillingAddress.identityNumber = this.identityNumberTextInputEditText.getText().toString();
            }
            this.progressViewHelper.show();
            this.requestHelper.getApplyzeUserAPIService().editBillingAddress(UserHelper.getUserId(), editBillingAddress).enqueue(new Callback<UserBillingAddressModel>() {
                public void onResponse(Call<UserBillingAddressModel> call, Response<UserBillingAddressModel> response) {
                    if (!AddressBottomSheetDialogFragment.this.getActivity().isFinishing() && AddressBottomSheetDialogFragment.this.progressViewHelper.isShowing()) {
                        AddressBottomSheetDialogFragment.this.progressViewHelper.dismiss();
                    }
                    if (response.isSuccessful()) {
                        Toast.makeText(AddressBottomSheetDialogFragment.this.getContext(), R.string.user_my_address_saved_toast_message, 0).show();
                        EventBus.getDefault().post(new EditBillingAddressEvent((UserBillingAddressModel) response.body()));
                        AddressBottomSheetDialogFragment.this.dismiss();
                    } else if (response.code() == 400) {
                        try {
                            Toast.makeText(AddressBottomSheetDialogFragment.this.getContext(), ErrorUtils.parseError(response).message(), 0).show();
                        } catch (Exception unused) {
                            ErrorUtils.showErrorToast(AddressBottomSheetDialogFragment.this.getContext());
                        }
                    } else {
                        ErrorUtils.showErrorToast(AddressBottomSheetDialogFragment.this.getContext());
                        AddressBottomSheetDialogFragment.this.dismiss();
                    }
                }

                public void onFailure(Call<UserBillingAddressModel> call, Throwable th) {
                    if (!AddressBottomSheetDialogFragment.this.getActivity().isFinishing() && AddressBottomSheetDialogFragment.this.progressViewHelper.isShowing()) {
                        AddressBottomSheetDialogFragment.this.progressViewHelper.dismiss();
                    }
                    ErrorUtils.showErrorToast(AddressBottomSheetDialogFragment.this.getContext());
                }
            });
        } else {
            EditShippingAddress editShippingAddress = new EditShippingAddress();
            editShippingAddress.apiKey = getString(R.string.applyze_api_key);
            editShippingAddress.appKey = getString(R.string.applyze_app_key);
            editShippingAddress.addressLine = this.addressTextInputEditText.getText().toString();
            editShippingAddress.city = this.districtTextInputEditText.getText().toString();
            editShippingAddress.country = this.countryTextInputEditText.getText().toString();
            editShippingAddress.state = this.stateTextInputEditText.getText().toString();
            editShippingAddress.zipCode = this.postalTextInputEditText.getText().toString();
            editShippingAddress.phone = this.phoneTextInputEditText.getText().toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.nameTextInputEditText.getText().toString());
            sb2.append(str3);
            sb2.append(this.surnameTextInputEditText.getText().toString());
            editShippingAddress.nameSurname = sb2.toString();
            editShippingAddress.title = this.titleTextInputEditText.getText().toString();
            editShippingAddress.f2192id = this.userShippingAddressModel.f2189id;
            if (this.corporate.isChecked()) {
                editShippingAddress.type = str;
                editShippingAddress.companyName = this.companyNameTextInputEditText.getText().toString();
                editShippingAddress.taxNumber = this.taxNumberTextInputEditText.getText().toString();
                editShippingAddress.taxOffice = this.taxAdminTextInputEditText.getText().toString();
            } else {
                editShippingAddress.type = str2;
                editShippingAddress.identityNumber = this.identityNumberTextInputEditText.getText().toString();
            }
            this.progressViewHelper.show();
            this.requestHelper.getApplyzeUserAPIService().editShippingAddress(UserHelper.getUserId(), editShippingAddress).enqueue(new Callback<UserShippingAddressModel>() {
                public void onResponse(Call<UserShippingAddressModel> call, Response<UserShippingAddressModel> response) {
                    if (!AddressBottomSheetDialogFragment.this.getActivity().isFinishing() && AddressBottomSheetDialogFragment.this.progressViewHelper.isShowing()) {
                        AddressBottomSheetDialogFragment.this.progressViewHelper.dismiss();
                    }
                    if (response.isSuccessful()) {
                        Toast.makeText(AddressBottomSheetDialogFragment.this.getContext(), R.string.user_my_address_saved_toast_message, 0).show();
                        EventBus.getDefault().post(new EditShippingAddressEvent((UserShippingAddressModel) response.body()));
                        AddressBottomSheetDialogFragment.this.dismiss();
                    } else if (response.code() == 400) {
                        try {
                            Toast.makeText(AddressBottomSheetDialogFragment.this.getContext(), ErrorUtils.parseError(response).message(), 0).show();
                        } catch (Exception unused) {
                            ErrorUtils.showErrorToast(AddressBottomSheetDialogFragment.this.getContext());
                        }
                    } else {
                        ErrorUtils.showErrorToast(AddressBottomSheetDialogFragment.this.getActivity());
                        AddressBottomSheetDialogFragment.this.dismiss();
                    }
                }

                public void onFailure(Call<UserShippingAddressModel> call, Throwable th) {
                    if (!AddressBottomSheetDialogFragment.this.getActivity().isFinishing() && AddressBottomSheetDialogFragment.this.progressViewHelper.isShowing()) {
                        AddressBottomSheetDialogFragment.this.progressViewHelper.dismiss();
                    }
                    ErrorUtils.showErrorToast(AddressBottomSheetDialogFragment.this.getActivity());
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public void saveAddress() {
        if (!UtilManager.networkHelper().isConnected()) {
            DialogUtil.showNoConnectionInfo(getContext());
            return;
        }
        String str = "Corporate";
        String str2 = "Individual";
        String str3 = " ";
        if (this.isBilling) {
            AddBillingAddress addBillingAddress = new AddBillingAddress();
            addBillingAddress.apiKey = getString(R.string.applyze_api_key);
            addBillingAddress.appKey = getString(R.string.applyze_app_key);
            addBillingAddress.addressLine = this.addressTextInputEditText.getText().toString();
            addBillingAddress.city = this.districtTextInputEditText.getText().toString();
            addBillingAddress.country = this.countryTextInputEditText.getText().toString();
            addBillingAddress.state = this.stateTextInputEditText.getText().toString();
            addBillingAddress.zipCode = this.postalTextInputEditText.getText().toString();
            addBillingAddress.phone = this.phoneTextInputEditText.getText().toString();
            StringBuilder sb = new StringBuilder();
            sb.append(this.nameTextInputEditText.getText().toString());
            sb.append(str3);
            sb.append(this.surnameTextInputEditText.getText().toString());
            addBillingAddress.nameSurname = sb.toString();
            addBillingAddress.title = this.titleTextInputEditText.getText().toString();
            if (this.corporate.isChecked()) {
                addBillingAddress.type = str;
                addBillingAddress.companyName = this.companyNameTextInputEditText.getText().toString();
                addBillingAddress.taxNumber = this.taxNumberTextInputEditText.getText().toString();
                addBillingAddress.taxOffice = this.taxAdminTextInputEditText.getText().toString();
            } else {
                addBillingAddress.type = str2;
                addBillingAddress.identityNumber = this.identityNumberTextInputEditText.getText().toString();
            }
            this.progressViewHelper.show();
            this.requestHelper.getApplyzeUserAPIService().addBillingAddress(UserHelper.getUserId(), addBillingAddress).enqueue(new Callback<UserBillingAddressModel>() {
                public void onResponse(Call<UserBillingAddressModel> call, Response<UserBillingAddressModel> response) {
                    if (!AddressBottomSheetDialogFragment.this.getActivity().isFinishing() && AddressBottomSheetDialogFragment.this.progressViewHelper.isShowing()) {
                        AddressBottomSheetDialogFragment.this.progressViewHelper.dismiss();
                    }
                    if (response.isSuccessful()) {
                        Toast.makeText(AddressBottomSheetDialogFragment.this.getContext(), R.string.user_my_address_saved_toast_message, 0).show();
                        EventBus.getDefault().post(new NewBillingAddressEvent((UserBillingAddressModel) response.body()));
                        AddressBottomSheetDialogFragment.this.dismiss();
                    } else if (response.code() == 400) {
                        try {
                            Toast.makeText(AddressBottomSheetDialogFragment.this.getContext(), ErrorUtils.parseError(response).message(), 0).show();
                        } catch (Exception unused) {
                            ErrorUtils.showErrorToast(AddressBottomSheetDialogFragment.this.getContext());
                        }
                    } else {
                        ErrorUtils.showErrorToast(AddressBottomSheetDialogFragment.this.getContext());
                        AddressBottomSheetDialogFragment.this.dismiss();
                    }
                }

                public void onFailure(Call<UserBillingAddressModel> call, Throwable th) {
                    if (!AddressBottomSheetDialogFragment.this.getActivity().isFinishing() && AddressBottomSheetDialogFragment.this.progressViewHelper.isShowing()) {
                        AddressBottomSheetDialogFragment.this.progressViewHelper.dismiss();
                    }
                    ErrorUtils.showErrorToast(AddressBottomSheetDialogFragment.this.getContext());
                }
            });
        } else {
            AddShippingAddress addShippingAddress = new AddShippingAddress();
            addShippingAddress.apiKey = getString(R.string.applyze_api_key);
            addShippingAddress.appKey = getString(R.string.applyze_app_key);
            addShippingAddress.addressLine = this.addressTextInputEditText.getText().toString();
            addShippingAddress.city = this.districtTextInputEditText.getText().toString();
            addShippingAddress.country = this.countryTextInputEditText.getText().toString();
            addShippingAddress.state = this.stateTextInputEditText.getText().toString();
            addShippingAddress.zipCode = this.postalTextInputEditText.getText().toString();
            addShippingAddress.phone = this.phoneTextInputEditText.getText().toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.nameTextInputEditText.getText().toString());
            sb2.append(str3);
            sb2.append(this.surnameTextInputEditText.getText().toString());
            addShippingAddress.nameSurname = sb2.toString();
            addShippingAddress.title = this.titleTextInputEditText.getText().toString();
            addShippingAddress.addBoth = this.saveAsBilling.isChecked();
            if (this.corporate.isChecked()) {
                addShippingAddress.type = str;
                addShippingAddress.companyName = this.companyNameTextInputEditText.getText().toString();
                addShippingAddress.taxNumber = this.taxNumberTextInputEditText.getText().toString();
                addShippingAddress.taxOffice = this.taxAdminTextInputEditText.getText().toString();
            } else {
                addShippingAddress.type = str2;
                addShippingAddress.identityNumber = this.identityNumberTextInputEditText.getText().toString();
            }
            this.progressViewHelper.show();
            if (addShippingAddress.addBoth) {
                this.requestHelper.getApplyzeUserAPIService().addAddress(UserHelper.getUserId(), addShippingAddress).enqueue(new Callback<DefaultAddressList>() {
                    public void onResponse(Call<DefaultAddressList> call, Response<DefaultAddressList> response) {
                        if (!AddressBottomSheetDialogFragment.this.getActivity().isFinishing() && AddressBottomSheetDialogFragment.this.progressViewHelper.isShowing()) {
                            AddressBottomSheetDialogFragment.this.progressViewHelper.dismiss();
                        }
                        if (response.isSuccessful()) {
                            Toast.makeText(AddressBottomSheetDialogFragment.this.getContext(), R.string.user_my_address_saved_toast_message, 0).show();
                            EventBus.getDefault().post(new NewShippingAddressEvent(((DefaultAddressList) response.body()).shippingAddress));
                            EventBus.getDefault().post(new NewBillingAddressEvent(((DefaultAddressList) response.body()).billingAddress));
                            AddressBottomSheetDialogFragment.this.dismiss();
                        } else if (response.code() == 400) {
                            try {
                                Toast.makeText(AddressBottomSheetDialogFragment.this.getContext(), ErrorUtils.parseError(response).message(), 0).show();
                            } catch (Exception unused) {
                                ErrorUtils.showErrorToast(AddressBottomSheetDialogFragment.this.getContext());
                            }
                        } else {
                            ErrorUtils.showErrorToast(AddressBottomSheetDialogFragment.this.getContext());
                            AddressBottomSheetDialogFragment.this.dismiss();
                        }
                    }

                    public void onFailure(Call<DefaultAddressList> call, Throwable th) {
                        if (!AddressBottomSheetDialogFragment.this.getActivity().isFinishing() && AddressBottomSheetDialogFragment.this.progressViewHelper.isShowing()) {
                            AddressBottomSheetDialogFragment.this.progressViewHelper.dismiss();
                        }
                        ErrorUtils.showErrorToast(AddressBottomSheetDialogFragment.this.getContext());
                    }
                });
            } else {
                this.requestHelper.getApplyzeUserAPIService().addShippingAddress(UserHelper.getUserId(), addShippingAddress).enqueue(new Callback<UserShippingAddressModel>() {
                    public void onResponse(Call<UserShippingAddressModel> call, Response<UserShippingAddressModel> response) {
                        if (!AddressBottomSheetDialogFragment.this.getActivity().isFinishing() && AddressBottomSheetDialogFragment.this.progressViewHelper.isShowing()) {
                            AddressBottomSheetDialogFragment.this.progressViewHelper.dismiss();
                        }
                        if (response.isSuccessful()) {
                            Toast.makeText(AddressBottomSheetDialogFragment.this.getContext(), R.string.user_my_address_saved_toast_message, 0).show();
                            EventBus.getDefault().post(new NewShippingAddressEvent((UserShippingAddressModel) response.body()));
                            AddressBottomSheetDialogFragment.this.dismiss();
                        } else if (response.code() == 400) {
                            try {
                                Toast.makeText(AddressBottomSheetDialogFragment.this.getContext(), ErrorUtils.parseError(response).message(), 0).show();
                            } catch (Exception unused) {
                                ErrorUtils.showErrorToast(AddressBottomSheetDialogFragment.this.getContext());
                            }
                        } else {
                            ErrorUtils.showErrorToast(AddressBottomSheetDialogFragment.this.getContext());
                            AddressBottomSheetDialogFragment.this.dismiss();
                        }
                    }

                    public void onFailure(Call<UserShippingAddressModel> call, Throwable th) {
                        if (!AddressBottomSheetDialogFragment.this.getActivity().isFinishing() && AddressBottomSheetDialogFragment.this.progressViewHelper.isShowing()) {
                            AddressBottomSheetDialogFragment.this.progressViewHelper.dismiss();
                        }
                        ErrorUtils.showErrorToast(AddressBottomSheetDialogFragment.this.getContext());
                    }
                });
            }
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    private void setFocusView(View view) {
        if (this.focusView == null) {
            this.focusView = view;
        }
    }

    /* access modifiers changed from: 0000 */
    public void getCountries() {
        this.countriesCall = this.applyzeUserServiceInterface.getCountries();
        this.countriesCall.enqueue(new Callback<List<CountryModel>>() {
            public void onFailure(Call<List<CountryModel>> call, Throwable th) {
            }

            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                if (response.isSuccessful()) {
                    AddressBottomSheetDialogFragment.this.countryList = (List) response.body();
                    if (AddressBottomSheetDialogFragment.this.getActivity() == null) {
                    }
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void getStates(String str) {
        this.progressViewHelper.show();
        this.statesCall = this.applyzeUserServiceInterface.getStates(str);
        this.statesCall.enqueue(new Callback<List<CountryModel>>() {
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                if (!AddressBottomSheetDialogFragment.this.getActivity().isFinishing() && AddressBottomSheetDialogFragment.this.progressViewHelper.isShowing()) {
                    AddressBottomSheetDialogFragment.this.progressViewHelper.dismiss();
                }
                if (response.isSuccessful()) {
                    AddressBottomSheetDialogFragment.this.stateList = (List) response.body();
                    if (AddressBottomSheetDialogFragment.this.stateList == null || AddressBottomSheetDialogFragment.this.stateList.size() == 0) {
                        AddressBottomSheetDialogFragment.this.stateTextInputLayout.setVisibility(8);
                        AddressBottomSheetDialogFragment.this.districtTextInputEditText.setText(AddressBottomSheetDialogFragment.this.countrySelectedModel.name);
                        AddressBottomSheetDialogFragment.this.stateTextInputEditText.setText(AddressBottomSheetDialogFragment.this.countrySelectedModel.name);
                        AddressBottomSheetDialogFragment addressBottomSheetDialogFragment = AddressBottomSheetDialogFragment.this;
                        addressBottomSheetDialogFragment.stateSelectedModel = addressBottomSheetDialogFragment.countrySelectedModel;
                        AddressBottomSheetDialogFragment addressBottomSheetDialogFragment2 = AddressBottomSheetDialogFragment.this;
                        addressBottomSheetDialogFragment2.districtSelectedModel = addressBottomSheetDialogFragment2.countrySelectedModel;
                    } else {
                        AddressBottomSheetDialogFragment.this.stateTextInputLayout.setVisibility(0);
                        AddressBottomSheetDialogFragment.this.districtSelectedModel = null;
                        AddressBottomSheetDialogFragment.this.stateSelectedModel = null;
                    }
                    AddressBottomSheetDialogFragment.this.districtTextInputLayout.setVisibility(8);
                    if (AddressBottomSheetDialogFragment.this.getActivity() == null) {
                    }
                }
            }

            public void onFailure(Call<List<CountryModel>> call, Throwable th) {
                if (!AddressBottomSheetDialogFragment.this.getActivity().isFinishing() && AddressBottomSheetDialogFragment.this.progressViewHelper.isShowing()) {
                    AddressBottomSheetDialogFragment.this.progressViewHelper.dismiss();
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void getCities(String str) {
        this.districtCall = this.applyzeUserServiceInterface.getCities(str);
        this.progressViewHelper.show();
        this.districtCall.enqueue(new Callback<List<CountryModel>>() {
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                if (!AddressBottomSheetDialogFragment.this.getActivity().isFinishing() && AddressBottomSheetDialogFragment.this.progressViewHelper.isShowing()) {
                    AddressBottomSheetDialogFragment.this.progressViewHelper.dismiss();
                }
                if (response.isSuccessful()) {
                    AddressBottomSheetDialogFragment.this.districtList = (List) response.body();
                    if (!(AddressBottomSheetDialogFragment.this.districtList == null || AddressBottomSheetDialogFragment.this.districtList.size() == 0)) {
                        AddressBottomSheetDialogFragment.this.districtTextInputLayout.setVisibility(0);
                        if (AddressBottomSheetDialogFragment.this.districtList.size() == 1) {
                            AddressBottomSheetDialogFragment addressBottomSheetDialogFragment = AddressBottomSheetDialogFragment.this;
                            addressBottomSheetDialogFragment.districtSelectedModel = (CountryModel) addressBottomSheetDialogFragment.districtList.get(0);
                            AddressBottomSheetDialogFragment.this.districtSelectedPosition = 0;
                            AddressBottomSheetDialogFragment.this.districtTextInputEditText.setEnabled(true);
                            AddressBottomSheetDialogFragment.this.districtTextInputEditText.setText(AddressBottomSheetDialogFragment.this.districtSelectedModel.name);
                        }
                    }
                    if (AddressBottomSheetDialogFragment.this.getActivity() == null) {
                    }
                }
            }

            public void onFailure(Call<List<CountryModel>> call, Throwable th) {
                if (!AddressBottomSheetDialogFragment.this.getActivity().isFinishing() && AddressBottomSheetDialogFragment.this.progressViewHelper.isShowing()) {
                    AddressBottomSheetDialogFragment.this.progressViewHelper.dismiss();
                }
            }
        });
    }

    public void onStop() {
        super.onStop();
        Call<List<CountryModel>> call = this.countriesCall;
        if (call != null) {
            call.cancel();
        }
        Call<List<CountryModel>> call2 = this.districtCall;
        if (call2 != null) {
            call2.cancel();
        }
        Call<List<CountryModel>> call3 = this.statesCall;
        if (call3 != null) {
            call3.cancel();
        }
        if (!getActivity().isFinishing() && this.progressViewHelper.isShowing()) {
            this.progressViewHelper.dismiss();
        }
    }
}
