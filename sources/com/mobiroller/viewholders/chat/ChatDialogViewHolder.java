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
import com.mobiroller.helpers.ChatNotificationManagerHelper;
import com.mobiroller.helpers.EncryptionHelper;
import com.mobiroller.helpers.FirebaseChatHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatModel;
import com.mobiroller.models.chat.ChatReceiverModel;
import com.mobiroller.models.chat.ChatRoleModel;
import com.mobiroller.models.chat.ChatUserModel;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.views.CircleImageView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChatDialogViewHolder extends ViewHolder {
    private ChatModel chatModel;
    private ChatReceiverModel chatReceiverModel;
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
    InterstitialAdsUtil interstitialAdsUtil;
    private View itemView;
    private String screenId;
    /* access modifiers changed from: private */
    public ChatUserModel userModel;

    public ChatDialogViewHolder(View view, String str, InterstitialAdsUtil interstitialAdsUtil2) {
        super(view);
        this.interstitialAdsUtil = interstitialAdsUtil2;
        this.itemView = view;
        this.screenId = str;
        ButterKnife.bind((Object) this, view);
    }

    public void bind(ChatModel chatModel2, Activity activity) {
        this.firebaseChatHelper = new FirebaseChatHelper(activity.getApplicationContext());
        this.chatModel = chatModel2;
        this.context = activity;
        this.userModel = new ChatUserModel();
        if (chatModel2.getSenderUid().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            this.chatReceiverModel = new ChatReceiverModel(chatModel2.getReceiverName(), chatModel2.getReceiverUid(), chatModel2.getReceiverImageUrl(), chatModel2.isReceiverRead());
            this.userModel.uid = chatModel2.getReceiverUid();
            this.userModel.name = chatModel2.getReceiverName();
            this.userModel.imageUrl = chatModel2.getReceiverImageUrl();
            this.userModel.chatRoleIdString = chatModel2.receiverChatRoleId;
        } else {
            this.chatReceiverModel = new ChatReceiverModel(chatModel2.getSenderName(), chatModel2.getSenderUid(), chatModel2.getSenderImageUrl(), chatModel2.isSenderRead());
            this.userModel.uid = chatModel2.getSenderUid();
            this.userModel.name = chatModel2.getSenderName();
            this.userModel.imageUrl = chatModel2.getSenderImageUrl();
            this.userModel.chatRoleIdString = chatModel2.senderChatRoleId;
        }
        if (this.userModel.chatRoleIdString != null) {
            for (int i = 0; i < MobiRollerApplication.getChatRoleModels().size(); i++) {
                if (((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i)).getId().equalsIgnoreCase(this.userModel.chatRoleIdString)) {
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
        String str = "";
        if (!new ChatNotificationManagerHelper(activity, this.screenId).getMessageViaUserUid(this.userModel.uid).equalsIgnoreCase(str)) {
            this.dialogLastMessage.setTypeface(null, 1);
            this.dialogDate.setTypeface(null, 1);
            this.dialogName.setTypeface(null, 1);
        } else if (chatModel2.isRead) {
            this.dialogLastMessage.setTypeface(null, 1);
            this.dialogDate.setTypeface(null, 1);
            this.dialogName.setTypeface(null, 1);
        } else {
            this.dialogLastMessage.setTypeface(null, 0);
            this.dialogDate.setTypeface(null, 0);
            this.dialogName.setTypeface(null, 0);
        }
        if (MobiRollerApplication.openChatId != null && MobiRollerApplication.openChatId.equalsIgnoreCase(this.firebaseChatHelper.getRoomType(FirebaseAuth.getInstance().getCurrentUser().getUid(), this.userModel.uid))) {
            this.dialogLastMessage.setTypeface(null, 0);
            this.dialogDate.setTypeface(null, 0);
            this.dialogName.setTypeface(null, 0);
        }
        if (chatModel2.getIsText()) {
            this.dialogLastMessage.setText(this.encryptionHelper.decryptMessage(chatModel2.getLastMessage()));
        } else {
            this.dialogLastMessage.setText(activity.getString(R.string.need_to_update));
        }
        if (DateUtils.isToday(((Long) chatModel2.getTimeStamp()).longValue())) {
            this.dialogDate.setText(new SimpleDateFormat("HH:mm").format(new Date(((Long) chatModel2.getTimeStamp()).longValue())));
        } else {
            Calendar instance = Calendar.getInstance();
            instance.add(6, -1);
            Calendar instance2 = Calendar.getInstance();
            instance2.setTime(new Date(((Long) chatModel2.getTimeStamp()).longValue()));
            if (instance.get(1) == instance2.get(1) && instance.get(6) == instance2.get(6)) {
                this.dialogDate.setText(activity.getString(R.string.date_header_yesterday));
            } else {
                this.dialogDate.setText(new SimpleDateFormat(ChatConstants.CHAT_DIALOG_DATE_FORMAT).format(new Date(((Long) chatModel2.getTimeStamp()).longValue())));
            }
        }
        this.dialogName.setText(this.userModel.getName());
        if (this.userModel.imageUrl == null || this.userModel.imageUrl.equalsIgnoreCase(str)) {
            Glide.with(activity).load(Integer.valueOf(R.drawable.defaultuser)).into((ImageView) this.dialogAvatar);
        } else {
            ImageManager.displayUserImage(activity, this.userModel.imageUrl, this.dialogAvatar);
        }
        if (chatModel2.isOnline()) {
            this.dialogUnreadBubble.setBackground(activity.getResources().getDrawable(R.drawable.chat_online_circle));
        } else {
            this.dialogUnreadBubble.setBackground(activity.getResources().getDrawable(R.drawable.chat_offline_circle));
        }
    }

    @OnLongClick({2131362268, 2131362275, 2131362272, 2131362269})
    public boolean openOptionsDialog() {
        new Builder(this.context).title((int) R.string.options).items((int) R.array.chat_dialog_options).itemsCallback(new ListCallback() {
            public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                if (i == 0) {
                    ChatDialogViewHolder.this.deleteDialog();
                } else if (i == 1) {
                    ChatDialogViewHolder.this.archiveDialog();
                }
            }
        }).show();
        return true;
    }

    /* access modifiers changed from: private */
    public void archiveDialog() {
        this.firebaseChatHelper.archiveDialog(this.userModel.uid);
    }

    /* access modifiers changed from: private */
    public void deleteDialog() {
        new Builder(this.context).content((int) R.string.action_delete_chat).positiveText((int) R.string.yes).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                ChatDialogViewHolder.this.firebaseChatHelper.deleteDialog(ChatDialogViewHolder.this.userModel.uid);
            }
        }).show();
    }

    @OnClick({2131362268, 2131362275, 2131362272, 2131362269})
    public void openChatActivity() {
        Intent intent = new Intent(this.context, ChatActivity.class);
        intent.putExtra(Constants.KEY_SCREEN_ID, ChatConstants.chatScreenId);
        intent.putExtra(ChatConstants.ARG_FIREBASE_USER_UID, this.userModel.uid);
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
                ChatDialogViewHolder.this.openChatActivity();
            }
        });
        imageView3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ChatDialogViewHolder.this.interstitialAdsUtil.checkInterstitialAds(new Intent(ChatDialogViewHolder.this.context, UserProfileActivity.class).putExtra(ChatConstants.ARG_FIREBASE_USER_UID, ChatDialogViewHolder.this.userModel.uid));
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
