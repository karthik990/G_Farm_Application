package com.nightonke.boommenu.BoomButtons;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import com.nightonke.boommenu.Piece.BoomPiece;
import com.nightonke.boommenu.Util;
import java.lang.ref.WeakReference;

public abstract class BoomButtonBuilder<T> {
    private WeakReference<BoomButton> boomButtonWeakReference;
    int buttonCornerRadius = Util.dp2px(5.0f);
    int buttonHeight = Util.dp2px(60.0f);
    int buttonRadius = Util.dp2px(40.0f);
    int buttonWidth = Util.dp2px(300.0f);
    boolean containsSubText = true;
    TruncateAt ellipsize = TruncateAt.MARQUEE;
    int highlightedColor = Util.getColor();
    int highlightedColorRes = 0;
    Drawable highlightedImageDrawable = null;
    int highlightedImageRes = 0;
    String highlightedText;
    int highlightedTextColor = -1;
    int highlightedTextColorRes = 0;
    int highlightedTextRes = 0;
    Rect imagePadding = new Rect(0, 0, 0, 0);
    Rect imageRect = new Rect(Util.dp2px(10.0f), Util.dp2px(10.0f), Util.dp2px(70.0f), Util.dp2px(70.0f));
    int index = -1;
    boolean isRound = true;
    InnerOnBoomButtonClickListener listener;
    int maxLines = 1;
    int normalColor = Util.getColor();
    int normalColorRes = 0;
    Drawable normalImageDrawable = null;
    int normalImageRes = 0;
    String normalText;
    int normalTextColor = -1;
    int normalTextColorRes = 0;
    int normalTextRes = 0;
    OnBMClickListener onBMClickListener;
    private BoomPiece piece = null;
    Integer pieceColor = null;
    int pieceColorRes = 0;
    boolean rippleEffect = true;
    boolean rotateImage = true;
    boolean rotateText = true;
    int shadowColor = Color.parseColor("#88757575");
    int shadowCornerRadius = Util.dp2px(5.0f);
    boolean shadowEffect = true;
    int shadowOffsetX = Util.dp2px(0.0f);
    int shadowOffsetY = Util.dp2px(3.0f);
    int shadowRadius = Util.dp2px(8.0f);
    TruncateAt subEllipsize = TruncateAt.MARQUEE;
    String subHighlightedText;
    int subHighlightedTextColor = -1;
    int subHighlightedTextColorRes = 0;
    int subHighlightedTextRes = 0;
    int subMaxLines = 1;
    String subNormalText;
    int subNormalTextColor = -1;
    int subNormalTextColorRes = 0;
    int subNormalTextRes = 0;
    int subTextGravity = 8388627;
    Rect subTextPadding = new Rect(0, 0, 0, 0);
    Rect subTextRect = new Rect(Util.dp2px(70.0f), Util.dp2px(35.0f), Util.dp2px(280.0f), Util.dp2px(55.0f));
    int subTextSize = 10;
    Typeface subTypeface = null;
    String subUnableText;
    int subUnableTextColor = -1;
    int subUnableTextColorRes = 0;
    int subUnableTextRes = 0;
    int textGravity = 17;
    int textHeight = Util.dp2px(20.0f);
    Rect textPadding = new Rect(0, 0, 0, 0);
    Rect textRect = new Rect(Util.dp2px(15.0f), Util.dp2px(52.0f), Util.dp2px(65.0f), Util.dp2px(72.0f));
    int textSize = 10;
    int textTopMargin = Util.dp2px(5.0f);
    int textWidth = Util.dp2px(80.0f);
    Typeface typeface = null;
    boolean unable = false;
    int unableColor = Util.getColor();
    int unableColorRes = 0;
    Drawable unableImageDrawable = null;
    int unableImageRes = 0;
    String unableText;
    int unableTextColor = -1;
    int unableTextColorRes = 0;
    int unableTextRes = 0;

    public abstract BoomButton build(Context context);

    public int pieceColor(Context context) {
        if (this.pieceColor != null || this.pieceColorRes != 0) {
            Integer num = this.pieceColor;
            if (num == null) {
                return Util.getColor(context, this.pieceColorRes);
            }
            return Util.getColor(context, this.pieceColorRes, num.intValue());
        } else if (this.unable) {
            return Util.getColor(context, this.unableColorRes, this.unableColor);
        } else {
            return Util.getColor(context, this.normalColorRes, this.normalColor);
        }
    }

    public void setUnable(boolean z) {
        this.unable = z;
    }

