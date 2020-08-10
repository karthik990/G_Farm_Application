package com.nightonke.boommenu.BoomButtons;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils.TruncateAt;

public abstract class BoomButtonWithTextBuilder<T> extends BoomButtonBuilder<T> {
    public T normalText(String str) {
        if (this.normalText == null || !this.normalText.equals(str)) {
            this.normalText = str;
            BoomButton button = button();
            if (button != null) {
                button.normalText = str;
                button.updateText();
            }
        }
        return this;
    }

    public T normalTextRes(int i) {
        if (this.normalTextRes != i) {
            this.normalTextRes = i;
            BoomButton button = button();
            if (button != null) {
                button.normalTextRes = i;
                button.updateText();
            }
        }
        return this;
    }

    public T highlightedText(String str) {
        if (this.highlightedText == null || !this.highlightedText.equals(str)) {
            this.highlightedText = str;
            BoomButton button = button();
            if (button != null) {
                button.highlightedText = str;
                button.updateText();
            }
        }
        return this;
    }

    public T highlightedTextRes(int i) {
        if (this.highlightedTextRes != i) {
            this.highlightedTextRes = i;
            BoomButton button = button();
            if (button != null) {
                button.highlightedTextRes = i;
                button.updateText();
            }
        }
        return this;
    }

    public T unableText(String str) {
        if (this.unableText == null || !this.unableText.equals(str)) {
            this.unableText = str;
            BoomButton button = button();
            if (button != null) {
                button.unableText = str;
                button.updateText();
            }
        }
        return this;
    }

    public T unableTextRes(int i) {
        if (this.unableTextRes != i) {
            this.unableTextRes = i;
            BoomButton button = button();
            if (button != null) {
                button.unableTextRes = i;
                button.updateText();
            }
        }
        return this;
    }

    public T normalTextColor(int i) {
        if (this.normalTextColor != i) {
            this.normalTextColor = i;
            BoomButton button = button();
            if (button != null) {
                button.normalTextColor = i;
                button.updateText();
            }
        }
        return this;
    }

    public T normalTextColorRes(int i) {
        if (this.normalTextColorRes != i) {
            this.normalTextColorRes = i;
            BoomButton button = button();
            if (button != null) {
                button.normalTextColorRes = i;
                button.updateText();
            }
        }
        return this;
    }

    public T highlightedTextColor(int i) {
        if (this.highlightedTextColor != i) {
            this.highlightedTextColor = i;
            BoomButton button = button();
            if (button != null) {
                button.highlightedTextColor = i;
                button.updateText();
            }
        }
        return this;
    }

    public T highlightedTextColorRes(int i) {
        if (this.highlightedTextColorRes != i) {
            this.highlightedTextColorRes = i;
            BoomButton button = button();
            if (button != null) {
                button.highlightedTextColorRes = i;
                button.updateText();
            }
        }
        return this;
    }

    public T unableTextColor(int i) {
        if (this.unableTextColor != i) {
            this.unableTextColor = i;
            BoomButton button = button();
            if (button != null) {
                button.unableTextColor = i;
                button.updateText();
            }
        }
        return this;
    }

    public T unableTextColorRes(int i) {
        if (this.unableTextColorRes != i) {
            this.unableTextColorRes = i;
            BoomButton button = button();
            if (button != null) {
                button.unableTextColorRes = i;
                button.updateText();
            }
        }
        return this;
    }

    public T textRect(Rect rect) {
        if (this.textRect != rect) {
            this.textRect = rect;
            BoomButton button = button();
            if (button != null) {
                button.textRect = rect;
                button.updateTextRect();
            }
        }
        return this;
    }

    public T textPadding(Rect rect) {
        if (this.textPadding != rect) {
            this.textPadding = rect;
            BoomButton button = button();
            if (button != null) {
                button.textPadding = rect;
                button.updateTextPadding();
            }
        }
        return this;
    }

    public T typeface(Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    public T maxLines(int i) {
        this.maxLines = i;
        return this;
    }

    public T textGravity(int i) {
        this.textGravity = i;
        return this;
    }

    public T ellipsize(TruncateAt truncateAt) {
        this.ellipsize = truncateAt;
        return this;
    }

    public T textSize(int i) {
        this.textSize = i;
        return this;
    }
}
