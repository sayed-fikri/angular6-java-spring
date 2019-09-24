package com.irichment.enrollment.business.service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.irichment.DexConstants;
import com.irichment.enrollment.api.vo.Billplz;
import com.irichment.enrollment.business.event.EvaluationCompleteEvent;
import com.irichment.enrollment.domain.dao.*;
import com.irichment.enrollment.domain.model.*;
import com.irichment.identity.business.service.IdentityService;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexTherapist;
import com.irichment.security.business.service.SecurityService;
import com.irichment.system.business.service.SystemService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;


/**
 * @author canang technologies
 */
@Transactional
@Service("dexEnrollmentService")
public class EnrollmentServiceImpl implements EnrollmentService {

    private static final Logger LOG = LoggerFactory.getLogger(EnrollmentServiceImpl.class);

    private EntityManager entityManager;
    private SecurityService securityService;
    private DexEvaluationSchemaDao evaluationSchemaDao;
    private DexChildDao childDao;
    private DexEnrollmentDao enrollmentDao;
    private DexCouponDao couponDao;
    private DexEvaluationDao evaluationDao;
    private DexCalibrationDao calibrationDao;
    private DexConsultationDao consultationDao;
    private IdentityService identityService;
    private SystemService systemService;
    private ApplicationContext applicationContext;
    public static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Autowired
    public EnrollmentServiceImpl(EntityManager entityManager,
                                 SecurityService securityService,
                                 IdentityService identityService,
                                 SystemService systemService,
                                 DexEvaluationSchemaDao evaluationSchemaDao,
                                 DexEvaluationDao evaluationDao,
                                 DexChildDao childDao,
                                 DexEnrollmentDao enrollmentDao,
                                 DexCouponDao couponDao,
                                 DexCalibrationDao calibrationDao,
                                 DexConsultationDao consultationDao,
                                 ApplicationContext applicationContext) {
        this.entityManager = entityManager;
        this.securityService = securityService;
        this.evaluationSchemaDao = evaluationSchemaDao;
        this.evaluationDao = evaluationDao;
        this.childDao = childDao;
        this.enrollmentDao = enrollmentDao;
        this.couponDao = couponDao;
        this.identityService = identityService;
        this.systemService = systemService;
        this.calibrationDao = calibrationDao;
        this.consultationDao = consultationDao;
        this.applicationContext = applicationContext;
    }

    //==============================================================================================
    // CHILD
    //==============================================================================================

    @Override
    public DexChild findChildById(Long id) {
        return childDao.findById(id);
    }

    @Override
    public DexChild findChildByCode(String code) {
        return childDao.findChildByCode(code);
    }

    @Override
    public List<DexChild> findChild(String filter, Integer offset, Integer limit) {
        return childDao.find(filter, offset, limit);
    }

    @Override
    public List<DexChild> findChildByGuardian(DexGuardian guardian, Integer offset, Integer limit) {
        return childDao.findChildByGuardian(guardian, offset, limit);
    }

    @Override
    public List<DexChild> findChildByTherapist(DexTherapist therapist, Integer offset, Integer limit) {
        return childDao.findChildByTherapist(therapist, offset, limit);
    }

    @Override
    public Integer countChildByTherapist() {
        return childDao.count();
    }

    @Override
    public Integer countChildByGuardian(DexGuardian guardian) {
        return childDao.count(guardian);
    }

    @Override
    public Integer countChildByTherapist(DexTherapist therapist) {
        return childDao.count(therapist);
    }

    @Override
    public void registerChild(DexChild child, DexGuardian guardian) throws Exception {

        // check if child exist
        if (childDao.isExists(child.getIdentityNo()))
            throw new Exception("You have already register this child");

        child.setCode(systemService.generateReferenceNo(DexConstants.CHILD_CODE));
        child.setCalibrated(false);
        child.setGuardian(guardian);
        childDao.save(child, securityService.getCurrentUser());
        entityManager.flush();
        entityManager.refresh(child);

        // first select the right schema for the age
        // selecting schema for the child
        DexEvaluationSchema schema = findEvaluationSchemaByOrdinal(1);

        // prepare calibration for the child
        DexCalibration calibration = new DexCalibrationImpl();
        calibration.setCode(systemService.generateReferenceNo(DexConstants.CALIBRATION));
        calibration.setTotalScore(0);
        calibration.setChild(child);
        calibration.setSchema(schema);
        calibrationDao.save(calibration, securityService.getCurrentUser());
        entityManager.flush();
        entityManager.refresh(calibration);

        child.setCalibration(calibration);
        childDao.update(child, securityService.getCurrentUser());
        entityManager.flush();
        entityManager.refresh(child);

        //prepare calibration response

        List<DexEvaluationSection> sections = evaluationSchemaDao.findSections(schema);

        for (DexEvaluationSection section : sections) {
            DexCalibrationSectionResponse sectionResponse = new DexCalibrationSectionResponseImpl();
            sectionResponse.setCode(systemService.generateReferenceNo(DexConstants.CALIBRATION_SECTION_RESPONSE));
            sectionResponse.setSection(section);
            sectionResponse.setSectionScore(0);
            List<DexEvaluationQuestion> questions = evaluationSchemaDao.findQuestions(section);

            for (DexEvaluationQuestion question : questions) {
                DexCalibrationQuestionResponse questionResponse = new DexCalibrationQuestionResponseImpl();
                questionResponse.setCode(systemService.generateReferenceNo(DexConstants.CALIBRATION_QUESTION_RESPONSE));
                questionResponse.setQuestion(question);
                questionResponse.setQuestionAnswer(DexAnswerType.NO);
                questionResponse.setQuestionScore(0);
                calibrationDao.addQuestionResponse(sectionResponse, questionResponse, securityService.getCurrentUser());
                entityManager.flush();
            }
            calibrationDao.addSectionResponse(calibration, sectionResponse, securityService.getCurrentUser());
            entityManager.flush();

        }

        entityManager.flush();

    }

