package com.stfalcon.chatkit.messages;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.stfalcon.chatkit.commons.models.IMessage;

public class MessagesList extends RecyclerView {
    private MessagesListStyle messagesListStyle;

    public MessagesList(Context context) {
        super(context);
    }

    public MessagesList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        parseStyle(context, attributeSet);
    }

    public MessagesList(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        parseStyle(context, attributeSet);
    }

    public void setAdapter(Adapter adapter) {
        throw new IllegalArgumentException("You can't set adapter to MessagesList. Use #setAdapter(MessagesListAdapter) instead.");
    }

    public <MESSAGE extends IMessage> void setAdapter(MessagesListAdapter<MESSAGE> messagesListAdapter) {
        setAdapter(messagesListAdapter, true);
    }

    public <MESSAGE extends IMessage> void setAdapter(MessagesListAdapter<MESSAGE> messagesListAdapter, boolean z) {
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setSupportsChangeAnimations(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), 1, z);
        setItemAnimator(defaultItemAnimator);
        setLayoutManager(linearLayoutManager);
        messagesListAdapter.setLayoutManager(linearLayoutManager);
        messagesListAdapter.setStyle(this.messagesListStyle);
        addOnScrollListener(new RecyclerScrollMoreListener(linearLayoutManager, messagesListAdapter));
        super.setAdapter(messagesListAdapter);
    }

    private void parseStyle(Context context, AttributeSet attributeSet) {
        this.messagesListStyle = MessagesListStyle.parse(context, attributeSet);
    }
}
