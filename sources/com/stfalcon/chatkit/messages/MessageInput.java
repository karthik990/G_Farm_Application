package com.stfalcon.chatkit.messages;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.legacy.widget.Space;
import com.stfalcon.chatkit.C2363R;
import java.lang.reflect.Field;

public class MessageInput extends RelativeLayout implements OnClickListener, TextWatcher {
    protected ImageButton attachmentButton;
    protected Space attachmentButtonSpace;
    private AttachmentsListener attachmentsListener;
    private CharSequence input;
    private InputListener inputListener;
    protected EditText messageInput;
    protected ImageButton messageSendButton;
    protected Space sendButtonSpace;

    public interface AttachmentsListener {
        void onAddAttachments();
    }

    public interface InputListener {
        boolean onSubmit(CharSequence charSequence);
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public MessageInput(Context context) {
        super(context);
        init(context);
    }

    public MessageInput(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public MessageInput(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    public void setInputListener(InputListener inputListener2) {
        this.inputListener = inputListener2;
    }

    public void setAttachmentsListener(AttachmentsListener attachmentsListener2) {
        this.attachmentsListener = attachmentsListener2;
    }

    public EditText getInputEditText() {
        return this.messageInput;
    }

    public ImageButton getButton() {
        return this.messageSendButton;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C2363R.C2366id.messageSendButton) {
            if (onSubmit()) {
                this.messageInput.setText("");
            }
        } else if (id == C2363R.C2366id.attachmentButton) {
            onAddAttachments();
        }
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        this.input = charSequence;
        this.messageSendButton.setEnabled(this.input.length() > 0);
    }

    private boolean onSubmit() {
        InputListener inputListener2 = this.inputListener;
        return inputListener2 != null && inputListener2.onSubmit(this.input);
    }

    private void onAddAttachments() {
        AttachmentsListener attachmentsListener2 = this.attachmentsListener;
        if (attachmentsListener2 != null) {
            attachmentsListener2.onAddAttachments();
        }
    }

    private void init(Context context, AttributeSet attributeSet) {
        init(context);
        MessageInputStyle parse = MessageInputStyle.parse(context, attributeSet);
        this.messageInput.setMaxLines(parse.getInputMaxLines());
        this.messageInput.setHint(parse.getInputHint());
        this.messageInput.setText(parse.getInputText());
        int i = 0;
        this.messageInput.setTextSize(0, (float) parse.getInputTextSize());
        this.messageInput.setTextColor(parse.getInputTextColor());
        this.messageInput.setHintTextColor(parse.getInputHintColor());
        ViewCompat.setBackground(this.messageInput, parse.getInputBackground());
        setCursor(parse.getInputCursorDrawable());
        this.attachmentButton.setVisibility(parse.showAttachmentButton() ? 0 : 8);
        this.attachmentButton.setImageDrawable(parse.getAttachmentButtonIcon());
        this.attachmentButton.getLayoutParams().width = parse.getAttachmentButtonWidth();
        this.attachmentButton.getLayoutParams().height = parse.getAttachmentButtonHeight();
        ViewCompat.setBackground(this.attachmentButton, parse.getAttachmentButtonBackground());
        Space space = this.attachmentButtonSpace;
        if (!parse.showAttachmentButton()) {
            i = 8;
        }
        space.setVisibility(i);
        this.attachmentButtonSpace.getLayoutParams().width = parse.getAttachmentButtonMargin();
        this.messageSendButton.setImageDrawable(parse.getInputButtonIcon());
        this.messageSendButton.getLayoutParams().width = parse.getInputButtonWidth();
        this.messageSendButton.getLayoutParams().height = parse.getInputButtonHeight();
        ViewCompat.setBackground(this.messageSendButton, parse.getInputButtonBackground());
        this.sendButtonSpace.getLayoutParams().width = parse.getInputButtonMargin();
        if (getPaddingLeft() == 0 && getPaddingRight() == 0 && getPaddingTop() == 0 && getPaddingBottom() == 0) {
            setPadding(parse.getInputDefaultPaddingLeft(), parse.getInputDefaultPaddingTop(), parse.getInputDefaultPaddingRight(), parse.getInputDefaultPaddingBottom());
        }
    }

    private void init(Context context) {
        inflate(context, C2363R.layout.view_message_input, this);
        this.messageInput = (EditText) findViewById(C2363R.C2366id.messageInput);
        this.messageSendButton = (ImageButton) findViewById(C2363R.C2366id.messageSendButton);
        this.attachmentButton = (ImageButton) findViewById(C2363R.C2366id.attachmentButton);
        this.sendButtonSpace = (Space) findViewById(C2363R.C2366id.sendButtonSpace);
        this.attachmentButtonSpace = (Space) findViewById(C2363R.C2366id.attachmentButtonSpace);
        this.messageSendButton.setOnClickListener(this);
        this.attachmentButton.setOnClickListener(this);
        this.messageInput.addTextChangedListener(this);
        this.messageInput.setText("");
    }

    private void setCursor(Drawable drawable) {
        Class<TextView> cls;
        Object obj;
        if (drawable != null) {
            try {
                TextView.class.getDeclaredField("mCursorDrawableRes").setAccessible(true);
                if (VERSION.SDK_INT < 16) {
                    obj = this.messageInput;
                    cls = TextView.class;
                } else {
                    Field declaredField = TextView.class.getDeclaredField("mEditor");
                    declaredField.setAccessible(true);
                    obj = declaredField.get(this.messageInput);
                    cls = obj.getClass();
                }
                Field declaredField2 = cls.getDeclaredField("mCursorDrawable");
                declaredField2.setAccessible(true);
                declaredField2.set(obj, new Drawable[]{drawable, drawable});
            } catch (Exception unused) {
            }
        }
    }
}
