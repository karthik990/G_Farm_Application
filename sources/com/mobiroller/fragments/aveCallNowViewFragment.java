package com.mobiroller.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mobiroller.mobi942763453128.R;

public class aveCallNowViewFragment extends BaseModuleFragment {
    @BindView(2131362104)
    ImageView callNowImage;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.call_now, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        return inflate;
    }

    @OnClick({2131362104})
    public void onClickCallImage() {
        if (this.screenModel.getPhoneNumber() != null || !this.screenModel.getPhoneNumber().equalsIgnoreCase("null")) {
            Intent flags = new Intent("android.intent.action.DIAL").setFlags(268435456);
            StringBuilder sb = new StringBuilder();
            sb.append("tel:");
            sb.append(this.screenModel.getPhoneNumber());
            startActivity(flags.setData(Uri.parse(sb.toString())));
        }
    }
}
