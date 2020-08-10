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
import android.widget.Toast;
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
import com.mobiroller.models.chat.ChatRoleModel;
import com.mobiroller.models.chat.ChatUserModel;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.views.CircleImageView;

public class ChatUserViewHolder extends ViewHolder {
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
    public ChatUserModel userModel;

    public ChatUserViewHolder(View view, InterstitialAdsUtil interstitialAdsUtil2) {
        super(view);
        this.interstitialAdsUtil = interstitialAdsUtil2;
        ButterKnife.bind((Object) this, view);
    }

    public void bind(ChatUserModel chatUserModel, Activity activity) {
        boolean z;
        this.userModel = chatUserModel;
        this.context = activity;
        if (chatUserModel.imageUrl == null || chatUserModel.imageUrl.equalsIgnoreCase("")) {
            Glide.with(activity).load(Integer.valueOf(R.drawable.defaultuser)).into((ImageView) this.dialogAvatar);
        } else {
            ImageManager.displayUserImage(activity, chatUserModel.imageUrl, this.dialogAvatar);
        }
        this.dialogName.setText(chatUserModel.getName());
        if (chatUserModel.chatRoleIdString != null) {
            int i = 0;
            while (true) {
                if (i < MobiRollerApplication.getChatRoleModels().size()) {
                    if (((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i)).getId().equalsIgnoreCase(chatUserModel.chatRoleIdString) && ((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i)).getRibbonImage() != null && !((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i)).getRibbonImage().isEmpty()) {
                        Glide.with(activity).load(((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i)).getRibbonImage()).into(this.dialogBadge);
                        this.dialogBadge.setVisibility(0);
                        z = true;
                        break;
                    }
                    i++;
                } else {
                    z = false;
                    break;
                }
            }
            if (!z) {
                this.dialogBadge.setVisibility(8);
            }
        } else {
            this.dialogBadge.setVisibility(8);
        }
        if (chatUserModel.status != null) {
            this.dialogStatus.setText(chatUserModel.status);
            this.dialogStatus.setVisibility(0);
        } else {
            this.dialogStatus.setVisibility(8);
        }
        if (chatUserModel.isOnline == null || !chatUserModel.isOnline.equalsIgnoreCase("n")) {
            this.dialogUnreadBubble.setBackground(activity.getResources().getDrawable(R.drawable.chat_offline_circle));
        } else {
            this.dialogUnreadBubble.setBackground(activity.getResources().getDrawable(R.drawable.chat_online_circle));
        }
    }

    @OnClick({2131362268, 2131362275, 2131362278})
    public void openChatActivity() {
        Intent intent = new Intent(this.context, ChatActivity.class);
        intent.putExtra(ChatConstants.ARG_FIREBASE_USER_UID, this.userModel.uid);
        if (ChatConstants.chatScreenId == null) {
            Activity activity = this.context;
            Toast.makeText(activity, activity.getString(R.string.common_error), 0).show();
            return;
        }
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
        if (this.userModel.imageUrl == null || this.userModel.imageUrl.equalsIgnoreCase("")) {
            Glide.with(this.context).load(Integer.valueOf(R.drawable.defaultuser)).into(imageView);
        } else {
            ImageManager.displayUserImage(this.context, this.userModel.imageUrl, imageView);
        }
        textView.setText(this.userModel.getName());
        imageView2.setColorFilter(sharedPrefHelper.getActionBarColor(), Mode.SRC_IN);
        imageView3.setColorFilter(sharedPrefHelper.getActionBarColor(), Mode.SRC_IN);
        findViewById.setBackgroundColor(sharedPrefHelper.getActionBarColor());
        imageView2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ChatUserViewHolder.this.openChatActivity();
            }
        });
        imageView3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ChatUserViewHolder.this.interstitialAdsUtil.checkInterstitialAds(new Intent(ChatUserViewHolder.this.context, UserProfileActivity.class).putExtra(ChatConstants.ARG_FIREBASE_USER_UID, ChatUserViewHolder.this.userModel.uid));
            }
        });
        build.setCancelable(true);
        build.show();
    }
}
