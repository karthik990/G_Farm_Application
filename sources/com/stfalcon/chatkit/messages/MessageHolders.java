package com.stfalcon.chatkit.messages;

import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.stfalcon.chatkit.C2363R;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.ViewHolder;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.MessageContentType;
import com.stfalcon.chatkit.commons.models.MessageContentType.Image;
import com.stfalcon.chatkit.messages.MessagesListAdapter.OnMessageViewClickListener;
import com.stfalcon.chatkit.utils.DateFormatter;
import com.stfalcon.chatkit.utils.DateFormatter.Formatter;
import com.stfalcon.chatkit.utils.DateFormatter.Template;
import com.stfalcon.chatkit.utils.RoundedImageView;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.objectweb.asm.Opcodes;

public class MessageHolders {
    private static final short VIEW_TYPE_DATE_HEADER = 130;
    private static final short VIEW_TYPE_IMAGE_MESSAGE = 132;
    private static final short VIEW_TYPE_TEXT_MESSAGE = 131;
    private ContentChecker contentChecker;
    private List<ContentTypeConfig> customContentTypes = new ArrayList();
    private Class<? extends ViewHolder<Date>> dateHeaderHolder = DefaultDateHeaderViewHolder.class;
    private int dateHeaderLayout = C2363R.layout.item_date_header;
    private HolderConfig<Image> incomingImageConfig = new HolderConfig<>(DefaultIncomingImageMessageViewHolder.class, C2363R.layout.item_incoming_image_message);
    private HolderConfig<IMessage> incomingTextConfig = new HolderConfig<>(DefaultIncomingTextMessageViewHolder.class, C2363R.layout.item_incoming_text_message);
    private HolderConfig<Image> outcomingImageConfig = new HolderConfig<>(DefaultOutcomingImageMessageViewHolder.class, C2363R.layout.item_outcoming_image_message);
    private HolderConfig<IMessage> outcomingTextConfig = new HolderConfig<>(DefaultOutcomingTextMessageViewHolder.class, C2363R.layout.item_outcoming_text_message);

    public static abstract class BaseIncomingMessageViewHolder<MESSAGE extends IMessage> extends BaseMessageViewHolder<MESSAGE> implements DefaultMessageViewHolder {
        protected TextView time;
        protected ImageView userAvatar;

        public BaseIncomingMessageViewHolder(View view) {
            super(view);
            this.time = (TextView) view.findViewById(C2363R.C2366id.messageTime);
            this.userAvatar = (ImageView) view.findViewById(C2363R.C2366id.messageUserAvatar);
        }

        public void onBind(MESSAGE message) {
            TextView textView = this.time;
            if (textView != null) {
                textView.setText(DateFormatter.format(message.getCreatedAt(), Template.TIME));
            }
            if (this.userAvatar != null) {
                int i = 0;
                boolean z = (this.imageLoader == null || message.getUser().getAvatar() == null || message.getUser().getAvatar().isEmpty()) ? false : true;
                ImageView imageView = this.userAvatar;
                if (!z) {
                    i = 8;
                }
                imageView.setVisibility(i);
                if (z) {
                    this.imageLoader.loadImage(this.userAvatar, message.getUser().getAvatar());
                }
            }
        }

        public void applyStyle(MessagesListStyle messagesListStyle) {
            TextView textView = this.time;
            if (textView != null) {
                textView.setTextColor(messagesListStyle.getIncomingTimeTextColor());
                this.time.setTextSize(0, (float) messagesListStyle.getIncomingTimeTextSize());
                TextView textView2 = this.time;
                textView2.setTypeface(textView2.getTypeface(), messagesListStyle.getIncomingTimeTextStyle());
            }
            ImageView imageView = this.userAvatar;
            if (imageView != null) {
                imageView.getLayoutParams().width = messagesListStyle.getIncomingAvatarWidth();
                this.userAvatar.getLayoutParams().height = messagesListStyle.getIncomingAvatarHeight();
            }
        }
    }

    public static abstract class BaseMessageViewHolder<MESSAGE extends IMessage> extends ViewHolder<MESSAGE> {
        ImageLoader imageLoader;
        boolean isSelected;

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

