package com.irichment.enrollment.api.controller;

import com.irichment.core.api.controller.CoreTransformer;
import com.irichment.enrollment.api.vo.*;
import com.irichment.enrollment.domain.model.*;
import com.irichment.identity.api.vo.ActorType;
import com.irichment.identity.api.vo.Guardian;
import com.irichment.identity.api.vo.Therapist;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexTherapist;
import com.irichment.learning.api.controller.LearningTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Component("enrollmentTransformer")
public class EnrollmentTransformer {

    private CoreTransformer coreTransformer;
    private LearningTransformer learningTransformer;

    @Autowired
    public EnrollmentTransformer(CoreTransformer coreTransformer, LearningTransformer learningTransformer) {
        this.coreTransformer = coreTransformer;
        this.learningTransformer = learningTransformer;
    }

    // =============================================================================================
    // EVALUATION SECTION
    // =============================================================================================

    public EvaluationSection toEvaluationSectionVo(DexEvaluationSection e) {
        if (null == e) return null;
        EvaluationSection vo = new EvaluationSection();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setDescription(e.getDescription());
        vo.setQuestions(toEvaluationQuestionVos(e.getQuestions()));
        coreTransformer.toMetadata(e, vo);
        return vo;
    }

    public List<EvaluationSection> toEvaluationSectionVos(List<DexEvaluationSection> e) {
        List<EvaluationSection> vos = e.stream().map((e1) -> toEvaluationSectionVo(e1)).collect(Collectors.toList());
        return vos;
    }

    // =============================================================================================
    // EVALUATION SCHEMA
    // =============================================================================================

    public EvaluationSchema toEvaluationSchemaVo(DexEvaluationSchema e) {
        if (null == e) return null;
        EvaluationSchema vo = new EvaluationSchema();
        vo.setId(e.getId());
        vo.setDescription(e.getDescription());
        vo.setCode(e.getCode());
        vo.setMinAge(e.getMinAge());
        vo.setMaxAge(e.getMaxAge());
        vo.setExpectedScore(e.getExpectedScore());
        vo.setFullScore(e.getFullScore());
        vo.setOrdinal(e.getOrdinal());
        vo.setTotalQuestion(e.getTotalQuestion());
        vo.setSchema(SchemaType.get(e.getSchemaType().ordinal()));
        vo.setSections(toEvaluationSectionVos(e.getSections()));
        coreTransformer.toMetadata(e, vo);
        return vo;
    }

    public List<EvaluationSchema> toEvaluationSchemaVos(List<DexEvaluationSchema> e) {
        List<EvaluationSchema> vos = e.stream().map((e1) -> toEvaluationSchemaVo(e1)).collect(Collectors.toList());
        return vos;


    }

    // =============================================================================================
    // EVALUATION QUESTION
    // =============================================================================================

    public EvaluationQuestion toEvaluationQuestionVo(DexEvaluationQuestion e) {
        if (null == e) return null;
        EvaluationQuestion vo = new EvaluationQuestion();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setDescription(e.getDescription());
        vo.setStatement(e.getStatement());
        coreTransformer.toMetadata(e, vo);
        return vo;
    }

    public List<EvaluationQuestion> toEvaluationQuestionVos(List<DexEvaluationQuestion> e) {
        List<EvaluationQuestion> vos = e.stream().map((e1) -> toEvaluationQuestionVo(e1)).collect(Collectors.toList());
        return vos;
    }

    // =============================================================================================
    // EVALUATION
    // =============================================================================================

    public Evaluation toEvaluationVo(DexEvaluation e) {
        if (null == e) return null;
        Evaluation vo = new Evaluation();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setDescription(e.getDescription());
        vo.setTotalScore(e.getTotalScore());
        vo.setChild(toSimpleChildVo(e.getChild()));
        vo.setGuardian(toGuardianVo(e.getGuardian()));
        vo.setSchema(toEvaluationSchemaVo(e.getSchema()));
        vo.setEvaluationStatus(toEvaluationStatusVo(e.getEvaluationStatus()));
        vo.setSectionResponses(toEvaluationSectionResponseVos(e.getSectionResponses()));
        coreTransformer.toMetadata(e, vo);
        return vo;
    }

