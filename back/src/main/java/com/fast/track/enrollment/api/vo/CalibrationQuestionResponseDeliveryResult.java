package com.irichment.enrollment.api.vo;

import java.util.List;

public class CalibrationQuestionResponseDeliveryResult {

    private List<CalibrationQuestionResponseDelivery> data;
    private Integer page;
    private Integer totalSize;

    public CalibrationQuestionResponseDeliveryResult(List<CalibrationQuestionResponseDelivery>data, Integer totalSize){
        this.data=data;
        this.totalSize=totalSize;
    }

    public List<CalibrationQuestionResponseDelivery>getData(){ return data; }

    public void setData (List<CalibrationQuestionResponseDelivery>data){this.data=data;}

    public Integer getPage(){return page;}

    public void setPage(Integer page){this.page=page;}

    public Integer getTotalSize(){ return totalSize;}

    public void  setTotalSize(Integer totalSize){this.totalSize=totalSize;}
}
