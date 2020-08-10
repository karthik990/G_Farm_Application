package com.mobiroller.viewholders.chat;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.ListCallback;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.bumptech.glide.Glide;
import com.mobiroller.activities.user.UserProfileActivity;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.helpers.FirebaseChatHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatUserModel;
import com.mobiroller.models.chat.ReportModel;
import com.mobiroller.util.ImageManager;
import com.mobiroller.views.CircleImageView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChatReportViewHolder extends ViewHolder {
    /* access modifiers changed from: private */
    public Activity context;
    @BindView(2131362268)
    RelativeLayout dialogContainer;
    @BindView(2131362269)
    TextView dialogDate;
    @BindView(2131362272)
    TextView dialogLastMessage;
    @BindView(2131362277)
    CircleImageView dialogSenderAvatar;
    FirebaseChatHelper firebaseChatHelper;
    private View itemView;
    @BindView(2131362730)
    RelativeLayout messageLayout;
    /* access modifiers changed from: private */
    public ChatUserModel receiverUser;
    /* access modifiers changed from: private */
    public ReportModel reportModel;
    @BindView(2131363035)
    TextView reportedName;
    @BindView(2131363120)
    RelativeLayout senderLayout;
    /* access modifiers changed from: private */
    public ChatUserModel senderUser;

    public ChatReportViewHolder(View view) {
        super(view);
        this.itemView = view;
        ButterKnife.bind((Object) this, view);
    }

    public void bind(ReportModel reportModel2, Activity activity) {
        this.dialogLastMessage.setText(reportModel2.getReport());
        this.firebaseChatHelper = new FirebaseChatHelper(activity.getApplicationContext());
        this.reportModel = reportModel2;
        this.context = activity;
        if (reportModel2.getReportedUid() != null) {
            this.firebaseChatHelper.getUserFromFirebase(reportModel2.getReportedUid(), true);
        }
        if (reportModel2.getSenderUid() != null) {
            this.firebaseChatHelper.getUserFromFirebase(reportModel2.getSenderUid(), true);
        }
        if (DateUtils.isToday(((Long) reportModel2.getTimeStamp()).longValue())) {
            this.dialogDate.setText(new SimpleDateFormat("HH:mm").format(new Date(((Long) reportModel2.getTimeStamp()).longValue())));
        } else {
            Calendar instance = Calendar.getInstance();
            instance.add(6, -1);
            Calendar instance2 = Calendar.getInstance();
            instance2.setTime(new Date(((Long) reportModel2.getTimeStamp()).longValue()));
            if (instance.get(1) == instance2.get(1) && instance.get(6) == instance2.get(6)) {
                this.dialogDate.setText(activity.getString(R.string.date_header_yesterday));
            } else {
                this.dialogDate.setText(new SimpleDateFormat(ChatConstants.CHAT_LOG_DATE_FORMAT).format(new Date(((Long) reportModel2.getTimeStamp()).longValue())));
            }
        }
        if (reportModel2.senderUser != null) {
            setSenderUser(reportModel2.senderUser);
        }
        if (reportModel2.receiverUser != null) {
            setReceiverUser(reportModel2.receiverUser);
        }
    }

    public void openOptionsDialog() {
        new Builder(this.context).title((int) R.string.options).items(this.context.getString(R.string.action_delete), this.context.getString(R.string.details), this.senderUser.getName(), this.receiverUser.getName()).itemsCallback(new ListCallback() {
            public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                if (i == 0) {
                    ChatReportViewHolder.this.deleteDialog();
                } else if (i != 1) {
                    String str = ChatConstants.ARG_FIREBASE_USER_UID;
                    if (i == 2) {
                        ChatReportViewHolder.this.context.startActivity(new Intent(ChatReportViewHolder.this.context, UserProfileActivity.class).putExtra(str, ChatReportViewHolder.this.senderUser.uid));
                    } else if (i == 3) {
                        ChatReportViewHolder.this.context.startActivity(new Intent(ChatReportViewHolder.this.context, UserProfileActivity.class).putExtra(str, ChatReportViewHolder.this.receiverUser.uid));
                    }
                } else {
                    ChatReportViewHolder.this.openMessageLayout();
                }
            }
        }).show();
    }

    /* access modifiers changed from: private */
    public void deleteDialog() {
        new Builder(this.context).content((int) R.string.action_delete_chat).positiveText((int) R.string.yes).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                ChatReportViewHolder.this.firebaseChatHelper.deleteReportDialog(ChatReportViewHolder.this.reportModel.key);
            }
        }).show();
    }

    @OnClick({2131362268})
    public void openMessageLayout() {
        Builder title = new Builder(this.context).title((int) R.string.details);
        StringBuilder sb = new StringBuilder();
        sb.append(this.context.getString(R.string.chat_reported_by));
        String str = " : ";
        sb.append(str);
        sb.append(this.senderUser.getName());
        String str2 = " <br> ";
        sb.append(str2);
        sb.append(this.context.getString(R.string.chat_complained));
        sb.append(str);
        sb.append(this.receiverUser.getName());
        sb.append(str2);
        sb.append(this.context.getString(R.string.message));
        sb.append(str);
        sb.append(this.reportModel.getReport());
        title.content((CharSequence) Html.fromHtml(sb.toString())).positiveText((CharSequence) this.context.getString(R.string.action_delete)).neutralText((CharSequence) this.receiverUser.getName()).negativeText((CharSequence) this.senderUser.getName()).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                ChatReportViewHolder.this.firebaseChatHelper.deleteReportDialog(ChatReportViewHolder.this.reportModel.key);
            }
        }).onNegative(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                ChatReportViewHolder.this.context.startActivity(new Intent(ChatReportViewHolder.this.context, UserProfileActivity.class).putExtra(ChatConstants.ARG_FIREBASE_USER_UID, ChatReportViewHolder.this.senderUser.uid));
            }
        }).onNeutral(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                ChatReportViewHolder.this.context.startActivity(new Intent(ChatReportViewHolder.this.context, UserProfileActivity.class).putExtra(ChatConstants.ARG_FIREBASE_USER_UID, ChatReportViewHolder.this.receiverUser.uid));
            }
        }).show();
    }

    private void setSenderUser(ChatUserModel chatUserModel) {
        if (chatUserModel.uid.equalsIgnoreCase(this.reportModel.getSenderUid())) {
            this.senderUser = chatUserModel;
        }
        this.reportedName.setText(this.senderUser.getName());
        if (this.senderUser.imageUrl == null || this.senderUser.imageUrl.equalsIgnoreCase("")) {
            Glide.with(this.context).load(Integer.valueOf(R.drawable.defaultuser)).into((ImageView) this.dialogSenderAvatar);
        } else {
            ImageManager.displayUserImage(this.context, this.senderUser.imageUrl, this.dialogSenderAvatar);
        }
    }

    private void setReceiverUser(ChatUserModel chatUserModel) {
        if (chatUserModel.uid.equalsIgnoreCase(this.reportModel.getReportedUid())) {
            this.receiverUser = chatUserModel;
        }
    }

    @OnClick({2131363120})
    public void openReceiverProfile() {
        Activity activity = this.context;
        activity.startActivity(new Intent(activity, UserProfileActivity.class).putExtra(ChatConstants.ARG_FIREBASE_USER_UID, this.senderUser.uid));
    }
}
