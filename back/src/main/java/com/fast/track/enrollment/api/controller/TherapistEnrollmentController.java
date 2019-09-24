package com.irichment.enrollment.api.controller;

import com.irichment.core.api.ApplicationSuccess;
import com.irichment.enrollment.api.vo.*;
import com.irichment.enrollment.business.service.EnrollmentService;
import com.irichment.enrollment.domain.model.*;
import com.irichment.identity.business.service.IdentityService;
import com.irichment.identity.domain.model.DexTherapist;
import com.irichment.identity.domain.model.DexUser;
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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import static com.irichment.DexConstants.LIMIT;

/**
 *
 */
@Transactional
@RestController
@RequestMapping("/api/therapist/enrollment")
public class TherapistEnrollmentController {

    private static final Logger LOG = LoggerFactory.getLogger(TherapistEnrollmentController.class);

    private EnrollmentService enrollmentService;
    private EnrollmentTransformer enrollmentTransformer;
    private LearningService learningService;
    private AuthenticationManager authenticationManager;
    private IdentityService identityService;
    private SecurityService securityService;
    private EntityManager entityManager;
    private AttachmentService attachmentService;

    @Autowired
    public TherapistEnrollmentController(EnrollmentService enrollmentService, EnrollmentTransformer enrollmentTransformer,
                                         LearningService learningService, AuthenticationManager authenticationManager,
                                         IdentityService identityService, SecurityService securityService,
                                         EntityManager entityManager, AttachmentService attachmentService) {
        this.enrollmentService = enrollmentService;
        this.enrollmentTransformer = enrollmentTransformer;
        this.authenticationManager = authenticationManager;
        this.learningService = learningService;
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
        Integer count = enrollmentService.countChildByTherapist(getCurrentTherapist());
        List<DexChild> children = enrollmentService.findChildByTherapist(getCurrentTherapist(), (page - 1) * LIMIT, LIMIT);
        List<Child> vos = enrollmentTransformer.toChildVos(children);
        return new ResponseEntity<ChildResult>(new ChildResult(vos, count), HttpStatus.OK);
    }

    @GetMapping(value = "/children/{code}")
    public ResponseEntity<Child> findChildByCode(@PathVariable String code) {
        return new ResponseEntity<Child>(enrollmentTransformer.toChildVo(
                enrollmentService.findChildByCode(code)), HttpStatus.OK);
    }

    //==============================================================================================
    // CONSULTATION
    //==============================================================================================

    @GetMapping(value = "/consultations", params = {"page", "filter"})
    public ResponseEntity<ConsultationResult> findPagedConsultations(@RequestParam Integer page, @RequestParam String filter) {
        DexTherapist therapist = getCurrentTherapist();
        Integer count = enrollmentService.countConsultationByTherapist(therapist);
        List<DexConsultation> consultations = enrollmentService.findConsultationByTherapist(therapist, (page - 1) * LIMIT, LIMIT);
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

    @GetMapping(value = "/consultations/children/{code}")
    public ResponseEntity<Consultation> findConsultationByChild(@PathVariable String code) {
        DexChild child = enrollmentService.findChildByCode(code);
        return new ResponseEntity<Consultation>(enrollmentTransformer.toConsultationVo(
                enrollmentService.findConsultationByChildAndTherapist(child,getCurrentTherapist())), HttpStatus.OK);

    }

    @PutMapping(value = "/consultations/{code}")
    public ResponseEntity<ApplicationSuccess> updateConsultation(@PathVariable String code,
                                                                 @RequestBody Consultation vo) {
        DexChild child = enrollmentService.findChildByCode(code);
        DexTherapist therapist = getCurrentTherapist();
        DexConsultation consultation = enrollmentService.findConsultationByChildAndTherapist(child,therapist);
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
    // COMMENT
    //==============================================================================================

    @GetMapping(value = "/consultations/{code}/comments", params = {"page", "filter"})
    public ResponseEntity<CommentResult> findPagedComments(@PathVariable String code, @RequestParam Integer page, @RequestParam String filter) {
        DexConsultation consultation = enrollmentService.findConsultationByCode(code);
        Integer count = enrollmentService.countComment(consultation);
        List<DexComment> comments = enrollmentService.findComments(consultation, (page - 1) * LIMIT, LIMIT);
        return new ResponseEntity<CommentResult>(
                new CommentResult(
                        enrollmentTransformer.toCommentVos(comments),
                        count
                ), HttpStatus.OK);
    }

    @GetMapping(value = "/consultations/comments/{code}")
    public ResponseEntity<Comment> findCommentByCode(@PathVariable String code) {
        return new ResponseEntity<Comment>(enrollmentTransformer.toCommentVo(
                enrollmentService.findCommentByCode(code)), HttpStatus.OK);

    }

    @PostMapping(value = "/consultations/{code}/comments")
    public ResponseEntity<ApplicationSuccess> saveComment(@PathVariable String code,@RequestBody Comment vo) {
        DexConsultation consultation = enrollmentService.findConsultationByCode(code);
        DexComment comment = new DexCommentImpl();
        comment.setComment(vo.getComment());
        enrollmentService.saveComment(consultation,comment);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @PutMapping(value = "/consultations/comments/{code}")
    public ResponseEntity<ApplicationSuccess> updateComment(@PathVariable String code,@RequestBody Comment vo) {
        DexComment comment = enrollmentService.findCommentByCode(code);
        comment.setComment(vo.getComment());
        enrollmentService.updateComment(comment);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/consultations/comments/{code}")
    public ResponseEntity<ApplicationSuccess> removeComment(@PathVariable String code) {
        DexComment comment = enrollmentService.findCommentByCode(code);
        enrollmentService.removeComment(comment);
        return new ResponseEntity<ApplicationSuccess>(new ApplicationSuccess(), HttpStatus.OK);
    }


    // =============================================================================================
    // Evaluation Log
    // =============================================================================================

    @GetMapping(value = "/evaluation-log")
    public ResponseEntity<EvaluationLogResult> findEvaluationLogs() {
        List<EvaluationLog> evaluationLogs = new ArrayList<>();
        List<DexConsultation> consultations = enrollmentService.findConsultationByTherapist(getCurrentTherapist());
        Integer count = enrollmentService.countConsultationByTherapist(getCurrentTherapist());
        for (DexConsultation consultation : consultations) {
            List<DexEvaluation> evaluations = enrollmentService.findEvaluationsByChild(consultation.getChild());
            for(DexEvaluation evaluation : evaluations){
                EvaluationLog evaluationLog = new EvaluationLog();
                evaluationLog.setChild(enrollmentTransformer.toEvaluationVo(evaluation).getChild());
                evaluationLog.setEvaluation(enrollmentTransformer.toEvaluationVo(evaluation));
                evaluationLogs.add(evaluationLog);
            }
        }

        return ResponseEntity.ok(new EvaluationLogResult(evaluationLogs,count));
    }

    // =============================================================================================
    // PRIVATE METHODS
    // =============================================================================================

    private DexTherapist getCurrentTherapist() {
        DexUser user = securityService.getCurrentUser();
        return (DexTherapist) user.getActor();
    }

    private void autoLogin() {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("root", "abc123");
        Authentication authed = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authed);
    }
}


