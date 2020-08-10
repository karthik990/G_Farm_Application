package com.mobiroller.views.twowayview;

import java.util.Arrays;

class ItemEntries {
    private static final int MIN_SIZE = 10;
    private int mAdapterSize;
    private ItemEntry[] mItemEntries;
    private boolean mRestoringItem;

    ItemEntries() {
    }

    private int sizeForPosition(int i) {
        int length = this.mItemEntries.length;
        while (length <= i) {
            length *= 2;
        }
        if (!this.mRestoringItem) {
            int i2 = this.mAdapterSize;
            if (length > i2) {
                return i2;
            }
        }
        return length;
    }

    private void ensureSize(int i) {
        ItemEntry[] itemEntryArr = this.mItemEntries;
        if (itemEntryArr == null) {
            this.mItemEntries = new ItemEntry[(Math.max(i, 10) + 1)];
            Arrays.fill(this.mItemEntries, null);
        } else if (i >= itemEntryArr.length) {
            this.mItemEntries = new ItemEntry[sizeForPosition(i)];
            System.arraycopy(itemEntryArr, 0, this.mItemEntries, 0, itemEntryArr.length);
            ItemEntry[] itemEntryArr2 = this.mItemEntries;
            Arrays.fill(itemEntryArr2, itemEntryArr.length, itemEntryArr2.length, null);
        }
    }

    public ItemEntry getItemEntry(int i) {
        ItemEntry[] itemEntryArr = this.mItemEntries;
        if (itemEntryArr == null || i >= itemEntryArr.length) {
            return null;
        }
        return itemEntryArr[i];
    }

    public void putItemEntry(int i, ItemEntry itemEntry) {
        ensureSize(i);
        this.mItemEntries[i] = itemEntry;
    }

    public void restoreItemEntry(int i, ItemEntry itemEntry) {
        this.mRestoringItem = true;
        putItemEntry(i, itemEntry);
        this.mRestoringItem = false;
    }

    public int size() {
        ItemEntry[] itemEntryArr = this.mItemEntries;
        if (itemEntryArr != null) {
            return itemEntryArr.length;
        }
        return 0;
    }

    public void setAdapterSize(int i) {
        this.mAdapterSize = i;
    }

    public void invalidateItemLanesAfter(int i) {
        ItemEntry[] itemEntryArr = this.mItemEntries;
        if (itemEntryArr != null && i < itemEntryArr.length) {
            while (true) {
                ItemEntry[] itemEntryArr2 = this.mItemEntries;
                if (i < itemEntryArr2.length) {
                    ItemEntry itemEntry = itemEntryArr2[i];
                    if (itemEntry != null) {
                        itemEntry.invalidateLane();
                    }
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    public void clear() {
        ItemEntry[] itemEntryArr = this.mItemEntries;
        if (itemEntryArr != null) {
            Arrays.fill(itemEntryArr, null);
        }
    }

    /* access modifiers changed from: 0000 */
    public void offsetForRemoval(int i, int i2) {
        ItemEntry[] itemEntryArr = this.mItemEntries;
        if (itemEntryArr != null && i < itemEntryArr.length) {
            int i3 = i + i2;
            ensureSize(i3);
            ItemEntry[] itemEntryArr2 = this.mItemEntries;
            System.arraycopy(itemEntryArr2, i3, itemEntryArr2, i, (itemEntryArr2.length - i) - i2);
            ItemEntry[] itemEntryArr3 = this.mItemEntries;
            Arrays.fill(itemEntryArr3, itemEntryArr3.length - i2, itemEntryArr3.length, null);
        }
    }

    /* access modifiers changed from: 0000 */
    public void offsetForAddition(int i, int i2) {
        ItemEntry[] itemEntryArr = this.mItemEntries;
        if (itemEntryArr != null && i < itemEntryArr.length) {
            int i3 = i + i2;
            ensureSize(i3);
            ItemEntry[] itemEntryArr2 = this.mItemEntries;
            System.arraycopy(itemEntryArr2, i, itemEntryArr2, i3, (itemEntryArr2.length - i) - i2);
            Arrays.fill(this.mItemEntries, i, i3, null);
        }
    }
}