    public static abstract class BaseOutcomingMessageViewHolder<MESSAGE extends IMessage> extends BaseMessageViewHolder<MESSAGE> implements DefaultMessageViewHolder {
        protected TextView time;

        public BaseOutcomingMessageViewHolder(View view) {
            super(view);
            this.time = (TextView) view.findViewById(C2363R.C2366id.messageTime);
        }

        public void onBind(MESSAGE message) {
            TextView textView = this.time;
            if (textView != null) {
                textView.setText(DateFormatter.format(message.getCreatedAt(), Template.TIME));
            }
        }

        public void applyStyle(MessagesListStyle messagesListStyle) {
            TextView textView = this.time;
            if (textView != null) {
                textView.setTextColor(messagesListStyle.getOutcomingTimeTextColor());
                this.time.setTextSize(0, (float) messagesListStyle.getOutcomingTimeTextSize());
                TextView textView2 = this.time;
                textView2.setTypeface(textView2.getTypeface(), messagesListStyle.getOutcomingTimeTextStyle());
            }
        }
    }

    public interface ContentChecker<MESSAGE extends IMessage> {
        boolean hasContentFor(MESSAGE message, byte b);
    }

    private static class ContentTypeConfig<TYPE extends MessageContentType> {
        /* access modifiers changed from: private */
        public HolderConfig<TYPE> incomingConfig;
        /* access modifiers changed from: private */
        public HolderConfig<TYPE> outcomingConfig;
        /* access modifiers changed from: private */
        public byte type;

        private ContentTypeConfig(byte b, HolderConfig<TYPE> holderConfig, HolderConfig<TYPE> holderConfig2) {
            this.type = b;
            this.incomingConfig = holderConfig;
            this.outcomingConfig = holderConfig2;
        }
    }

    public static class DefaultDateHeaderViewHolder extends ViewHolder<Date> implements DefaultMessageViewHolder {
        protected String dateFormat;
        protected Formatter dateHeadersFormatter;
        protected TextView text;

        public DefaultDateHeaderViewHolder(View view) {
            super(view);
            this.text = (TextView) view.findViewById(C2363R.C2366id.messageText);
        }

