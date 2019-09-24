package com.irichment.identity.api.controller;


import com.irichment.common.api.controller.CommonTransformer;
import com.irichment.identity.api.vo.*;
import com.irichment.identity.business.service.IdentityService;
import com.irichment.identity.domain.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author canang technologies
 */
@Component("identityTransformer")
public class IdentityTransformer {

    private static final Logger LOG = LoggerFactory.getLogger(IdentityTransformer.class);

    private CommonTransformer commonTransformer;
    private IdentityService identityService;

    @Autowired
    public IdentityTransformer(CommonTransformer commonTransformer,
                               IdentityService identityService) {
        this.commonTransformer = commonTransformer;
        this.identityService = identityService;
    }


    //==============================================================================================
    // AUTHENTICATED USER
    //==============================================================================================

    public AuthenticatedUser toAuthenticatedUserVo(DexUser e) {
        AuthenticatedUser vo = new AuthenticatedUser();
        vo.setName(e.getName());
        vo.setUsername(e.getRealName());
        vo.setActor(toActorVo(e.getActor()));

        // todo:
        Set<DexPrincipalRole> roles = e.getRoles();
        for (DexPrincipalRole role : roles) {
            vo.setRoleType(RoleType.get(role.getRole().ordinal()));
        }

        return vo;
    }

    //==============================================================================================
    // PRINCIPAL
    //==============================================================================================

    public Principal toPrincipalVo(DexPrincipal e) {
        if (null == e) return null;
        Principal vo = new Principal();
        vo.setId(e.getId());
        vo.setName(e.getName());
        vo.setPrincipalType(PrincipalType.get(e.getPrincipalType().ordinal()));
        return vo;
    }

    public List<Principal> toPrincipalVos(List<DexPrincipal> e) {
        List<Principal> vos = e.stream()
                .map((e1) -> toPrincipalVo(e1))
                .collect(Collectors.toList());
        return vos;
    }

    //==============================================================================================
    // USER
    //==============================================================================================

    public User toUserVo(DexUser e) {
        if (null == e) return null;
        User vo = new User();
        vo.setId(e.getId());
        vo.setName(e.getName());
        vo.setRealName(e.getRealName());
        vo.setEmail(e.getEmail());
        vo.setPrincipalType(PrincipalType.get(e.getPrincipalType().ordinal()));
        vo.setPassword(e.getPassword());
        return vo;
    }

    public List<User> toUserVos(List<DexUser> e) {
        List<User> vos = e.stream()
                .map((e1) -> toUserVo(e1))
                .collect(Collectors.toList());
        return vos;
    }

    //==============================================================================================
    // GROUP
    //==============================================================================================

    public Group toGroupVo(DexGroup e) {
        if (null == e) return null;
        Group vo = new Group();
        vo.setId(e.getId());
        vo.setName(e.getName());
        vo.setMemberCount(identityService.countGroupMember(e));
        return vo;
    }

    public List<Group> toGroupVos(List<DexGroup> e) {
        List<Group> vos = e.stream()
                .map((e1) -> toGroupVo(e1))
                .collect(Collectors.toList());
        return vos;
    }

    //==============================================================================================
    // GROUP MEMBER
    //==============================================================================================

    public GroupMember toGroupMemberVo(DexGroupMember e) {
        if (null == e) return null;
        GroupMember vo = new GroupMember();
        vo.setId(e.getId());
        vo.setPrincipal(toPrincipalVo(e.getPrincipal()));
        return vo;
    }

    public List<GroupMember> toGroupMemberVos(List<DexGroupMember> e) {
        List<GroupMember> vos = e.stream()
                .map((e1) -> toGroupMemberVo(e1))
                .collect(Collectors.toList());
        return vos;
    }

    //==============================================================================================
    // GUARDIAN
    //==============================================================================================

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

    public List<Guardian> toGuardiansVos(List<DexGuardian> e) {
        List<Guardian> vos = e.stream()
                .map((e1) -> toGuardianVo(e1))
                .collect(Collectors.toList());
        return vos;
    }

    //==============================================================================================
    // THERAPIST
    //==============================================================================================

    public Therapist toTherapistVo(DexTherapist e) {
        if (null == e) return null;
        Therapist vo = new Therapist();
        vo.setId(e.getId());
        vo.setCode(e.getCode());
        vo.setActorType(ActorType.get(e.getActorType().ordinal()));
        vo.setIdentityNo(e.getIdentityNo());
        vo.setName(e.getName());
        vo.setHospital(e.getHospital());
        vo.setProfession(e.getProfession());
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

    public List<Therapist> toTherapistsVos(List<DexTherapist> e) {
        List<Therapist> vos = e.stream()
                .map((e1) -> toTherapistVo(e1))
                .collect(Collectors.toList());
        return vos;
    }

    //==============================================================================================
    // STAFF
    //==============================================================================================

    public Staff toStaffVo(DexStaff e) {
        if (null == e) return null;
        Staff vo = new Staff();
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

    public List<Staff> toStaffsVos(List<DexStaff> e) {
        List<Staff> vos = e.stream()
                .map((e1) -> toStaffVo(e1))
                .collect(Collectors.toList());
        return vos;
    }

    //==============================================================================================
    // INSTRUCTOR
    //==============================================================================================

    public Instructor toInstructorVo(DexInstructor e) {
        if (null == e) return null;
        Instructor vo = new Instructor();
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

    public List<Instructor> toInstructorsVos(List<DexInstructor> e) {
        List<Instructor> vos = e.stream()
                .map((e1) -> toInstructorVo(e1))
                .collect(Collectors.toList());
        return vos;
    }

    //==============================================================================================
    // ACTOR
    //==============================================================================================

    private Actor toActorVo(DexActor e) {
        Actor actor1 = new Actor();
        actor1.setId(e.getId());
        actor1.setCode(e.getCode());
        actor1.setName(e.getName());
        actor1.setIdentityNo(e.getIdentityNo());
        actor1.setEmail(e.getEmail());
        actor1.setActorType(ActorType.get(e.getActorType().ordinal()));
        return actor1;
    }
}
