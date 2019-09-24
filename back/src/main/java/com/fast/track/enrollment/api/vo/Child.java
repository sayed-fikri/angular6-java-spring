package com.irichment.enrollment.api.vo;

import com.irichment.core.api.MetaObject;
import com.irichment.identity.api.vo.Guardian;
import com.irichment.identity.api.vo.Therapist;

import java.util.List;

public class Child extends MetaObject {

    private String code;
    private String name;
    private Boolean calibrated;
    private String identityNo;
    private GenderType gender;
    private ConcernType concernType;
    private String remark;
    private Integer realAge ;
    private String cognitiveAge ;

    private Guardian guardian;
    private Evaluation evaluation;
    private Calibration calibration;
    private Enrollment enrollment;
    private List<Consultation> consultations;

    public String getCode(){ return code; }

    public void setCode(String code){
        this.code = code;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public GenderType getGender() {
        return gender;
    }

    public Boolean getCalibrated() { return calibrated; }

    public void setCalibrated(Boolean calibrated) { this.calibrated = calibrated; }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getIdentityNo() { return identityNo; }

    public void setIdentityNo(String identityNo) { this.identityNo = identityNo; }

    public ConcernType getConcernType() {
        return concernType;
    }

    public void setConcernType(ConcernType concernType) {
        this.concernType = concernType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRealAge() { return realAge; }

    public void setRealAge(Integer realAge) { this.realAge = realAge; }

    public String getCognitiveAge() { return cognitiveAge; }

    public void setCognitiveAge(String cognitiveAge) { this.cognitiveAge = cognitiveAge; }

    public Guardian getGuardian() { return guardian; }

    public void setGuardian(Guardian guardian) { this.guardian = guardian; }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public Calibration getCalibration() { return calibration;}

    public void setCalibration(Calibration calibration) { this.calibration = calibration; }

    public Enrollment getEnrollment() { return enrollment; }

    public void setEnrollment(Enrollment enrollment) { this.enrollment = enrollment; }

    public List<Consultation> getConsultations() { return consultations; }

    public void setConsultations(List<Consultation> consultations) { this.consultations = consultations; }
}
