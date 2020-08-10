package com.mobiroller.fragments.ecommerce;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobiroller.DynamicConstants;
import com.mobiroller.adapters.user.UserPopupAddressAdapter;
import com.mobiroller.constants.UserConstants;
import com.mobiroller.enums.MobirollerDialogType;
import com.mobiroller.fragments.user.AddressBottomSheetDialogFragment;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.AddressContinueEvent;
import com.mobiroller.models.events.EditBillingAddressEvent;
import com.mobiroller.models.events.EditShippingAddressEvent;
import com.mobiroller.models.events.NewBillingAddressEvent;
import com.mobiroller.models.events.NewShippingAddressEvent;
import com.mobiroller.models.events.OrderAddressesEvent;
import com.mobiroller.models.events.ValidateStepEvent;
import com.mobiroller.models.user.BaseAddressModel;
import com.mobiroller.models.user.DefaultAddressList;
import com.mobiroller.models.user.UserBillingAddressModel;
import com.mobiroller.models.user.UserShippingAddressModel;
import com.mobiroller.serviceinterfaces.ApplyzeUserServiceInterface;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.util.validation.Validator;
import com.mobiroller.util.validation.exceptions.RequiredFieldException;
import com.mobiroller.views.custom.MobirollerDialog.Builder;
import com.mobiroller.views.custom.MobirollerDialog.DialogListCallback;
import com.mobiroller.views.custom.MobirollerTextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderChooseAddressFragment extends OrderFlowBaseFragment {
    @BindView(2131361857)
    MobirollerTextView actionAddNewBillingAddress;
    @BindView(2131361859)
    MobirollerTextView actionAddNewShippingAddress;
    private ApplyzeUserServiceInterface applyzeUserServiceInterface;
    @BindView(2131361983)
    TextView billingAddressDescription;
    @BindView(2131361984)
    MobirollerTextView billingAddressDescriptionFirst;
    @BindView(2131361986)
    TextView billingAddressTitle;
    @BindView(2131361990)
    ConstraintLayout billingContentLayout;
    @BindView(2131361993)
    ConstraintLayout billingEmptyLayout;
    @BindView(2131361996)
    ConstraintLayout billingLayout;
    @BindView(2131361997)
    ConstraintLayout billingTitleLayout;
    @BindView(2131362016)
    ConstraintLayout bottomLayout;
    @BindView(2131362202)
    FloatingActionButton continueButton;
    /* access modifiers changed from: private */
    public DefaultAddressList defaultAddressList = null;
    @BindView(2131362246)
    ConstraintLayout deliveryContentLayout;
    @BindView(2131362248)
    ConstraintLayout deliveryEmptyLayout;
    @BindView(2131362250)
    ConstraintLayout deliveryLayout;
    @BindView(2131362251)
    ConstraintLayout deliveryTitleLayout;
    private boolean isValid = false;
    @BindView(2131362859)
    MobirollerTextView otherBillingAddresses;
    @BindView(2131362860)
    MobirollerTextView otherShippingAddresses;
    /* access modifiers changed from: private */
    public ProgressViewHelper progressViewHelper;
    private RequestHelper requestHelper;
    private SharedPrefHelper sharedPrefHelper;
    @BindView(2131363126)
    MobirollerTextView shippingAddressDescription;
    @BindView(2131363127)
    MobirollerTextView shippingAddressDescriptionFirst;
    @BindView(2131363128)
    MobirollerTextView shippingAddressTitle;
    Unbinder unbinder;

    @OnTouch({2131362016, 2131362203, 2131363281})
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        this.requestHelper = new RequestHelper();
        this.applyzeUserServiceInterface = this.requestHelper.getApplyzeUserAPIService();
        getDefaultAddresses();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view;
        if (getView() != null) {
            view = getView();
        } else {
            view = layoutInflater.inflate(R.layout.layout_choose_address, viewGroup, false);
        }
        this.unbinder = ButterKnife.bind((Object) this, view);
        setContinueButton(false);
        if (this.defaultAddressList != null) {
            setupView();
        }
        return view;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    public boolean isValid() {
        try {
            if (Validator.validateForNulls(this.defaultAddressList, getActivity())) {
                return true;
            }
            return this.isValid;
        } catch (RequiredFieldException | ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            if (e instanceof RequiredFieldException) {
                ((RequiredFieldException) e).notifyUserWithToast(getContext());
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    @OnClick({2131361859, 2131361860})
    public void onClickNewShipping() {
        if (!DynamicConstants.MobiRoller_Stage) {
            AddressBottomSheetDialogFragment addressBottomSheetDialogFragment = new AddressBottomSheetDialogFragment();
            addressBottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), addressBottomSheetDialogFragment.getTag());
        }
    }

    @OnClick({2131361857, 2131361858})
    public void onClickNewBilling() {
        if (!DynamicConstants.MobiRoller_Stage) {
            AddressBottomSheetDialogFragment addressBottomSheetDialogFragment = new AddressBottomSheetDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(UserConstants.BUNDLE_EXTRA_USER_ADDRESS, UserConstants.BUNDLE_EXTRA_USER_BILLING_ADDRESS);
            addressBottomSheetDialogFragment.setArguments(bundle);
            addressBottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), addressBottomSheetDialogFragment.getTag());
        }
    }

    private void getDefaultAddresses() {
        if (DynamicConstants.MobiRoller_Stage) {
            this.defaultAddressList = new DefaultAddressList();
            this.defaultAddressList.billingAddress = UserBillingAddressModel.getFakeAddres();
            this.defaultAddressList.shippingAddress = UserShippingAddressModel.getFakeAddres();
            setupView();
            this.bottomLayout.setVisibility(0);
            return;
        }
        if (UtilManager.networkHelper().isConnected()) {
            if (!getActivity().isFinishing() && !this.progressViewHelper.isShowing()) {
                this.progressViewHelper.show();
            }
            this.applyzeUserServiceInterface.getDefaultAddresses(UserHelper.getUserId(), getString(R.string.applyze_api_key), getString(R.string.applyze_app_key)).enqueue(new Callback<DefaultAddressList>() {
                public void onResponse(Call<DefaultAddressList> call, Response<DefaultAddressList> response) {
                    if (!OrderChooseAddressFragment.this.getActivity().isFinishing() && OrderChooseAddressFragment.this.progressViewHelper.isShowing()) {
                        OrderChooseAddressFragment.this.progressViewHelper.dismiss();
                    }
                    OrderChooseAddressFragment.this.bottomLayout.setVisibility(0);
                    if (response.isSuccessful()) {
                        OrderChooseAddressFragment.this.defaultAddressList = (DefaultAddressList) response.body();
                        OrderChooseAddressFragment.this.setupView();
                        return;
                    }
                    ErrorUtils.showErrorToast(OrderChooseAddressFragment.this.getContext());
                }

                public void onFailure(Call<DefaultAddressList> call, Throwable th) {
                    if (!OrderChooseAddressFragment.this.getActivity().isFinishing() && OrderChooseAddressFragment.this.progressViewHelper.isShowing()) {
                        OrderChooseAddressFragment.this.progressViewHelper.dismiss();
                    }
                    ErrorUtils.showErrorToast(OrderChooseAddressFragment.this.getContext());
                }
            });
        } else {
            DialogUtil.showNoConnectionError(getActivity());
        }
    }

    @OnClick({2131361982})
    public void onClickBillingEdit() {
        if (!DynamicConstants.MobiRoller_Stage) {
            AddressBottomSheetDialogFragment addressBottomSheetDialogFragment = new AddressBottomSheetDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(UserConstants.BUNDLE_EXTRA_USER_ADDRESS_EDIT, true);
            bundle.putString(UserConstants.BUNDLE_EXTRA_USER_ADDRESS, UserConstants.BUNDLE_EXTRA_USER_BILLING_ADDRESS);
            bundle.putSerializable(UserConstants.BUNDLE_EXTRA_USER_ADDRESS_MODEL, this.defaultAddressList.billingAddress);
            addressBottomSheetDialogFragment.setArguments(bundle);
            addressBottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), addressBottomSheetDialogFragment.getTag());
        }
    }

    @OnClick({2131363125})
    public void onClickShippingEdit() {
        if (!DynamicConstants.MobiRoller_Stage) {
            AddressBottomSheetDialogFragment addressBottomSheetDialogFragment = new AddressBottomSheetDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(UserConstants.BUNDLE_EXTRA_USER_ADDRESS_EDIT, true);
            bundle.putString(UserConstants.BUNDLE_EXTRA_USER_ADDRESS, UserConstants.BUNDLE_EXTRA_USER_SHIPPING_ADDRESS);
            bundle.putSerializable(UserConstants.BUNDLE_EXTRA_USER_ADDRESS_MODEL, this.defaultAddressList.shippingAddress);
            addressBottomSheetDialogFragment.setArguments(bundle);
            addressBottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), addressBottomSheetDialogFragment.getTag());
        }
    }

    /* access modifiers changed from: private */
    public void setupView() {
        this.deliveryLayout.setVisibility(0);
        this.billingLayout.setVisibility(0);
        setBillingLayout();
        setShippingLayout();
        checkIsValid();
    }

    @Subscribe
    public void onPostNewBillingAddressEvent(NewBillingAddressEvent newBillingAddressEvent) {
        this.defaultAddressList.billingAddress = newBillingAddressEvent.addressModel;
        setBillingLayout();
        checkIsValid();
    }

    @Subscribe
    public void onPostNewShippingAddressEvent(NewShippingAddressEvent newShippingAddressEvent) {
        this.defaultAddressList.shippingAddress = newShippingAddressEvent.addressModel;
        setShippingLayout();
        checkIsValid();
    }

    @Subscribe
    public void onPostEditBillingAddressEvent(EditBillingAddressEvent editBillingAddressEvent) {
        this.defaultAddressList.billingAddress = editBillingAddressEvent.addressModel;
        setBillingLayout();
    }

    @Subscribe
    public void onPostEditShippingAddressEvent(EditShippingAddressEvent editShippingAddressEvent) {
        this.defaultAddressList.shippingAddress = editShippingAddressEvent.addressModel;
        setShippingLayout();
    }

    /* access modifiers changed from: private */
    public void setShippingLayout() {
        if (DynamicConstants.MobiRoller_Stage) {
            this.deliveryEmptyLayout.setVisibility(8);
            this.deliveryContentLayout.setVisibility(0);
            this.otherShippingAddresses.setVisibility(0);
            this.shippingAddressTitle.setText(getString(R.string.preview_e_commerce_shipping_address_title_sample));
            this.shippingAddressDescription.setText(getString(R.string.preview_e_commerce_shipping_address_sample));
            return;
        }
        if (this.defaultAddressList.shippingAddress != null) {
            this.deliveryEmptyLayout.setVisibility(8);
            this.deliveryContentLayout.setVisibility(0);
            this.otherShippingAddresses.setVisibility(0);
            this.shippingAddressTitle.setText(this.defaultAddressList.shippingAddress.title);
            this.shippingAddressDescriptionFirst.setText(this.defaultAddressList.shippingAddress.addressLine);
            this.shippingAddressDescription.setText(this.defaultAddressList.shippingAddress.getDescriptionArea());
            this.actionAddNewShippingAddress.setVisibility(0);
        } else {
            this.deliveryEmptyLayout.setVisibility(0);
            this.deliveryContentLayout.setVisibility(8);
            this.otherShippingAddresses.setVisibility(8);
            this.actionAddNewShippingAddress.setVisibility(8);
        }
    }

    /* access modifiers changed from: private */
    public void setBillingLayout() {
        if (DynamicConstants.MobiRoller_Stage) {
            this.billingEmptyLayout.setVisibility(8);
            this.billingContentLayout.setVisibility(0);
            this.otherBillingAddresses.setVisibility(0);
            this.billingAddressTitle.setText(getString(R.string.preview_e_commerce_billing_address_title_sample));
            this.billingAddressDescription.setText(getString(R.string.preview_e_commerce_billing_address_sample));
            return;
        }
        if (this.defaultAddressList.billingAddress != null) {
            this.billingEmptyLayout.setVisibility(8);
            this.billingContentLayout.setVisibility(0);
            this.otherBillingAddresses.setVisibility(0);
            this.billingAddressTitle.setText(this.defaultAddressList.billingAddress.title);
            this.billingAddressDescriptionFirst.setText(this.defaultAddressList.billingAddress.addressLine);
            this.billingAddressDescription.setText(this.defaultAddressList.billingAddress.getDescriptionArea());
            this.actionAddNewBillingAddress.setVisibility(0);
        } else {
            this.billingEmptyLayout.setVisibility(0);
            this.billingContentLayout.setVisibility(8);
            this.otherBillingAddresses.setVisibility(8);
            this.actionAddNewBillingAddress.setVisibility(8);
        }
    }

    /* access modifiers changed from: private */
    public void checkIsValid() {
        if (DynamicConstants.MobiRoller_Stage) {
            EventBus.getDefault().post(new OrderAddressesEvent(this.defaultAddressList.shippingAddress, this.defaultAddressList.billingAddress));
            setContinueButton(true);
            EventBus.getDefault().post(new ValidateStepEvent(1, true));
            return;
        }
        if (this.billingContentLayout.getVisibility() == 0 && this.deliveryContentLayout.getVisibility() == 0) {
            this.isValid = true;
        }
        if (this.isValid) {
            EventBus.getDefault().post(new OrderAddressesEvent(this.defaultAddressList.shippingAddress, this.defaultAddressList.billingAddress));
        }
        setContinueButton(this.isValid);
        EventBus.getDefault().post(new ValidateStepEvent(1, this.isValid));
    }

    private void getShippingAddressList() {
        if (!DynamicConstants.MobiRoller_Stage) {
            if (UtilManager.networkHelper().isConnected()) {
                this.progressViewHelper.show();
                this.applyzeUserServiceInterface.getShippingAddresses(UserHelper.getUserId(), getString(R.string.applyze_api_key), getString(R.string.applyze_app_key)).enqueue(new Callback<List<UserShippingAddressModel>>() {
                    public void onResponse(Call<List<UserShippingAddressModel>> call, Response<List<UserShippingAddressModel>> response) {
                        if (!OrderChooseAddressFragment.this.getActivity().isFinishing() && OrderChooseAddressFragment.this.progressViewHelper.isShowing()) {
                            OrderChooseAddressFragment.this.progressViewHelper.dismiss();
                        }
                        if (response.isSuccessful()) {
                            ArrayList arrayList = new ArrayList();
                            if (response.body() == null || ((List) response.body()).size() == 0) {
                                ErrorUtils.showErrorToast(OrderChooseAddressFragment.this.getContext());
                                return;
                            }
                            arrayList.addAll((Collection) response.body());
                            UserPopupAddressAdapter userPopupAddressAdapter = new UserPopupAddressAdapter((AppCompatActivity) OrderChooseAddressFragment.this.getActivity(), arrayList);
                            OrderChooseAddressFragment orderChooseAddressFragment = OrderChooseAddressFragment.this;
                            orderChooseAddressFragment.showListDialog(userPopupAddressAdapter, orderChooseAddressFragment.getString(R.string.e_commerce_address_selection_address_shipping_popup_title), OrderChooseAddressFragment.this.getString(R.string.e_commerce_address_selection_address_shipping_popup_description), OrderChooseAddressFragment.this.defaultAddressList.shippingAddress);
                            return;
                        }
                        ErrorUtils.showErrorToast(OrderChooseAddressFragment.this.getContext());
                    }

                    public void onFailure(Call<List<UserShippingAddressModel>> call, Throwable th) {
                        if (!OrderChooseAddressFragment.this.getActivity().isFinishing() && OrderChooseAddressFragment.this.progressViewHelper.isShowing()) {
                            OrderChooseAddressFragment.this.progressViewHelper.dismiss();
                        }
                        ErrorUtils.showErrorToast(OrderChooseAddressFragment.this.getContext());
                    }
                });
            } else {
                DialogUtil.showNoConnectionInfo(getActivity());
            }
        }
    }

    private void getBillingAddressList() {
        if (!DynamicConstants.MobiRoller_Stage) {
            if (UtilManager.networkHelper().isConnected()) {
                this.progressViewHelper.show();
                this.applyzeUserServiceInterface.getBillingAddresses(UserHelper.getUserId(), getString(R.string.applyze_api_key), getString(R.string.applyze_app_key)).enqueue(new Callback<List<UserBillingAddressModel>>() {
                    public void onResponse(Call<List<UserBillingAddressModel>> call, Response<List<UserBillingAddressModel>> response) {
                        if (!OrderChooseAddressFragment.this.getActivity().isFinishing() && OrderChooseAddressFragment.this.progressViewHelper.isShowing()) {
                            OrderChooseAddressFragment.this.progressViewHelper.dismiss();
                        }
                        if (response.isSuccessful()) {
                            ArrayList arrayList = new ArrayList();
                            if (response.body() == null || ((List) response.body()).size() == 0) {
                                ErrorUtils.showErrorToast(OrderChooseAddressFragment.this.getContext());
                                return;
                            }
                            arrayList.addAll((Collection) response.body());
                            UserPopupAddressAdapter userPopupAddressAdapter = new UserPopupAddressAdapter((AppCompatActivity) OrderChooseAddressFragment.this.getActivity(), arrayList);
                            OrderChooseAddressFragment orderChooseAddressFragment = OrderChooseAddressFragment.this;
                            orderChooseAddressFragment.showListDialog(userPopupAddressAdapter, orderChooseAddressFragment.getString(R.string.e_commerce_address_selection_address_invoice_popup_title), OrderChooseAddressFragment.this.getString(R.string.e_commerce_address_selection_address_invoice_popup_description), OrderChooseAddressFragment.this.defaultAddressList.billingAddress);
                            return;
                        }
                        ErrorUtils.showErrorToast(OrderChooseAddressFragment.this.getContext());
                    }

                    public void onFailure(Call<List<UserBillingAddressModel>> call, Throwable th) {
                        if (!OrderChooseAddressFragment.this.getActivity().isFinishing() && OrderChooseAddressFragment.this.progressViewHelper.isShowing()) {
                            OrderChooseAddressFragment.this.progressViewHelper.dismiss();
                        }
                        ErrorUtils.showErrorToast(OrderChooseAddressFragment.this.getContext());
                    }
                });
            } else {
                DialogUtil.showNoConnectionInfo(getContext());
            }
        }
    }

    /* access modifiers changed from: private */
    public void showListDialog(final UserPopupAddressAdapter userPopupAddressAdapter, String str, String str2, BaseAddressModel baseAddressModel) {
        if (!DynamicConstants.MobiRoller_Stage) {
            new Builder().setContext(getActivity()).setTitle(str).setIconResource(R.drawable.ic_truck_icon).setType(MobirollerDialogType.LIST).setAdapter(userPopupAddressAdapter).setHasDivider(true).setListSelectionListener(new DialogListCallback() {
                public void onSelect(int i) {
                    Object selectedAddress = userPopupAddressAdapter.getSelectedAddress(i);
                    if (selectedAddress instanceof UserBillingAddressModel) {
                        OrderChooseAddressFragment.this.defaultAddressList.billingAddress = (UserBillingAddressModel) selectedAddress;
                        OrderChooseAddressFragment.this.setBillingLayout();
                    } else {
                        OrderChooseAddressFragment.this.defaultAddressList.shippingAddress = (UserShippingAddressModel) selectedAddress;
                        OrderChooseAddressFragment.this.setShippingLayout();
                    }
                    OrderChooseAddressFragment.this.checkIsValid();
                }
            }).setColor(Color.parseColor("#f8e7d8")).show();
        }
    }

    @OnClick({2131362860, 2131362859})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.other_billing_addresses /*2131362859*/:
                getBillingAddressList();
                return;
            case R.id.other_shipping_addresses /*2131362860*/:
                getShippingAddressList();
                return;
            default:
                return;
        }
    }

    @OnClick({2131362202})
    public void onViewClicked() {
        EventBus.getDefault().post(new AddressContinueEvent());
    }

    private void setContinueButton(boolean z) {
        this.continueButton.setEnabled(z);
    }

    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}
