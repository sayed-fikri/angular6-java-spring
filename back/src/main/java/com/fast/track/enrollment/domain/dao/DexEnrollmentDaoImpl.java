package com.irichment.enrollment.domain.dao;

import com.irichment.core.domain.DexMetaObject;
import com.irichment.core.domain.DexMetaState;
import com.irichment.core.domain.DexMetadata;
import com.irichment.core.domain.GenericDaoSupport;
import com.irichment.enrollment.domain.model.DexChild;
import com.irichment.enrollment.domain.model.DexCoupon;
import com.irichment.enrollment.domain.model.DexEnrollment;
import com.irichment.enrollment.domain.model.DexEnrollmentImpl;
import com.irichment.identity.domain.model.DexGuardian;
import com.irichment.identity.domain.model.DexUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;

@Repository("enrollmentDao")
public class DexEnrollmentDaoImpl extends GenericDaoSupport<Long, DexEnrollment> implements DexEnrollmentDao {

    private static final Logger LOG = LoggerFactory.getLogger(DexEnrollmentDaoImpl.class);

    public DexEnrollmentDaoImpl() {
        super(DexEnrollmentImpl.class);
    }

    @Override
    public List<DexEnrollment> findAllEnrollments() {
        Query q = entityManager.createQuery("select e from DexEnrollment e ");
        return q.getResultList();

    }

    @Override
    public DexEnrollment findByCode(String code) {
        Query query = entityManager.createQuery("select s from DexEnrollment s where s.code = :code and  " +
                " s.metadata.state = :state");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexEnrollment) query.getSingleResult();
    }

    @Override
    public DexEnrollment findEnrollmentByChild(DexChild child) {
        Query query = entityManager.createQuery("select s from DexEnrollment s where s.child = :child and  " +
                " s.metadata.state = :state");
        query.setParameter("child", child);
        query.setParameter("state", DexMetaState.ACTIVE);
        return (DexEnrollment) query.getSingleResult();
    }

    @Override
    public List<DexEnrollment> findByGuardian(DexGuardian guardian) {
        Query query = entityManager.createQuery("select s from DexEnrollment s where " +
                "s.guardian =:guardian " +
                "and s.metadata.state = :state ");
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setParameter("guardian", guardian);
        return (List<DexEnrollment>) query.getResultList();
    }


    @Override
    public List<DexEnrollment> find(DexGuardian guardian, DexChild child, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexEnrollment s where " +
                "s.child =:child " +
                "and s.guardian =:guardian " +
                "and s.metadata.state = :state ");
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setParameter("guardian", guardian);
        query.setParameter("child", child);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexEnrollment>) query.getResultList();
    }

    @Override
    public List<DexEnrollment> find(String filter, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select v from DexEnrollment v where"+
                " upper(v.code) like upper(:filter)"+
                "and v.metadata.state = :metaState");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("metaState", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexEnrollment>) query.getResultList();
    }

    @Override
    public List<DexEnrollment> findByGuardian(DexGuardian guardian, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select s from DexEnrollment s where " +
                "s.guardian =:guardian " +
                "and s.metadata.state = :state ");
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setParameter("guardian", guardian);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexEnrollment>) query.getResultList();
    }

    @Override
    public Integer count(DexGuardian guardian, DexChild child) {
        Query query = entityManager.createQuery("select count(s) from DexEnrollment s where " +
                "s.child =:child " +
                "and s.guardian =:guardian " +
                "and s.metadata.state = :state ");
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setParameter("guardian", guardian);
        query.setParameter("child", child);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer countByGuardian(DexGuardian guardian) {
        Query query = entityManager.createQuery("select count(s) from DexEnrollment s where " +
                "s.guardian =:guardian " +
                "and s.metadata.state = :state ");
        query.setParameter("state", DexMetaState.ACTIVE);
        query.setParameter("guardian", guardian);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer count(String filter) {
        Query query = entityManager.createQuery("select count(v) from DexEnrollment v where"+
                " upper(v.code) like upper(:filter) "+
                "and v.metadata.state = :metaState");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("metaState", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public boolean isExists(String code) {
        Query query = entityManager.createQuery("select count(s) from DexEnrollment s where " +
                "s.code = :code " +
                "and s.metadata.state = :state ");
        query.setParameter("code", code);
        query.setParameter("state", DexMetaState.ACTIVE);
        return 0 < ((Long) query.getSingleResult()).intValue();
    }
}
