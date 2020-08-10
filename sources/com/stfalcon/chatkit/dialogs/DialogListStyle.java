package com.stfalcon.chatkit.dialogs;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.stfalcon.chatkit.C2363R;
import com.stfalcon.chatkit.commons.Style;

class DialogListStyle extends Style {
    private int dialogAvatarHeight;
    private int dialogAvatarWidth;
    private int dialogDateColor;
    private int dialogDateSize;
    private int dialogDateStyle;
    private int dialogDividerColor;
    private boolean dialogDividerEnabled;
    private int dialogDividerLeftPadding;
    private int dialogDividerRightPadding;
    private int dialogItemBackground;
    private boolean dialogMessageAvatarEnabled;
    private int dialogMessageAvatarHeight;
    private int dialogMessageAvatarWidth;
    private int dialogMessageTextColor;
    private int dialogMessageTextSize;
    private int dialogMessageTextStyle;
    private int dialogTitleTextColor;
    private int dialogTitleTextSize;
    private int dialogTitleTextStyle;
    private int dialogUnreadBubbleBackgroundColor;
    private boolean dialogUnreadBubbleEnabled;
    private int dialogUnreadBubbleTextColor;
    private int dialogUnreadBubbleTextSize;
    private int dialogUnreadBubbleTextStyle;
    private int dialogUnreadDateColor;
    private int dialogUnreadDateStyle;
    private int dialogUnreadItemBackground;
    private int dialogUnreadMessageTextColor;
    private int dialogUnreadMessageTextStyle;
    private int dialogUnreadTitleTextColor;
    private int dialogUnreadTitleTextStyle;

