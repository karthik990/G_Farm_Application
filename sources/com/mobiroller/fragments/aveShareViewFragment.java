package com.mobiroller.fragments;

import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.mobi942763453128.R;
import javax.inject.Inject;
import org.apache.http.protocol.HTTP;

public class aveShareViewFragment extends BaseModuleFragment {
    private String contentText;
    @Inject
    LocalizationHelper localizationHelper;
    @BindView(2131363123)
    ImageView shareImage;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    Unbinder unbinder;
    private String url;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_share_view, viewGroup, false);
        if (this.screenModel.getContentText() != null) {
            this.contentText = this.localizationHelper.getLocalizedTitle(this.screenModel.getContentText());
            this.url = this.screenModel.getGooglePlayLink();
        }
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        this.shareImage.setColorFilter(this.sharedPrefHelper.getActionBarColor(), Mode.SRC_ATOP);
        return inflate;
    }

    @OnClick({2131363123})
    public void onClickShareImage() {
        if (this.contentText != null) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            StringBuilder sb = new StringBuilder();
            sb.append(this.contentText);
            sb.append("  ");
            sb.append(this.url);
            intent.putExtra("android.intent.extra.TEXT", sb.toString());
            intent.setType(HTTP.PLAIN_TEXT_TYPE);
            startActivity(Intent.createChooser(intent, getString(R.string.action_share)));
            return;
        }
        Toast.makeText(getActivity(), getString(R.string.common_error), 0).show();
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
