package com.irichment.enrollment.api.vo;

import java.util.List;

public class EvaluationQuestionResult {

    private List<EvaluationQuestion> data;
    private Integer page;
    private Integer totalSize;

    public EvaluationQuestionResult(List<EvaluationQuestion>data, Integer totalSize){
        this.data=data;
        this.totalSize=totalSize;
    }

    public List<EvaluationQuestion>getData(){ return data; }

    public void setData (List<EvaluationQuestion>data){this.data=data;}

    public Integer getPage(){return page;}

    public void setPage(Integer page){this.page=page;}

    public Integer getTotalSize(){ return totalSize;}

    public void  setTotalSize(Integer totalSize){this.totalSize=totalSize;}
}
