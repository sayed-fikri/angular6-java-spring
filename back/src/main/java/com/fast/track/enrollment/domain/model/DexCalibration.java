package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetaObject;
import com.irichment.core.domain.DexMetadata;

import java.util.List;

public interface DexCalibration extends DexMetaObject {
    Long getId();

    String getCode();

    void setCode(String code);

    String getDescription();

    void setDescription(String description);

    Integer getTotalScore();

    void setTotalScore(Integer totalScore);

    DexEvaluationSchema getSchema();

    void setSchema(DexEvaluationSchema schema);

    DexChild getChild();

    void setChild(DexChild child);

    DexEvaluationStatus getEvaluationStatus();

    void setEvaluationStatus(DexEvaluationStatus evaluationStatus);

    List<DexCalibrationSectionResponse> getSectionResponses();

    void setSectionResponses(List<DexCalibrationSectionResponse> sectionResponses);

    DexMetadata getMetadata();

    void setMetadata(DexMetadata metadata);

    Class<?> getInterfaceClass();
}
