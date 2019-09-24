package com.irichment.identity.business.service;

import com.irichment.DexConstants;
import com.irichment.account.business.service.AccountService;
import com.irichment.enrollment.domain.model.DexChild;
import com.irichment.identity.domain.dao.*;
import com.irichment.identity.domain.model.*;
import com.irichment.profile.domain.dao.DexGuardianProfileDao;
import com.irichment.profile.domain.model.DexGuardianProfile;
import com.irichment.security.business.service.SecurityService;

import com.irichment.system.business.service.SystemService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author canang technologies
 */
@Transactional
@Service("DexIdentityService")
public class IdentityServiceImpl implements IdentityService {

    private static final String GROUP_ROOT = "GRP_ADMN";
    private static final Logger LOG = LoggerFactory.getLogger(IdentityServiceImpl.class);

    private EntityManager entityManager;
    private SecurityService securityService;
    private SystemService systemService;
    private DexPrincipalDao principalDao;
    private DexUserDao userDao;
    private DexActorDao actorDao;
    private DexGroupDao groupDao;
    private DexStaffDao staffDao;
    private DexGuardianDao guardianDao;
    private DexTherapistDao therapistDao;
    private DexGuardianProfileDao guardianProfileDao;
    private AccountService accountService;

    @Autowired
    public IdentityServiceImpl(EntityManager entityManager,
                               SecurityService securityService,
                               SystemService systemService,
                               DexPrincipalDao principalDao,
                               DexUserDao userDao,
                               DexActorDao actorDao,
                               DexGroupDao groupDao,
                               DexStaffDao staffDao,
                               DexGuardianDao guardianDao,
                               DexTherapistDao therapistDao,
                               DexGuardianProfileDao guardianProfileDao,
                               AccountService accountService
    ) {
        this.entityManager = entityManager;
        this.securityService = securityService;
        this.systemService = systemService;
        this.principalDao = principalDao;
        this.userDao = userDao;
        this.actorDao = actorDao;
        this.groupDao = groupDao;
        this.guardianDao = guardianDao;
        this.therapistDao = therapistDao;
        this.guardianProfileDao = guardianProfileDao;
        this.accountService = accountService;
    }

    //==============================================================================================
    // PRINCIPAL
    //==============================================================================================

    @Override
    public DexPrincipal findPrincipalById(Long id) {
        return principalDao.findById(id);
    }

    @Override
    public DexPrincipal findPrincipalByName(String name) {
        return principalDao.findByName(name);
    }

    @Override
    public List<DexPrincipal> findPrincipals(String filter, Integer offset, Integer limit) {
        return principalDao.find(filter, offset, limit);
    }

    @Override
    public Set<String> findSids(DexPrincipal principal) {
        Set<DexGroup> groups = null;
        Set<String> principals = new HashSet<String>();
        try {
            groups = groupDao.findEffectiveAsNative(principal);
        } catch (Exception e) {
            LOG.error("Error occured loading principals", e);
        } finally {
            if (null != groups) {
                for (DexGroup group : groups) {
                    principals.add(group.getName());
                }
            }
            principals.add(principal.getName());
        }
        return principals;
    }

    @Override
    public Integer countPrincipal(String filter) {
        return principalDao.count(filter);
    }

