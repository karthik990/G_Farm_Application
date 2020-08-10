package com.mobiroller.activities.chat;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.helpers.ChatNotificationManagerHelper;
import com.mobiroller.helpers.FirebaseGroupChatHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.AuthorModel;
import com.mobiroller.models.chat.GroupChatModel;
import com.mobiroller.models.chat.MessageModel;
import com.mobiroller.models.chat.UserModel;
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
import java.util.Date;
import java.util.Locale;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class GroupChatActivity extends AveActivity implements InputListener, AttachmentsListener, SelectionListener, OnLoadMoreListener, Formatter {
    public static final String TAG = "GroupChatActivity";
    private static final int TOTAL_MESSAGES_COUNT = 100;
    ChatNotificationManagerHelper chatNotificationManagerHelper;
    FirebaseGroupChatHelper firebaseGroupChatHelper;
    private GroupChatModel groupChatModel;
    protected ImageLoader imageLoader;
    /* access modifiers changed from: private */
    public boolean isFirst = false;
    private Date lastLoadedDate;
    private Menu menu;
    MessagesListAdapter<MessageModel> messagesAdapter;
    MessagesList messagesList;
    @Inject
    NetworkHelper networkHelper;
    private UserModel receiver;
    private int selectionCount;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    private Toolbar toolbar;
    @Inject
    ToolbarHelper toolbarHelper;

    public String format(Date date) {
        return null;
    }

    @Subscribe
    public void loadMessages(MessageModel messageModel) {
    }

    public void onAddAttachments() {
    }

    public void onLoadMore(int i, int i2) {
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
        this.firebaseGroupChatHelper = new FirebaseGroupChatHelper(this);
        checkNetworkConnection();
    }

    public void checkNetworkConnection() {
        if (this.networkHelper.isConnected()) {
            init();
        } else {
            new Builder(this).title((int) R.string.failed).content((int) R.string.please_check_your_internet_connection).positiveText((int) R.string.try_again).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    GroupChatActivity.this.checkNetworkConnection();
                }
            }).onNegative(new SingleButtonCallback() {
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    GroupChatActivity.this.finish();
                }
            }).show();
        }
    }

    public void setChatProperties() {
        Intent intent = getIntent();
        String str = ChatConstants.ARG_RECEIVER;
        if (intent.hasExtra(str)) {
            this.receiver = (UserModel) getIntent().getSerializableExtra(str);
        }
    }

    public void init() {
        EventBus.getDefault().register(this);
        setChatProperties();
        this.imageLoader = new ImageLoader() {
            public void loadImage(ImageView imageView, String str) {
            }
        };
        this.messagesList = (MessagesList) findViewById(R.id.messagesList);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(this.toolbar);
        this.toolbarHelper.setToolbarTitle(this, this.receiver.getName());
        initAdapter();
        ((NotificationManager) getSystemService("notification")).cancel(this.receiver.getUid().hashCode());
        this.chatNotificationManagerHelper.clearMessagesViaUserUid(this.receiver.getUid());
        MessageInput messageInput = (MessageInput) findViewById(R.id.input);
        messageInput.setInputListener(this);
        messageInput.setAttachmentsListener(this);
        MobiRollerApplication.openChatId = this.groupChatModel.getChatId();
        getMessageFromFirebaseUser(this.groupChatModel.getChatId(), false);
    }

    private void initAdapter() {
        this.messagesAdapter = new MessagesListAdapter<>(FirebaseAuth.getInstance().getCurrentUser().getUid(), this.imageLoader);
        this.messagesAdapter.enableSelectionMode(this);
        this.messagesAdapter.setLoadMoreListener(this);
        this.messagesAdapter.setDateHeadersFormatter(this);
        this.messagesList.setAdapter(this.messagesAdapter);
    }

    public boolean onSubmit(CharSequence charSequence) {
        MessageModel messageModel = new MessageModel();
        messageModel.setImage(false);
        messageModel.setMessage(charSequence.toString());
        messageModel.setAuthor(new AuthorModel(FirebaseAuth.getInstance().getCurrentUser().getUid(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), UserHelper.getUserImageUrl()));
        messageModel.setReceiverUid(this.receiver.getUid());
        messageModel.setSenderUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        messageModel.setTimestamp(ServerValue.TIMESTAMP);
        this.firebaseGroupChatHelper.sendMessageToGroupChat(this.groupChatModel, messageModel, "123456");
        if (this.isFirst) {
            getMessageFromFirebaseUser(this.groupChatModel.getChatId(), true);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
    }

    public void onSelectionChanged(int i) {
        this.selectionCount = i;
        boolean z = true;
        this.menu.findItem(R.id.action_delete).setVisible(i > 0);
        MenuItem findItem = this.menu.findItem(R.id.action_copy);
        if (i <= 0) {
            z = false;
        }
        findItem.setVisible(z);
    }

    private MessagesListAdapter.Formatter<MessageModel> getMessageStringFormatter() {
        return new MessagesListAdapter.Formatter<MessageModel>() {
            public String format(MessageModel messageModel) {
                String format = new SimpleDateFormat("MMM d, EEE 'at' h:mm a", Locale.getDefault()).format(messageModel.getCreatedAt());
                String text = messageModel.getText();
                if (text == null) {
                    text = "[attachment]";
                }
                return String.format(Locale.getDefault(), "%s: %s (%s)", new Object[]{messageModel.getUser().getName(), text, format});
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
        this.menu = menu2;
        getMenuInflater().inflate(R.menu.chat_actions_menu, menu2);
        onSelectionChanged(0);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_copy /*2131361880*/:
                this.messagesAdapter.copySelectedMessagesText(this, getMessageStringFormatter(), true);
                Toast.makeText(this, R.string.copied_message, 0).show();
                break;
            case R.id.action_delete /*2131361881*/:
                this.messagesAdapter.deleteSelectedMessages();
                break;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.firebaseGroupChatHelper = null;
        EventBus.getDefault().unregister(this);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        MobiRollerApplication.openChatId = null;
    }

    public void getMessageFromFirebaseUser(final String str, final boolean z) {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(str)) {
                    GroupChatActivity.this.isFirst = false;
                    FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_CHAT_ROOMS).child(str).addChildEventListener(new ChildEventListener() {
                        public void onCancelled(DatabaseError databaseError) {
                        }

                        public void onChildChanged(DataSnapshot dataSnapshot, String str) {
                        }

                        public void onChildMoved(DataSnapshot dataSnapshot, String str) {
                        }

                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                        }

                        public void onChildAdded(DataSnapshot dataSnapshot, String str) {
                            GroupChatActivity.this.loadMessages((MessageModel) dataSnapshot.getValue(MessageModel.class));
                        }
                    });
                    return;
                }
                GroupChatActivity.this.isFirst = true;
                boolean z = z;
                if (z) {
                    GroupChatActivity.this.getMessageFromFirebaseUser(str, z);
                }
            }
        });
    }
}
