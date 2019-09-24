package com.irichment.enrollment.api.vo;

import java.util.List;

public class CalibrationSectionResponseDeliveryResult {

    private List<CalibrationSectionResponseDelivery> data;
    private Integer page;
    private Integer totalSize;

    public CalibrationSectionResponseDeliveryResult(List<CalibrationSectionResponseDelivery>data, Integer totalSize){
        this.data=data;
        this.totalSize=totalSize;
    }

    public List<CalibrationSectionResponseDelivery>getData(){ return data; }

    public void setData (List<CalibrationSectionResponseDelivery>data){this.data=data;}

    public Integer getPage(){return page;}

    public void setPage(Integer page){this.page=page;}

    public Integer getTotalSize(){ return totalSize;}

    public void  setTotalSize(Integer totalSize){this.totalSize=totalSize;}
}
