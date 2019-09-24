package com.irichment.enrollment.api.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irichment.core.api.MetaObject;
import com.irichment.identity.api.vo.Guardian;

import java.io.IOException;
import java.util.List;

public class Evaluation extends MetaObject {

    private String code;
    private String description;
    private Integer totalScore;
    private Child child;
    private EvaluationSchema schema;
    private EvaluationStatus evaluationStatus;
    private List<EvaluationSectionResponse> sectionResponses;
    private Guardian guardian;

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Integer getTotalScore() { return totalScore; }

    public void setTotalScore(Integer totalScore) { this.totalScore = totalScore; }

    public Child getChild() { return child; }

    public void setChild(Child child) { this.child = child; }

    public EvaluationSchema getSchema() { return schema; }

    public void setSchema(EvaluationSchema schema) { this.schema = schema; }

    public EvaluationStatus getEvaluationStatus() { return evaluationStatus; }

    public void setEvaluationStatus(EvaluationStatus evaluationStatus) { this.evaluationStatus = evaluationStatus; }

    public List<EvaluationSectionResponse> getSectionResponses() { return sectionResponses; }

    public void setSectionResponses(List<EvaluationSectionResponse> sectionResponses) { this.sectionResponses = sectionResponses; }

    public Guardian getGuardian() { return guardian; }

    public void setGuardian(Guardian guardian) { this.guardian = guardian; }
}