        public void onBind(Date date) {
            if (this.text != null) {
                String str = null;
                Formatter formatter = this.dateHeadersFormatter;
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

    private static class DefaultIncomingImageMessageViewHolder extends IncomingImageMessageViewHolder<Image> {
        public DefaultIncomingImageMessageViewHolder(View view) {
            super(view);
        }
    }

    private static class DefaultIncomingTextMessageViewHolder extends IncomingTextMessageViewHolder<IMessage> {
        public DefaultIncomingTextMessageViewHolder(View view) {
            super(view);
        }
    }

    interface DefaultMessageViewHolder {
        void applyStyle(MessagesListStyle messagesListStyle);
    }

    private static class DefaultOutcomingImageMessageViewHolder extends OutcomingImageMessageViewHolder<Image> {
        public DefaultOutcomingImageMessageViewHolder(View view) {
            super(view);
        }
    }

    private static class DefaultOutcomingTextMessageViewHolder extends OutcomingTextMessageViewHolder<IMessage> {
        public DefaultOutcomingTextMessageViewHolder(View view) {
            super(view);
        }
    }

    private class HolderConfig<T extends IMessage> {
        Class<? extends BaseMessageViewHolder<? extends T>> holder;
        int layout;

        HolderConfig(Class<? extends BaseMessageViewHolder<? extends T>> cls, int i) {
            this.holder = cls;
            this.layout = i;
        }
    }

    public static class IncomingImageMessageViewHolder<MESSAGE extends Image> extends BaseIncomingMessageViewHolder<MESSAGE> {
        protected ImageView image;
        protected View imageOverlay;

        public IncomingImageMessageViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(C2363R.C2366id.image);
            this.imageOverlay = view.findViewById(C2363R.C2366id.imageOverlay);
            ImageView imageView = this.image;
            if (imageView != null && (imageView instanceof RoundedImageView)) {
                ((RoundedImageView) imageView).setCorners(C2363R.dimen.message_bubble_corners_radius, C2363R.dimen.message_bubble_corners_radius, C2363R.dimen.message_bubble_corners_radius, 0);
            }
        }

        public void onBind(MESSAGE message) {
            super.onBind(message);
            if (!(this.image == null || this.imageLoader == null)) {
                this.imageLoader.loadImage(this.image, message.getImageUrl());
            }
            View view = this.imageOverlay;
            if (view != null) {
                view.setSelected(isSelected());
            }
        }

        public final void applyStyle(MessagesListStyle messagesListStyle) {
            super.applyStyle(messagesListStyle);
            if (this.time != null) {
                this.time.setTextColor(messagesListStyle.getIncomingImageTimeTextColor());
                this.time.setTextSize(0, (float) messagesListStyle.getIncomingImageTimeTextSize());
                this.time.setTypeface(this.time.getTypeface(), messagesListStyle.getIncomingImageTimeTextStyle());
            }
            View view = this.imageOverlay;
            if (view != null) {
                ViewCompat.setBackground(view, messagesListStyle.getIncomingImageOverlayDrawable());
            }
        }
    }

    public static class IncomingTextMessageViewHolder<MESSAGE extends IMessage> extends BaseIncomingMessageViewHolder<MESSAGE> {
        protected ViewGroup bubble;
        protected TextView text;

        public IncomingTextMessageViewHolder(View view) {
            super(view);
            this.bubble = (ViewGroup) view.findViewById(C2363R.C2366id.bubble);
            this.text = (TextView) view.findViewById(C2363R.C2366id.messageText);
        }

        public void onBind(MESSAGE message) {
            super.onBind(message);
            ViewGroup viewGroup = this.bubble;
            if (viewGroup != null) {
                viewGroup.setSelected(isSelected());
            }
            TextView textView = this.text;
            if (textView != null) {
                textView.setText(message.getText());
            }
        }

        public void applyStyle(MessagesListStyle messagesListStyle) {
            super.applyStyle(messagesListStyle);
            ViewGroup viewGroup = this.bubble;
            if (viewGroup != null) {
                viewGroup.setPadding(messagesListStyle.getIncomingDefaultBubblePaddingLeft(), messagesListStyle.getIncomingDefaultBubblePaddingTop(), messagesListStyle.getIncomingDefaultBubblePaddingRight(), messagesListStyle.getIncomingDefaultBubblePaddingBottom());
                ViewCompat.setBackground(this.bubble, messagesListStyle.getIncomingBubbleDrawable());
            }
            TextView textView = this.text;
            if (textView != null) {
                textView.setTextColor(messagesListStyle.getIncomingTextColor());
                this.text.setTextSize(0, (float) messagesListStyle.getIncomingTextSize());
                TextView textView2 = this.text;
                textView2.setTypeface(textView2.getTypeface(), messagesListStyle.getIncomingTextStyle());
                this.text.setAutoLinkMask(messagesListStyle.getTextAutoLinkMask());
                this.text.setLinkTextColor(messagesListStyle.getIncomingTextLinkColor());
                configureLinksBehavior(this.text);
            }
        }
    }

    public static class OutcomingImageMessageViewHolder<MESSAGE extends Image> extends BaseOutcomingMessageViewHolder<MESSAGE> {
        protected ImageView image;
        protected View imageOverlay;

        public OutcomingImageMessageViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(C2363R.C2366id.image);
            this.imageOverlay = view.findViewById(C2363R.C2366id.imageOverlay);
            ImageView imageView = this.image;
            if (imageView != null && (imageView instanceof RoundedImageView)) {
                ((RoundedImageView) imageView).setCorners(C2363R.dimen.message_bubble_corners_radius, C2363R.dimen.message_bubble_corners_radius, 0, C2363R.dimen.message_bubble_corners_radius);
            }
        }

        public void onBind(MESSAGE message) {
            super.onBind(message);
            if (!(this.image == null || this.imageLoader == null)) {
                this.imageLoader.loadImage(this.image, message.getImageUrl());
            }
            View view = this.imageOverlay;
            if (view != null) {
                view.setSelected(isSelected());
            }
        }

        public final void applyStyle(MessagesListStyle messagesListStyle) {
            super.applyStyle(messagesListStyle);
            if (this.time != null) {
                this.time.setTextColor(messagesListStyle.getOutcomingImageTimeTextColor());
                this.time.setTextSize(0, (float) messagesListStyle.getOutcomingImageTimeTextSize());
                this.time.setTypeface(this.time.getTypeface(), messagesListStyle.getOutcomingImageTimeTextStyle());
            }
            View view = this.imageOverlay;
            if (view != null) {
                ViewCompat.setBackground(view, messagesListStyle.getOutcomingImageOverlayDrawable());
            }
        }
    }