    static DialogListStyle parse(Context context, AttributeSet attributeSet) {
        DialogListStyle dialogListStyle = new DialogListStyle(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C2363R.styleable.DialogsList);
        dialogListStyle.setDialogItemBackground(obtainStyledAttributes.getColor(C2363R.styleable.DialogsList_dialogItemBackground, dialogListStyle.getColor(C2363R.C2364color.transparent)));
        dialogListStyle.setDialogUnreadItemBackground(obtainStyledAttributes.getColor(C2363R.styleable.DialogsList_dialogUnreadItemBackground, dialogListStyle.getColor(C2363R.C2364color.transparent)));
        dialogListStyle.setDialogTitleTextColor(obtainStyledAttributes.getColor(C2363R.styleable.DialogsList_dialogTitleTextColor, dialogListStyle.getColor(C2363R.C2364color.dialog_title_text)));
        dialogListStyle.setDialogTitleTextSize(obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.DialogsList_dialogTitleTextSize, context.getResources().getDimensionPixelSize(C2363R.dimen.dialog_title_text_size)));
        dialogListStyle.setDialogTitleTextStyle(obtainStyledAttributes.getInt(C2363R.styleable.DialogsList_dialogTitleTextStyle, 0));
        dialogListStyle.setDialogUnreadTitleTextColor(obtainStyledAttributes.getColor(C2363R.styleable.DialogsList_dialogUnreadTitleTextColor, dialogListStyle.getColor(C2363R.C2364color.dialog_title_text)));
        dialogListStyle.setDialogUnreadTitleTextStyle(obtainStyledAttributes.getInt(C2363R.styleable.DialogsList_dialogUnreadTitleTextStyle, 0));
        dialogListStyle.setDialogMessageTextColor(obtainStyledAttributes.getColor(C2363R.styleable.DialogsList_dialogMessageTextColor, dialogListStyle.getColor(C2363R.C2364color.dialog_message_text)));
        dialogListStyle.setDialogMessageTextSize(obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.DialogsList_dialogMessageTextSize, context.getResources().getDimensionPixelSize(C2363R.dimen.dialog_message_text_size)));
        dialogListStyle.setDialogMessageTextStyle(obtainStyledAttributes.getInt(C2363R.styleable.DialogsList_dialogMessageTextStyle, 0));
        dialogListStyle.setDialogUnreadMessageTextColor(obtainStyledAttributes.getColor(C2363R.styleable.DialogsList_dialogUnreadMessageTextColor, dialogListStyle.getColor(C2363R.C2364color.dialog_message_text)));
        dialogListStyle.setDialogUnreadMessageTextStyle(obtainStyledAttributes.getInt(C2363R.styleable.DialogsList_dialogUnreadMessageTextStyle, 0));
        dialogListStyle.setDialogDateColor(obtainStyledAttributes.getColor(C2363R.styleable.DialogsList_dialogDateColor, dialogListStyle.getColor(C2363R.C2364color.dialog_date_text)));
        dialogListStyle.setDialogDateSize(obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.DialogsList_dialogDateSize, context.getResources().getDimensionPixelSize(C2363R.dimen.dialog_date_text_size)));
        dialogListStyle.setDialogDateStyle(obtainStyledAttributes.getInt(C2363R.styleable.DialogsList_dialogDateStyle, 0));
        dialogListStyle.setDialogUnreadDateColor(obtainStyledAttributes.getColor(C2363R.styleable.DialogsList_dialogUnreadDateColor, dialogListStyle.getColor(C2363R.C2364color.dialog_date_text)));
        dialogListStyle.setDialogUnreadDateStyle(obtainStyledAttributes.getInt(C2363R.styleable.DialogsList_dialogUnreadDateStyle, 0));
        dialogListStyle.setDialogUnreadBubbleEnabled(obtainStyledAttributes.getBoolean(C2363R.styleable.DialogsList_dialogUnreadBubbleEnabled, true));
        dialogListStyle.setDialogUnreadBubbleBackgroundColor(obtainStyledAttributes.getColor(C2363R.styleable.DialogsList_dialogUnreadBubbleBackgroundColor, dialogListStyle.getColor(C2363R.C2364color.dialog_unread_bubble)));
        dialogListStyle.setDialogUnreadBubbleTextColor(obtainStyledAttributes.getColor(C2363R.styleable.DialogsList_dialogUnreadBubbleTextColor, dialogListStyle.getColor(C2363R.C2364color.dialog_unread_text)));
        dialogListStyle.setDialogUnreadBubbleTextSize(obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.DialogsList_dialogUnreadBubbleTextSize, context.getResources().getDimensionPixelSize(C2363R.dimen.dialog_unread_bubble_text_size)));
        dialogListStyle.setDialogUnreadBubbleTextStyle(obtainStyledAttributes.getInt(C2363R.styleable.DialogsList_dialogUnreadBubbleTextStyle, 0));
        dialogListStyle.setDialogAvatarWidth(obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.DialogsList_dialogAvatarWidth, context.getResources().getDimensionPixelSize(C2363R.dimen.dialog_avatar_width)));
        dialogListStyle.setDialogAvatarHeight(obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.DialogsList_dialogAvatarHeight, context.getResources().getDimensionPixelSize(C2363R.dimen.dialog_avatar_height)));
        dialogListStyle.setDialogMessageAvatarEnabled(obtainStyledAttributes.getBoolean(C2363R.styleable.DialogsList_dialogMessageAvatarEnabled, true));
        dialogListStyle.setDialogMessageAvatarWidth(obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.DialogsList_dialogMessageAvatarWidth, context.getResources().getDimensionPixelSize(C2363R.dimen.dialog_last_message_avatar_width)));
        dialogListStyle.setDialogMessageAvatarHeight(obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.DialogsList_dialogMessageAvatarHeight, context.getResources().getDimensionPixelSize(C2363R.dimen.dialog_last_message_avatar_height)));
        dialogListStyle.setDialogDividerEnabled(obtainStyledAttributes.getBoolean(C2363R.styleable.DialogsList_dialogDividerEnabled, true));
        dialogListStyle.setDialogDividerColor(obtainStyledAttributes.getColor(C2363R.styleable.DialogsList_dialogDividerColor, dialogListStyle.getColor(C2363R.C2364color.dialog_divider)));
        dialogListStyle.setDialogDividerLeftPadding(obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.DialogsList_dialogDividerLeftPadding, context.getResources().getDimensionPixelSize(C2363R.dimen.dialog_divider_margin_left)));
        dialogListStyle.setDialogDividerRightPadding(obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.DialogsList_dialogDividerRightPadding, context.getResources().getDimensionPixelSize(C2363R.dimen.dialog_divider_margin_right)));
        obtainStyledAttributes.recycle();
        return dialogListStyle;
    }

    private DialogListStyle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: 0000 */
    public int getDialogTitleTextColor() {
        return this.dialogTitleTextColor;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogTitleTextColor(int i) {
        this.dialogTitleTextColor = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogTitleTextSize() {
        return this.dialogTitleTextSize;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogTitleTextSize(int i) {
        this.dialogTitleTextSize = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogTitleTextStyle() {
        return this.dialogTitleTextStyle;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogTitleTextStyle(int i) {
        this.dialogTitleTextStyle = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogUnreadTitleTextColor() {
        return this.dialogUnreadTitleTextColor;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogUnreadTitleTextColor(int i) {
        this.dialogUnreadTitleTextColor = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogUnreadTitleTextStyle() {
        return this.dialogUnreadTitleTextStyle;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogUnreadTitleTextStyle(int i) {
        this.dialogUnreadTitleTextStyle = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogMessageTextColor() {
        return this.dialogMessageTextColor;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogMessageTextColor(int i) {
        this.dialogMessageTextColor = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogMessageTextSize() {
        return this.dialogMessageTextSize;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogMessageTextSize(int i) {
        this.dialogMessageTextSize = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogMessageTextStyle() {
        return this.dialogMessageTextStyle;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogMessageTextStyle(int i) {
        this.dialogMessageTextStyle = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogUnreadMessageTextColor() {
        return this.dialogUnreadMessageTextColor;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogUnreadMessageTextColor(int i) {
        this.dialogUnreadMessageTextColor = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogUnreadMessageTextStyle() {
        return this.dialogUnreadMessageTextStyle;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogUnreadMessageTextStyle(int i) {
        this.dialogUnreadMessageTextStyle = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogDateColor() {
        return this.dialogDateColor;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogDateColor(int i) {
        this.dialogDateColor = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogDateSize() {
        return this.dialogDateSize;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogDateSize(int i) {
        this.dialogDateSize = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogDateStyle() {
        return this.dialogDateStyle;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogDateStyle(int i) {
        this.dialogDateStyle = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogUnreadDateColor() {
        return this.dialogUnreadDateColor;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogUnreadDateColor(int i) {
        this.dialogUnreadDateColor = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogUnreadDateStyle() {
        return this.dialogUnreadDateStyle;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogUnreadDateStyle(int i) {
        this.dialogUnreadDateStyle = i;
    }

    /* access modifiers changed from: 0000 */
    public boolean isDialogUnreadBubbleEnabled() {
        return this.dialogUnreadBubbleEnabled;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogUnreadBubbleEnabled(boolean z) {
        this.dialogUnreadBubbleEnabled = z;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogUnreadBubbleTextColor() {
        return this.dialogUnreadBubbleTextColor;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogUnreadBubbleTextColor(int i) {
        this.dialogUnreadBubbleTextColor = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogUnreadBubbleTextSize() {
        return this.dialogUnreadBubbleTextSize;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogUnreadBubbleTextSize(int i) {
        this.dialogUnreadBubbleTextSize = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogUnreadBubbleTextStyle() {
        return this.dialogUnreadBubbleTextStyle;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogUnreadBubbleTextStyle(int i) {
        this.dialogUnreadBubbleTextStyle = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogUnreadBubbleBackgroundColor() {
        return this.dialogUnreadBubbleBackgroundColor;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogUnreadBubbleBackgroundColor(int i) {
        this.dialogUnreadBubbleBackgroundColor = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogAvatarWidth() {
        return this.dialogAvatarWidth;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogAvatarWidth(int i) {
        this.dialogAvatarWidth = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogAvatarHeight() {
        return this.dialogAvatarHeight;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogAvatarHeight(int i) {
        this.dialogAvatarHeight = i;
    }

    /* access modifiers changed from: 0000 */
    public boolean isDialogDividerEnabled() {
        return this.dialogDividerEnabled;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogDividerEnabled(boolean z) {
        this.dialogDividerEnabled = z;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogDividerColor() {
        return this.dialogDividerColor;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogDividerColor(int i) {
        this.dialogDividerColor = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogDividerLeftPadding() {
        return this.dialogDividerLeftPadding;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogDividerLeftPadding(int i) {
        this.dialogDividerLeftPadding = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogDividerRightPadding() {
        return this.dialogDividerRightPadding;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogDividerRightPadding(int i) {
        this.dialogDividerRightPadding = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogItemBackground() {
        return this.dialogItemBackground;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogItemBackground(int i) {
        this.dialogItemBackground = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogUnreadItemBackground() {
        return this.dialogUnreadItemBackground;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogUnreadItemBackground(int i) {
        this.dialogUnreadItemBackground = i;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogMessageAvatarEnabled(boolean z) {
        this.dialogMessageAvatarEnabled = z;
    }

    /* access modifiers changed from: 0000 */
    public boolean isDialogMessageAvatarEnabled() {
        return this.dialogMessageAvatarEnabled;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogMessageAvatarWidth() {
        return this.dialogMessageAvatarWidth;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogMessageAvatarWidth(int i) {
        this.dialogMessageAvatarWidth = i;
    }

    /* access modifiers changed from: 0000 */
    public int getDialogMessageAvatarHeight() {
        return this.dialogMessageAvatarHeight;
    }

    /* access modifiers changed from: 0000 */
    public void setDialogMessageAvatarHeight(int i) {
        this.dialogMessageAvatarHeight = i;
    }
}
