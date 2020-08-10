package com.stfalcon.chatkit.messages;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.core.graphics.drawable.DrawableCompat;
import com.stfalcon.chatkit.C2363R;
import com.stfalcon.chatkit.commons.Style;

class MessageInputStyle extends Style {
    private static final int DEFAULT_MAX_LINES = 5;
    private int attachmentButtonBackground;
    private int attachmentButtonDefaultBgColor;
    private int attachmentButtonDefaultBgDisabledColor;
    private int attachmentButtonDefaultBgPressedColor;
    private int attachmentButtonDefaultIconColor;
    private int attachmentButtonDefaultIconDisabledColor;
    private int attachmentButtonDefaultIconPressedColor;
    private int attachmentButtonHeight;
    private int attachmentButtonIcon;
    private int attachmentButtonMargin;
    private int attachmentButtonWidth;
    private Drawable inputBackground;
    private int inputButtonBackground;
    private int inputButtonDefaultBgColor;
    private int inputButtonDefaultBgDisabledColor;
    private int inputButtonDefaultBgPressedColor;
    private int inputButtonDefaultIconColor;
    private int inputButtonDefaultIconDisabledColor;
    private int inputButtonDefaultIconPressedColor;
    private int inputButtonHeight;
    private int inputButtonIcon;
    private int inputButtonMargin;
    private int inputButtonWidth;
    private Drawable inputCursorDrawable;
    private int inputDefaultPaddingBottom;
    private int inputDefaultPaddingLeft;
    private int inputDefaultPaddingRight;
    private int inputDefaultPaddingTop;
    private String inputHint;
    private int inputHintColor;
    private int inputMaxLines;
    private String inputText;
    private int inputTextColor;
    private int inputTextSize;
    private boolean showAttachmentButton;

