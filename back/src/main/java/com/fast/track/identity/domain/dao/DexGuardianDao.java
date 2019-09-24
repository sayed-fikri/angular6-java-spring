package com.irichment.identity.domain.dao;

import com.irichment.core.domain.GenericDao;
import com.irichment.identity.domain.model.DexActorType;
import com.irichment.identity.domain.model.DexGuardian;

import java.util.List;

public interface DexGuardianDao extends GenericDao<Long, DexGuardian> {

    DexGuardian findByCode(String code);

    DexGuardian findByIdentityNo(String identityNo);

    DexGuardian findByEmail(String email);

    List<DexGuardian> findGuardians(String filter, Integer offset, Integer limit);

    List<DexGuardian> findGuardians( Integer offset, Integer limit);

    Integer count(String filter);

    boolean isEmailExists(String email);

    DexGuardian findGuardianByCode(String code);
}
