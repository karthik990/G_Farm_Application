package com.mobiroller.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.ShareUtil;
import org.apache.http.protocol.HTTP;

public class aveAboutUsViewFragment extends BaseModuleFragment {
    @BindView(2131361821)
    TextView aboutTextView;
    @BindView(2131362257)
    TextView descriptionTextView;
    @BindView(2131362317)
    ImageView emailImage;
    @BindView(2131362384)
    ImageView facebookImage;
    @BindView(2131362473)
    ImageView googleImage;
    @BindView(2131362477)
    GridLayout gridLayout;
    @BindView(2131362503)
    TextView headerTextView;
    @BindView(2131362616)
    ImageView linkedinImage;
    @BindView(2131362649)
    RelativeLayout mainLayout;
    @BindView(2131362873)
    ConstraintLayout overlayLayout;
    @BindView(2131363327)
    ImageView twitterImage;
    @BindView(2131363393)
    ImageView webImage;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_about_us, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        loadUI();
        return inflate;
    }

    private void loadUI() {
        setText(this.headerTextView, this.screenModel.getContentHeader());
        setText(this.aboutTextView, this.screenModel.getAbout());
        setText(this.descriptionTextView, this.screenModel.getDescription());
        setContactImage(this.twitterImage, this.screenModel.getTwitter());
        setContactImage(this.facebookImage, this.screenModel.getFacebook());
        setContactImage(this.linkedinImage, this.screenModel.getLinkedin());
        setContactImage(this.webImage, this.screenModel.getWebsite());
        setContactImage(this.googleImage, this.screenModel.getGoogleplus());
        setContactImage(this.emailImage, this.screenModel.getEmail());
        ImageManager.loadBackgroundImageFromImageModel(this.mainLayout, this.screenModel.getBackgroundImageName());
    }

    private void setText(TextView textView, String str) {
        if (str != null) {
            textView.setText(this.localizationHelper.getLocalizedTitle(str));
        }
    }

    private void setContactImage(ImageView imageView, String str) {
        if (str == null || str.equals("")) {
            this.gridLayout.removeView(imageView);
        }
    }

    @OnClick({2131363327})
    public void onClickTwitterImage() {
        ShareUtil.getOpenTwitterIntent(getActivity(), this.screenModel.getTwitter());
    }

    @OnClick({2131362384})
    public void onClickFacebookImage() {
        ShareUtil.getOpenFacebookIntent(getActivity(), this.screenModel.getFacebook());
    }

    @OnClick({2131362616})
    public void onClickLinkedinImage() {
        ShareUtil.getOpenLinkedinIntent(getActivity(), this.screenModel.getLinkedin());
    }

    @OnClick({2131363393})
    public void onClickWebImage() {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.screenModel.getWebsite())));
    }

    @OnClick({2131362473})
    public void onClickGoogleImage() {
        ShareUtil.getOpenGooglePlusIntent(getActivity(), this.screenModel.getGoogleplus());
    }

    @OnClick({2131362317})
    public void onClickEmailImage() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(HTTP.PLAIN_TEXT_TYPE);
        intent.putExtra("android.intent.extra.EMAIL", this.screenModel.getEmail());
        startActivity(Intent.createChooser(intent, getString(R.string.send_email)));
    }

    public void onResume() {
        super.onResume();
        if (this.mainLayout != null) {
            this.bannerHelper.addBannerAd(this.mainLayout, this.overlayLayout);
        }
    }
}
