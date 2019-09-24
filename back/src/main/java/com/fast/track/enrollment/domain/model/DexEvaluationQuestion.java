package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetaObject;

public interface DexEvaluationQuestion extends DexMetaObject {

    String getCode();

    void setCode(String code);

    String getDescription();

    void setDescription(String description);

    String getStatement();

    void setStatement(String statement);

    DexEvaluationSection getSection();

    void setSection(DexEvaluationSection section);
}
