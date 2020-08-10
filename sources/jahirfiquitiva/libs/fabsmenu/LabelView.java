package jahirfiquitiva.libs.fabsmenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class LabelView extends CardView {
    private TextView content;
    private int rightBackgroundColor;
    private int textColor;

    public LabelView(Context context, int i) {
        super(context);
        this.rightBackgroundColor = i;
        setCardBackgroundColor(0);
    }

    public LabelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        this.rightBackgroundColor = i;
        setCardBackgroundColor(0);
    }

    public LabelView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
        this.rightBackgroundColor = i2;
        setCardBackgroundColor(0);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        if (onClickListener != null) {
            TypedValue typedValue = new TypedValue();
            getContext().getTheme().resolveAttribute(16843534, typedValue, true);
            setForeground(ContextCompat.getDrawable(getContext(), typedValue.resourceId));
            setClickable(true);
            setFocusable(true);
        } else {
            setForeground(null);
            setClickable(false);
            setFocusable(false);
        }
        super.setOnClickListener(onClickListener);
    }

    public void setForeground(Drawable drawable) {
        super.setForeground(drawable);
    }

    public TextView getContent() {
        return this.content;
    }

    public void setContent(TextView textView) {
        this.content = textView;
    }

    public int getTextColor() {
        return this.textColor;
    }

    public void setTextColor(int i) {
        this.textColor = i;
        this.content.setTextColor(i);
    }

    public void setTextColorFromRes(int i) {
        setTextColor(ContextCompat.getColor(getContext(), i));
    }

    public void setBackgroundColor(int i) {
        this.rightBackgroundColor = i;
        setCardBackgroundColor(0);
    }

    public void setCardBackgroundColor(int i) {
        super.setCardBackgroundColor(this.rightBackgroundColor);
    }
}
