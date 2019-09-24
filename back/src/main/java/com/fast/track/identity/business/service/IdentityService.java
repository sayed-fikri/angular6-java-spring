package com.irichment.identity.business.service;

import com.irichment.enrollment.domain.model.DexChild;
import com.irichment.identity.domain.dao.RecursiveGroupException;
import com.irichment.identity.domain.model.*;
import com.irichment.profile.domain.model.DexGuardianProfile;

import java.util.List;
import java.util.Set;

/**
 * @author canang technologies
 */
public interface IdentityService {

    // =============================================================================================
    // PRINCIPAL
    // =============================================================================================

    DexPrincipal findPrincipalById(Long id);

    DexPrincipal findPrincipalByName(String name);

    List<DexPrincipal> findPrincipals(String filter, Integer offset, Integer limit);

    Set<String> findSids(DexPrincipal principal);

    Integer countPrincipal(String filter);

    void addPrincipalRole(DexPrincipal principal, DexPrincipalRole principalRole);

    void deletePrincipalRole(DexPrincipal principal, DexPrincipalRole principalRole);

    // =============================================================================================
    // USER
    // =============================================================================================

    DexUser findUserByEmail(String email);

    DexUser findUserByUsername(String username);

    DexUser findUserById(Long id);

    List<DexUser> findUsers(Integer offset, Integer limit);

    List<DexUser> findUsers(String filter, Integer offset, Integer limit);

    Integer countUser();

    Integer countUser(String filter);

    boolean isUserExists(String username);

    void saveUser(DexUser user);

    void updateUser(DexUser user);

    void removeUser(DexUser user);

    void changePassword(DexUser user, String newPassword);

    // =============================================================================================
    // GROUP
    // =============================================================================================

    DexGroup getRootGroup();

    DexGroup findGroupByName(String name);

    DexGroup findOrCreateGroupByName(String name);

    DexGroup findGroupById(Long id);

    List<DexGroup> findGroups(Integer offset, Integer limit);

    List<DexGroup> findImmediateGroups(DexPrincipal principal);

    List<DexGroup> findImmediateGroups(DexPrincipal principal, Integer offset, Integer limit);

    Set<DexGroup> findEffectiveGroups(DexPrincipal principal);

    Set<String> findEffectiveGroupsAsString(DexPrincipal principal);

    List<DexGroup> findAvailableUserGroups(DexUser user);

    List<DexPrincipal> findAvailableGroupMembers(DexGroup group);

    List<DexGroupMember> findGroupMembersAsGroupMember(DexGroup group);

    List<DexPrincipal> findGroupMembers(DexGroup group);

    List<DexPrincipal> findGroupMembers(DexGroup group, Integer offset, Integer limit);

    Integer countGroup();

    Integer countGroupMember(DexGroup group);

    boolean isGroupExists(String name);

    boolean hasMembership(DexGroup group, DexPrincipal principal);

    DexGroup createGroupWithRole(String groupName, DexRoleType... types);

    void saveGroup(DexGroup group);

    void updateGroup(DexGroup group);

    void removeGroup(DexGroup group);

    void addGroupMember(DexGroup group, DexPrincipal principal) throws RecursiveGroupException;

    void deleteGroupMember(DexGroup group, DexPrincipal principal);

    // =============================================================================================
    // GUARDIAN
    // =============================================================================================

    DexGuardian findGuardianById(Long id);

    DexGuardian findGuardianByCode(String code);

    DexGuardian findGuardianByIdentityNo(String identityNo);

    DexGuardian findGuardianByEmail(String email);

    List<DexGuardian> findGuardians(String filter, Integer offset, Integer limit);

    List<DexGuardian> findGuardians(Integer offset, Integer limit);

    Integer countGuardian(String filter);

    Integer countGuardian();

    void saveGuardian(DexGuardian Guardian);

    void updateGuardian(DexGuardian Guardian);

    void removeGuardian(DexGuardian Guardian);

    // =============================================================================================
    // THERAPIST
    // =============================================================================================

    DexTherapist findTherapistById(Long id);

    DexTherapist findTherapistByCode(String code);

    DexTherapist findTherapistByIdentityNo(String identityNo);

    DexTherapist findTherapistByEmail(String email);

    List<DexTherapist> findTherapists(String filter, Integer offset, Integer limit);

    List<DexTherapist> findTherapistsByGuardian(String code, Integer offset, Integer limit);

    List<DexTherapist> findTherapists(Integer offset, Integer limit);

    List<DexGuardian> findGuardians(DexTherapist therapist, Integer offset, Integer limit);

    Integer countTherapist(String filter);

    Integer countTherapistByChild(DexChild child);

    Integer countGuardian(DexTherapist therapist);

    void saveTherapist(DexTherapist Therapist);

    void updateTherapist(DexTherapist Therapist);

    void removeTherapist(DexTherapist Therapist);

    // =============================================================================================
    // STAFF
    // =============================================================================================
    DexStaff findStaffById(Long staffId);

    DexStaff findStaffByCode(String code);

    DexStaff findStaffByIdentityNo(String identityNo);

    DexStaff findStaffByEmail(String email);

    DexStaff findStaffByActorType(DexActorType actorType);

    List<DexStaff> findStaffs(String filter, Integer offset, Integer limit);

    List<DexStaff> findStaffs(Integer offset, Integer limit);

    Integer countStaff(String filter);

    Integer countStaff();

    void saveStaff(DexStaff Staff);

    void updateStaff(DexStaff Staff);

    void removeStaff(DexStaff Staff);

    // =============================================================================================
    // REGISTER
    // =============================================================================================
    void registerGuardian(DexGuardian guardian, DexUser user);

    void registerTherapist(DexTherapist therapist, DexUser user);

}
