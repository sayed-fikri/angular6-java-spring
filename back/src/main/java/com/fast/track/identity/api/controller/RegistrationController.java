package com.irichment.identity.api.controller;

import com.irichment.account.business.service.AccountService;
import com.irichment.common.business.service.CommonService;
import com.irichment.core.api.ApplicationError;
import com.irichment.core.api.ApplicationSuccess;
import com.irichment.identity.api.vo.*;
import com.irichment.identity.business.service.IdentityService;
import com.irichment.identity.domain.model.*;
import com.irichment.security.business.integration.DexAutoLoginToken;
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

/**
 * @author canang technologies
 */
@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    private IdentityService identityService;
    private SecurityService securityService;
    private IdentityTransformer identityTransformer;
    private CommonService commonService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public RegistrationController(IdentityService identityService, SecurityService securityService,
                                  IdentityTransformer identityTransformer, CommonService commonService,
                                  AuthenticationManager authenticationManager) {
        this.identityService = identityService;
        this.securityService = securityService;
        this.identityTransformer = identityTransformer;
        this.commonService = commonService;
        this.authenticationManager = authenticationManager;
    }

    // =============================================================================================
    // CHECK USER
    // =============================================================================================



    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> findUsers() {
        return new ResponseEntity<List<User>>(identityTransformer.toUserVos(
                identityService.findUsers("%", 0, Integer.MAX_VALUE)), HttpStatus.OK);
    }

    @GetMapping(value = "/users/username",params ={"username"})
    public ResponseEntity<User> checkUsersByUsername(@RequestParam String username) throws Exception{
        autoLogin();
        return new ResponseEntity<User>(identityTransformer.toUserVo(
                identityService.findUserByUsername(username)), HttpStatus.OK);
    }

    @GetMapping(value = "/users/email", params = {"email"})
    public ResponseEntity<User> checkUsersByEmail(@RequestParam String email) throws Exception{
        autoLogin();
        return new ResponseEntity<User>(identityTransformer.toUserVo(
                identityService.findUserByEmail(email)), HttpStatus.OK);
    }


    // =============================================================================================
    // REGISTER GUARDIAN
    // =============================================================================================

    @PostMapping(value = "/register-user-guardian")
    public ResponseEntity<ApplicationSuccess> registerGuardian(@RequestBody User vo) {
        autoLogin();
        DexGuardian guardian = new DexGuardianImpl();
        guardian.setEmail(vo.getEmail());
        guardian.setName(vo.getRealName());
        DexUser user = new DexUserImpl();
        user.setName(vo.getName());
        user.setPassword(vo.getPassword());
        user.setRealName(vo.getRealName());
        user.setEmail(vo.getEmail());
        user.setEnabled(false);
        identityService.registerGuardian(guardian, user);
        return ResponseEntity.ok(new ApplicationSuccess());
    }

    // =============================================================================================
    // REGISTER THERAPIST
    // =============================================================================================

    @PostMapping(value = "/register-user-therapist")
    public ResponseEntity<ApplicationSuccess> registerTherapist(@RequestBody User vo) {
        autoLogin();
        DexTherapist therapist = new DexTherapistImpl();
        therapist.setEmail(vo.getEmail());
        therapist.setName(vo.getRealName());
        DexUser user = new DexUserImpl();
        user.setName(vo.getName());
        user.setPassword(vo.getPassword());
        user.setRealName(vo.getRealName());
        user.setEmail(vo.getEmail());
        user.setEnabled(false);
        identityService.registerTherapist(therapist, user);
        return ResponseEntity.ok(new ApplicationSuccess());
    }

    // =============================================================================================
    // REGISTER INSTRUCTOR
    // =============================================================================================

    private void autoLogin() {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("shasa.aziz", "abc123");
        Authentication authed = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authed);
    }

}
