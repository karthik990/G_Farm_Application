package com.mobiroller.fragments.chat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView.OnCloseListener;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.afollestad.materialdialogs.Theme;
import com.afollestad.materialdialogs.internal.MDButton;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.chat.ChatUserSearchResultActivity;
import com.mobiroller.adapters.chat.ChatUserAdapter;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.fragments.BaseFragment;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.FragmentComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatRoleModel;
import com.mobiroller.models.chat.ChatUserModel;
import com.mobiroller.models.events.UserListEmptyEvent;
import com.mobiroller.views.EndlessRecyclerViewScrollListenerRecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ChatUserListFragment extends BaseFragment implements OnQueryTextListener, OnCloseListener {
    public static boolean isVisible;
    private final int CHAT_PAGINATION_LIMIT = 20;
    /* access modifiers changed from: private */
    public ChatUserAdapter adapter;
    @Inject
    BannerHelper bannerHelper;
    private List<String> blockedByUserList;
    private List<String> blockedUserList;
    @BindView(2131362144)
    RecyclerView chatList;
    @BindView(2131362334)
    ImageView emptyImageView;
    @BindView(2131362335)
    TextView emptyTextView;
    @BindView(2131362145)
    LinearLayout emptyView;
    @BindView(2131362631)
    SpinKitView loadMoreProgressView;
    private ChildEventListener mEventListener;
    /* access modifiers changed from: private */
    public int mFilterPosition = 0;
    /* access modifiers changed from: private */
    public String mLastKey = "";
    private boolean mPaginate = true;
    /* access modifiers changed from: private */
    public String mSearchText;
    /* access modifiers changed from: private */
    public String mUserLastKey;
    @BindView(2131363281)
    RelativeLayout mainLayout;
    @BindView(2131362873)
    RelativeLayout overlayLayout;
    private ProgressViewHelper progressViewHelper;
    ArrayList<String> roles = null;
    private SharedPrefHelper sharedPrefHelper;
    Unbinder unbinder;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_chat_user_list, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        if (getArguments() != null) {
            this.roles = getArguments().getStringArrayList("roles");
        }
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.emptyView.setVisibility(8);
        this.loadMoreProgressView.setVisibility(0);
        this.blockedUserList = UserHelper.getUserBlockedUserList();
        this.blockedByUserList = UserHelper.getUserBlockedByUserList();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.chatList.setVisibility(0);
        this.loadMoreProgressView.setColor(this.sharedPrefHelper.getActionBarColor());
        this.loadMoreProgressView.setVisibility(8);
        this.emptyTextView.setText(getString(R.string.no_user_found));
        this.emptyTextView.setTextColor(this.sharedPrefHelper.getActionBarColor());
        this.emptyImageView.setColorFilter(this.sharedPrefHelper.getActionBarColor());
        this.emptyImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_person_white_24dp));
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        this.progressViewHelper.show();
        setHasOptionsMenu(true);
        new Timer().schedule(new TimerTask() {
            public void run() {
                EventBus.getDefault().post(new UserListEmptyEvent());
            }
        }, 1000);
        return inflate;
    }

    public void onResume() {
        super.onResume();
        RelativeLayout relativeLayout = this.mainLayout;
        if (relativeLayout != null) {
            this.bannerHelper.addBannerAd(relativeLayout, this.overlayLayout);
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setChatUserList();
    }

    /* access modifiers changed from: private */
    public void setUserAdapter(ChatUserModel chatUserModel) {
        if (chatUserModel != null && !chatUserModel.isBanned) {
            ArrayList<String> arrayList = this.roles;
            String str = " ";
            if (arrayList == null || arrayList.size() <= 0) {
                this.progressViewHelper.dismiss();
                if (!checkUserIsIncognito(chatUserModel)) {
                    addItemToAdapter(chatUserModel);
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.adapter.getOnlineCount());
                        sb.append(str);
                        sb.append(getString(R.string.user_online));
                        supportActionBar.setSubtitle((CharSequence) sb.toString());
                        return;
                    }
                    return;
                }
                return;
            }
            for (int i = 0; i < this.roles.size(); i++) {
                if (chatUserModel.roleString.equalsIgnoreCase((String) this.roles.get(i))) {
                    if (!checkUserIsIncognito(chatUserModel)) {
                        addItemToAdapter(chatUserModel);
                        ActionBar supportActionBar2 = ((AppCompatActivity) getActivity()).getSupportActionBar();
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(this.adapter.getOnlineCount());
                        sb2.append(str);
                        sb2.append(getString(R.string.user_online));
                        supportActionBar2.setSubtitle((CharSequence) sb2.toString());
                    }
                    if (this.progressViewHelper != null && !getActivity().isFinishing()) {
                        this.progressViewHelper.dismiss();
                        return;
                    }
                    return;
                }
            }
        }
    }

    private boolean checkUserIsIncognito(ChatUserModel chatUserModel) {
        boolean z = false;
        if (chatUserModel.chatRoleIdString == null) {
            return false;
        }
        int i = 0;
        while (i < MobiRollerApplication.getChatRoleModels().size()) {
            if (!chatUserModel.chatRoleIdString.equalsIgnoreCase(((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i)).getId()) || !((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i)).isIncognito()) {
                i++;
            } else {
                String permissionTypeID = ((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i)).getPermissionTypeID();
                String str = IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
                if (permissionTypeID.equalsIgnoreCase(str) && !this.sharedPrefHelper.getUserIsChatAdmin()) {
                    return true;
                }
                if (((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i)).getPermissionTypeID().equalsIgnoreCase(str) && this.sharedPrefHelper.getUserIsChatAdmin()) {
                    return false;
                }
                if ((((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i)).getPermissionTypeID().equalsIgnoreCase(str) && !this.sharedPrefHelper.getUserIsChatAdmin()) || !this.sharedPrefHelper.getUserIsChatMod()) {
                    return true;
                }
                if (((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i)).getPermissionTypeID().equalsIgnoreCase(str) || (!this.sharedPrefHelper.getUserIsChatMod() && !this.sharedPrefHelper.getUserIsChatAdmin())) {
                    z = true;
                }
                return z;
            }
        }
        return false;
    }

    private void addItemToAdapter(ChatUserModel chatUserModel) {
        if (!chatUserModel.isBanned && !this.blockedByUserList.contains(chatUserModel.uid) && !this.blockedUserList.contains(chatUserModel.uid)) {
            if (this.adapter.getItemCount() == 0) {
                this.adapter.addItem(chatUserModel);
            } else {
                this.adapter.checkItem(chatUserModel);
            }
        }
    }

    public void onStop() {
        super.onStop();
        if (this.mEventListener != null) {
            FirebaseDatabase.getInstance().getReference().removeEventListener(this.mEventListener);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    private void setChatUserList() {
        this.adapter = new ChatUserAdapter(getActivity(), this.chatList, false);
        this.chatList.setAdapter(this.adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        this.chatList.setLayoutManager(linearLayoutManager);
        this.chatList.addOnScrollListener(new EndlessRecyclerViewScrollListenerRecyclerView(linearLayoutManager) {
            public void onLoadMore(int i, int i2) {
                ChatUserListFragment.this.loadMoreProgressView.setVisibility(0);
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        ChatUserListFragment.this.getAllUsersFromFirebaseMore();
                    }
                }, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
            }
        });
        getOnlineUsers();
        getAllUsersFromFirebase();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_search) {
            MaterialDialog build = new Builder(getActivity()).title((int) R.string.action_filter).customView((int) R.layout.dialog_chat_user_filter, true).positiveText((int) R.string.action_search).theme(Theme.LIGHT).negativeText(17039360).onPositive(new SingleButtonCallback() {
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    View currentFocus = materialDialog.getCurrentFocus();
                    if (currentFocus != null) {
                        ((InputMethodManager) ChatUserListFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                    }
                    ChatUserListFragment chatUserListFragment = ChatUserListFragment.this;
                    chatUserListFragment.startActivity(new Intent(chatUserListFragment.getActivity(), ChatUserSearchResultActivity.class).putExtra(ChatConstants.SEARCH_INTENT_EXTRA_FILTER_BY, ChatUserListFragment.this.mFilterPosition).putExtra(ChatConstants.SEARCH_INTENT_EXTRA_SEARCH_TEXT, ChatUserListFragment.this.mSearchText));
                }
            }).build();
            final MDButton actionButton = build.getActionButton(DialogAction.POSITIVE);
            ((EditText) build.getCustomView().findViewById(R.id.search_edit_text)).addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable editable) {
                }

                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    ChatUserListFragment.this.mSearchText = charSequence.toString().trim().toLowerCase();
                    actionButton.setEnabled(charSequence.toString().trim().length() > 0);
                }
            });
            Spinner spinner = (Spinner) build.getCustomView().findViewById(R.id.spinner);
            String str = "#80000000";
            spinner.getBackground().setColorFilter(Color.parseColor(str), Mode.SRC_ATOP);
            spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    ChatUserListFragment.this.mFilterPosition = i;
                }
            });
            ArrayList arrayList = new ArrayList();
            arrayList.add(getString(R.string.search_name));
            arrayList.add(getString(R.string.chat_username));
            spinner.setSelection(0, true);
            ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.item_dialog_chat_user_filter, arrayList);
            arrayAdapter.setDropDownViewResource(17367049);
            ViewCompat.setBackgroundTintList(spinner, ColorStateList.valueOf(Color.parseColor(str)));
            spinner.setAdapter(arrayAdapter);
            build.show();
            actionButton.setEnabled(false);
        }
        return true;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menu.clear();
        menuInflater.inflate(R.menu.search_option_menu, menu);
    }

    public boolean onQueryTextSubmit(String str) {
        this.adapter.filter(str);
        return false;
    }

    public boolean onQueryTextChange(String str) {
        this.adapter.filter(str);
        return false;
    }

    public boolean onClose() {
        this.adapter.removeFilter();
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkAdapterIsEmpty(UserListEmptyEvent userListEmptyEvent) {
        if (getActivity() != null) {
            if (!getActivity().isFinishing() && this.progressViewHelper.isShowing()) {
                this.progressViewHelper.dismiss();
            }
            if (this.adapter.getItemCount() != 0) {
                this.emptyView.setVisibility(8);
                this.chatList.setVisibility(0);
            } else {
                this.emptyView.setVisibility(0);
                this.chatList.setVisibility(8);
            }
        }
    }

    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        isVisible = z;
    }

    public void getOnlineUsers() {
        this.mEventListener = FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).orderByChild("o").startAt("n").endAt("n").addChildEventListener(new ChildEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String str) {
            }

            public void onChildMoved(DataSnapshot dataSnapshot, String str) {
            }

            public void onChildAdded(DataSnapshot dataSnapshot, String str) {
                ChatUserModel chatUserModel;
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    try {
                        chatUserModel = (ChatUserModel) dataSnapshot.getValue(ChatUserModel.class);
                    } catch (Exception unused) {
                        chatUserModel = new ChatUserModel();
                        chatUserModel.parseSnapshot(dataSnapshot);
                    }
                    chatUserModel.uid = dataSnapshot.getKey();
                    if (!TextUtils.equals(chatUserModel.uid, FirebaseAuth.getInstance().getCurrentUser().getUid()) && ChatUserListFragment.this.validateUserModel(chatUserModel)) {
                        ChatUserListFragment.this.setUserAdapter(chatUserModel);
                    }
                }
            }

            /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
            /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0014 */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onChildRemoved(com.google.firebase.database.DataSnapshot r4) {
                /*
                    r3 = this;
                    com.google.firebase.auth.FirebaseAuth r0 = com.google.firebase.auth.FirebaseAuth.getInstance()     // Catch:{ Exception -> 0x00a6 }
                    com.google.firebase.auth.FirebaseUser r0 = r0.getCurrentUser()     // Catch:{ Exception -> 0x00a6 }
                    if (r0 != 0) goto L_0x000b
                    return
                L_0x000b:
                    java.lang.Class<com.mobiroller.models.chat.ChatUserModel> r0 = com.mobiroller.models.chat.ChatUserModel.class
                    java.lang.Object r0 = r4.getValue(r0)     // Catch:{ Exception -> 0x0014 }
                    com.mobiroller.models.chat.ChatUserModel r0 = (com.mobiroller.models.chat.ChatUserModel) r0     // Catch:{ Exception -> 0x0014 }
                    goto L_0x001c
                L_0x0014:
                    com.mobiroller.models.chat.ChatUserModel r0 = new com.mobiroller.models.chat.ChatUserModel     // Catch:{ Exception -> 0x00a6 }
                    r0.<init>()     // Catch:{ Exception -> 0x00a6 }
                    r0.parseSnapshot(r4)     // Catch:{ Exception -> 0x00a6 }
                L_0x001c:
                    java.lang.String r4 = r4.getKey()     // Catch:{ Exception -> 0x00a6 }
                    r0.uid = r4     // Catch:{ Exception -> 0x00a6 }
                    java.lang.String r4 = r0.uid     // Catch:{ Exception -> 0x00a6 }
                    com.google.firebase.auth.FirebaseAuth r1 = com.google.firebase.auth.FirebaseAuth.getInstance()     // Catch:{ Exception -> 0x00a6 }
                    com.google.firebase.auth.FirebaseUser r1 = r1.getCurrentUser()     // Catch:{ Exception -> 0x00a6 }
                    java.lang.String r1 = r1.getUid()     // Catch:{ Exception -> 0x00a6 }
                    boolean r4 = android.text.TextUtils.equals(r4, r1)     // Catch:{ Exception -> 0x00a6 }
                    if (r4 != 0) goto L_0x00aa
                    com.mobiroller.fragments.chat.ChatUserListFragment r4 = com.mobiroller.fragments.chat.ChatUserListFragment.this     // Catch:{ Exception -> 0x00a6 }
                    boolean r4 = r4.validateUserModel(r0)     // Catch:{ Exception -> 0x00a6 }
                    if (r4 == 0) goto L_0x00aa
                    com.mobiroller.fragments.chat.ChatUserListFragment r4 = com.mobiroller.fragments.chat.ChatUserListFragment.this     // Catch:{ Exception -> 0x00a6 }
                    com.mobiroller.adapters.chat.ChatUserAdapter r4 = r4.adapter     // Catch:{ Exception -> 0x00a6 }
                    r4.removeItem(r0)     // Catch:{ Exception -> 0x00a6 }
                    com.mobiroller.fragments.chat.ChatUserListFragment r4 = com.mobiroller.fragments.chat.ChatUserListFragment.this     // Catch:{ Exception -> 0x00a6 }
                    androidx.fragment.app.FragmentActivity r4 = r4.getActivity()     // Catch:{ Exception -> 0x00a6 }
                    if (r4 == 0) goto L_0x00aa
                    com.mobiroller.fragments.chat.ChatUserListFragment r4 = com.mobiroller.fragments.chat.ChatUserListFragment.this     // Catch:{ Exception -> 0x00a6 }
                    androidx.fragment.app.FragmentActivity r4 = r4.getActivity()     // Catch:{ Exception -> 0x00a6 }
                    boolean r4 = r4.isFinishing()     // Catch:{ Exception -> 0x00a6 }
                    if (r4 != 0) goto L_0x00aa
                    com.mobiroller.fragments.chat.ChatUserListFragment r4 = com.mobiroller.fragments.chat.ChatUserListFragment.this     // Catch:{ Exception -> 0x00a6 }
                    com.mobiroller.adapters.chat.ChatUserAdapter r4 = r4.adapter     // Catch:{ Exception -> 0x00a6 }
                    if (r4 == 0) goto L_0x00aa
                    com.mobiroller.fragments.chat.ChatUserListFragment r4 = com.mobiroller.fragments.chat.ChatUserListFragment.this     // Catch:{ Exception -> 0x00a6 }
                    com.mobiroller.adapters.chat.ChatUserAdapter r4 = r4.adapter     // Catch:{ Exception -> 0x00a6 }
                    int r4 = r4.getItemCount()     // Catch:{ Exception -> 0x00a6 }
                    if (r4 == 0) goto L_0x00aa
                    com.mobiroller.fragments.chat.ChatUserListFragment r4 = com.mobiroller.fragments.chat.ChatUserListFragment.this     // Catch:{ Exception -> 0x00a6 }
                    androidx.fragment.app.FragmentActivity r4 = r4.getActivity()     // Catch:{ Exception -> 0x00a6 }
                    androidx.appcompat.app.AppCompatActivity r4 = (androidx.appcompat.app.AppCompatActivity) r4     // Catch:{ Exception -> 0x00a6 }
                    androidx.appcompat.app.ActionBar r4 = r4.getSupportActionBar()     // Catch:{ Exception -> 0x00a6 }
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a6 }
                    r0.<init>()     // Catch:{ Exception -> 0x00a6 }
                    com.mobiroller.fragments.chat.ChatUserListFragment r1 = com.mobiroller.fragments.chat.ChatUserListFragment.this     // Catch:{ Exception -> 0x00a6 }
                    com.mobiroller.adapters.chat.ChatUserAdapter r1 = r1.adapter     // Catch:{ Exception -> 0x00a6 }
                    int r1 = r1.getOnlineCount()     // Catch:{ Exception -> 0x00a6 }
                    r0.append(r1)     // Catch:{ Exception -> 0x00a6 }
                    java.lang.String r1 = " "
                    r0.append(r1)     // Catch:{ Exception -> 0x00a6 }
                    com.mobiroller.fragments.chat.ChatUserListFragment r1 = com.mobiroller.fragments.chat.ChatUserListFragment.this     // Catch:{ Exception -> 0x00a6 }
                    r2 = 2131821452(0x7f11038c, float:1.9275648E38)
                    java.lang.String r1 = r1.getString(r2)     // Catch:{ Exception -> 0x00a6 }
                    r0.append(r1)     // Catch:{ Exception -> 0x00a6 }
                    java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00a6 }
                    r4.setSubtitle(r0)     // Catch:{ Exception -> 0x00a6 }
                    goto L_0x00aa
                L_0x00a6:
                    r4 = move-exception
                    r4.printStackTrace()
                L_0x00aa:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.fragments.chat.ChatUserListFragment.C23316.onChildRemoved(com.google.firebase.database.DataSnapshot):void");
            }
        });
    }

    private void getAllUsersFromFirebase() {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).orderByChild("n").startAt("").limitToFirst(20).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                ChatUserModel chatUserModel;
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                        try {
                            chatUserModel = (ChatUserModel) dataSnapshot2.getValue(ChatUserModel.class);
                        } catch (Exception unused) {
                            chatUserModel = new ChatUserModel();
                            chatUserModel.parseSnapshot(dataSnapshot2);
                        }
                        chatUserModel.uid = dataSnapshot2.getKey();
                        ChatUserListFragment.this.mLastKey = dataSnapshot2.getKey();
                        ChatUserListFragment.this.mUserLastKey = chatUserModel.name;
                        if (!TextUtils.equals(chatUserModel.uid, FirebaseAuth.getInstance().getCurrentUser().getUid()) && ChatUserListFragment.this.validateUserModel(chatUserModel)) {
                            ChatUserListFragment.this.setUserAdapter(chatUserModel);
                        }
                    }
                }
                if (ChatUserListFragment.this.loadMoreProgressView != null) {
                    ChatUserListFragment.this.loadMoreProgressView.setVisibility(8);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void getAllUsersFromFirebaseMore() {
        if (this.mUserLastKey != null) {
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).orderByChild("n").startAt(this.mUserLastKey).limitToFirst(20).addListenerForSingleValueEvent(new ValueEventListener() {
                public void onCancelled(DatabaseError databaseError) {
                }

                public void onDataChange(DataSnapshot dataSnapshot) {
                    ChatUserModel chatUserModel;
                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                            try {
                                chatUserModel = (ChatUserModel) dataSnapshot2.getValue(ChatUserModel.class);
                            } catch (Exception unused) {
                                chatUserModel = new ChatUserModel();
                                chatUserModel.parseSnapshot(dataSnapshot2);
                            }
                            chatUserModel.uid = dataSnapshot2.getKey();
                            ChatUserListFragment.this.mLastKey = dataSnapshot2.getKey();
                            ChatUserListFragment.this.mUserLastKey = chatUserModel.name;
                            if (!TextUtils.equals(chatUserModel.uid, FirebaseAuth.getInstance().getCurrentUser().getUid()) && ChatUserListFragment.this.validateUserModel(chatUserModel)) {
                                ChatUserListFragment.this.setUserAdapter(chatUserModel);
                            }
                        }
                    }
                    if (dataSnapshot.getChildrenCount() != 20) {
                        ChatUserListFragment.this.mUserLastKey = null;
                    }
                    if (ChatUserListFragment.this.loadMoreProgressView != null) {
                        ChatUserListFragment.this.loadMoreProgressView.setVisibility(8);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public boolean validateUserModel(ChatUserModel chatUserModel) {
        return (chatUserModel == null || chatUserModel.getName() == null || chatUserModel.getName().isEmpty()) ? false : true;
    }

    public Fragment injectFragment(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
        return this;
    }
}