    public static class OutcomingTextMessageViewHolder<MESSAGE extends IMessage> extends BaseOutcomingMessageViewHolder<MESSAGE> {
        protected ViewGroup bubble;
        protected TextView text;

        public OutcomingTextMessageViewHolder(View view) {
            super(view);
            this.bubble = (ViewGroup) view.findViewById(C2363R.C2366id.bubble);
            this.text = (TextView) view.findViewById(C2363R.C2366id.messageText);
        }

        public void onBind(MESSAGE message) {
            super.onBind(message);
            ViewGroup viewGroup = this.bubble;
            if (viewGroup != null) {
                viewGroup.setSelected(isSelected());
            }
            TextView textView = this.text;
            if (textView != null) {
                textView.setText(message.getText());
            }
        }

        public final void applyStyle(MessagesListStyle messagesListStyle) {
            super.applyStyle(messagesListStyle);
            ViewGroup viewGroup = this.bubble;
            if (viewGroup != null) {
                viewGroup.setPadding(messagesListStyle.getOutcomingDefaultBubblePaddingLeft(), messagesListStyle.getOutcomingDefaultBubblePaddingTop(), messagesListStyle.getOutcomingDefaultBubblePaddingRight(), messagesListStyle.getOutcomingDefaultBubblePaddingBottom());
                ViewCompat.setBackground(this.bubble, messagesListStyle.getOutcomingBubbleDrawable());
            }
            TextView textView = this.text;
            if (textView != null) {
                textView.setTextColor(messagesListStyle.getOutcomingTextColor());
                this.text.setTextSize(0, (float) messagesListStyle.getOutcomingTextSize());
                TextView textView2 = this.text;
                textView2.setTypeface(textView2.getTypeface(), messagesListStyle.getOutcomingTextStyle());
                this.text.setAutoLinkMask(messagesListStyle.getTextAutoLinkMask());
                this.text.setLinkTextColor(messagesListStyle.getOutcomingTextLinkColor());
                configureLinksBehavior(this.text);
            }
        }
    }

    public MessageHolders setIncomingTextConfig(Class<? extends BaseMessageViewHolder<? extends IMessage>> cls, int i) {
        HolderConfig<IMessage> holderConfig = this.incomingTextConfig;
        holderConfig.holder = cls;
        holderConfig.layout = i;
        return this;
    }

    public MessageHolders setIncomingTextHolder(Class<? extends BaseMessageViewHolder<? extends IMessage>> cls) {
        this.incomingTextConfig.holder = cls;
        return this;
    }

    public MessageHolders setIncomingTextLayout(int i) {
        this.incomingTextConfig.layout = i;
        return this;
    }

    public MessageHolders setOutcomingTextConfig(Class<? extends BaseMessageViewHolder<? extends IMessage>> cls, int i) {
        HolderConfig<IMessage> holderConfig = this.outcomingTextConfig;
        holderConfig.holder = cls;
        holderConfig.layout = i;
        return this;
    }

    public MessageHolders setOutcomingTextHolder(Class<? extends BaseMessageViewHolder<? extends IMessage>> cls) {
        this.outcomingTextConfig.holder = cls;
        return this;
    }

    public MessageHolders setOutcomingTextLayout(int i) {
        this.outcomingTextConfig.layout = i;
        return this;
    }

