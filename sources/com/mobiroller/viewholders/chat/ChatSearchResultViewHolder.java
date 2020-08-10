package com.mobiroller.viewholders.chat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.bumptech.glide.Glide;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.chat.ChatActivity;
import com.mobiroller.activities.user.UserProfileActivity;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatIndexModel;
import com.mobiroller.models.chat.ChatRoleModel;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.views.CircleImageView;

public class ChatSearchResultViewHolder extends ViewHolder {
    /* access modifiers changed from: private */
    public Activity context;
    @BindView(2131362266)
    CircleImageView dialogAvatar;
    @BindView(2131362267)
    ImageView dialogBadge;
    @BindView(2131362268)
    RelativeLayout dialogContainer;
    @BindView(2131362275)
    TextView dialogName;
    @BindView(2131362278)
    TextView dialogStatus;
    @BindView(2131362279)
    TextView dialogUnreadBubble;
    /* access modifiers changed from: private */
    public InterstitialAdsUtil interstitialAdsUtil;
    /* access modifiers changed from: private */
    public ChatIndexModel userModel;

    public ChatSearchResultViewHolder(View view, InterstitialAdsUtil interstitialAdsUtil2) {
        super(view);
        this.interstitialAdsUtil = interstitialAdsUtil2;
        ButterKnife.bind((Object) this, view);
    }

    public void bind(ChatIndexModel chatIndexModel, Activity activity) {
        this.userModel = chatIndexModel;
        this.context = activity;
        if (chatIndexModel.chatUserModel != null) {
            loadUser();
        }
    }

    private void loadUser() {
        if (this.userModel.chatUserModel.imageUrl == null || this.userModel.chatUserModel.imageUrl.equalsIgnoreCase("")) {
            Glide.with(this.context).load(Integer.valueOf(R.drawable.defaultuser)).into((ImageView) this.dialogAvatar);
        } else {
            ImageManager.displayUserImage(this.context, this.userModel.chatUserModel.imageUrl, this.dialogAvatar);
        }
        this.dialogName.setText(this.userModel.chatUserModel.getName());
        if (this.userModel.chatUserModel.chatRoleIdString != null) {
            for (int i = 0; i < MobiRollerApplication.getChatRoleModels().size(); i++) {
                if (((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i)).getId().equalsIgnoreCase(this.userModel.chatUserModel.chatRoleIdString) && ((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i)).getRibbonImage() != null && !((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i)).getRibbonImage().isEmpty()) {
                    Glide.with(this.context).load(((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i)).getRibbonImage()).into(this.dialogBadge);
                    this.dialogBadge.setVisibility(0);
                }
            }
        } else {
            this.dialogBadge.setVisibility(8);
        }
        if (this.userModel.chatUserModel.status != null) {
            this.dialogStatus.setText(this.userModel.chatUserModel.status);
            this.dialogStatus.setVisibility(0);
        } else {
            this.dialogStatus.setVisibility(8);
        }
        if (this.userModel.chatUserModel.isOnline == null || !this.userModel.chatUserModel.isOnline.equalsIgnoreCase("n")) {
            this.dialogUnreadBubble.setBackground(this.context.getResources().getDrawable(R.drawable.chat_offline_circle));
        } else {
            this.dialogUnreadBubble.setBackground(this.context.getResources().getDrawable(R.drawable.chat_online_circle));
        }
    }

    @OnClick({2131362268, 2131362275, 2131362278})
    public void openChatActivity() {
        Intent intent = new Intent(this.context, ChatActivity.class);
        intent.putExtra(ChatConstants.ARG_FIREBASE_USER_UID, this.userModel.uid);
        intent.putExtra(Constants.KEY_SCREEN_ID, ChatConstants.chatScreenId);
        this.interstitialAdsUtil.checkInterstitialAds(intent);
    }

    @OnClick({2131362266})
    public void showImage() {
        SharedPrefHelper sharedPrefHelper = UtilManager.sharedPrefHelper();
        MaterialDialog build = new Builder(this.context).customView((int) R.layout.layout_chat_image_popup, false).build();
        build.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View customView = build.getCustomView();
        ImageView imageView = (ImageView) customView.findViewById(R.id.image);
        TextView textView = (TextView) customView.findViewById(R.id.userName);
        View findViewById = customView.findViewById(R.id.divider);
        ImageView imageView2 = (ImageView) customView.findViewById(R.id.text_user);
        ImageView imageView3 = (ImageView) customView.findViewById(R.id.user_profile);
        if (this.userModel.chatUserModel.imageUrl == null || this.userModel.chatUserModel.imageUrl.equalsIgnoreCase("")) {
            Glide.with(this.context).load(Integer.valueOf(R.drawable.defaultuser)).into(imageView);
        } else {
            ImageManager.displayUserImage(this.context, this.userModel.chatUserModel.imageUrl, imageView);
        }
        textView.setText(this.userModel.chatUserModel.getName());
        imageView2.setColorFilter(sharedPrefHelper.getActionBarColor(), Mode.SRC_IN);
        imageView3.setColorFilter(sharedPrefHelper.getActionBarColor(), Mode.SRC_IN);
        findViewById.setBackgroundColor(sharedPrefHelper.getActionBarColor());
        imageView2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ChatSearchResultViewHolder.this.openChatActivity();
            }
        });
        imageView3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ChatSearchResultViewHolder.this.interstitialAdsUtil.checkInterstitialAds(new Intent(ChatSearchResultViewHolder.this.context, UserProfileActivity.class).putExtra(ChatConstants.ARG_FIREBASE_USER_UID, ChatSearchResultViewHolder.this.userModel.uid));
            }
        });
        build.setCancelable(true);
        build.show();
    }
}
