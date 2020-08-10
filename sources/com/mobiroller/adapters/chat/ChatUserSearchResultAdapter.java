package com.mobiroller.adapters.chat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.recyclerview.widget.SortedList;
import androidx.recyclerview.widget.SortedList.Callback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatIndexModel;
import com.mobiroller.models.chat.ChatUserModel;
import com.mobiroller.models.events.UserListEmptyEvent;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.viewholders.chat.ChatSearchResultViewHolder;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class ChatUserSearchResultAdapter extends Adapter<ViewHolder> {
    private Activity context;
    /* access modifiers changed from: private */
    public SortedList<ChatIndexModel> dataList;
    private List<ChatIndexModel> dataListCopy;
    private InterstitialAdsUtil interstitialAdsUtil;
    private RecyclerView mRecyclerView;

    public ChatUserSearchResultAdapter(Activity activity, final RecyclerView recyclerView, boolean z) {
        this.interstitialAdsUtil = new InterstitialAdsUtil(activity);
        this.mRecyclerView = recyclerView;
        this.dataList = new SortedList<>(ChatIndexModel.class, new Callback<ChatIndexModel>() {
            public int compare(ChatIndexModel chatIndexModel, ChatIndexModel chatIndexModel2) {
                return chatIndexModel.compareTo(chatIndexModel2);
            }

            public void onInserted(int i, int i2) {
                ChatUserSearchResultAdapter.this.notifyItemRangeInserted(i, i2);
            }

            public void onRemoved(int i, int i2) {
                ChatUserSearchResultAdapter.this.notifyItemRangeRemoved(i, i2);
            }

            public void onMoved(int i, int i2) {
                ChatUserSearchResultAdapter.this.notifyItemMoved(i, i2);
                ChatUserSearchResultAdapter.this.notifyItemChanged(i2);
                if (i2 == 0) {
                    try {
                        ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            public void onChanged(int i, int i2) {
                ChatUserSearchResultAdapter.this.notifyItemRangeChanged(i, i2);
            }

            public boolean areContentsTheSame(ChatIndexModel chatIndexModel, ChatIndexModel chatIndexModel2) {
                return chatIndexModel.name.equals(chatIndexModel2.name);
            }

            public boolean areItemsTheSame(ChatIndexModel chatIndexModel, ChatIndexModel chatIndexModel2) {
                return chatIndexModel.uid == chatIndexModel2.uid;
            }
        });
        this.context = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ChatSearchResultViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_user_list_item, viewGroup, false), this.interstitialAdsUtil);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((ChatSearchResultViewHolder) viewHolder).bind((ChatIndexModel) this.dataList.get(i), this.context);
    }

    public int getItemCount() {
        return this.dataList.size();
    }

    public void addItem(ChatIndexModel chatIndexModel) {
        this.dataList.add(chatIndexModel);
        getUser(chatIndexModel.uid);
        this.dataListCopy = new ArrayList();
        for (int i = 0; i < this.dataList.size(); i++) {
            this.dataListCopy.add(this.dataList.get(i));
        }
        EventBus.getDefault().post(new UserListEmptyEvent());
    }

    private void getUser(String str) {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).child(str).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                ChatUserModel chatUserModel;
                try {
                    chatUserModel = (ChatUserModel) dataSnapshot.getValue(ChatUserModel.class);
                } catch (Exception unused) {
                    chatUserModel = new ChatUserModel();
                    chatUserModel.parseSnapshot(dataSnapshot);
                }
                if (chatUserModel != null) {
                    chatUserModel.uid = dataSnapshot.getKey();
                    int i = 0;
                    while (true) {
                        if (i >= ChatUserSearchResultAdapter.this.dataList.size()) {
                            break;
                        } else if (chatUserModel.uid.equals(((ChatIndexModel) ChatUserSearchResultAdapter.this.dataList.get(i)).uid)) {
                            ((ChatIndexModel) ChatUserSearchResultAdapter.this.dataList.get(i)).chatUserModel = chatUserModel;
                            ChatUserSearchResultAdapter.this.notifyItemChanged(i);
                            break;
                        } else {
                            i++;
                        }
                    }
                }
            }
        });
    }

    public void checkItem(ChatIndexModel chatIndexModel) {
        if (chatIndexModel != null && chatIndexModel.uid != null && chatIndexModel.name != null) {
            boolean z = false;
            for (int i = 0; i < this.dataList.size(); i++) {
                if (chatIndexModel.uid.equalsIgnoreCase(((ChatIndexModel) this.dataList.get(i)).uid)) {
                    this.dataList.updateItemAt(i, chatIndexModel);
                    z = true;
                }
            }
            if (!z) {
                addItem(chatIndexModel);
            }
            this.dataListCopy = new ArrayList();
            for (int i2 = 0; i2 < this.dataList.size(); i2++) {
                this.dataListCopy.add(this.dataList.get(i2));
            }
        }
    }

    public void removeItem(ChatUserModel chatUserModel) {
        for (int i = 0; i < this.dataList.size(); i++) {
            if (((ChatIndexModel) this.dataList.get(i)).uid.equalsIgnoreCase(chatUserModel.uid)) {
                this.dataList.removeItemAt(i);
            }
        }
    }
}
