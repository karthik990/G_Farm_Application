package com.mobiroller.models.chat;

import com.google.firebase.database.PropertyName;
import java.io.Serializable;

public class ChatMetaModel implements Serializable {
    @PropertyName("t")
    public String title;
    @PropertyName("sn")
    public String type;
    @PropertyName("v")
    public String value;

    public ChatMetaModel() {
    }

    public ChatMetaModel(String str, String str2, String str3) {
        this.type = str;
        this.value = str2;
        this.title = str3;
    }
}
