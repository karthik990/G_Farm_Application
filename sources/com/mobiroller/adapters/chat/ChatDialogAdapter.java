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
import com.google.firebase.auth.FirebaseAuth;
import com.mobiroller.helpers.FirebaseChatHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatDialogUserEvent;
import com.mobiroller.models.chat.ChatModel;
import com.mobiroller.models.events.ChatIsReadEvent;
import com.mobiroller.models.events.MessageListEmptyEvent;
import com.mobiroller.models.events.RemoveDialogEvent;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.viewholders.chat.ChatArchivedDialogsViewHolder;
import com.mobiroller.viewholders.chat.ChatDialogViewHolder;
import java.util.ArrayList;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ChatDialogAdapter extends Adapter<ViewHolder> {
    private final int ARCHIVED_VIEW = 1;
    private final int CHAT_VIEW = 0;
    private Activity context;
    private SortedList<Object> dataList;
    private FirebaseChatHelper firebaseChatHelper;
    private InterstitialAdsUtil interstitialAdsUtil;
    /* access modifiers changed from: private */
    public RecyclerView mRecyclerView;
    private String screenId;

    public ChatDialogAdapter(Activity activity, String str, FirebaseChatHelper firebaseChatHelper2, final RecyclerView recyclerView) {
        this.firebaseChatHelper = firebaseChatHelper2;
        this.interstitialAdsUtil = new InterstitialAdsUtil(activity);
        this.screenId = str;
        this.mRecyclerView = recyclerView;
        this.context = activity;
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.dataList = new SortedList<>(Object.class, new Callback<Object>() {
            public boolean areContentsTheSame(Object obj, Object obj2) {
                return false;
            }

            public boolean areItemsTheSame(Object obj, Object obj2) {
                return false;
            }

            public int compare(Object obj, Object obj2) {
                if (obj instanceof ChatModel) {
                    return ((ChatModel) obj).compareTo(obj2);
                }
                return 1;
            }

            public void onInserted(int i, int i2) {
                boolean z = i == 0 && ((LinearLayoutManager) ChatDialogAdapter.this.mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition() == 0;
                ChatDialogAdapter.this.notifyItemRangeInserted(i, i2);
                if (z) {
                    recyclerView.scrollToPosition(0);
                }
            }

            public void onRemoved(int i, int i2) {
                ChatDialogAdapter.this.notifyItemRangeRemoved(i, i2);
            }

            public void onMoved(int i, int i2) {
                boolean z = i2 == 0 && ((LinearLayoutManager) ChatDialogAdapter.this.mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition() == 0;
                ChatDialogAdapter.this.notifyItemMoved(i, i2);
                if (z) {
                    recyclerView.scrollToPosition(0);
                }
            }

            public void onChanged(int i, int i2) {
                boolean z = i == 0 && ((LinearLayoutManager) ChatDialogAdapter.this.mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition() == 0;
                ChatDialogAdapter.this.notifyItemRangeChanged(i, i2);
                if (z) {
                    recyclerView.scrollToPosition(0);
                }
            }
        });
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new ChatDialogViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_dialog_list_item, viewGroup, false), this.screenId, this.interstitialAdsUtil);
        }
        return new ChatArchivedDialogsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_dialog_archived_list_item, viewGroup, false), this.interstitialAdsUtil);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (FirebaseAuth.getInstance() != null && FirebaseAuth.getInstance().getCurrentUser() != null && FirebaseAuth.getInstance().getCurrentUser().getUid() != null) {
            if (this.dataList.get(i) instanceof ChatModel) {
                ((ChatDialogViewHolder) viewHolder).bind((ChatModel) this.dataList.get(i), this.context);
            } else if (this.dataList.get(i) instanceof ArrayList) {
                ((ChatArchivedDialogsViewHolder) viewHolder).bind((ArrayList) this.dataList.get(i), this.context);
            }
        }
    }

    public int getItemViewType(int i) {
        return this.dataList.get(i) instanceof ChatModel ? 0 : 1;
    }

    public int getItemCount() {
        return this.dataList.size();
    }

    public void addItem(ChatModel chatModel) {
        boolean z = false;
        if (!chatModel.isArchived()) {
            if (this.dataList.size() == 0) {
                this.dataList.add(chatModel);
            } else {
                checkIsArchived(chatModel);
                int i = 0;
                while (true) {
                    if (i >= this.dataList.size()) {
                        break;
                    } else if (this.dataList.get(i) instanceof ChatModel) {
                        this.dataList.add(chatModel);
                        z = true;
                        break;
                    } else {
                        i++;
                    }
                }
                if (!z) {
                    if (hasArchived()) {
                        this.dataList.add(chatModel);
                    } else {
                        this.dataList.add(chatModel);
                    }
                }
            }
        } else if (this.dataList.size() == 0) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(chatModel);
            this.dataList.add(arrayList);
        } else if (this.dataList.get(getItemCount() - 1) instanceof ChatModel) {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(chatModel);
            this.dataList.add(arrayList2);
        } else if (this.dataList.get(getItemCount() - 1) instanceof ArrayList) {
            ArrayList arrayList3 = (ArrayList) this.dataList.get(getItemCount() - 1);
            int i2 = 0;
            while (true) {
                if (i2 >= arrayList3.size()) {
                    z = true;
                    break;
                } else if (((ChatModel) arrayList3.get(i2)).getRealReceiverUid().equalsIgnoreCase(chatModel.getRealReceiverUid())) {
                    break;
                } else {
                    i2++;
                }
            }
            if (z) {
                arrayList3.add(chatModel);
                this.dataList.updateItemAt(getItemCount() - 1, arrayList3);
            }
        }
        this.firebaseChatHelper.getUserFromFirebase(chatModel.getRealReceiverUid(), true);
        EventBus.getDefault().post(new MessageListEmptyEvent());
    }

    public void checkItem(ChatModel chatModel) {
        if (!chatModel.isArchived()) {
            boolean z = false;
            int i = 0;
            while (true) {
                if (i < this.dataList.size()) {
                    if ((this.dataList.get(i) instanceof ChatModel) && chatModel.getRealReceiverUid().equalsIgnoreCase(((ChatModel) this.dataList.get(i)).getRealReceiverUid())) {
                        chatModel.setRealRecevierNameAndImage(((ChatModel) this.dataList.get(i)).getRealRecevierName(), ((ChatModel) this.dataList.get(i)).getRealRecevierImageUrl());
                        chatModel.setOnline(((ChatModel) this.dataList.get(i)).isOnline());
                        this.dataList.updateItemAt(i, chatModel);
                        notifyItemChanged(i);
                        z = true;
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            if (!z) {
                addItem(chatModel);
            }
        } else {
            addItem(chatModel);
        }
        EventBus.getDefault().post(new MessageListEmptyEvent());
    }

    @Subscribe
    public void notifyUser(ChatDialogUserEvent chatDialogUserEvent) {
        for (int i = 0; i < this.dataList.size(); i++) {
            if (this.dataList.get(i) instanceof ChatModel) {
                if (chatDialogUserEvent.getUserModel().uid.equalsIgnoreCase(((ChatModel) this.dataList.get(i)).getReceiverUid())) {
                    ((ChatModel) this.dataList.get(i)).setReceiverName(chatDialogUserEvent.getUserModel().getName());
                    ((ChatModel) this.dataList.get(i)).setReceiverImageUrl(chatDialogUserEvent.getUserModel().imageUrl);
                    ((ChatModel) this.dataList.get(i)).setOnline(chatDialogUserEvent.getUserModel().isOnline);
                    ((ChatModel) this.dataList.get(i)).receiverChatRoleId = chatDialogUserEvent.getUserModel().chatRoleIdString;
                    notifyItemChanged(i);
                } else if (chatDialogUserEvent.getUserModel().uid.equalsIgnoreCase(((ChatModel) this.dataList.get(i)).getSenderUid())) {
                    ((ChatModel) this.dataList.get(i)).setSenderName(chatDialogUserEvent.getUserModel().getName());
                    ((ChatModel) this.dataList.get(i)).setSenderImageUrl(chatDialogUserEvent.getUserModel().imageUrl);
                    ((ChatModel) this.dataList.get(i)).setOnline(chatDialogUserEvent.getUserModel().isOnline);
                    ((ChatModel) this.dataList.get(i)).senderChatRoleId = chatDialogUserEvent.getUserModel().chatRoleIdString;
                    notifyItemChanged(i);
                }
            } else if (this.dataList.get(i) instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) this.dataList.get(i);
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    if (chatDialogUserEvent.getUserModel().uid.equalsIgnoreCase(((ChatModel) arrayList.get(i2)).getReceiverUid())) {
                        ((ChatModel) arrayList.get(i2)).setReceiverName(chatDialogUserEvent.getUserModel().getName());
                        ((ChatModel) arrayList.get(i2)).setReceiverImageUrl(chatDialogUserEvent.getUserModel().imageUrl);
                        ((ChatModel) arrayList.get(i2)).setOnline(chatDialogUserEvent.getUserModel().isOnline);
                        ((ChatModel) arrayList.get(i2)).receiverChatRoleId = chatDialogUserEvent.getUserModel().chatRoleIdString;
                        this.dataList.updateItemAt(i, arrayList);
                    } else if (chatDialogUserEvent.getUserModel().uid.equalsIgnoreCase(((ChatModel) arrayList.get(i2)).getSenderUid())) {
                        ((ChatModel) arrayList.get(i2)).setSenderName(chatDialogUserEvent.getUserModel().getName());
                        ((ChatModel) arrayList.get(i2)).setSenderImageUrl(chatDialogUserEvent.getUserModel().imageUrl);
                        ((ChatModel) arrayList.get(i2)).setOnline(chatDialogUserEvent.getUserModel().isOnline);
                        ((ChatModel) arrayList.get(i2)).senderChatRoleId = chatDialogUserEvent.getUserModel().chatRoleIdString;
                        this.dataList.updateItemAt(i, arrayList);
                    }
                }
            }
        }
    }

    @Subscribe
    public void removeItem(RemoveDialogEvent removeDialogEvent) {
        if (!removeDialogEvent.uid.equalsIgnoreCase("-1")) {
            int i = 0;
            while (true) {
                if (i < this.dataList.size()) {
                    if ((this.dataList.get(i) instanceof ChatModel) && removeDialogEvent.uid.equalsIgnoreCase(((ChatModel) this.dataList.get(i)).getRealReceiverUid())) {
                        this.dataList.removeItemAt(i);
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
        }
        EventBus.getDefault().post(new MessageListEmptyEvent());
    }

    private void checkIsArchived(ChatModel chatModel) {
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= this.dataList.size()) {
                break;
            } else if (this.dataList.get(i2) instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) this.dataList.get(i2);
                while (true) {
                    if (i >= arrayList.size()) {
                        break;
                    } else if (((ChatModel) arrayList.get(i)).getRealReceiverUid().equalsIgnoreCase(chatModel.getRealReceiverUid())) {
                        arrayList.remove(i);
                        if (arrayList.size() != 0) {
                            this.dataList.updateItemAt(i2, arrayList);
                        } else {
                            this.dataList.removeItemAt(i2);
                        }
                    } else {
                        i++;
                    }
                }
            } else {
                i2++;
            }
        }
        EventBus.getDefault().post(new MessageListEmptyEvent());
    }

    private boolean hasArchived() {
        for (int i = 0; i < this.dataList.size(); i++) {
            if (this.dataList.get(i) instanceof ArrayList) {
                return true;
            }
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setIsRead(ChatIsReadEvent chatIsReadEvent) {
        if (chatIsReadEvent.screenId.equalsIgnoreCase(this.screenId)) {
            for (int i = 0; i < this.dataList.size(); i++) {
                if ((this.dataList.get(i) instanceof ChatModel) && ((ChatModel) this.dataList.get(i)).getRealReceiverUid().equalsIgnoreCase(chatIsReadEvent.getUid())) {
                    ((ChatModel) this.dataList.get(i)).isRead = chatIsReadEvent.isRead();
                    notifyItemChanged(i);
                }
            }
        }
    }
}
