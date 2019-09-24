package com.irichment.enrollment.api.vo;

import java.util.List;

public class CalibrationQuestionResponseResult {

    private List<CalibrationQuestionResponse> data;
    private Integer page;
    private Integer totalSize;

    public CalibrationQuestionResponseResult(List<CalibrationQuestionResponse>data, Integer totalSize){
        this.data=data;
        this.totalSize=totalSize;
    }

    public List<CalibrationQuestionResponse>getData(){ return data; }

    public void setData (List<CalibrationQuestionResponse>data){this.data=data;}

    public Integer getPage(){return page;}

    public void setPage(Integer page){this.page=page;}

    public Integer getTotalSize(){ return totalSize;}

    public void  setTotalSize(Integer totalSize){this.totalSize=totalSize;}
}
