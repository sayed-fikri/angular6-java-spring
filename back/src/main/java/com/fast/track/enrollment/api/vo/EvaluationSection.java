package com.irichment.enrollment.api.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irichment.core.api.MetaObject;

import java.io.IOException;
import java.util.List;

/**
 * @author canang technologies
 */
public class EvaluationSection extends MetaObject {

    private String code;
    private String description;
    private List<EvaluationQuestion> questions;

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

    public List<EvaluationQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<EvaluationQuestion> questions) {
        this.questions = questions;
    }
}