    public BoomButton button() {
        WeakReference<BoomButton> weakReference = this.boomButtonWeakReference;
        if (weakReference != null) {
            return (BoomButton) weakReference.get();
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public BoomButton weakReferenceButton(BoomButton boomButton) {
        this.boomButtonWeakReference = new WeakReference<>(boomButton);
        return boomButton;
    }

    public void piece(BoomPiece boomPiece) {
        this.piece = boomPiece;
    }

    public BoomButtonBuilder index(int i) {
        this.index = i;
        return this;
    }

    public BoomButtonBuilder innerListener(InnerOnBoomButtonClickListener innerOnBoomButtonClickListener) {
        this.listener = innerOnBoomButtonClickListener;
        return this;
    }

    public T listener(OnBMClickListener onBMClickListener2) {
        this.onBMClickListener = onBMClickListener2;
        return this;
    }

    public T rotateImage(boolean z) {
        this.rotateImage = z;
        return this;
    }

    public T shadowEffect(boolean z) {
        this.shadowEffect = z;
        return this;
    }

    public T shadowOffsetX(int i) {
        this.shadowOffsetX = i;
        return this;
    }

    public T shadowOffsetY(int i) {
        this.shadowOffsetY = i;
        return this;
    }

    public T shadowRadius(int i) {
        this.shadowRadius = i;
        return this;
    }

    public T shadowCornerRadius(int i) {
        this.shadowCornerRadius = i;
        return this;
    }

    public T shadowColor(int i) {
        this.shadowColor = i;
        return this;
    }

    public T normalImageDrawable(Drawable drawable) {
        if (this.normalImageDrawable != drawable) {
            this.normalImageDrawable = drawable;
            BoomButton button = button();
            if (button != null) {
                button.normalImageDrawable = drawable;
                button.updateImage();
            }
        }
        return this;
    }

    public T normalImageRes(int i) {
        if (this.normalImageRes != i) {
            this.normalImageRes = i;
            BoomButton button = button();
            if (button != null) {
                button.normalImageRes = i;
                button.updateImage();
            }
        }
        return this;
    }

    public T highlightedImageDrawable(Drawable drawable) {
        if (this.highlightedImageDrawable != drawable) {
            this.highlightedImageDrawable = drawable;
            BoomButton button = button();
            if (button != null) {
                button.highlightedImageDrawable = drawable;
                button.updateImage();
            }
        }
        return this;
    }

    public T highlightedImageRes(int i) {
        if (this.highlightedImageRes != i) {
            this.highlightedImageRes = i;
            BoomButton button = button();
            if (button != null) {
                button.highlightedImageRes = i;
                button.updateImage();
            }
        }
        return this;
    }

    public T unableImageDrawable(Drawable drawable) {
        if (this.unableImageDrawable != drawable) {
            this.unableImageDrawable = drawable;
            BoomButton button = button();
            if (button != null) {
                button.unableImageDrawable = drawable;
                button.updateImage();
            }
        }
        return this;
    }

    public T unableImageRes(int i) {
        if (this.unableImageRes != i) {
            this.unableImageRes = i;
            BoomButton button = button();
            if (button != null) {
                button.unableImageRes = i;
                button.updateImage();
            }
        }
        return this;
    }

    public T imageRect(Rect rect) {
        if (this.imageRect != rect) {
            this.imageRect = rect;
            BoomButton button = button();
            if (button != null) {
                button.imageRect = rect;
                button.updateImageRect();
            }
        }
        return this;
    }

    public T imagePadding(Rect rect) {
        if (this.imagePadding != rect) {
            this.imagePadding = rect;
            BoomButton button = button();
            if (button != null) {
                button.imagePadding = rect;
                button.updateImagePadding();
            }
        }
        return this;
    }

    public T rippleEffect(boolean z) {
        this.rippleEffect = z;
        return this;
    }

    public T normalColor(int i) {
        if (this.normalColor != i) {
            this.normalColor = i;
            BoomButton button = button();
            if (button != null) {
                button.normalColor = i;
                button.updateButtonDrawable();
            }
        }
        return this;
    }

    public T normalColorRes(int i) {
        if (this.normalColorRes != i) {
            this.normalColorRes = i;
            BoomButton button = button();
            if (button != null) {
                button.normalColorRes = i;
                button.updateButtonDrawable();
            }
        }
        return this;
    }

    public T highlightedColor(int i) {
        if (this.highlightedColor != i) {
            this.highlightedColor = i;
            BoomButton button = button();
            if (button != null) {
                button.highlightedColor = i;
                button.updateButtonDrawable();
            }
        }
        return this;
    }

    public T highlightedColorRes(int i) {
        if (this.highlightedColorRes != i) {
            this.highlightedColorRes = i;
            BoomButton button = button();
            if (button != null) {
                button.highlightedColorRes = i;
                button.updateButtonDrawable();
            }
        }
        return this;
    }

    public T unableColor(int i) {
        if (this.unableColor != i) {
            this.unableColor = i;
            BoomButton button = button();
            if (button != null) {
                button.unableColor = i;
                button.updateButtonDrawable();
            }
        }
        return this;
    }

    public T unableColorRes(int i) {
        if (this.unableColorRes != i) {
            this.unableColorRes = i;
            BoomButton button = button();
            if (button != null) {
                button.unableColorRes = i;
                button.updateButtonDrawable();
            }
        }
        return this;
    }

    public T pieceColor(int i) {
        Integer num = this.pieceColor;
        if (num == null || num.intValue() != i) {
            this.pieceColor = Integer.valueOf(i);
            BoomButton button = button();
            if (button != null) {
                button.pieceColor = Integer.valueOf(i);
            }
            BoomPiece boomPiece = this.piece;
            if (boomPiece != null) {
                boomPiece.setColor(i);
            }
        }
        return this;
    }

    public T pieceColorRes(int i) {
        if (this.pieceColorRes != i) {
            this.pieceColorRes = i;
            BoomButton button = button();
            if (button != null) {
                button.pieceColorRes = i;
            }
            BoomPiece boomPiece = this.piece;
            if (boomPiece != null) {
                boomPiece.setColorRes(i);
            }
        }
        return this;
    }

    public T unable(boolean z) {
        if (this.unable != z) {
            this.unable = z;
            BoomButton button = button();
            if (button != null) {
                button.unable = z;
                button.updateUnable();
                BoomPiece boomPiece = this.piece;
                if (boomPiece != null) {
                    boomPiece.setColor(button.pieceColor());
                }
            }
        }
        return this;
    }
}
