package com.irichment.enrollment.api.vo;

import java.util.List;

public class EvaluationQuestionResponseResult {

    private List<EvaluationQuestionResponse> data;
    private Integer page;
    private Integer totalSize;

    public EvaluationQuestionResponseResult(List<EvaluationQuestionResponse>data, Integer totalSize){
        this.data=data;
        this.totalSize=totalSize;
    }

    public List<EvaluationQuestionResponse>getData(){ return data; }

    public void setData (List<EvaluationQuestionResponse>data){this.data=data;}

    public Integer getPage(){return page;}

    public void setPage(Integer page){this.page=page;}

    public Integer getTotalSize(){ return totalSize;}

    public void  setTotalSize(Integer totalSize){this.totalSize=totalSize;}
}
