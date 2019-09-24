package com.irichment.enrollment.api.vo;

import com.irichment.core.api.MetaObject;

public class Schema extends MetaObject {

    private String code;
    private String name;
    private String evaluation;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

}