    public List<Evaluation> toEvaluationVos(List<DexEvaluation> e) {
        List<Evaluation> vos = e.stream().map((e1) -> toEvaluationVo(e1)).collect(Collectors.toList());
        return vos;
    }

    // =============================================================================================
    // CALIBRATION
    // =============================================================================================

    public Calibration toCalibrationVo(DexCalibration e) {
        if (null == e) return null;
        Calibration vo = new Calibration();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setDescription(e.getDescription());
        vo.setTotalScore(e.getTotalScore());
        vo.setChild(toSimpleChildVo(e.getChild()));
        vo.setSchema(toEvaluationSchemaVo(e.getSchema()));
        vo.setEvaluationStatus(toEvaluationStatusVo(e.getEvaluationStatus()));
        vo.setSectionResponses(toCalibrationSectionResponseVos(e.getSectionResponses()));
        coreTransformer.toMetadata(e, vo);
        return vo;
    }

    public List<Calibration> toCalibrationVos(List<DexCalibration> e) {
        List<Calibration> vos = e.stream().map((e1) -> toCalibrationVo(e1)).collect(Collectors.toList());
        return vos;
    }

    // =============================================================================================
    // EVALUATION STATUS
    // =============================================================================================

    public EvaluationStatus toEvaluationStatusVo(DexEvaluationStatus e) {
        if (null == e) return null;
        EvaluationStatus vo = new EvaluationStatus();
        vo.setId(e.getId());
        vo.setMin(e.getMin());
        vo.setMax(e.getMax());
        vo.setStatus(e.getStatus());
        vo.setStatusType(StatusType.get(e.getStatusType().ordinal()));
        coreTransformer.toMetadata(e, vo);
        return vo;
    }

    public List<EvaluationStatus> toEvaluationStatusVos(List<DexEvaluationStatus> e) {
        List<EvaluationStatus> vos = e.stream().map((e1) -> toEvaluationStatusVo(e1)).collect(Collectors.toList());
        return vos;
    }

    // =============================================================================================
    // EVALUATION SECTION RESPONSE
    // =============================================================================================

    public EvaluationSectionResponse toEvaluationSectionResponseVo(DexEvaluationSectionResponse e) {
        if (null == e) return null;
        EvaluationSectionResponse vo = new EvaluationSectionResponse();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setDescription(e.getDescription());
        vo.setSectionScore(e.getSectionScore());
        vo.setFullScore(e.getFullScore());
        vo.setEvaluationSection(toEvaluationSectionVo(e.getSection()));
        vo.setQuestionResponses(toEvaluationQuestionResponseVos(e.getQuestionResponses()));
        coreTransformer.toMetadata(e, vo);
        return vo;
    }

    public List<EvaluationSectionResponse> toEvaluationSectionResponseVos(List<DexEvaluationSectionResponse> e) {
        List<EvaluationSectionResponse> vos = e.stream().map((e1) -> toEvaluationSectionResponseVo(e1)).collect(Collectors.toList());
        return vos;
    }

    // =============================================================================================
    // CALIBRATION SECTION RESPONSE
    // =============================================================================================

    public CalibrationSectionResponse toCalibrationSectionResponseVo(DexCalibrationSectionResponse e) {
        if (null == e) return null;
        CalibrationSectionResponse vo = new CalibrationSectionResponse();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setDescription(e.getDescription());
        vo.setSectionScore(e.getSectionScore());
        vo.setFullScore(e.getFullScore());
        vo.setEvaluationSection(toEvaluationSectionVo(e.getSection()));
        vo.setQuestionResponses(toCalibrationQuestionResponseVos(e.getQuestionResponses()));
        coreTransformer.toMetadata(e, vo);
        return vo;
    }

    public List<CalibrationSectionResponse> toCalibrationSectionResponseVos(List<DexCalibrationSectionResponse> e) {
        List<CalibrationSectionResponse> vos = e.stream().map((e1) -> toCalibrationSectionResponseVo(e1)).collect(Collectors.toList());
        return vos;
    }

    // =============================================================================================
    // EVALUATION QUESTION RESPONSE
    // =============================================================================================

