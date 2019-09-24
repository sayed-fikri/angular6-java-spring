package com.irichment.identity.domain.dao;

import com.irichment.core.domain.GenericDao;
import com.irichment.identity.domain.model.DexActorType;
import com.irichment.identity.domain.model.DexStaff;

import java.util.List;

public interface DexStaffDao extends GenericDao<Long, DexStaff> {

    DexStaff findByCode(String code);

    DexStaff findByIdentityNo(String identityNo);

    DexStaff findByEmail(String email);

    DexStaff findByActorType(DexActorType actorType);

    List<DexStaff> findStaffs(String filter, Integer offset, Integer limit);

    List<DexStaff> findStaffs(Integer offset, Integer limit);

    Integer count(String filter);

    boolean isEmailExists(String email);

    DexStaff findStaffByCode(String code);
}