    public MessageHolders setIncomingImageConfig(Class<? extends BaseMessageViewHolder<? extends Image>> cls, int i) {
        HolderConfig<Image> holderConfig = this.incomingImageConfig;
        holderConfig.holder = cls;
        holderConfig.layout = i;
        return this;
    }

    public MessageHolders setIncomingImageHolder(Class<? extends BaseMessageViewHolder<? extends Image>> cls) {
        this.incomingImageConfig.holder = cls;
        return this;
    }

    public MessageHolders setIncomingImageLayout(int i) {
        this.incomingImageConfig.layout = i;
        return this;
    }

    public MessageHolders setOutcomingImageConfig(Class<? extends BaseMessageViewHolder<? extends Image>> cls, int i) {
        HolderConfig<Image> holderConfig = this.outcomingImageConfig;
        holderConfig.holder = cls;
        holderConfig.layout = i;
        return this;
    }

    public MessageHolders setOutcomingImageHolder(Class<? extends BaseMessageViewHolder<? extends Image>> cls) {
        this.outcomingImageConfig.holder = cls;
        return this;
    }

    public MessageHolders setOutcomingImageLayout(int i) {
        this.outcomingImageConfig.layout = i;
        return this;
    }

    public MessageHolders setDateHeaderConfig(Class<? extends ViewHolder<Date>> cls, int i) {
        this.dateHeaderHolder = cls;
        this.dateHeaderLayout = i;
        return this;
    }

    public MessageHolders setDateHeaderHolder(Class<? extends ViewHolder<Date>> cls) {
        this.dateHeaderHolder = cls;
        return this;
    }

    public MessageHolders setDateHeaderLayout(int i) {
        this.dateHeaderLayout = i;
        return this;
    }

    public <TYPE extends MessageContentType> MessageHolders registerContentType(byte b, Class<? extends BaseMessageViewHolder<TYPE>> cls, int i, int i2, ContentChecker contentChecker2) {
        return registerContentType(b, cls, i, cls, i2, contentChecker2);
    }

    public <TYPE extends MessageContentType> MessageHolders registerContentType(byte b, Class<? extends BaseMessageViewHolder<TYPE>> cls, int i, Class<? extends BaseMessageViewHolder<TYPE>> cls2, int i2, ContentChecker contentChecker2) {
        if (b != 0) {
            this.customContentTypes.add(new ContentTypeConfig(b, new HolderConfig(cls, i), new HolderConfig(cls2, i2)));
            this.contentChecker = contentChecker2;
            return this;
        }
        throw new IllegalArgumentException("content type must be greater or less than '0'!");
    }

    /* access modifiers changed from: 0000 */
    public ViewHolder getHolder(ViewGroup viewGroup, int i, MessagesListStyle messagesListStyle) {
        if (i == -132) {
            return getHolder(viewGroup, (HolderConfig) this.outcomingImageConfig, messagesListStyle);
        }
        if (i == -131) {
            return getHolder(viewGroup, (HolderConfig) this.outcomingTextConfig, messagesListStyle);
        }
        switch (i) {
            case 130:
                return getHolder(viewGroup, this.dateHeaderLayout, this.dateHeaderHolder, messagesListStyle);
            case Opcodes.LXOR /*131*/:
                return getHolder(viewGroup, (HolderConfig) this.incomingTextConfig, messagesListStyle);
            case Opcodes.IINC /*132*/:
                return getHolder(viewGroup, (HolderConfig) this.incomingImageConfig, messagesListStyle);
            default:
                for (ContentTypeConfig contentTypeConfig : this.customContentTypes) {
                    if (Math.abs(contentTypeConfig.type) == Math.abs(i)) {
                        if (i > 0) {
                            return getHolder(viewGroup, contentTypeConfig.incomingConfig, messagesListStyle);
                        }
                        return getHolder(viewGroup, contentTypeConfig.outcomingConfig, messagesListStyle);
                    }
                }
                throw new IllegalStateException("Wrong message view type. Please, report this issue on GitHub with full stacktrace in description.");
        }
    }

