package com.irichment.enrollment.api.vo;

import com.irichment.core.api.MetaObject;

import java.util.List;

public class Calibration extends MetaObject {

    private String code;
    private String description;
    private Integer totalScore;
    private Child child;
    private EvaluationSchema schema;
    private EvaluationStatus evaluationStatus;
    private List<CalibrationSectionResponse> sectionResponses;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public EvaluationSchema getSchema() {
        return schema;
    }

    public void setSchema(EvaluationSchema schema) {
        this.schema = schema;
    }

    public EvaluationStatus getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(EvaluationStatus evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public List<CalibrationSectionResponse> getSectionResponses() {
        return sectionResponses;
    }

    public void setSectionResponses(List<CalibrationSectionResponse> sectionResponses) {
        this.sectionResponses = sectionResponses;
    }
}
