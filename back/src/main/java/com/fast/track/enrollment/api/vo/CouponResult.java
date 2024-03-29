package com.irichment.enrollment.api.vo;

import java.util.List;

public class CouponResult {

    private List<Coupon> data;
    private Integer page;
    private Integer totalSize;

    public CouponResult(List<Coupon>data, Integer totalSize){
        this.data=data;
        this.totalSize=totalSize;
    }

    public List<Coupon>getData(){ return data; }

    public void setData (List<Coupon>data){this.data=data;}

    public Integer getPage(){return page;}

    public void setPage(Integer page){this.page=page;}

    public Integer getTotalSize(){ return totalSize;}

    public void  setTotalSize(Integer totalSize){this.totalSize=totalSize;}
}