    /* access modifiers changed from: 0000 */
    public void bind(ViewHolder viewHolder, Object obj, boolean z, ImageLoader imageLoader, OnClickListener onClickListener, OnLongClickListener onLongClickListener, Formatter formatter, SparseArray<OnMessageViewClickListener> sparseArray) {
        if (obj instanceof IMessage) {
            BaseMessageViewHolder baseMessageViewHolder = (BaseMessageViewHolder) viewHolder;
            baseMessageViewHolder.isSelected = z;
            baseMessageViewHolder.imageLoader = imageLoader;
            viewHolder.itemView.setOnLongClickListener(onLongClickListener);
            viewHolder.itemView.setOnClickListener(onClickListener);
            for (int i = 0; i < sparseArray.size(); i++) {
                final int keyAt = sparseArray.keyAt(i);
                View findViewById = viewHolder.itemView.findViewById(keyAt);
                if (findViewById != null) {
                    final SparseArray<OnMessageViewClickListener> sparseArray2 = sparseArray;
                    final View view = findViewById;
                    final Object obj2 = obj;
                    C30001 r0 = new OnClickListener() {
                        public void onClick(View view) {
                            ((OnMessageViewClickListener) sparseArray2.get(keyAt)).onMessageViewClick(view, (IMessage) obj2);
                        }
                    };
                    findViewById.setOnClickListener(r0);
                }
            }
        } else if (obj instanceof Date) {
            ((DefaultDateHeaderViewHolder) viewHolder).dateHeadersFormatter = formatter;
        }
        viewHolder.onBind(obj);
    }

    /* access modifiers changed from: 0000 */
    public int getViewType(Object obj, String str) {
        boolean z;
        short s;
        if (obj instanceof IMessage) {
            IMessage iMessage = (IMessage) obj;
            z = iMessage.getUser().getId().contentEquals(str);
            s = getContentViewType(iMessage);
        } else {
            s = VIEW_TYPE_DATE_HEADER;
            z = false;
        }
        return z ? s * -1 : s;
    }

    private ViewHolder getHolder(ViewGroup viewGroup, HolderConfig holderConfig, MessagesListStyle messagesListStyle) {
        return getHolder(viewGroup, holderConfig.layout, holderConfig.holder, messagesListStyle);
    }

    private <HOLDER extends ViewHolder> ViewHolder getHolder(ViewGroup viewGroup, int i, Class<HOLDER> cls, MessagesListStyle messagesListStyle) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false);
        try {
            Constructor declaredConstructor = cls.getDeclaredConstructor(new Class[]{View.class});
            declaredConstructor.setAccessible(true);
            ViewHolder viewHolder = (ViewHolder) declaredConstructor.newInstance(new Object[]{inflate});
            if ((viewHolder instanceof DefaultMessageViewHolder) && messagesListStyle != null) {
                ((DefaultMessageViewHolder) viewHolder).applyStyle(messagesListStyle);
            }
            return viewHolder;
        } catch (Exception e) {
            throw new RuntimeException("Somehow we couldn't create the ViewHolder for message. Please, report this issue on GitHub with full stacktrace in description.", e);
        }
    }

    private short getContentViewType(IMessage iMessage) {
        if ((iMessage instanceof Image) && ((Image) iMessage).getImageUrl() != null) {
            return VIEW_TYPE_IMAGE_MESSAGE;
        }
        if (iMessage instanceof MessageContentType) {
            int i = 0;
            while (i < this.customContentTypes.size()) {
                ContentTypeConfig contentTypeConfig = (ContentTypeConfig) this.customContentTypes.get(i);
                ContentChecker contentChecker2 = this.contentChecker;
                if (contentChecker2 == null) {
                    throw new IllegalArgumentException("ContentChecker cannot be null when using custom content types!");
                } else if (contentChecker2.hasContentFor(iMessage, contentTypeConfig.type)) {
                    return (short) contentTypeConfig.type;
                } else {
                    i++;
                }
            }
        }
        return VIEW_TYPE_TEXT_MESSAGE;
    }
}
