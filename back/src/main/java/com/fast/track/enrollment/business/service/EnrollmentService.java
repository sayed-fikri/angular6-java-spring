package com.irichment.enrollment.business.service;


import com.irichment.enrollment.api.vo.Billplz;
import com.irichment.enrollment.domain.model.*;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexTherapist;
import com.irichment.learning.domain.model.DexCourse;

import java.util.List;

/**
 * @author canang technologies
 */
public interface EnrollmentService {

    //==============================================================================================
    // CHILD
    //==============================================================================================

    DexChild findChildById(Long id);

    DexChild findChildByCode(String code);

    List<DexChild> findChild(String filter, Integer offset, Integer limit);

    List<DexChild> findChildByGuardian(DexGuardian guardian, Integer offset, Integer limit);

    List<DexChild> findChildByTherapist(DexTherapist therapist, Integer offset, Integer limit);

    Integer countChildByTherapist();

    Integer countChildByGuardian(DexGuardian guardian);

    Integer countChildByTherapist(DexTherapist therapist);

    void registerChild(DexChild child, DexGuardian guardian) throws Exception;

    void addTherapist(DexChild child, DexTherapist therapist);

    void updateChild(DexChild child, DexGuardian guardian);

    void removeChild(DexChild child, DexGuardian guardian);

    //==============================================================================================
    // EVALUATION SCHEMA
    //==============================================================================================

    DexEvaluationSchema findEvaluationSchemaById(Long id);

    DexEvaluationSection findEvaluationSectionById(Long id);

    DexEvaluationQuestion findEvaluationQuestionById(Long id);

    DexEvaluationSchema findEvaluationSchemaByCode(String code);

    DexEvaluationSection findEvaluationSectionByCode(String code);

    DexEvaluationQuestion findEvaluationQuestionByCode(String code);

    DexEvaluationSchema findEvaluationSchemaByAge(Integer age);

    DexEvaluationSchema findEvaluationSchemaByOrdinal(Integer ordinal);

    List<DexEvaluationSchema> findEvaluationSchemas(String filter, Integer offset, Integer limit);

    List<DexEvaluationSection> findEvaluationSections(DexEvaluationSchema schema, Integer offset, Integer limit);

    List<DexEvaluationQuestion> findEvaluationQuestions(DexEvaluationSection section, Integer offset, Integer limit);

    Integer countEvaluationSchema(String filter);

    Integer countEvaluationSection(DexEvaluationSchema schema);

    Integer countEvaluationQuestion(DexEvaluationSection section);

    void saveEvaluationSchema(DexEvaluationSchema schema);

    void saveEvaluationSection(DexEvaluationSchema schema, DexEvaluationSection section);

    void saveEvaluationQuestion(DexEvaluationSection section, DexEvaluationQuestion question);

    void updateEvaluationSchema(DexEvaluationSchema schema);

    void updateEvaluationSection(DexEvaluationSchema schema, DexEvaluationSection section);

    void updateEvaluationQuestion(DexEvaluationSection section, DexEvaluationQuestion question);

    void removeEvaluationSchema(DexEvaluationSchema schema);

    void removeEvaluationSection(DexEvaluationSchema schema, DexEvaluationSection section);

    void removeEvaluationQuestion(DexEvaluationSection section, DexEvaluationQuestion question);

    //==============================================================================================
    // EVALUATION
    //==============================================================================================

    DexEvaluation findEvaluationById(Long id);

    DexEvaluationSectionResponse findSectionResponseById(Long id);

    DexEvaluationQuestionResponse findQuestionResponseById(Long id);

    DexEvaluation findEvaluationByCode(String code);

    DexEvaluation findEvaluationByChild(DexChild child);

    List<DexEvaluation> findEvaluationsByChild(DexChild child);


    DexEvaluationStatus findEvaluationStatusBySchemaAndTotalScore(DexEvaluationSchema schema, Integer totalScore);

    DexEvaluationSectionResponse findSectionResponseByCode(String code);

    List<DexEvaluationSectionResponse> findSectionResponseByEvaluation(DexEvaluation evaluation);

    DexEvaluationQuestionResponse findQuestionResponseByCode(String code);

    DexEvaluationQuestionResponse findQuestionResponseBySectionResponse(DexEvaluationSectionResponse sectionResponse);

    List<DexEvaluation> findEvaluations(String filter, Integer offset, Integer limit);

    List<DexEvaluation> findEvaluationsByGuardian(DexGuardian guardian, Integer offset, Integer limit);

    List<DexEvaluationSectionResponse> findSectionResponses(DexEvaluation evaluation, Integer offset, Integer limit);

    List<DexEvaluationQuestionResponse> findQuestionResponses(DexEvaluationSectionResponse sectionResponse, Integer offset, Integer limit);

    Integer countEvaluation(String filter);

    Integer countEvaluationByGuardian(DexGuardian guardian);

    Integer countSectionResponse(DexEvaluation evaluation);

    Integer countQuestionResponse(DexEvaluationSectionResponse schemaResponse);

    void prepareEvaluation(DexChild child, DexEvaluationSchema schema);

    void updateEvaluation(DexEvaluation evaluation);

    void submitEvaluation(DexEvaluation evaluation);

    void checkEvaluation(DexEvaluation evaluation);

    void removeEvaluation(DexEvaluation evaluation, DexEvaluationSchema schema, DexChild child);

    void updateSectionResponse(DexEvaluation evaluation,
                               DexEvaluationSectionResponse sectionResponse);

