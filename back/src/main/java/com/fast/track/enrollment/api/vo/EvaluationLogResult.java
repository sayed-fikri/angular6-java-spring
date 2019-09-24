package com.irichment.enrollment.api.vo;

import java.util.List;

public class EvaluationLogResult {

    private List<EvaluationLog> data;
    private Integer page;
    private Integer totalSize;

    public EvaluationLogResult(List<EvaluationLog>data, Integer totalSize){
        this.data=data;
        this.totalSize=totalSize;
    }

    public List<EvaluationLog>getData(){ return data; }

    public void setData (List<EvaluationLog>data){this.data=data;}

    public Integer getPage(){return page;}

    public void setPage(Integer page){this.page=page;}

    public Integer getTotalSize(){ return totalSize;}

    public void  setTotalSize(Integer totalSize){this.totalSize=totalSize;}
}
