package com.nightonke.boommenu.Animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Util;
import java.util.ArrayList;
import java.util.Iterator;

public class ShareLinesView extends View {
    private long animationHideDelay1;
    private long animationHideDelay2;
    private long animationHideDelay3;
    private long animationHideDuration1;
    private long animationHideDuration2;
    private long animationHideTotalDuration;
    private long animationShowDelay1;
    private long animationShowDelay2;
    private long animationShowDelay3;
    private long animationShowDuration1;
    private long animationShowDuration2;
    private long animationShowTotalDuration;
    private int line1Color = -1;
    private int line2Color = -1;
    private Paint linePaint = new Paint();
    private float lineWidth = 3.0f;
    private ArrayList<PointF> piecePositions;
    private float processForLine1 = 1.0f;
    private float processForLine2 = 1.0f;

    public ShareLinesView(Context context) {
        super(context);
        this.linePaint.setAntiAlias(true);
    }

    public void setData(ArrayList<RectF> arrayList, BoomMenuButton boomMenuButton) {
        float dotRadius = boomMenuButton.getDotRadius() - (this.lineWidth / 4.0f);
        double dotRadius2 = (double) boomMenuButton.getDotRadius();
        double d = (double) this.lineWidth;
        double sqrt = Math.sqrt(3.0d);
        Double.isNaN(d);
        double d2 = (d * sqrt) / 4.0d;
        Double.isNaN(dotRadius2);
        float dp2px = ((float) (dotRadius2 - d2)) + ((float) Util.dp2px(0.25f));
        this.piecePositions = new ArrayList<>();
        Iterator it = arrayList.iterator();
        while (true) {
            boolean z = false;
            if (!it.hasNext()) {
                break;
            }
            RectF rectF = (RectF) it.next();
            Iterator it2 = this.piecePositions.iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (((PointF) it2.next()).equals(rectF.left, rectF.top)) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!z) {
                this.piecePositions.add(new PointF(rectF.left, rectF.top));
            }
        }
        Iterator it3 = this.piecePositions.iterator();
        while (it3.hasNext()) {
            ((PointF) it3.next()).offset(dotRadius, dp2px);
        }
        int[] iArr = new int[3];
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            int i2 = i % 3;
            iArr[i2] = iArr[i2] + 1;
        }
        this.animationShowDelay1 = boomMenuButton.getShowDelay() * ((long) (iArr[0] - 1));
        this.animationShowDuration1 = ((long) iArr[0]) * boomMenuButton.getShowDelay();
        this.animationShowDelay2 = ((long) ((iArr[0] - 1) + iArr[1])) * boomMenuButton.getShowDelay();
        this.animationShowDuration2 = ((long) (iArr[0] + iArr[1])) * boomMenuButton.getShowDelay();
        this.animationShowDelay3 = (boomMenuButton.getShowDelay() * ((long) ((iArr[2] - 1) + iArr[1] + iArr[0]))) + boomMenuButton.getShowDuration();
        this.animationShowTotalDuration = this.animationShowDelay3;
        this.animationHideDelay1 = (((long) (iArr[2] - 1)) * boomMenuButton.getHideDelay()) + boomMenuButton.getHideDuration();
        this.animationHideDuration1 = (((long) iArr[2]) * boomMenuButton.getHideDelay()) + boomMenuButton.getHideDuration();
        this.animationHideDelay2 = (boomMenuButton.getHideDelay() * ((long) ((iArr[2] - 1) + iArr[1]))) + boomMenuButton.getHideDuration();
        this.animationHideDuration2 = (((long) (iArr[2] + iArr[1])) * boomMenuButton.getHideDelay()) + boomMenuButton.getHideDuration();
        this.animationHideDelay3 = (boomMenuButton.getHideDelay() * ((long) ((iArr[2] - 1) + iArr[1] + iArr[0]))) + boomMenuButton.getHideDuration();
        this.animationHideTotalDuration = this.animationHideDelay3;
    }

    private void setShowProcess(float f) {
        long j = (long) (f * ((float) this.animationShowTotalDuration));
        long j2 = this.animationShowDelay1;
        if (j2 < j) {
            long j3 = this.animationShowDuration1;
            if (j <= j3) {
                this.processForLine1 = ((float) (j3 - j)) / ((float) (j3 - j2));
                invalidate();
            }
        }
        if (this.animationShowDuration1 >= j || j > this.animationShowDelay2) {
            long j4 = this.animationShowDelay2;
            if (j4 < j) {
                long j5 = this.animationShowDuration2;
                if (j <= j5) {
                    this.processForLine2 = ((float) (j5 - j)) / ((float) (j5 - j4));
                    invalidate();
                }
            }
            if (this.animationShowDuration2 <= j) {
                this.processForLine1 = 0.0f;
                this.processForLine2 = 0.0f;
            }
            invalidate();
        }
        this.processForLine1 = 0.0f;
        invalidate();
    }

    private void setHideProcess(float f) {
        long j = (long) (f * ((float) this.animationHideTotalDuration));
        long j2 = this.animationHideDelay1;
        if (j2 < j) {
            long j3 = this.animationHideDuration1;
            if (j <= j3) {
                this.processForLine2 = 1.0f - (((float) (j3 - j)) / ((float) (j3 - j2)));
                invalidate();
            }
        }
        if (this.animationHideDuration1 >= j || j > this.animationHideDelay2) {
            long j4 = this.animationHideDelay2;
            if (j4 < j) {
                long j5 = this.animationHideDuration2;
                if (j <= j5) {
                    this.processForLine1 = 1.0f - (((float) (j5 - j)) / ((float) (j5 - j4)));
                    invalidate();
                }
            }
            if (this.animationHideDuration2 <= j) {
                this.processForLine1 = 1.0f;
                this.processForLine2 = 1.0f;
            }
            invalidate();
        }
        this.processForLine2 = 1.0f;
        invalidate();
    }

    public void setLine1Color(int i) {
        this.line1Color = i;
        this.linePaint.setColor(i);
    }

    public void setLine2Color(int i) {
        this.line2Color = i;
    }

    public void setLineWidth(float f) {
        this.lineWidth = f;
        this.linePaint.setStrokeWidth(f);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
        canvas2.drawLine(((PointF) this.piecePositions.get(1)).x, ((PointF) this.piecePositions.get(1)).y, ((PointF) this.piecePositions.get(1)).x + ((((PointF) this.piecePositions.get(0)).x - ((PointF) this.piecePositions.get(1)).x) * this.processForLine1), ((((PointF) this.piecePositions.get(0)).y - ((PointF) this.piecePositions.get(1)).y) * this.processForLine1) + ((PointF) this.piecePositions.get(1)).y, this.linePaint);
        this.linePaint.setColor(this.line2Color);
        canvas.drawLine(((PointF) this.piecePositions.get(2)).x, ((PointF) this.piecePositions.get(2)).y, ((((PointF) this.piecePositions.get(1)).x - ((PointF) this.piecePositions.get(2)).x) * this.processForLine2) + ((PointF) this.piecePositions.get(2)).x, ((((PointF) this.piecePositions.get(1)).y - ((PointF) this.piecePositions.get(2)).y) * this.processForLine2) + ((PointF) this.piecePositions.get(2)).y, this.linePaint);
        super.onDraw(canvas);
    }

    public void place(int i, int i2, int i3, int i4) {
        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        if (layoutParams != null) {
            layoutParams.leftMargin = i;
            layoutParams.topMargin = i2;
            layoutParams.width = i3;
            layoutParams.height = i4;
            setLayoutParams(layoutParams);
        }
    }
}
