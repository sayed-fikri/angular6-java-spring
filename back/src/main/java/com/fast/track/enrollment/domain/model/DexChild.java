package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetaObject;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexTherapist;

import java.util.List;

public interface DexChild extends DexMetaObject {

    String getCode();

    void setCode(String code);

    String getName();

    void setName(String name);

    Integer getRealAge();

    void setRealAge(Integer realAge);

    String getCognitiveAge();

    void setCognitiveAge(String cognitiveAge);

    String getIdentityNo();

    void setIdentityNo(String identityNo);

    String getRemark();

    void setRemark(String remark);

    DexConcernType getConcernType();

    void setConcernType(DexConcernType concernType);

    DexGenderType getGender();

    void setGender(DexGenderType gender);

    Boolean getCalibrated();

    void setCalibrated(Boolean calibrated);

    DexGuardian getGuardian();

    void setGuardian(DexGuardian guardian);

    DexEvaluation getEvaluation();

    void setEvaluation(DexEvaluation evaluation);

    DexCalibration getCalibration();

    void setCalibration(DexCalibration calibration);

    DexEnrollment getEnrollment();

    void setEnrollment(DexEnrollment enrollment);

}
