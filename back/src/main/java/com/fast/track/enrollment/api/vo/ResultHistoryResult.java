package com.irichment.enrollment.api.vo;

import java.util.List;

public class ResultHistoryResult {

    private List<ResultHistory> data;
    private Integer page;
    private Integer totalSize;

    public ResultHistoryResult(List<ResultHistory>data, Integer totalSize){
        this.data=data;
        this.totalSize=totalSize;
    }

    public List<ResultHistory>getData(){ return data; }

    public void setData (List<ResultHistory>data){this.data=data;}

    public Integer getPage(){return page;}

    public void setPage(Integer page){this.page=page;}

    public Integer getTotalSize(){ return totalSize;}

    public void  setTotalSize(Integer totalSize){this.totalSize=totalSize;}
}
