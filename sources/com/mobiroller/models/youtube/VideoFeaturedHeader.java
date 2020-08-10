package com.mobiroller.models.youtube;

import java.io.Serializable;

public class VideoFeaturedHeader implements Serializable {
    private ItemDetail itemDetail;

    public VideoFeaturedHeader(ItemDetail itemDetail2) {
        this.itemDetail = itemDetail2;
    }

    public ItemDetail getItemDetail() {
        return this.itemDetail;
    }

    public void setItemDetail(ItemDetail itemDetail2) {
        this.itemDetail = itemDetail2;
    }
}