    @Override
    public void addTherapist(DexChild child, DexTherapist therapist) {
//        child.setTherapists(therapist);
        childDao.update(child, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void updateChild(DexChild child, DexGuardian guardian) {
        childDao.update(child, securityService.getCurrentUser());
        entityManager.flush();

    }

    @Override
    public void removeChild(DexChild child, DexGuardian guardian) {
        childDao.remove(child, securityService.getCurrentUser());
        entityManager.flush();
    }

    //==============================================================================================
    // CALIBRATION
    //==============================================================================================

    @Override
    public DexCalibration findCalibrationById(Long id) {
        return calibrationDao.findById(id);
    }

    @Override
    public DexCalibration findCalibrationByCode(String code) {
        return calibrationDao.findCalibrationByCode(code);
    }

    @Override
    public DexCalibration findCalibrationByChild(DexChild child) {
        return calibrationDao.findCalibrationByChild(child);
    }

    @Override
    public List<DexCalibration> findCalibrations(String filter, Integer offset, Integer limit) {
        return calibrationDao.findCalibrations(filter, offset, limit);
    }

    @Override
    public void calibrate(DexChild child, DexEvaluationSchema schema) {

        DexCalibration calibration = new DexCalibrationImpl();
        calibration.setCode(systemService.generateReferenceNo(DexConstants.CALIBRATION));
        calibration.setTotalScore(0);
        calibration.setChild(child);
        calibration.setSchema(schema);
        calibrationDao.save(calibration, securityService.getCurrentUser());
        entityManager.flush();
        entityManager.refresh(calibration);

        child.setCalibration(calibration);
        childDao.update(child, securityService.getCurrentUser());
        entityManager.flush();
        entityManager.refresh(child);

        //prepare calibration response

        List<DexEvaluationSection> sections = evaluationSchemaDao.findSections(schema);

        for (DexEvaluationSection section : sections) {
            DexCalibrationSectionResponse sectionResponse = new DexCalibrationSectionResponseImpl();
            sectionResponse.setCode(systemService.generateReferenceNo(DexConstants.CALIBRATION_SECTION_RESPONSE));
            sectionResponse.setSection(section);
            sectionResponse.setSectionScore(0);
            List<DexEvaluationQuestion> questions = evaluationSchemaDao.findQuestions(section);

            for (DexEvaluationQuestion question : questions) {
                DexCalibrationQuestionResponse questionResponse = new DexCalibrationQuestionResponseImpl();
                questionResponse.setCode(systemService.generateReferenceNo(DexConstants.CALIBRATION_QUESTION_RESPONSE));
                questionResponse.setQuestion(question);
                questionResponse.setQuestionAnswer(DexAnswerType.NO);
                questionResponse.setQuestionScore(0);
                calibrationDao.addQuestionResponse(sectionResponse, questionResponse, securityService.getCurrentUser());
                entityManager.flush();
            }
            calibrationDao.addSectionResponse(calibration, sectionResponse, securityService.getCurrentUser());
            entityManager.flush();

        }
        entityManager.flush();
    }

    @Override
    public void submitCalibration(DexCalibration calibration) {

        List<DexCalibrationSectionResponse> sectionResponses = calibrationDao.findSectionResponseByCalibration(calibration);
        Integer totalScore = 0;
        Integer question = 0;
        for (DexCalibrationSectionResponse sectionResponse : sectionResponses) {
            Integer questionSection = 0;
            Integer fullScore;
            Integer sectionScore = 0;
            for (DexCalibrationQuestionResponse questionResponse : sectionResponse.getQuestionResponses()) {
                questionSection++;
                question++;
                sectionScore += questionResponse.getQuestionScore();
            }
            totalScore += sectionScore;
            fullScore = questionSection * 2;
            sectionResponse.setSectionScore(sectionScore);
            sectionResponse.setFullScore(fullScore);
            calibrationDao.updateSectionResponse(calibration, sectionResponse, securityService.getCurrentUser());
            entityManager.flush();

        }

        calibration.setTotalScore(totalScore);
        DexEvaluationStatus evaluationStatus = findEvaluationStatusBySchemaAndTotalScore(calibration.getSchema(), calibration.getTotalScore());
        calibration.setEvaluationStatus(evaluationStatus);
        calibrationDao.updateCalibration(calibration, securityService.getCurrentUser());
        entityManager.flush();

    }

    @Override
    public void checkCalibration(DexCalibration calibration) {

        if (calibration.getEvaluationStatus().getStatusType() == DexStatusType.FAIL ) {
            //prepare evaluation
            prepareEvaluation(calibration.getChild(), calibration.getSchema());
        } else if (calibration.getEvaluationStatus().getStatusType() == DexStatusType.FAIL
                && calibration.getSchema().getOrdinal() == 11) {
            //prepare evaluation
            prepareEvaluation(calibration.getChild(), calibration.getSchema());
        } else if (calibration.getEvaluationStatus().getStatusType() != DexStatusType.FAIL
                && calibration.getSchema().getOrdinal() == 11) {
            //prepare evaluation
            prepareEvaluation(calibration.getChild(), calibration.getSchema());
        } else {
            //prepare calibration schema
            DexEvaluationSchema schema = calibration.getSchema();
            DexEvaluationSchema currentSchema = findEvaluationSchemaByOrdinal(schema.getOrdinal() + 1);
            calibrate(calibration.getChild(), currentSchema);
        }
    }


    @Override
    public Integer countCalibration(String filter) {
        return calibrationDao.count(filter);
    }

    @Override
    public void updateCalibration(DexCalibration calibration) {
        calibrationDao.updateCalibration(calibration, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void removeCalibration(DexCalibration calibration, DexEvaluationSchema schema, DexChild child) {
        calibration.setChild(child);
        calibrationDao.deleteCalibration(calibration, securityService.getCurrentUser());
        entityManager.flush();
    }


    //==============================================================================================
    // EVALUATION
    //==============================================================================================


    @Override
    public DexEvaluation findEvaluationById(Long id) {
        return evaluationDao.findById(id);
    }

    @Override
    public DexEvaluation findEvaluationByCode(String code) {
        return evaluationDao.findEvaluationByCode(code);
    }

    @Override
    public DexEvaluation findEvaluationByChild(DexChild child) {
        return evaluationDao.findEvaluationByChild(child);
    }

    @Override
    public List<DexEvaluation> findEvaluationsByChild(DexChild child) {
        return evaluationDao.findEvaluationsByChild(child);
    }

    @Override
    public List<DexEvaluation> findEvaluations(String filter, Integer offset, Integer limit) {
        return evaluationDao.findEvaluations(filter, offset, limit);
    }

    @Override
    public List<DexEvaluation> findEvaluationsByGuardian(DexGuardian guardian, Integer offset, Integer limit) {
        return evaluationDao.findEvaluationsByGuardian(guardian, offset, limit);
    }

    @Override
    public void prepareEvaluation(DexChild child, DexEvaluationSchema schema) {

        // prepare evaluation for this child
        DexEvaluation evaluation = new DexEvaluationImpl();
        evaluation.setCode(systemService.generateReferenceNo(DexConstants.EVALUATION));
        evaluation.setTotalScore(0);
        evaluation.setChild(child);
        evaluation.setGuardian(child.getGuardian());
        evaluation.setSchema(schema);
        evaluationDao.save(evaluation, securityService.getCurrentUser());
        entityManager.flush();
        entityManager.refresh(evaluation);

        child.setEvaluation(evaluation);
        child.setCalibrated(true);
        child.setCognitiveAge(schema.getDescription());
        childDao.update(child, securityService.getCurrentUser());
        entityManager.flush();

        // prepare evaluation responses
        List<DexEvaluationSection> sections = evaluationSchemaDao.findSections(schema);

        for (DexEvaluationSection section : sections) {
            DexEvaluationSectionResponse sectionResponse = new DexEvaluationSectionResponseImpl();
            sectionResponse.setCode(systemService.generateReferenceNo(DexConstants.EVALUATION_SECTION_RESPONSE));
            sectionResponse.setSection(section);
            sectionResponse.setSectionScore(0);
            List<DexEvaluationQuestion> questions = evaluationSchemaDao.findQuestions(section);

            for (DexEvaluationQuestion question : questions) {
                DexEvaluationQuestionResponse questionResponse = new DexEvaluationQuestionResponseImpl();
                questionResponse.setCode(systemService.generateReferenceNo(DexConstants.EVALUATION_QUESTION_RESPONSE));
                questionResponse.setQuestion(question);
                questionResponse.setQuestionAnswer(DexAnswerType.NO);
                questionResponse.setQuestionScore(0);
                evaluationDao.addQuestionResponse(sectionResponse, questionResponse, securityService.getCurrentUser());
                entityManager.flush();
            }
            evaluationDao.addSectionResponse(evaluation, sectionResponse, securityService.getCurrentUser());
            entityManager.flush();

        }
        entityManager.flush();
    }

    @Override
    public void submitEvaluation(DexEvaluation evaluation) {

        List<DexEvaluationSectionResponse> sectionResponses = evaluationDao.findSectionResponseByEvaluation(evaluation);
        Integer totalScore = 0;
        Integer question = 0;
        for (DexEvaluationSectionResponse sectionResponse : sectionResponses) {
            Integer questionSection = 0;
            Integer sectionScore = 0;
            Integer totalSectionScore;
            for (DexEvaluationQuestionResponse questionResponse : sectionResponse.getQuestionResponses()) {
                questionSection++;
                question++;
                sectionScore += questionResponse.getQuestionScore();
            }
            totalScore += sectionScore;
            totalSectionScore = questionSection * 2;
            sectionResponse.setFullScore(totalSectionScore);
            sectionResponse.setSectionScore(sectionScore);
            evaluationDao.updateSectionResponse(evaluation, sectionResponse, securityService.getCurrentUser());
            entityManager.flush();

        }

        evaluation.setTotalScore(totalScore);
        DexEvaluationStatus evaluationStatus = findEvaluationStatusBySchemaAndTotalScore(evaluation.getSchema(), evaluation.getTotalScore());
        evaluation.setEvaluationStatus(evaluationStatus);
        evaluationDao.updateEvaluation(evaluation, securityService.getCurrentUser());
        entityManager.flush();
        entityManager.refresh(evaluation);

        applicationContext.publishEvent(new EvaluationCompleteEvent(evaluation));
    }


    @Override
    public void checkEvaluation(DexEvaluation evaluation) {

        if (evaluation.getSchema().getOrdinal() == 11) {
            //prepare evaluation
            prepareEvaluation(evaluation.getChild(), evaluation.getSchema());
        } else {
            //prepare calibration schema
            DexEvaluationSchema schema = evaluation.getSchema();
            DexEvaluationSchema currentSchema = findEvaluationSchemaByOrdinal(schema.getOrdinal() + 1);
            prepareEvaluation(evaluation.getChild(), currentSchema);
        }
    }

    @Override
    public Integer countEvaluation(String filter) {
        return evaluationDao.count(filter);
    }

    @Override
    public Integer countEvaluationByGuardian(DexGuardian guardian) {
        return evaluationDao.count(guardian);
    }


    @Override
    public void updateEvaluation(DexEvaluation evaluation) {
        evaluationDao.updateEvaluation(evaluation, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void removeEvaluation(DexEvaluation evaluation, DexEvaluationSchema schema, DexChild child) {
        evaluation.setChild(child);
        evaluationDao.remove(evaluation, securityService.getCurrentUser());
        entityManager.flush();
    }

    //====================================================================================================
    // EvaluationSchema
    //====================================================================================================

    @Override
    public DexEvaluationSchema findEvaluationSchemaById(Long id) {
        return evaluationSchemaDao.findById(id);
    }

    @Override
    public DexEvaluationSchema findEvaluationSchemaByCode(String code) {
        return evaluationSchemaDao.findByCode(code);
    }

    @Override
    public DexEvaluationSchema findEvaluationSchemaByAge(Integer age) {
        return evaluationSchemaDao.findSchemaByAge(age);
    }

    @Override
    public DexEvaluationSchema findEvaluationSchemaByOrdinal(Integer ordinal) {
        return evaluationSchemaDao.findSchemaByOrdinal(ordinal);
    }

    @Override
    public List<DexEvaluationSchema> findEvaluationSchemas(String filter, Integer offset, Integer limit) {
        return evaluationSchemaDao.find(filter, offset, limit);
    }

    @Override
    public Integer countEvaluationSchema(String filter) {
        return evaluationSchemaDao.count(filter);
    }

    @Override
    public void saveEvaluationSchema(DexEvaluationSchema schema) {
//        schema.setCode(systemService.generateReferenceNo(DexConstants.EVALUATION_SCHEMA_CODE));
        evaluationSchemaDao.save(schema, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void updateEvaluationSchema(DexEvaluationSchema EvaluationSchema) {
        evaluationSchemaDao.update(EvaluationSchema, securityService.getCurrentUser());
        entityManager.flush();
    }


    @Override
    public void removeEvaluationSchema(DexEvaluationSchema EvaluationSchema) {
        evaluationSchemaDao.remove(EvaluationSchema, securityService.getCurrentUser());
        entityManager.flush();
    }

    //====================================================================================================
    // EvaluationStatus
    //====================================================================================================

    @Override
    public DexEvaluationStatus findEvaluationStatusBySchemaAndTotalScore(DexEvaluationSchema schema, Integer totalScore) {
        return evaluationDao.findEvaluationStatusBySchemaAndTotalScore(schema, totalScore);
    }


    //====================================================================================================
    // Evaluation Section
    //====================================================================================================

    @Override
    public DexEvaluationSection findEvaluationSectionById(Long id) {
        return evaluationSchemaDao.findSectionById(id);
    }


    @Override
    public DexEvaluationSection findEvaluationSectionByCode(String code) {
        return evaluationSchemaDao.findSectionByCode(code);
    }


    @Override
    public List<DexEvaluationSection> findEvaluationSections(DexEvaluationSchema schema, Integer offset, Integer limit) {
        return evaluationSchemaDao.findSections(schema, offset, limit);
    }


    @Override
    public Integer countEvaluationSection(DexEvaluationSchema schema) {
        return evaluationSchemaDao.countSection(schema);
    }


    @Override
    public void saveEvaluationSection(DexEvaluationSchema schema, DexEvaluationSection section) {
        evaluationSchemaDao.addSection(schema, section, securityService.getCurrentUser());
    }


    @Override
    public void updateEvaluationSection(DexEvaluationSchema schema, DexEvaluationSection section) {
        evaluationSchemaDao.updateSection(schema, section, securityService.getCurrentUser());
    }


    @Override
    public void removeEvaluationSection(DexEvaluationSchema schema, DexEvaluationSection section) {
        evaluationSchemaDao.deleteSection(schema, section, securityService.getCurrentUser());

    }


    //====================================================================================================
    // Evaluation Question
    //====================================================================================================

    @Override
    public DexEvaluationQuestion findEvaluationQuestionById(Long id) {
        return evaluationSchemaDao.findQuestionById(id);
    }

    @Override
    public DexEvaluationQuestion findEvaluationQuestionByCode(String code) {
        return evaluationSchemaDao.findQuestionByCode(code);
    }

    @Override
    public List<DexEvaluationQuestion> findEvaluationQuestions(DexEvaluationSection section, Integer offset, Integer limit) {
        return evaluationSchemaDao.findQuestions(section, offset, limit);
    }

    @Override
    public Integer countEvaluationQuestion(DexEvaluationSection section) {
        return evaluationSchemaDao.countQuestion(section);
    }

    @Override
    public void saveEvaluationQuestion(DexEvaluationSection section, DexEvaluationQuestion question) {
        evaluationSchemaDao.addQuestion(section, question, securityService.getCurrentUser());
    }

    @Override
    public void updateEvaluationQuestion(DexEvaluationSection section, DexEvaluationQuestion question) {
        evaluationSchemaDao.updateQuestion(section, question, securityService.getCurrentUser());
    }

    @Override
    public void removeEvaluationQuestion(DexEvaluationSection section, DexEvaluationQuestion question) {
        evaluationSchemaDao.deleteQuestion(section, question, securityService.getCurrentUser());
    }


    //====================================================================================================
    // Evaluation Section Feedback
    //====================================================================================================

    @Override
    public DexEvaluationSectionResponse findSectionResponseById(Long id) {
        return evaluationDao.findSectionResponseById(id);
    }


    @Override
    public DexEvaluationSectionResponse findSectionResponseByCode(String code) {
        return evaluationDao.findSectionResponseByCode(code);
    }

    @Override
    public List<DexEvaluationSectionResponse> findSectionResponseByEvaluation(DexEvaluation evaluation) {
        return evaluationDao.findSectionResponseByEvaluation(evaluation);
    }


    @Override
    public List<DexEvaluationSectionResponse> findSectionResponses(DexEvaluation evaluation,
                                                                   Integer offset, Integer limit) {
        return evaluationDao.findSectionResponses(evaluation, offset, limit);
    }


    @Override
    public Integer countSectionResponse(DexEvaluation evaluation) {
        return evaluationDao.countSectionResponse(evaluation);
    }

    @Override
    public void updateSectionResponse(DexEvaluation evaluation,
                                      DexEvaluationSectionResponse sectionResponse) {
        evaluationDao.updateSectionResponse(evaluation, sectionResponse, securityService.getCurrentUser());
        entityManager.flush();
    }


    @Override
    public void deleteSectionResponse(DexEvaluation evaluation,
                                      DexEvaluationSectionResponse sectionResponse) {
        evaluationDao.deleteSectionResponse(evaluation, sectionResponse, securityService.getCurrentUser());
        entityManager.flush();
    }

    //====================================================================================================
    // Calibration Section Feedback
    //====================================================================================================

    @Override
    public DexCalibrationSectionResponse findCalibrationSectionResponseById(Long id) {
        return calibrationDao.findSectionResponseById(id);
    }


    @Override
    public DexCalibrationSectionResponse findCalibrationSectionResponseByCode(String code) {
        return calibrationDao.findSectionResponseByCode(code);
    }

    @Override
    public List<DexCalibrationSectionResponse> findSectionResponseByCalibration(DexCalibration calibration) {
        return calibrationDao.findSectionResponseByCalibration(calibration);
    }


    @Override
    public List<DexCalibrationSectionResponse> findSectionResponses(DexCalibration calibration,
                                                                    Integer offset, Integer limit) {
        return calibrationDao.findSectionResponses(calibration, offset, limit);
    }


    @Override
    public Integer countSectionResponse(DexCalibration calibration) {
        return calibrationDao.countSectionResponse(calibration);
    }

    @Override
    public void updateSectionResponse(DexCalibration calibration,
                                      DexCalibrationSectionResponse sectionResponse) {
        calibrationDao.updateSectionResponse(calibration, sectionResponse, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void deleteSectionResponse(DexCalibration calibration,
                                      DexCalibrationSectionResponse sectionResponse) {
        calibrationDao.deleteSectionResponse(calibration, sectionResponse, securityService.getCurrentUser());
        entityManager.flush();
    }


    //====================================================================================================
    // Calibration Question Feedback
    //====================================================================================================

    @Override
    public DexCalibrationQuestionResponse findCalibrationQuestionResponseById(Long id) {
        return calibrationDao.findQuestionResponseById(id);
    }


    @Override
    public DexCalibrationQuestionResponse findCalibrationQuestionResponseByCode(String code) {
        return calibrationDao.findQuestionResponseByCode(code);
    }

    @Override
    public DexCalibrationQuestionResponse findQuestionResponseBySectionResponse(DexCalibrationSectionResponse sectionResponse) {
        return calibrationDao.findQuestionResponseBySectionResponse(sectionResponse);
    }


    @Override
    public List<DexCalibrationQuestionResponse> findQuestionResponses(DexCalibrationSectionResponse sectionResponse,
                                                                      Integer offset, Integer limit) {
        return calibrationDao.findQuestionResponses(sectionResponse, offset, limit);
    }


    @Override
    public Integer countQuestionResponse(DexCalibrationSectionResponse sectionResponse) {
        return calibrationDao.countQuestionResponse(sectionResponse);
    }

    @Override
    public void updateQuestionResponse(DexCalibrationQuestionResponse questionResponse) {
        questionResponse.setQuestionScore(questionResponse.getQuestionAnswer().ordinal());
        calibrationDao.updateQuestionResponse(questionResponse, securityService.getCurrentUser());
    }


    @Override
    public void deleteQuestionResponse(DexCalibrationSectionResponse sectionResponse,
                                       DexCalibrationQuestionResponse questionResponse) {
        calibrationDao.deleteQuestionResponse(sectionResponse, questionResponse, securityService.getCurrentUser());
        entityManager.flush();
    }

    //====================================================================================================
    // Evaluation Question Feedback
    //====================================================================================================

    @Override
    public DexEvaluationQuestionResponse findQuestionResponseById(Long id) {
        return evaluationDao.findQuestionResponseById(id);
    }


    @Override
    public DexEvaluationQuestionResponse findQuestionResponseByCode(String code) {
        return evaluationDao.findQuestionResponseByCode(code);
    }

    @Override
    public DexEvaluationQuestionResponse findQuestionResponseBySectionResponse(DexEvaluationSectionResponse sectionResponse) {
        return evaluationDao.findQuestionResponseBySectionResponse(sectionResponse);
    }


    @Override
    public List<DexEvaluationQuestionResponse> findQuestionResponses(DexEvaluationSectionResponse sectionResponse,
                                                                     Integer offset, Integer limit) {
        return evaluationDao.findQuestionResponses(sectionResponse, offset, limit);
    }


    @Override
    public Integer countQuestionResponse(DexEvaluationSectionResponse sectionResponse) {
        return evaluationDao.countQuestionResponse(sectionResponse);
    }

    @Override
    public void updateQuestionResponse(DexEvaluationQuestionResponse questionResponse) {
        questionResponse.setQuestionScore(questionResponse.getQuestionAnswer().ordinal());
        evaluationDao.updateQuestionResponse(questionResponse, securityService.getCurrentUser());
    }


    @Override
    public void deleteQuestionResponse(DexEvaluationSectionResponse sectionResponse,
                                       DexEvaluationQuestionResponse questionResponse) {
        evaluationDao.deleteQuestionResponse(sectionResponse, questionResponse, securityService.getCurrentUser());
        entityManager.flush();
    }

    //====================================================================================================
    // ENROLLMENT
    //====================================================================================================

    @Override
    public List<DexEnrollment> findAllEnrollments(Integer offset, Integer limit) {
        return enrollmentDao.find(offset, limit);
    }

    @Override
    public List<DexEnrollment> findEnrollments(String filter, Integer offset, Integer limit) {
        return enrollmentDao.find(filter, offset, limit);
    }

    @Override
    public List<DexEnrollment> findEnrollmentByGuardian(DexGuardian guardian) {
        return enrollmentDao.findByGuardian(guardian);
    }

    @Override
    public List<DexEnrollment> findEnrollmentByGuardian(DexGuardian guardian, Integer offset, Integer limit) {
        return enrollmentDao.findByGuardian(guardian, offset, limit);
    }

    @Override
    public DexEnrollment findEnrollmentById(Long id) {
        return enrollmentDao.findById(id);
    }

    @Override
    public DexEnrollment findEnrollmentByCode(String code) {
        return enrollmentDao.findByCode(code);
    }

    @Override
    public DexEnrollment findEnrollmentByChild(DexChild child) {
        return enrollmentDao.findEnrollmentByChild(child);
    }

    @Override
    public Integer countEnrollment(String filter) {
        return enrollmentDao.count(filter);
    }

    @Override
    public Integer countEnrollmentByGuardian(DexGuardian guardian) {
        return enrollmentDao.countByGuardian(guardian);
    }

    @Override
    public void save(DexEnrollment enrollment) {

        enrollment.setCode(systemService.generateReferenceNo(DexConstants.ENROLLMENT));
        enrollmentDao.save(enrollment, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void enroll(DexEnrollment enrollment, DexChild child) {

        //prepare enrollment
        enrollment.setCode(systemService.generateReferenceNo(DexConstants.ENROLLMENT));
        enrollment.setChild(child);
        enrollment.setGuardian(child.getGuardian());
        enrollment.setPaid(false);
        enrollmentDao.save(enrollment, securityService.getCurrentUser());
        entityManager.flush();
        entityManager.refresh(enrollment);

        child.setEnrollment(enrollment);
        childDao.update(child, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void updateEnrollment(DexEnrollment enrollment) {
        enrollmentDao.update(enrollment, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void removeEnrollment(DexEnrollment enrollment) {
        enrollmentDao.remove(enrollment, securityService.getCurrentUser());
        entityManager.flush();

    }

    //====================================================================================================
    // COUPON
    //====================================================================================================

    @Override
    public List<DexCoupon> findAllCoupons() {
        return couponDao.findAllCoupons();
    }

    @Override
    public List<DexCoupon> findCoupons(String filter, Integer offset, Integer limit) {
        return couponDao.findCoupons(filter, offset, limit);
    }

    @Override
    public void payByCoupon(DexEnrollment enrollment, DexCoupon coupon) {
        //prepare enrollment
        enrollment.setPaid(true);
        enrollment.setCoupon(coupon);
        enrollment.setBillNo(coupon.getCode());
        enrollmentDao.update(enrollment, securityService.getCurrentUser());
        entityManager.flush();
        entityManager.refresh(enrollment);

        coupon.setEnrollment(enrollment);
        coupon.setRedeemed(true);
        couponDao.update(coupon, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public DexCoupon findCouponByCode(String code) {
        return couponDao.findByCode(code);
    }

    @Override
    public DexCoupon findAvailableCouponByCode(String code) {
        return couponDao.findAvailableCouponByCode(code);
    }

    @Override
    public Integer countCoupon(String filter) {
        return couponDao.count(filter);
    }

    @Override
    public void saveCoupon(DexCoupon coupon) {
        couponDao.save(coupon, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void updateCoupon(DexCoupon coupon) {
        couponDao.update(coupon, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void removeCoupon(DexCoupon coupon) {
        couponDao.remove(coupon, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void generateCoupon(Integer count) {
        for (int i = 0; i < count; i++) {
            DexCoupon coupon = new DexCouponImpl();
            coupon.setRedeemed(false);
            coupon.setCode(generateRandomCharacter(6)); // todo: maybe configurable length
            coupon.setDescription("GENERATED COUPON");
            coupon.setEnrollment(null);
            couponDao.save(coupon, securityService.getCurrentUser());
        }
        entityManager.flush();
    }

    //====================================================================================================
    // CONSULTATION
    //====================================================================================================

    @Override
    public List<DexConsultation> findConsultations(String filter, Integer offset, Integer limit) {
        return consultationDao.find(filter, offset, limit);
    }

    @Override
    public List<DexConsultation> findConsultationByChild(DexChild child, Integer offset, Integer limit) {
        return consultationDao.findByChild(child, offset, limit);
    }

    @Override
    public List<DexConsultation> findConsultationByChild(DexChild child) {
        return consultationDao.findByChild(child);
    }


    @Override
    public List<DexConsultation> findConsultationByTherapist(DexTherapist therapist, Integer offset, Integer limit) {
        return consultationDao.findByTherapist(therapist, offset, limit);
    }

    @Override
    public List<DexConsultation> findConsultationByTherapist(DexTherapist therapist) {
        return consultationDao.findByTherapist(therapist);
    }

    @Override
    public List<DexComment> findComments(DexConsultation consultation, Integer offset, Integer limit) {
        return consultationDao.findComments(consultation, offset, limit);
    }

    @Override
    public DexConsultation findConsultationByCode(String code) {
        return consultationDao.findByCode(code);
    }

    @Override
    public DexConsultation findConsultationByChildAndTherapist(DexChild child, DexTherapist therapist) {
        return consultationDao.findByChildAndTherapist(child, therapist);
    }

    @Override
    public DexComment findCommentByCode(String code) {
        return consultationDao.findCommentByCode(code);
    }

    @Override
    public Integer countConsultation(String filter) {
        return consultationDao.count(filter);
    }

    @Override
    public Integer countConsultationByChild(DexChild child) {
        return consultationDao.countByChild(child);
    }

    @Override
    public Integer countConsultationByTherapist(DexTherapist therapist) {
        return consultationDao.countByTherapist(therapist);
    }

    @Override
    public Integer countComment(DexConsultation consultation) {
        return consultationDao.countComment(consultation);
    }

    @Override
    public void saveConsultation(DexChild child, DexTherapist therapist) {
        DexConsultation consultation = new DexConsultationImpl();
        consultation.setCode(systemService.generateReferenceNo(DexConstants.CONSULTATION));
        consultation.setChild(child);
        consultation.setTherapist(therapist);
        consultationDao.save(consultation, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void updateConsultation(DexConsultation consultation) {
        consultationDao.update(consultation, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void removeConsultation(DexConsultation consultation) {
        consultationDao.remove(consultation, securityService.getCurrentUser());
        entityManager.flush();

    }

    @Override
    public void saveComment(DexConsultation consultation, DexComment comment) {
        comment.setCode(systemService.generateReferenceNo(DexConstants.COMMENT));
        comment.setConsultation(consultation);
        consultationDao.addComment(consultation, comment, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void updateComment(DexComment comment) {
        consultationDao.updateComment(comment, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void removeComment(DexComment comment) {
        consultationDao.deleteComment(comment, securityService.getCurrentUser());
        entityManager.flush();

    }

    // =================================================================================================================
    // BILLPLZ
    // =================================================================================================================

    @Override
    public Billplz generateBill(DexEnrollment enrollment, Billplz billplz) {
        HttpClient httpclient = HttpClientBuilder.create().build();

        Base64.Encoder encoder = Base64.getEncoder();
        String encoding = encoder.encodeToString((systemService.findConfigurationByKey(DexConstants.BILL_PLZ_API_KEY).getValue()).getBytes());

        billplz.setCallback_url(systemService.findConfigurationByKey(DexConstants.BILL_PLZ_CALLBACK_URL).getValue() + enrollment.getCode() + "/payment-completion");
        billplz.setRedirect_url(systemService.findConfigurationByKey(DexConstants.BILL_PLZ_REDIRECT_URL).getValue());
//        billplz.setCallback_url("http://localhost:8080/api/guardian/enrollment/enrollments/" + enrollment.getCode() + "/payment-completion");
//        billplz.setRedirect_url("http://localhost:4200/#/guardian/enrollment/payment-completion");

        HttpPost httppost = new HttpPost("https://billplz-staging.herokuapp.com/api/v3/bills");
        httppost.setHeader("Authorization", "Basic " + encoding);
        try {
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("collection_id", "nio1_wfq"));
            urlParameters.add(new BasicNameValuePair("description", billplz.getDescription()));
            urlParameters.add(new BasicNameValuePair("email", enrollment.getGuardian().getEmail()));
            urlParameters.add(new BasicNameValuePair("name", enrollment.getGuardian().getName()));
            urlParameters.add(new BasicNameValuePair("amount", billplz.getAmount().toString()));
            urlParameters.add(new BasicNameValuePair("callback_url", billplz.getCallback_url()));
            urlParameters.add(new BasicNameValuePair("redirect_url", billplz.getRedirect_url()));
            urlParameters.add(new BasicNameValuePair("reference_1_label", "Course"));
            urlParameters.add(new BasicNameValuePair("reference_1", enrollment.getDescription()));
            urlParameters.add(new BasicNameValuePair("reference_2_label", "Enrollment ID"));
            urlParameters.add(new BasicNameValuePair("reference_2", enrollment.getCode()));

            httppost.setEntity(new UrlEncodedFormEntity(urlParameters));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }

        LOG.debug("executing request " + httppost.getRequestLine());
        HttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));
            String line = null;

            while ((line = rd.readLine()) != null) {
                LOG.debug("BILLPLIZ {}", line);
                Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
                billplz = gson.fromJson(line, Billplz.class);
            }
        } catch (IOException | UnsupportedOperationException ex) {
            ex.printStackTrace();
        }
        return billplz;
    }

    private String generateRandomCharacter(Integer length) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            Random rnd = new Random();
            buffer.append(ALPHABET.charAt(rnd.nextInt(ALPHABET.length())));
        }
        return buffer.toString();
    }
}





