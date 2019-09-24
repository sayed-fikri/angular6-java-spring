package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetaObject;
import com.irichment.identity.domain.model.DexGuardian;

import java.util.List;

public interface DexEvaluation extends DexMetaObject {

    abstract String getCode();

    void setCode(String code);

    String getDescription();

    void setDescription(String description);

    Integer getTotalScore();

    void setTotalScore(Integer totalScore);

    abstract DexEvaluationSchema getSchema();

    void setSchema(DexEvaluationSchema schema);

    DexChild getChild();

    void setChild(DexChild child);


    DexEvaluationStatus getEvaluationStatus();

    void setEvaluationStatus(DexEvaluationStatus evaluationStatus);

    List<DexEvaluationSectionResponse> getSectionResponses();

    void setSectionResponses(List<DexEvaluationSectionResponse> sectionResponses);

    DexGuardian getGuardian();

    void setGuardian(DexGuardian guardian);
}
