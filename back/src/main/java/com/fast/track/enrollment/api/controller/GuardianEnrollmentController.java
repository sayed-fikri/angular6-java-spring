package com.irichment.enrollment.api.controller;

import com.irichment.core.api.ApplicationSuccess;
import com.irichment.core.api.controller.CoreTransformer;
import com.irichment.core.domain.DexMetadata;
import com.irichment.enrollment.api.vo.*;
import com.irichment.enrollment.business.service.EnrollmentService;
import com.irichment.enrollment.domain.model.*;
import com.irichment.identity.api.vo.Therapist;
import com.irichment.identity.business.service.IdentityService;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexTherapist;
import com.irichment.identity.domain.model.DexUser;
import com.irichment.learning.api.controller.LearningTransformer;
import com.irichment.learning.business.service.LearningService;
import com.irichment.security.business.service.SecurityService;
import com.irichment.storage.business.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;

import static com.irichment.DexConstants.LIMIT;

/**
 *
 */
@Transactional
@RestController
@RequestMapping("/api/guardian/enrollment")
public class GuardianEnrollmentController {

    private static final Logger LOG = LoggerFactory.getLogger(GuardianEnrollmentController.class);

    private EnrollmentService enrollmentService;
    private EnrollmentTransformer enrollmentTransformer;
    private LearningService learningService;
    private CoreTransformer coreTransformer;
    private LearningTransformer learningTransformer;
    private AuthenticationManager authenticationManager;
    private IdentityService identityService;
    private SecurityService securityService;
    private EntityManager entityManager;
    private AttachmentService attachmentService;

    @Autowired
    public GuardianEnrollmentController(EnrollmentService enrollmentService, EnrollmentTransformer enrollmentTransformer,
                                        LearningService learningService, LearningTransformer learningTransformer,
                                        CoreTransformer coreTransformer,
                                        AuthenticationManager authenticationManager,
                                        IdentityService identityService, SecurityService securityService,
                                        EntityManager entityManager, AttachmentService attachmentService) {
        this.enrollmentService = enrollmentService;
        this.enrollmentTransformer = enrollmentTransformer;
        this.authenticationManager = authenticationManager;
        this.learningService = learningService;
        this.coreTransformer = coreTransformer;
        this.learningTransformer = learningTransformer;
        this.identityService = identityService;
        this.securityService = securityService;
        this.entityManager = entityManager;
        this.attachmentService = attachmentService;
    }

    //==============================================================================================
    // CHILD
    //==============================================================================================

    @GetMapping(value = "/children", params = {"page"})
    public ResponseEntity<ChildResult> findPagedChildren(@RequestParam Integer page) {
        LOG.debug("findPagedChildren: {}", page);
        Integer count = enrollmentService.countChildByGuardian(getCurrentGuardian());
        List<DexChild> children = enrollmentService.findChildByGuardian(getCurrentGuardian(), (page - 1) * LIMIT, LIMIT);
        List<Child> vos = enrollmentTransformer.toChildVos(children);
        return new ResponseEntity<ChildResult>(new ChildResult(vos, count), HttpStatus.OK);
    }

    @GetMapping(value = "/children")
    public ResponseEntity<List<Child>> findChildByGuardian() {
        DexGuardian guardian = getCurrentGuardian();
        return new ResponseEntity<List<Child>>(enrollmentTransformer.toChildVos(
                enrollmentService.findChildByGuardian(guardian, 0, Integer.MAX_VALUE)), HttpStatus.OK);
    }

    @GetMapping(value = "/children/{code}")
    public ResponseEntity<Child> findChildByCode(@PathVariable String code) {
        return new ResponseEntity<Child>(enrollmentTransformer.toChildVo(
                enrollmentService.findChildByCode(code)), HttpStatus.OK);
    }

