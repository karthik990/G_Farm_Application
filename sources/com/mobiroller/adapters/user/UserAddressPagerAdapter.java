package com.mobiroller.adapters.user;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.mobiroller.constants.UserConstants;
import com.mobiroller.fragments.user.UserAddressFragment;
import com.mobiroller.mobi942763453128.R;

public class UserAddressPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public int getCount() {
        return 2;
    }

    public UserAddressPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mContext = context;
    }

    public Fragment getItem(int i) {
        String str = UserConstants.BUNDLE_EXTRA_USER_ADDRESS;
        if (i == 0) {
            Bundle bundle = new Bundle();
            bundle.putString(str, UserConstants.BUNDLE_EXTRA_USER_SHIPPING_ADDRESS);
            UserAddressFragment userAddressFragment = new UserAddressFragment();
            userAddressFragment.setArguments(bundle);
            return userAddressFragment;
        } else if (i != 1) {
            return new UserAddressFragment();
        } else {
            Bundle bundle2 = new Bundle();
            bundle2.putString(str, UserConstants.BUNDLE_EXTRA_USER_BILLING_ADDRESS);
            UserAddressFragment userAddressFragment2 = new UserAddressFragment();
            userAddressFragment2.setArguments(bundle2);
            return userAddressFragment2;
        }
    }

    public CharSequence getPageTitle(int i) {
        if (i == 0) {
            return this.mContext.getString(R.string.user_address_delivery_address_title);
        }
        if (i != 1) {
            return null;
        }
        return this.mContext.getString(R.string.user_address_invoice_address_title);
    }
}
