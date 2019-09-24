package com.irichment.enrollment.api.vo;


import java.util.List;

/**
 */
public class EvaluationSectionResult {
    private List<EvaluationSection> data;
    private Integer page;
    private Integer totalSize;

    public EvaluationSectionResult(List<EvaluationSection> data, Integer totalSize) {
        this.data = data;
        this.totalSize = totalSize;
    }

    public List<EvaluationSection> getData() {
        return data;
    }

    public void setData(List<EvaluationSection> data) {
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
