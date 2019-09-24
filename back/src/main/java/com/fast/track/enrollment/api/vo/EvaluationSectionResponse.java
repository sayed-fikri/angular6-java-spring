package com.irichment.enrollment.api.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irichment.core.api.MetaObject;

import java.io.IOException;
import java.util.List;

public class EvaluationSectionResponse extends MetaObject {

    private String code;
    private String description;
    private Integer sectionScore;
    private Integer fullScore;
    private EvaluationSection evaluationSection;
    private List<EvaluationQuestionResponse> questionResponses;

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Integer getSectionScore() { return sectionScore; }

    public void setSectionScore(Integer sectionScore) { this.sectionScore = sectionScore; }

    public Integer getFullScore() { return fullScore; }

    public void setFullScore(Integer fullScore) { this.fullScore = fullScore; }

    public EvaluationSection getEvaluationSection() { return evaluationSection; }

    public void setEvaluationSection(EvaluationSection evaluationSection) { this.evaluationSection = evaluationSection; }

    public List<EvaluationQuestionResponse> getQuestionResponses() {
        return questionResponses;
    }

    public void setQuestionResponses(List<EvaluationQuestionResponse> questionResponses) {
        this.questionResponses = questionResponses;
    }
}
