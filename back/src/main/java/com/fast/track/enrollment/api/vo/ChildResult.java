package com.irichment.enrollment.api.vo;

import java.util.List;

public class ChildResult {

    private List<Child> data;
    private Integer page;
    private Integer totalSize;

    public ChildResult(List<Child>data, Integer totalSize){
        this.data=data;
        this.totalSize=totalSize;
    }

    public List<Child>getData(){ return data; }

    public void setData (List<Child>data){this.data=data;}

    public Integer getPage(){return page;}

    public void setPage(Integer page){this.page=page;}

    public Integer getTotalSize(){ return totalSize;}

    public void  setTotalSize(Integer totalSize){this.totalSize=totalSize;}
}