    public EvaluationQuestionResponse toEvaluationQuestionResponseVo(DexEvaluationQuestionResponse e) {
        if (null == e) return null;
        EvaluationQuestionResponse vo = new EvaluationQuestionResponse();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setDescription(e.getDescription());
        vo.setEvaluationQuestion(toEvaluationQuestionVo(e.getQuestion()));
        vo.setQuestionAnswer(AnswerType.get(e.getQuestionAnswer().ordinal()));
        vo.setQuestionScore(e.getQuestionScore());
        coreTransformer.toMetadata(e, vo);
        return vo;
    }

    public List<EvaluationQuestionResponse> toEvaluationQuestionResponseVos(List<DexEvaluationQuestionResponse> e) {
        List<EvaluationQuestionResponse> vos = e.stream().map((e1) -> toEvaluationQuestionResponseVo(e1)).collect(Collectors.toList());
        return vos;
    }

    // =============================================================================================
    // CALIBRATION QUESTION RESPONSE
    // =============================================================================================

    public CalibrationQuestionResponse toCalibrationQuestionResponseVo(DexCalibrationQuestionResponse e) {
        if (null == e) return null;
        CalibrationQuestionResponse vo = new CalibrationQuestionResponse();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setDescription(e.getDescription());
        vo.setEvaluationQuestion(toEvaluationQuestionVo(e.getQuestion()));
        vo.setQuestionAnswer(AnswerType.get(e.getQuestionAnswer().ordinal()));
        vo.setQuestionScore(e.getQuestionScore());
        coreTransformer.toMetadata(e, vo);
        return vo;
    }

    public List<CalibrationQuestionResponse> toCalibrationQuestionResponseVos(List<DexCalibrationQuestionResponse> e) {
        List<CalibrationQuestionResponse> vos = e.stream().map((e1) -> toCalibrationQuestionResponseVo(e1)).collect(Collectors.toList());
        return vos;
    }

    // =============================================================================================
    // CHILD
    // =============================================================================================

    public Child toChildVo(DexChild e) {
        if (null == e) return null;
        Child vo = new Child();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setName(e.getName());
        vo.setIdentityNo(e.getIdentityNo());
        vo.setGender(GenderType.get(e.getGender().ordinal()));
        vo.setCalibrated(e.getCalibrated());
        vo.setRealAge(e.getRealAge());
        vo.setCognitiveAge(e.getCognitiveAge());
        vo.setGuardian(toGuardianVo(e.getGuardian()));
        vo.setConcernType(ConcernType.get(e.getConcernType().ordinal()));
        vo.setRemark(e.getRemark());
        vo.setEvaluation(toEvaluationVo(e.getEvaluation()));
        vo.setCalibration(toCalibrationVo(e.getCalibration()));
        vo.setEnrollment(toSimpleEnrollmentVo(e.getEnrollment()));

        coreTransformer.toMetadata(e, vo);
        return vo;
    }

    public Child toSimpleChildVo(DexChild e) {
        if (null == e) return null;
        Child vo = new Child();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setName(e.getName());
        vo.setCalibrated(e.getCalibrated());
        vo.setEnrollment(toSimpleEnrollmentVo(e.getEnrollment()));
        return vo;
    }

    public Guardian toGuardianVo(DexGuardian e) {
        if (null == e) return null;
        Guardian vo = new Guardian();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setActorType(ActorType.get(e.getActorType().ordinal()));
        vo.setIdentityNo(e.getIdentityNo());
        vo.setName(e.getName());
        vo.setEmail(e.getEmail());
        vo.setMobile(e.getMobile());
        vo.setIdentityNo(e.getIdentityNo());
        vo.setPhone(e.getPhone());
        vo.setAddress1(e.getAddress1());
        vo.setAddress2(e.getAddress2());
        vo.setAddress3(e.getAddress3());
        vo.setPostcode(e.getPostcode());
        vo.setFax(e.getFax());
        vo.setPostcode(e.getPostcode());
        return vo;
    }

    public List<Child> toChildVos(List<DexChild> e) {
        List<Child> vos = e.stream().map((e1) -> toChildVo(e1)).collect(Collectors.toList());
        return vos;
    }

    // =============================================================================================
    // ENROLLMENT
    // =============================================================================================

