package com.irichment.identity.domain.dao;

import com.irichment.core.domain.GenericDao;
import com.irichment.enrollment.domain.model.DexChild;
import com.irichment.identity.domain.model.DexTherapist;

import java.util.List;

public interface DexTherapistDao extends GenericDao<Long, DexTherapist> {

    DexTherapist findByCode(String code);

    DexTherapist findByIdentityNo(String identityNo);

    DexTherapist findByEmail(String email);

    List<DexTherapist> findTherapists(String filter, Integer offset, Integer limit);

    List<DexTherapist> findTherapistsByGuardian(String code, Integer offset, Integer limit);

    List<DexTherapist> findTherapists(Integer offset, Integer limit);

    List<DexTherapist> findGuardians(DexTherapist therapist, Integer offset, Integer limit);

    Integer count(String filter);

    Integer countByChild(DexChild child);

    Integer countGuardian(DexTherapist therapist);

    boolean isEmailExists(String email);

}
