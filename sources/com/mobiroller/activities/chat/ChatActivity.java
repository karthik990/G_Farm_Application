package com.mobiroller.activities.chat;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.InputCallback;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.activities.user.UserProfileActivity;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.ChatNotificationManagerHelper;
import com.mobiroller.helpers.EncryptionHelper;
import com.mobiroller.helpers.FirebaseChatHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.AuthorModel;
import com.mobiroller.models.chat.ChatUserDetails;
import com.mobiroller.models.chat.MessageModel;
import com.mobiroller.models.chat.ReportModel;
import com.mobiroller.models.events.ChatIsReadEvent;
import com.mobiroller.models.events.DialogArchivedEvent;
import com.mobiroller.models.events.DialogRemovedEvent;
import com.mobiroller.models.events.NewDetailsEvent;
import com.mobiroller.models.events.ReportSentEvent;
import com.mobiroller.models.events.UnarchiveDialogEvent;
import com.mobiroller.models.events.UserBlockEvent;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.views.CircleImageView;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.mobiroller.views.theme.Theme;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessageInput.AttachmentsListener;
import com.stfalcon.chatkit.messages.MessageInput.InputListener;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.messages.MessagesListAdapter.OnLoadMoreListener;
import com.stfalcon.chatkit.messages.MessagesListAdapter.SelectionListener;
import com.stfalcon.chatkit.utils.DateFormatter.Formatter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;

public class ChatActivity extends AveActivity implements InputListener, AttachmentsListener, SelectionListener, OnLoadMoreListener, Formatter, OnGlobalLayoutListener {
    public static final String TAG = "ChatActivity";
    private List<String> blockedByUserList;
    private List<String> blockedUserList;
    private RelativeLayout chatMainLayout;
    ChatNotificationManagerHelper chatNotificationManagerHelper;
    private RelativeLayout chatOverlayLayout;
    private LinearLayout conversationImageLayout;
    /* access modifiers changed from: private */
    public CircleImageView dialogAvatar;
    /* access modifiers changed from: private */
    public TextView dialogMessage;
    /* access modifiers changed from: private */
    public LinearLayout emptyView;
    EncryptionHelper encryptionHelper = new EncryptionHelper();
    FirebaseChatHelper firebaseChatHelper;
    protected ImageLoader imageLoader;
    /* access modifiers changed from: private */
    public MessageInput input;
    /* access modifiers changed from: private */
    public InterstitialAdsUtil interstitialAdsUtil;
    private boolean isArchived = false;
    /* access modifiers changed from: private */
    public boolean isFirst = false;
    /* access modifiers changed from: private */
    public String lastKey = null;
    /* access modifiers changed from: private */
    public String mReceiverUid;
    private Menu menu;
    /* access modifiers changed from: private */
    public ChildEventListener messageListener;
    MessagesListAdapter<MessageModel> messagesAdapter;
    MessagesList messagesList;
    @Inject
    NetworkHelper networkHelper;
    /* access modifiers changed from: private */
    public boolean paginationIsFirst = true;
    /* access modifiers changed from: private */
    public ChatUserDetails receiver;
    /* access modifiers changed from: private */
    public String room_type;
    private int selectionCount;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    private TextView subTitle;
    /* access modifiers changed from: private */
    public TextView title;
    private MobirollerToolbar toolbar;
    @Inject
    ToolbarHelper toolbarHelper;
    private LinearLayout toolbarTextLayout;
    /* access modifiers changed from: private */
    public CircleImageView userLogo;

    public String format(Date date) {
        return null;
    }

    public void onAddAttachments() {
    }