    @PutMapping(value = "/children/{code}")
    public ResponseEntity<ApplicationSuccess> updateChild(@PathVariable String code, @RequestBody Child vo) {
        DexChild child = enrollmentService.findChildByCode(code);
        child.setName(vo.getName());
        child.setRealAge(vo.getRealAge());
        child.setGender(DexGenderType.get(vo.getGender().ordinal()));
        child.setConcernType(DexConcernType.get(vo.getConcernType().ordinal()));
        child.setRemark(vo.getRemark());
        enrollmentService.updateChild(child, getCurrentGuardian());
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/children/{code}")
    public ResponseEntity<ApplicationSuccess> removeChild(@PathVariable String code) {
        DexChild child = enrollmentService.findChildByCode(code);
        List<DexConsultation> consultations = enrollmentService.findConsultationByChild(child);
        for (DexConsultation consultation : consultations){
            enrollmentService.removeConsultation(consultation);
        }
        enrollmentService.removeChild(child, getCurrentGuardian());
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @PostMapping(value = "/children/registration")
    public ResponseEntity<ApplicationSuccess> registerChild(@RequestBody Child vo) throws Exception {
        DexChild child = new DexChildImpl();
        child.setName(vo.getName());
        child.setRealAge(vo.getRealAge());
        child.setIdentityNo(vo.getIdentityNo());
        child.setGender(DexGenderType.get(vo.getGender().ordinal()));
        child.setConcernType(DexConcernType.get(vo.getConcernType().ordinal()));
        child.setRemark(vo.getRemark());
        DexGuardian currentGuardian = getCurrentGuardian();
        LOG.debug("Guardian {} is registering Children {}", currentGuardian.getCode(), vo.getName());
        enrollmentService.registerChild(child, currentGuardian);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @PutMapping(value = "/children/therapist/{code}")
    public ResponseEntity<ApplicationSuccess> addTherapist(@PathVariable String code, @RequestBody Therapist vo) {
        DexChild child = enrollmentService.findChildByCode(code);
        DexTherapist therapist = identityService.findTherapistByCode(vo.getCode());
        enrollmentService.addTherapist(child, therapist);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }


    //==============================================================================================
    // CALIBRATION
    //==============================================================================================

    @GetMapping(value = "/calibrations/{code}")
    public ResponseEntity<Calibration> findCalibrationByCode(@PathVariable String code) {
        return new ResponseEntity<Calibration>(enrollmentTransformer.toCalibrationVo(
                enrollmentService.findCalibrationByCode(code)), HttpStatus.OK);
    }

    @GetMapping(value = "/calibrations/children", params = {"code"})
    public ResponseEntity<Calibration> findCalibrationByChild(@RequestParam String code) {
        DexChild child = enrollmentService.findChildByCode(code);
        return new ResponseEntity<Calibration>(enrollmentTransformer.toCalibrationVo(
                enrollmentService.findCalibrationByCode(child.getCalibration().getCode())), HttpStatus.OK);
    }

    @GetMapping(value = "/calibrations/{code}/submission")
    public ResponseEntity<ApplicationSuccess> submitCalibration(@PathVariable String code) {
        DexCalibration calibration = enrollmentService.findCalibrationByCode(code);
        enrollmentService.submitCalibration(calibration);
        enrollmentService.checkCalibration(calibration);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @PutMapping(value = "/calibrations/{code}/question-response")
    public ResponseEntity<ApplicationSuccess> updateCalibrationQuestionResponse
            (@PathVariable String code, @RequestBody EvaluationQuestionResponse vo) {
        DexCalibration calibration = enrollmentService.findCalibrationByCode(code);
        DexCalibrationQuestionResponse questionResponse = enrollmentService.findCalibrationQuestionResponseById(vo.getId());
        questionResponse.setQuestionAnswer(DexAnswerType.get(vo.getQuestionAnswer().ordinal()));
        enrollmentService.updateQuestionResponse(questionResponse);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/calibrations/{code}")
    public ResponseEntity<ApplicationSuccess> removeCalibration
            (@PathVariable String code, @PathVariable String schemaCode, @PathVariable String childCode) {
        DexCalibration calibration = enrollmentService.findCalibrationByCode(code);
        DexEvaluationSchema schema = enrollmentService.findEvaluationSchemaByCode(schemaCode);
        DexChild child = enrollmentService.findChildByCode(childCode);
        enrollmentService.removeCalibration(calibration, schema, child);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    //==============================================================================================
    // EVALUATION
    //==============================================================================================

    @GetMapping(value = "/evaluations", params = {"page"})
    public ResponseEntity<EvaluationResult> findPagedEvaluations(@RequestParam Integer page) {
        LOG.debug("findPagedEvaluations: {}", page);
        Integer count = enrollmentService.countEvaluationByGuardian(getCurrentGuardian());
        List<DexEvaluation> evaluations = enrollmentService.findEvaluationsByGuardian(getCurrentGuardian(), (page - 1) * LIMIT, LIMIT);
        List<Evaluation> vos = enrollmentTransformer.toEvaluationVos(evaluations);
        return new ResponseEntity<EvaluationResult>(new EvaluationResult(vos, count), HttpStatus.OK);
    }

    @GetMapping(value = "/evaluations")
    public ResponseEntity<List<Evaluation>> findEvaluations() {
        return new ResponseEntity<List<Evaluation>>(enrollmentTransformer.toEvaluationVos(
                enrollmentService.findEvaluations("%", 0, Integer.MAX_VALUE)), HttpStatus.OK);
    }

    @GetMapping(value = "/evaluations/{code}")
    public ResponseEntity<Evaluation> findEvaluationByCode(@PathVariable String code) {
        return new ResponseEntity<Evaluation>(enrollmentTransformer.toEvaluationVo(
                enrollmentService.findEvaluationByCode(code)), HttpStatus.OK);
    }

    @GetMapping(value = "/evaluations/children", params = {"code"})
    public ResponseEntity<Evaluation> findEvaluationByChild(@RequestParam String code) {
        DexChild child = enrollmentService.findChildByCode(code);
        return new ResponseEntity<Evaluation>(enrollmentTransformer.toEvaluationVo(
                enrollmentService.findEvaluationByCode(child.getEvaluation().getCode())), HttpStatus.OK);
    }

    @GetMapping(value = "/evaluations/{code}/submission")
    public ResponseEntity<ApplicationSuccess> submitEvaluation(@PathVariable String code) {
        DexEvaluation evaluation = enrollmentService.findEvaluationByCode(code);
        DexMetadata metadata = evaluation.getMetadata();
        metadata.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        evaluation.setMetadata(metadata);
        enrollmentService.submitEvaluation(evaluation);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @GetMapping(value = "/evaluations/{code}/child-level-up")
    public ResponseEntity<ApplicationSuccess> upLevelEvaluation(@PathVariable String code) {
        DexEvaluation evaluation = enrollmentService.findEvaluationByCode(code);
        enrollmentService.checkEvaluation(evaluation);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/evaluations/{code}")
    public ResponseEntity<ApplicationSuccess> removeEvaluation
            (@PathVariable String code, @PathVariable String schemaCode, @PathVariable String childCode) {
        DexEvaluation evaluation = enrollmentService.findEvaluationByCode(code);
        DexEvaluationSchema schema = enrollmentService.findEvaluationSchemaByCode(schemaCode);
        DexChild child = enrollmentService.findChildByCode(childCode);
        enrollmentService.removeEvaluation(evaluation, schema, child);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    //==============================================================================================
    // EVALUATION SCHEMA
    //==============================================================================================

    @GetMapping(value = "/evaluation-schemas", params = {"page"})
    public ResponseEntity<EvaluationSchemaResult> findPagedEvaluationSchemas(@RequestParam Integer page) {
        LOG.debug("findPagedEvaluationSchemas: {}", page);
        Integer count = enrollmentService.countEvaluationSchema("%");
        List<EvaluationSchema> evaluationSchemas = enrollmentTransformer.toEvaluationSchemaVos(
                enrollmentService.findEvaluationSchemas("%", ((page - 1) * LIMIT), LIMIT));
        return new ResponseEntity<EvaluationSchemaResult>(new EvaluationSchemaResult(evaluationSchemas, count), HttpStatus.OK);
    }

    @GetMapping(value = "/evaluation-schemas")
    public ResponseEntity<List<EvaluationSchema>> findEvaluationSchemas() {
        return new ResponseEntity<List<EvaluationSchema>>(enrollmentTransformer.toEvaluationSchemaVos(
                enrollmentService.findEvaluationSchemas("%", 0, Integer.MAX_VALUE)), HttpStatus.OK);
    }

    @GetMapping(value = "/evaluation-schemas/{code}")
    public ResponseEntity<EvaluationSchema> findEvaluationSchemasByCode(@PathVariable String code) {
        return new ResponseEntity<EvaluationSchema>(enrollmentTransformer.toEvaluationSchemaVo(
                enrollmentService.findEvaluationSchemaByCode(code)), HttpStatus.OK);
    }


    //==============================================================================================
    // EVALUATION SECTION
    //==============================================================================================


    @GetMapping(value = "/evaluation-schemas/{code}/evaluation-sections")
    public ResponseEntity<EvaluationSectionResult> findPagedEvaluationSections(@PathVariable String code) {
        DexEvaluationSchema schema = enrollmentService.findEvaluationSchemaByCode(code);
        Integer count = enrollmentService.countEvaluationSection(schema);
        List<DexEvaluationSection> evaluationSections = enrollmentService.findEvaluationSections(schema, 0, Integer.MAX_VALUE);
        List<EvaluationSection> sections = enrollmentTransformer.toEvaluationSectionVos(evaluationSections);
        return new ResponseEntity<EvaluationSectionResult>(new EvaluationSectionResult(sections, count), HttpStatus.OK);
    }

    @GetMapping(value = "/evaluation-schemas/{code}/evaluation-sections/{sectionCode}")
    public ResponseEntity<EvaluationSection> findEvaluationSectionsByCode(@PathVariable String code, @PathVariable String sectionCode) {
        DexEvaluationSchema schema = enrollmentService.findEvaluationSchemaByCode(code);
        DexEvaluationSection section = enrollmentService.findEvaluationSectionByCode(sectionCode);
        return new ResponseEntity<EvaluationSection>(enrollmentTransformer.toEvaluationSectionVo(section), HttpStatus.OK);
    }


    //==============================================================================================
    // EVALUATION QUESTION
    //==============================================================================================

    @GetMapping(value = "/evaluation-schemas/{code}/evaluation-sections/{sectionCode}/evaluation-questions")
    public ResponseEntity<EvaluationQuestionResult> findPagedEvaluationQuestions
            (@PathVariable String code, @PathVariable String sectionCode) {
        DexEvaluationSchema schema = enrollmentService.findEvaluationSchemaByCode(code);
        DexEvaluationSection section = enrollmentService.findEvaluationSectionByCode(sectionCode);
        Integer count = enrollmentService.countEvaluationQuestion(section);
        List<DexEvaluationQuestion> evaluationQuestions = enrollmentService.findEvaluationQuestions(section, 0, Integer.MAX_VALUE);
        List<EvaluationQuestion> questions = enrollmentTransformer.toEvaluationQuestionVos(evaluationQuestions);
        return new ResponseEntity<EvaluationQuestionResult>(new EvaluationQuestionResult(questions, count), HttpStatus.OK);
    }

    @GetMapping(value = "/evaluation-schemas/{code}/evaluation-sections/{sectionCode}/evaluation-questions/{questionCode}")
    public ResponseEntity<EvaluationQuestion> findPagedEvaluationQuestions
            (@PathVariable String code, @PathVariable String sectionCode, @PathVariable String questionCode) {
        DexEvaluationSchema schema = enrollmentService.findEvaluationSchemaByCode(code);
        DexEvaluationSection section = enrollmentService.findEvaluationSectionByCode(sectionCode);
        DexEvaluationQuestion question = enrollmentService.findEvaluationQuestionByCode(questionCode);
        return new ResponseEntity<EvaluationQuestion>(enrollmentTransformer.toEvaluationQuestionVo(question), HttpStatus.OK);
    }

    //==============================================================================================
    // EVALUATION SECTION RESPONSE
    //==============================================================================================

    @GetMapping(value = "/schema-responses/{code}/section-responses")
    public ResponseEntity<EvaluationSectionResponseResult> findPagedEvaluationSectionResponses(@PathVariable String code) {
        DexEvaluation evaluation = enrollmentService.findEvaluationByCode(code);
        Integer count = enrollmentService.countSectionResponse(evaluation);
        List<DexEvaluationSectionResponse> evaluationSectionResponses = enrollmentService.findSectionResponses(evaluation, 0, Integer.MAX_VALUE);
        List<EvaluationSectionResponse> sectionResponses = enrollmentTransformer.toEvaluationSectionResponseVos(evaluationSectionResponses);
        return new ResponseEntity<EvaluationSectionResponseResult>(new EvaluationSectionResponseResult(sectionResponses, count), HttpStatus.OK);
    }

    @GetMapping(value = "/schema-responses/{code}/section-responses/{sectionResponseCode}")
    public ResponseEntity<EvaluationSectionResponse> findPagedEvaluationSectionResponses(@PathVariable String code, @PathVariable String sectionResponseCode) {
        DexEvaluation evaluation = enrollmentService.findEvaluationByCode(code);
        DexEvaluationSectionResponse sectionResponse = enrollmentService.findSectionResponseByCode(sectionResponseCode);
        return new ResponseEntity<EvaluationSectionResponse>(enrollmentTransformer.toEvaluationSectionResponseVo(sectionResponse), HttpStatus.OK);
    }


    @PutMapping(value = "/schema-responses/{code}/section-responses/{sectionResponseCode}")
    public ResponseEntity<ApplicationSuccess> updateEvaluationSectionResponse(@PathVariable String code, @PathVariable String sectionResponseCode, @RequestBody EvaluationSectionResponse vo) {
        DexEvaluation evaluation = enrollmentService.findEvaluationByCode(code);
        DexEvaluationSectionResponse sectionResponse = enrollmentService.findSectionResponseById(vo.getId());
        sectionResponse.setDescription(vo.getDescription());
        enrollmentService.updateSectionResponse(evaluation, sectionResponse);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/schema-responses/{code}/section-responses/{sectionResponseCode}")
    public ResponseEntity<ApplicationSuccess> removeEvaluationSectionResponse(@PathVariable String code, @PathVariable String sectionResponseCode) {
        DexEvaluation evaluation = enrollmentService.findEvaluationByCode(code);
        DexEvaluationSectionResponse sectionResponse = enrollmentService.findSectionResponseByCode(sectionResponseCode);
        enrollmentService.deleteSectionResponse(evaluation, sectionResponse);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }


    //==============================================================================================
    // EVALUATION QUESTION RESPONSE
    //==============================================================================================

    @GetMapping(value = "/schema-responses/{code}/section-responses/{sectionResponseCode}/question-responses")
    public ResponseEntity<EvaluationQuestionResponseResult> findPagedEvaluationQuestionResponses
            (@PathVariable String code, @PathVariable String sectionResponseCode) {
        DexEvaluation evaluation = enrollmentService.findEvaluationByCode(code);
        DexEvaluationSectionResponse sectionResponse = enrollmentService.findSectionResponseByCode(sectionResponseCode);
        Integer count = enrollmentService.countQuestionResponse(sectionResponse);
        List<DexEvaluationQuestionResponse> evaluationQuestionResponses = enrollmentService.findQuestionResponses(sectionResponse, 0, Integer.MAX_VALUE);
        List<EvaluationQuestionResponse> questionResponses = enrollmentTransformer.toEvaluationQuestionResponseVos(evaluationQuestionResponses);
        return new ResponseEntity<EvaluationQuestionResponseResult>(new EvaluationQuestionResponseResult(questionResponses, count), HttpStatus.OK);
    }

    @GetMapping(value = "/schema-responses/{code}/section-responses/{sectionResponseCode}/question-responses/{questionResponseCode}")
    public ResponseEntity<EvaluationQuestionResponse> findPagedEvaluationQuestionResponses
            (@PathVariable String code, @PathVariable String sectionResponseCode, @PathVariable String questionResponseCode) {
        DexEvaluation evaluation = enrollmentService.findEvaluationByCode(code);
        DexEvaluationSectionResponse sectionResponse = enrollmentService.findSectionResponseByCode(sectionResponseCode);
        DexEvaluationQuestionResponse questionResponse = enrollmentService.findQuestionResponseByCode(questionResponseCode);
        return new ResponseEntity<EvaluationQuestionResponse>(enrollmentTransformer.toEvaluationQuestionResponseVo(questionResponse), HttpStatus.OK);
    }

    @PutMapping(value = "/evaluations/{code}/question-response")
    public ResponseEntity<ApplicationSuccess> updateEvaluationQuestionResponse
            (@PathVariable String code, @RequestBody EvaluationQuestionResponse vo) {
        DexEvaluation evaluation = enrollmentService.findEvaluationByCode(code);
        DexEvaluationQuestionResponse questionResponse = enrollmentService.findQuestionResponseById(vo.getId());
        questionResponse.setQuestionAnswer(DexAnswerType.get(vo.getQuestionAnswer().ordinal()));
        enrollmentService.updateQuestionResponse(questionResponse);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    //==============================================================================================
    // ENROLLMENT
    //==============================================================================================

    @GetMapping(value = "/enrollments", params = {"page", "filter"})
    public ResponseEntity<EnrollmentResult> findPagedEnrollments(@RequestParam Integer page, @RequestParam String filter) {
        Integer count = enrollmentService.countEnrollmentByGuardian(getCurrentGuardian());
        List<DexEnrollment> enrollments = enrollmentService.findEnrollmentByGuardian(getCurrentGuardian(), (page - 1) * LIMIT, LIMIT);
        return new ResponseEntity<EnrollmentResult>(
                new EnrollmentResult(
                        enrollmentTransformer.toEnrollmentVos(enrollments),
                        count
                ), HttpStatus.OK);
    }

    @GetMapping(value = "/enrollments")
    public ResponseEntity<List<Enrollment>> findAllEnrollments() {
        return new ResponseEntity<List<Enrollment>>(enrollmentTransformer.toEnrollmentVos(
                enrollmentService.findEnrollmentByGuardian(getCurrentGuardian(),0, Integer.MAX_VALUE)), HttpStatus.OK);
    }

    @GetMapping(value = "/enrollments/{code}")
    public ResponseEntity<Enrollment> findEnrollmentByCode(@PathVariable String code) {
        return new ResponseEntity<Enrollment>(enrollmentTransformer.toEnrollmentVo(
                enrollmentService.findEnrollmentByCode(code)), HttpStatus.OK);

    }

    @GetMapping(value = "/enrollments/children/{code}")
    public ResponseEntity<Enrollment> findEnrollmentByChild(@PathVariable String code) {
        DexChild child = enrollmentService.findChildByCode(code);
        return new ResponseEntity<Enrollment>(enrollmentTransformer.toEnrollmentVo(
                enrollmentService.findEnrollmentByCode(child.getEnrollment().getCode())), HttpStatus.OK);

    }

    @PostMapping(value = "/enrollments/children/{code}")
    public ResponseEntity<ApplicationSuccess> saveEnrollment(@PathVariable String code,
                                                             @RequestBody Enrollment vo) throws Exception {
        DexChild child = enrollmentService.findChildByCode(code);
        DexEnrollment enrollment = new DexEnrollmentImpl();
        enrollment.setCourse1(learningService.findCourseByCode(vo.getCourse1().getCode()));
//        enrollment.setCourse2(learningService.findCourseByCode(vo.getCourse2().getCode()));
//        enrollment.setCourse3(learningService.findCourseByCode(vo.getCourse3().getCode()));
        enrollmentService.enroll(enrollment,child);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @PutMapping(value = "/enrollments/{code}")
    public ResponseEntity<ApplicationSuccess> updateEnrollment(@PathVariable String code,
                                                               @RequestBody Enrollment vo) {
        DexEnrollment enrollment = enrollmentService.findEnrollmentByCode(code);
        enrollment.setCode(vo.getCode());
        enrollment.setPaid(vo.getPaid());
        enrollment.setBillNo(vo.getBillNo());
        enrollment.setDescription(vo.getDescription());
        enrollmentService.updateEnrollment(enrollment);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/enrollments/{code}")
    public ResponseEntity<ApplicationSuccess> removeEnrollment(@PathVariable String code) {
        DexEnrollment enrollment = enrollmentService.findEnrollmentByCode(code);
        enrollmentService.removeEnrollment(enrollment);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }


    //==============================================================================================
    // CONSULTATION
    //==============================================================================================

    @GetMapping(value = "/consultations/{code}", params = {"page", "filter"})
    public ResponseEntity<ConsultationResult> findPagedConsultations(@PathVariable String code,@RequestParam Integer page, @RequestParam String filter) {
        DexChild child = enrollmentService.findChildByCode(code);
        Integer count = enrollmentService.countConsultationByChild(child);
        List<DexConsultation> consultations = enrollmentService.findConsultationByChild(child, (page - 1) * LIMIT, LIMIT);
        return new ResponseEntity<ConsultationResult>(
                new ConsultationResult(
                        enrollmentTransformer.toConsultationVos(consultations),
                        count
                ), HttpStatus.OK);
    }

    @GetMapping(value = "/consultations/{code}")
    public ResponseEntity<Consultation> findConsultationByCode(@PathVariable String code) {
        return new ResponseEntity<Consultation>(enrollmentTransformer.toConsultationVo(
                enrollmentService.findConsultationByCode(code)), HttpStatus.OK);

    }

    @PostMapping(value = "/consultations/children/{code}")
    public ResponseEntity<ApplicationSuccess> saveConsultation(@PathVariable String code,
                                                             @RequestBody Therapist vo) throws Exception {
        DexChild child = enrollmentService.findChildByCode(code);
        DexTherapist therapist = identityService.findTherapistByCode(vo.getCode());
        enrollmentService.saveConsultation(child,therapist);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }


    @DeleteMapping(value = "/consultations/{code}")
    public ResponseEntity<ApplicationSuccess> removeConsultation(@PathVariable String code) {
        DexConsultation consultation = enrollmentService.findConsultationByCode(code);
        enrollmentService.removeConsultation(consultation);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    //==============================================================================================
    // COMMENT
    //==============================================================================================

    @GetMapping(value = "/consultations/{code}/comments",  params = {"page", "filter"})
    public ResponseEntity<CommentResult> findPagedComment(@PathVariable String code,@RequestParam String filter,@RequestParam Integer page) {
        DexConsultation consultation = enrollmentService.findConsultationByCode(code);
        Integer count = enrollmentService.countComment(consultation);
        List<DexComment> comments = enrollmentService.findComments(consultation, (page - 1) * LIMIT, LIMIT);
        List<Comment> vos = enrollmentTransformer.toCommentVos(comments);
        return new ResponseEntity<CommentResult>(new CommentResult(vos, count), HttpStatus.OK);
    }

    @GetMapping(value = "/consultations/comments/{code}")
    public ResponseEntity<Comment> findCommentByCode(@PathVariable String code) {
        return new ResponseEntity<Comment>(enrollmentTransformer.toCommentVo(
                enrollmentService.findCommentByCode(code)), HttpStatus.OK);
    }


    //==============================================================================================
    // COUPON
    //==============================================================================================

    @GetMapping(value = "/coupons", params = {"code"})
    public ResponseEntity<Coupon> searchCoupon(@RequestParam String code) throws Exception {
        return new ResponseEntity<Coupon>(enrollmentTransformer.toCouponVo(
                enrollmentService.findAvailableCouponByCode(code)), HttpStatus.OK);
    }

    @GetMapping(value = "/coupons/{code}")
    public ResponseEntity<Coupon> findCouponByCode(@PathVariable String code) {
        return new ResponseEntity<Coupon>(enrollmentTransformer.toCouponVo(
                enrollmentService.findCouponByCode(code)), HttpStatus.OK);
    }
    @GetMapping(value = "/enrollments/{code}/coupons/{couponCode}/coupon-enroll")
    public ResponseEntity<ApplicationSuccess> payByCoupon(@PathVariable String code, @PathVariable String couponCode) {
        DexEnrollment enrollment = enrollmentService.findEnrollmentByCode(code);
        DexCoupon coupon = enrollmentService.findCouponByCode(couponCode);
        enrollmentService.payByCoupon(enrollment,coupon);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    //==============================================================================================
    // PAYMENT BILLPLZ
    //==============================================================================================

    // =============================================================================================
    // 1- Customer visits your site.
    // 2- Customer chooses to make payment.
    // 3- Your site creates a Bill via API call.
    // 4- Billplz API returns Bill's URL.
    // 5- nmb
    // 6- The customer makes payment via payment option of choice.
    // 7- Billplz sends a server-side update**at best effort (Payment Completion) to your site upon payment failure or success.
    //    (Basic Callback URL / X Signature Callback URL depending on your configuration)
    //    [your backend server should capture the transaction update at this point] refer to API#X Signature Callback Url
    //    Billplz redirects (Payment Completion) the customer back to your site if redirect_url is not empty (Basic Redirect
    //    URL / X Signature Redirect URL depending on your configuration)
    // 8- Billplz redirects (Payment Completion) the customer back to your site if redirect_url is not empty (Basic
    //    Redirect URL / X Signature Redirect URL depending on your configuration)[your server should capture the
    //    transaction update at this point and give your user an instant reflection on the page loaded] refer to API#X
    //    Signature Redirect Url or, The customer will see Billplz receipt if redirect_url is not present.
    // ===============================================================================================

    // BillPlz Step 3
    @PutMapping(value = "/enrollments/{code}/generate-bill")
    public ResponseEntity<Billplz> generateBill(@PathVariable String code, @RequestBody Billplz vo) {
        DexChild child = enrollmentService.findChildByCode(code);
        DexEnrollment enrollment = enrollmentService.findEnrollmentByCode(child.getEnrollment().getCode());

        return new ResponseEntity<Billplz>(enrollmentService.generateBill(enrollment, vo), HttpStatus.OK);
    }

    // BillPlz Step 7
    @PostMapping(value = "/enrollments/{code}/payment-completion")
    public ResponseEntity<DexEnrollment> paymentCompletion(
            @PathVariable String code,
            @RequestParam("id") String id,
            @RequestParam("collection_id") String collectionId,
            @RequestParam("paid") Boolean paid,
            @RequestParam("amount") Double amount,
            @RequestParam("paid_amount") Double paidAmount,
            @RequestParam("due_at") String dueAt,
            @RequestParam("email") String email,
            @RequestParam("mobile") String mobile,
            @RequestParam("name") String name,
            @RequestParam("url") String url,
            @RequestParam("paid_at") String paidAt) {
        autoLogin();
        DexEnrollment enrollment = enrollmentService.findEnrollmentByCode(code);

        enrollment.setPaid(paid);
        enrollment.setBillNo(id);
        enrollmentService.updateEnrollment(enrollment);
        entityManager.flush();

        return new ResponseEntity<DexEnrollment>(enrollment, HttpStatus.OK);
    }


    // =============================================================================================
    // PRIVATE METHODS
    // =============================================================================================

    private DexGuardian getCurrentGuardian() {
        DexUser user = securityService.getCurrentUser();
        return (DexGuardian) user.getActor();
    }

    private void autoLogin() {
//        DexUser user = securityService.getCurrentUser();
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());
//        Authentication authed = authenticationManager.authenticate(token);
//        SecurityContextHolder.getContext().setAuthentication(authed);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("shasa.aziz", "abc123");
        Authentication authed = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authed);
    }
}


