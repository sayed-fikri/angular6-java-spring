package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetaObject;

import java.util.List;

public interface DexCalibrationSectionResponse extends DexMetaObject {



    String getCode();

    void setCode(String code);

    String getDescription();

    void setDescription(String description);

    Integer getSectionScore();

    void setSectionScore(Integer sectionScore);

    Integer getFullScore();

    void setFullScore(Integer fullScore);

    DexEvaluationSection getSection();

    void setSection(DexEvaluationSection section);

    DexCalibration getCalibration();

    void setCalibration(DexCalibration calibration);

    List<DexCalibrationQuestionResponse> getQuestionResponses();

    void setQuestionResponses(List<DexCalibrationQuestionResponse> questionResponses);
}
