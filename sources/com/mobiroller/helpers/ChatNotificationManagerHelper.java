package com.mobiroller.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ChatNotificationManagerHelper {
    private final String SharedPrefName = "Mobiroller_Chat_Pref";
    private final String SharedPrefUsers = "Mobiroller_Chat_Users";
    private Editor editor;
    private String screenId;
    private SharedPreferences sharedPreferences;

    public ChatNotificationManagerHelper(Context context, String str) {
        this.screenId = str;
        StringBuilder sb = new StringBuilder();
        sb.append("Mobiroller_Chat_Pref_");
        sb.append(str);
        this.sharedPreferences = context.getSharedPreferences(sb.toString(), 0);
        this.editor = this.sharedPreferences.edit();
    }

    public void saveMessageViaUserUid(String str, String str2) {
        saveUserUid(str);
        String messageViaUserUid = getMessageViaUserUid(str);
        if (messageViaUserUid.equalsIgnoreCase("")) {
            Editor editor2 = this.editor;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(this.screenId);
            editor2.putString(String.valueOf(sb.toString().hashCode()), str2).apply();
            return;
        }
        Editor editor3 = this.editor;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(this.screenId);
        String valueOf = String.valueOf(sb2.toString().hashCode());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(messageViaUserUid);
        sb3.append(",");
        sb3.append(str2);
        editor3.putString(valueOf, sb3.toString()).apply();
    }

    public String getMessageViaUserUid(String str) {
        SharedPreferences sharedPreferences2 = this.sharedPreferences;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(this.screenId);
        boolean contains = sharedPreferences2.contains(String.valueOf(sb.toString().hashCode()));
        String str2 = "";
        if (!contains) {
            return str2;
        }
        SharedPreferences sharedPreferences3 = this.sharedPreferences;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(this.screenId);
        return sharedPreferences3.getString(String.valueOf(sb2.toString().hashCode()), str2);
    }

    public void clearMessagesViaUserUid(String str) {
        Editor edit = this.sharedPreferences.edit();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(this.screenId);
        edit.remove(String.valueOf(sb.toString().hashCode())).apply();
    }

    private void saveUserUid(String str) {
        String userUid = getUserUid();
        String str2 = "Mobiroller_Chat_Users";
        if (userUid.equalsIgnoreCase("")) {
            this.editor.putString(str2, str).apply();
            return;
        }
        Editor editor2 = this.editor;
        StringBuilder sb = new StringBuilder();
        sb.append(userUid);
        sb.append(",");
        sb.append(str);
        editor2.putString(str2, sb.toString()).apply();
    }

    private void clearUserUid() {
        this.editor.remove("Mobiroller_Chat_Users").apply();
    }

    private String getUserUid() {
        String str = "Mobiroller_Chat_Users";
        String str2 = "";
        return this.sharedPreferences.contains(str) ? this.sharedPreferences.getString(str, str2) : str2;
    }

    public void clearAll() {
        String userUid = getUserUid();
        String str = "";
        if (!userUid.equalsIgnoreCase(str)) {
            String str2 = ",";
            if (userUid.contains(str2)) {
                String[] split = userUid.split(str2);
                for (String clearMessagesViaUserUid : split) {
                    clearMessagesViaUserUid(clearMessagesViaUserUid);
                }
                clearUserUid();
            }
        }
        if (!userUid.equalsIgnoreCase(str)) {
            clearMessagesViaUserUid(userUid);
        }
        clearUserUid();
    }
}
