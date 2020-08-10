package com.stfalcon.chatkit.messages;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.core.graphics.drawable.DrawableCompat;
import com.stfalcon.chatkit.C2363R;
import com.stfalcon.chatkit.commons.Style;

class MessagesListStyle extends Style {
    private String dateHeaderFormat;
    private int dateHeaderPadding;
    private int dateHeaderTextColor;
    private int dateHeaderTextSize;
    private int dateHeaderTextStyle;
    private int incomingAvatarHeight;
    private int incomingAvatarWidth;
    private int incomingBubbleDrawable;
    private int incomingDefaultBubbleColor;
    private int incomingDefaultBubblePaddingBottom;
    private int incomingDefaultBubblePaddingLeft;
    private int incomingDefaultBubblePaddingRight;
    private int incomingDefaultBubblePaddingTop;
    private int incomingDefaultBubblePressedColor;
    private int incomingDefaultBubbleSelectedColor;
    private int incomingDefaultImageOverlayPressedColor;
    private int incomingDefaultImageOverlaySelectedColor;
    private int incomingImageOverlayDrawable;
    private int incomingImageTimeTextColor;
    private int incomingImageTimeTextSize;
    private int incomingImageTimeTextStyle;
    private int incomingTextColor;
    private int incomingTextLinkColor;
    private int incomingTextSize;
    private int incomingTextStyle;
    private int incomingTimeTextColor;
    private int incomingTimeTextSize;
    private int incomingTimeTextStyle;
    private int outcomingBubbleDrawable;
    private int outcomingDefaultBubbleColor;
    private int outcomingDefaultBubblePaddingBottom;
    private int outcomingDefaultBubblePaddingLeft;
    private int outcomingDefaultBubblePaddingRight;
    private int outcomingDefaultBubblePaddingTop;
    private int outcomingDefaultBubblePressedColor;
    private int outcomingDefaultBubbleSelectedColor;
    private int outcomingDefaultImageOverlayPressedColor;
    private int outcomingDefaultImageOverlaySelectedColor;
    private int outcomingImageOverlayDrawable;
    private int outcomingImageTimeTextColor;
    private int outcomingImageTimeTextSize;
    private int outcomingImageTimeTextStyle;
    private int outcomingTextColor;
    private int outcomingTextLinkColor;
    private int outcomingTextSize;
    private int outcomingTextStyle;
    private int outcomingTimeTextColor;
    private int outcomingTimeTextSize;
    private int outcomingTimeTextStyle;
    private int textAutoLinkMask;

