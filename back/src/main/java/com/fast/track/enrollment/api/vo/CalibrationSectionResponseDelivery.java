package com.irichment.enrollment.api.vo;

import com.irichment.core.api.MetaObject;

import java.util.List;

public class CalibrationSectionResponseDelivery extends MetaObject {

    private String code;
    private String description;
    private Integer sectionScore;
    private EvaluationSection evaluationSection;
    private List<CalibrationQuestionResponseDelivery> questionResponses;

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Integer getSectionScore() { return sectionScore; }

    public void setSectionScore(Integer sectionScore) { this.sectionScore = sectionScore; }

    public EvaluationSection getEvaluationSection() { return evaluationSection; }

    public void setEvaluationSection(EvaluationSection evaluationSection) { this.evaluationSection = evaluationSection; }

    public List<CalibrationQuestionResponseDelivery> getQuestionResponses() {
        return questionResponses;
    }

    public void setQuestionResponses(List<CalibrationQuestionResponseDelivery> questionResponses) {
        this.questionResponses = questionResponses;
    }
}
