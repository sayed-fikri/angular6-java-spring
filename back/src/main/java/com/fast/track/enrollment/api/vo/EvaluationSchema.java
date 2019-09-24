package com.irichment.enrollment.api.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irichment.core.api.MetaObject;

import java.io.IOException;
import java.util.List;

public class EvaluationSchema extends MetaObject {

    private String code;
    private SchemaType schema;
    private String description;
    private Integer minAge;
    private Integer maxAge;
    private Integer expectedScore;
    private Integer fullScore;
    private Integer ordinal;
    private Integer totalQuestion;
    private List<EvaluationSection> sections;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SchemaType getSchema() {
        return schema;
    }

    public void setSchema(SchemaType schema) {
        this.schema = schema;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMinAge() { return minAge; }

    public void setMinAge(Integer minAge) { this.minAge = minAge; }

    public Integer getMaxAge() { return maxAge; }

    public void setMaxAge(Integer maxAge) { this.maxAge = maxAge; }

    public Integer getExpectedScore() { return expectedScore; }

    public void setExpectedScore(Integer expectedScore) { this.expectedScore = expectedScore; }

    public Integer getFullScore() { return fullScore; }

    public Integer getOrdinal() { return ordinal; }

    public void setOrdinal(Integer ordinal) { this.ordinal = ordinal; }

    public void setFullScore(Integer fullScore) { this.fullScore = fullScore; }

    public Integer getTotalQuestion() { return totalQuestion; }

    public void setTotalQuestion(Integer totalQuestion) { this.totalQuestion = totalQuestion; }

    public List<EvaluationSection> getSections() {
        return sections;
    }

    public void setSections(List<EvaluationSection> sections) {
        this.sections = sections;
    }
}
