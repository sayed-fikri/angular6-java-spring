package com.irichment.enrollment.api.controller;

import com.irichment.core.api.ApplicationSuccess;
import com.irichment.enrollment.api.vo.*;
import com.irichment.enrollment.business.service.EnrollmentService;
import com.irichment.enrollment.domain.model.*;
import com.irichment.identity.business.service.IdentityService;
import com.irichment.identity.domain.model.DexGuardian;
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
import java.util.List;

import static com.irichment.DexConstants.LIMIT;

/**
 *
 */
@Transactional
@RestController
@RequestMapping("/api/staff/enrollment")
public class StaffEnrollmentController {

    private static final Logger LOG = LoggerFactory.getLogger(StaffEnrollmentController.class);

    private EnrollmentService enrollmentService;
    private EnrollmentTransformer enrollmentTransformer;
    private LearningService learningService;
    private LearningTransformer learningTransformer;
    private AuthenticationManager authenticationManager;
    private IdentityService identityService;
    private SecurityService securityService;
    private EntityManager entityManager;
    private AttachmentService attachmentService;

    @Autowired
    public StaffEnrollmentController(EnrollmentService enrollmentService, EnrollmentTransformer enrollmentTransformer,
                                     LearningService learningService, LearningTransformer learningTransformer,
                                     AuthenticationManager authenticationManager,
                                     IdentityService identityService, SecurityService securityService,
                                     EntityManager entityManager, AttachmentService attachmentService) {
        this.enrollmentService = enrollmentService;
        this.enrollmentTransformer = enrollmentTransformer;
        this.authenticationManager = authenticationManager;
        this.learningService = learningService;
        this.learningTransformer = learningTransformer;
        this.identityService = identityService;
        this.securityService = securityService;
        this.entityManager = entityManager;
        this.attachmentService = attachmentService;
    }

    //==============================================================================================
    // CHILD
    //==============================================================================================

    @GetMapping(value = "/guardian/children", params = {"page", "code"})
    public ResponseEntity<ChildResult> findPagedChildByGuardian(@RequestParam Integer page, @RequestParam String code) {
        LOG.debug("findPagedChildGuardian: {code}", page);
        DexGuardian guardian = identityService.findGuardianByCode(code);
        Integer count = enrollmentService.countChildByGuardian(guardian);
        List<DexChild> children = enrollmentService.findChildByGuardian(guardian, (page - 1) * LIMIT, LIMIT);
        List<Child> vos = enrollmentTransformer.toChildVos(children);
        return new ResponseEntity<ChildResult>(new ChildResult(vos, count), HttpStatus.OK);
    }

    @GetMapping(value = "/guardian/children", params = {"code"})
    public ResponseEntity<List<Child>> findChildByGuardian(@RequestParam String code) {
        DexGuardian guardian = identityService.findGuardianByCode(code);
        return new ResponseEntity<List<Child>>(enrollmentTransformer.toChildVos(
                enrollmentService.findChildByGuardian(guardian, 0, Integer.MAX_VALUE)), HttpStatus.OK);
    }


    @GetMapping(value = "/children/{code}")
    public ResponseEntity<Child> findChildByCode(@PathVariable String code) {
        return new ResponseEntity<Child>(enrollmentTransformer.toChildVo(
                enrollmentService.findChildByCode(code)), HttpStatus.OK);
    }

