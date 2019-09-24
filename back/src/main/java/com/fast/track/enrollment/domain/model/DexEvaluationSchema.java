package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetaObject;

import java.util.List;

public interface DexEvaluationSchema extends DexMetaObject {

    String getCode();

    void setCode(String code);

    String getDescription();

    void setDescription(String description);

    Integer getOrdinal();

    void setOrdinal(Integer ordinal);

    Integer getMinAge();

    void setMinAge(Integer minAge);

    Integer getMaxAge();

    void setMaxAge(Integer maxAge);

    Integer getExpectedScore();

    void setExpectedScore(Integer expectedScore);

    DexSchemaType getSchema();

    void setSchema(DexSchemaType schema);

    Integer getTotalQuestion();

    void setTotalQuestion(Integer totalQuestion);

    Integer getFullScore();

    void setFullScore(Integer fullScore);

    DexSchemaType getSchemaType();

    void setSchemaType(DexSchemaType schemaType);

    List<DexEvaluationSection> getSections();

    void setSections(List<DexEvaluationSection> sections);
}
