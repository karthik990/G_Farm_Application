package com.mobiroller.models.ecommerce;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductListResponse implements Serializable {
    private List<ProductListModel> data;

    public List<ProductListModel> getData() {
        List<ProductListModel> list = this.data;
        return list == null ? new ArrayList() : list;
    }
}