    @PostMapping(value = "/registration/children")
    public ResponseEntity<ApplicationSuccess> registerChild(@RequestBody Child vo) throws Exception {
        DexChild child = new DexChildImpl();
        child.setName(vo.getName());
        child.setRealAge(vo.getRealAge());
        child.setIdentityNo(vo.getIdentityNo());
        child.setGender(DexGenderType.get(vo.getGender().ordinal()));
        child.setConcernType(DexConcernType.get(vo.getConcernType().ordinal()));
        child.setRemark(vo.getRemark());
        DexGuardian guardian = (DexGuardian) securityService.getCurrentUser().getActor();
        LOG.debug("Guardian {} is registering Children {}", guardian.getCode(), vo.getName());
        enrollmentService.registerChild(child, guardian);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    //todo update child
    @PutMapping(value = "/children/{code}")
    public ResponseEntity<ApplicationSuccess> updateChild(@PathVariable String guardianCode, @PathVariable String code, @RequestBody Child vo) {
        DexChild child = enrollmentService.findChildByCode(code);
        child.setName(vo.getName());
        child.setRealAge(vo.getRealAge());
        child.setConcernType(DexConcernType.get(vo.getConcernType().ordinal()));
        child.setRemark(vo.getRemark());
        enrollmentService.updateChild(child, identityService.findGuardianByCode(guardianCode));
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    //attachment file controller
//    @PostMapping(value = "/upload-picture/{code}/{attachmentDescription}")
//    public ResponseEntity<ApplicationSuccess> addPermitApplicationAttachment(@PathVariable String code, @PathVariable String attachmentDescription,
//                                                                             @RequestParam("file") MultipartFile file) throws Exception {
//        DexChild children = enrollmentService.findChildByCode(code);
//
//        File tempFile = File.createTempFile("tmp_", null);
//        FileUtils.writeByteArrayToFile(tempFile, file.getBytes());
//
//        String name = file.getOriginalFilename();
//        if (null == name)
//            name = System.currentTimeMillis() + ".txt";
//
//        Attachment attachment = new Attachment(children, name, attachmentDescription, new FileInputStream(tempFile));
//        attachmentService.addAttachment(attachment);
//        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
//    }

    @DeleteMapping(value = "/children/{code}")
    public ResponseEntity<ApplicationSuccess> removeChild(@PathVariable String guardianCode, @PathVariable String code) {
        DexChild child = enrollmentService.findChildByCode(code);
        enrollmentService.removeChild(child, identityService.findGuardianByCode(guardianCode));
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    //==============================================================================================
    // EVALUATION
    //==============================================================================================

    @GetMapping(value = "/evaluations", params = {"page"})
    public ResponseEntity<EvaluationResult> findPagedEvaluations(@RequestParam Integer page) {
        LOG.debug("findPagedEvaluations: {}", page);
        Integer count = enrollmentService.countEvaluation("%");
        List<Evaluation> evaluations = enrollmentTransformer.toEvaluationVos(
                enrollmentService.findEvaluations("%", ((page - 1) * LIMIT), LIMIT));
        return new ResponseEntity<EvaluationResult>(new EvaluationResult(evaluations, count), HttpStatus.OK);
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
                enrollmentService.findEvaluationByChild(child)), HttpStatus.OK);
    }

    @GetMapping(value = "/evaluations/{code}/submission")
    public ResponseEntity<ApplicationSuccess> submitEvaluation(@PathVariable String code) {
        DexEvaluation evaluation = enrollmentService.findEvaluationByCode(code);
        enrollmentService.submitEvaluation(evaluation);
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

    @PostMapping(value = "/evaluation-schemas")
    public ResponseEntity<ApplicationSuccess> saveEvaluationSchema(@RequestBody EvaluationSchema vo) {
        DexEvaluationSchema evaluationSchema = new DexEvaluationSchemaImpl();
        evaluationSchema.setCode(vo.getCode());
        evaluationSchema.setDescription(vo.getDescription());
        enrollmentService.saveEvaluationSchema(evaluationSchema);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @PutMapping(value = "/evaluation-schemas/{code}")
    public ResponseEntity<ApplicationSuccess> updateEvaluationSchema(@PathVariable String code, @RequestBody EvaluationSchema vo) {
        DexEvaluationSchema evaluationSchema = enrollmentService.findEvaluationSchemaById(vo.getId());
        evaluationSchema.setCode(vo.getCode());
        evaluationSchema.setDescription(vo.getDescription());
        enrollmentService.updateEvaluationSchema(evaluationSchema);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/evaluation-schemas/{code}")
    public ResponseEntity<ApplicationSuccess> removeEvaluationSchema(@PathVariable String code) {
        DexEvaluationSchema evaluationSchema = enrollmentService.findEvaluationSchemaByCode(code);
        enrollmentService.removeEvaluationSchema(evaluationSchema);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
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

    @PostMapping(value = "/evaluation-schemas/{code}/evaluation-sections")
    public ResponseEntity<ApplicationSuccess> saveEvaluationSection(@PathVariable String code, @RequestBody EvaluationSection vo) {
        DexEvaluationSchema schema = enrollmentService.findEvaluationSchemaByCode(code);
        DexEvaluationSection section = new DexEvaluationSectionImpl();
        section.setCode(vo.getCode());
        section.setDescription(vo.getDescription());
        enrollmentService.saveEvaluationSection(schema, section);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @PutMapping(value = "/evaluation-schemas/{code}/evaluation-sections/{sectionCode}")
    public ResponseEntity<ApplicationSuccess> updateEvaluationSection(@PathVariable String code, @PathVariable String sectionCode, @RequestBody EvaluationSection vo) {
        DexEvaluationSchema schema = enrollmentService.findEvaluationSchemaByCode(code);
        DexEvaluationSection evaluationSection = enrollmentService.findEvaluationSectionByCode(sectionCode);
        evaluationSection.setDescription(vo.getDescription());
        enrollmentService.updateEvaluationSection(schema, evaluationSection);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/evaluation-schemas/{code}/evaluation-sections/{sectionCode}")
    public ResponseEntity<ApplicationSuccess> removeEvaluationSection(@PathVariable String code, @PathVariable String sectionCode) {
        DexEvaluationSchema schema = enrollmentService.findEvaluationSchemaByCode(code);
        DexEvaluationSection evaluationSection = enrollmentService.findEvaluationSectionByCode(sectionCode);
        enrollmentService.removeEvaluationSection(schema, evaluationSection);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
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

    @PostMapping(value = "/evaluation-schemas/{code}/evaluation-sections/{sectionCode}/evaluation-question")
    public ResponseEntity<ApplicationSuccess> saveEvaluationQuestion
            (@PathVariable String code, @PathVariable String sectionCode, @RequestBody EvaluationQuestion vo) {
        DexEvaluationSchema schema = enrollmentService.findEvaluationSchemaByCode(code);
        DexEvaluationSection section = enrollmentService.findEvaluationSectionByCode(sectionCode);
        DexEvaluationQuestion evaluationQuestion = new DexEvaluationQuestionImpl();
        evaluationQuestion.setCode(vo.getCode());
        evaluationQuestion.setDescription(vo.getDescription());
        evaluationQuestion.setStatement(vo.getStatement());
        enrollmentService.saveEvaluationQuestion(section, evaluationQuestion);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @PutMapping(value = "/evaluation-schemas/{code}/evaluation-sections/{sectionCode}/evaluation-question/{questionCode}")
    public ResponseEntity<ApplicationSuccess> updateEvaluationQuestion
            (@PathVariable String code, @PathVariable String sectionCode, @PathVariable String questionCode,
             @RequestBody EvaluationQuestion vo) {
        DexEvaluationSchema schema = enrollmentService.findEvaluationSchemaByCode(code);
        DexEvaluationSection section = enrollmentService.findEvaluationSectionByCode(sectionCode);
        DexEvaluationQuestion evaluationQuestion = enrollmentService.findEvaluationQuestionById(vo.getId());
        evaluationQuestion.setDescription(vo.getDescription());
        evaluationQuestion.setStatement(vo.getStatement());
        enrollmentService.updateEvaluationQuestion(section, evaluationQuestion);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @DeleteMapping(value = "evaluation-schemas/{code}/evaluation-sections/{sectionCode}/evaluation-question/{questionCode}")
    public ResponseEntity<ApplicationSuccess> removeEvaluationQuestion
            (@PathVariable String code, @PathVariable String sectionCode, @PathVariable String questionCode) {
        DexEvaluationSchema schema = enrollmentService.findEvaluationSchemaByCode(code);
        DexEvaluationSection section = enrollmentService.findEvaluationSectionByCode(sectionCode);
        DexEvaluationQuestion question = enrollmentService.findEvaluationQuestionByCode(questionCode);
        enrollmentService.removeEvaluationQuestion(section, question);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
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

    @DeleteMapping(value = "/schema-responses/{code}/section-responses/{sectionResponseCode}/question-responses/{questionResponseCode}")
    public ResponseEntity<ApplicationSuccess> removeEvaluationQuestionResponse
            (@PathVariable String code, @PathVariable String sectionResponseCode, @PathVariable String questionReponseCode) {
        DexEvaluation evaluation = enrollmentService.findEvaluationByCode(code);
        DexEvaluationSectionResponse sectionResponse = enrollmentService.findSectionResponseByCode(sectionResponseCode);
        DexEvaluationQuestionResponse questionResponse = enrollmentService.findQuestionResponseByCode(questionReponseCode);
        enrollmentService.deleteQuestionResponse(sectionResponse, questionResponse);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    //==============================================================================================
    // ENROLLMENT
    //==============================================================================================

    @GetMapping(value = "/enrollments", params = {"page", "filter"})
    public ResponseEntity<EnrollmentResult> findPagedEnrollments(@RequestParam Integer page, @RequestParam String filter) {
        Integer count = enrollmentService.countEnrollment(filter);
        List<DexEnrollment> enrollments = enrollmentService.findEnrollments(filter, (page - 1) * LIMIT, LIMIT);
        return new ResponseEntity<EnrollmentResult>(
                new EnrollmentResult(
                        enrollmentTransformer.toEnrollmentVos(enrollments),
                        count
                ), HttpStatus.OK);
    }

    @GetMapping(value = "/enrollments")
    public ResponseEntity<List<Enrollment>> findAllEnrollments() {
        return new ResponseEntity<List<Enrollment>>(enrollmentTransformer.toEnrollmentVos(
                enrollmentService.findAllEnrollments(0, Integer.MAX_VALUE)), HttpStatus.OK);
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
                enrollmentService.findEnrollmentByChild(child)), HttpStatus.OK);

    }

    @PostMapping(value = "/enrollments")
    public ResponseEntity<ApplicationSuccess> saveEnrollment(@RequestBody Enrollment vo) throws Exception {
        DexEnrollment enrollment = new DexEnrollmentImpl();
        enrollment.setPaid(vo.getPaid());
        enrollment.setBillNo(vo.getBillNo());
        enrollment.setDescription(vo.getDescription());
        enrollmentService.save(enrollment);
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

    @GetMapping(value = "/consultations", params = {"page", "filter"})
    public ResponseEntity<ConsultationResult> findPagedConsultations(@RequestParam Integer page, @RequestParam String filter) {
        Integer count = enrollmentService.countConsultation(filter);
        List<DexConsultation> consultations = enrollmentService.findConsultations(filter, (page - 1) * LIMIT, LIMIT);
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

    @PutMapping(value = "/consultations/{code}")
    public ResponseEntity<ApplicationSuccess> updateConsultation(@PathVariable String code,
                                                                 @RequestBody Consultation vo) {
        DexConsultation consultation = enrollmentService.findConsultationByCode(code);
        enrollmentService.updateConsultation(consultation);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/consultations/{code}")
    public ResponseEntity<ApplicationSuccess> removeConsultation(@PathVariable String code) {
        DexConsultation consultation = enrollmentService.findConsultationByCode(code);
        enrollmentService.removeConsultation(consultation);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }



    //==============================================================================================
    // COUPON
    //==============================================================================================

    @GetMapping(value = "/coupons", params = {"page", "filter"})
    public ResponseEntity<CouponResult> findPagedCoupons(@RequestParam Integer page, @RequestParam String filter) {
        Integer count = enrollmentService.countCoupon(filter);
        List<DexCoupon> coupons = enrollmentService.findCoupons(filter, (page - 1) * LIMIT, LIMIT);
        List<Coupon> vos = enrollmentTransformer.toCouponVos(coupons);
        return ResponseEntity.ok(new CouponResult(vos, count));
    }

    @GetMapping(value = "/coupons/{code}")
    public ResponseEntity<Coupon> findCouponByCode(@PathVariable String code) {
        DexCoupon coupon = enrollmentService.findCouponByCode(code);
        Coupon vos = enrollmentTransformer.toCouponVo(coupon);
        return ResponseEntity.ok(vos);
    }

    @PutMapping(value = "/coupons/{code}")
    public ResponseEntity<ApplicationSuccess> updateCoupon(@PathVariable String code, @RequestBody Coupon vo) {
        DexCoupon coupon = enrollmentService.findCouponByCode(code);
        coupon.setCode(vo.getCode());
        coupon.setDescription(vo.getDescription());
        coupon.setRedeemed(vo.getRedeemed());
        enrollmentService.updateCoupon(coupon);
        return ResponseEntity.ok(new ApplicationSuccess());
    }

    @DeleteMapping(value = "/coupons/{code}")
    public ResponseEntity<ApplicationSuccess> removeCoupon(@PathVariable String code) {
        DexCoupon coupon = enrollmentService.findCouponByCode(code);
        enrollmentService.removeCoupon(coupon);
        return ResponseEntity.ok(new ApplicationSuccess());
    }

    @PostMapping(value = "/coupons/generate", params={"count"})
    public ResponseEntity<ApplicationSuccess> generateCoupon(@RequestParam Integer count) {
        enrollmentService.generateCoupon(count);
        return ResponseEntity.ok(new ApplicationSuccess("Success","Coupons are generated" ));
    }

    // =============================================================================================
    // PRIVATE METHODS
    // =============================================================================================

    private void autoLogin() {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("root", "abc123");
        Authentication authed = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authed);
    }
}


