package com.irichment.enrollment.api.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irichment.core.api.MetaObject;

import java.io.IOException;

public class EvaluationQuestionResponse extends MetaObject {

    private String code;
    private String description;
    private AnswerType questionAnswer;
    private Integer questionScore;
    private EvaluationQuestion evaluationQuestion;


    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public AnswerType getQuestionAnswer() { return questionAnswer; }

    public void setQuestionAnswer(AnswerType questionAnswer) { this.questionAnswer = questionAnswer; }

    public Integer getQuestionScore() { return questionScore; }

    public void setQuestionScore(Integer questionScore) { this.questionScore = questionScore; }

    public EvaluationQuestion getEvaluationQuestion() { return evaluationQuestion; }

    public void setEvaluationQuestion(EvaluationQuestion evaluationQuestion) { this.evaluationQuestion = evaluationQuestion; }
}
