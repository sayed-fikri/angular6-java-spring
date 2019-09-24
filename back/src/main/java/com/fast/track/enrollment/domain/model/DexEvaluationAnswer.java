package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetaObject;

public interface DexEvaluationAnswer extends DexMetaObject {

    String getCode();

    void setCode(String code);

    String getDescription();

    void setDescription(String description);

    DexAnswerType getQuestionAnswer();

    void setQuestionAnswer(DexAnswerType questionAnswer);

    Integer getAnswerScore();

    void setAnswerScore(Integer answerScore);


    DexEvaluationQuestion getQuestion();

    void setQuestion(DexEvaluationQuestion question);
}