    void updateQuestionResponse(DexEvaluationQuestionResponse questionResponse);

    void deleteSectionResponse(DexEvaluation evaluation,
                               DexEvaluationSectionResponse sectionResponse);

    void deleteQuestionResponse(DexEvaluationSectionResponse sectionResponse,
                                DexEvaluationQuestionResponse questionResponse);

    //==============================================================================================
    // CALIBRATION
    //==============================================================================================

    DexCalibration findCalibrationById(Long id);

    DexCalibrationSectionResponse findCalibrationSectionResponseById(Long id);

    DexCalibrationQuestionResponse findCalibrationQuestionResponseById(Long id);

    DexCalibration findCalibrationByCode(String code);

    DexCalibration findCalibrationByChild(DexChild child);

    DexCalibrationSectionResponse findCalibrationSectionResponseByCode(String code);

    List<DexCalibrationSectionResponse> findSectionResponseByCalibration(DexCalibration calibration);

    DexCalibrationQuestionResponse findCalibrationQuestionResponseByCode(String code);

    DexCalibrationQuestionResponse findQuestionResponseBySectionResponse(DexCalibrationSectionResponse sectionResponse);

    List<DexCalibration> findCalibrations(String filter, Integer offset, Integer limit);

    List<DexCalibrationSectionResponse> findSectionResponses(DexCalibration calibration, Integer offset, Integer limit);

    List<DexCalibrationQuestionResponse> findQuestionResponses(DexCalibrationSectionResponse sectionResponse, Integer offset, Integer limit);

    Integer countCalibration(String filter);

    Integer countSectionResponse(DexCalibration calibration);

    Integer countQuestionResponse(DexCalibrationSectionResponse schemaResponse);

    void updateCalibration(DexCalibration calibration);

    void calibrate(DexChild child, DexEvaluationSchema schema);

    void checkCalibration(DexCalibration calibration);

    void submitCalibration(DexCalibration calibration);

    void removeCalibration(DexCalibration calibration, DexEvaluationSchema schema, DexChild child);

    void updateSectionResponse(DexCalibration calibration,
                               DexCalibrationSectionResponse sectionResponse);

    void updateQuestionResponse(DexCalibrationQuestionResponse questionResponse);

    void deleteSectionResponse(DexCalibration calibration,
                               DexCalibrationSectionResponse sectionResponse);

    void deleteQuestionResponse(DexCalibrationSectionResponse sectionResponse,
                                DexCalibrationQuestionResponse questionResponse);

    //==============================================================================================
    // ENROLLMENT
    //==============================================================================================

    List<DexEnrollment> findAllEnrollments(Integer offset, Integer limit);

    List<DexEnrollment> findEnrollments(String filter, Integer offset, Integer limit);

    List<DexEnrollment> findEnrollmentByGuardian(DexGuardian guardian);

    List<DexEnrollment> findEnrollmentByGuardian(DexGuardian guardian, Integer offset, Integer limit);

    DexEnrollment findEnrollmentById(Long id);

    DexEnrollment findEnrollmentByCode(String code);

    DexEnrollment findEnrollmentByChild(DexChild child);

    Integer countEnrollment(String filter);

    Integer countEnrollmentByGuardian(DexGuardian guardian);

    void save(DexEnrollment enrollment);

    void enroll(DexEnrollment enrollment, DexChild child);

    void updateEnrollment(DexEnrollment enrollment);

    void removeEnrollment(DexEnrollment enrollment);

    //==============================================================================================
    // COUPON
    //==============================================================================================

    List<DexCoupon> findAllCoupons();

    List<DexCoupon> findCoupons(String filter, Integer offset, Integer limit);

    void payByCoupon(DexEnrollment enrollment, DexCoupon coupon);

    DexCoupon findCouponByCode(String code);

    DexCoupon findAvailableCouponByCode(String code);

    Integer countCoupon(String filter);

    void saveCoupon(DexCoupon coupon);

    void updateCoupon(DexCoupon coupon);

    void removeCoupon(DexCoupon coupon);

    void generateCoupon(Integer count);

    //==============================================================================================
    // CONSULTATION
    //==============================================================================================

    List<DexConsultation> findConsultations(String filter, Integer offset, Integer limit);

    List<DexConsultation> findConsultationByChild(DexChild child, Integer offset, Integer limit);

    List<DexConsultation> findConsultationByChild(DexChild child);

    List<DexConsultation> findConsultationByTherapist(DexTherapist therapist, Integer offset, Integer limit);

    List<DexConsultation> findConsultationByTherapist(DexTherapist therapist);

    List<DexComment> findComments(DexConsultation consultation, Integer offset, Integer limit);

    DexConsultation findConsultationByCode(String code);

    DexConsultation findConsultationByChildAndTherapist(DexChild child, DexTherapist therapist);

    DexComment findCommentByCode(String code);

    Integer countConsultation(String filter);

    Integer countConsultationByChild(DexChild child);

    Integer countConsultationByTherapist(DexTherapist therapist);

    Integer countComment(DexConsultation consultation);

    void saveConsultation(DexChild child, DexTherapist therapist);

    void updateConsultation(DexConsultation consultation);

    void removeConsultation(DexConsultation consultation);

    void saveComment(DexConsultation consultation, DexComment comment);

    void updateComment(DexComment comment);

    void removeComment(DexComment comment);

    //==============================================================================================
    // BILLPLZ
    //==============================================================================================

    Billplz generateBill(DexEnrollment enrollment, Billplz billplz);

}

