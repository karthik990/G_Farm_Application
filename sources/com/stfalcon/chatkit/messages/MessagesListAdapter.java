package com.stfalcon.chatkit.messages;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import com.stfalcon.chatkit.C2363R;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.ViewHolder;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessageHolders.IncomingTextMessageViewHolder;
import com.stfalcon.chatkit.messages.MessageHolders.OutcomingTextMessageViewHolder;
import com.stfalcon.chatkit.utils.DateFormatter;
import com.stfalcon.chatkit.utils.DateFormatter.Template;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MessagesListAdapter<MESSAGE extends IMessage> extends Adapter<ViewHolder> implements OnLoadMoreListener {
    static boolean isSelectionModeEnabled;
    private com.stfalcon.chatkit.utils.DateFormatter.Formatter dateHeadersFormatter;
    private MessageHolders holders;
    private ImageLoader imageLoader;
    private List<Wrapper> items;
    private LayoutManager layoutManager;
    private OnLoadMoreListener loadMoreListener;
    private MessagesListStyle messagesListStyle;
    private OnMessageClickListener<MESSAGE> onMessageClickListener;
    private OnMessageLongClickListener<MESSAGE> onMessageLongClickListener;
    private OnMessageViewClickListener<MESSAGE> onMessageViewClickListener;
    private OnMessageViewLongClickListener<MESSAGE> onMessageViewLongClickListener;
    private int selectedItemsCount;
    /* access modifiers changed from: private */
    public SelectionListener selectionListener;
    private String senderId;
    private SparseArray<OnMessageViewClickListener> viewClickListenersArray;

    public interface OnLoadMoreListener {
        void onLoadMore(int i, int i2);
    }

    public interface SelectionListener {
        void onSelectionChanged(int i);
    }

    @Deprecated
    public static abstract class BaseMessageViewHolder<MESSAGE extends IMessage> extends com.stfalcon.chatkit.messages.MessageHolders.BaseMessageViewHolder<MESSAGE> {
        protected ImageLoader imageLoader;
        private boolean isSelected;

        public BaseMessageViewHolder(View view) {
            super(view);
        }

        public boolean isSelected() {
            return this.isSelected;
        }

        public boolean isSelectionModeEnabled() {
            return MessagesListAdapter.isSelectionModeEnabled;
        }

        public ImageLoader getImageLoader() {
            return this.imageLoader;
        }

        /* access modifiers changed from: protected */
        public void configureLinksBehavior(TextView textView) {
            textView.setLinksClickable(false);
            textView.setMovementMethod(new LinkMovementMethod() {
                public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
                    boolean onTouchEvent = !MessagesListAdapter.isSelectionModeEnabled ? super.onTouchEvent(textView, spannable, motionEvent) : false;
                    BaseMessageViewHolder.this.itemView.onTouchEvent(motionEvent);
                    return onTouchEvent;
                }
            });
        }
    }

    @Deprecated
    public static class DefaultDateHeaderViewHolder extends ViewHolder<Date> implements DefaultMessageViewHolder {
        protected String dateFormat;
        protected com.stfalcon.chatkit.utils.DateFormatter.Formatter dateHeadersFormatter;
        protected TextView text;

        public DefaultDateHeaderViewHolder(View view) {
            super(view);
            this.text = (TextView) view.findViewById(C2363R.C2366id.messageText);
        }

        public void onBind(Date date) {
            if (this.text != null) {
                String str = null;
                com.stfalcon.chatkit.utils.DateFormatter.Formatter formatter = this.dateHeadersFormatter;
                if (formatter != null) {
                    str = formatter.format(date);
                }
                TextView textView = this.text;
                if (str == null) {
                    str = DateFormatter.format(date, this.dateFormat);
                }
                textView.setText(str);
            }
        }

        public void applyStyle(MessagesListStyle messagesListStyle) {
            TextView textView = this.text;
            if (textView != null) {
                textView.setTextColor(messagesListStyle.getDateHeaderTextColor());
                this.text.setTextSize(0, (float) messagesListStyle.getDateHeaderTextSize());
                TextView textView2 = this.text;
                textView2.setTypeface(textView2.getTypeface(), messagesListStyle.getDateHeaderTextStyle());
                this.text.setPadding(messagesListStyle.getDateHeaderPadding(), messagesListStyle.getDateHeaderPadding(), messagesListStyle.getDateHeaderPadding(), messagesListStyle.getDateHeaderPadding());
            }
            this.dateFormat = messagesListStyle.getDateHeaderFormat();
            String str = this.dateFormat;
            if (str == null) {
                str = Template.STRING_DAY_MONTH_YEAR.get();
            }
            this.dateFormat = str;
        }
    }

    public interface Formatter<MESSAGE> {
        String format(MESSAGE message);
    }

    @Deprecated
    public static class HoldersConfig extends MessageHolders {
        @Deprecated
        public void setIncoming(Class<? extends com.stfalcon.chatkit.messages.MessageHolders.BaseMessageViewHolder<? extends IMessage>> cls, int i) {
            super.setIncomingTextConfig(cls, i);
        }

        @Deprecated
        public void setIncomingHolder(Class<? extends com.stfalcon.chatkit.messages.MessageHolders.BaseMessageViewHolder<? extends IMessage>> cls) {
            super.setIncomingTextHolder(cls);
        }

        @Deprecated
        public void setIncomingLayout(int i) {
            super.setIncomingTextLayout(i);
        }

        @Deprecated
        public void setOutcoming(Class<? extends com.stfalcon.chatkit.messages.MessageHolders.BaseMessageViewHolder<? extends IMessage>> cls, int i) {
            super.setOutcomingTextConfig(cls, i);
        }

        @Deprecated
        public void setOutcomingHolder(Class<? extends com.stfalcon.chatkit.messages.MessageHolders.BaseMessageViewHolder<? extends IMessage>> cls) {
            super.setOutcomingTextHolder(cls);
        }

        @Deprecated
        public void setOutcomingLayout(int i) {
            setOutcomingTextLayout(i);
        }

        @Deprecated
        public void setDateHeader(Class<? extends ViewHolder<Date>> cls, int i) {
            super.setDateHeaderConfig(cls, i);
        }
    }

    @Deprecated
    public static class IncomingMessageViewHolder<MESSAGE extends IMessage> extends IncomingTextMessageViewHolder<MESSAGE> implements DefaultMessageViewHolder {
        public IncomingMessageViewHolder(View view) {
            super(view);
        }
    }

    public interface OnMessageClickListener<MESSAGE extends IMessage> {
        void onMessageClick(MESSAGE message);
    }

    public interface OnMessageLongClickListener<MESSAGE extends IMessage> {
        void onMessageLongClick(MESSAGE message);
    }

    public interface OnMessageViewClickListener<MESSAGE extends IMessage> {
        void onMessageViewClick(View view, MESSAGE message);
    }

    public interface OnMessageViewLongClickListener<MESSAGE extends IMessage> {
        void onMessageViewLongClick(View view, MESSAGE message);
    }

    @Deprecated
    public static class OutcomingMessageViewHolder<MESSAGE extends IMessage> extends OutcomingTextMessageViewHolder<MESSAGE> {
        public OutcomingMessageViewHolder(View view) {
            super(view);
        }
    }

    private class Wrapper<DATA> {
        boolean isSelected;
        DATA item;

        Wrapper(DATA data) {
            this.item = data;
        }
    }

    public MessagesListAdapter(String str, ImageLoader imageLoader2) {
        this(str, new MessageHolders(), imageLoader2);
    }

    public MessagesListAdapter(String str, MessageHolders messageHolders, ImageLoader imageLoader2) {
        this.viewClickListenersArray = new SparseArray<>();
        this.senderId = str;
        this.holders = messageHolders;
        this.imageLoader = imageLoader2;
        this.items = new ArrayList();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return this.holders.getHolder(viewGroup, i, this.messagesListStyle);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Wrapper wrapper = (Wrapper) this.items.get(i);
        this.holders.bind(viewHolder, wrapper.item, wrapper.isSelected, this.imageLoader, getMessageClickListener(wrapper), getMessageLongClickListener(wrapper), this.dateHeadersFormatter, this.viewClickListenersArray);
    }

    public int getItemCount() {
        return this.items.size();
    }

    public int getItemViewType(int i) {
        return this.holders.getViewType(((Wrapper) this.items.get(i)).item, this.senderId);
    }

    public void onLoadMore(int i, int i2) {
        OnLoadMoreListener onLoadMoreListener = this.loadMoreListener;
        if (onLoadMoreListener != null) {
            onLoadMoreListener.onLoadMore(i, i2);
        }
    }

    public void addToStart(MESSAGE message, boolean z) {
        int i = 1;
        boolean z2 = !isPreviousSameDate(0, message.getCreatedAt());
        if (z2) {
            this.items.add(0, new Wrapper(message.getCreatedAt()));
        }
        this.items.add(0, new Wrapper(message));
        if (z2) {
            i = 2;
        }
        notifyItemRangeInserted(0, i);
        LayoutManager layoutManager2 = this.layoutManager;
        if (layoutManager2 != null && z) {
            layoutManager2.scrollToPosition(0);
        }
    }

    public void addToEnd(List<MESSAGE> list, boolean z) {
        if (z) {
            Collections.reverse(list);
        }
        if (!this.items.isEmpty()) {
            int size = this.items.size() - 1;
            if (DateFormatter.isSameDay(((IMessage) list.get(0)).getCreatedAt(), (Date) ((Wrapper) this.items.get(size)).item)) {
                this.items.remove(size);
                notifyItemRemoved(size);
            }
        }
        int size2 = this.items.size();
        generateDateHeaders(list);
        notifyItemRangeInserted(size2, this.items.size() - size2);
    }

    public void update(MESSAGE message) {
        update(message.getId(), message);
    }

    public void update(String str, MESSAGE message) {
        int messagePositionById = getMessagePositionById(str);
        if (messagePositionById >= 0) {
            this.items.set(messagePositionById, new Wrapper(message));
            notifyItemChanged(messagePositionById);
        }
    }

    public void delete(MESSAGE message) {
        deleteById(message.getId());
    }

    public void delete(List<MESSAGE> list) {
        for (MESSAGE id : list) {
            int messagePositionById = getMessagePositionById(id.getId());
            this.items.remove(messagePositionById);
            notifyItemRemoved(messagePositionById);
        }
        recountDateHeaders();
    }

    public void deleteById(String str) {
        int messagePositionById = getMessagePositionById(str);
        if (messagePositionById >= 0) {
            this.items.remove(messagePositionById);
            notifyItemRemoved(messagePositionById);
            recountDateHeaders();
        }
    }

    public void deleteByIds(String[] strArr) {
        for (String messagePositionById : strArr) {
            int messagePositionById2 = getMessagePositionById(messagePositionById);
            this.items.remove(messagePositionById2);
            notifyItemRemoved(messagePositionById2);
        }
        recountDateHeaders();
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public void clear() {
        this.items.clear();
    }

    public void enableSelectionMode(SelectionListener selectionListener2) {
        if (selectionListener2 != null) {
            this.selectionListener = selectionListener2;
            return;
        }
        throw new IllegalArgumentException("SelectionListener must not be null. Use `disableSelectionMode()` if you want tp disable selection mode");
    }

    public void disableSelectionMode() {
        this.selectionListener = null;
        unselectAllItems();
    }

    public ArrayList<MESSAGE> getSelectedMessages() {
        ArrayList<MESSAGE> arrayList = new ArrayList<>();
        for (Wrapper wrapper : this.items) {
            if ((wrapper.item instanceof IMessage) && wrapper.isSelected) {
                arrayList.add((IMessage) wrapper.item);
            }
        }
        return arrayList;
    }

    public String getSelectedMessagesText(Formatter<MESSAGE> formatter, boolean z) {
        String selectedText = getSelectedText(formatter, z);
        unselectAllItems();
        return selectedText;
    }

    public String copySelectedMessagesText(Context context, Formatter<MESSAGE> formatter, boolean z) {
        String selectedText = getSelectedText(formatter, z);
        copyToClipboard(context, selectedText);
        unselectAllItems();
        return selectedText;
    }

    public void unselectAllItems() {
        for (int i = 0; i < this.items.size(); i++) {
            Wrapper wrapper = (Wrapper) this.items.get(i);
            if (wrapper.isSelected) {
                wrapper.isSelected = false;
                notifyItemChanged(i);
            }
        }
        isSelectionModeEnabled = false;
        this.selectedItemsCount = 0;
        notifySelectionChanged();
    }

    public void deleteSelectedMessages() {
        delete((List<MESSAGE>) getSelectedMessages());
        unselectAllItems();
    }

    public void setOnMessageClickListener(OnMessageClickListener<MESSAGE> onMessageClickListener2) {
        this.onMessageClickListener = onMessageClickListener2;
    }

    public void setOnMessageViewClickListener(OnMessageViewClickListener<MESSAGE> onMessageViewClickListener2) {
        this.onMessageViewClickListener = onMessageViewClickListener2;
    }

    public void registerViewClickListener(int i, OnMessageViewClickListener<MESSAGE> onMessageViewClickListener2) {
        this.viewClickListenersArray.append(i, onMessageViewClickListener2);
    }

    public void setOnMessageLongClickListener(OnMessageLongClickListener<MESSAGE> onMessageLongClickListener2) {
        this.onMessageLongClickListener = onMessageLongClickListener2;
    }

    public void setOnMessageViewLongClickListener(OnMessageViewLongClickListener<MESSAGE> onMessageViewLongClickListener2) {
        this.onMessageViewLongClickListener = onMessageViewLongClickListener2;
    }

    public void setLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.loadMoreListener = onLoadMoreListener;
    }

    public void setDateHeadersFormatter(com.stfalcon.chatkit.utils.DateFormatter.Formatter formatter) {
        this.dateHeadersFormatter = formatter;
    }

    private void recountDateHeaders() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < this.items.size(); i++) {
            if (((Wrapper) this.items.get(i)).item instanceof Date) {
                if (i == 0) {
                    arrayList.add(Integer.valueOf(i));
                } else if (((Wrapper) this.items.get(i - 1)).item instanceof Date) {
                    arrayList.add(Integer.valueOf(i));
                }
            }
        }
        Collections.reverse(arrayList);
        for (Integer intValue : arrayList) {
            int intValue2 = intValue.intValue();
            this.items.remove(intValue2);
            notifyItemRemoved(intValue2);
        }
    }

    private void generateDateHeaders(List<MESSAGE> list) {
        int i = 0;
        while (i < list.size()) {
            IMessage iMessage = (IMessage) list.get(i);
            this.items.add(new Wrapper(iMessage));
            i++;
            if (list.size() > i) {
                if (!DateFormatter.isSameDay(iMessage.getCreatedAt(), ((IMessage) list.get(i)).getCreatedAt())) {
                    this.items.add(new Wrapper(iMessage.getCreatedAt()));
                }
            } else {
                this.items.add(new Wrapper(iMessage.getCreatedAt()));
            }
        }
    }

    /* access modifiers changed from: private */
    public int getMessagePositionById(String str) {
        for (int i = 0; i < this.items.size(); i++) {
            Wrapper wrapper = (Wrapper) this.items.get(i);
            if ((wrapper.item instanceof IMessage) && ((IMessage) wrapper.item).getId().contentEquals(str)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isPreviousSameDate(int i, Date date) {
        if (this.items.size() > i && (((Wrapper) this.items.get(i)).item instanceof IMessage)) {
            return DateFormatter.isSameDay(date, ((IMessage) ((Wrapper) this.items.get(i)).item).getCreatedAt());
        }
        return false;
    }

    private boolean isPreviousSameAuthor(String str, int i) {
        boolean z = true;
        int i2 = i + 1;
        if (this.items.size() <= i2) {
            return false;
        }
        if (!(((Wrapper) this.items.get(i2)).item instanceof IMessage) || !((IMessage) ((Wrapper) this.items.get(i2)).item).getUser().getId().contentEquals(str)) {
            z = false;
        }
        return z;
    }

    /* access modifiers changed from: private */
    public void incrementSelectedItemsCount() {
        this.selectedItemsCount++;
        notifySelectionChanged();
    }

    /* access modifiers changed from: private */
    public void decrementSelectedItemsCount() {
        boolean z = true;
        this.selectedItemsCount--;
        if (this.selectedItemsCount <= 0) {
            z = false;
        }
        isSelectionModeEnabled = z;
        notifySelectionChanged();
    }

    private void notifySelectionChanged() {
        SelectionListener selectionListener2 = this.selectionListener;
        if (selectionListener2 != null) {
            selectionListener2.onSelectionChanged(this.selectedItemsCount);
        }
    }

    /* access modifiers changed from: private */
    public void notifyMessageClicked(MESSAGE message) {
        OnMessageClickListener<MESSAGE> onMessageClickListener2 = this.onMessageClickListener;
        if (onMessageClickListener2 != null) {
            onMessageClickListener2.onMessageClick(message);
        }
    }

    /* access modifiers changed from: private */
    public void notifyMessageViewClicked(View view, MESSAGE message) {
        OnMessageViewClickListener<MESSAGE> onMessageViewClickListener2 = this.onMessageViewClickListener;
        if (onMessageViewClickListener2 != null) {
            onMessageViewClickListener2.onMessageViewClick(view, message);
        }
    }

    /* access modifiers changed from: private */
    public void notifyMessageLongClicked(MESSAGE message) {
        OnMessageLongClickListener<MESSAGE> onMessageLongClickListener2 = this.onMessageLongClickListener;
        if (onMessageLongClickListener2 != null) {
            onMessageLongClickListener2.onMessageLongClick(message);
        }
    }

    /* access modifiers changed from: private */
    public void notifyMessageViewLongClicked(View view, MESSAGE message) {
        OnMessageViewLongClickListener<MESSAGE> onMessageViewLongClickListener2 = this.onMessageViewLongClickListener;
        if (onMessageViewLongClickListener2 != null) {
            onMessageViewLongClickListener2.onMessageViewLongClick(view, message);
        }
    }

    private OnClickListener getMessageClickListener(final Wrapper<MESSAGE> wrapper) {
        return new OnClickListener() {
            public void onClick(View view) {
                if (MessagesListAdapter.this.selectionListener == null || !MessagesListAdapter.isSelectionModeEnabled) {
                    MessagesListAdapter.this.notifyMessageClicked((IMessage) wrapper.item);
                    MessagesListAdapter.this.notifyMessageViewClicked(view, (IMessage) wrapper.item);
                    return;
                }
                Wrapper wrapper = wrapper;
                wrapper.isSelected = !wrapper.isSelected;
                if (wrapper.isSelected) {
                    MessagesListAdapter.this.incrementSelectedItemsCount();
                } else {
                    MessagesListAdapter.this.decrementSelectedItemsCount();
                }
                IMessage iMessage = (IMessage) wrapper.item;
                MessagesListAdapter messagesListAdapter = MessagesListAdapter.this;
                messagesListAdapter.notifyItemChanged(messagesListAdapter.getMessagePositionById(iMessage.getId()));
            }
        };
    }

    private OnLongClickListener getMessageLongClickListener(final Wrapper<MESSAGE> wrapper) {
        return new OnLongClickListener() {
            public boolean onLongClick(View view) {
                if (MessagesListAdapter.this.selectionListener == null) {
                    MessagesListAdapter.this.notifyMessageLongClicked((IMessage) wrapper.item);
                    MessagesListAdapter.this.notifyMessageViewLongClicked(view, (IMessage) wrapper.item);
                    return true;
                }
                MessagesListAdapter.isSelectionModeEnabled = true;
                view.performClick();
                return true;
            }
        };
    }

    private String getSelectedText(Formatter<MESSAGE> formatter, boolean z) {
        String str;
        StringBuilder sb = new StringBuilder();
        ArrayList selectedMessages = getSelectedMessages();
        if (z) {
            Collections.reverse(selectedMessages);
        }
        Iterator it = selectedMessages.iterator();
        while (it.hasNext()) {
            IMessage iMessage = (IMessage) it.next();
            if (formatter == null) {
                str = iMessage.toString();
            } else {
                str = formatter.format(iMessage);
            }
            sb.append(str);
            sb.append("\n\n");
        }
        sb.replace(sb.length() - 2, sb.length(), "");
        return sb.toString();
    }

    private void copyToClipboard(Context context, String str) {
        ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(str, str));
    }

    /* access modifiers changed from: 0000 */
    public void setLayoutManager(LayoutManager layoutManager2) {
        this.layoutManager = layoutManager2;
    }

    /* access modifiers changed from: 0000 */
    public void setStyle(MessagesListStyle messagesListStyle2) {
        this.messagesListStyle = messagesListStyle2;
    }
}
