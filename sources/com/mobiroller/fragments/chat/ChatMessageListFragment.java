package com.mobiroller.fragments.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.mobiroller.adapters.chat.ChatDialogAdapter;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.fragments.BaseFragment;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.FirebaseChatHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.FragmentComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatModel;
import com.mobiroller.models.events.ChatAccountEvent;
import com.mobiroller.models.events.DialogArchivedEvent;
import com.mobiroller.models.events.DialogRemovedEvent;
import com.mobiroller.models.events.MessageListEmptyEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ChatMessageListFragment extends BaseFragment {
    public static boolean isVisible;
    @BindView(2131361976)
    LinearLayout bannedLayout;
    @Inject
    BannerHelper bannerHelper;
    @BindView(2131362144)
    RecyclerView chatList;
    @BindView(2131362334)
    ImageView emptyImageView;
    @BindView(2131362335)
    TextView emptyTextView;
    @BindView(2131362145)
    LinearLayout emptyView;
    private boolean isBanned = false;
    private ChatDialogAdapter mAdapter;
    private FirebaseChatHelper mFirebaseChatHelper;
    private ChildEventListener mGetAllUsersRelation;
    private ProgressViewHelper mProgressViewHelper;
    private SharedPrefHelper mSharedPref;
    private String mUid;
    @BindView(2131363281)
    RelativeLayout mainLayout;
    @BindView(2131362873)
    RelativeLayout overlayLayout;
    private String screenId;
    private Snackbar snackbar;
    Unbinder unbinder;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        String str = "";
        View inflate = layoutInflater.inflate(R.layout.layout_chat_user_list, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        EventBus.getDefault().register(this);
        this.mFirebaseChatHelper = new FirebaseChatHelper(getActivity().getApplicationContext());
        this.mSharedPref = UtilManager.sharedPrefHelper();
        if (getArguments() != null) {
            Bundle arguments = getArguments();
            String str2 = Constants.KEY_SCREEN_ID;
            if (arguments.containsKey(str2)) {
                this.screenId = getArguments().getString(str2);
            }
        }
        this.mProgressViewHelper = new ProgressViewHelper(getActivity());
        this.mProgressViewHelper.show();
        this.mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        setChatMessageList();
        try {
            this.snackbar = Snackbar.make(getActivity().findViewById(R.id.chat_container), (CharSequence) str, 0);
        } catch (Exception unused) {
            this.snackbar = Snackbar.make(getActivity().findViewById(R.id.chat_container_fragment), (CharSequence) str, 0);
        }
        return inflate;
    }

    public void onResume() {
        super.onResume();
        isVisible = true;
        RelativeLayout relativeLayout = this.mainLayout;
        if (relativeLayout != null) {
            this.bannerHelper.addBannerAd(relativeLayout, this.overlayLayout);
        }
    }

    public void onStop() {
        super.onStop();
        isVisible = false;
    }

    /* access modifiers changed from: private */
    public void setDialogAdapter(ChatModel chatModel) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            ProgressViewHelper progressViewHelper = this.mProgressViewHelper;
            if (progressViewHelper != null) {
                progressViewHelper.dismiss();
            }
        }
        if (chatModel != null) {
            if (this.mAdapter.getItemCount() == 0) {
                this.mAdapter.addItem(chatModel);
            } else {
                this.mAdapter.checkItem(chatModel);
            }
        }
        checkAdapterIsEmpty(new MessageListEmptyEvent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkAdapterIsEmpty(MessageListEmptyEvent messageListEmptyEvent) {
        if (!this.isBanned && this.emptyView != null && this.chatList != null) {
            if (getActivity() != null && !getActivity().isFinishing()) {
                ProgressViewHelper progressViewHelper = this.mProgressViewHelper;
                if (progressViewHelper != null) {
                    progressViewHelper.dismiss();
                }
            }
            if (this.mAdapter.getItemCount() != 0) {
                this.emptyView.setVisibility(8);
                this.chatList.setVisibility(0);
                return;
            }
            this.emptyView.setVisibility(0);
            this.chatList.setVisibility(8);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.unbinder.unbind();
        FirebaseChatHelper firebaseChatHelper = this.mFirebaseChatHelper;
        if (firebaseChatHelper != null) {
            firebaseChatHelper.removeUsersListeners();
        }
        if (this.mGetAllUsersRelation != null) {
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(ChatConstants.ARG_MESSAGING_USERS).child(this.mUid).orderByChild(Param.TIMESTAMP).removeEventListener(this.mGetAllUsersRelation);
        }
    }

    private void setChatMessageList() {
        this.mAdapter = new ChatDialogAdapter(getActivity(), this.screenId, this.mFirebaseChatHelper, this.chatList);
        this.chatList.setAdapter(this.mAdapter);
        this.chatList.setLayoutManager(new LinearLayoutManager(getActivity()));
        getUserAllRelationFromFirebase();
    }

    @Subscribe
    public void showSnackBarItemRemoved(DialogRemovedEvent dialogRemovedEvent) {
        this.snackbar.setText((int) R.string.dialog_removed);
        this.snackbar.show();
    }

    @Subscribe
    public void showSnackBarItemArchived(DialogArchivedEvent dialogArchivedEvent) {
        this.snackbar.setText((int) R.string.dialog_archived);
        this.snackbar.show();
    }

    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        isVisible = z;
    }

    private void getUserAllRelationFromFirebase() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                EventBus.getDefault().post(new MessageListEmptyEvent());
            }
        }, 1000);
        this.mGetAllUsersRelation = FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(ChatConstants.ARG_MESSAGING_USERS).child(this.mUid).orderByChild("timeStamp").addChildEventListener(new ChildEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onChildMoved(DataSnapshot dataSnapshot, String str) {
            }

            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            public void onChildAdded(DataSnapshot dataSnapshot, String str) {
                ChatModel chatModel = (ChatModel) dataSnapshot.getValue(ChatModel.class);
                if (ChatMessageListFragment.this.validateChatModel(chatModel)) {
                    ChatMessageListFragment.this.setDialogAdapter(chatModel);
                }
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String str) {
                ChatModel chatModel = (ChatModel) dataSnapshot.getValue(ChatModel.class);
                if (ChatMessageListFragment.this.validateChatModel(chatModel)) {
                    ChatMessageListFragment.this.setDialogAdapter(chatModel);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void chatAccountEvent(ChatAccountEvent chatAccountEvent) {
        if (getActivity() != null) {
            if (chatAccountEvent.isBanned()) {
                this.isBanned = true;
                this.bannedLayout.setVisibility(0);
                this.chatList.setVisibility(8);
            } else {
                this.isBanned = false;
                this.bannedLayout.setVisibility(8);
                this.chatList.setVisibility(0);
            }
        }
    }

    public Fragment injectFragment(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
        return this;
    }

    /* access modifiers changed from: private */
    public boolean validateChatModel(ChatModel chatModel) {
        return (chatModel == null || chatModel.getRealReceiverUid() == null) ? false : true;
    }
}
