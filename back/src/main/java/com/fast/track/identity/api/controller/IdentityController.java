package com.irichment.identity.api.controller;

import com.irichment.common.business.service.CommonService;
import com.irichment.core.api.ApplicationSuccess;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.irichment.DexConstants.LIMIT;

/**
 * @author canang technologies
 */
@RestController
@RequestMapping("/api/identity")
public class IdentityController {

    private static final Logger LOG = LoggerFactory.getLogger(IdentityController.class);

    private IdentityService identityService;
    private SecurityService securityService;
    private IdentityTransformer identityTransformer;
    private CommonService commonService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public IdentityController(IdentityService identityService, SecurityService securityService,
                              IdentityTransformer identityTransformer, CommonService commonService,
                              AuthenticationManager authenticationManager) {
        this.identityService = identityService;
        this.securityService = securityService;
        this.identityTransformer = identityTransformer;
        this.commonService = commonService;
        this.authenticationManager = authenticationManager;
    }

    // =============================================================================================
    // AUTHENTICATED USER AND ACTOR
    // =============================================================================================

    @GetMapping(value = "/authenticated-user")
    public ResponseEntity<AuthenticatedUser> findAuthenticatedUser() {
        DexUser currentUser = securityService.getCurrentUser();
        return new ResponseEntity<AuthenticatedUser>(identityTransformer.toAuthenticatedUserVo(currentUser), HttpStatus.OK);
    }

    //==============================================================================================
    // PRINCIPAL
    //==============================================================================================

    @GetMapping(value = "/principals")
    public ResponseEntity<List<Principal>> findPrincipals() {
        List<DexPrincipal> principals = identityService.findPrincipals("%", 0, Integer.MAX_VALUE);
        List<Principal> vos = identityTransformer.toPrincipalVos(principals);
        return new ResponseEntity<List<Principal>>(vos, HttpStatus.OK);
    }

    @GetMapping(value = "/principal/{name}")
    public ResponseEntity<Principal> findPrincipalByName(@PathVariable String name) {
        DexPrincipal principal = identityService.findPrincipalByName(name);
        Principal vo = identityTransformer.toPrincipalVo(principal);
        return new ResponseEntity<Principal>(vo, HttpStatus.OK);
    }

    //==============================================================================================
    // USER
    //==============================================================================================

