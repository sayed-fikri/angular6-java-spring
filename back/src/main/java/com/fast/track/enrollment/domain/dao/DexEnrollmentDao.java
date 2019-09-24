package com.irichment.enrollment.domain.dao;


import com.irichment.core.domain.GenericDao;
import com.irichment.enrollment.domain.model.DexChild;
import com.irichment.enrollment.domain.model.DexCoupon;
import com.irichment.enrollment.domain.model.DexEnrollment;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexUser;

import java.util.List;

public interface DexEnrollmentDao extends GenericDao<Long, DexEnrollment> {

    // ====================================================================================================
    // FINDER
    // ====================================================================================================

    DexEnrollment findByCode(String code);

    DexEnrollment findEnrollmentByChild(DexChild child);

    List<DexEnrollment> findAllEnrollments();

    List<DexEnrollment> find(String filter, Integer offset, Integer limit);

    List<DexEnrollment> findByGuardian(DexGuardian guardian);

    List<DexEnrollment> find(DexGuardian guardian, DexChild child, Integer offset, Integer limit);

    List<DexEnrollment> findByGuardian(DexGuardian guardian, Integer offset, Integer limit);

    // ====================================================================================================
    // HELPER
    // ====================================================================================================

    Integer count(String filter);

    Integer count(DexGuardian guardian, DexChild child);

    Integer countByGuardian(DexGuardian guardian);

    boolean isExists(String code);
}
