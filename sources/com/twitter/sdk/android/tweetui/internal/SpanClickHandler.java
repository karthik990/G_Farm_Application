package com.twitter.sdk.android.tweetui.internal;

import android.text.Layout;
import android.text.Spanned;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class SpanClickHandler {
    private HighlightedClickableSpan highlightedClickableSpan;
    private Layout layout;
    private float left;
    private float top;
    private final View view;

    public static void enableClicksOnSpans(TextView textView) {
        textView.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return SpanClickHandler.lambda$enableClicksOnSpans$0(SpanClickHandler.this, view, motionEvent);
            }
        });
    }

    static /* synthetic */ boolean lambda$enableClicksOnSpans$0(SpanClickHandler spanClickHandler, View view2, MotionEvent motionEvent) {
        TextView textView = (TextView) view2;
        Layout layout2 = textView.getLayout();
        if (layout2 == null) {
            return false;
        }
        spanClickHandler.layout = layout2;
        spanClickHandler.left = (float) (textView.getTotalPaddingLeft() + textView.getScrollX());
        spanClickHandler.top = (float) (textView.getTotalPaddingTop() + textView.getScrollY());
        return spanClickHandler.handleTouchEvent(motionEvent);
    }

    public SpanClickHandler(View view2, Layout layout2) {
        this.view = view2;
        this.layout = layout2;
    }

    public boolean handleTouchEvent(MotionEvent motionEvent) {
        CharSequence text = this.layout.getText();
        Spanned spanned = text instanceof Spanned ? (Spanned) text : null;
        if (spanned == null) {
            return false;
        }
        int action = motionEvent.getAction() & 255;
        int x = (int) (motionEvent.getX() - this.left);
        int y = (int) (motionEvent.getY() - this.top);
        if (x < 0 || x >= this.layout.getWidth() || y < 0 || y >= this.layout.getHeight()) {
            deselectSpan();
            return false;
        }
        int lineForVertical = this.layout.getLineForVertical(y);
        float f = (float) x;
        if (f < this.layout.getLineLeft(lineForVertical) || f > this.layout.getLineRight(lineForVertical)) {
            deselectSpan();
            return false;
        }
        if (action == 0) {
            int offsetForHorizontal = this.layout.getOffsetForHorizontal(lineForVertical, f);
            HighlightedClickableSpan[] highlightedClickableSpanArr = (HighlightedClickableSpan[]) spanned.getSpans(offsetForHorizontal, offsetForHorizontal, HighlightedClickableSpan.class);
            if (highlightedClickableSpanArr.length > 0) {
                selectSpan(highlightedClickableSpanArr[0]);
                return true;
            }
        } else if (action == 1) {
            HighlightedClickableSpan highlightedClickableSpan2 = this.highlightedClickableSpan;
            if (highlightedClickableSpan2 != null) {
                highlightedClickableSpan2.onClick(this.view);
                deselectSpan();
                return true;
            }
        }
        return false;
    }

    private void selectSpan(HighlightedClickableSpan highlightedClickableSpan2) {
        highlightedClickableSpan2.select(true);
        this.highlightedClickableSpan = highlightedClickableSpan2;
        invalidate();
    }

    private void deselectSpan() {
        HighlightedClickableSpan highlightedClickableSpan2 = this.highlightedClickableSpan;
        if (highlightedClickableSpan2 != null && highlightedClickableSpan2.isSelected()) {
            highlightedClickableSpan2.select(false);
            this.highlightedClickableSpan = null;
            invalidate();
        }
    }

    private void invalidate() {
        View view2 = this.view;
        float f = this.left;
        view2.invalidate((int) f, (int) this.top, ((int) f) + this.layout.getWidth(), ((int) this.top) + this.layout.getHeight());
    }
}