    @GetMapping(value = "/users", params = {"page"})
    public ResponseEntity<UserResult> findPagedUsers(@RequestParam Integer page) {
        Integer count = identityService.countUser("%");
        List<DexUser> users = identityService.findUsers("%", (page - 1) * LIMIT, LIMIT);
        List<User> vos = identityTransformer.toUserVos(users);
        return ResponseEntity.ok(new UserResult(vos, count));
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> findUsers() {
        return new ResponseEntity<List<User>>(identityTransformer.toUserVos(
                identityService.findUsers("%", 0, Integer.MAX_VALUE)), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{username:.+}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) {
        return new ResponseEntity<User>(identityTransformer.toUserVo(
                identityService.findUserByUsername(username)), HttpStatus.OK);
    }


    @PutMapping(value = "/users/{username:.+}")
    public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody User vo) {
        DexUser user = identityService.findUserByUsername(username);
        user.setName(vo.getName());
        user.setRealName(vo.getRealName());
        user.setEmail(vo.getEmail());
        identityService.updateUser(user);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{username:.+}")
    public ResponseEntity<String> removeUser(@PathVariable String username) {
        DexUser user = identityService.findUserByUsername(username);
        identityService.removeUser(user);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PutMapping(value = "/users/password/{username:.+}")
    public ResponseEntity<String> changePassword(@PathVariable String username, @RequestBody User vo) {
        DexUser user = identityService.findUserByUsername(username);
        user.setPassword(vo.getPassword());
        identityService.updateUser(user);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    //==============================================================================================
    // GROUP
    //==============================================================================================

    @GetMapping(value = "/groups", params = {"page"})
    public ResponseEntity<GroupResult> findPagedGroups(@RequestParam Integer page) {
        Integer count = identityService.countGroup();
        List<DexGroup> groups = identityService.findGroups((page - 1) * LIMIT, LIMIT);
        return new ResponseEntity<GroupResult>(
                new GroupResult(
                        identityTransformer.toGroupVos(groups),
                        count
                ), HttpStatus.OK);
    }

    @GetMapping(value = "/groups")
    public ResponseEntity<List<Group>> findGroups() {
        return new ResponseEntity<List<Group>>(identityTransformer.toGroupVos(
                identityService.findGroups(0, Integer.MAX_VALUE)), HttpStatus.OK);
    }

    @GetMapping(value = "/groups/{name}")
    public ResponseEntity<Group> findGroupByName(@PathVariable String name) {
        return new ResponseEntity<Group>(identityTransformer.toGroupVo(
                identityService.findGroupByName(name)), HttpStatus.OK);
    }

    @PostMapping(value = "/groups")
    public ResponseEntity<String> saveGroup(@RequestBody Group vo) {
        DexGroup group = new DexGroupImpl();
        group.setName(vo.getName());
        identityService.saveGroup(group);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PutMapping(value = "/groups/{name}")
    public ResponseEntity<String> updateGroup(@PathVariable String name, @RequestBody Group vo) {
        DexGroup group = identityService.findGroupByName(name);
        group.setName(vo.getName());
        identityService.updateGroup(group);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @DeleteMapping(value = "/groups/{name}")
    public ResponseEntity<String> removeGroup(@PathVariable String name) {
        DexGroup group = identityService.findGroupByName(name);
        identityService.removeGroup(group);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @GetMapping(value = "/groups/{name}/group-members")
    public ResponseEntity<List<GroupMember>> findGroupMembers(@PathVariable String name) {
        DexGroup groupByName = identityService.findGroupByName(name);
        return new ResponseEntity<List<GroupMember>>(
                identityTransformer.toGroupMemberVos(identityService.findGroupMembersAsGroupMember(groupByName)),
                HttpStatus.OK);
    }

    @PostMapping(value = "/groups/{name}/group-members")
    public ResponseEntity<String> addGroupMember(@PathVariable String name, @RequestBody GroupMember member) throws RecursiveGroupException {
        DexGroup group = identityService.findGroupByName(name);
        DexPrincipal principal = identityService.findPrincipalById(member.getPrincipal().getId());
        identityService.addGroupMember(group, principal);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @DeleteMapping(value = "/groups/{name}/group-member/{memberName:.+}")
    public ResponseEntity<String> deleteGroupMember(@PathVariable String name, @PathVariable String memberName) {
        DexGroup group = identityService.findGroupByName(name);
        DexPrincipal principal = identityService.findPrincipalByName(memberName);
        identityService.deleteGroupMember(group, principal);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    // =============================================================================================
    // GUARDIAN
    // =============================================================================================

    @GetMapping(value = "/guardians", params = {"page", "filter"})
    public ResponseEntity<GuardianResult> findPagedGuardians(@RequestParam Integer page, @RequestParam String filter) {
        Integer count = identityService.countGuardian(filter);
        List<DexGuardian> guardians = identityService.findGuardians(filter, (page - 1) * LIMIT, LIMIT);
        List<Guardian> vos = identityTransformer.toGuardiansVos(guardians);
        return ResponseEntity.ok(new GuardianResult(vos, count));
    }


    @GetMapping(value = "/guardians")
    public ResponseEntity<List<Guardian>> findGuardians() {
        List<DexGuardian> guardians = identityService.findGuardians(0, Integer.MAX_VALUE);
        List<Guardian> vos = identityTransformer.toGuardiansVos(guardians);
        return ResponseEntity.ok(vos);
    }

    @GetMapping(value = "/guardians/{code}")
    public ResponseEntity<Guardian> findGuardianByCode(@PathVariable String code) {
        DexGuardian guardian = identityService.findGuardianByCode(code);
        Guardian vo = identityTransformer.toGuardianVo(guardian);
        return ResponseEntity.ok(vo);
    }

    @PostMapping(value = "/guardians")
    public ResponseEntity<ApplicationSuccess> saveGuardian(@RequestBody User vo) {
        DexGuardian guardian = new DexGuardianImpl();
        guardian.setEmail(vo.getEmail());
        guardian.setName(vo.getRealName());
        DexUser user = new DexUserImpl();
        user.setName(vo.getName());
        user.setPassword(vo.getPassword());
        user.setRealName(vo.getRealName());
        user.setEmail(vo.getEmail());
        //todo email verification
        user.setEnabled(true);
        identityService.registerGuardian(guardian, user);
        return ResponseEntity.ok(new ApplicationSuccess());
    }

    @PutMapping(value = "/guardians/{code}")
    public ResponseEntity<ApplicationSuccess> updateGuardian(@PathVariable String code, @RequestBody Guardian vo) {
        DexGuardian guardian = identityService.findGuardianByCode(code);
        guardian.setIdentityNo(vo.getIdentityNo());
        guardian.setCode(vo.getCode());
        guardian.setName(vo.getName());
        guardian.setEmail(vo.getEmail());
        guardian.setIdentityNo(vo.getIdentityNo());
        guardian.setMobile(vo.getMobile());
        guardian.setPhone(vo.getPhone());
        guardian.setAddress1(vo.getAddress1());
        guardian.setAddress2(vo.getAddress2());
        guardian.setAddress3(vo.getAddress3());
        guardian.setPostcode(vo.getPostcode());
        identityService.updateGuardian(guardian);
        return ResponseEntity.ok(new ApplicationSuccess());
    }

    @DeleteMapping(value = "/guardians/{code}")
    public ResponseEntity<ApplicationSuccess> removeGuardian(@PathVariable String code) {
        DexGuardian guardian = identityService.findGuardianByCode(code);
        identityService.removeGuardian(guardian);
        return ResponseEntity.ok(new ApplicationSuccess());
    }

    // =============================================================================================
    // THERAPIST
    // =============================================================================================

    @GetMapping(value = "/therapists", params = {"page", "filter"})
    public ResponseEntity<TherapistResult> findPagedTherapists(@RequestParam Integer page, @RequestParam String filter) {
        Integer count = identityService.countTherapist(filter);
        List<DexTherapist> therapists = identityService.findTherapists(filter, (page - 1) * LIMIT, LIMIT);
        List<Therapist> vos = identityTransformer.toTherapistsVos(therapists);
        return ResponseEntity.ok(new TherapistResult(vos, count));
    }


    @GetMapping(value = "/therapists")
    public ResponseEntity<List<Therapist>> findTherapists() {
        List<DexTherapist> therapists = identityService.findTherapists(0, Integer.MAX_VALUE);
        List<Therapist> vos = identityTransformer.toTherapistsVos(therapists);
        return ResponseEntity.ok(vos);
    }

    @GetMapping(value = "/therapists/{code}")
    public ResponseEntity<Therapist> findTherapistByCode(@PathVariable String code) {
        DexTherapist therapist = identityService.findTherapistByCode(code);
        Therapist vo = identityTransformer.toTherapistVo(therapist);
        return ResponseEntity.ok(vo);
    }

    @PostMapping(value = "/therapists")
    public ResponseEntity<ApplicationSuccess> saveTherapist(@RequestBody User vo) {
        DexTherapist therapist = new DexTherapistImpl();
        therapist.setEmail(vo.getEmail());
        therapist.setName(vo.getRealName());
        DexUser user = new DexUserImpl();
        user.setName(vo.getName());
        user.setPassword(vo.getPassword());
        user.setRealName(vo.getRealName());
        user.setEmail(vo.getEmail());
        user.setEnabled(true);
        identityService.registerTherapist(therapist, user);
        return ResponseEntity.ok(new ApplicationSuccess());
    }

    @PutMapping(value = "/therapists/{code}")
    public ResponseEntity<ApplicationSuccess> updateTherapist(@PathVariable String code, @RequestBody Therapist vo) {
        DexTherapist therapist = identityService.findTherapistByCode(code);
        therapist.setIdentityNo(vo.getIdentityNo());
        therapist.setCode(vo.getCode());
        therapist.setName(vo.getName());
        therapist.setEmail(vo.getEmail());
        therapist.setIdentityNo(vo.getIdentityNo());
        therapist.setMobile(vo.getMobile());
        therapist.setPhone(vo.getPhone());
        therapist.setAddress1(vo.getAddress1());
        therapist.setAddress2(vo.getAddress2());
        therapist.setAddress3(vo.getAddress3());
        therapist.setPostcode(vo.getPostcode());
        identityService.updateTherapist(therapist);
        return ResponseEntity.ok(new ApplicationSuccess());
    }

    @DeleteMapping(value = "/therapists/{code}")
    public ResponseEntity<ApplicationSuccess> removeTherapist(@PathVariable String code) {
        DexTherapist therapist = identityService.findTherapistByCode(code);
        identityService.removeTherapist(therapist);
        return ResponseEntity.ok(new ApplicationSuccess());
    }

}
