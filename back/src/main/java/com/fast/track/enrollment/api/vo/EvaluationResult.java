package com.irichment.enrollment.api.vo;

import java.util.List;

public class EvaluationResult {

    private List<Evaluation> data;
    private Integer page;
    private Integer totalSize;

    public EvaluationResult(List<Evaluation>data, Integer totalSize){
        this.data=data;
        this.totalSize=totalSize;
    }

    public List<Evaluation>getData(){ return data; }

    public void setData (List<Evaluation>data){this.data=data;}

    public Integer getPage(){return page;}

    public void setPage(Integer page){this.page=page;}

    public Integer getTotalSize(){ return totalSize;}

    public void  setTotalSize(Integer totalSize){this.totalSize=totalSize;}
}
