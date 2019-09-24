package com.irichment.enrollment.api.vo;

import java.util.List;

public class EvaluationSectionResponseResult {

    private List<EvaluationSectionResponse> data;
    private Integer page;
    private Integer totalSize;

    public EvaluationSectionResponseResult(List<EvaluationSectionResponse>data, Integer totalSize){
        this.data=data;
        this.totalSize=totalSize;
    }

    public List<EvaluationSectionResponse>getData(){ return data; }

    public void setData (List<EvaluationSectionResponse>data){this.data=data;}

    public Integer getPage(){return page;}

    public void setPage(Integer page){this.page=page;}

    public Integer getTotalSize(){ return totalSize;}

    public void  setTotalSize(Integer totalSize){this.totalSize=totalSize;}
}
