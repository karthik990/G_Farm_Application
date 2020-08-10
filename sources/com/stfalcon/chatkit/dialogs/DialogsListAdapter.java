package com.stfalcon.chatkit.dialogs;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.stfalcon.chatkit.C2363R;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.ViewHolder;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.utils.DateFormatter;
import com.stfalcon.chatkit.utils.DateFormatter.Formatter;
import com.stfalcon.chatkit.utils.DateFormatter.Template;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DialogsListAdapter<DIALOG extends IDialog> extends Adapter<BaseDialogViewHolder> {
    private Formatter datesFormatter;
    private DialogListStyle dialogStyle;
    private Class<? extends BaseDialogViewHolder> holderClass;
    private ImageLoader imageLoader;
    private int itemLayoutId;
    private List<DIALOG> items;
    private OnDialogClickListener<DIALOG> onDialogClickListener;
    private OnDialogViewClickListener<DIALOG> onDialogViewClickListener;
    private OnDialogViewLongClickListener<DIALOG> onDialogViewLongClickListener;
    private OnDialogLongClickListener<DIALOG> onLongItemClickListener;

    public static abstract class BaseDialogViewHolder<DIALOG extends IDialog> extends ViewHolder<DIALOG> {
        protected Formatter datesFormatter;
        protected ImageLoader imageLoader;
        protected OnDialogClickListener<DIALOG> onDialogClickListener;
        protected OnDialogViewClickListener<DIALOG> onDialogViewClickListener;
        protected OnDialogViewLongClickListener<DIALOG> onDialogViewLongClickListener;
        protected OnDialogLongClickListener<DIALOG> onLongItemClickListener;

        public BaseDialogViewHolder(View view) {
            super(view);
        }

        /* access modifiers changed from: 0000 */
        public void setImageLoader(ImageLoader imageLoader2) {
            this.imageLoader = imageLoader2;
        }

        /* access modifiers changed from: 0000 */
        public void setOnDialogClickListener(OnDialogClickListener<DIALOG> onDialogClickListener2) {
            this.onDialogClickListener = onDialogClickListener2;
        }

        /* access modifiers changed from: 0000 */
        public void setOnDialogViewClickListener(OnDialogViewClickListener<DIALOG> onDialogViewClickListener2) {
            this.onDialogViewClickListener = onDialogViewClickListener2;
        }

        /* access modifiers changed from: 0000 */
        public void setOnLongItemClickListener(OnDialogLongClickListener<DIALOG> onDialogLongClickListener) {
            this.onLongItemClickListener = onDialogLongClickListener;
        }

        /* access modifiers changed from: 0000 */
        public void setOnDialogViewLongClickListener(OnDialogViewLongClickListener<DIALOG> onDialogViewLongClickListener2) {
            this.onDialogViewLongClickListener = onDialogViewLongClickListener2;
        }

        public void setDatesFormatter(Formatter formatter) {
            this.datesFormatter = formatter;
        }
    }

    public static class DialogViewHolder<DIALOG extends IDialog> extends BaseDialogViewHolder<DIALOG> {
        protected ViewGroup container;
        protected DialogListStyle dialogStyle;
        protected View divider;
        protected ViewGroup dividerContainer;
        protected ImageView ivAvatar;
        protected ImageView ivLastMessageUser;
        protected ViewGroup root;
        protected TextView tvBubble;
        protected TextView tvDate;
        protected TextView tvLastMessage;
        protected TextView tvName;

        public DialogViewHolder(View view) {
            super(view);
            this.root = (ViewGroup) view.findViewById(C2363R.C2366id.dialogRootLayout);
            this.container = (ViewGroup) view.findViewById(C2363R.C2366id.dialogContainer);
            this.tvName = (TextView) view.findViewById(C2363R.C2366id.dialogName);
            this.tvDate = (TextView) view.findViewById(C2363R.C2366id.dialogDate);
            this.tvLastMessage = (TextView) view.findViewById(C2363R.C2366id.dialogLastMessage);
            this.tvBubble = (TextView) view.findViewById(C2363R.C2366id.dialogUnreadBubble);
            this.ivLastMessageUser = (ImageView) view.findViewById(C2363R.C2366id.dialogLastMessageUserAvatar);
            this.ivAvatar = (ImageView) view.findViewById(C2363R.C2366id.dialogAvatar);
            this.dividerContainer = (ViewGroup) view.findViewById(C2363R.C2366id.dialogDividerContainer);
            this.divider = view.findViewById(C2363R.C2366id.dialogDivider);
        }

        private void applyStyle() {
            DialogListStyle dialogListStyle = this.dialogStyle;
            if (dialogListStyle != null) {
                TextView textView = this.tvName;
                if (textView != null) {
                    textView.setTextSize(0, (float) dialogListStyle.getDialogTitleTextSize());
                }
                TextView textView2 = this.tvLastMessage;
                if (textView2 != null) {
                    textView2.setTextSize(0, (float) this.dialogStyle.getDialogMessageTextSize());
                }
                TextView textView3 = this.tvDate;
                if (textView3 != null) {
                    textView3.setTextSize(0, (float) this.dialogStyle.getDialogDateSize());
                }
                View view = this.divider;
                if (view != null) {
                    view.setBackgroundColor(this.dialogStyle.getDialogDividerColor());
                }
                ViewGroup viewGroup = this.dividerContainer;
                if (viewGroup != null) {
                    viewGroup.setPadding(this.dialogStyle.getDialogDividerLeftPadding(), 0, this.dialogStyle.getDialogDividerRightPadding(), 0);
                }
                ImageView imageView = this.ivAvatar;
                if (imageView != null) {
                    imageView.getLayoutParams().width = this.dialogStyle.getDialogAvatarWidth();
                    this.ivAvatar.getLayoutParams().height = this.dialogStyle.getDialogAvatarHeight();
                }
                ImageView imageView2 = this.ivLastMessageUser;
                if (imageView2 != null) {
                    imageView2.getLayoutParams().width = this.dialogStyle.getDialogMessageAvatarWidth();
                    this.ivLastMessageUser.getLayoutParams().height = this.dialogStyle.getDialogMessageAvatarHeight();
                }
                TextView textView4 = this.tvBubble;
                if (textView4 != null) {
                    ((GradientDrawable) textView4.getBackground()).setColor(this.dialogStyle.getDialogUnreadBubbleBackgroundColor());
                    this.tvBubble.setVisibility(this.dialogStyle.isDialogDividerEnabled() ? 0 : 8);
                    this.tvBubble.setTextSize(0, (float) this.dialogStyle.getDialogUnreadBubbleTextSize());
                    this.tvBubble.setTextColor(this.dialogStyle.getDialogUnreadBubbleTextColor());
                    TextView textView5 = this.tvBubble;
                    textView5.setTypeface(textView5.getTypeface(), this.dialogStyle.getDialogUnreadBubbleTextStyle());
                }
            }
        }

        private void applyDefaultStyle() {
            DialogListStyle dialogListStyle = this.dialogStyle;
            if (dialogListStyle != null) {
                ViewGroup viewGroup = this.root;
                if (viewGroup != null) {
                    viewGroup.setBackgroundColor(dialogListStyle.getDialogItemBackground());
                }
                TextView textView = this.tvName;
                if (textView != null) {
                    textView.setTextColor(this.dialogStyle.getDialogTitleTextColor());
                    this.tvName.setTypeface(Typeface.DEFAULT, this.dialogStyle.getDialogTitleTextStyle());
                }
                TextView textView2 = this.tvDate;
                if (textView2 != null) {
                    textView2.setTextColor(this.dialogStyle.getDialogDateColor());
                    this.tvDate.setTypeface(Typeface.DEFAULT, this.dialogStyle.getDialogDateStyle());
                }
                TextView textView3 = this.tvLastMessage;
                if (textView3 != null) {
                    textView3.setTextColor(this.dialogStyle.getDialogMessageTextColor());
                    this.tvLastMessage.setTypeface(Typeface.DEFAULT, this.dialogStyle.getDialogMessageTextStyle());
                }
            }
        }

        private void applyUnreadStyle() {
            DialogListStyle dialogListStyle = this.dialogStyle;
            if (dialogListStyle != null) {
                ViewGroup viewGroup = this.root;
                if (viewGroup != null) {
                    viewGroup.setBackgroundColor(dialogListStyle.getDialogUnreadItemBackground());
                }
                TextView textView = this.tvName;
                if (textView != null) {
                    textView.setTextColor(this.dialogStyle.getDialogUnreadTitleTextColor());
                    this.tvName.setTypeface(Typeface.DEFAULT, this.dialogStyle.getDialogUnreadTitleTextStyle());
                }
                TextView textView2 = this.tvDate;
                if (textView2 != null) {
                    textView2.setTextColor(this.dialogStyle.getDialogUnreadDateColor());
                    this.tvDate.setTypeface(Typeface.DEFAULT, this.dialogStyle.getDialogUnreadDateStyle());
                }
                TextView textView3 = this.tvLastMessage;
                if (textView3 != null) {
                    textView3.setTextColor(this.dialogStyle.getDialogUnreadMessageTextColor());
                    this.tvLastMessage.setTypeface(Typeface.DEFAULT, this.dialogStyle.getDialogUnreadMessageTextStyle());
                }
            }
        }

        public void onBind(final DIALOG dialog) {
            if (dialog.getUnreadCount() > 0) {
                applyUnreadStyle();
            } else {
                applyDefaultStyle();
            }
            this.tvName.setText(dialog.getDialogName());
            String str = null;
            Date createdAt = dialog.getLastMessage().getCreatedAt();
            if (this.datesFormatter != null) {
                str = this.datesFormatter.format(createdAt);
            }
            TextView textView = this.tvDate;
            if (str == null) {
                str = getDateString(createdAt);
            }
            textView.setText(str);
            if (this.imageLoader != null) {
                this.imageLoader.loadImage(this.ivAvatar, dialog.getDialogPhoto());
            }
            if (this.imageLoader != null) {
                this.imageLoader.loadImage(this.ivLastMessageUser, dialog.getLastMessage().getUser().getAvatar());
            }
            int i = 0;
            this.ivLastMessageUser.setVisibility((!this.dialogStyle.isDialogMessageAvatarEnabled() || dialog.getUsers().size() <= 1) ? 8 : 0);
            this.tvLastMessage.setText(dialog.getLastMessage().getText());
            this.tvBubble.setText(String.valueOf(dialog.getUnreadCount()));
            TextView textView2 = this.tvBubble;
            if (!this.dialogStyle.isDialogUnreadBubbleEnabled() || dialog.getUnreadCount() <= 0) {
                i = 8;
            }
            textView2.setVisibility(i);
            this.container.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (DialogViewHolder.this.onDialogClickListener != null) {
                        DialogViewHolder.this.onDialogClickListener.onDialogClick(dialog);
                    }
                    if (DialogViewHolder.this.onDialogViewClickListener != null) {
                        DialogViewHolder.this.onDialogViewClickListener.onDialogViewClick(view, dialog);
                    }
                }
            });
            this.container.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View view) {
                    if (DialogViewHolder.this.onLongItemClickListener != null) {
                        DialogViewHolder.this.onLongItemClickListener.onDialogLongClick(dialog);
                    }
                    if (DialogViewHolder.this.onDialogViewLongClickListener != null) {
                        DialogViewHolder.this.onDialogViewLongClickListener.onDialogViewLongClick(view, dialog);
                    }
                    return (DialogViewHolder.this.onLongItemClickListener == null && DialogViewHolder.this.onDialogViewLongClickListener == null) ? false : true;
                }
            });
        }

        /* access modifiers changed from: protected */
        public String getDateString(Date date) {
            return DateFormatter.format(date, Template.TIME);
        }

        /* access modifiers changed from: protected */
        public DialogListStyle getDialogStyle() {
            return this.dialogStyle;
        }

        /* access modifiers changed from: protected */
        public void setDialogStyle(DialogListStyle dialogListStyle) {
            this.dialogStyle = dialogListStyle;
            applyStyle();
        }
    }

    public interface OnDialogClickListener<DIALOG extends IDialog> {
        void onDialogClick(DIALOG dialog);
    }

    public interface OnDialogLongClickListener<DIALOG extends IDialog> {
        void onDialogLongClick(DIALOG dialog);
    }

    public interface OnDialogViewClickListener<DIALOG extends IDialog> {
        void onDialogViewClick(View view, DIALOG dialog);
    }

    public interface OnDialogViewLongClickListener<DIALOG extends IDialog> {
        void onDialogViewLongClick(View view, DIALOG dialog);
    }

    public DialogsListAdapter(ImageLoader imageLoader2) {
        this(C2363R.layout.item_dialog, DialogViewHolder.class, imageLoader2);
    }

    public DialogsListAdapter(int i, ImageLoader imageLoader2) {
        this(i, DialogViewHolder.class, imageLoader2);
    }

    public DialogsListAdapter(int i, Class<? extends BaseDialogViewHolder> cls, ImageLoader imageLoader2) {
        this.items = new ArrayList();
        this.itemLayoutId = i;
        this.holderClass = cls;
        this.imageLoader = imageLoader2;
    }

    public void onBindViewHolder(BaseDialogViewHolder baseDialogViewHolder, int i) {
        baseDialogViewHolder.setImageLoader(this.imageLoader);
        baseDialogViewHolder.setOnDialogClickListener(this.onDialogClickListener);
        baseDialogViewHolder.setOnDialogViewClickListener(this.onDialogViewClickListener);
        baseDialogViewHolder.setOnLongItemClickListener(this.onLongItemClickListener);
        baseDialogViewHolder.setOnDialogViewLongClickListener(this.onDialogViewLongClickListener);
        baseDialogViewHolder.setDatesFormatter(this.datesFormatter);
        baseDialogViewHolder.onBind(this.items.get(i));
    }

    public BaseDialogViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(this.itemLayoutId, viewGroup, false);
        try {
            Constructor declaredConstructor = this.holderClass.getDeclaredConstructor(new Class[]{View.class});
            declaredConstructor.setAccessible(true);
            BaseDialogViewHolder baseDialogViewHolder = (BaseDialogViewHolder) declaredConstructor.newInstance(new Object[]{inflate});
            if (baseDialogViewHolder instanceof DialogViewHolder) {
                ((DialogViewHolder) baseDialogViewHolder).setDialogStyle(this.dialogStyle);
            }
            return baseDialogViewHolder;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getItemCount() {
        return this.items.size();
    }

    public void deleteById(String str) {
        for (int i = 0; i < this.items.size(); i++) {
            if (((IDialog) this.items.get(i)).getId().equals(str)) {
                this.items.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public void clear() {
        List<DIALOG> list = this.items;
        if (list != null) {
            list.clear();
        }
        notifyDataSetChanged();
    }

    public void setItems(List<DIALOG> list) {
        this.items = list;
        notifyDataSetChanged();
    }

    public void addItems(List<DIALOG> list) {
        if (list != null) {
            if (this.items == null) {
                this.items = new ArrayList();
            }
            int size = this.items.size();
            this.items.addAll(list);
            notifyItemRangeInserted(size, this.items.size());
        }
    }

    public void addItem(DIALOG dialog) {
        this.items.add(dialog);
        notifyItemInserted(0);
    }

    public void addItem(int i, DIALOG dialog) {
        this.items.add(i, dialog);
        notifyItemInserted(i);
    }

    public void updateItem(int i, DIALOG dialog) {
        if (this.items == null) {
            this.items = new ArrayList();
        }
        this.items.set(i, dialog);
        notifyItemChanged(i);
    }

    public void updateItemById(DIALOG dialog) {
        if (this.items == null) {
            this.items = new ArrayList();
        }
        for (int i = 0; i < this.items.size(); i++) {
            if (((IDialog) this.items.get(i)).getId().equals(dialog.getId())) {
                this.items.set(i, dialog);
                notifyItemChanged(i);
                return;
            }
        }
    }

    public boolean updateDialogWithMessage(String str, IMessage iMessage) {
        for (int i = 0; i < this.items.size(); i++) {
            if (((IDialog) this.items.get(i)).getId().equals(str)) {
                ((IDialog) this.items.get(i)).setLastMessage(iMessage);
                notifyItemChanged(i);
                if (i != 0) {
                    Collections.swap(this.items, i, 0);
                    notifyItemMoved(i, 0);
                }
                return true;
            }
        }
        return false;
    }

    public void sortByLastMessageDate() {
        Collections.sort(this.items, new Comparator<DIALOG>() {
            public int compare(DIALOG dialog, DIALOG dialog2) {
                if (dialog.getLastMessage().getCreatedAt().after(dialog2.getLastMessage().getCreatedAt())) {
                    return -1;
                }
                return dialog.getLastMessage().getCreatedAt().before(dialog2.getLastMessage().getCreatedAt()) ? 1 : 0;
            }
        });
        notifyDataSetChanged();
    }

    public void sort(Comparator<DIALOG> comparator) {
        Collections.sort(this.items, comparator);
        notifyDataSetChanged();
    }

    public void setImageLoader(ImageLoader imageLoader2) {
        this.imageLoader = imageLoader2;
    }

    public ImageLoader getImageLoader() {
        return this.imageLoader;
    }

    public OnDialogClickListener getOnDialogClickListener() {
        return this.onDialogClickListener;
    }

    public void setOnDialogClickListener(OnDialogClickListener<DIALOG> onDialogClickListener2) {
        this.onDialogClickListener = onDialogClickListener2;
    }

    public OnDialogViewClickListener getOnDialogViewClickListener() {
        return this.onDialogViewClickListener;
    }

    public void setOnDialogViewClickListener(OnDialogViewClickListener<DIALOG> onDialogViewClickListener2) {
        this.onDialogViewClickListener = onDialogViewClickListener2;
    }

    public OnDialogLongClickListener getOnLongItemClickListener() {
        return this.onLongItemClickListener;
    }

    public void setOnDialogLongClickListener(OnDialogLongClickListener<DIALOG> onDialogLongClickListener) {
        this.onLongItemClickListener = onDialogLongClickListener;
    }

    public OnDialogViewLongClickListener<DIALOG> getOnDialogViewLongClickListener() {
        return this.onDialogViewLongClickListener;
    }

    public void setOnDialogViewLongClickListener(OnDialogViewLongClickListener<DIALOG> onDialogViewLongClickListener2) {
        this.onDialogViewLongClickListener = onDialogViewLongClickListener2;
    }

    public void setDatesFormatter(Formatter formatter) {
        this.datesFormatter = formatter;
    }

    /* access modifiers changed from: 0000 */
    public void setStyle(DialogListStyle dialogListStyle) {
        this.dialogStyle = dialogListStyle;
    }
}
