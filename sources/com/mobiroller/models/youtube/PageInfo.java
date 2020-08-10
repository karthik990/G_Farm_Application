package com.mobiroller.models.youtube;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class PageInfo implements Serializable {
    @SerializedName("resultsPerPage")
    @Expose
    private Integer resultsPerPage;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;

    public Integer getTotalResults() {
        return this.totalResults;
    }

    public void setTotalResults(Integer num) {
        this.totalResults = num;
    }

    public Integer getResultsPerPage() {
        return this.resultsPerPage;
    }

    public void setResultsPerPage(Integer num) {
        this.resultsPerPage = num;
    }
}
