package com.mobiroller.fragments.user;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mobiroller.adapters.user.UserAddressAdapter;
import com.mobiroller.constants.UserConstants;
import com.mobiroller.fragments.BaseFragment;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.interfaces.FragmentComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.DeleteAddressModel;
import com.mobiroller.models.events.EditBillingAddressEvent;
import com.mobiroller.models.events.EditShippingAddressEvent;
import com.mobiroller.models.events.NewBillingAddressEvent;
import com.mobiroller.models.events.NewShippingAddressEvent;
import com.mobiroller.models.user.UserBillingAddressModel;
import com.mobiroller.models.user.UserShippingAddressModel;
import com.mobiroller.serviceinterfaces.ApplyzeUserServiceInterface;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.views.custom.MobirollerEmptyView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAddressFragment extends BaseFragment {
    private UserAddressAdapter adapter;
    @BindView(2131362375)
    FloatingActionButton addAddress;
    @BindView(2131362617)
    RecyclerView addressList;
    private ApplyzeUserServiceInterface applyzeUserServiceInterface;
    /* access modifiers changed from: private */
    public List<Object> dataList = new ArrayList();
    @BindView(2131362333)
    MobirollerEmptyView emptyView;
    private boolean isBilling = false;
    private boolean isShipping = false;
    @BindView(2131362649)
    CoordinatorLayout mainLayout;
    /* access modifiers changed from: private */
    public ProgressViewHelper progressViewHelper;
    private RequestHelper requestHelper;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    private Snackbar snackbar;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_user_address, viewGroup, false);
        ButterKnife.bind((Object) this, inflate);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.requestHelper = new RequestHelper();
        this.applyzeUserServiceInterface = this.requestHelper.getApplyzeUserAPIService();
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        this.addAddress.setBackgroundTintList(ColorStateList.valueOf(this.sharedPrefHelper.getActionBarColor()));
        if (getArguments() != null) {
            if (getArguments().getString(UserConstants.BUNDLE_EXTRA_USER_ADDRESS).equalsIgnoreCase(UserConstants.BUNDLE_EXTRA_USER_SHIPPING_ADDRESS)) {
                this.isShipping = true;
                getShippingAddressList();
            } else {
                this.isBilling = true;
                getBillingAddressList();
            }
        }
        return inflate;
    }

    /* access modifiers changed from: 0000 */
    public void getShippingAddressList() {
        this.progressViewHelper.show();
        this.applyzeUserServiceInterface.getShippingAddresses(UserHelper.getUserId(), getString(R.string.applyze_api_key), getString(R.string.applyze_app_key)).enqueue(new Callback<List<UserShippingAddressModel>>() {
            public void onResponse(Call<List<UserShippingAddressModel>> call, Response<List<UserShippingAddressModel>> response) {
                if (!UserAddressFragment.this.getActivity().isFinishing() && UserAddressFragment.this.progressViewHelper.isShowing()) {
                    UserAddressFragment.this.progressViewHelper.dismiss();
                }
                if (response.isSuccessful()) {
                    UserAddressFragment.this.dataList.addAll((Collection) response.body());
                    UserAddressFragment.this.setupView();
                    return;
                }
                ErrorUtils.showErrorToast(UserAddressFragment.this.getContext());
            }

            public void onFailure(Call<List<UserShippingAddressModel>> call, Throwable th) {
                if (!UserAddressFragment.this.getActivity().isFinishing() && UserAddressFragment.this.progressViewHelper.isShowing()) {
                    UserAddressFragment.this.progressViewHelper.dismiss();
                }
                ErrorUtils.showErrorToast(UserAddressFragment.this.getContext());
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void getBillingAddressList() {
        this.progressViewHelper.show();
        this.applyzeUserServiceInterface.getBillingAddresses(UserHelper.getUserId(), getString(R.string.applyze_api_key), getString(R.string.applyze_app_key)).enqueue(new Callback<List<UserBillingAddressModel>>() {
            public void onResponse(Call<List<UserBillingAddressModel>> call, Response<List<UserBillingAddressModel>> response) {
                if (!UserAddressFragment.this.getActivity().isFinishing() && UserAddressFragment.this.progressViewHelper.isShowing()) {
                    UserAddressFragment.this.progressViewHelper.dismiss();
                }
                if (response.isSuccessful()) {
                    UserAddressFragment.this.dataList.addAll((Collection) response.body());
                    UserAddressFragment.this.setupView();
                    return;
                }
                ErrorUtils.showErrorToast(UserAddressFragment.this.getContext());
            }

            public void onFailure(Call<List<UserBillingAddressModel>> call, Throwable th) {
                if (!UserAddressFragment.this.getActivity().isFinishing() && UserAddressFragment.this.progressViewHelper.isShowing()) {
                    UserAddressFragment.this.progressViewHelper.dismiss();
                }
                ErrorUtils.showErrorToast(UserAddressFragment.this.getContext());
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void setupView() {
        if (this.dataList.size() != 0) {
            setRecyclerView();
        } else {
            setEmptyView();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setRecyclerView() {
        this.adapter = new UserAddressAdapter((AppCompatActivity) getActivity(), this.dataList);
        this.addressList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.addressList.setAdapter(this.adapter);
        this.addressList.setVisibility(0);
        this.emptyView.setVisibility(8);
    }

    /* access modifiers changed from: 0000 */
    public void setEmptyView() {
        this.addressList.setVisibility(8);
        this.emptyView.setVisibility(0);
    }

    public Fragment injectFragment(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
        return this;
    }

    @OnClick({2131362375})
    public void onClickFabButton() {
        AddressBottomSheetDialogFragment addressBottomSheetDialogFragment = new AddressBottomSheetDialogFragment();
        if (this.isBilling) {
            Bundle bundle = new Bundle();
            bundle.putString(UserConstants.BUNDLE_EXTRA_USER_ADDRESS, UserConstants.BUNDLE_EXTRA_USER_BILLING_ADDRESS);
            addressBottomSheetDialogFragment.setArguments(bundle);
        }
        addressBottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), addressBottomSheetDialogFragment.getTag());
    }

    @Subscribe
    public void onPostBillingAddressEvent(NewBillingAddressEvent newBillingAddressEvent) {
        if (this.isBilling) {
            this.dataList.add(newBillingAddressEvent.addressModel);
            if (this.dataList.size() != 1) {
                this.adapter.notifyItemInserted(this.dataList.size() - 1);
            } else {
                setupView();
            }
        }
    }

    @Subscribe
    public void onPostShippingAddressEvent(NewShippingAddressEvent newShippingAddressEvent) {
        if (this.isShipping) {
            this.dataList.add(newShippingAddressEvent.addressModel);
            if (this.dataList.size() != 1) {
                this.adapter.notifyItemInserted(this.dataList.size() - 1);
            } else {
                setupView();
            }
        }
    }

    @Subscribe
    public void onPostBillingAddressEvent(EditBillingAddressEvent editBillingAddressEvent) {
        if (this.isBilling) {
            UserAddressAdapter userAddressAdapter = this.adapter;
            if (userAddressAdapter != null) {
                userAddressAdapter.update(editBillingAddressEvent.addressModel, editBillingAddressEvent.addressModel.f2189id);
            }
        }
    }

    @Subscribe
    public void onPostShippingAddressEvent(EditShippingAddressEvent editShippingAddressEvent) {
        if (this.isShipping) {
            UserAddressAdapter userAddressAdapter = this.adapter;
            if (userAddressAdapter != null) {
                userAddressAdapter.update(editShippingAddressEvent.addressModel, editShippingAddressEvent.addressModel.f2189id);
            }
        }
    }

    @Subscribe
    public void onPostDeleteAddressEvent(DeleteAddressModel deleteAddressModel) {
        int i = 0;
        int i2 = 0;
        while (i2 < this.dataList.size()) {
            if ((!(this.dataList.get(i2) instanceof UserBillingAddressModel) || !((UserBillingAddressModel) this.dataList.get(i2)).f2189id.equalsIgnoreCase(deleteAddressModel.addressId)) && (!(this.dataList.get(i2) instanceof UserShippingAddressModel) || !((UserShippingAddressModel) this.dataList.get(i2)).f2189id.equalsIgnoreCase(deleteAddressModel.addressId))) {
                i2++;
            } else {
                if (this.dataList.get(i2) instanceof UserBillingAddressModel) {
                    Snackbar.make((View) this.mainLayout, (CharSequence) getString(R.string.user_my_address_billing_address_removed_message), -1).show();
                } else {
                    Snackbar.make((View) this.mainLayout, (CharSequence) getString(R.string.user_my_address_shipping_address_removed_message), -1).show();
                }
                this.dataList.remove(i2);
                this.adapter.notifyItemRemoved(i2);
                if (this.adapter.getItemCount() == 0) {
                    setEmptyView();
                }
                return;
            }
        }
        if (this.adapter.delete(deleteAddressModel.addressId)) {
            if (this.adapter.getItemCount() == 0) {
                setEmptyView();
            }
            while (i < this.dataList.size()) {
                if ((!(this.dataList.get(i) instanceof UserBillingAddressModel) || !((UserBillingAddressModel) this.dataList.get(i)).f2189id.equalsIgnoreCase(deleteAddressModel.addressId)) && (!(this.dataList.get(i) instanceof UserShippingAddressModel) || !((UserShippingAddressModel) this.dataList.get(i)).f2189id.equalsIgnoreCase(deleteAddressModel.addressId))) {
                    i++;
                } else {
                    this.dataList.remove(i);
                    this.adapter.notifyItemRemoved(i);
                    return;
                }
            }
        }
    }

    public void onDestroyView() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
    }
}
