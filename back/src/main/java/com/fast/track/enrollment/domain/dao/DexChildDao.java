package com.irichment.enrollment.domain.dao;

import com.irichment.core.domain.GenericDao;
import com.irichment.enrollment.domain.model.DexCalibration;
import com.irichment.enrollment.domain.model.DexChild;
import com.irichment.enrollment.domain.model.DexEvaluation;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexTherapist;
import com.irichment.identity.domain.model.DexUser;

import java.util.List;

public interface DexChildDao extends GenericDao<Long, DexChild> {


    // ====================================================================================================
    // FINDER
    // ====================================================================================================
    DexChild findChildByCode(String code);

    DexChild findChildByEvaluation(DexEvaluation evaluation);

    DexChild findChildByCalibration(DexCalibration calibration);

    List<DexChild> find(String filter, Integer offset, Integer limit);

    List<DexChild> findChildByGuardian(DexGuardian guardian, Integer offset, Integer limit);

    List<DexChild> findChildByTherapist(DexTherapist therapist, Integer offset, Integer limit);

    // ====================================================================================================
    // HELPER
    // ====================================================================================================

    Integer count(String filter);

    Integer count(DexGuardian guardian);

    Integer count(DexTherapist therapist);

    boolean isExists(String identityNo);

}
