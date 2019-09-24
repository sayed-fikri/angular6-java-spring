package com.irichment.enrollment.api.vo;

import java.util.List;

public class EvaluationStatusResult {

    private List<EvaluationStatus> data;
    private Integer page;
    private Integer totalSize;

    public EvaluationStatusResult(List<EvaluationStatus>data, Integer totalSize){
        this.data=data;
        this.totalSize=totalSize;
    }

    public List<EvaluationStatus>getData(){ return data; }

    public void setData (List<EvaluationStatus>data){this.data=data;}

    public Integer getPage(){return page;}

    public void setPage(Integer page){this.page=page;}

    public Integer getTotalSize(){ return totalSize;}

    public void  setTotalSize(Integer totalSize){this.totalSize=totalSize;}
}