    @Override
    public void addPrincipalRole(DexPrincipal principal, DexPrincipalRole principalRole) {
        principalDao.addRole(principal, principalRole, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void deletePrincipalRole(DexPrincipal principal, DexPrincipalRole principalRole) {
        principalDao.deleteRole(principal, principalRole, securityService.getCurrentUser());
        entityManager.flush();
    }

    //==============================================================================================
    // USER
    //==============================================================================================

    @Override
    public DexUser findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public DexUser findUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public DexUser findUserById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<DexUser> findUsers(Integer offset, Integer limit) {
        return userDao.find(offset, limit);
    }

    @Override
    public List<DexUser> findUsers(String filter, Integer offset, Integer limit) {
        return userDao.find(filter, offset, limit);
    }

    @Override
    public Integer countUser() {
        return userDao.count();
    }

    @Override
    public Integer countUser(String filter) {
        return userDao.count(filter);
    }

    @Override
    public boolean isUserExists(String username) {
        return userDao.isExists(username);
    }

    @Override
    public void saveUser(DexUser user) {
        userDao.save(user, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void updateUser(DexUser user) {
        userDao.update(user, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void removeUser(DexUser user) {
        userDao.remove(user, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void changePassword(DexUser user, String newPassword) {
        user.setPassword(newPassword);
        userDao.update(user, securityService.getCurrentUser());
        entityManager.flush();
    }

    //==============================================================================================
    // GROUP
    //==============================================================================================

    @Override
    public DexGroup getRootGroup() {
        return groupDao.findByName(GROUP_ROOT);
    }

    @Override
    public DexGroup findGroupByName(String name) {
        return groupDao.findByName(name);
    }

    @Override
    public DexGroup findOrCreateGroupByName(String name) {
        DexGroup group = null;
        if (groupDao.isExists(name))
            group = groupDao.findByName(name);
        else {
            group = new DexGroupImpl();
            group.setName(name);
            group.setEnabled(true);
            group.setLocked(false);
            groupDao.save(group, securityService.getCurrentUser());
        }
        entityManager.flush();
        entityManager.refresh(group);
        return group;
    }

    @Override
    public DexGroup findGroupById(Long id) {
        return groupDao.findById(id);
    }

    @Override
    public List<DexGroup> findGroups(Integer offset, Integer limit) {
        return groupDao.find(offset, limit);
    }

    @Override
    public List<DexGroup> findImmediateGroups(DexPrincipal principal) {
        return groupDao.findImmediate(principal);
    }

    @Override
    public List<DexGroup> findImmediateGroups(DexPrincipal principal, Integer offset, Integer limit) {
        return groupDao.findImmediate(principal, offset, limit);
    }

    @Override
    public Set<DexGroup> findEffectiveGroups(DexPrincipal principal) {
        return groupDao.findEffectiveAsNative(principal);
    }

    @Override
    public Set<String> findEffectiveGroupsAsString(DexPrincipal principal) {
        Set<String> groups = new HashSet<>();
        Set<DexGroup> groupSet = groupDao.findEffectiveAsNative(principal);
        for (DexGroup adGroup : groupSet) {
            groups.add(adGroup.getName());
        }
        return groups;
    }

    @Override
    public List<DexGroup> findAvailableUserGroups(DexUser user) {
        return groupDao.findAvailableGroups(user);
    }

    @Override
    public List<DexPrincipal> findAvailableGroupMembers(DexGroup group) {
        return groupDao.findAvailableMembers(group);
    }

    @Override
    public List<DexGroupMember> findGroupMembersAsGroupMember(DexGroup group) {
        List<DexGroupMember> members = groupDao.findMembersAsGroupMember(group);
        return members;
    }

    @Override
    public List<DexPrincipal> findGroupMembers(DexGroup group) {
        return groupDao.findMembers(group);
    }

    @Override
    public List<DexPrincipal> findGroupMembers(DexGroup group, Integer offset, Integer limit) {
        return groupDao.findMembers(group, offset, limit);
    }

    @Override
    public Integer countGroup() {
        return groupDao.count();
    }

    @Override
    public Integer countGroupMember(DexGroup group) {
        return groupDao.countMember(group);
    }

    @Override
    public boolean isGroupExists(String name) {
        return groupDao.isExists(name);
    }

    @Override
    public boolean hasMembership(DexGroup group, DexPrincipal principal) {
        return groupDao.hasMembership(group, principal);
    }

    @Override
    public DexGroup createGroupWithRole(String name, DexRoleType... types) {
        DexGroup group = new DexGroupImpl();
        group.setName(name);
        group.setEnabled(true);
        group.setLocked(false);
        groupDao.save(group, securityService.getCurrentUser());
        entityManager.flush();
        entityManager.refresh(group);

        for (DexRoleType type : types) {
            DexPrincipalRole role = new DexPrincipalRoleImpl();
            role.setRole(type);
            principalDao.addRole(group, role, securityService.getCurrentUser());
            entityManager.flush();
        }
        entityManager.refresh(group);
        return group;
    }

    @Override
    public void saveGroup(DexGroup group) {
        groupDao.save(group, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void updateGroup(DexGroup group) {
        groupDao.update(group, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void removeGroup(DexGroup group) {
        groupDao.remove(group, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void addGroupMember(DexGroup group, DexPrincipal principal) throws RecursiveGroupException {
        groupDao.addMember(group, principal, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void deleteGroupMember(DexGroup group, DexPrincipal principal) {
        groupDao.deleteMember(group, principal);
        entityManager.flush();
    }


    //==============================================================================================
    // GUARDIAN
    //==============================================================================================

    @Override
    public DexGuardian findGuardianById(Long id) {
        return guardianDao.findById(id);
    }

    @Override
    public DexGuardian findGuardianByCode(String code) {
        return guardianDao.findGuardianByCode(code);
    }

    @Override
    public DexGuardian findGuardianByIdentityNo(String identityNo) {
        return guardianDao.findByIdentityNo(identityNo);
    }

    @Override
    public DexGuardian findGuardianByEmail(String email) {
        return guardianDao.findByEmail(email);
    }

    @Override
    public List<DexGuardian> findGuardians(String filter, Integer offset, Integer limit) {
        return guardianDao.findGuardians(filter, offset, limit);
    }

    @Override
    public List<DexGuardian> findGuardians(Integer offset, Integer limit) {
        return guardianDao.findGuardians(offset, limit);
    }

    @Override
    public Integer countGuardian(String filter) {
        return guardianDao.count(filter);
    }

    @Override
    public Integer countGuardian() {
        return guardianDao.count();
    }

    @Override
    public void saveGuardian(DexGuardian guardian) {
        guardianDao.save(guardian, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void updateGuardian(DexGuardian guardian) {
        guardianDao.update(guardian, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void removeGuardian(DexGuardian guardian) {
        guardianDao.remove(guardian, securityService.getCurrentUser());
        entityManager.flush();
    }

    //==============================================================================================
    // THERAPIST
    //==============================================================================================

    @Override
    public DexTherapist findTherapistById(Long id) {
        return therapistDao.findById(id);
    }

    public DexTherapist findTherapistByCode(String code) {
        return therapistDao.findByCode(code);
    }

    @Override
    public DexTherapist findTherapistByIdentityNo(String identityNo) {
        return therapistDao.findByIdentityNo(identityNo);
    }

    @Override
    public DexTherapist findTherapistByEmail(String email) {
        return therapistDao.findByEmail(email);
    }

    @Override
    public List<DexTherapist> findTherapists(String filter, Integer offset, Integer limit) {
        return therapistDao.findTherapists(filter, offset, limit);
    }

    @Override
    public List<DexTherapist> findTherapistsByGuardian(String code, Integer offset, Integer limit) {
        return therapistDao.findTherapistsByGuardian(code, offset, limit);
    }

    @Override
    public List<DexTherapist> findTherapists(Integer offset, Integer limit) {
        return therapistDao.findTherapists(offset, limit);
    }

    @Override
    public List<DexGuardian> findGuardians(DexTherapist therapist, Integer offset, Integer limit) {
        return null;
    }

    @Override
    public Integer countTherapist(String filter) {
        return therapistDao.count(filter);
    }

    @Override
    public Integer countTherapistByChild(DexChild child) {
        return therapistDao.countByChild(child);
    }

    @Override
    public Integer countGuardian(DexTherapist therapist) {
        return null;
    }

    @Override
    public void saveTherapist(DexTherapist therapist) {
        therapistDao.save(therapist, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void updateTherapist(DexTherapist therapist) {
        therapistDao.update(therapist, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void removeTherapist(DexTherapist therapist) {
        therapistDao.remove(therapist, securityService.getCurrentUser());
        entityManager.flush();
    }

    //==============================================================================================
    // STAFF
    //==============================================================================================
    @Override
    public DexStaff findStaffById(Long staffId) {
        return staffDao.findById(staffId);
    }

    @Override
    public DexStaff findStaffByCode(String code) {
        return staffDao.findStaffByCode(code);
    }

    @Override
    public DexStaff findStaffByIdentityNo(String identityNo) {
        return staffDao.findByIdentityNo(identityNo);
    }

    @Override
    public DexStaff findStaffByEmail(String email) {
        return staffDao.findByEmail(email);
    }

    @Override
    public DexStaff findStaffByActorType(DexActorType actorType) {
        return staffDao.findByActorType(actorType);
    }

    @Override
    public List<DexStaff> findStaffs(String filter, Integer offset, Integer limit) {
        return staffDao.findStaffs(filter, offset, limit);
    }

    @Override
    public List<DexStaff> findStaffs(Integer offset, Integer limit) {
        return staffDao.findStaffs(offset, limit);
    }

    @Override
    public Integer countStaff(String filter) {
        return staffDao.count(filter);
    }

    @Override
    public Integer countStaff() {
        return staffDao.count();
    }

    @Override
    public void saveStaff(DexStaff staff) {
        staffDao.save(staff, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void updateStaff(DexStaff staff) {
        staffDao.update(staff, securityService.getCurrentUser());
        entityManager.flush();
    }

    @Override
    public void removeStaff(DexStaff staff) {
        staffDao.remove(staff, securityService.getCurrentUser());
        entityManager.flush();
    }

    //==============================================================================================
    // REGISTER
    //==============================================================================================
    @Override
    public void registerGuardian(DexGuardian guardian, DexUser user) {
        guardian.setCode(systemService.generateReferenceNo(DexConstants.GUARDIAN_CODE));
        actorDao.save(guardian, securityService.getCurrentUser());
        entityManager.flush();
        entityManager.refresh(guardian);

        user.setActor(guardian);
        userDao.save(user, securityService.getCurrentUser());
        entityManager.flush();
        entityManager.refresh(user);

        DexPrincipalRole principalRole = new DexPrincipalRoleImpl();
        principalRole.setRole(DexRoleType.ROLE_GUARDIAN);
        principalRole.setPrincipal(user);
        principalDao.addRole(user, principalRole, securityService.getCurrentUser());
        entityManager.flush();

        accountService.createRegistrationToken(user);

    }

    @Override
    public void registerTherapist(DexTherapist therapist, DexUser user) {
        therapist.setCode(systemService.generateReferenceNo(DexConstants.THERAPIST_CODE));
        actorDao.save(therapist, securityService.getCurrentUser());
        entityManager.flush();
        entityManager.refresh(therapist);

//        user.setEnabled(false);
        user.setActor(therapist);
        userDao.save(user, securityService.getCurrentUser());
        entityManager.flush();
        entityManager.refresh(user);

        DexPrincipalRole principalRole = new DexPrincipalRoleImpl();
        principalRole.setRole(DexRoleType.ROLE_THERAPIST);
        principalRole.setPrincipal(user);
        principalDao.addRole(user, principalRole, securityService.getCurrentUser());
        entityManager.flush();
        accountService.createRegistrationToken(user);
    }
}
