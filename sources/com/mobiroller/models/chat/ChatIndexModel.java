package com.mobiroller.models.chat;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;
import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;

public class ChatIndexModel implements Serializable, Comparable<ChatIndexModel> {
    @Exclude
    public ChatUserModel chatUserModel;
    @PropertyName("n")
    public String name;
    public String uid;
    @PropertyName("un")
    public String username;

    @Exclude
    public int compareTo(ChatIndexModel chatIndexModel) {
        Collator instance = Collator.getInstance(new Locale("tr", "TR"));
        instance.setStrength(0);
        return instance.compare(this.name, chatIndexModel.name);
    }
}
