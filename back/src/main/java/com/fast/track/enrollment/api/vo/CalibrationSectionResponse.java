package com.irichment.enrollment.api.vo;

import com.irichment.core.api.MetaObject;

import java.util.List;

public class CalibrationSectionResponse extends MetaObject {

    private String code;
    private String description;
    private Integer sectionScore;
    private Integer fullScore;
    private EvaluationSection evaluationSection;
    private List<CalibrationQuestionResponse> questionResponses;

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Integer getSectionScore() { return sectionScore; }

    public void setSectionScore(Integer sectionScore) { this.sectionScore = sectionScore; }

    public EvaluationSection getEvaluationSection() { return evaluationSection; }

    public Integer getFullScore() { return fullScore; }

    public void setFullScore(Integer fullScore) { this.fullScore = fullScore; }

    public void setEvaluationSection(EvaluationSection evaluationSection) { this.evaluationSection = evaluationSection; }

    public List<CalibrationQuestionResponse> getQuestionResponses() {
        return questionResponses;
    }

    public void setQuestionResponses(List<CalibrationQuestionResponse> questionResponses) {
        this.questionResponses = questionResponses;
    }
}
