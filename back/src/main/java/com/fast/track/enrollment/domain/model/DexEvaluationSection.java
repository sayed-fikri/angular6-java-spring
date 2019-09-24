package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetaObject;

import java.util.List;

public interface DexEvaluationSection extends DexMetaObject {

    String getCode();

    void setCode(String code);

    String getDescription();

    void setDescription(String description);

    DexEvaluationSchema getSchema();

    void setSchema(DexEvaluationSchema schema);

    List<DexEvaluationQuestion> getQuestions();

    void setQuestions(List<DexEvaluationQuestion> questions);

}
