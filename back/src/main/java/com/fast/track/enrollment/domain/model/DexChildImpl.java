package com.irichment.enrollment.domain.model;

import com.irichment.core.domain.DexMetadata;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexGuardianImpl;
import com.irichment.identity.domain.model.DexTherapist;
import com.irichment.identity.domain.model.DexTherapistImpl;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "DexChild")
@Table(name = "DEX_CHLD")
public class DexChildImpl implements DexChild {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SQ_DEX_CHLD")
    @SequenceGenerator(name = "SQ_DEX_CHLD", sequenceName = "SQ_DEX_CHLD", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IDENTITY_NO")
    private String identityNo;

    @Column(name = "CONCERN_TYPE", columnDefinition = "text")
    private DexConcernType concernType;

    @Column(name = "REMARK", columnDefinition = "text")
    private String remark;

    @Column(name = "REAL_AGE")
    private Integer realAge;

    @Column(name = "COGNITIVE_AGE")
    private String cognitiveAge;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "GENDER", nullable = false)
    private DexGenderType gender;

    @Column(name = "CALIBRATED")
    private Boolean calibrated;

    @ManyToOne(targetEntity = DexGuardianImpl.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "GUARDIAN_ID")
    private DexGuardian guardian;

    @OneToOne(targetEntity = DexEvaluationImpl.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "EVALUATION_ID")
    private DexEvaluation evaluation;

    @OneToOne(targetEntity = DexCalibrationImpl.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "CALIBRATION_ID")
    private DexCalibration calibration;

    @OneToOne(targetEntity = DexEnrollmentImpl.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ENROLLMENT_ID")
    private DexEnrollment enrollment;

    @OneToMany(targetEntity = DexConsultationImpl.class, mappedBy = "child", fetch = FetchType.LAZY)
    private List<DexConsultation> consultations;

    @Embedded
    private DexMetadata metadata;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getRealAge() {
        return realAge;
    }

    @Override
    public void setRealAge(Integer realAge) {
        this.realAge = realAge;
    }

    @Override
    public String getCognitiveAge() { return cognitiveAge; }

    @Override
    public void setCognitiveAge(String cognitiveAge) { this.cognitiveAge = cognitiveAge; }

    @Override
    public String getIdentityNo() {
        return identityNo;
    }

    @Override
    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    @Override
    public DexConcernType getConcernType() {
        return concernType;
    }

    @Override
    public void setConcernType(DexConcernType concernType) {
        this.concernType = concernType;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public DexGenderType getGender() {
        return gender;
    }

    @Override
    public void setGender(DexGenderType gender) {
        this.gender = gender;
    }

    @Override
    public Boolean getCalibrated() { return calibrated; }

    @Override
    public void setCalibrated(Boolean calibrated) { this.calibrated = calibrated; }

    @Override
    public DexGuardian getGuardian() {
        return guardian;
    }

    @Override
    public void setGuardian(DexGuardian guardian) {
        this.guardian = guardian;
    }

    @Override
    public DexEvaluation getEvaluation() {
        return evaluation;
    }

    @Override
    public void setEvaluation(DexEvaluation evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public DexCalibration getCalibration() {
        return calibration;
    }

    @Override
    public void setCalibration(DexCalibration calibration) {
        this.calibration = calibration;
    }

    @Override
    public DexEnrollment getEnrollment() { return enrollment; }

    @Override
    public void setEnrollment(DexEnrollment enrollment) { this.enrollment = enrollment; }

    @Override
    public DexMetadata getMetadata() {
        return metadata;
    }

    @Override
    public void setMetadata(DexMetadata metadata) {
        this.metadata = metadata;
    }

    public List<DexConsultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<DexConsultation> consultations) {
        this.consultations = consultations;
    }

    @Override
    public Class<?> getInterfaceClass() {
        return DexChild.class;
    }


}




