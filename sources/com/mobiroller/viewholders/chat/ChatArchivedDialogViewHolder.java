package com.mobiroller.viewholders.chat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.ListCallback;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.chat.ChatActivity;
import com.mobiroller.activities.user.UserProfileActivity;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.EncryptionHelper;
import com.mobiroller.helpers.FirebaseChatHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatModel;
import com.mobiroller.models.chat.ChatRoleModel;
import com.mobiroller.models.chat.UserModel;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.views.CircleImageView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChatArchivedDialogViewHolder extends ViewHolder {
    /* access modifiers changed from: private */
    public Activity context;
    @BindView(2131362266)
    CircleImageView dialogAvatar;
    @BindView(2131362267)
    ImageView dialogBadge;
    @BindView(2131362268)
    RelativeLayout dialogContainer;
    @BindView(2131362269)
    TextView dialogDate;
    @BindView(2131362272)
    TextView dialogLastMessage;
    @BindView(2131362275)
    TextView dialogName;
    @BindView(2131362279)
    TextView dialogUnreadBubble;
    private EncryptionHelper encryptionHelper = new EncryptionHelper();
    FirebaseChatHelper firebaseChatHelper;
    /* access modifiers changed from: private */
    public InterstitialAdsUtil interstitialAdsUtil;
    /* access modifiers changed from: private */
    public UserModel userModel;

    public ChatArchivedDialogViewHolder(View view, InterstitialAdsUtil interstitialAdsUtil2) {
        super(view);
        this.interstitialAdsUtil = interstitialAdsUtil2;
        ButterKnife.bind((Object) this, view);
    }

    public void bind(ChatModel chatModel, Activity activity) {
        this.firebaseChatHelper = new FirebaseChatHelper(activity.getApplicationContext());
        this.context = activity;
        if (chatModel.getSenderUid().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            this.userModel = new UserModel(chatModel.getReceiverUid(), chatModel.getReceiverName(), chatModel.getReceiverImageUrl());
        } else {
            this.userModel = new UserModel(chatModel.getSenderUid(), chatModel.getSenderName(), chatModel.getSenderImageUrl());
        }
        if (this.userModel.getChatRoleIdString() != null) {
            for (int i = 0; i < MobiRollerApplication.getChatRoleModels().size(); i++) {
                if (((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i)).getId().equalsIgnoreCase(String.valueOf(this.userModel.getChatRoleIdString()))) {
                    this.dialogBadge.setVisibility(0);
                    ChatRoleModel chatRoleModel = (ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i);
                    if (chatRoleModel.getRibbonImage() == null || chatRoleModel.getRibbonImage().isEmpty()) {
                        this.dialogBadge.setVisibility(8);
                    } else {
                        Glide.with(activity).load(chatRoleModel.getRibbonImage()).into(this.dialogBadge);
                    }
                }
            }
        }
        this.dialogLastMessage.setText(this.encryptionHelper.decryptMessage(chatModel.getLastMessage()));
        if (DateUtils.isToday(((Long) chatModel.getTimeStamp()).longValue())) {
            this.dialogDate.setText(new SimpleDateFormat("HH:mm").format(new Date(((Long) chatModel.getTimeStamp()).longValue())));
        } else {
            Calendar instance = Calendar.getInstance();
            instance.add(6, -1);
            Calendar instance2 = Calendar.getInstance();
            instance2.setTime(new Date(((Long) chatModel.getTimeStamp()).longValue()));
            if (instance.get(1) == instance2.get(1) && instance.get(6) == instance2.get(6)) {
                this.dialogDate.setText(activity.getString(R.string.date_header_yesterday));
            } else {
                this.dialogDate.setText(new SimpleDateFormat(ChatConstants.CHAT_DIALOG_DATE_FORMAT).format(new Date(((Long) chatModel.getTimeStamp()).longValue())));
            }
        }
        this.dialogName.setText(this.userModel.getName());
        if (this.userModel.getImageUrl() == null || this.userModel.getImageUrl().equalsIgnoreCase("")) {
            Glide.with(activity).load(Integer.valueOf(R.drawable.defaultuser)).into((ImageView) this.dialogAvatar);
        } else {
            ImageManager.displayUserImage(activity, this.userModel.getImageUrl(), this.dialogAvatar);
        }
        if (chatModel.isOnline()) {
            this.dialogUnreadBubble.setBackground(activity.getResources().getDrawable(R.drawable.chat_online_circle));
        } else {
            this.dialogUnreadBubble.setBackground(activity.getResources().getDrawable(R.drawable.chat_offline_circle));
        }
    }

    @OnLongClick({2131362268, 2131362275, 2131362272, 2131362269})
    public boolean openOptionsDialog() {
        new Builder(this.context).title((int) R.string.options).items((int) R.array.chat_archive_dialog_options).itemsCallback(new ListCallback() {
            public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                if (i == 0) {
                    ChatArchivedDialogViewHolder.this.deleteDialog();
                } else if (i == 1) {
                    ChatArchivedDialogViewHolder.this.unarchiveDialog();
                }
            }
        }).show();
        return true;
    }

    /* access modifiers changed from: private */
    public void unarchiveDialog() {
        this.firebaseChatHelper.unarchiveDialog(this.userModel.getUid());
    }

    /* access modifiers changed from: private */
    public void deleteDialog() {
        new Builder(this.context).content((int) R.string.action_delete_chat).positiveText((int) R.string.yes).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                ChatArchivedDialogViewHolder.this.firebaseChatHelper.deleteDialog(ChatArchivedDialogViewHolder.this.userModel.getUid());
            }
        }).show();
    }

    @OnClick({2131362268})
    public void openChatActivity() {
        Intent intent = new Intent(this.context, ChatActivity.class);
        intent.putExtra(Constants.KEY_SCREEN_ID, ChatConstants.chatScreenId);
        intent.putExtra(ChatConstants.ARG_FIREBASE_USER_UID, this.userModel.uid);
        intent.putExtra(ChatConstants.ARG_IS_ARCHIVED, true);
        this.interstitialAdsUtil.checkInterstitialAds(intent);
    }

    @OnClick({2131362266})
    public void showImage() {
        SharedPrefHelper sharedPrefHelper = UtilManager.sharedPrefHelper();
        final MaterialDialog build = new Builder(this.context).customView((int) R.layout.layout_chat_image_popup, false).build();
        build.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View customView = build.getCustomView();
        ImageView imageView = (ImageView) customView.findViewById(R.id.image);
        TextView textView = (TextView) customView.findViewById(R.id.userName);
        ImageView imageView2 = (ImageView) customView.findViewById(R.id.text_user);
        View findViewById = customView.findViewById(R.id.divider);
        RelativeLayout relativeLayout = (RelativeLayout) customView.findViewById(R.id.mainLayout);
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
                ChatArchivedDialogViewHolder.this.openChatActivity();
            }
        });
        imageView3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ChatArchivedDialogViewHolder.this.interstitialAdsUtil.checkInterstitialAds(new Intent(ChatArchivedDialogViewHolder.this.context, UserProfileActivity.class).putExtra(ChatConstants.ARG_FIREBASE_USER_UID, ChatArchivedDialogViewHolder.this.userModel.uid));
            }
        });
        relativeLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                build.dismiss();
            }
        });
        build.setCancelable(true);
        build.show();
    }
}