    public Enrollment toEnrollmentVo(DexEnrollment e) {
        if (null == e) return null;
        Enrollment vo = new Enrollment();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setDescription(e.getDescription());
        vo.setPaid(e.getPaid());
        vo.setBillNo(e.getBillNo());
        vo.setGuardian(toGuardianVo(e.getGuardian()));
        vo.setCoupon(toSimpleCouponVo(e.getCoupon()));
        vo.setChild(toChildVo(e.getChild()));
        if (e.getCourse1() != null) {
            vo.setCourse1(learningTransformer.toSimpleCourseVo(e.getCourse1()));
        }
        if (e.getCourse2() != null) {
            vo.setCourse2(learningTransformer.toSimpleCourseVo(e.getCourse2()));
        }
        if (e.getCourse3() != null) {
            vo.setCourse3(learningTransformer.toSimpleCourseVo(e.getCourse3()));
        }
        coreTransformer.toMetadata(e, vo);
        return vo;
    }

    public Enrollment toSimpleEnrollmentVo(DexEnrollment e) {
        if (null == e) return null;
        Enrollment vo = new Enrollment();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        return vo;
    }

    public List<Enrollment> toEnrollmentVos(List<DexEnrollment> e) {
        List<Enrollment> vos = e.stream().map((e1) -> toEnrollmentVo(e1)).collect(Collectors.toList());
        return vos;
    }

    // =============================================================================================
    // COUPON
    // =============================================================================================

    public Coupon toCouponVo(DexCoupon e) {
        if (null == e) return null;
        Coupon vo = new Coupon();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setDescription(e.getDescription());
        vo.setRedeemed(e.getRedeemed());
        vo.setEnrollment(toEnrollmentVo(e.getEnrollment()));
        coreTransformer.toMetadata(e, vo);
        return vo;
    }

    public Coupon toSimpleCouponVo(DexCoupon e) {
        if (null == e) return null;
        Coupon vo = new Coupon();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        return vo;
    }

    public List<Coupon> toCouponVos(List<DexCoupon> e) {
        List<Coupon> vos = e.stream().map((e1) -> toCouponVo(e1)).collect(Collectors.toList());
        return vos;
    }

    // =============================================================================================
    // CONSULTATION
    // =============================================================================================

    public Consultation toConsultationVo(DexConsultation e) {
        if (null == e) return null;
        Consultation vo = new Consultation();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setChild(toChildVo(e.getChild()));
        vo.setTherapist(toTherapistVo(e.getTherapist()));
        coreTransformer.toMetadata(e, vo);
        return vo;
    }

    public List<Consultation> toConsultationVos(List<DexConsultation> e) {
        List<Consultation> vos = e.stream().map((e1) -> toConsultationVo(e1)).collect(Collectors.toList());
        return vos;
    }

    public Therapist toTherapistVo(DexTherapist e) {
        if (null == e) return null;
        Therapist vo = new Therapist();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setActorType(ActorType.get(e.getActorType().ordinal()));
        vo.setIdentityNo(e.getIdentityNo());
        vo.setName(e.getName());
        vo.setEmail(e.getEmail());
        vo.setMobile(e.getMobile());
        vo.setIdentityNo(e.getIdentityNo());
        vo.setPhone(e.getPhone());
        vo.setAddress1(e.getAddress1());
        vo.setAddress2(e.getAddress2());
        vo.setAddress3(e.getAddress3());
        vo.setPostcode(e.getPostcode());
        vo.setFax(e.getFax());
        vo.setPostcode(e.getPostcode());
        return vo;
    }

    // =============================================================================================
    // COMMENT
    // =============================================================================================

    public Comment toCommentVo(DexComment e) {
        if (null == e) return null;
        Comment vo = new Comment();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setComment(e.getComment());

        if(e.getConsultation() != null){
        vo.setConsultation(toConsultationVo(e.getConsultation()));
        }
        coreTransformer.toMetadata(e, vo);
        return vo;
    }

    public List<Comment> toCommentVos(List<DexComment> e) {
        List<Comment> vos = e.stream().map((e1) -> toCommentVo(e1)).collect(Collectors.toList());
        return vos;
    }
}

