package com.mobiroller.views;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import com.mobiroller.adapters.PagedDragDropGridAdapter;
import com.mobiroller.containers.PagedContainer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DragDropGrid extends ViewGroup implements OnTouchListener, OnLongClickListener {
    private static int ANIMATION_DURATION = 250;
    private static int EGDE_DETECTION_MARGIN = 35;
    private PagedDragDropGridAdapter adapter;
    private int biggestChildHeight;
    private int biggestChildWidth;
    private int columnWidthSize;
    private int computedColumnCount;
    private int computedRowCount;
    private PagedContainer container;
    private DeleteDropZoneView deleteZone;
    private int dragged = -1;
    private Timer edgeScrollTimer;
    /* access modifiers changed from: private */
    public final Handler edgeTimerHandler = new Handler();
    private int gridPageHeight;
    private int gridPageWidth = 0;
    private int initialX;
    private int initialY;
    private int lastTarget = -1;
    private int lastTouchX;
    private int lastTouchY;
    private boolean movingView;
    private SparseIntArray newPositions = new SparseIntArray();
    private OnClickListener onClickListener = null;
    private int rowHeightSize;
    /* access modifiers changed from: private */
    public boolean wasOnEdgeJustNow = false;

    private class ItemPosition {
        public int itemIndex;
        public int pageIndex;

        public ItemPosition(int i, int i2) {
            this.pageIndex = i;
            this.itemIndex = i2;
        }
    }

    private boolean childHasMoved(int i) {
        return i != -1;
    }

    private boolean viewWasAlreadyMoved(int i, int i2) {
        return i2 != i;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    public DragDropGrid(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public DragDropGrid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public DragDropGrid(Context context) {
        super(context);
        init();
    }

    public DragDropGrid(Context context, AttributeSet attributeSet, int i, PagedDragDropGridAdapter pagedDragDropGridAdapter, PagedContainer pagedContainer) {
        super(context, attributeSet, i);
        this.adapter = pagedDragDropGridAdapter;
        this.container = pagedContainer;
        init();
    }

    public DragDropGrid(Context context, AttributeSet attributeSet, PagedDragDropGridAdapter pagedDragDropGridAdapter, PagedContainer pagedContainer) {
        super(context, attributeSet);
        this.adapter = pagedDragDropGridAdapter;
        this.container = pagedContainer;
        init();
    }

    public DragDropGrid(Context context, PagedDragDropGridAdapter pagedDragDropGridAdapter, PagedContainer pagedContainer) {
        super(context);
        this.adapter = pagedDragDropGridAdapter;
        this.container = pagedContainer;
        init();
    }

    private void init() {
        if (isInEditMode() && this.adapter == null) {
            useEditModeAdapter();
        }
        setOnTouchListener(this);
        setOnLongClickListener(this);
        createDeleteZone();
    }

    private void useEditModeAdapter() {
        this.adapter = new PagedDragDropGridAdapter() {
            public int columnCount() {
                return 0;
            }

            public int deleteDropZoneLocation() {
                return 2;
            }

            public int getCount() {
                return 0;
            }

            public Object getItem(int i) {
                return null;
            }

            public long getItemId(int i) {
                return 0;
            }

            public View getView(int i, View view, ViewGroup viewGroup) {
                return null;
            }

            public int itemCountInPage(int i) {
                return 0;
            }

            public int pageCount() {
                return 3;
            }

            public int rowCount() {
                return 3;
            }

            public boolean showRemoveDropZone() {
                return true;
            }

            public View view(int i, int i2) {
                return null;
            }
        };
    }

    public void setAdapter(PagedDragDropGridAdapter pagedDragDropGridAdapter) {
        this.adapter = pagedDragDropGridAdapter;
        addChildViews();
    }

    public void setOnClickListener(OnClickListener onClickListener2) {
        this.onClickListener = onClickListener2;
    }

    private void addChildViews() {
        for (int i = 0; i < this.adapter.pageCount(); i++) {
            for (int i2 = 0; i2 < this.adapter.itemCountInPage(i); i2++) {
                addView(this.adapter.view(i, i2));
            }
        }
        this.deleteZone.bringToFront();
    }

    /* access modifiers changed from: private */
    public void animateMoveAllItems() {
        Animation createFastRotateAnimation = createFastRotateAnimation();
        for (int i = 0; i < getItemViewCount(); i++) {
            getChildAt(i).startAnimation(createFastRotateAnimation);
        }
    }

    /* access modifiers changed from: private */
    public void cancelAnimations() {
        for (int i = 0; i < getItemViewCount() - 2; i++) {
            getChildAt(i).clearAnimation();
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return onTouch(null, motionEvent);
    }

    private void touchUp(MotionEvent motionEvent) {
        if (aViewIsDragged()) {
            cancelAnimations();
            manageChildrenReordering();
            hideDeleteView();
            cancelEdgeTimer();
            this.movingView = false;
            this.dragged = -1;
            this.lastTarget = -1;
            this.container.enableScroll();
        } else if (this.onClickListener != null) {
            View childAt = getChildAt(getTargetAtCoor((int) motionEvent.getX(), (int) motionEvent.getY()));
            if (childAt != null) {
                this.onClickListener.onClick(childAt);
            }
        }
    }

    private void manageChildrenReordering() {
        if (touchUpInDeleteZoneDrop(this.lastTouchX, this.lastTouchY)) {
            animateDeleteDragged();
            reorderChildrenWhenDraggedIsDeleted();
            return;
        }
        reorderChildren();
    }

    private void animateDeleteDragged() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.4f, 0.0f, 1.4f, 0.0f, (float) (this.biggestChildWidth / 2), (float) (this.biggestChildHeight / 2));
        scaleAnimation.setDuration(200);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setFillEnabled(true);
        getDraggedView().clearAnimation();
        getDraggedView().startAnimation(scaleAnimation);
    }

    private void reorderChildrenWhenDraggedIsDeleted() {
        SparseIntArray sparseIntArray = this.newPositions;
        int i = this.dragged;
        Integer valueOf = Integer.valueOf(sparseIntArray.get(i, i));
        addReorderedChildrenToParent(cleanUnorderedChildren());
        tellAdapterDraggedIsDeleted(valueOf);
        removeViewAt(valueOf.intValue());
        requestLayout();
    }

    private void tellAdapterDraggedIsDeleted(Integer num) {
        ItemPosition itemInformationAtPosition = itemInformationAtPosition(num.intValue());
        this.adapter.deleteItem(itemInformationAtPosition.pageIndex, itemInformationAtPosition.itemIndex);
    }

    private void touchDown(MotionEvent motionEvent) {
        this.initialX = (int) motionEvent.getRawX();
        this.initialY = (int) motionEvent.getRawY();
        this.lastTouchX = ((int) motionEvent.getRawX()) + (currentPage() * this.gridPageWidth);
        this.lastTouchY = (int) motionEvent.getRawY();
    }

    private void touchMove(MotionEvent motionEvent) {
        if (this.movingView && aViewIsDragged()) {
            this.lastTouchX = (int) motionEvent.getX();
            this.lastTouchY = (int) motionEvent.getY();
            ensureThereIsNoArtifact();
            moveDraggedView(this.lastTouchX, this.lastTouchY);
            manageSwapPosition(this.lastTouchX, this.lastTouchY);
            manageEdgeCoordinates(this.lastTouchX);
            manageDeleteZoneHover(this.lastTouchX, this.lastTouchY);
        }
    }

    private void ensureThereIsNoArtifact() {
        invalidate();
    }

    private void manageDeleteZoneHover(int i, int i2) {
        Rect rect = new Rect();
        this.deleteZone.getHitRect(rect);
        if (rect.intersect(i, i2, i + 1, i2 + 1)) {
            this.deleteZone.highlight();
        } else {
            this.deleteZone.smother();
        }
    }

    private boolean touchUpInDeleteZoneDrop(int i, int i2) {
        Rect rect = new Rect();
        this.deleteZone.getHitRect(rect);
        if (!rect.intersect(i, i2, i + 1, i2 + 1)) {
            return false;
        }
        this.deleteZone.smother();
        return true;
    }

    private void moveDraggedView(int i, int i2) {
        View draggedView = getDraggedView();
        int measuredWidth = draggedView.getMeasuredWidth();
        int measuredHeight = draggedView.getMeasuredHeight();
        int i3 = i - ((measuredWidth * 1) / 2);
        int i4 = i2 - ((measuredHeight * 1) / 2);
        draggedView.layout(i3, i4, measuredWidth + i3, measuredHeight + i4);
    }

    private void manageSwapPosition(int i, int i2) {
        int targetAtCoor = getTargetAtCoor(i, i2);
        if (childHasMoved(targetAtCoor) && targetAtCoor != this.lastTarget) {
            animateGap(targetAtCoor);
            this.lastTarget = targetAtCoor;
        }
    }

    private void manageEdgeCoordinates(int i) {
        boolean onRightEdgeOfScreen = onRightEdgeOfScreen(i);
        boolean onLeftEdgeOfScreen = onLeftEdgeOfScreen(i);
        if (!canScrollToEitherSide(onRightEdgeOfScreen, onLeftEdgeOfScreen)) {
            if (this.wasOnEdgeJustNow) {
                stopAnimateOnTheEdge();
            }
            this.wasOnEdgeJustNow = false;
            cancelEdgeTimer();
        } else if (!this.wasOnEdgeJustNow) {
            startEdgeDelayTimer(onRightEdgeOfScreen, onLeftEdgeOfScreen);
            this.wasOnEdgeJustNow = true;
        }
    }

    private void stopAnimateOnTheEdge() {
        getDraggedView().clearAnimation();
        animateDragged();
    }

    private void cancelEdgeTimer() {
        Timer timer = this.edgeScrollTimer;
        if (timer != null) {
            timer.cancel();
            this.edgeScrollTimer = null;
        }
    }

    private void startEdgeDelayTimer(boolean z, boolean z2) {
        if (canScrollToEitherSide(z, z2)) {
            animateOnTheEdge();
            if (this.edgeScrollTimer == null) {
                this.edgeScrollTimer = new Timer();
                scheduleScroll(z, z2);
            }
        }
    }

    private void scheduleScroll(final boolean z, final boolean z2) {
        this.edgeScrollTimer.schedule(new TimerTask() {
            public void run() {
                if (DragDropGrid.this.wasOnEdgeJustNow) {
                    DragDropGrid.this.wasOnEdgeJustNow = false;
                    DragDropGrid.this.edgeTimerHandler.post(new Runnable() {
                        public void run() {
                            DragDropGrid.this.hideDeleteView();
                            DragDropGrid.this.scroll(z, z2);
                            DragDropGrid.this.cancelAnimations();
                            DragDropGrid.this.animateMoveAllItems();
                            DragDropGrid.this.animateDragged();
                            DragDropGrid.this.popDeleteView();
                        }
                    });
                }
            }
        }, 1000);
    }

    private boolean canScrollToEitherSide(boolean z, boolean z2) {
        return (z2 && this.container.canScrollToPreviousPage()) || (z && this.container.canScrollToNextPage());
    }

    /* access modifiers changed from: private */
    public void scroll(boolean z, boolean z2) {
        cancelEdgeTimer();
        if (z2 && this.container.canScrollToPreviousPage()) {
            scrollToPreviousPage();
        } else if (z && this.container.canScrollToNextPage()) {
            scrollToNextPage();
        }
        this.wasOnEdgeJustNow = false;
    }

    private void scrollToNextPage() {
        tellAdapterToMoveItemToNextPage(this.dragged);
        moveDraggedToNextPage();
        this.container.scrollRight();
        int currentPage = currentPage();
        this.dragged = positionOfItem(currentPage, this.adapter.itemCountInPage(currentPage) - 1);
        stopAnimateOnTheEdge();
    }

    private void scrollToPreviousPage() {
        tellAdapterToMoveItemToPreviousPage(this.dragged);
        moveDraggedToPreviousPage();
        this.container.scrollLeft();
        int currentPage = currentPage();
        this.dragged = positionOfItem(currentPage, this.adapter.itemCountInPage(currentPage) - 1);
        stopAnimateOnTheEdge();
    }

    private void moveDraggedToPreviousPage() {
        List reeorderView = reeorderView(cleanUnorderedChildren());
        SparseIntArray sparseIntArray = this.newPositions;
        int i = this.dragged;
        int i2 = sparseIntArray.get(i, i);
        View view = (View) reeorderView.get(i2);
        reeorderView.remove(i2);
        reorderAndAddViews(reeorderView, view, findTheIndexOfFirstElementInCurrentPage() - 1);
    }

    private int findTheIndexOfFirstElementInCurrentPage() {
        int i = 0;
        for (int i2 = 0; i2 < currentPage(); i2++) {
            i += this.adapter.itemCountInPage(i2);
        }
        return i;
    }

    private void removeItemChildren(List<View> list) {
        for (View removeView : list) {
            removeView(removeView);
        }
    }

    private void moveDraggedToNextPage() {
        List reeorderView = reeorderView(cleanUnorderedChildren());
        SparseIntArray sparseIntArray = this.newPositions;
        int i = this.dragged;
        int i2 = sparseIntArray.get(i, i);
        View view = (View) reeorderView.get(i2);
        reeorderView.remove(i2);
        reorderAndAddViews(reeorderView, view, findTheIndexLastElementInNextPage() - 1);
    }

    private int findTheIndexLastElementInNextPage() {
        int i = 0;
        for (int i2 = 0; i2 <= currentPage() + 1; i2++) {
            i += this.adapter.itemCountInPage(i2);
        }
        return i;
    }

    private void reorderAndAddViews(List<View> list, View view, int i) {
        list.add(i, view);
        this.newPositions.clear();
        for (View view2 : list) {
            if (view2 != null) {
                addView(view2);
            }
        }
        this.deleteZone.bringToFront();
    }

    private boolean onLeftEdgeOfScreen(int i) {
        return i > 0 && i - (this.container.currentPage() * this.gridPageWidth) <= EGDE_DETECTION_MARGIN;
    }

    private boolean onRightEdgeOfScreen(int i) {
        int currentPage = this.container.currentPage();
        int i2 = this.gridPageWidth;
        int i3 = (currentPage * i2) + i2;
        int i4 = i3 - i;
        int i5 = EGDE_DETECTION_MARGIN;
        return i > i3 - i5 && i4 < i5;
    }

    private void animateOnTheEdge() {
        View draggedView = getDraggedView();
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.667f, 1.5f, 0.667f, 1.5f, (float) ((draggedView.getMeasuredWidth() * 3) / 4), (float) ((draggedView.getMeasuredHeight() * 3) / 4));
        scaleAnimation.setDuration(200);
        scaleAnimation.setRepeatMode(2);
        scaleAnimation.setRepeatCount(-1);
        draggedView.clearAnimation();
        draggedView.startAnimation(scaleAnimation);
    }

    private void animateGap(int i) {
        int currentViewAtPosition = currentViewAtPosition(i);
        if (currentViewAtPosition != this.dragged) {
            View childView = getChildView(currentViewAtPosition);
            Point coorForIndex = getCoorForIndex(currentViewAtPosition);
            SparseIntArray sparseIntArray = this.newPositions;
            int i2 = this.dragged;
            animateMoveToNewPosition(childView, computeTranslationStartDeltaRelativeToRealViewPosition(i, currentViewAtPosition, coorForIndex), computeTranslationEndDeltaRelativeToRealViewPosition(coorForIndex, getCoorForIndex(sparseIntArray.get(i2, i2))));
            saveNewPositions(i, currentViewAtPosition);
        }
    }

    private Point computeTranslationEndDeltaRelativeToRealViewPosition(Point point, Point point2) {
        return new Point(point2.x - point.x, point2.y - point.y);
    }

    private Point computeTranslationStartDeltaRelativeToRealViewPosition(int i, int i2, Point point) {
        if (viewWasAlreadyMoved(i, i2)) {
            return computeTranslationEndDeltaRelativeToRealViewPosition(point, getCoorForIndex(i));
        }
        return new Point(0, 0);
    }

    private void saveNewPositions(int i, int i2) {
        SparseIntArray sparseIntArray = this.newPositions;
        int i3 = this.dragged;
        sparseIntArray.put(i2, sparseIntArray.get(i3, i3));
        this.newPositions.put(this.dragged, i);
        SparseIntArray sparseIntArray2 = this.newPositions;
        int i4 = this.dragged;
        tellAdapterToSwapDraggedWithTarget(sparseIntArray2.get(i4, i4), this.newPositions.get(i2, i2));
    }

    private void animateMoveToNewPosition(View view, Point point, Point point2) {
        AnimationSet animationSet = new AnimationSet(true);
        Animation createFastRotateAnimation = createFastRotateAnimation();
        TranslateAnimation createTranslateAnimation = createTranslateAnimation(point, point2);
        animationSet.addAnimation(createFastRotateAnimation);
        animationSet.addAnimation(createTranslateAnimation);
        view.clearAnimation();
        view.startAnimation(animationSet);
    }

    private TranslateAnimation createTranslateAnimation(Point point, Point point2) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, (float) point.x, 0, (float) point2.x, 0, (float) point.y, 0, (float) point2.y);
        translateAnimation.setDuration((long) ANIMATION_DURATION);
        translateAnimation.setFillEnabled(true);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        return translateAnimation;
    }

    private Animation createFastRotateAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(-2.0f, 2.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setRepeatMode(2);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setDuration(60);
        rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        return rotateAnimation;
    }

    private int currentViewAtPosition(int i) {
        for (int i2 = 0; i2 < this.newPositions.size(); i2++) {
            if (this.newPositions.valueAt(i2) == i) {
                return this.newPositions.keyAt(i2);
            }
        }
        return i;
    }

    private Point getCoorForIndex(int i) {
        ItemPosition itemInformationAtPosition = itemInformationAtPosition(i);
        int i2 = itemInformationAtPosition.itemIndex / this.computedColumnCount;
        return new Point((currentPage() * this.gridPageWidth) + (this.columnWidthSize * (itemInformationAtPosition.itemIndex - (this.computedColumnCount * i2))), this.rowHeightSize * i2);
    }

    private int getTargetAtCoor(int i, int i2) {
        int currentPage = currentPage();
        return positionOfItem(currentPage, getColumnOfCoordinate(i, currentPage) + (getRowOfCoordinate(i2) * this.computedColumnCount));
    }

    private int getColumnOfCoordinate(int i, int i2) {
        int i3 = i2 * this.gridPageWidth;
        int i4 = 1;
        int i5 = 0;
        while (i4 <= this.computedColumnCount && i >= (this.columnWidthSize * i4) + i3) {
            i5++;
            i4++;
        }
        return i5;
    }

    private int getRowOfCoordinate(int i) {
        int i2 = 1;
        int i3 = 0;
        while (i2 <= this.computedRowCount && i >= this.rowHeightSize * i2) {
            i3++;
            i2++;
        }
        return i3;
    }

    private int currentPage() {
        return this.container.currentPage();
    }

    private void reorderChildren() {
        addReorderedChildrenToParent(cleanUnorderedChildren());
    }

    private List<View> cleanUnorderedChildren() {
        List<View> saveChildren = saveChildren();
        removeItemChildren(saveChildren);
        return saveChildren;
    }

    private void addReorderedChildrenToParent(List<View> list) {
        List<View> reeorderView = reeorderView(list);
        this.newPositions.clear();
        for (View view : reeorderView) {
            if (view != null) {
                addView(view);
            }
        }
        this.deleteZone.bringToFront();
    }

    private List<View> saveChildren() {
        View view;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < getItemViewCount(); i++) {
            if (i == this.dragged) {
                view = getDraggedView();
            } else {
                view = getChildView(i);
            }
            view.clearAnimation();
            arrayList.add(view);
        }
        return arrayList;
    }

    private List<View> reeorderView(List<View> list) {
        View[] viewArr = new View[list.size()];
        for (int i = 0; i < list.size(); i++) {
            int i2 = this.newPositions.get(i, -1);
            if (childHasMoved(i2)) {
                viewArr[i2] = (View) list.get(i);
            } else {
                viewArr[i] = (View) list.get(i);
            }
        }
        return new ArrayList(Arrays.asList(viewArr));
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        Display defaultDisplay = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay();
        int acknowledgeWidthSize = acknowledgeWidthSize(mode, size, defaultDisplay);
        int acknowledgeHeightSize = acknowledgeHeightSize(mode2, size2, defaultDisplay);
        try {
            adaptChildrenMeasuresToViewSize(acknowledgeWidthSize, acknowledgeHeightSize);
            searchBiggestChildMeasures();
            computeGridMatrixSize(acknowledgeWidthSize, acknowledgeHeightSize);
            computeColumnsAndRowsSizes(acknowledgeWidthSize, acknowledgeHeightSize);
            measureChild(this.deleteZone, MeasureSpec.makeMeasureSpec(this.gridPageWidth, 1073741824), MeasureSpec.makeMeasureSpec((int) getPixelFromDip(40), 1073741824));
            setMeasuredDimension(acknowledgeWidthSize * this.adapter.pageCount(), acknowledgeHeightSize);
        } catch (Exception unused) {
        }
    }

    private float getPixelFromDip(int i) {
        return TypedValue.applyDimension(1, (float) i, getResources().getDisplayMetrics());
    }

    private void computeColumnsAndRowsSizes(int i, int i2) {
        try {
            this.columnWidthSize = i / this.computedColumnCount;
            this.rowHeightSize = i2 / this.computedRowCount;
        } catch (Exception unused) {
        }
    }

    private void computeGridMatrixSize(int i, int i2) {
        try {
            if (this.adapter.columnCount() != -1 && this.adapter.rowCount() != -1) {
                this.computedColumnCount = this.adapter.columnCount();
                this.computedRowCount = this.adapter.rowCount();
            } else if (this.biggestChildWidth > 0 && this.biggestChildHeight > 0) {
                this.computedColumnCount = i / this.biggestChildWidth;
                this.computedRowCount = i2 / this.biggestChildHeight;
            }
            if (this.computedColumnCount == 0) {
                this.computedColumnCount = 1;
            }
            if (this.computedRowCount == 0) {
                this.computedRowCount = 1;
            }
        } catch (Exception unused) {
        }
    }

    private void searchBiggestChildMeasures() {
        this.biggestChildWidth = 0;
        this.biggestChildHeight = 0;
        for (int i = 0; i < getItemViewCount(); i++) {
            View childAt = getChildAt(i);
            if (this.biggestChildHeight < childAt.getMeasuredHeight()) {
                this.biggestChildHeight = childAt.getMeasuredHeight();
            }
            if (this.biggestChildWidth < childAt.getMeasuredWidth()) {
                this.biggestChildWidth = childAt.getMeasuredWidth();
            }
        }
    }

    private int getItemViewCount() {
        return getChildCount() - 1;
    }

    private void adaptChildrenMeasuresToViewSize(int i, int i2) {
        try {
            if (this.adapter.columnCount() == 3 || this.adapter.rowCount() == 3) {
                measureChildren(0, 0);
                return;
            }
            measureChildren(MeasureSpec.makeMeasureSpec(i / this.adapter.columnCount(), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(i2 / this.adapter.rowCount(), Integer.MIN_VALUE));
        } catch (Exception unused) {
        }
    }

    private int acknowledgeHeightSize(int i, int i2, Display display) {
        if (i == 0) {
            i2 = display.getHeight();
        }
        this.gridPageHeight = i2;
        return i2;
    }

    private int acknowledgeWidthSize(int i, int i2, Display display) {
        if (i == 0) {
            i2 = display.getWidth();
        }
        this.gridPageWidth = i2;
        return i2;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        try {
            int pageCount = (i + i3) / this.adapter.pageCount();
            for (int i5 = 0; i5 < this.adapter.pageCount(); i5++) {
                layoutPage(pageCount, i5);
            }
            if (weWereMovingDraggedBetweenPages()) {
                bringDraggedToFront();
            }
        } catch (Exception unused) {
        }
    }

    private boolean weWereMovingDraggedBetweenPages() {
        return this.dragged != -1;
    }

    private void layoutPage(int i, int i2) {
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < this.adapter.itemCountInPage(i2); i5++) {
            layoutAChild(i, i2, i3, i4, i5);
            i3++;
            if (i3 == this.computedColumnCount) {
                i4++;
                i3 = 0;
            }
        }
    }

    private void layoutAChild(int i, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int positionOfItem = positionOfItem(i2, i5);
        View childAt = getChildAt(positionOfItem);
        if (positionOfItem != this.dragged || !lastTouchOnEdge()) {
            int i8 = i2 * i;
            int i9 = this.columnWidthSize;
            i7 = ((i9 - childAt.getMeasuredWidth()) / 2) + i8 + (i3 * i9);
            int i10 = this.rowHeightSize;
            i6 = ((i10 - childAt.getMeasuredHeight()) / 2) + (i4 * i10);
        } else {
            i7 = computePageEdgeXCoor(childAt);
            i6 = this.lastTouchY - (childAt.getMeasuredHeight() / 2);
        }
        childAt.layout(i7, i6, childAt.getMeasuredWidth() + i7, childAt.getMeasuredHeight() + i6);
    }

    private boolean lastTouchOnEdge() {
        return onRightEdgeOfScreen(this.lastTouchX) || onLeftEdgeOfScreen(this.lastTouchX);
    }

    private int computePageEdgeXCoor(View view) {
        int measuredWidth = this.lastTouchX - (view.getMeasuredWidth() / 2);
        if (onRightEdgeOfScreen(this.lastTouchX)) {
            return measuredWidth - this.gridPageWidth;
        }
        return onLeftEdgeOfScreen(this.lastTouchX) ? measuredWidth + this.gridPageWidth : measuredWidth;
    }

    public boolean onLongClick(View view) {
        if (positionForView(view) == -1) {
            return false;
        }
        this.container.disableScroll();
        this.movingView = true;
        this.dragged = positionForView(view);
        bringDraggedToFront();
        animateMoveAllItems();
        animateDragged();
        popDeleteView();
        return true;
    }

    private void bringDraggedToFront() {
        getChildAt(this.dragged).bringToFront();
        this.deleteZone.bringToFront();
    }

    private View getDraggedView() {
        return getChildAt(getChildCount() - 2);
    }

    /* access modifiers changed from: private */
    public void animateDragged() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.4f, 1.0f, 1.4f, (float) (this.biggestChildWidth / 2), (float) (this.biggestChildHeight / 2));
        scaleAnimation.setDuration(200);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setFillEnabled(true);
        if (aViewIsDragged()) {
            View draggedView = getDraggedView();
            draggedView.clearAnimation();
            draggedView.startAnimation(scaleAnimation);
        }
    }

    private boolean aViewIsDragged() {
        return weWereMovingDraggedBetweenPages();
    }

    /* access modifiers changed from: private */
    public void popDeleteView() {
        if (this.adapter.showRemoveDropZone()) {
            showDeleteView();
        }
    }

    private void showDeleteView() {
        this.deleteZone.setVisibility(0);
        int currentPage = currentPage() * this.deleteZone.getMeasuredWidth();
        this.deleteZone.layout(currentPage, computeDropZoneVerticalLocation(), this.gridPageWidth + currentPage, computeDropZoneVerticalBottom());
    }

    private int computeDropZoneVerticalBottom() {
        if (this.adapter.deleteDropZoneLocation() == 1) {
            return this.deleteZone.getMeasuredHeight();
        }
        return (this.gridPageHeight - this.deleteZone.getMeasuredHeight()) + this.gridPageHeight;
    }

    private int computeDropZoneVerticalLocation() {
        if (this.adapter.deleteDropZoneLocation() == 1) {
            return 0;
        }
        return this.gridPageHeight - this.deleteZone.getMeasuredHeight();
    }

    private void createDeleteZone() {
        this.deleteZone = new DeleteDropZoneView(getContext());
        addView(this.deleteZone);
    }

    /* access modifiers changed from: private */
    public void hideDeleteView() {
        this.deleteZone.setVisibility(4);
    }

    private int positionForView(View view) {
        for (int i = 0; i < getItemViewCount(); i++) {
            if (isPointInsideView((float) this.initialX, (float) this.initialY, getChildView(i))) {
                return i;
            }
        }
        return -1;
    }

    private View getChildView(int i) {
        if (!weWereMovingDraggedBetweenPages() || i < this.dragged) {
            return getChildAt(i);
        }
        return getChildAt(i - 1);
    }

    private boolean isPointInsideView(float f, float f2, View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return pointIsInsideViewBounds(f, f2, view, iArr[0], iArr[1]);
    }

    private boolean pointIsInsideViewBounds(float f, float f2, View view, int i, int i2) {
        return f > ((float) i) && f < ((float) (i + view.getWidth())) && f2 > ((float) i2) && f2 < ((float) (i2 + view.getHeight()));
    }

    public void setContainer(PagedDragDropGrid pagedDragDropGrid) {
        this.container = pagedDragDropGrid;
    }

    private int positionOfItem(int i, int i2) {
        int i3 = 0;
        int i4 = 0;
        while (i3 < this.adapter.pageCount()) {
            int itemCountInPage = this.adapter.itemCountInPage(i3);
            int i5 = i4;
            for (int i6 = 0; i6 < itemCountInPage; i6++) {
                if (i == i3 && i2 == i6) {
                    return i5;
                }
                i5++;
            }
            i3++;
            i4 = i5;
        }
        return -1;
    }

    private ItemPosition itemInformationAtPosition(int i) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < this.adapter.pageCount()) {
            int itemCountInPage = this.adapter.itemCountInPage(i2);
            int i4 = i3;
            for (int i5 = 0; i5 < itemCountInPage; i5++) {
                if (i4 == i) {
                    return new ItemPosition(i2, i5);
                }
                i4++;
            }
            i2++;
            i3 = i4;
        }
        return null;
    }

    private void tellAdapterToSwapDraggedWithTarget(int i, int i2) {
        ItemPosition itemInformationAtPosition = itemInformationAtPosition(i);
        ItemPosition itemInformationAtPosition2 = itemInformationAtPosition(i2);
        if (itemInformationAtPosition != null && itemInformationAtPosition2 != null) {
            this.adapter.swapItems(itemInformationAtPosition.pageIndex, itemInformationAtPosition.itemIndex, itemInformationAtPosition2.itemIndex);
        }
    }

    private void tellAdapterToMoveItemToPreviousPage(int i) {
        ItemPosition itemInformationAtPosition = itemInformationAtPosition(i);
        this.adapter.moveItemToPreviousPage(itemInformationAtPosition.pageIndex, itemInformationAtPosition.itemIndex);
    }

    private void tellAdapterToMoveItemToNextPage(int i) {
        ItemPosition itemInformationAtPosition = itemInformationAtPosition(i);
        this.adapter.moveItemToNextPage(itemInformationAtPosition.pageIndex, itemInformationAtPosition.itemIndex);
    }
}