    public boolean onPrepareOptionsMenu(Menu menu2) {
        return true;
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_chat_activity);
        setMobirollerToolbar(this.toolbar);
        this.interstitialAdsUtil = new InterstitialAdsUtil((AppCompatActivity) this);
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        this.firebaseChatHelper = new FirebaseChatHelper(getApplicationContext());
        this.screenId = getIntent().getStringExtra(Constants.KEY_SCREEN_ID);
        ChatConstants.chatScreenId = String.valueOf(this.screenId);
        checkNetworkConnection();
    }

    public void checkNetworkConnection() {
        if (this.networkHelper.isConnected()) {
            init();
        } else {
            new Builder(this).title((int) R.string.failed).content((int) R.string.please_check_your_internet_connection).positiveText((int) R.string.try_again).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    ChatActivity.this.checkNetworkConnection();
                }
            }).onNegative(new SingleButtonCallback() {
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    ChatActivity.this.finish();
                }
            }).show();
        }
    }

    public void setChatProperties() {
        Intent intent = getIntent();
        String str = ChatConstants.ARG_FIREBASE_USER_UID;
        if (intent.hasExtra(str)) {
            this.mReceiverUid = getIntent().getStringExtra(str);
            onPostNewDetails(new NewDetailsEvent());
            listenReceiver();
        }
        this.isArchived = getIntent().getBooleanExtra(ChatConstants.ARG_IS_ARCHIVED, false);
    }

    public void init() {
        this.messagesList = (MessagesList) findViewById(R.id.messagesList);
        this.toolbar = (MobirollerToolbar) findViewById(R.id.toolbar_top);
        this.input = (MessageInput) findViewById(R.id.input);
        setChatProperties();
        this.progressViewHelper.show();
        new Thread() {
            public void run() {
                try {
                    synchronized (this) {
                        wait(3000);
                        ChatActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                ChatActivity.this.progressViewHelper.dismiss();
                                if (ChatActivity.this.messagesAdapter.getItemCount() == 0) {
                                    ChatActivity.this.messagesList.setVisibility(8);
                                    ChatActivity.this.emptyView.setVisibility(0);
                                    ChatActivity.this.getWindow().setSoftInputMode(3);
                                    return;
                                }
                                ChatActivity.this.messagesList.setVisibility(0);
                                ChatActivity.this.emptyView.setVisibility(8);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.emptyView = (LinearLayout) findViewById(R.id.empty_view);
        this.toolbarTextLayout = (LinearLayout) findViewById(R.id.toolbar_text_layout);
        this.conversationImageLayout = (LinearLayout) findViewById(R.id.conversation_image);
        this.dialogAvatar = (CircleImageView) findViewById(R.id.dialogAvatar);
        this.dialogMessage = (TextView) findViewById(R.id.dialogMessage);
        this.title = (TextView) findViewById(R.id.action_bar_title_1);
        this.subTitle = (TextView) findViewById(R.id.action_bar_title_2);
        this.chatMainLayout = (RelativeLayout) findViewById(R.id.chat_main_layout);
        this.chatOverlayLayout = (RelativeLayout) findViewById(R.id.chat_overlay_layout);
        this.userLogo = (CircleImageView) findViewById(R.id.conversation_contact_photo);
        this.chatMainLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
        int i = -1;
        this.title.setTextColor(ColorUtil.isColorDark(Theme.primaryColor) ? -1 : ViewCompat.MEASURED_STATE_MASK);
        TextView textView = this.subTitle;
        if (!ColorUtil.isColorDark(Theme.primaryColor)) {
            i = ViewCompat.MEASURED_STATE_MASK;
        }
        textView.setTextColor(i);
        this.imageLoader = new ImageLoader() {
            public void loadImage(ImageView imageView, String str) {
                Glide.with((FragmentActivity) ChatActivity.this).load(str).into(imageView);
            }
        };
        this.conversationImageLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ChatActivity.this.interstitialAdsUtil.checkInterstitialAds(new Intent(ChatActivity.this, UserProfileActivity.class).putExtra(ChatConstants.ARG_FIREBASE_USER_UID, ChatActivity.this.mReceiverUid));
            }
        });
        this.toolbarTextLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ChatActivity.this.interstitialAdsUtil.checkInterstitialAds(new Intent(ChatActivity.this, UserProfileActivity.class).putExtra(ChatConstants.ARG_FIREBASE_USER_UID, ChatActivity.this.mReceiverUid));
            }
        });
        setSupportActionBar(this.toolbar);
        this.toolbarHelper.setToolbarTitle(this, "");
        initAdapter();
        this.input.setInputListener(this);
        this.input.setAttachmentsListener(this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.receiver != null) {
            EventBus.getDefault().post(new ChatIsReadEvent(this.mReceiverUid, false, this.screenId));
            this.chatNotificationManagerHelper.clearMessagesViaUserUid(this.mReceiverUid);
        }
    }

    private void initAdapter() {
        this.messagesAdapter = new MessagesListAdapter<>(FirebaseAuth.getInstance().getCurrentUser().getUid(), this.imageLoader);
        this.messagesAdapter.enableSelectionMode(this);
        this.messagesAdapter.setLoadMoreListener(this);
        this.messagesAdapter.setDateHeadersFormatter(this);
        this.messagesList.setAdapter(this.messagesAdapter);
    }

    public boolean onSubmit(CharSequence charSequence) {
        if (this.receiver == null) {
            listenReceiver();
            return false;
        } else if (this.networkHelper.isConnected()) {
            MessageModel messageModel = new MessageModel();
            messageModel.setImage(false);
            messageModel.setText(true);
            messageModel.setMessage(this.encryptionHelper.encryptMessage(charSequence.toString()));
            messageModel.setAuthor(new AuthorModel(FirebaseAuth.getInstance().getCurrentUser().getUid(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), UserHelper.getUserImageUrl()));
            messageModel.setReceiverUid(this.mReceiverUid);
            messageModel.setSenderUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
            messageModel.setTimestamp(ServerValue.TIMESTAMP);
            this.firebaseChatHelper.sendMessageToFirebaseUser(messageModel, this.receiver.getFirebaseToken(), this.sharedPrefHelper.getFirebaseToken(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), this.receiver.getUserInfo().name, this.receiver.getUserInfo().imageUrl, String.valueOf(this.screenId));
            if (this.messagesAdapter.getItemCount() == 0) {
                getMessageFromFirebaseUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), this.mReceiverUid, true, false);
            }
            return true;
        } else {
            new Builder(this).title((int) R.string.connection_error).content((int) R.string.please_check_your_internet_connection).positiveText((int) R.string.OK).show();
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
    }

    public void onSelectionChanged(int i) {
        this.selectionCount = i;
        boolean z = false;
        this.menu.findItem(R.id.action_delete).setVisible(i > 0);
        this.menu.findItem(R.id.action_copy).setVisible(i > 0);
        MenuItem findItem = this.menu.findItem(R.id.a_More);
        if (i < 1) {
            z = true;
        }
        findItem.setVisible(z);
    }

    public void onLoadMore(int i, int i2) {
        loadMoreMessage();
    }

    @Subscribe
    public void loadMessages(MessageModel messageModel, boolean z) {
        this.progressViewHelper.dismiss();
        if (messageModel.isText()) {
            messageModel.setMessage(this.encryptionHelper.decryptMessage(messageModel.getMessage()));
            if (messageModel.getSenderUid().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                messageModel.setAuthor(new AuthorModel(FirebaseAuth.getInstance().getCurrentUser().getUid(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), UserHelper.getUserImageUrl()));
            } else {
                messageModel.setAuthor(new AuthorModel(this.receiver.getUserInfo().uid, this.receiver.getUserInfo().name, this.receiver.getUserInfo().imageUrl));
            }
            if (z) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(messageModel);
                this.messagesAdapter.addToEnd(arrayList, false);
            } else {
                this.messagesAdapter.addToStart(messageModel, true);
            }
        } else {
            messageModel.setMessage(getString(R.string.need_to_update));
            if (messageModel.getSenderUid().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                messageModel.setAuthor(new AuthorModel(FirebaseAuth.getInstance().getCurrentUser().getUid(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), UserHelper.getUserImageUrl()));
            } else {
                messageModel.setAuthor(new AuthorModel(this.receiver.getUserInfo().uid, this.receiver.getUserInfo().name, this.receiver.getUserInfo().imageUrl));
            }
            if (z) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(messageModel);
                this.messagesAdapter.addToEnd(arrayList2, false);
            } else {
                this.messagesAdapter.addToStart(messageModel, true);
            }
        }
        if (this.messagesAdapter.getItemCount() == 0) {
            this.messagesList.setVisibility(8);
            this.emptyView.setVisibility(0);
            return;
        }
        this.messagesList.setVisibility(0);
        this.emptyView.setVisibility(8);
    }

    private MessagesListAdapter.Formatter<MessageModel> getMessageStringFormatter() {
        return new MessagesListAdapter.Formatter<MessageModel>() {
            public String format(MessageModel messageModel) {
                new SimpleDateFormat("MMM d, EEE 'at' h:mm a", Locale.getDefault()).format(messageModel.getCreatedAt());
                String text = messageModel.getText();
                return text == null ? "[attachment]" : text;
            }
        };
    }

    public void onBackPressed() {
        if (this.selectionCount == 0) {
            super.onBackPressed();
            finish();
            return;
        }
        this.messagesAdapter.unselectAllItems();
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        if (!this.isFirst) {
            this.toolbar.inflateMenu(R.menu.chat_actions_menu);
            this.menu = this.toolbar.getMenu();
            onSelectionChanged(0);
            setBlockText(this.menu.findItem(R.id.action_block_user));
            if (this.isArchived) {
                this.menu.findItem(R.id.action_archive).setTitle(R.string.action_unarchive);
                this.menu.findItem(R.id.action_archive).setIcon(R.drawable.ic_unarchive_black_24dp);
            }
            ChatUserDetails chatUserDetails = this.receiver;
            if (chatUserDetails != null && chatUserDetails.isAuthorizedUser()) {
                this.menu.findItem(R.id.action_block_user).setVisible(false);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_archive /*2131361862*/:
                if (!this.isArchived) {
                    archiveChat();
                    break;
                } else {
                    unArchiveChat();
                    break;
                }
            case R.id.action_block_user /*2131361873*/:
                blockOrUnBlockUser();
                break;
            case R.id.action_copy /*2131361880*/:
                this.messagesAdapter.copySelectedMessagesText(this, getMessageStringFormatter(), true);
                Toast.makeText(this, R.string.copied_message, 0).show();
                break;
            case R.id.action_delete /*2131361881*/:
                if (!this.networkHelper.isConnected()) {
                    new Builder(this).title((int) R.string.connection_error).content((int) R.string.please_check_your_internet_connection).positiveText((int) R.string.OK).show();
                    break;
                } else {
                    deleteMessages();
                    break;
                }
            case R.id.action_delete_chat /*2131361882*/:
                deleteChat();
                break;
            case R.id.action_report /*2131361901*/:
                sendReport();
                break;
            case R.id.action_show_user /*2131361906*/:
                openUserProfileActivity();
                break;
        }
        return true;
    }

    private void blockOrUnBlockUser() {
        if (UserHelper.getUserBlockedUserList().contains(this.mReceiverUid)) {
            new Builder(this).content((int) R.string.chat_action_unblock_info).positiveText((int) R.string.chat_action_unblock).onPositive(new SingleButtonCallback() {
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    ChatActivity.this.firebaseChatHelper.unBlockUser(ChatActivity.this.mReceiverUid, 2);
                }
            }).show();
        } else {
            new Builder(this).content((int) R.string.chat_action_block_info).positiveText((int) R.string.chat_action_block).onPositive(new SingleButtonCallback() {
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    ChatActivity.this.firebaseChatHelper.blockUser(ChatActivity.this.mReceiverUid, 2);
                }
            }).show();
        }
    }

    private void setBlockText(MenuItem menuItem) {
        if (UserHelper.getUserBlockedUserList().contains(this.mReceiverUid)) {
            menuItem.setTitle(getString(R.string.chat_action_unblock));
        } else {
            menuItem.setTitle(getString(R.string.chat_action_block));
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
        MobiRollerApplication.openChatId = null;
    }

    public void getMessageFromFirebaseUser(final String str, String str2, final boolean z, final boolean z2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(str2);
        this.room_type = sb.toString();
        EventBus.getDefault().post(new ChatIsReadEvent(this.mReceiverUid, false, this.screenId));
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(ChatConstants.ARG_MESSAGING_USER_LIST).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(ChatActivity.this.mReceiverUid)) {
                    if (ChatActivity.this.messagesAdapter.getItemCount() == 0) {
                        ChatActivity.this.invalidateOptionsMenu();
                    }
                    ChatActivity.this.isFirst = false;
                    if (ChatActivity.this.messageListener == null) {
                        ChatActivity.this.messageListener = FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(ChatConstants.ARG_CHAT_ROOMS).child(ChatActivity.this.room_type).orderByKey().limitToLast(20).addChildEventListener(new ChildEventListener() {
                            public void onCancelled(DatabaseError databaseError) {
                            }

                            public void onChildChanged(DataSnapshot dataSnapshot, String str) {
                            }

                            public void onChildMoved(DataSnapshot dataSnapshot, String str) {
                            }

                            public void onChildAdded(DataSnapshot dataSnapshot, String str) {
                                MessageModel messageModel = (MessageModel) dataSnapshot.getValue(MessageModel.class);
                                messageModel.key = dataSnapshot.getKey();
                                if (ChatActivity.this.lastKey == null) {
                                    ChatActivity.this.lastKey = dataSnapshot.getKey();
                                }
                                ChatActivity.this.loadMessages(messageModel, false);
                                EventBus.getDefault().post(new ChatIsReadEvent(ChatActivity.this.mReceiverUid, false, ChatActivity.this.screenId));
                            }

                            public void onChildRemoved(DataSnapshot dataSnapshot) {
                                ChatActivity.this.messagesAdapter.deleteById(dataSnapshot.getKey());
                            }
                        });
                    }
                } else {
                    ChatActivity.this.progressViewHelper.dismiss();
                    if (ChatActivity.this.messagesAdapter.getItemCount() == 0) {
                        ChatActivity.this.messagesList.setVisibility(8);
                        ChatActivity.this.emptyView.setVisibility(0);
                        ChatActivity.this.getWindow().setSoftInputMode(3);
                    } else {
                        ChatActivity.this.messagesList.setVisibility(0);
                        ChatActivity.this.emptyView.setVisibility(8);
                    }
                    ChatActivity.this.isFirst = true;
                    if (z || z2) {
                        ChatActivity chatActivity = ChatActivity.this;
                        chatActivity.getMessageFromFirebaseUser(str, chatActivity.mReceiverUid, z, false);
                    }
                }
            }
        });
    }

    public void sendReport() {
        String str = "";
        new Builder(this).title((int) R.string.action_report).inputRangeRes(5, -1, R.color.red).input((CharSequence) str, (CharSequence) str, (InputCallback) new InputCallback() {
            public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                if (ChatActivity.this.networkHelper.isConnected()) {
                    ChatActivity.this.firebaseChatHelper.sendReport(new ReportModel(FirebaseAuth.getInstance().getCurrentUser().getUid(), ChatActivity.this.mReceiverUid, charSequence.toString()));
                } else {
                    new Builder(ChatActivity.this).title((int) R.string.connection_error).content((int) R.string.please_check_your_internet_connection).positiveText((int) R.string.OK).show();
                }
            }
        }).positiveText((int) R.string.action_send).negativeText((int) R.string.cancel).show();
    }

    @Subscribe
    public void showReportSentDialog(ReportSentEvent reportSentEvent) {
        new Builder(this).title((int) R.string.success).content((int) R.string.report_success).positiveText((int) R.string.OK).show();
    }

    public void deleteChat() {
        new Builder(this).content((int) R.string.action_delete_chat).positiveText((int) R.string.yes).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                ChatActivity.this.firebaseChatHelper.deleteDialog(ChatActivity.this.mReceiverUid);
            }
        }).show();
    }

    public void deleteMessages() {
        new Builder(this).content((int) R.string.action_delete_messages).positiveText((int) R.string.yes).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < ChatActivity.this.messagesAdapter.getSelectedMessages().size(); i++) {
                    arrayList.add(((MessageModel) ChatActivity.this.messagesAdapter.getSelectedMessages().get(i)).key);
                }
                ChatActivity.this.messagesAdapter.deleteSelectedMessages();
                FirebaseChatHelper firebaseChatHelper = ChatActivity.this.firebaseChatHelper;
                StringBuilder sb = new StringBuilder();
                sb.append(FirebaseAuth.getInstance().getCurrentUser().getUid());
                String str = EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR;
                sb.append(str);
                sb.append(ChatActivity.this.receiver.getUserInfo().uid);
                firebaseChatHelper.removeMessagesFromChat(sb.toString(), arrayList);
                FirebaseChatHelper firebaseChatHelper2 = ChatActivity.this.firebaseChatHelper;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(FirebaseAuth.getInstance().getCurrentUser().getUid());
                sb2.append(str);
                sb2.append(ChatActivity.this.receiver.getUserInfo().uid);
                firebaseChatHelper2.setLastMessage(sb2.toString(), ChatActivity.this.receiver);
                if (ChatActivity.this.messagesAdapter.isEmpty()) {
                    ChatActivity.this.firebaseChatHelper.deleteDialog(ChatActivity.this.receiver.getUserInfo().uid);
                }
                if (ChatActivity.this.messagesAdapter.getItemCount() == 0) {
                    ChatActivity.this.messagesList.setVisibility(8);
                    ChatActivity.this.emptyView.setVisibility(0);
                    return;
                }
                ChatActivity.this.messagesList.setVisibility(0);
                ChatActivity.this.emptyView.setVisibility(8);
            }
        }).show();
    }

    public void archiveChat() {
        this.firebaseChatHelper.archiveDialog(this.mReceiverUid);
    }

    public void unArchiveChat() {
        this.firebaseChatHelper.unarchiveDialog(this.mReceiverUid);
    }

    @Subscribe
    public void showDeleteDialog(DialogRemovedEvent dialogRemovedEvent) {
        new Builder(this).title((int) R.string.success).content((int) R.string.dialog_removed).positiveText((int) R.string.OK).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                ChatActivity.this.finish();
            }
        }).show();
    }

    @Subscribe
    public void showUnarchivedDialog(UnarchiveDialogEvent unarchiveDialogEvent) {
        new Builder(this).title((int) R.string.success).content((int) R.string.dialog_unarchived).positiveText((int) R.string.OK).show();
        this.isArchived = false;
        this.menu.findItem(R.id.action_archive).setTitle(R.string.action_archive);
        this.menu.findItem(R.id.action_archive).setIcon(R.drawable.ic_archive_black_24dp);
    }

    @Subscribe
    public void showArchivedDialog(DialogArchivedEvent dialogArchivedEvent) {
        new Builder(this).title((int) R.string.success).content((int) R.string.dialog_archived).positiveText((int) R.string.OK).show();
        this.isArchived = true;
        this.menu.findItem(R.id.action_archive).setTitle(R.string.action_unarchive);
        this.menu.findItem(R.id.action_archive).setIcon(R.drawable.ic_unarchive_black_24dp);
    }

    private void listenReceiver() {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(this.mReceiverUid).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                ChatUserDetails chatUserDetails;
                try {
                    chatUserDetails = (ChatUserDetails) dataSnapshot.getValue(ChatUserDetails.class);
                } catch (Exception unused) {
                    chatUserDetails = new ChatUserDetails();
                    chatUserDetails.parseSnapshot(dataSnapshot);
                }
                ChatActivity.this.receiver = chatUserDetails;
                EventBus.getDefault().post(new ChatIsReadEvent(ChatActivity.this.mReceiverUid, false, ChatActivity.this.screenId));
                MobiRollerApplication.openChatId = ChatActivity.this.firebaseChatHelper.getRoomType(FirebaseAuth.getInstance().getCurrentUser().getUid(), ChatActivity.this.mReceiverUid);
                ChatActivity.this.getMessageFromFirebaseUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), ChatActivity.this.mReceiverUid, false, true);
                String str = "";
                if (ChatActivity.this.receiver.getUserInfo().imageUrl == null || ChatActivity.this.receiver.getUserInfo().imageUrl.equalsIgnoreCase(str)) {
                    Glide.with(ChatActivity.this.getApplicationContext()).load(Integer.valueOf(R.drawable.defaultuser)).into((ImageView) ChatActivity.this.dialogAvatar);
                } else {
                    Glide.with(ChatActivity.this.getApplicationContext()).load(ChatActivity.this.receiver.getUserInfo().imageUrl).into((ImageView) ChatActivity.this.dialogAvatar);
                }
                TextView access$900 = ChatActivity.this.dialogMessage;
                ChatActivity chatActivity = ChatActivity.this;
                access$900.setText(chatActivity.getString(R.string.start_messaging_now, new Object[]{chatActivity.receiver.getUserInfo().name}));
                NotificationManager notificationManager = (NotificationManager) ChatActivity.this.getSystemService("notification");
                StringBuilder sb = new StringBuilder();
                sb.append(ChatActivity.this.receiver.getUserInfo().uid);
                sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                sb.append(ChatActivity.this.screenId);
                notificationManager.cancel(sb.toString().hashCode());
                ChatActivity chatActivity2 = ChatActivity.this;
                chatActivity2.chatNotificationManagerHelper = new ChatNotificationManagerHelper(chatActivity2, String.valueOf(chatActivity2.screenId));
                ChatActivity.this.chatNotificationManagerHelper.clearMessagesViaUserUid(ChatActivity.this.receiver.getUserInfo().uid);
                ChatActivity.this.title.setText(ChatActivity.this.receiver.getUserInfo().name);
                if (ChatActivity.this.receiver.getUserInfo().imageUrl != null && !ChatActivity.this.receiver.getUserInfo().imageUrl.equalsIgnoreCase(str)) {
                    Glide.with((FragmentActivity) ChatActivity.this).load(ChatActivity.this.receiver.getUserInfo().imageUrl).into((ImageView) ChatActivity.this.userLogo);
                    ChatActivity.this.userLogo.setVisibility(0);
                }
                if (ChatActivity.this.receiver.getUserInfo().isOnline == null || !ChatActivity.this.receiver.getUserInfo().isOnline.equalsIgnoreCase("n")) {
                    ChatActivity.this.animateTitleChange(str);
                } else {
                    ChatActivity chatActivity3 = ChatActivity.this;
                    chatActivity3.animateTitleChange(chatActivity3.getString(R.string.user_online));
                }
                if (ChatActivity.this.receiver.getUserInfo().isBanned) {
                    ChatActivity.this.input.getInputEditText().setHint(ChatActivity.this.getString(R.string.blocked_by_app_manager));
                    ChatActivity.this.input.getInputEditText().setEnabled(false);
                    ChatActivity.this.input.getButton().setEnabled(false);
                }
            }
        });
    }

    public void openUserProfileActivity() {
        this.interstitialAdsUtil.checkInterstitialAds(new Intent(this, UserProfileActivity.class).putExtra(ChatConstants.ARG_FIREBASE_USER_UID, this.mReceiverUid));
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (this.messageListener != null) {
            FirebaseDatabase.getInstance().getReference().removeEventListener(this.messageListener);
        }
    }

    /* access modifiers changed from: private */
    public void animateTitleChange(String str) {
        String str2 = "";
        if (!str.equalsIgnoreCase(str2) || this.subTitle != null) {
            this.subTitle.setText(str);
            if (str.equalsIgnoreCase(str2)) {
                this.subTitle.setVisibility(8);
            } else {
                this.subTitle.setVisibility(0);
            }
        }
    }

    public void onGlobalLayout() {
        Rect rect = new Rect();
        this.chatMainLayout.getWindowVisibleDisplayFrame(rect);
        int height = this.chatMainLayout.getRootView().getHeight();
        double d = (double) (height - rect.bottom);
        double d2 = (double) height;
        Double.isNaN(d2);
        if (d > d2 * 0.15d) {
            if (this.messagesAdapter.getItemCount() == 0) {
                this.messagesList.setVisibility(0);
                this.emptyView.setVisibility(8);
            }
        } else if (this.messagesAdapter.getItemCount() == 0 && !this.progressViewHelper.isShowing()) {
            this.messagesList.setVisibility(8);
            this.emptyView.setVisibility(0);
        }
    }

    public void loadMoreMessage() {
        if (this.lastKey != null && this.messagesAdapter.getItemCount() > 19) {
            this.paginationIsFirst = true;
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(ChatConstants.ARG_CHAT_ROOMS).child(this.room_type).orderByKey().endAt(this.lastKey).limitToLast(20).addChildEventListener(new ChildEventListener() {
                public void onCancelled(DatabaseError databaseError) {
                }

                public void onChildChanged(DataSnapshot dataSnapshot, String str) {
                }

                public void onChildMoved(DataSnapshot dataSnapshot, String str) {
                }

                public void onChildAdded(DataSnapshot dataSnapshot, String str) {
                    MessageModel messageModel = (MessageModel) dataSnapshot.getValue(MessageModel.class);
                    messageModel.key = dataSnapshot.getKey();
                    if (!ChatActivity.this.lastKey.equals(messageModel.key)) {
                        if (ChatActivity.this.paginationIsFirst) {
                            ChatActivity.this.lastKey = dataSnapshot.getKey();
                            ChatActivity.this.paginationIsFirst = false;
                        }
                        ChatActivity.this.loadMessages(messageModel, true);
                    }
                }

                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    MessageModel messageModel = (MessageModel) dataSnapshot.getValue(MessageModel.class);
                    ChatActivity.this.messagesAdapter.deleteById(dataSnapshot.getKey());
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Subscribe
    public void onPostUserBlockEvent(UserBlockEvent userBlockEvent) {
        if (userBlockEvent.uid.equalsIgnoreCase(this.mReceiverUid) && userBlockEvent.from == 1) {
            invalidateOptionsMenu();
            if (userBlockEvent.isBlocked) {
                new Builder(this).content((int) R.string.chat_action_blocked_user).positiveText((int) R.string.OK).show();
            } else {
                new Builder(this).content((int) R.string.chat_action_unblocked_user).positiveText((int) R.string.OK).show();
            }
            onPostNewDetails(new NewDetailsEvent());
        }
    }

    @Subscribe
    public void onPostNewDetails(NewDetailsEvent newDetailsEvent) {
        this.blockedByUserList = UserHelper.getUserBlockedByUserList();
        this.blockedUserList = UserHelper.getUserBlockedUserList();
        invalidateOptionsMenu();
        if (this.blockedByUserList.contains(this.mReceiverUid) || this.blockedUserList.contains(this.mReceiverUid)) {
            if (this.blockedByUserList.contains(this.mReceiverUid)) {
                this.input.getInputEditText().setHint(getString(R.string.chat_action_blocked_by_user));
            } else {
                this.input.getInputEditText().setHint(getString(R.string.chat_action_blocked_user));
            }
            this.input.getInputEditText().setEnabled(false);
            this.input.getButton().setEnabled(false);
            return;
        }
        this.input.getInputEditText().setHint(getString(R.string.hint_enter_a_message));
        this.input.getInputEditText().setEnabled(true);
        this.input.getButton().setEnabled(true);
    }
}
