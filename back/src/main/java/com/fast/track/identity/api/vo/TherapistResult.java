package com.irichment.identity.api.vo;

import java.util.List;

/**
 */
public class TherapistResult {
    private List<Therapist> data;
    private Integer page;
    private Integer totalSize;

    public TherapistResult(List<Therapist> data, Integer totalSize) {
        this.data = data;
        this.totalSize = totalSize;
    }

    public List<Therapist> getData() {
        return data;
    }

    public void setData(List<Therapist> data) {
        this.data = data;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }
}
