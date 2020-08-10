package com.stfalcon.chatkit.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;

public class RoundedImageView extends ImageView {
    private Drawable mDrawable;
    private float[] mRadii = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    private int mResource = 0;

    static class RoundedCornerDrawable extends Drawable {
        private Bitmap mBitmap;
        private final int mBitmapHeight;
        private final Paint mBitmapPaint;
        private final RectF mBitmapRect = new RectF();
        private BitmapShader mBitmapShader;
        private final int mBitmapWidth;
        private RectF mBounds = new RectF();
        private boolean mBoundsConfigured = false;
        private Path mPath = new Path();
        private float[] mRadii = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};

        RoundedCornerDrawable(Bitmap bitmap, Resources resources) {
            this.mBitmap = bitmap;
            this.mBitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
            this.mBitmapWidth = bitmap.getScaledWidth(resources.getDisplayMetrics());
            this.mBitmapHeight = bitmap.getScaledHeight(resources.getDisplayMetrics());
            this.mBitmapRect.set(0.0f, 0.0f, (float) this.mBitmapWidth, (float) this.mBitmapHeight);
            this.mBitmapPaint = new Paint(1);
            this.mBitmapPaint.setStyle(Style.FILL);
            this.mBitmapPaint.setShader(this.mBitmapShader);
        }

        static RoundedCornerDrawable fromBitmap(Bitmap bitmap, Resources resources) {
            if (bitmap != null) {
                return new RoundedCornerDrawable(bitmap, resources);
            }
            return null;
        }

        static Drawable fromDrawable(Drawable drawable, Resources resources) {
            if (drawable != null) {
                if (drawable instanceof RoundedCornerDrawable) {
                    return drawable;
                }
                if (drawable instanceof LayerDrawable) {
                    LayerDrawable layerDrawable = (LayerDrawable) drawable;
                    int numberOfLayers = layerDrawable.getNumberOfLayers();
                    for (int i = 0; i < numberOfLayers; i++) {
                        layerDrawable.setDrawableByLayerId(layerDrawable.getId(i), fromDrawable(layerDrawable.getDrawable(i), resources));
                    }
                    return layerDrawable;
                }
                Bitmap drawableToBitmap = drawableToBitmap(drawable);
                if (drawableToBitmap != null) {
                    drawable = new RoundedCornerDrawable(drawableToBitmap, resources);
                }
            }
            return drawable;
        }

        static Bitmap drawableToBitmap(Drawable drawable) {
            Bitmap bitmap = null;
            if (drawable == null) {
                return null;
            }
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }
            try {
                Bitmap createBitmap = Bitmap.createBitmap(Math.max(drawable.getIntrinsicWidth(), 2), Math.max(drawable.getIntrinsicHeight(), 2), Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                bitmap = createBitmap;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        private void configureBounds(Canvas canvas) {
            applyScaleToRadii(canvas.getMatrix());
            this.mBounds.set(this.mBitmapRect);
        }

        private void applyScaleToRadii(Matrix matrix) {
            float[] fArr = new float[9];
            matrix.getValues(fArr);
            int i = 0;
            while (true) {
                float[] fArr2 = this.mRadii;
                if (i < fArr2.length) {
                    fArr2[i] = fArr2[i] / fArr[0];
                    i++;
                } else {
                    return;
                }
            }
        }

        public void draw(Canvas canvas) {
            canvas.save();
            if (!this.mBoundsConfigured) {
                configureBounds(canvas);
                this.mBoundsConfigured = true;
            }
            this.mPath.addRoundRect(this.mBounds, this.mRadii, Direction.CW);
            canvas.drawPath(this.mPath, this.mBitmapPaint);
            canvas.restore();
        }

        /* access modifiers changed from: 0000 */
        public void setCornerRadii(float[] fArr) {
            if (fArr != null) {
                if (fArr.length == 8) {
                    System.arraycopy(fArr, 0, this.mRadii, 0, fArr.length);
                    return;
                }
                throw new ArrayIndexOutOfBoundsException("radii[] needs 8 values");
            }
        }

        public int getOpacity() {
            Bitmap bitmap = this.mBitmap;
            return (bitmap == null || bitmap.hasAlpha() || this.mBitmapPaint.getAlpha() < 255) ? -3 : -1;
        }

        public void setAlpha(int i) {
            this.mBitmapPaint.setAlpha(i);
            invalidateSelf();
        }

        public void setColorFilter(ColorFilter colorFilter) {
            this.mBitmapPaint.setColorFilter(colorFilter);
            invalidateSelf();
        }

        public void setDither(boolean z) {
            this.mBitmapPaint.setDither(z);
            invalidateSelf();
        }

        public void setFilterBitmap(boolean z) {
            this.mBitmapPaint.setFilterBitmap(z);
            invalidateSelf();
        }

        public int getIntrinsicWidth() {
            return this.mBitmapWidth;
        }

        public int getIntrinsicHeight() {
            return this.mBitmapHeight;
        }
    }

    public RoundedImageView(Context context) {
        super(context);
    }

    public RoundedImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RoundedImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    public void setImageDrawable(Drawable drawable) {
        this.mResource = 0;
        this.mDrawable = RoundedCornerDrawable.fromDrawable(drawable, getResources());
        super.setImageDrawable(this.mDrawable);
        updateDrawable();
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.mResource = 0;
        this.mDrawable = RoundedCornerDrawable.fromBitmap(bitmap, getResources());
        super.setImageDrawable(this.mDrawable);
        updateDrawable();
    }

    public void setImageResource(int i) {
        if (this.mResource != i) {
            this.mResource = i;
            this.mDrawable = resolveResource();
            super.setImageDrawable(this.mDrawable);
            updateDrawable();
        }
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        setImageDrawable(getDrawable());
    }

    public void setCorners(int i, int i2, int i3, int i4) {
        float f;
        float f2;
        float f3 = 0.0f;
        float dimension = i == 0 ? 0.0f : getResources().getDimension(i);
        if (i2 == 0) {
            f = 0.0f;
        } else {
            f = getResources().getDimension(i2);
        }
        if (i3 == 0) {
            f2 = 0.0f;
        } else {
            f2 = getResources().getDimension(i3);
        }
        if (i4 != 0) {
            f3 = getResources().getDimension(i4);
        }
        setCorners(dimension, f, f2, f3);
    }

    public void setCorners(float f, float f2, float f3, float f4) {
        this.mRadii = new float[]{f, f, f2, f2, f3, f3, f4, f4};
        updateDrawable();
    }

    private Drawable resolveResource() {
        Drawable drawable;
        if (this.mResource != 0) {
            try {
                drawable = ContextCompat.getDrawable(getContext(), this.mResource);
            } catch (NotFoundException unused) {
                this.mResource = 0;
            }
            return RoundedCornerDrawable.fromDrawable(drawable, getResources());
        }
        drawable = null;
        return RoundedCornerDrawable.fromDrawable(drawable, getResources());
    }

    private void updateDrawable() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            ((RoundedCornerDrawable) drawable).setCornerRadii(this.mRadii);
        }
    }
}
