package com.irichment.enrollment.api.vo;

import java.util.List;

public class CalibrationSectionResponseResult {

    private List<CalibrationSectionResponse> data;
    private Integer page;
    private Integer totalSize;

    public CalibrationSectionResponseResult(List<CalibrationSectionResponse>data, Integer totalSize){
        this.data=data;
        this.totalSize=totalSize;
    }

    public List<CalibrationSectionResponse>getData(){ return data; }

    public void setData (List<CalibrationSectionResponse>data){this.data=data;}

    public Integer getPage(){return page;}

    public void setPage(Integer page){this.page=page;}

    public Integer getTotalSize(){ return totalSize;}

    public void  setTotalSize(Integer totalSize){this.totalSize=totalSize;}
}
