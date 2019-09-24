package com.irichment.identity.api.controller;

import com.irichment.core.api.ApplicationSuccess;
import com.irichment.enrollment.api.controller.EnrollmentTransformer;
import com.irichment.enrollment.business.service.EnrollmentService;
import com.irichment.identity.api.vo.Therapist;
import com.irichment.identity.api.vo.TherapistProfile;
import com.irichment.identity.api.vo.User;
import com.irichment.identity.business.service.IdentityService;
import com.irichment.identity.domain.model.DexTherapist;
import com.irichment.identity.domain.model.DexUser;
import com.irichment.learning.api.controller.LearningTransformer;
import com.irichment.learning.business.service.LearningService;
import com.irichment.profile.api.controller.ProfileTransformer;
import com.irichment.profile.business.service.ProfileService;
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

@Transactional
@RestController
@RequestMapping("/api/therapist/identity")
public class TherapistIdentityController {

    private static final Logger LOG = LoggerFactory.getLogger(TherapistIdentityController.class);

    private EnrollmentService enrollmentService;
    private IdentityTransformer identityTransformer;
    private EnrollmentTransformer enrollmentTransformer;
    private LearningService learningService;
    private LearningTransformer learningTransformer;
    private ProfileTransformer profileTransformer;
    private ProfileService profileService;
    private AuthenticationManager authenticationManager;
    private IdentityService identityService;
    private SecurityService securityService;
    private EntityManager entityManager;
    private AttachmentService attachmentService;


    @Autowired
    public TherapistIdentityController(EnrollmentService enrollmentService, EnrollmentTransformer enrollmentTransformer,
                                       LearningService learningService, LearningTransformer learningTransformer,
                                       AuthenticationManager authenticationManager, ProfileTransformer profileTransformer,
                                       IdentityService identityService, IdentityTransformer identityTransformer,
                                       ProfileService profileService,
                                       SecurityService securityService, EntityManager entityManager, AttachmentService attachmentService) {

        this.enrollmentService = enrollmentService;
        this.enrollmentTransformer = enrollmentTransformer;
        this.authenticationManager = authenticationManager;
        this.learningService = learningService;
        this.learningTransformer = learningTransformer;
        this.identityService = identityService;
        this.securityService = securityService;
        this.profileService = profileService;
        this.entityManager = entityManager;
        this.attachmentService = attachmentService;
        this.identityTransformer = identityTransformer;
        this.profileTransformer = profileTransformer;
    }

//    ================================================================================
//    THERAPIST
//    ================================================================================


    @GetMapping(value = "/therapists")
    public ResponseEntity<Therapist> findTherapist() {
        return new ResponseEntity<Therapist>(identityTransformer.toTherapistVo(
                getCurrentTherapist()), HttpStatus.OK);
    }

    @GetMapping(value = "/therapists-profile")
    public ResponseEntity<TherapistProfile> findTherapistProfile() {
        Therapist therapist = identityTransformer.toTherapistVo(getCurrentTherapist());
        User user = identityTransformer.toUserVo(securityService.getCurrentUser());
        TherapistProfile therapistProfile = new TherapistProfile();
        therapistProfile.setTherapist(therapist);
        therapistProfile.setUser(user);
        return new ResponseEntity<TherapistProfile>(therapistProfile, HttpStatus.OK);
    }

    @PutMapping(value = "/therapists-user")
    public ResponseEntity<ApplicationSuccess> updateUserTherapist(@RequestBody User vo) {
        DexUser user = securityService.getCurrentUser();
        user.setUsername(vo.getName());
        user.setPassword(vo.getPassword());
        identityService.updateUser(user);
        return ResponseEntity.ok(new ApplicationSuccess());

    }

    @PutMapping(value = "/therapists/{code}")
    public ResponseEntity<ApplicationSuccess> updateProfileTherapist(@PathVariable String code, @RequestBody Therapist vo) {
        DexTherapist therapist = identityService.findTherapistByCode(code);
        DexUser user = securityService.getCurrentUser();
        therapist.setIdentityNo(vo.getIdentityNo());
        therapist.setCode(vo.getCode());
        therapist.setName(vo.getName());
        therapist.setHospital(vo.getHospital());
        therapist.setProfession(vo.getProfession());
        therapist.setEmail(vo.getEmail());
        therapist.setIdentityNo(vo.getIdentityNo());
        therapist.setMobile(vo.getMobile());
        therapist.setPhone(vo.getPhone());
        therapist.setAddress1(vo.getAddress1());
        therapist.setAddress2(vo.getAddress2());
        therapist.setAddress3(vo.getAddress3());
        therapist.setPostcode(vo.getPostcode());
        user.setEmail(vo.getEmail());
        identityService.updateTherapist(therapist);
        identityService.updateUser(user);
        return ResponseEntity.ok(new ApplicationSuccess());

    }
    // =============================================================================================
    // PRIVATE METHODS
    // =============================================================================================

    private DexTherapist getCurrentTherapist () {
        DexUser user = securityService.getCurrentUser();
        return (DexTherapist) user.getActor();
    }

    private void autoLogin () {
        DexUser user = securityService.getCurrentUser();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());
        Authentication authed = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authed);
    }
}

