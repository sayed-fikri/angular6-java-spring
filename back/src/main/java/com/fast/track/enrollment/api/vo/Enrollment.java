package com.irichment.enrollment.api.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irichment.core.api.MetaObject;
import com.irichment.identity.api.vo.Guardian;
import com.irichment.learning.api.vo.Course;

import java.io.IOException;
import java.util.List;

public class Enrollment extends MetaObject {

    private String code;
    private Boolean paid;
    private String billNo;
    private String description;
    private Guardian guardian;
    private Child child;
    private Coupon coupon;
    private Course course1;
    private Course course2;
    private Course course3;


    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public Boolean getPaid() { return paid; }

    public void setPaid(Boolean paid) { this.paid = paid; }

    public String getBillNo() { return billNo; }

    public void setBillNo(String billNo) { this.billNo = billNo; }

    public String getDescription() { return description;}

    public void setDescription(String description) {
        this.description = description;
    }

    public Guardian getGuardian() { return guardian; }

    public void setGuardian(Guardian guardian) { this.guardian = guardian; }

    public Child getChild() { return child; }

    public void setChild(Child child) { this.child = child; }

    public Coupon getCoupon() { return coupon; }

    public void setCoupon(Coupon coupon) { this.coupon = coupon; }

    public Course getCourse1() { return course1; }

    public void setCourse1(Course course1) { this.course1 = course1; }

    public Course getCourse2() { return course2; }

    public void setCourse2(Course course2) { this.course2 = course2; }

    public Course getCourse3() { return course3; }

    public void setCourse3(Course course3) { this.course3 = course3; }


}
