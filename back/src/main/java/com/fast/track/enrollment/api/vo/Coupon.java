package com.irichment.enrollment.api.vo;

import com.irichment.core.api.MetaObject;

public class Coupon extends MetaObject {

    private String code;
    private Boolean redeemed;
    private String description;
    private Enrollment enrollment;

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public Boolean getRedeemed() { return redeemed; }

    public void setRedeemed(Boolean redeemed) { this.redeemed = redeemed; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Enrollment getEnrollment() { return enrollment; }

    public void setEnrollment(Enrollment enrollment) { this.enrollment = enrollment; }
}
