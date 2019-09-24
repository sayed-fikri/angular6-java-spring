package com.irichment.identity.domain.dao;


import com.irichment.core.domain.DexMetaState;
import com.irichment.core.domain.GenericDaoSupport;
import com.irichment.enrollment.domain.model.DexChild;
import com.irichment.identity.domain.model.DexTherapist;
import com.irichment.identity.domain.model.DexTherapistImpl;

import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.Query;

@Repository("therapistDao")
public class DexTherapistDaoImpl extends GenericDaoSupport<Long, DexTherapist> implements DexTherapistDao {

    public DexTherapistDaoImpl() {
        super(DexTherapistImpl.class);
    }

    @Override
    public DexTherapist findByCode(String code) {
        Query query = entityManager.createQuery("select u from DexTherapist u where " +
                "u.code = :code ");
        query.setParameter("code", code);
        return (DexTherapist) query.getSingleResult();
    }

    @Override
    public DexTherapist findByIdentityNo(String identityNo) {
        Query query = entityManager.createQuery("select u from DexTherapist u where " +
                "u.identityNo = :identityNo ");
        query.setParameter("identityNo", identityNo);
        return (DexTherapist) query.getSingleResult();
    }

    @Override
    public DexTherapist findByEmail(String email) {
        Query query = entityManager.createQuery("select u from DexTherapist u where " +
                "u.email = :identityNo ");
        query.setParameter("identityNo", email);
        return (DexTherapist) query.getSingleResult();
    }

    @Override
    public List<DexTherapist> findTherapists(String filter, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select v from DexTherapist v where" +
                "(upper(v.name) like upper(:filter)" +
                "or upper(v.hospital) like upper(:filter) " +
                "or upper(v.profession) like upper(:filter) " +
                "or upper(v.code) like upper(:filter))" +
                "and v.metadata.state = :metaState");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("metaState", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexTherapist>) query.getResultList();
    }

    @Override
    public List<DexTherapist> findTherapistsByGuardian(String code, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select v from DexTherapist v where" +
                " upper(v.code) like upper(:code)" +
                "and v.metadata.state = :metaState");
        query.setParameter("code", WILDCARD + code + WILDCARD);
        query.setParameter("metaState", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexTherapist>) query.getResultList();
    }

    @Override
    public List<DexTherapist> findTherapists(Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select v from DexTherapist v where" +
                " v.metadata.state = :metaState");
        query.setParameter("metaState", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexTherapist>) query.getResultList();
    }

    @Override
    public List<DexTherapist> findGuardians(DexTherapist therapist, Integer offset, Integer limit) {
        Query query = entityManager.createQuery("select v from DexGuardian v where " +
                " v.metadata.state = :metaState");
        query.setParameter("metaState", DexMetaState.ACTIVE);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<DexTherapist>) query.getResultList();
    }

    @Override
    public Integer count(String filter) {
        Query query = entityManager.createQuery("select count(v) from DexTherapist v where" +
                "(upper(v.name) like upper(:filter)" +
                "or upper(v.hospital) like upper(:filter) " +
                "or upper(v.profession) like upper(:filter) " +
                "or upper(v.code) like upper(:filter))" +
                "and v.metadata.state = :metaState");
        query.setParameter("filter", WILDCARD + filter + WILDCARD);
        query.setParameter("metaState", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer countByChild(DexChild children) {
        Query query = entityManager.createQuery("select count(v) from DexTherapist v where" +
                " v.metadata.state = :metaState");
        query.setParameter("metaState", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Integer countGuardian(DexTherapist therapist) {
        Query query = entityManager.createQuery("select count(v) from DexGuardian v where " +
                " v.metadata.state = :metaState");
        query.setParameter("metaState", DexMetaState.ACTIVE);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public boolean isEmailExists(String email) {
        Query query = entityManager.createQuery("select count(u) from DexTherapist u where " +
                "upper(u.email) = upper(:email) ");
        query.setParameter("email", email);
        return 0 < ((Long) query.getSingleResult()).intValue();
    }
}
