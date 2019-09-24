package com.irichment.identity.api.vo;

import java.util.List;

/**
 */
public class GuardianResult {
    private List<Guardian> data;
    private Integer page;
    private Integer totalSize;

    public GuardianResult(List<Guardian> data, Integer totalSize) {
        this.data = data;
        this.totalSize = totalSize;
    }

    public List<Guardian> getData() {
        return data;
    }

    public void setData(List<Guardian> data) {
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
