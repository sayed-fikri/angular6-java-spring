package com.irichment.enrollment.domain.dao;


import com.irichment.core.domain.DexMetaObject;
import com.irichment.core.domain.DexMetaState;
import com.irichment.core.domain.DexMetadata;
import com.irichment.core.domain.GenericDaoSupport;
import com.irichment.enrollment.domain.model.DexCalibration;
import com.irichment.enrollment.domain.model.DexChild;
import com.irichment.enrollment.domain.model.DexEvaluation;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexTherapist;
import com.irichment.identity.domain.model.DexUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.Query;

import java.sql.Timestamp;
import java.util.List;

@Repository("childDao")
public class DexChildDaoImpl extends GenericDaoSupport<Long, DexChild> implements DexChildDao {

    private static final Logger LOG = LoggerFactory.getLogger(DexChildDaoImpl.class);

    public DexChildDaoImpl() {
        super(DexChildDao.class);
    }


    @Override
    public DexChild findChildByCode(String code) {
        Query query = entityManager.createQuery("select s from DexChild s where s.code = :code" +
                " and s.metadata.state = :state");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexChild) query.getSingleResult();
    }

    @Override
    public DexChild findChildByEvaluation(DexEvaluation evaluation) {
        Query query = entityManager.createQuery("select s from DexChild s where" +
                " s.evaluation = :evaluation" +
                " and s.metadata.state = :state");
        query.setParameter("evaluation", evaluation);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexChild) query.getSingleResult();
    }

    @Override
    public DexChild findChildByCalibration(DexCalibration calibration) {
        Query query = entityManager.createQuery("select s from DexChild s where" +
                " s.calibration = :calibration" +
                " and s.metadata.state = :state");
        query.setParameter("calibration", calibration);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexChild) query.getSingleResult();
    }

    @Override
    public List<DexChild> find(String filter, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexChild s where " +
                "(upper(s.code) like upper(:filter) " +
                "or upper(s.name) like upper(:filter)) " +
                "and s.metadata.state = :state ");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexChild>) query.getResultList();
    }

    @Override
    public List<DexChild> findChildByGuardian(DexGuardian guardian, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexChild s where " +
                "s.guardian =:guardian" +
                " and s.metadata.state = :state ");
        query.setParameter("guardian", guardian);
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexChild>) query.getResultList();
    }

    @Override
    public List<DexChild> findChildByTherapist(DexTherapist therapist, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexChild s where " +
                " s.therapist =:therapist" +
                " and s.metadata.state = :state ");
        query.setParameter("therapist", therapist);
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexChild>) query.getResultList();
    }

    @Override
    public Integer count(String filter) {
        Query query = entityManager.createQuery("select count(s) from DexChild s where " +
                "(upper(s.code) like upper(:filter) " +
                "or upper(s.name) like upper(:filter)) " +
                "and s.metadata.state = :state ");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer count(DexGuardian guardian) {
        Query query = entityManager.createQuery("select count(s) from DexChild s where " +
                "s.guardian = :guardian " +
                "and s.metadata.state = :state ");
        query.setParameter("guardian", guardian);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer count(DexTherapist therapist) {
        Query query = entityManager.createQuery("select count(s) from DexChild s where " +
                "s.guardian.therapist = :therapist " +
                "and s.metadata.state = :state ");
        query.setParameter("therapist", therapist);
        query.setParameter("state", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }


    public boolean isExists(String identityNo) {
        Query query = entityManager.createQuery("select count(s) from DexChild s where " +
                "s.identityNo = :identityNo " +
                "and s.metadata.state = :state ");
        query.setParameter("identityNo", identityNo);
        query.setParameter("state", DexMetaState.ACTIVE);
        return 0 < ((Long) query.getSingleResult()).intValue();
    }
}
