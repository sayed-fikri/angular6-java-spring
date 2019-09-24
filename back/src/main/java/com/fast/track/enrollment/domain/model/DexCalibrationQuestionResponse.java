package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetaObject;

public interface DexCalibrationQuestionResponse extends DexMetaObject {

    String getCode();

    void setCode(String code);

    String getDescription();

    void setDescription(String description);

    DexAnswerType getQuestionAnswer();

    void setQuestionAnswer(DexAnswerType questionAnswer);

    Integer getQuestionScore();

    void setQuestionScore(Integer questionScore);

    DexEvaluationQuestion getQuestion();

    void setQuestion(DexEvaluationQuestion question);

    DexCalibrationSectionResponse getSectionResponse();

    void setSectionResponse(DexCalibrationSectionResponse sectionResponse);
}
