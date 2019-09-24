package com.irichment.identity.api.controller;

import com.irichment.common.business.service.CommonService;
import com.irichment.core.api.ApplicationSuccess;
import com.irichment.enrollment.business.service.EnrollmentService;
import com.irichment.identity.api.vo.*;
import com.irichment.identity.business.service.IdentityService;
import com.irichment.identity.domain.dao.RecursiveGroupException;
import com.irichment.identity.domain.model.*;
import com.irichment.security.business.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.irichment.DexConstants.LIMIT;

/**
 * @author canang technologies
 */
@RestController
@RequestMapping("/api/guardian/identity")
public class GuardianIdentityController {

    private static final Logger LOG = LoggerFactory.getLogger(GuardianIdentityController.class);

    private IdentityService identityService;
    private SecurityService securityService;
    private IdentityTransformer identityTransformer;
    private EnrollmentService enrollmentService;
    private CommonService commonService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public GuardianIdentityController(IdentityService identityService, SecurityService securityService,
                                      IdentityTransformer identityTransformer, EnrollmentService enrollmentService,
                                      CommonService commonService, AuthenticationManager authenticationManager) {
        this.identityService = identityService;
        this.securityService = securityService;
        this.identityTransformer = identityTransformer;
        this.enrollmentService = enrollmentService;
        this.commonService = commonService;
        this.authenticationManager = authenticationManager;
    }

    // ================================================================================
    // GUARDIAN
    // ================================================================================


    @GetMapping(value = "/guardians")
    public ResponseEntity<Guardian> findGuardian() {
        return new ResponseEntity<Guardian>(identityTransformer.toGuardianVo(
                getCurrentGuardian()), HttpStatus.OK);
    }

    @GetMapping(value = "/guardians-profile")
    public ResponseEntity<GuardianProfile> findGuardianProfile() {
        Guardian guardian = identityTransformer.toGuardianVo(getCurrentGuardian());
        User user = identityTransformer.toUserVo(securityService.getCurrentUser());
        GuardianProfile guardianProfile = new GuardianProfile();
        guardianProfile.setGuardian(guardian);
        guardianProfile.setUser(user);
        return new ResponseEntity<GuardianProfile>(guardianProfile, HttpStatus.OK);
    }

    @PutMapping(value = "/guardians-user")
    public ResponseEntity<ApplicationSuccess> updateUserGuardian(@RequestBody User vo) {
        DexUser user = securityService.getCurrentUser();
        user.setUsername(vo.getName());
        user.setPassword(vo.getPassword());
        identityService.updateUser(user);
        return ResponseEntity.ok(new ApplicationSuccess());

    }

    @PutMapping(value = "/guardians/{code}")
    public ResponseEntity<ApplicationSuccess> updateGuardian(@PathVariable String code, @RequestBody Guardian vo) {
        DexGuardian guardian = identityService.findGuardianByCode(code);
        DexUser user = securityService.getCurrentUser();
        guardian.setIdentityNo(vo.getIdentityNo());
        guardian.setCode(vo.getCode());
        guardian.setName(vo.getName());
        guardian.setEmail(vo.getEmail());
        guardian.setMobile(vo.getMobile());
        guardian.setPhone(vo.getPhone());
        guardian.setAddress1(vo.getAddress1());
        guardian.setAddress2(vo.getAddress2());
        guardian.setAddress3(vo.getAddress3());
        guardian.setPostcode(vo.getPostcode());
        user.setEmail(vo.getEmail());
        identityService.updateGuardian(guardian);
        identityService.updateUser(user);
        return ResponseEntity.ok(new ApplicationSuccess());

    }

    // =============================================================================================
    // THERAPIST
    // =============================================================================================

    @GetMapping(value = "/therapists", params = {"page", "code"})
    public ResponseEntity<TherapistResult> findPagedTherapists(@RequestParam Integer page, @RequestParam String code) {
        Integer count = identityService.countTherapist(code);
        List<DexTherapist> therapists = identityService.findTherapistsByGuardian(code, (page - 1) * LIMIT, LIMIT);
        List<Therapist> vos = identityTransformer.toTherapistsVos(therapists);
        return ResponseEntity.ok(new TherapistResult(vos, count));
    }

    // =============================================================================================
    // PRIVATE METHODS
    // =============================================================================================

    private DexGuardian getCurrentGuardian () {
        DexUser user = securityService.getCurrentUser();
        return (DexGuardian) user.getActor();
    }

    private void autoLogin () {
        DexUser user = securityService.getCurrentUser();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());
        Authentication authed = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authed);
    }

}