    static MessagesListStyle parse(Context context, AttributeSet attributeSet) {
        MessagesListStyle messagesListStyle = new MessagesListStyle(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C2363R.styleable.MessagesList);
        messagesListStyle.textAutoLinkMask = obtainStyledAttributes.getInt(C2363R.styleable.MessagesList_textAutoLink, 0);
        messagesListStyle.incomingTextLinkColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_incomingTextLinkColor, messagesListStyle.getSystemAccentColor());
        messagesListStyle.outcomingTextLinkColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_outcomingTextLinkColor, messagesListStyle.getSystemAccentColor());
        messagesListStyle.incomingAvatarWidth = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_incomingAvatarWidth, messagesListStyle.getDimension(C2363R.dimen.message_avatar_width));
        messagesListStyle.incomingAvatarHeight = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_incomingAvatarHeight, messagesListStyle.getDimension(C2363R.dimen.message_avatar_height));
        messagesListStyle.incomingBubbleDrawable = obtainStyledAttributes.getResourceId(C2363R.styleable.MessagesList_incomingBubbleDrawable, -1);
        messagesListStyle.incomingDefaultBubbleColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_incomingDefaultBubbleColor, messagesListStyle.getColor(C2363R.C2364color.white_two));
        messagesListStyle.incomingDefaultBubblePressedColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_incomingDefaultBubblePressedColor, messagesListStyle.getColor(C2363R.C2364color.white_two));
        messagesListStyle.incomingDefaultBubbleSelectedColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_incomingDefaultBubbleSelectedColor, messagesListStyle.getColor(C2363R.C2364color.cornflower_blue_two_24));
        messagesListStyle.incomingImageOverlayDrawable = obtainStyledAttributes.getResourceId(C2363R.styleable.MessagesList_incomingImageOverlayDrawable, -1);
        messagesListStyle.incomingDefaultImageOverlayPressedColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_incomingDefaultImageOverlayPressedColor, messagesListStyle.getColor(C2363R.C2364color.transparent));
        messagesListStyle.incomingDefaultImageOverlaySelectedColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_incomingDefaultImageOverlaySelectedColor, messagesListStyle.getColor(C2363R.C2364color.cornflower_blue_light_40));
        messagesListStyle.incomingDefaultBubblePaddingLeft = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_incomingBubblePaddingLeft, messagesListStyle.getDimension(C2363R.dimen.message_padding_left));
        messagesListStyle.incomingDefaultBubblePaddingRight = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_incomingBubblePaddingRight, messagesListStyle.getDimension(C2363R.dimen.message_padding_right));
        messagesListStyle.incomingDefaultBubblePaddingTop = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_incomingBubblePaddingTop, messagesListStyle.getDimension(C2363R.dimen.message_padding_top));
        messagesListStyle.incomingDefaultBubblePaddingBottom = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_incomingBubblePaddingBottom, messagesListStyle.getDimension(C2363R.dimen.message_padding_bottom));
        messagesListStyle.incomingTextColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_incomingTextColor, messagesListStyle.getColor(C2363R.C2364color.dark_grey_two));
        messagesListStyle.incomingTextSize = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_incomingTextSize, messagesListStyle.getDimension(C2363R.dimen.message_text_size));
        messagesListStyle.incomingTextStyle = obtainStyledAttributes.getInt(C2363R.styleable.MessagesList_incomingTextStyle, 0);
        messagesListStyle.incomingTimeTextColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_incomingTimeTextColor, messagesListStyle.getColor(C2363R.C2364color.warm_grey_four));
        messagesListStyle.incomingTimeTextSize = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_incomingTimeTextSize, messagesListStyle.getDimension(C2363R.dimen.message_time_text_size));
        messagesListStyle.incomingTimeTextStyle = obtainStyledAttributes.getInt(C2363R.styleable.MessagesList_incomingTimeTextStyle, 0);
        messagesListStyle.incomingImageTimeTextColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_incomingImageTimeTextColor, messagesListStyle.getColor(C2363R.C2364color.warm_grey_four));
        messagesListStyle.incomingImageTimeTextSize = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_incomingImageTimeTextSize, messagesListStyle.getDimension(C2363R.dimen.message_time_text_size));
        messagesListStyle.incomingImageTimeTextStyle = obtainStyledAttributes.getInt(C2363R.styleable.MessagesList_incomingImageTimeTextStyle, 0);
        messagesListStyle.outcomingBubbleDrawable = obtainStyledAttributes.getResourceId(C2363R.styleable.MessagesList_outcomingBubbleDrawable, -1);
        messagesListStyle.outcomingDefaultBubbleColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_outcomingDefaultBubbleColor, messagesListStyle.getColor(C2363R.C2364color.cornflower_blue_two));
        messagesListStyle.outcomingDefaultBubblePressedColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_outcomingDefaultBubblePressedColor, messagesListStyle.getColor(C2363R.C2364color.cornflower_blue_two));
        messagesListStyle.outcomingDefaultBubbleSelectedColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_outcomingDefaultBubbleSelectedColor, messagesListStyle.getColor(C2363R.C2364color.cornflower_blue_two_24));
        messagesListStyle.outcomingImageOverlayDrawable = obtainStyledAttributes.getResourceId(C2363R.styleable.MessagesList_outcomingImageOverlayDrawable, -1);
        messagesListStyle.outcomingDefaultImageOverlayPressedColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_outcomingDefaultImageOverlayPressedColor, messagesListStyle.getColor(C2363R.C2364color.transparent));
        messagesListStyle.outcomingDefaultImageOverlaySelectedColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_outcomingDefaultImageOverlaySelectedColor, messagesListStyle.getColor(C2363R.C2364color.cornflower_blue_light_40));
        messagesListStyle.outcomingDefaultBubblePaddingLeft = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_outcomingBubblePaddingLeft, messagesListStyle.getDimension(C2363R.dimen.message_padding_left));
        messagesListStyle.outcomingDefaultBubblePaddingRight = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_outcomingBubblePaddingRight, messagesListStyle.getDimension(C2363R.dimen.message_padding_right));
        messagesListStyle.outcomingDefaultBubblePaddingTop = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_outcomingBubblePaddingTop, messagesListStyle.getDimension(C2363R.dimen.message_padding_top));
        messagesListStyle.outcomingDefaultBubblePaddingBottom = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_outcomingBubblePaddingBottom, messagesListStyle.getDimension(C2363R.dimen.message_padding_bottom));
        messagesListStyle.outcomingTextColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_outcomingTextColor, messagesListStyle.getColor(C2363R.C2364color.white));
        messagesListStyle.outcomingTextSize = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_outcomingTextSize, messagesListStyle.getDimension(C2363R.dimen.message_text_size));
        messagesListStyle.outcomingTextStyle = obtainStyledAttributes.getInt(C2363R.styleable.MessagesList_outcomingTextStyle, 0);
        messagesListStyle.outcomingTimeTextColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_outcomingTimeTextColor, messagesListStyle.getColor(C2363R.C2364color.white60));
        messagesListStyle.outcomingTimeTextSize = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_outcomingTimeTextSize, messagesListStyle.getDimension(C2363R.dimen.message_time_text_size));
        messagesListStyle.outcomingTimeTextStyle = obtainStyledAttributes.getInt(C2363R.styleable.MessagesList_outcomingTimeTextStyle, 0);
        messagesListStyle.outcomingImageTimeTextColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_outcomingImageTimeTextColor, messagesListStyle.getColor(C2363R.C2364color.warm_grey_four));
        messagesListStyle.outcomingImageTimeTextSize = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_outcomingImageTimeTextSize, messagesListStyle.getDimension(C2363R.dimen.message_time_text_size));
        messagesListStyle.outcomingImageTimeTextStyle = obtainStyledAttributes.getInt(C2363R.styleable.MessagesList_outcomingImageTimeTextStyle, 0);
        messagesListStyle.dateHeaderPadding = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_dateHeaderPadding, messagesListStyle.getDimension(C2363R.dimen.message_date_header_padding));
        messagesListStyle.dateHeaderFormat = obtainStyledAttributes.getString(C2363R.styleable.MessagesList_dateHeaderFormat);
        messagesListStyle.dateHeaderTextColor = obtainStyledAttributes.getColor(C2363R.styleable.MessagesList_dateHeaderTextColor, messagesListStyle.getColor(C2363R.C2364color.warm_grey_two));
        messagesListStyle.dateHeaderTextSize = obtainStyledAttributes.getDimensionPixelSize(C2363R.styleable.MessagesList_dateHeaderTextSize, messagesListStyle.getDimension(C2363R.dimen.message_date_header_text_size));
        messagesListStyle.dateHeaderTextStyle = obtainStyledAttributes.getInt(C2363R.styleable.MessagesList_dateHeaderTextStyle, 0);
        obtainStyledAttributes.recycle();
        return messagesListStyle;
    }

    private MessagesListStyle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private Drawable getMessageSelector(int i, int i2, int i3, int i4) {
        Drawable mutate = DrawableCompat.wrap(getVectorDrawable(i4)).mutate();
        DrawableCompat.setTintList(mutate, new ColorStateList(new int[][]{new int[]{16842913}, new int[]{16842919}, new int[]{-16842919, -16842913}}, new int[]{i2, i3, i}));
        return mutate;
    }

    /* access modifiers changed from: 0000 */
    public int getTextAutoLinkMask() {
        return this.textAutoLinkMask;
    }

    /* access modifiers changed from: 0000 */
    public int getIncomingTextLinkColor() {
        return this.incomingTextLinkColor;
    }

    /* access modifiers changed from: 0000 */
    public int getOutcomingTextLinkColor() {
        return this.outcomingTextLinkColor;
    }

    /* access modifiers changed from: 0000 */
    public int getIncomingAvatarWidth() {
        return this.incomingAvatarWidth;
    }

    /* access modifiers changed from: 0000 */
    public int getIncomingAvatarHeight() {
        return this.incomingAvatarHeight;
    }

    /* access modifiers changed from: 0000 */
    public int getIncomingDefaultBubblePaddingLeft() {
        return this.incomingDefaultBubblePaddingLeft;
    }

    /* access modifiers changed from: 0000 */
    public int getIncomingDefaultBubblePaddingRight() {
        return this.incomingDefaultBubblePaddingRight;
    }

    /* access modifiers changed from: 0000 */
    public int getIncomingDefaultBubblePaddingTop() {
        return this.incomingDefaultBubblePaddingTop;
    }

    /* access modifiers changed from: 0000 */
    public int getIncomingDefaultBubblePaddingBottom() {
        return this.incomingDefaultBubblePaddingBottom;
    }

    /* access modifiers changed from: 0000 */
    public int getIncomingTextColor() {
        return this.incomingTextColor;
    }

    /* access modifiers changed from: 0000 */
    public int getIncomingTextSize() {
        return this.incomingTextSize;
    }

    /* access modifiers changed from: 0000 */
    public int getIncomingTextStyle() {
        return this.incomingTextStyle;
    }

    /* access modifiers changed from: 0000 */
    public Drawable getOutcomingBubbleDrawable() {
        int i = this.outcomingBubbleDrawable;
        if (i == -1) {
            return getMessageSelector(this.outcomingDefaultBubbleColor, this.outcomingDefaultBubbleSelectedColor, this.outcomingDefaultBubblePressedColor, C2363R.C2365drawable.shape_outcoming_message);
        }
        return getDrawable(i);
    }

    /* access modifiers changed from: 0000 */
    public Drawable getOutcomingImageOverlayDrawable() {
        int i = this.outcomingImageOverlayDrawable;
        if (i == -1) {
            return getMessageSelector(0, this.outcomingDefaultImageOverlaySelectedColor, this.outcomingDefaultImageOverlayPressedColor, C2363R.C2365drawable.shape_outcoming_message);
        }
        return getDrawable(i);
    }

    /* access modifiers changed from: 0000 */
    public int getOutcomingDefaultBubblePaddingLeft() {
        return this.outcomingDefaultBubblePaddingLeft;
    }

    /* access modifiers changed from: 0000 */
    public int getOutcomingDefaultBubblePaddingRight() {
        return this.outcomingDefaultBubblePaddingRight;
    }

    /* access modifiers changed from: 0000 */
    public int getOutcomingDefaultBubblePaddingTop() {
        return this.outcomingDefaultBubblePaddingTop;
    }

    /* access modifiers changed from: 0000 */
    public int getOutcomingDefaultBubblePaddingBottom() {
        return this.outcomingDefaultBubblePaddingBottom;
    }

    /* access modifiers changed from: 0000 */
    public int getOutcomingTextColor() {
        return this.outcomingTextColor;
    }

    /* access modifiers changed from: 0000 */
    public int getOutcomingTextSize() {
        return this.outcomingTextSize;
    }

    /* access modifiers changed from: 0000 */
    public int getOutcomingTextStyle() {
        return this.outcomingTextStyle;
    }

    /* access modifiers changed from: 0000 */
    public int getOutcomingTimeTextColor() {
        return this.outcomingTimeTextColor;
    }

    /* access modifiers changed from: 0000 */
    public int getOutcomingTimeTextSize() {
        return this.outcomingTimeTextSize;
    }

    /* access modifiers changed from: 0000 */
    public int getOutcomingTimeTextStyle() {
        return this.outcomingTimeTextStyle;
    }

    /* access modifiers changed from: 0000 */
    public int getOutcomingImageTimeTextColor() {
        return this.outcomingImageTimeTextColor;
    }

    /* access modifiers changed from: 0000 */
    public int getOutcomingImageTimeTextSize() {
        return this.outcomingImageTimeTextSize;
    }

    /* access modifiers changed from: 0000 */
    public int getOutcomingImageTimeTextStyle() {
        return this.outcomingImageTimeTextStyle;
    }

    /* access modifiers changed from: 0000 */
    public int getDateHeaderTextColor() {
        return this.dateHeaderTextColor;
    }

    /* access modifiers changed from: 0000 */
    public int getDateHeaderTextSize() {
        return this.dateHeaderTextSize;
    }

    /* access modifiers changed from: 0000 */
    public int getDateHeaderTextStyle() {
        return this.dateHeaderTextStyle;
    }

    /* access modifiers changed from: 0000 */
    public int getDateHeaderPadding() {
        return this.dateHeaderPadding;
    }

    /* access modifiers changed from: 0000 */
    public String getDateHeaderFormat() {
        return this.dateHeaderFormat;
    }

    /* access modifiers changed from: 0000 */
    public int getIncomingTimeTextSize() {
        return this.incomingTimeTextSize;
    }

    /* access modifiers changed from: 0000 */
    public int getIncomingTimeTextStyle() {
        return this.incomingTimeTextStyle;
    }

    /* access modifiers changed from: 0000 */
    public int getIncomingTimeTextColor() {
        return this.incomingTimeTextColor;
    }

    /* access modifiers changed from: 0000 */
    public int getIncomingImageTimeTextColor() {
        return this.incomingImageTimeTextColor;
    }

    /* access modifiers changed from: 0000 */
    public int getIncomingImageTimeTextSize() {
        return this.incomingImageTimeTextSize;
    }

    /* access modifiers changed from: 0000 */
    public int getIncomingImageTimeTextStyle() {
        return this.incomingImageTimeTextStyle;
    }

    /* access modifiers changed from: 0000 */
    public Drawable getIncomingBubbleDrawable() {
        int i = this.incomingBubbleDrawable;
        if (i == -1) {
            return getMessageSelector(this.incomingDefaultBubbleColor, this.incomingDefaultBubbleSelectedColor, this.incomingDefaultBubblePressedColor, C2363R.C2365drawable.shape_incoming_message);
        }
        return getDrawable(i);
    }

    /* access modifiers changed from: 0000 */
    public Drawable getIncomingImageOverlayDrawable() {
        int i = this.incomingImageOverlayDrawable;
        if (i == -1) {
            return getMessageSelector(0, this.incomingDefaultImageOverlaySelectedColor, this.incomingDefaultImageOverlayPressedColor, C2363R.C2365drawable.shape_incoming_message);
        }
        return getDrawable(i);
    }
}