    static MessageInputStyle parse(Context context, AttributeSet attributeSet) {
        MessageInputStyle messageInputStyle = new MessageInputStyle(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C2363R.styleable.MessageInput);
        messageInputStyle.showAttachmentButton = obtainStyledAttributes.getBoolean(C2363R.styleable.MessageInput_showAttachmentButton, false);
        messageInputStyle.attachmentButtonBackground = obtainStyledAttributes.getResourceId(C2363R.styleable.MessageInput_attachmentButtonBackground, -1);
        messageInputStyle.attachmentButtonDefaultBgColor = obtainStyledAttributes.getColor(C2363R.styleable.MessageInput_attachmentButtonDefaultBgColor, messageInputStyle.getColor(C2363R.C2364color.white_four));
        messageInputStyle.attachmentButtonDefaultBgPressedColor = obtainStyledAttributes.getColor(C2363R.styleable.MessageInput_attachmentButtonDefaultBgPressedColor, messageInputStyle.getColor(C2363R.C2364color.white_five));
        messageInputStyle.attachmentButtonDefaultBgDisabledColor = obtainStyledAttributes.getColor(C2363R.styleable.MessageInput_attachmentButtonDefaultBgDisabledColor, messageInputStyle.getColor(C2363R.C2364color.transparent));
        messageInputStyle.attachmentButtonIcon = obtainStyledAttributes.getResourceId(C2363R.styleable.MessageInput_attachmentButtonIcon, -1);
        messageInputStyle.attachmentButtonDefaultIconColor = obtainStyledAttributes.getColor(C2363R.styleable.MessageInput_attachmentButtonDefaultIconColor, messageInputStyle.getColor(C2363R.C2364color.cornflower_blue_two));
        messageInputStyle.attachmentButtonDefaultIconPressedColor = obtainStyledAttributes.getColor(C2363R.styleable.MessageInput_attachmentButtonDefaultIconPressedColor, messageInputStyle.getColor(C2363R.C2364color.cornflower_blue_two_dark));
        messageInputStyle.attachmentButtonDefaultIconDisabledColor = obtainStyledAttributes.getColor(C2363R.styleable.MessageInput_attachmentButtonDefaultIconDisabledColor, messageInputStyle.getColor(C2363R.C2364color.cornflower_blue_light_40));
        messageInputStyle.attachmentButtonWidth = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessageInput_attachmentButtonWidth, messageInputStyle.getDimension(C2363R.dimen.input_button_width));
        messageInputStyle.attachmentButtonHeight = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessageInput_attachmentButtonHeight, messageInputStyle.getDimension(C2363R.dimen.input_button_height));
        messageInputStyle.attachmentButtonMargin = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessageInput_attachmentButtonMargin, messageInputStyle.getDimension(C2363R.dimen.input_button_margin));
        messageInputStyle.inputButtonBackground = obtainStyledAttributes.getResourceId(C2363R.styleable.MessageInput_inputButtonBackground, -1);
        messageInputStyle.inputButtonDefaultBgColor = obtainStyledAttributes.getColor(C2363R.styleable.MessageInput_inputButtonDefaultBgColor, messageInputStyle.getColor(C2363R.C2364color.cornflower_blue_two));
        messageInputStyle.inputButtonDefaultBgPressedColor = obtainStyledAttributes.getColor(C2363R.styleable.MessageInput_inputButtonDefaultBgPressedColor, messageInputStyle.getColor(C2363R.C2364color.cornflower_blue_two_dark));
        messageInputStyle.inputButtonDefaultBgDisabledColor = obtainStyledAttributes.getColor(C2363R.styleable.MessageInput_inputButtonDefaultBgDisabledColor, messageInputStyle.getColor(C2363R.C2364color.white_four));
        messageInputStyle.inputButtonIcon = obtainStyledAttributes.getResourceId(C2363R.styleable.MessageInput_inputButtonIcon, -1);
        messageInputStyle.inputButtonDefaultIconColor = obtainStyledAttributes.getColor(C2363R.styleable.MessageInput_inputButtonDefaultIconColor, messageInputStyle.getColor(C2363R.C2364color.white));
        messageInputStyle.inputButtonDefaultIconPressedColor = obtainStyledAttributes.getColor(C2363R.styleable.MessageInput_inputButtonDefaultIconPressedColor, messageInputStyle.getColor(C2363R.C2364color.white));
        messageInputStyle.inputButtonDefaultIconDisabledColor = obtainStyledAttributes.getColor(C2363R.styleable.MessageInput_inputButtonDefaultIconDisabledColor, messageInputStyle.getColor(C2363R.C2364color.warm_grey));
        messageInputStyle.inputButtonWidth = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessageInput_inputButtonWidth, messageInputStyle.getDimension(C2363R.dimen.input_button_width));
        messageInputStyle.inputButtonHeight = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessageInput_inputButtonHeight, messageInputStyle.getDimension(C2363R.dimen.input_button_height));
        messageInputStyle.inputButtonMargin = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessageInput_inputButtonMargin, messageInputStyle.getDimension(C2363R.dimen.input_button_margin));
        messageInputStyle.inputMaxLines = obtainStyledAttributes.getInt(C2363R.styleable.MessageInput_inputMaxLines, 5);
        messageInputStyle.inputHint = obtainStyledAttributes.getString(C2363R.styleable.MessageInput_inputHint);
        messageInputStyle.inputText = obtainStyledAttributes.getString(C2363R.styleable.MessageInput_inputText);
        messageInputStyle.inputTextSize = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessageInput_inputTextSize, messageInputStyle.getDimension(C2363R.dimen.input_text_size));
        messageInputStyle.inputTextColor = obtainStyledAttributes.getColor(C2363R.styleable.MessageInput_inputTextColor, messageInputStyle.getColor(C2363R.C2364color.dark_grey_two));
        messageInputStyle.inputHintColor = obtainStyledAttributes.getColor(C2363R.styleable.MessageInput_inputHintColor, messageInputStyle.getColor(C2363R.C2364color.warm_grey_three));
        messageInputStyle.inputBackground = obtainStyledAttributes.getDrawable(C2363R.styleable.MessageInput_inputBackground);
        messageInputStyle.inputCursorDrawable = obtainStyledAttributes.getDrawable(C2363R.styleable.MessageInput_inputCursorDrawable);
        obtainStyledAttributes.recycle();
        messageInputStyle.inputDefaultPaddingLeft = messageInputStyle.getDimension(C2363R.dimen.input_padding_left);
        messageInputStyle.inputDefaultPaddingRight = messageInputStyle.getDimension(C2363R.dimen.input_padding_right);
        messageInputStyle.inputDefaultPaddingTop = messageInputStyle.getDimension(C2363R.dimen.input_padding_top);
        messageInputStyle.inputDefaultPaddingBottom = messageInputStyle.getDimension(C2363R.dimen.input_padding_bottom);
        return messageInputStyle;
    }

    private MessageInputStyle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private Drawable getSelector(int i, int i2, int i3, int i4) {
        Drawable mutate = DrawableCompat.wrap(getVectorDrawable(i4)).mutate();
        DrawableCompat.setTintList(mutate, new ColorStateList(new int[][]{new int[]{16842910, -16842919}, new int[]{16842910, 16842919}, new int[]{-16842910}}, new int[]{i, i2, i3}));
        return mutate;
    }

    /* access modifiers changed from: 0000 */
    public boolean showAttachmentButton() {
        return this.showAttachmentButton;
    }

    /* access modifiers changed from: 0000 */
    public Drawable getAttachmentButtonBackground() {
        int i = this.attachmentButtonBackground;
        if (i == -1) {
            return getSelector(this.attachmentButtonDefaultBgColor, this.attachmentButtonDefaultBgPressedColor, this.attachmentButtonDefaultBgDisabledColor, C2363R.C2365drawable.mask);
        }
        return getDrawable(i);
    }

    /* access modifiers changed from: 0000 */
    public Drawable getAttachmentButtonIcon() {
        int i = this.attachmentButtonIcon;
        if (i == -1) {
            return getSelector(this.attachmentButtonDefaultIconColor, this.attachmentButtonDefaultIconPressedColor, this.attachmentButtonDefaultIconDisabledColor, C2363R.C2365drawable.ic_add_attachment);
        }
        return getDrawable(i);
    }

    /* access modifiers changed from: 0000 */
    public int getAttachmentButtonWidth() {
        return this.attachmentButtonWidth;
    }

    /* access modifiers changed from: 0000 */
    public int getAttachmentButtonHeight() {
        return this.attachmentButtonHeight;
    }

    /* access modifiers changed from: 0000 */
    public int getAttachmentButtonMargin() {
        return this.attachmentButtonMargin;
    }

    /* access modifiers changed from: 0000 */
    public Drawable getInputButtonBackground() {
        int i = this.inputButtonBackground;
        if (i == -1) {
            return getSelector(this.inputButtonDefaultBgColor, this.inputButtonDefaultBgPressedColor, this.inputButtonDefaultBgDisabledColor, C2363R.C2365drawable.mask);
        }
        return getDrawable(i);
    }

    /* access modifiers changed from: 0000 */
    public Drawable getInputButtonIcon() {
        int i = this.inputButtonIcon;
        if (i == -1) {
            return getSelector(this.inputButtonDefaultIconColor, this.inputButtonDefaultIconPressedColor, this.inputButtonDefaultIconDisabledColor, C2363R.C2365drawable.ic_send);
        }
        return getDrawable(i);
    }

    /* access modifiers changed from: 0000 */
    public int getInputButtonMargin() {
        return this.inputButtonMargin;
    }

    /* access modifiers changed from: 0000 */
    public int getInputButtonWidth() {
        return this.inputButtonWidth;
    }

    /* access modifiers changed from: 0000 */
    public int getInputButtonHeight() {
        return this.inputButtonHeight;
    }

    /* access modifiers changed from: 0000 */
    public int getInputMaxLines() {
        return this.inputMaxLines;
    }

    /* access modifiers changed from: 0000 */
    public String getInputHint() {
        return this.inputHint;
    }

    /* access modifiers changed from: 0000 */
    public String getInputText() {
        return this.inputText;
    }

    /* access modifiers changed from: 0000 */
    public int getInputTextSize() {
        return this.inputTextSize;
    }

    /* access modifiers changed from: 0000 */
    public int getInputTextColor() {
        return this.inputTextColor;
    }

    /* access modifiers changed from: 0000 */
    public int getInputHintColor() {
        return this.inputHintColor;
    }

    /* access modifiers changed from: 0000 */
    public Drawable getInputBackground() {
        return this.inputBackground;
    }

    /* access modifiers changed from: 0000 */
    public Drawable getInputCursorDrawable() {
        return this.inputCursorDrawable;
    }

    /* access modifiers changed from: 0000 */
    public int getInputDefaultPaddingLeft() {
        return this.inputDefaultPaddingLeft;
    }

    /* access modifiers changed from: 0000 */
    public int getInputDefaultPaddingRight() {
        return this.inputDefaultPaddingRight;
    }

    /* access modifiers changed from: 0000 */
    public int getInputDefaultPaddingTop() {
        return this.inputDefaultPaddingTop;
    }

    /* access modifiers changed from: 0000 */
    public int getInputDefaultPaddingBottom() {
        return this.inputDefaultPaddingBottom;
    }
}
